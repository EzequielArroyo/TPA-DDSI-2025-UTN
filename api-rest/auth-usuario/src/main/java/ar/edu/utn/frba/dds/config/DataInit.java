package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.models.entities.Permiso;
import ar.edu.utn.frba.dds.models.entities.Rol;
import ar.edu.utn.frba.dds.models.entities.Usuario;
import ar.edu.utn.frba.dds.models.repositories.UsuariosRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
  private final UsuariosRepository usuariosRepository;
  private final PasswordEncoder passwordEncoder;

  public DataInit(UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder) {
    this.usuariosRepository = usuariosRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) {
    if (usuariosRepository.count() > 0) {
      System.out.println("Usuarios ya existen en la base de datos. No requiere inicialización.");
      return;
    }
    Usuario usuario = new Usuario();
    usuario.setUsername("user");
    usuario.setContrasenia(passwordEncoder.encode("123"));
    usuario.setEmail("eze@gmail.com");
    usuario.setNombre("Ezequiel");
    usuario.setRol(Rol.ADMIN);
    usuario.setPermisos(List.of(Permiso.CREAR_HECHO,Permiso.EDITAR_HECHO));

    usuariosRepository.save(usuario);
    System.out.println("Inicialización de datos completada. Usuario 'user' creado.");
  }
}