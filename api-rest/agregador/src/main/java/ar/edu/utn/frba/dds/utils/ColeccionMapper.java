package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.converters.AtributoAlgoritmoConverter;
import ar.edu.utn.frba.dds.entities.Coleccion;
import ar.edu.utn.frba.dds.entities.dto.output.ColeccionConHechoOutputDTo;
import ar.edu.utn.frba.dds.entities.dto.output.ColeccionOutputDTO;
import ar.edu.utn.frba.dds.fuentes.IFuente;
import org.springframework.stereotype.Component;

@Component
public class ColeccionMapper {
    private final AtributoAlgoritmoConverter atributoAlgoritmoConverter;

    public ColeccionMapper(AtributoAlgoritmoConverter atributoAlgoritmoConverter) {
        this.atributoAlgoritmoConverter = atributoAlgoritmoConverter;
    }

    public ColeccionOutputDTO aDto(Coleccion coleccion){
        ColeccionOutputDTO dto = new ColeccionOutputDTO();
        dto.setId(coleccion.getId());
        dto.setHandle(coleccion.getHandle());
        dto.setTitulo(coleccion.getTitulo());
        dto.setDescripcion(coleccion.getDescripcion());
        dto.setAlgoritmo(atributoAlgoritmoConverter.convertToDatabaseColumn(coleccion.getAlgoritmo()));
        dto.setFuentes(coleccion.getFuentes().stream().map(IFuente::get).toList());
        dto.setCriterioDePertenencia(coleccion.getCriterioDePertenencia());
        return dto;
    }
    public ColeccionConHechoOutputDTo aDtoConHechos(Coleccion coleccion){
        ColeccionConHechoOutputDTo dto = new ColeccionConHechoOutputDTo();
        dto.setId(coleccion.getId());
        dto.setHandle(coleccion.getHandle());
        dto.setTitulo(coleccion.getTitulo());
        dto.setDescripcion(coleccion.getDescripcion());
        dto.setAlgoritmo(atributoAlgoritmoConverter.convertToDatabaseColumn(coleccion.getAlgoritmo()));
        dto.setFuentes(coleccion.getFuentes().stream().map(IFuente::get).toList());
        dto.setCriterioDePertenencia(coleccion.getCriterioDePertenencia());
        dto.getHechos().addAll(coleccion.getHechos().stream().map(hecho -> new HechoMapper().aDto(hecho)).toList());
        return dto;
    }
}
