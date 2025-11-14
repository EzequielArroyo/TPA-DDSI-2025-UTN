package ar.edu.utn.frba.dds.webclient.service.imp;

import ar.edu.utn.frba.dds.webclient.dto.FiltroDTO;
import ar.edu.utn.frba.dds.webclient.dto.input.coleccion.ColeccionDetalladoInputDTO;
import ar.edu.utn.frba.dds.webclient.dto.input.hecho.HechoInputDTO;
import ar.edu.utn.frba.dds.webclient.dto.input.coleccion.ColeccionInputDTO;
import ar.edu.utn.frba.dds.webclient.service.IColeccionService;
import ar.edu.utn.frba.dds.webclient.exceptions.NotFoundException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Slf4j
@Service
public class ColeccionService implements IColeccionService {

    private final WebClient webClient;

    public ColeccionService(@Value("${api.backend.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    // ================================
    // 🔹 Obtener todas las colecciones
    // ================================
    @Override
    public List<ColeccionInputDTO> obtenerTodasColecciones() {
        try {
            return webClient.get()
                    .uri("api/colecciones")
                    .retrieve()
                    .bodyToFlux(ColeccionInputDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error al obtener colecciones: {}", e.getMessage());
            throw new RuntimeException("No se pudieron obtener las colecciones");
        }
    }

    // ================================
    // 🔹 Obtener colección por ID
    // ================================
    public ColeccionDetalladoInputDTO obtenerColeccionPorId(Long id) {
        ColeccionDetalladoInputDTO coleccion = webClient.get()
                .uri("api/colecciones/{id}", id)
                .retrieve()
                .bodyToMono(ColeccionDetalladoInputDTO.class)
                .block();

        if (coleccion == null) {
            throw new NotFoundException("Colección no encontrada con id: " + id);
        }
        return coleccion;
    }

    // ================================
    // 🔹 Crear colección
    // ================================
    public ColeccionInputDTO crearColeccion(ColeccionInputDTO dto) {
        try {
            return webClient.post()
                    .uri("api/colecciones")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(ColeccionInputDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error al crear colección: {}", e.getMessage());
            throw new RuntimeException("Error al crear la colección");
        }
    }

    // ================================
    // 🔹 Editar colección existente
    // ================================
    public ColeccionInputDTO editarColeccion(Long id, ColeccionInputDTO dto) {
        try {
            return webClient.patch()
                    .uri("api/colecciones/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(ColeccionInputDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new NotFoundException("Colección no encontrada para editar: " + id);
        } catch (WebClientResponseException e) {
            log.error("Error al editar colección: {}", e.getMessage());
            throw new RuntimeException("Error al editar la colección");
        }
    }

    // ================================
    // 🔹 Eliminar colección
    // ================================
    public void eliminarColeccion(Long id) {
        try {
            webClient.delete()
                    .uri("api/colecciones/{id}", id)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new NotFoundException("Colección no encontrada para eliminar: " + id);
        } catch (WebClientResponseException e) {
            log.error("Error al eliminar colección: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar la colección");
        }
    }

    // ================================
    // 🔹 Obtener hechos de una colección
    // ================================
    public List<HechoInputDTO> obtenerHechosDeColeccion(Long id, FiltroDTO filtro) {
        try {
            return webClient.get()
                .uri(uriBuilder -> {
                    var ub = uriBuilder
                        .pathSegment("api", "colecciones", String.valueOf(id), "hechos")
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
        } catch (WebClientResponseException.NotFound e) {
            throw new NotFoundException("Colección no encontrada: " + id);
        } catch (WebClientResponseException e) {
            log.error("Error al obtener hechos: {}", e.getMessage());
            throw new RuntimeException("Error al obtener hechos de la colección");
        }
    }


    // ================================
    // 🔹 Actualizar todas las colecciones
    // ================================
    public void actualizarTodasColecciones() {
        try {
            webClient.put()
                    .uri("api/colecciones/actualizar")
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Error al actualizar colecciones: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar colecciones");
        }
    }

    public List<ColeccionInputDTO> obtenerMock(){
        return  List.of(
                new ColeccionInputDTO() {{
                    setId(1L);
                    setHandle("incidentes-urbanos");
                    setTitulo("Incidentes urbanos");
                    setDescripcion("Reportes verificados de cortes, obras y accidentes.");
                    setAlgoritmo("Curada");
                }},
                new ColeccionInputDTO() {{
                    setId(2L);
                    setHandle("avistajes");
                    setTitulo("Avistajes");
                    setDescripcion("Hechos subidos por la comunidad.");
                    setAlgoritmo("Irrestricta");
                }},
                new ColeccionInputDTO() {{
                    setId(3L);
                    setHandle("patrimonio-cultural");
                    setTitulo("Patrimonio cultural");
                    setDescripcion("Eventos y registros de valor histórico.");
                    setAlgoritmo("Curada");
                }});}

}

