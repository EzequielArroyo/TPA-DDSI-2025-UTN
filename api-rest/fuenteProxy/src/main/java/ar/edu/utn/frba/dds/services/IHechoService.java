package ar.edu.utn.frba.dds.services;
import ar.edu.utn.frba.dds.dto.input.PaginatedResponseDTO.HechoDTO;
import reactor.core.publisher.Flux;

public interface IHechoService {

  Flux<HechoDTO> obtenerTodosLosHechos();
  Flux<HechoDTO> obtenerHechosDeUnaPagina(Integer pagina);
}
