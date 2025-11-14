package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.detectorDeSpam.DetectorDeSpam;
import ar.edu.utn.frba.dds.entities.dto.input.SolicitudDto;
import ar.edu.utn.frba.dds.entities.solicitudDeEliminacion.Estado;
import ar.edu.utn.frba.dds.entities.solicitudDeEliminacion.SolicitudDeEliminacion;
import ar.edu.utn.frba.dds.excepciones.EntityNotFoundException;
import ar.edu.utn.frba.dds.excepciones.RequestAlreadyResolvedException;
import ar.edu.utn.frba.dds.repositories.ISolicitudRepository;
import ar.edu.utn.frba.dds.services.IHechoService;
import ar.edu.utn.frba.dds.services.ISolicitudService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService implements ISolicitudService {
  private final ISolicitudRepository solicitudRepository;
  private final DetectorDeSpam detectorDeSpam;
  private final IHechoService hechoService;

  public SolicitudService(ISolicitudRepository solicitudRepository, DetectorDeSpam detectorDeSpam, IHechoService hechoService) {
    this.solicitudRepository = solicitudRepository;
    this.detectorDeSpam = detectorDeSpam;
    this.hechoService = hechoService;
  }

  @Override
  public void crearSolicitud(SolicitudDto solicitudDto) {
    SolicitudDeEliminacion solicitud = new SolicitudDeEliminacion();
    solicitud.setIdSolicitante(solicitudDto.getIdUsuario());
    solicitud.setMotivo(solicitudDto.getMotivo());
    solicitud.setHechoSolicitado(hechoService.obtenerHechoPorId(solicitudDto.getIdHecho()));
    if (esSpam(solicitudDto)) {
        solicitud.setEsSpam(Boolean.TRUE);
        solicitud.setEstado(Estado.DENEGADA);
        solicitudRepository.save(solicitud);
    } else{
      solicitudRepository.save(solicitud);
    }
  }

  @Override
  public List<SolicitudDeEliminacion> obtenerTodas() {
    return solicitudRepository.findAll();
  }

  @Override
  public SolicitudDeEliminacion obtenerPorId(Long id) {
    return solicitudRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Solicitud con id: " + id + " no encontrada"));
  }

  @Override
  public void aprobarSolicitud(Long id) {
    SolicitudDeEliminacion solicitud = obtenerPorId(id);
    if(solicitud.getEstado() == Estado.APROBADA) {
      throw new RequestAlreadyResolvedException("La solicitud ya ha sido aprobada");
    }
    Hecho hecho = hechoService.obtenerHechoPorId(solicitud.getHechoSolicitado().getId());
    hecho.setActivo(false);
    hechoService.guardarSiNoExiste(hecho);
    solicitud.setEstado(Estado.APROBADA);
    solicitudRepository.save(solicitud);
  }

  @Override
  public void denegarSolicitud(Long id) {
    SolicitudDeEliminacion solicitud = obtenerPorId(id);
    if(solicitud.getEstado() == Estado.DENEGADA) {
      throw new RequestAlreadyResolvedException("La solicitud ya ha sido denegada");
    }
    solicitud.setEstado(Estado.DENEGADA);
    solicitudRepository.save(solicitud);
  }

  private boolean esSpam(SolicitudDto solicitud) {
    if (solicitud.getIdHecho() == null) {
      throw new IllegalArgumentException("El ID del hecho no puede ser nulo");
    }
    hechoService.obtenerHechoPorId(solicitud.getIdHecho());
    return solicitud.getMotivo() == null || detectorDeSpam.esSpam(solicitud.getMotivo());
  }
}
