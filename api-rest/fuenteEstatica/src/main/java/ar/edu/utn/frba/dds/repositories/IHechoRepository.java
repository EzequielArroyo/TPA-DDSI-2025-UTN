package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.entities.Hecho;

import java.util.List;

public interface IHechoRepository {
    void guardar(Hecho nuevoHecho);
    List<Hecho> obtenerHechos();
    Hecho getHechoPorNombre(String titulo);
}
