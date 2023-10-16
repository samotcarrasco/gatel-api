package es.mdef.apigatel.validation;

public class ArgumentNotValidException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ArgumentNotValidException(String accion) {
		super("Los argumentos no son válidos para " + accion);
	}

}