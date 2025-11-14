package ar.edu.utn.frba.dds.webclient.dto.input.hecho;

import ar.edu.utn.frba.dds.webclient.dto.Origen;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class HechoInputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private Long categoria;
    private List<Etiqueta> etiquetas;
    private Double latitud;
    private Double longitud;
    private String provincia;
    private LocalDateTime fechaAcontecimiento;
    private LocalDateTime fechaCarga;
    private Boolean esAnonimo;
    private List<Origen> origen;
}
