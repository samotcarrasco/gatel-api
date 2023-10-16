package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;

import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;

public class EquipoAsignarModel extends RepresentationModel<EquipoAsignarModel> {

	private UnidadConId unidad;
	private PersonaConId persona;
	private EquipoConId equipo;

	public UnidadConId getUnidad() {
		return unidad;
	}

	public void setUnidad(UnidadConId unidad) {
		this.unidad = unidad;
	}

	public PersonaConId getPersona() {
		return persona;
	}

	public void setPersona(PersonaConId persona) {
		this.persona = persona;
	}

	public EquipoConId getEquipo() {
		return equipo;
	}

	public void setEquipo(EquipoConId equipo) {
		this.equipo = equipo;
	}

	@Override
	public String toString() {
		return "EquipoAsignarModel [unidad=" + unidad + ", persona=" + persona + ", equipo=" + equipo + "]";
	}

}