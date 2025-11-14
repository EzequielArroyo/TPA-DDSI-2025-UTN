package ar.edu.utn.frba.dds.entities.dto.input.estadistica;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProvinciaCantidadDTO {
  private String provincia;
  private Long cantidad;
}
