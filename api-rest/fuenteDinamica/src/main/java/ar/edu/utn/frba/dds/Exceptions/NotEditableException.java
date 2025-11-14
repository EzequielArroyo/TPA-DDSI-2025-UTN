package ar.edu.utn.frba.dds.Exceptions;

public class NotEditableException extends RuntimeException {
  public NotEditableException(Long id) {
    super("El hecho" + id + "no se puede editar");
  }
}
