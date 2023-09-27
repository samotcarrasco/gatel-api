package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import es.mde.acing.gatel.PersonaImpl.TipoPersona;

@Relation(collectionRelation = "personas")
public class PersonaListaModel extends RepresentationModel<PersonaListaModel> {
	private String nombre;
	private String apellidos;
	private String email;
	private TipoPersona tipoPersona;
	private String dni;
	private String tip;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "PersonaListaModel [nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", tipoPersona=" + tipoPersona + "]";
	}

}
