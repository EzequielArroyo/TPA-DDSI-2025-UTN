package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.entities.dto.input.SolicitudDto;
import ar.edu.utn.frba.dds.entities.solicitudDeEliminacion.SolicitudDeEliminacion;
import ar.edu.utn.frba.dds.services.ISolicitudService;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/solicitudes")
public class SolicitudController {

  private final ISolicitudService solicitudService;

  public SolicitudController(ISolicitudService solicitudService) {
    this.solicitudService = solicitudService;
  }

  @PostMapping
  public ResponseEntity<String> crearSolicitud(@RequestBody SolicitudDto solicitudDto) {
    System.out.println("Llegó solicitud al backend");

    // Obtener el usuario logueado
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            ? null
            : authentication.getName();
    System.out.println("=== CREAR SOLICITUD === Usuario: " + username);

    // Llamada al service
    solicitudService.crearSolicitud(solicitudDto);

    return ResponseEntity.status(HttpStatus.CREATED).body("Solicitud creada correctamente");
  }
  @GetMapping
  public ResponseEntity<List<SolicitudDeEliminacion>> obtenerTodas() {
    return ResponseEntity.ok(solicitudService.obtenerTodas());
  }
  @PostMapping("/{id}/aprobar")
  public ResponseEntity<String> aprobarSolicitud(@PathVariable Long id) {
    solicitudService.aprobarSolicitud(id);
    return ResponseEntity.ok("Solicitud aprobada correctamente.");
  }
  @PostMapping("/{id}/denegar")
  public ResponseEntity<String> denegarSolicitud(@PathVariable Long id) {
    solicitudService.denegarSolicitud(id);
    return ResponseEntity.ok("Solicitud denegada correctamente.");
  }
}
