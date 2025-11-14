package ar.edu.utn.frba.dds.services;
import ar.edu.utn.frba.dds.entities.Hecho;

import ar.edu.utn.frba.dds.entities.InputHechoDto;
import java.util.List;

public interface IHechoService {
  List <Hecho> getHechos();
  void agregarHecho(InputHechoDto nuevoHecho, String username);
  void modificarHecho(Long id,InputHechoDto nuevoHecho);
}
