package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.Exceptions.NotEditableException;
import ar.edu.utn.frba.dds.Exceptions.NotFoundException;
import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.InputHechoDto;
import ar.edu.utn.frba.dds.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.services.IHechoService;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HechoService implements IHechoService {

  private final IHechoRepository hechoRepository;

  public HechoService(IHechoRepository hechoRepository) {
    this.hechoRepository = hechoRepository;
  }


  public List<Hecho> getHechos() {
  List<Hecho> hechos = hechoRepository.findByVigenteTrue();
    hechos.forEach(hecho -> {
      if (hecho.getEsAnonimo() && hecho.getUsername() != null) {
        hecho.setUsername("anonymousUser");
      }
    });
    return hechos;
  }

  @Override
  public void agregarHecho(InputHechoDto nuevoHecho, String username) {
    boolean anonimo = true;
    boolean editable = false;
    String usernameAGuardar = "anonymousUser";

    if (!(username == null)) {
      anonimo = nuevoHecho.getEsAnonimo();
      editable = true;
      usernameAGuardar= username;
    }

    Hecho hechoACargar = new Hecho(
        nuevoHecho.getTitulo(),
        nuevoHecho.getDescripcion(),
        nuevoHecho.getIdCategoria(),
        nuevoHecho.getLatitud(),
        nuevoHecho.getLongitud(),
        nuevoHecho.getFechaAcontecimiento());
    hechoACargar.setUsername(usernameAGuardar);
    hechoACargar.setEsAnonimo(anonimo);
    hechoACargar.setEsEditable(editable);
    this.hechoRepository.save(hechoACargar);
  }

  public void modificarHecho(Long id, InputHechoDto nuevoHecho) {
    Hecho hechoExistente = hechoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Hecho no encontrado"));

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    if (!hechoExistente.getUsername().equals(username)) {
      throw new AccessDeniedException("No puedes editar un hecho que no te pertenece");
    }

    if(!hechoExistente.getEsEditable()){
      throw new NotEditableException(hechoExistente.getId());
    }

    hechoExistente.setFechaAcontecimiento(nuevoHecho.getFechaAcontecimiento());
    hechoExistente.setLatitud(nuevoHecho.getLatitud());
    hechoExistente.setLongitud(nuevoHecho.getLongitud());
    hechoExistente.setDescripcion(nuevoHecho.getDescripcion());
    hechoExistente.setTitulo(nuevoHecho.getTitulo());
    this.hechoRepository.save(hechoExistente);
  }
}
