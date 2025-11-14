package ar.edu.utn.frba.dds.entities.algoritmoDeConsenso;

import ar.edu.utn.frba.dds.entities.Coleccion;
import ar.edu.utn.frba.dds.entities.ColeccionHecho;

import ar.edu.utn.frba.dds.entities.Origen;
import ar.edu.utn.frba.dds.entities.TipoFuente;
import ar.edu.utn.frba.dds.fuentes.IFuente;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class AlgoritmoAbsoluta implements AlgoritmoDeConsenso{

  @Override
  public void consensuarHechos(Coleccion coleccion) {
    Set<TipoFuente> fuentesColeccion = coleccion.getFuentes().stream()
        .map(IFuente::get)
        .collect(Collectors.toSet());

    for (ColeccionHecho ch : coleccion.getColeccionHechos()) {
      Set<TipoFuente> fuentesDelHecho = ch.getHecho().getOrigen().stream()
          .map(Origen::getTipoFuente)
          .collect(Collectors.toSet());

      ch.setConsensuado(fuentesDelHecho.containsAll(fuentesColeccion));
    }
  }
}
