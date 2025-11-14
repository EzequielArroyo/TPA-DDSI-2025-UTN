package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.dto.input.FiltroBusquedaHechosDTO;
import ar.edu.utn.frba.dds.entities.dto.output.HechoOutputDto;
import ar.edu.utn.frba.dds.services.IHechoService;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/hechos")
public class HechosController {

  private final IHechoService hechoService;

  public HechosController(IHechoService hechoService) {
    this.hechoService = hechoService;
  }

  @GetMapping
  public List<HechoOutputDto> getHechos(@ModelAttribute FiltroBusquedaHechosDTO filtros) {
    return hechoService.obtenerTodosHechos(filtros);
  }

  @GetMapping("/{id}")
  public HechoOutputDto getHechoById(@PathVariable Long id) {
    return hechoService.obtenerHechoOutputDtoPorId(id);
  }

}
