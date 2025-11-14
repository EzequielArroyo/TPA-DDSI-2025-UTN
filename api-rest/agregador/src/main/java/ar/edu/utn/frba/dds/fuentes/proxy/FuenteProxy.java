package ar.edu.utn.frba.dds.fuentes.proxy;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.TipoFuente;
import ar.edu.utn.frba.dds.fuentes.IFuente;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FuenteProxy implements IFuente {
    private final ClienteProxy fuente;
    private final MapeadorProxy mapper;

    @Override
    public TipoFuente get() {
        return TipoFuente.PROXY;
    }
    
    public FuenteProxy(ClienteProxy fuente, MapeadorProxy mapper) {
        this.fuente = fuente;
        this.mapper = mapper;
    }

    @Override
    public Mono<List<Hecho>> importarHechos() {
        String raiz = fuente.raiz();
        return fuente.buscarHechos()
                .map(dtos -> dtos.stream()
                        .map(dto -> mapper.aDominio(dto, raiz))
                        .toList());
    }
}
