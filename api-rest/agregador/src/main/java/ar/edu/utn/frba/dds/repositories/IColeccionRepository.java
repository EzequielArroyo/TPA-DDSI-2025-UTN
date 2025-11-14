package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.Coleccion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IColeccionRepository extends JpaRepository<Coleccion, Long> {
}
