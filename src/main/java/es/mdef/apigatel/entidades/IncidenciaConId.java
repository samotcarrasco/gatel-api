package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.IncidenciaImpl;

public class IncidenciaConId extends IncidenciaImpl {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "IncidenciaconId [id=" + id + " " + super.toString() + "]";
	}

}