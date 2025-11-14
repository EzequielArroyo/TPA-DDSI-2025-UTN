package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.entities.Hecho;

import java.util.List;


public interface IHechoService {

  List<Hecho> obtenerHechos();
  void importarHechos(String rutaArchivo);
}
