package ar.edu.utn.frba.dds.webclient.dto;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class FiltroDTO {
    private Long idColeccion;
    private Long idCategoria;
    private String provincia;
    private String fuente;
    private String fechaAcontecimientoDesde;
    private String fechaAcontecimientoHasta;
    private Boolean esAnonimo = false;
    private Boolean curado = false;
}
