package ar.edu.utn.frba.dds.entities.dto.input;

import ar.edu.utn.frba.dds.entities.Categoria;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HechoEstaticoDto {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private double latitud;
  private double longitud;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
  private String origen;
}
