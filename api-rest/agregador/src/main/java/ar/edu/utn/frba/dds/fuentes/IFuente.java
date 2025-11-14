package ar.edu.utn.frba.dds.fuentes;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.TipoFuente;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IFuente {
    TipoFuente get();
    Mono<List<Hecho>> importarHechos();
}
