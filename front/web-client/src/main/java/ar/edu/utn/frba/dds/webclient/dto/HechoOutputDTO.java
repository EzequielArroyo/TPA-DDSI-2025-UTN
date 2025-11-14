package ar.edu.utn.frba.dds.webclient.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class HechoOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoriaNombre;
    private List<String> etiquetas; // solo nombres de etiquetas
    private Double latitud;
    private Double longitud;
    private String provincia;
    private LocalDateTime fecha;
    private LocalDateTime fechaCarga;
    private Boolean esAnonimo;
    private List<String> origen; // solo descripciones o nombres de origen
}
