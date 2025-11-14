package ar.edu.utn.frba.dds.entities.solicitudDeEliminacion;

import ar.edu.utn.frba.dds.entities.Hecho;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "solicitud_de_eliminacion")
public class SolicitudDeEliminacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_solicitante")
    private Long idSolicitante;
    @OneToOne
    private Hecho hechoSolicitado;
    @Column(name = "motivo")
    private String motivo;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "es_spam")
    private Boolean esSpam;
    public SolicitudDeEliminacion() {
        this.estado = Estado.PENDIENTE;
        this.fecha = LocalDate.now();
        this.esSpam = false;
    }
}
