package ar.edu.utn.frba.dds.repositories;
import ar.edu.utn.frba.dds.entities.Categoria;
import java.util.List;

public interface ICategoriaRepository {
    void agregarCategoria(Categoria nuevaCategoria);
    Categoria getCategoriaPorNombre(String nombre);
}
