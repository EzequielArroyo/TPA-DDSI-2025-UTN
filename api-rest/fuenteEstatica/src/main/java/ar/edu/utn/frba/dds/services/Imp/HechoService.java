package ar.edu.utn.frba.dds.services.Imp;

import ar.edu.utn.frba.dds.entities.Categoria;
import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.HechoCsvData;
import ar.edu.utn.frba.dds.repositories.ICategoriaRepository;
import ar.edu.utn.frba.dds.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.services.IHechoService;
import ar.edu.utn.frba.dds.utils.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class HechoService implements IHechoService {

  private final Map<String, Reader> readers;
  private final ICategoriaRepository categoriaRepository;
  private final IHechoRepository hechoRepository;

  public HechoService(Map<String, Reader> readers, ICategoriaRepository categoriaRepository, IHechoRepository hechoRepository) {
    this.readers = readers;
    this.categoriaRepository = categoriaRepository;
    this.hechoRepository = hechoRepository;
  }

  @Override
  public List<Hecho> obtenerHechos() {
    return hechoRepository.obtenerHechos();
  }

  @Override
  public void importarHechos(String nombreArchivo) {

    String extension = obtenerExtension(nombreArchivo);
    Reader reader = readers.get(extension.toLowerCase());
    if (reader == null) {
      throw new IllegalArgumentException("extensión no validad: " + extension);
    }

    Path rutaArchivo = Paths.get("fuenteEstatica", "src", "main", "resources", "archivosCsv", nombreArchivo);
    List <HechoCsvData> hechos = reader.leer(rutaArchivo.toString());

    for (HechoCsvData hecho : hechos) {
      Hecho hechoExistente = hechoRepository.getHechoPorNombre(hecho.getTitulo());
      Categoria categoria = categoriaRepository.getCategoriaPorNombre(hecho.getCategoriaString());

      if (categoria == null) {
        categoria = new Categoria(hecho.getCategoriaString());
        categoriaRepository.agregarCategoria(categoria);
      }

      Hecho nuevoHecho = new Hecho(
              hecho.getTitulo(),
              hecho.getDescripcion(),
              categoria,
              hecho.getLatitud(),
              hecho.getLongitud(),
              hecho.getFechaAcontecimiento(),
              nombreArchivo
      );
      if(hechoExistente != null) {
        nuevoHecho.setId(hechoExistente.getId());
      }
      hechoRepository.guardar(nuevoHecho);
    }


  }
  private String obtenerExtension(String nombre) {
    int idx = nombre.lastIndexOf('.');
    if (idx == -1 || idx == nombre.length() - 1) {
      throw new IllegalArgumentException("El archivo debe tener una extensión válida");
    }
    return nombre.substring(idx + 1);
  }

}
