package ar.edu.utn.frba.dds.fuentes.proxy;

import ar.edu.utn.frba.dds.entities.Categoria;
import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.entities.Origen;
import ar.edu.utn.frba.dds.entities.TipoFuente;
import ar.edu.utn.frba.dds.entities.Ubicacion;
import ar.edu.utn.frba.dds.entities.dto.input.HechoProxyDto;
import java.time.OffsetDateTime;

import ar.edu.utn.frba.dds.services.impl.CategoriaService;
import org.springframework.stereotype.Component;

@Component
public class MapeadorProxy {
    private final CategoriaService categoriaService;

    public MapeadorProxy(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    public Hecho aDominio(HechoProxyDto dto, String raiz) {
        Hecho h = new Hecho();
        h.setActivo(true);
        h.setTitulo(dto.getTitulo());
        h.setDescripcion(dto.getDescripcion());
        h.setCategoria(categoriaService.buscarOCrearCategoria(dto.getCategoria()));
        h.setUbicacion(new Ubicacion(dto.getLatitud().doubleValue(),dto.getLongitud().doubleValue()));
        h.setFechaAcontecimiento(OffsetDateTime.parse(dto.getFecha_hecho()).toLocalDateTime());
        h.setFechaCarga(OffsetDateTime.parse(dto.getCreated_at()).toLocalDateTime());
        h.setEsAnonimo(false);
        h.getOrigen().add(new Origen(TipoFuente.PROXY, raiz));
        return h;
    }
    /*
    * OffsetDateTime odt = OffsetDateTime.parse(dto.getFecha_hecho());
h.setFechaAcontecimiento(odt.toLocalDateTime());

* h.setFechaAcontecimiento(OffsetDateTime.parse(dto.getFecha_hecho()).toLocalDateTime());
h.setFechaCarga(OffsetDateTime.parse(dto.getUpdated_at()).toLocalDateTime());

*
* DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
h.setFechaAcontecimiento(LocalDateTime.parse(dto.getFecha_hecho(), formatter));

* String fecha = dto.getFecha_hecho().replace("Z", "");
h.setFechaAcontecimiento(LocalDateTime.parse(fecha));
    * */
}
