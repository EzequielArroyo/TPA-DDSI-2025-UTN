package ar.edu.utn.frba.dds.schedulers;

import ar.edu.utn.frba.dds.services.IColeccionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AgregadorScheduler {
  private final IColeccionService coleccioneService;

  public AgregadorScheduler(IColeccionService coleccionService) {
    this.coleccioneService = coleccionService;
  }
  @Scheduled(cron = "${cron.agregador}")
  public void ActualizarColeccionesCadaHora(){
      coleccioneService.actualizarColecciones();
  }
}
