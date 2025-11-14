package ar.edu.utn.frba.dds.entities.dto.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudDto {
  private Long idHecho;
  private Long idUsuario;
  private String motivo;
}
