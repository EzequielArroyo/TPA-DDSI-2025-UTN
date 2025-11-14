package ar.edu.utn.frba.dds.entities;

import ar.edu.utn.frba.dds.converters.AtributoAlgoritmoConverter;
import ar.edu.utn.frba.dds.converters.AtributoFuenteConverter;
import ar.edu.utn.frba.dds.entities.algoritmoDeConsenso.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.entities.algoritmoDeConsenso.AlgoritmoNulo;
import ar.edu.utn.frba.dds.entities.criterios.Criterio;
import ar.edu.utn.frba.dds.fuentes.IFuente;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coleccion")
@Getter @Setter
public class Coleccion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "handle")
  private String handle;

  @Column(name = "titulo")
  private String titulo;

  @Column(name = "descripcion")
  private String descripcion;

  @ElementCollection
  @CollectionTable(name = "coleccion_fuente")
  @Convert(converter = AtributoFuenteConverter.class)
  @Column(name = "fuente")
  private List<IFuente> fuentes;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "coleccion_id", nullable = false)
  private List<Criterio> criterioDePertenencia;

  @Convert(converter = AtributoAlgoritmoConverter.class)
  @Column(name = "algoritmo")
  private AlgoritmoDeConsenso algoritmo;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "coleccion_id", nullable = false)
  private List<ColeccionHecho> coleccionHechos;

  public Coleccion() {
    this.handle = UUID.randomUUID().toString();
    this.algoritmo = new AlgoritmoNulo();
    this.fuentes = new ArrayList<>();
    this.coleccionHechos = new ArrayList<>();
    this.criterioDePertenencia = new ArrayList<>();
  }

  public List<Hecho> getHechos() {
    return coleccionHechos.stream()
        .map(ColeccionHecho::getHecho)
        .toList();
  }

  public void agregarHecho(Hecho hecho) {
    boolean yaExiste = this.coleccionHechos.stream()
        .anyMatch(ch -> ch.getHecho().getId() != null &&
            ch.getHecho().getId().equals(hecho.getId()));

    if (!yaExiste) {
      ColeccionHecho ch = new ColeccionHecho(hecho);
      this.coleccionHechos.add(ch);
    }
  }

  public void agregarCriterio(Criterio criterio) {
    this.criterioDePertenencia.add(criterio);
  }

  public void agregarFuente(IFuente fuente) {
    this.fuentes.add(fuente);
  }
  public void eliminarHechosObsoletos() {
    List<TipoFuente> fuentesValidas = this.fuentes.stream()
        .map(IFuente::get)
        .toList();

    List<Criterio> criterios = this.criterioDePertenencia;

    List<ColeccionHecho> hechosAEliminar = coleccionHechos.stream()
        .filter(ch -> {
          Hecho hecho = ch.getHecho();
          boolean tieneOrigenValido = hecho.getOrigen().stream()
              .anyMatch(origen -> fuentesValidas.contains(origen.getTipoFuente()));

          boolean cumpleCriterios = criterios.isEmpty() ||
              criterios.stream().allMatch(c -> c.cumple(hecho));

          return !tieneOrigenValido || !cumpleCriterios;
        })
        .toList();

    coleccionHechos.removeAll(hechosAEliminar);
  }

}
