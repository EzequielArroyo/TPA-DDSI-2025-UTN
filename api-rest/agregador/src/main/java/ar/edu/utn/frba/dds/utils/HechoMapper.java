package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.dto.output.HechoOutputDto;
import org.springframework.stereotype.Component;

@Component
public class HechoMapper {
    public HechoOutputDto aDto(Hecho hecho) {
        HechoOutputDto hechoOutputDto = new HechoOutputDto();
        hechoOutputDto.setId(hecho.getId());
        hechoOutputDto.setTitulo(hecho.getTitulo());
        hechoOutputDto.setDescripcion(hecho.getDescripcion());
        hechoOutputDto.setCategoria(hecho.getCategoria().getId());
        hechoOutputDto.setEtiquetas(hecho.getEtiquetas());
        hechoOutputDto.setLatitud(hecho.getUbicacion().getLatitud());
        hechoOutputDto.setLongitud(hecho.getUbicacion().getLongitud());
        hechoOutputDto.setProvincia(hecho.getUbicacion().getProvincia());
        hechoOutputDto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
        hechoOutputDto.setFechaCarga(hecho.getFechaCarga());
        hechoOutputDto.setEsAnonimo(hecho.getEsAnonimo());
        hechoOutputDto.setOrigen(hecho.getOrigen());

        return hechoOutputDto;
    }
}
