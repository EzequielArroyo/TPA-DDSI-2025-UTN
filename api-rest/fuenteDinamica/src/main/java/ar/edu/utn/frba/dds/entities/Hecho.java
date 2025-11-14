package ar.edu.utn.frba.dds.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hecho")
public class Hecho {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "vigente", nullable = false)
  private Boolean vigente;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @Column(name = "categoria_id")
  private Long idCategoria;

  @Column(name = "latitud")
  private Double latitud;

  @Column(name = "longitud")
  private Double longitud;

  @Column(name = "fecha_acontecimiento")
  private LocalDateTime fechaAcontecimiento;

  @Column(name = "fecha_carga")
  private LocalDateTime fechaCarga;

  @JoinColumn(name = "usuario_id")
  private String username;

  @Column(name = "es_anonimo")
  private Boolean esAnonimo;

  @Column(name = "es_editable")
  private Boolean esEditable;

   public Hecho(String titulo, String descripcion, Long categoriaId, Double latitud, Double longitud, LocalDateTime fechaAcontecimiento) {
      this.vigente = true;
      this.titulo = titulo;
      this.descripcion = descripcion;
      this.idCategoria = categoriaId;
      this.latitud = latitud;
      this.longitud = longitud;
      this.fechaAcontecimiento = fechaAcontecimiento;
      this.fechaCarga = LocalDateTime.now();
    }
  public Hecho() {
     this.fechaCarga = LocalDateTime.now();
  }
}

