package ar.edu.utn.frba.dds.webclient.dto.input.coleccion;

import ar.edu.utn.frba.dds.webclient.dto.input.hecho.HechoInputDTO;
import ar.edu.utn.frba.dds.webclient.dto.TipoFuente;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

// DTO para recibir del backend
@Getter @Setter
public class ColeccionDetalladoInputDTO {
    private Long id;
    private String handle;
    private String titulo;
    private String descripcion;
    private List<TipoFuente> fuentes;
    private List<CriterioDTO> criterioDePertenencia;
    private String algoritmo;
    private List<HechoInputDTO> hechos;
}
