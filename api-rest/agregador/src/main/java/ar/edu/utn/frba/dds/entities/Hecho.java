package ar.edu.utn.frba.dds.entities;

import ar.edu.utn.frba.dds.utils.HechosUtils;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import lombok.Getter;

import lombok.Setter;

@Entity
@Table(name = "hecho")
@Getter @Setter
public class Hecho {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "identificador", unique = true, nullable = false)
  private String identificador;

  @Column(name = "activo", nullable = false)
  private Boolean activo;

  @Column(name = "titulo")
  private String titulo;

  @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
  private String descripcion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoria_id", referencedColumnName = "id")
  private Categoria categoria;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "hecho_id", nullable = false)
  private List<Etiqueta> etiquetas;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
  private Ubicacion ubicacion;

  @Column(name = "fecha_acontecimiento")
  private LocalDateTime fechaAcontecimiento;

  @Column(name = "fecha_carga")
  private LocalDateTime fechaCarga;

  @Column(name = "es_anonimo", nullable = false)
  private Boolean esAnonimo;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "hecho_id", nullable = false)
  private List<Origen> origen;

  @OneToMany(mappedBy = "hecho", cascade = CascadeType.ALL)
  private List<ColeccionHecho> coleccionHechos;

  public Hecho() {
    this.etiquetas = new ArrayList<>();
    this.origen = new ArrayList<>();
    this.activo = true;
    this.esAnonimo = false;
    this.fechaCarga = LocalDateTime.now();
  }

  public void agregarEtiqueta(String valor) {
    this.etiquetas.add(new Etiqueta(valor));
  }

  public void agregarOrigen(TipoFuente tipo, String raiz) {
    this.origen.add(new Origen(tipo, raiz));
  }


  @PrePersist
  @PreUpdate
  public void generarIdentificador() {
    this.identificador = HechosUtils.generarIdentificador(this);
  }

}
