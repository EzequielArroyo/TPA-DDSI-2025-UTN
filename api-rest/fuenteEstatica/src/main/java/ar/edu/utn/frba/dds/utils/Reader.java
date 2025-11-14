package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.entities.HechoCsvData;

import java.util.List;

public interface Reader {
  List<HechoCsvData> leer(String rutaArchivo);
}