package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.services.IHechoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hechos")
public class HechoController {

  private final IHechoService iHechoService;

  public HechoController(IHechoService iHechoService) {
    this.iHechoService = iHechoService;
  }

  @GetMapping
  public List<Hecho> obtenerHechos(){
    return iHechoService.obtenerHechos();
  }

  @PostMapping(value = "/importar/{rutaArchivo}")
  public void importarHechos(@PathVariable String rutaArchivo) {
    iHechoService.importarHechos(rutaArchivo);
  }

}
