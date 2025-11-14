package ar.edu.utn.frba.dds.services.impl;

import ar.edu.utn.frba.dds.Client.DesastresApiClient;
import ar.edu.utn.frba.dds.dto.input.PaginatedResponseDTO.HechoDTO;
import ar.edu.utn.frba.dds.services.IHechoService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class HechoService implements IHechoService {

  private final DesastresApiClient desastresApiClient;

  public HechoService(DesastresApiClient desastresApiClient) {
    this.desastresApiClient = desastresApiClient;
  }


  @Override
  public Flux<HechoDTO> obtenerTodosLosHechos() {
    return desastresApiClient.obtenerTodosLosHechos();
  }

  @Override
  public Flux<HechoDTO> obtenerHechosDeUnaPagina(Integer numeroPagina) {
    String url = "/api/desastres?page=" + numeroPagina + "&per_page=20";
    return desastresApiClient.obtenerPagina(url)
        .flatMapMany(response -> Flux.fromIterable(response.getData()));
  }
}
