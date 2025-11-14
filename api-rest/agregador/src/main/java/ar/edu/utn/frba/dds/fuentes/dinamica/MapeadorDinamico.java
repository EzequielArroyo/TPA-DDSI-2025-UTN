package ar.edu.utn.frba.dds.fuentes.dinamica;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.Origen;
import ar.edu.utn.frba.dds.entities.TipoFuente;
import ar.edu.utn.frba.dds.entities.Ubicacion;
import ar.edu.utn.frba.dds.entities.dto.input.HechoDinamicoDto;
import ar.edu.utn.frba.dds.services.impl.CategoriaService;
import org.springframework.stereotype.Component;

@Component
public class MapeadorDinamico {
    private final CategoriaService categoriaService;

    public MapeadorDinamico(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public Hecho aDominio(HechoDinamicoDto dto) {
        Hecho h = new Hecho();
        h.setActivo(true);
        h.setTitulo(dto.getTitulo());
        h.setDescripcion(dto.getDescripcion());
        h.setCategoria(categoriaService.buscarPorId(dto.getIdCategoria()));
        h.setUbicacion(new Ubicacion(null,dto.getLatitud(),dto.getLongitud(),null));
        h.setFechaAcontecimiento(dto.getFechaAcontecimiento());
        h.setFechaCarga(dto.getFechaCarga());
        h.setEsAnonimo(dto.getEsAnonimo());
        h.getOrigen().add(new Origen(TipoFuente.CONTRIBUYENTE, dto.getUsername()));
        return h;
    }
}
