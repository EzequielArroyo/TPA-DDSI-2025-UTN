package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.solicitudDeEliminacion.SolicitudDeEliminacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISolicitudRepository extends JpaRepository<SolicitudDeEliminacion, Long> {

}
