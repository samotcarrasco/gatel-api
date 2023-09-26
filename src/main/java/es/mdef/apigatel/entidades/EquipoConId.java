package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.EquipoImpl;

public class EquipoConId extends EquipoImpl {
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EquipoConId [id=" + id + " " + super.toString() + "]";
	}

}