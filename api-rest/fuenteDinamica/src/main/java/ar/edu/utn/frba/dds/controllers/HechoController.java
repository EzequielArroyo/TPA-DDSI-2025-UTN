package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.InputHechoDto;
import ar.edu.utn.frba.dds.services.IHechoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/hechos")
public class HechoController {

  private final IHechoService hechoService;

  public HechoController(IHechoService hechoService) {
    this.hechoService = hechoService;
  }

  @GetMapping
  public List<Hecho> obtenerHechos(){
    return this.hechoService.getHechos();
  }

  @PostMapping
  public ResponseEntity<String> crearHecho(@RequestBody InputHechoDto nuevoHecho){
    System.out.println("LLeg a dinamica");
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String username =
        (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            ? null
            : authentication.getName();
    System.out.println("=== AGREGAR HECHO ===" + username);
    this.hechoService.agregarHecho(nuevoHecho, username);
    return ResponseEntity.status(HttpStatus.CREATED).body("Hecho creado con exito");
  }
  @PutMapping("/{id}")
  public void editarHecho(@RequestBody InputHechoDto nuevoHecho, @PathVariable Long id){
    this.hechoService.modificarHecho(id, nuevoHecho);
  }
}

