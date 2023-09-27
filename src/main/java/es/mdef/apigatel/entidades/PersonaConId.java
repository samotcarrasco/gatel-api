package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.PersonaImpl;

public class PersonaConId extends PersonaImpl {
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PersonaConId [id=" + id + " " + super.toString() + "]";
	}

}