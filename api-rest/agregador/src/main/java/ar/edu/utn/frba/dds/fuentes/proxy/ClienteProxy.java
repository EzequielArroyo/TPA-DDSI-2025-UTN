package ar.edu.utn.frba.dds.fuentes.proxy;

import ar.edu.utn.frba.dds.entities.dto.input.HechoProxyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ClienteProxy {
    private final WebClient webClient;
    private final String baseUrl;
    public ClienteProxy(@Value("${fuente.proxy.baseUrl}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
    }
    public Mono<List<HechoProxyDto>> buscarHechos() {
        return webClient.get()
                .uri("/hechos/1")
                .retrieve()
                .bodyToFlux(HechoProxyDto.class)
                .collectList();
    }
    public String raiz(){
        return this.baseUrl;
    }
}
