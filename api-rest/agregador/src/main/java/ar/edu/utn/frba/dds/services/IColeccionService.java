package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.entities.Coleccion;
import ar.edu.utn.frba.dds.entities.dto.input.FiltroBusquedaHechosDTO;
import ar.edu.utn.frba.dds.entities.dto.input.coleccion.ColeccionDTO;
import ar.edu.utn.frba.dds.entities.dto.output.ColeccionOutputDTO;
import ar.edu.utn.frba.dds.entities.dto.output.HechoOutputDto;

import java.util.List;

public interface IColeccionService {
  List<ColeccionOutputDTO> obtenerTodasColecciones();
  Coleccion buscarColeccionePorId(Long id);
  ColeccionOutputDTO obtenerColeccionPorId(Long id);
  void guardarColeccion(ColeccionDTO coleccionDto);
  void editarColeccion(Long idColeccion, ColeccionDTO coleccionDto);
  void eliminarColeccion(Long idColeccion);
  List<HechoOutputDto> obtenerHechosDeColeccion(Long idColeccion, FiltroBusquedaHechosDTO filtros);
  void actualizarColeccion(Coleccion coleccion);
  void actualizarColecciones();
}
