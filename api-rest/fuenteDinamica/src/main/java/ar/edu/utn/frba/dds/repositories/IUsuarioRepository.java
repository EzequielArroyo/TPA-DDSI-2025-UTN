package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

}
