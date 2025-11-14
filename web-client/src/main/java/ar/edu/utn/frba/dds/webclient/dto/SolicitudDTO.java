package ar.edu.utn.frba.dds.webclient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudDTO {
    private Long idHecho;
    private Long idUsuario;
    private String motivo;
}
