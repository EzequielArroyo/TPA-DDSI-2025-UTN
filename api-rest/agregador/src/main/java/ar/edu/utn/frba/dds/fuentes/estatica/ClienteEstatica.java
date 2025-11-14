package ar.edu.utn.frba.dds.fuentes.estatica;

import ar.edu.utn.frba.dds.entities.dto.input.HechoEstaticoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ClienteEstatica {
    private final WebClient webClient;
    private final String baseUrl;
    public ClienteEstatica(@Value("${fuente.estatica.baseUrl}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
    }
    public Mono<List<HechoEstaticoDto>> buscarHechos() {
        return webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(HechoEstaticoDto.class)
                .collectList();
    }
    public String raiz(){
        return this.baseUrl;
    }
}
