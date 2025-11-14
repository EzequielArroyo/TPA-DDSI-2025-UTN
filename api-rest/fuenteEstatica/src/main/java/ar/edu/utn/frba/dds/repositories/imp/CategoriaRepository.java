package ar.edu.utn.frba.dds.repositories.imp;

import ar.edu.utn.frba.dds.entities.Categoria;
import ar.edu.utn.frba.dds.repositories.ICategoriaRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaRepository implements ICategoriaRepository {
  private final AtomicLong idGenerator = new AtomicLong(1);
  private final Map< Long, Categoria> categorias = new HashMap<>();

  public void agregarCategoria(Categoria categoria) {
      Long nuevoId = idGenerator.getAndIncrement();
      categoria.setId(nuevoId);
      this.categorias.put(nuevoId, categoria);
  }

  public Categoria getCategoriaPorNombre(String nombre) {
    return this.categorias.values().stream()
            .filter(categoria -> categoria.getNombre().equalsIgnoreCase(nombre))
            .findFirst()
            .orElse(null);
  }

    public List<Categoria> obtenerCategorias() {
        return this.categorias.values().stream().toList();
    }

}
