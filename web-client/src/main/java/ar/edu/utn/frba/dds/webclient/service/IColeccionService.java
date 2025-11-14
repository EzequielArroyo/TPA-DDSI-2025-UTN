package ar.edu.utn.frba.dds.webclient.service;

import ar.edu.utn.frba.dds.webclient.dto.FiltroDTO;
import ar.edu.utn.frba.dds.webclient.dto.input.coleccion.ColeccionDetalladoInputDTO;
import ar.edu.utn.frba.dds.webclient.dto.input.hecho.HechoInputDTO;
import ar.edu.utn.frba.dds.webclient.dto.input.coleccion.ColeccionInputDTO;


import java.util.List;

public interface IColeccionService {

    List<ColeccionInputDTO> obtenerMock();
    // Obtener todas las colecciones
    List<ColeccionInputDTO> obtenerTodasColecciones();

    // Obtener colección por ID
    ColeccionDetalladoInputDTO obtenerColeccionPorId(Long id);

    // Crear una nueva colección
    ColeccionInputDTO crearColeccion(ColeccionInputDTO dto);

    // Editar una colección existente
    ColeccionInputDTO editarColeccion(Long id, ColeccionInputDTO dto);

    // Eliminar una colección
    void eliminarColeccion(Long id);

    // Obtener hechos de una colección
    List<HechoInputDTO> obtenerHechosDeColeccion(Long id, FiltroDTO filtros);

    // Actualizar todas las colecciones
    void actualizarTodasColecciones();
}
