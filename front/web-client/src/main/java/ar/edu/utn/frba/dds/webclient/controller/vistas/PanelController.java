package ar.edu.utn.frba.dds.webclient.controller.vistas;

import ar.edu.utn.frba.dds.webclient.service.imp.ColeccionService;
import ar.edu.utn.frba.dds.webclient.service.imp.HechoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class PanelController {

    private final ColeccionService coleccionService;

    public PanelController(ColeccionService coleccionService) {
        this.coleccionService = coleccionService;
    }

    @GetMapping()
    public String administrador(Model model) {

        model.addAttribute("colecciones", coleccionService.obtenerTodasColecciones());
        model.addAttribute("pageTitle", "Panel de Administrador");

        return "panel/panel";
    }
}