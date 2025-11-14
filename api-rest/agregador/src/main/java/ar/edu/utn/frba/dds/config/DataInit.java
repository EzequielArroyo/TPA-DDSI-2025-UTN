package ar.edu.utn.frba.dds.config;
import ar.edu.utn.frba.dds.entities.TipoFuente;
import ar.edu.utn.frba.dds.entities.algoritmoDeConsenso.TipoAlgoritmo;
import ar.edu.utn.frba.dds.entities.dto.input.coleccion.ColeccionDTO;
import ar.edu.utn.frba.dds.services.IColeccionService;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
  private final IColeccionService coleccionService;

  public DataInit(IColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @Override
  public void run(String... args) {
    if (coleccionService.obtenerTodasColecciones().isEmpty()) {
      ColeccionDTO coleccionDTO = new ColeccionDTO();
      coleccionDTO.setTitulo("Coleccion de Prueba");
      coleccionDTO.setDescripcion("Descripcion de la coleccion de prueba");
      coleccionDTO.setAlgoritmo(TipoAlgoritmo.SIMPLE);
      coleccionDTO.setCriterios(null);
      coleccionDTO.setFuentes(List.of(TipoFuente.DATASET));
      coleccionService.guardarColeccion(coleccionDTO);
      ColeccionDTO coleccionDTO2 = new ColeccionDTO();
      coleccionDTO2.setTitulo("Coleccion de Prueba 2");
      coleccionDTO2.setDescripcion("Descripcion de la coleccion de prueba 2");
      coleccionDTO2.setAlgoritmo(TipoAlgoritmo.SIMPLE);
      coleccionDTO2.setCriterios(null);
      coleccionDTO2.setFuentes(List.of(TipoFuente.CONTRIBUYENTE));
      coleccionService.guardarColeccion(coleccionDTO2);
      System.out.println("Datos iniciales cargados");
      coleccionService.actualizarColecciones();
      System.out.println("Se ha agregado un 2 colecciones de prueba a la base de datos.");
    }else{
      System.out.println("Las colecciones ya existen, no se cargan datos iniciales");
    }
  }
}