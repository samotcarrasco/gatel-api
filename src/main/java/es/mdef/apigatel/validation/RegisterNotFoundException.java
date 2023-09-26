package es.mdef.apigatel.validation;

public class RegisterNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RegisterNotFoundException(Long id, String tipo) {
		super("No se ha encontrado el " + tipo + " " + id);
	}

	public RegisterNotFoundException(String id, String tipo) {
		super("No se ha encontrado el " + tipo + " " + id);
	}
}