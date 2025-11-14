package ar.edu.utn.frba.dds.webclient.controller.vistas;
import ar.edu.utn.frba.dds.webclient.dto.input.hecho.HechoInputDTO;
import ar.edu.utn.frba.dds.webclient.dto.HechoOutputDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/contribuir")
public class ContribuyenteController {

    @GetMapping("/contribuyente")
    public String mostrarFormularioContribuyente(Model model) {
        model.addAttribute("pageTitle", "MetaMapa - Contribuir");
        model.addAttribute("nuevoHecho", new HechoInputDTO());
        return "contribuir/contribuyente";
    }

    @GetMapping("/mockConfirmacion")
    public String mockConfirmacion(Model model) {
        HechoOutputDTO mock = new HechoOutputDTO();
        mock.setTitulo("Bache en Av. Corrientes");
        mock.setCategoriaNombre("Transporte");
        mock.setFecha(java.time.LocalDateTime.now());

        model.addAttribute("hechoCreado", mock);
        model.addAttribute("contenido", "contribuir/contribuyente_confirmacion");
        model.addAttribute("pageTitle", "Hecho enviado (mock)");

        return "layout/base"; // tu plantilla base
    }

}

