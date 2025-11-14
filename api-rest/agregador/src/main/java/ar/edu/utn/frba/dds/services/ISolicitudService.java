package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.entities.dto.input.SolicitudDto;
import ar.edu.utn.frba.dds.entities.solicitudDeEliminacion.SolicitudDeEliminacion;
import java.util.List;

public interface ISolicitudService {
  void crearSolicitud(SolicitudDto dto);
  List<SolicitudDeEliminacion> obtenerTodas();
  SolicitudDeEliminacion obtenerPorId(Long id);
  void aprobarSolicitud(Long id);
  void denegarSolicitud(Long id);
}
