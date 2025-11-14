package ar.edu.utn.frba.dds.webclient.service.imp;

import ar.edu.utn.frba.dds.webclient.dto.FiltroDTO;
import ar.edu.utn.frba.dds.webclient.dto.HechoDTO;
import ar.edu.utn.frba.dds.webclient.dto.HechoOutputDTO;
import ar.edu.utn.frba.dds.webclient.dto.input.hecho.HechoInputDTO;
import ar.edu.utn.frba.dds.webclient.service.IHechoService;
import java.util.List;
import java.util.Optional;

import ar.edu.utn.frba.dds.webclient.service.internal.WebApiCallerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
public class HechoService implements IHechoService {

    private final WebClient agregadorWebClient;
    private final WebClient dinamicaWebClient;
    private final WebApiCallerService webApiCallerService;
    private final String dinamicaUrl;

    public HechoService(@Value("${api.dinamica.url}") String dinamicaBaseUrl, @Value("${api.backend.url}") String agregadorBaseUrl, WebApiCallerService webApiCallerService) {
        this.agregadorWebClient = WebClient.builder().baseUrl(agregadorBaseUrl).build();
        this.dinamicaWebClient = WebClient.builder().baseUrl(dinamicaBaseUrl).build();
        this.webApiCallerService = webApiCallerService;
        this.dinamicaUrl = dinamicaBaseUrl;
    }

    public void crearHechoSinToken(HechoDTO hechoDTO) {
        try {
            log.info("Enviando nuevo hecho: {}", hechoDTO.getTitulo());
            dinamicaWebClient
                    .post()
                    .uri("/api/hechos")
                    .bodyValue(hechoDTO)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            log.error("Error al crear el hecho: {}", e.getMessage());
            throw new RuntimeException("No se pudo crear el hecho");
        }
    }

    public List<HechoInputDTO> obtenerHechos(FiltroDTO filtro) {
        try {
            return agregadorWebClient
                    .get()
                    .uri(uriBuilder -> {
                        var ub = uriBuilder
                                .path("api/hechos")
                                .queryParamIfPresent("idCategoria", Optional.ofNullable(filtro.getIdCategoria()))
                                .queryParamIfPresent("provincia", Optional.ofNullable(filtro.getProvincia()))
                                .queryParamIfPresent("fuente", Optional.ofNullable(filtro.getFuente()))
                                .queryParamIfPresent("fechaAcontecimientoDesde",
                                        Optional.ofNullable(filtro.getFechaAcontecimientoDesde()))
                                .queryParamIfPresent("fechaAcontecimientoHasta",
                                        Optional.ofNullable(filtro.getFechaAcontecimientoHasta()))
                                .queryParamIfPresent("esAnonimo", Optional.ofNullable(filtro.getEsAnonimo()))
                                .queryParamIfPresent("curado", Optional.ofNullable(filtro.getCurado()));
                        return ub.build();
                    })
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(HechoInputDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error al obtener los hechos: {}", e.getMessage());
            throw new RuntimeException("No se pudieron obtener los hechos");
        }
    }

    public void crearHecho(HechoDTO hechoDTO) {
        try {
            String accessToken = null;
            var attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            accessToken = (String) request.getSession().getAttribute("accessToken");

            if (accessToken != null) {
                webApiCallerService.post(dinamicaUrl + "/api/hechos", hechoDTO, String.class);
            } else {
                crearHechoSinToken(hechoDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear el hecho", e);
        }
    }

    public HechoInputDTO obtenerHechoPorId(Long id) {
        return agregadorWebClient.get()
                .uri("/api/hechos/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(HechoInputDTO.class)
                .block();
    }
    public void editarHecho(Long id, HechoDTO hechoDTO) {
        try {
            webApiCallerService.put(dinamicaUrl + "/api/hechos/" + id, hechoDTO, String.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public long contarTodosLosHechos() {
        try {
            Long count = agregadorWebClient.get()
                    .uri("/api/hechos")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(HechoInputDTO.class)
                    .count()
                    .block(); // devuelve Long o null

            // Evitar null
            return count != null ? count : 0;

        } catch (WebClientResponseException.NotFound e) {
            log.warn("No se encontraron hechos: {}", e.getMessage());
            return 0;
        } catch (WebClientResponseException e) {
            log.error("Error al contar hechos: {}", e.getMessage());
            throw new RuntimeException("Error al contar hechos de la colección");
        } catch (Exception e) {
            log.error("Error inesperado al contar hechos", e);
            return 0;
        }
    }

}