package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.entities.Categoria;
import ar.edu.utn.frba.dds.entities.Etiqueta;
import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.Origen;
import ar.edu.utn.frba.dds.entities.dto.input.FiltroBusquedaHechosDTO;
import ar.edu.utn.frba.dds.entities.dto.output.HechoOutputDto;
import ar.edu.utn.frba.dds.entities.filtros.FiltroHecho;
import ar.edu.utn.frba.dds.excepciones.EntityNotFoundException;
import ar.edu.utn.frba.dds.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.services.IHechoService;
import ar.edu.utn.frba.dds.utils.FiltroFactory;
import ar.edu.utn.frba.dds.utils.HechoMapper;
import ar.edu.utn.frba.dds.utils.HechosUtils;
import java.util.List;

import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class HechoService implements IHechoService {

  private final IHechoRepository hechoRepository;
  private final CategoriaService categoriaService;
  private final GeoService geoService;
  private final FiltroFactory filtroFactory;
  private final HechoMapper hechoMapper;

  public HechoService(IHechoRepository hechoRepository, CategoriaService categoriaService, GeoService geoService, FiltroFactory filtroFactory, HechoMapper hechoMapper) {
    this.hechoRepository = hechoRepository;
    this.categoriaService = categoriaService;
      this.geoService = geoService;
    this.filtroFactory = filtroFactory;
    this.hechoMapper = hechoMapper;
  }

  @Override
  public Hecho obtenerHechoPorId(Long id) {
    return hechoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Hecho con id: " + id + " no encontrado"));
  }

  @Override
  public HechoOutputDto obtenerHechoOutputDtoPorId(Long id) {
    Hecho hecho = obtenerHechoPorId(id);
    return hechoMapper.aDto(hecho);
  }

  @Override
  public List<HechoOutputDto> obtenerTodosHechos(FiltroBusquedaHechosDTO filtrosDTO) {
    List<FiltroHecho> filtros = filtroFactory.aDominio(filtrosDTO);
    List<Hecho> hechos =  hechoRepository.findAll().stream()
        .filter(hecho -> filtros.stream().allMatch(filtro -> filtro.aplicarA(hecho)))
        .toList();
    return hechos.stream().map(hechoMapper::aDto).toList();
  }

  @Override
  @Transactional
  public Hecho guardarSiNoExiste(Hecho nuevoHecho) {
    String identificador = HechosUtils.generarIdentificador(nuevoHecho);
    Optional<Hecho> existenteOpt = hechoRepository.findByIdentificador(identificador);


    if (existenteOpt.isPresent()) {
      Hecho existente = existenteOpt.get();

      Set<String> existentes = existente.getEtiquetas().stream()
          .map(Etiqueta::getNombre)
          .collect(Collectors.toSet());

      for (Etiqueta etiqueta : nuevoHecho.getEtiquetas()) {
        if (!existentes.contains(etiqueta.getNombre())) {
          existente.getEtiquetas().add(new Etiqueta(etiqueta.getNombre()));
        }
      }

      Set<String> origenesExistentes = existente.getOrigen().stream()
          .map(o -> o.getTipoFuente() + "::" + o.getRaiz())
          .collect(Collectors.toSet());

      for (Origen origen : nuevoHecho.getOrigen()) {
        String key = origen.getTipoFuente() + "::" + origen.getRaiz();
        if (!origenesExistentes.contains(key)) {
          existente.getOrigen().add(new Origen(origen.getTipoFuente(), origen.getRaiz()));
        }
      }

      String provincia = geoService.obtenerProvincia(existente.getUbicacion().getLatitud(), existente.getUbicacion().getLongitud());
      existente.getUbicacion().setProvincia(provincia);

      return hechoRepository.save(existente);
    }
    String provincia = geoService.obtenerProvincia(nuevoHecho.getUbicacion().getLatitud(), nuevoHecho.getUbicacion().getLongitud());
    nuevoHecho.getUbicacion().setProvincia(provincia);

    return hechoRepository.save(nuevoHecho);
  }
}
