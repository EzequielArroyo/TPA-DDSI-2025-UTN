package ar.edu.utn.frba.dds.webclient.controller;

import ar.edu.utn.frba.dds.webclient.dto.FiltroDTO;
import ar.edu.utn.frba.dds.webclient.dto.HechoDTO;
import ar.edu.utn.frba.dds.webclient.dto.TipoFuente;
import ar.edu.utn.frba.dds.webclient.dto.input.hecho.HechoInputDTO;
import ar.edu.utn.frba.dds.webclient.exceptions.DuplicateEntityException;
import ar.edu.utn.frba.dds.webclient.exceptions.NotFoundException;
import ar.edu.utn.frba.dds.webclient.service.ICategoriaService;
import ar.edu.utn.frba.dds.webclient.service.IHechoService;
import ar.edu.utn.frba.dds.webclient.service.imp.ColeccionService;
import ar.edu.utn.frba.dds.webclient.service.imp.HechoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ar.edu.utn.frba.dds.webclient.utils.HechoMapper;

@Controller
@RequestMapping("/hechos")
public class HechoController {

    private final IHechoService hechoService;
    private final ColeccionService coleccionService;
    private final ICategoriaService categoriaService;
    private final HechoMapper hechoMapper;

    public HechoController(HechoService hechoService, ColeccionService coleccionService, ICategoriaService categoriaService, HechoMapper hechoMapper) {
        this.hechoService = hechoService;
        this.coleccionService = coleccionService;
        this.categoriaService = categoriaService;
        this.hechoMapper = hechoMapper;
    }

    @GetMapping
    public String listarHechos(@ModelAttribute("filtros") FiltroDTO filtros, Model model) throws JsonProcessingException {
        List<HechoInputDTO> hechos = new ArrayList<>();
        if (filtros.getIdColeccion() == null) {
            hechos.addAll(hechoService.obtenerHechos(filtros));
        } else {
            hechos.addAll(coleccionService.obtenerHechosDeColeccion(filtros.getIdColeccion(), filtros));
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String hechosJson = mapper.writeValueAsString(hechos);
        model.addAttribute("categorias", categoriaService.obtenerTodas());
        model.addAttribute("hechos", hechos);
        model.addAttribute("hechosJson", hechosJson);
        model.addAttribute("pageTitle", "MetaMapa - Ver hechos");
        return "hechos/ver-todo";
    }

    @PostMapping("/filtrar")
    public String filtrarHechos(@ModelAttribute FiltroDTO f, RedirectAttributes ra) {
        if (f.getIdColeccion() != null) ra.addAttribute("idColeccion", f.getIdColeccion());
        if (f.getIdCategoria() != null) ra.addAttribute("idCategoria", f.getIdCategoria());
        if (hasText(f.getProvincia())) ra.addAttribute("provincia", f.getProvincia());
        if (hasText(f.getFuente())) ra.addAttribute("fuente", f.getFuente());
        if (f.getFechaAcontecimientoDesde() != null)
            ra.addAttribute("fechaAcontecimientoDesde", f.getFechaAcontecimientoDesde());
        if (f.getFechaAcontecimientoHasta() != null)
            ra.addAttribute("fechaAcontecimientoHasta", f.getFechaAcontecimientoHasta());
        if (Boolean.TRUE.equals(f.getEsAnonimo())) ra.addAttribute("esAnonimo", true);
        if (Boolean.TRUE.equals(f.getCurado())) ra.addAttribute("curado", true);

        return "redirect:/hechos";
    }

    @GetMapping("/crear")
    public String formularioCrearHecho(Model model) {
        model.addAttribute("hecho", new HechoDTO());
        model.addAttribute("categorias", categoriaService.obtenerTodas());
        return "hechos/crear";
    }

    @PostMapping("/crear")
    public String crearHecho(@Valid @ModelAttribute("hecho") HechoDTO hechoDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categorias", categoriaService.obtenerTodas());
            return "hechos/crear";
        }
        try {
            hechoService.crearHecho(hechoDTO);
            redirectAttributes.addFlashAttribute("message", "Hecho creado exitosamente");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue(e.getFieldName(), "error" + e.getFieldName(), e.getMessage());
            return "hechos/crear";
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear el hecho: ");
        }
        return "redirect:/hechos";
    }

    @GetMapping({"/{id}"})
    public String DetalleHecho(@PathVariable Long id, Model model) {
        HechoInputDTO hecho = hechoService.obtenerHechoPorId(id);

        Boolean tieneContribuyente = hecho.getOrigen().stream()
            .anyMatch(o -> o.getTipoFuente().equals(TipoFuente.CONTRIBUYENTE));

        model.addAttribute("tieneContribuyente", tieneContribuyente);
        model.addAttribute("hecho", hecho);
        model.addAttribute("pageTitle", "MetaMapa - Ver hecho");
        return "hechos/ver-detalle";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditarHecho(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("ID recibido: " + id);
        try {
            HechoInputDTO hechoInputDTO = hechoService.obtenerHechoPorId(id);
            HechoDTO hecho = hechoMapper.aHechoDTO(hechoInputDTO);
            model.addAttribute("hecho", hecho);
            model.addAttribute("categorias", categoriaService.obtenerTodas());
            model.addAttribute("id", id);
            return "hechos/editar";
        } catch (NotFoundException ex) {
            redirectAttributes.addFlashAttribute("mensaje", ex.getMessage());
            return "redirect:/404";
        }
    }

    @PostMapping("/{id}/editar")
    public String editarHecho(@PathVariable Long id, @Valid @ModelAttribute("hecho") HechoDTO hechoDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("ID recibido para edición: " + id);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("categorias", categoriaService.obtenerTodas());
            return "hechos/editar";
        }
        try{
            System.out.println("Editando hecho con ID: " + id);
            hechoService.editarHecho(id, hechoDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Hecho editado correctamente.");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/hechos/{id}/editar";
        }
        return"redirect:/";

    }
    private boolean hasText(String s){ return s!=null && !s.trim().isEmpty(); }
}

