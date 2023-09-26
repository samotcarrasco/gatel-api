package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.UnidadImpl;

public class UnidadConId extends UnidadImpl {
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UnidadConId [id=" + id + " " + super.toString() + "]";
	}

}