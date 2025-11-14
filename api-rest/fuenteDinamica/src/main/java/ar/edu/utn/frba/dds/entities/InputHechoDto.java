package ar.edu.utn.frba.dds.entities;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InputHechoDto {
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaAcontecimiento;
    private Long idCategoria;
    private Double latitud;
    private Double longitud;
    private Boolean esAnonimo;

}
