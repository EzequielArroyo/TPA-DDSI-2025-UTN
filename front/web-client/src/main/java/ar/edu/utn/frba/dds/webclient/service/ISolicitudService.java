package ar.edu.utn.frba.dds.webclient.service;

import ar.edu.utn.frba.dds.webclient.dto.SolicitudDTO;

import java.util.List;

public interface ISolicitudService {

    List<SolicitudDTO> obtenerTodas();

   void crearSolicitud(SolicitudDTO solicitudDTO);

    void aprobarSolicitud(Long id);

    void denegarSolicitud(Long id);

}
