package ar.edu.utn.frba.dds.fuentes.dinamica;

import ar.edu.utn.frba.dds.entities.dto.input.HechoDinamicoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ClienteDinamica {
    private final WebClient webClient;
    private final String baseUrl;
    public ClienteDinamica(@Value("${fuente.dinamica.baseUrl}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
        this.baseUrl = baseUrl;
    }
    public Mono<List<HechoDinamicoDto>> buscarHechos() {
        return webClient.get()
                .uri("/hechos")
                .retrieve()
                .bodyToFlux(HechoDinamicoDto.class)
                .collectList();
    }
    public String raiz(){
        return this.baseUrl;
    }
}
