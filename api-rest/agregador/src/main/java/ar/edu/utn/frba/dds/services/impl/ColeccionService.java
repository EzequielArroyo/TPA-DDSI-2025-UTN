package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.entities.*;
import ar.edu.utn.frba.dds.entities.algoritmoDeConsenso.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.entities.criterios.Criterio;
import ar.edu.utn.frba.dds.entities.dto.input.FiltroBusquedaHechosDTO;
import ar.edu.utn.frba.dds.entities.dto.input.coleccion.ColeccionDTO;
import ar.edu.utn.frba.dds.entities.dto.output.ColeccionOutputDTO;
import ar.edu.utn.frba.dds.entities.dto.output.HechoOutputDto;
import ar.edu.utn.frba.dds.excepciones.EntityNotFoundException;
import ar.edu.utn.frba.dds.fuentes.IFuente;
import ar.edu.utn.frba.dds.utils.*;
import ar.edu.utn.frba.dds.entities.filtros.FiltroHecho;
import ar.edu.utn.frba.dds.repositories.IColeccionRepository;
import ar.edu.utn.frba.dds.services.IColeccionService;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ColeccionService implements IColeccionService {

  private final ColeccionFactory coleccionFactory;
  private final IColeccionRepository coleccionRepository;
  private final HechoService hechoService;
  private final FiltroFactory filtroFactory;
  private final ColeccionMapper coleccionMapper;
  private final HechoMapper hechoMapper;
  private final AlgoritmoFactory algoritmoFactory;
  private final FuenteFactory fuenteFactory;
  private final CriterioFactory criterioFactory;

  public ColeccionService(ColeccionFactory coleccionFactory, IColeccionRepository coleccionRepository, HechoService hechoService, FiltroFactory filtroFactory, ColeccionMapper coleccionMapper, HechoMapper hechoMapper, AlgoritmoFactory algoritmoFactory, FuenteFactory fuenteFactory, CriterioFactory criterioFactory) {
    this.coleccionFactory = coleccionFactory;
    this.coleccionRepository = coleccionRepository;
    this.hechoService = hechoService;
    this.filtroFactory = filtroFactory;
    this.coleccionMapper = coleccionMapper;
    this.hechoMapper = hechoMapper;
    this.algoritmoFactory = algoritmoFactory;
    this.fuenteFactory = fuenteFactory;
    this.criterioFactory = criterioFactory;
  }
  @Override
  @Transactional
  public List<ColeccionOutputDTO> obtenerTodasColecciones() {
    List<ColeccionOutputDTO> coleccionOutputDTO = new ArrayList<>();
    List<Coleccion> coleccion = coleccionRepository.findAll();
    for (Coleccion c : coleccion) {
      coleccionOutputDTO.add(coleccionMapper.aDto(c));
    }
    return coleccionOutputDTO;
  }

  public Coleccion buscarColeccionePorId(Long id) {
    return coleccionRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Coleccion con id: " + id + " no encontrada"));
  }
  public ColeccionOutputDTO obtenerColeccionPorId(Long id) {
    Coleccion coleccion = buscarColeccionePorId(id);
    return coleccionMapper.aDto(coleccion);
  }
  @Override
  public List<HechoOutputDto> obtenerHechosDeColeccion(Long idColeccion, FiltroBusquedaHechosDTO filtrosDTO) {
    List<FiltroHecho> filtros  = filtroFactory.aDominio(filtrosDTO);
    Coleccion coleccion = buscarColeccionePorId(idColeccion);
    //TODO: filtrar por curados
    List<Hecho> hechos = new ArrayList<>();
    for (ColeccionHecho coleccionhecho : coleccion.getColeccionHechos()) {
      hechos.add(hechoService.obtenerHechoPorId(coleccionhecho.getHecho().getId()));
    }
    hechos = (hechos.stream()
        .filter(hecho -> filtros.stream().allMatch(filtro -> filtro.aplicarA(hecho)))
        .toList());

    return hechos.stream().map(hechoMapper::aDto).toList();

  }

  @Override
  public void guardarColeccion(ColeccionDTO coleccionDto) {
    Coleccion coleccion = coleccionFactory.aDominio(coleccionDto);
    coleccionRepository.save(coleccion);
  }

  public void editarColeccion(Long idColeccion, ColeccionDTO coleccionDto) {
    Coleccion coleccionExistente = buscarColeccionePorId(idColeccion);

    if (coleccionDto.getTitulo() != null) {
      coleccionExistente.setTitulo(coleccionDto.getTitulo());
    }

    if (coleccionDto.getDescripcion() != null) {
      coleccionExistente.setDescripcion(coleccionDto.getDescripcion());
    }

    if (coleccionDto.getAlgoritmo() != null) {
      AlgoritmoDeConsenso algoritmo = algoritmoFactory.aDominio(coleccionDto.getAlgoritmo());
      coleccionExistente.setAlgoritmo(algoritmo);
    }

    if (coleccionDto.getFuentes() != null) {
      if (!coleccionDto.getFuentes().isEmpty()) {
        List<IFuente> nuevasFuentes = coleccionDto.getFuentes().stream()
            .map(fuenteFactory::aDominio)
            .toList();
        coleccionExistente.getFuentes().clear();
        coleccionExistente.getFuentes().addAll(nuevasFuentes);
      }
    }
    // Criterios
    if (coleccionDto.getCriterios() != null) {
      if(!coleccionDto.getCriterios().isEmpty()) {
        List<Criterio> nuevosCriterios = coleccionDto.getCriterios().stream()
            .map(criterioFactory::aDominio)
            .toList();

        coleccionExistente.getCriterioDePertenencia().clear();
        coleccionExistente.getCriterioDePertenencia().addAll(nuevosCriterios);
      }
    }

    coleccionRepository.save(coleccionExistente);
  }

  @Override
  public void eliminarColeccion(Long idColeccion) {
    Coleccion coleccion = buscarColeccionePorId(idColeccion);
    coleccionRepository.delete(coleccion);
  }

  @Override
  @Transactional
  public void actualizarColecciones(){
    List<Coleccion> colecciones = coleccionRepository.findAll();
    for (Coleccion coleccion : colecciones) {
      actualizarColeccion(coleccion);
    }
  }

  @Override
  @Transactional
  public void actualizarColeccion(Coleccion coleccion) {
    coleccion.eliminarHechosObsoletos();

    List<Hecho> hechosImportados = coleccion.getFuentes().stream()
        .map(IFuente::importarHechos)
        .filter(Objects::nonNull)
        .map(Mono::block)
        .filter(Objects::nonNull)
        .flatMap(List::stream)
        .toList();

    if (!hechosImportados.isEmpty()) {
      for (Hecho hecho : hechosImportados) {
        boolean cumple = coleccion.getCriterioDePertenencia().isEmpty()
            || coleccion.getCriterioDePertenencia().stream().allMatch(c -> c.cumple(hecho));

        if (cumple) {

          Hecho persistido = hechoService.guardarSiNoExiste(hecho); // fusión adentro
          coleccion.agregarHecho(persistido);
        }
      }
      coleccionRepository.save(coleccion);
    }
  }
}
