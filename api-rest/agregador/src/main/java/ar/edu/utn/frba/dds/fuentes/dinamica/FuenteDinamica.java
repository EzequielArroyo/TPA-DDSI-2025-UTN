package ar.edu.utn.frba.dds.fuentes.dinamica;
import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.TipoFuente;
import ar.edu.utn.frba.dds.fuentes.IFuente;
import java.util.List;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FuenteDinamica implements IFuente {
    private final ClienteDinamica fuente;
    private final MapeadorDinamico mapper;

    public FuenteDinamica(ClienteDinamica fuente, MapeadorDinamico mapper) {
        this.fuente = fuente;
        this.mapper = mapper;
    }

    @Override
    public TipoFuente get() {
        return TipoFuente.CONTRIBUYENTE;
    }

    @Override
    public Mono<List<Hecho>> importarHechos() {
        String raiz = fuente.raiz();
        return fuente.buscarHechos()
                .map(dtos -> dtos.stream()
                        .map(dto -> mapper.aDominio(dto))
                        .toList());
    }
}
