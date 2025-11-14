package ar.edu.utn.frba.dds.webclient.service;

import ar.edu.utn.frba.dds.webclient.dto.FiltroDTO;
import ar.edu.utn.frba.dds.webclient.dto.HechoDTO;
import ar.edu.utn.frba.dds.webclient.dto.input.hecho.HechoInputDTO;
import java.util.List;

public interface IHechoService {
     void crearHecho(HechoDTO hecho);
     List<HechoInputDTO> obtenerHechos(FiltroDTO filtro);
     HechoInputDTO obtenerHechoPorId(Long id);
     void editarHecho(Long id, HechoDTO hechoDTO);
}
