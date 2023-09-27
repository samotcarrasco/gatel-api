package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;

import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.PersonaConId;

public class AsignarEquipoPersonalModel extends RepresentationModel<AsignarEquipoPersonalModel> {

	private PersonaConId persona;
	
	private EquipoConId equipo;

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
	
	

}
