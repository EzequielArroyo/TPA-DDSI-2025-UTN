package ar.edu.utn.frba.dds.repositories;


import ar.edu.utn.frba.dds.entities.Hecho;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IHechoRepository extends JpaRepository<Hecho, Long> {
  Optional<Hecho> findByIdentificador(String identificador);
}
