package ar.edu.utn.frba.dds.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Hecho {
  private Long id;
  private String titulo;
  private String descripcion;
  private Categoria categoria;
  private double latitud;
  private double longitud;
  private LocalDate fechaAcontecimiento;
  private LocalDate fechaCarga;
  private String origen;

  /**
   * Constructor de la clase Hecho.
   *
   */
  public Hecho(String titulo, String descripcion, Categoria categoria, double latitud, double longitud, LocalDate fechaAcontecimiento, String origen) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.latitud = latitud;
    this.longitud = longitud;
    this.fechaAcontecimiento = fechaAcontecimiento;
    this.fechaCarga = LocalDate.now();
    this.origen = origen;
  }
}
