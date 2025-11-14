package ar.edu.utn.frba.dds.webclient.controller.vistas;

import ar.edu.utn.frba.dds.webclient.service.imp.ColeccionService;
import ar.edu.utn.frba.dds.webclient.service.imp.HechoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estadisticas")
public class EstadisticaController {

    private final HechoService hechoService;

    public EstadisticaController(HechoService hechoService) {
        this.hechoService = hechoService;
    }

    @GetMapping()
    public String administrador(Model model) {

        model.addAttribute("hechos", hechoService.contarTodosLosHechos());
        model.addAttribute("fuentes", 2);
        model.addAttribute("solicitudes", 0);
        model.addAttribute("pageTitle", "Estadisticas - Metamapa");

        return "estadisticas/estadisticas";
    }
}