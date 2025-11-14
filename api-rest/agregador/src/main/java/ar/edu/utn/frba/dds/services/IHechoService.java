package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.dto.input.FiltroBusquedaHechosDTO;
import ar.edu.utn.frba.dds.entities.dto.output.HechoOutputDto;
import java.util.List;

public interface IHechoService {
  Hecho obtenerHechoPorId(Long id);
  List<HechoOutputDto> obtenerTodosHechos(FiltroBusquedaHechosDTO filtrosDTO);
  Hecho guardarSiNoExiste(Hecho hecho);
  HechoOutputDto obtenerHechoOutputDtoPorId(Long id);
}
