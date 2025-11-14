package ar.edu.utn.frba.dds.entities.dto.input.Criterio;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("ubicacion")
public class CriterioPorUbicacionDTO implements CriterioDTO{
    private String tipo;
    private String provincia;

    @Override
    public String getTipo() {
        return this.tipo;
    }

}
