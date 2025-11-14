package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.entities.Coleccion;
import ar.edu.utn.frba.dds.entities.criterios.Criterio;
import ar.edu.utn.frba.dds.entities.dto.input.coleccion.ColeccionDTO;
import org.springframework.stereotype.Component;

@Component
public class ColeccionFactory {
    private final CriterioFactory criterioFactory;
    private final FuenteFactory fuenteFactory;
    private final AlgoritmoFactory algoritmoFactory;

    public ColeccionFactory(CriterioFactory criterioFactory, FuenteFactory fuenteFactory, AlgoritmoFactory algoritmoFactory) {
        this.criterioFactory = criterioFactory;
        this.fuenteFactory = fuenteFactory;
        this.algoritmoFactory = algoritmoFactory;
    }

    public Coleccion aDominio(ColeccionDTO coleccionDto) {
        Coleccion coleccion = new Coleccion();
        coleccion.setTitulo(coleccionDto.getTitulo());
        coleccion.setDescripcion(coleccionDto.getDescripcion());

        if (coleccionDto.getCriterios() != null) {
            for (var criterioDto : coleccionDto.getCriterios()) {
                Criterio criterio = criterioFactory.aDominio(criterioDto);
                coleccion.agregarCriterio(criterio);
            }
        }

        if (coleccionDto.getFuentes() != null) {
            for (var tipoFuente : coleccionDto.getFuentes()) {
                var fuente = fuenteFactory.aDominio(tipoFuente);
                coleccion.agregarFuente(fuente);
            }
        }

        if (coleccionDto.getAlgoritmo() != null) {
            var algoritmo = algoritmoFactory.aDominio(coleccionDto.getAlgoritmo());
            coleccion.setAlgoritmo(algoritmo);
        }

        return coleccion;

    }
}
