package ar.edu.utn.frba.dds.fuentes.estatica;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.TipoFuente;
import ar.edu.utn.frba.dds.fuentes.IFuente;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FuenteEstatica implements IFuente {
    private final ClienteEstatica fuente;
    private final MapeadorEstatico mapper;

    public FuenteEstatica(ClienteEstatica fuente, MapeadorEstatico mapper) {
        this.fuente = fuente;
        this.mapper = mapper;
    }


    @Override
    public TipoFuente get() {
        return TipoFuente.DATASET;
    }

    @Override
    public Mono<List<Hecho>> importarHechos() {
        return fuente.buscarHechos()
                .map(dtos -> dtos.stream()
                        .map(mapper::aDominio)
                        .toList());
    }
}
