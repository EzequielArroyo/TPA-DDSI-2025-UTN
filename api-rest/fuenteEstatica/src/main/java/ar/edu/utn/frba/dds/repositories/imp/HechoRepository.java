package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.entities.Hecho;
import ar.edu.utn.frba.dds.repositories.IHechoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class HechoRepository implements IHechoRepository {

  private final AtomicLong idGenerator = new AtomicLong(1);
  private final Map<Long, Hecho> hechos = new HashMap<>();

  @Override
  public void guardar(Hecho nuevoHecho){
    if(nuevoHecho.getId() == null) {
      Long nuevoId = idGenerator.getAndIncrement();
      nuevoHecho.setId(nuevoId);
      hechos.put(nuevoId, nuevoHecho);
    }else{
      hechos.put(nuevoHecho.getId(), nuevoHecho);
    }
  }

  @Override
  public List<Hecho> obtenerHechos() {
    return hechos.values().stream().toList();
  }

  @Override
  public Hecho getHechoPorNombre(String titulo) {
    return hechos.values().stream()
            .filter(hecho -> hecho.getTitulo().equalsIgnoreCase(titulo))
            .findFirst()
            .orElse(null);
  }
}
