package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.entities.Coleccion;
import ar.edu.utn.frba.dds.entities.algoritmoDeConsenso.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.repositories.IColeccionRepository;
import ar.edu.utn.frba.dds.services.IAlgoritmoService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AlgoritmoService implements IAlgoritmoService {
  private final IColeccionRepository coleccionRepository;

  public AlgoritmoService(IColeccionRepository coleccionRepository) {
    this.coleccionRepository = coleccionRepository;
  }

  public void consensuarHechos() {
    List<Coleccion> colecciones = coleccionRepository.findAll();

    for (Coleccion coleccion : colecciones) {
      AlgoritmoDeConsenso algoritmo = coleccion.getAlgoritmo();
      algoritmo.consensuarHechos(coleccion);
    }
    coleccionRepository.saveAll(colecciones);
  }
}
