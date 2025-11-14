package ar.edu.utn.frba.dds.entities.criterios;

import ar.edu.utn.frba.dds.entities.Hecho;

import ar.edu.utn.frba.dds.entities.filtros.FiltroPorCategoria;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("categoria")
@Getter @Setter @NoArgsConstructor
public class CriterioPorCategoria extends Criterio{
    @Column(name="id_categoria")
    private Long idCategoria;


    public CriterioPorCategoria(Long idCategoria) {
        this.nombre = "categoria";
        this.idCategoria = idCategoria;
    }

    @Override
    public Boolean cumple(Hecho hecho) {
        return new FiltroPorCategoria(idCategoria).aplicarA(hecho);
    }
}
