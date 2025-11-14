package ar.edu.utn.frba.dds.webclient.controller;

import ar.edu.utn.frba.dds.webclient.dto.SolicitudDTO;
import ar.edu.utn.frba.dds.webclient.service.ISolicitudService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final ISolicitudService solicitudService;

    public SolicitudController(ISolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public String listarSolicitudes(Model model) {
        model.addAttribute("solicitudes", solicitudService.obtenerTodas());
        model.addAttribute("pageTitle", "Solicitudes de Eliminación");
        return "panel/solicitudes";
    }

    @GetMapping("/crear/{hechoId}")
    public String mostrarFormularioCreacion(@PathVariable Long hechoId, Model model) {
        SolicitudDTO solicitudDto = new SolicitudDTO();
        solicitudDto.setIdHecho(hechoId);
        model.addAttribute("solicitudDto", solicitudDto);
        model.addAttribute("pageTitle", "Reportar Hecho");
        return "solicitudes/crear"; // HTML en carpeta solicitudes
    }

    @PostMapping("/solicitudes/crear")
    public String crearSolicitud(@Valid @ModelAttribute("solicitud") SolicitudDTO solicitudDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("==> Entró a crearSolicitud");
        logger.info("Datos recibidos: {}", solicitudDTO);

        if (bindingResult.hasErrors()) {
            logger.warn("Errores de validación: {}", bindingResult.getAllErrors());
            return "solicitudes/crear"; // vuelve al formulario si hay errores de validación
        }

        try {
            logger.info("Llamando a solicitudService.crearSolicitud...");
            solicitudService.crearSolicitud(solicitudDTO);
            logger.info("Solicitud creada correctamente");

            redirectAttributes.addFlashAttribute("message", "Solicitud creada exitosamente");
            redirectAttributes.addFlashAttribute("messageType", "success");

            logger.info("Redirigiendo a la misma página del formulario con flash message");
            return "redirect:/solicitudes/crear/" + solicitudDTO.getIdHecho(); // o al path que corresponda
        } catch (Exception e) {
            logger.error("Error al crear la solicitud: {}", e.getMessage(), e);
            model.addAttribute("error", "Error al crear la solicitud: " + e.getMessage());
            return "solicitudes/crear"; // vuelve al formulario en caso de error
        }
    }



    @PostMapping("/{id}/aprobar")
    public String aprobarSolicitud(@PathVariable Long id) {
        solicitudService.aprobarSolicitud(id);
        return "redirect:/panel/solicitudes";
    }

    @PostMapping("/{id}/denegar")
    public String denegarSolicitud(@PathVariable Long id) {
        solicitudService.denegarSolicitud(id);
        return "redirect:/panel/solicitudes";
    }
}
