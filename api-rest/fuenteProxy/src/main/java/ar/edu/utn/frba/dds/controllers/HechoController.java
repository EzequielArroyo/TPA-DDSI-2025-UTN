package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.dto.input.PaginatedResponseDTO.HechoDTO;
import ar.edu.utn.frba.dds.services.IHechoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/apiProxy")
public class HechoController {

    private final IHechoService hechoService;

    public HechoController(IHechoService hechoService) {
        this.hechoService = hechoService;
    }

    @GetMapping("/hechos/{id}")
    public ResponseEntity<Flux<HechoDTO>> obtenerHechoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(hechoService.obtenerHechosDeUnaPagina(id));
    }

    @GetMapping("/hechos")
    public ResponseEntity<Flux<HechoDTO>> obtenerTodosLosHechos() {
        return ResponseEntity.ok(hechoService.obtenerTodosLosHechos());
    }
}
