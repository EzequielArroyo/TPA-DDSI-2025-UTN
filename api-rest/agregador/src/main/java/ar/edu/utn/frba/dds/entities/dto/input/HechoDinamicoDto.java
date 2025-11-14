package ar.edu.utn.frba.dds.entities.dto.input;

import ar.edu.utn.frba.dds.entities.Categoria;

import ar.edu.utn.frba.dds.entities.Usuario;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HechoDinamicoDto {
  private Long id;
  private Boolean vigente;
  private String titulo;
  private String descripcion;
  private Long idCategoria;
  private Double latitud;
  private Double longitud;
  private LocalDateTime fechaAcontecimiento;
  private LocalDateTime fechaCarga;
  private String username;
  private Boolean esAnonimo;
}
