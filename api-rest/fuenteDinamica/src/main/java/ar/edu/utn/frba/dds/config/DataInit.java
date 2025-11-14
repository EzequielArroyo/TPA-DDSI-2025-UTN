package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.InputHechoDto;
import ar.edu.utn.frba.dds.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.repositories.IUsuarioRepository;
import ar.edu.utn.frba.dds.services.IHechoService;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
  private final IHechoRepository hechoRepository;

  public DataInit(IHechoRepository hechoRepository) {

    this.hechoRepository = hechoRepository;
  }

  @Override
  public void run(String... args) {
    if(hechoRepository.count() > 0){
      System.out.println("Ya hay hechos cargados, no se agrega ninguno nuevo");
      return;
    }
    Hecho nuevoHecho = new Hecho();
    nuevoHecho.setVigente(true);
    nuevoHecho.setTitulo("Caída de aeronave impacta en Olavarría");
    nuevoHecho.setDescripcion("Grave caída de aeronave ocurrió en las inmediaciones de Olavarría, Buenos Aires. El incidente provocó pánico entre los residentes locales. Voluntarios de diversas organizaciones se han sumado a las tareas de auxilio.");
    nuevoHecho.setFechaAcontecimiento(LocalDateTime.of(2001, 11, 29, 0, 0));
    nuevoHecho.setIdCategoria(1L);
    nuevoHecho.setLatitud(-36.868375);
    nuevoHecho.setLongitud(-60.343297);
    nuevoHecho.setEsAnonimo(false);
    nuevoHecho.setEsEditable(true);
    nuevoHecho.setUsername("user");
    hechoRepository.save(nuevoHecho);
    System.out.println("Se ha agregado un hecho de prueba a la base de datos.");

  }
}
