package ar.edu.utn.frba.dds.utils;


import ar.edu.utn.frba.dds.entities.criterios.Criterio;
import ar.edu.utn.frba.dds.entities.criterios.CriterioPorCategoria;
import ar.edu.utn.frba.dds.entities.criterios.CriterioPorRangoFechaAcontecimiento;
import ar.edu.utn.frba.dds.entities.criterios.CriterioPorProvincia;
import ar.edu.utn.frba.dds.entities.dto.input.Criterio.CriterioDTO;
import ar.edu.utn.frba.dds.entities.dto.input.Criterio.CriterioPorCategoriaDTO;
import ar.edu.utn.frba.dds.entities.dto.input.Criterio.CriterioPorFechaDTO;
import ar.edu.utn.frba.dds.entities.dto.input.Criterio.CriterioPorUbicacionDTO;
import org.springframework.stereotype.Component;

@Component
public class CriterioFactory {

    public Criterio aDominio(CriterioDTO dto) {
        if (dto instanceof CriterioPorFechaDTO f) {
          return new CriterioPorRangoFechaAcontecimiento(f.getFechaDesde(), f.getFechaHasta());
        }
        if (dto instanceof CriterioPorUbicacionDTO u) {
          return new CriterioPorProvincia(u.getProvincia());
        }
        if (dto instanceof CriterioPorCategoriaDTO cat) {
            return new CriterioPorCategoria(cat.getIdCategoria());
        }
        throw new IllegalArgumentException("Tipo de criterio no soportado: " + dto.getClass().getSimpleName());
    }
}