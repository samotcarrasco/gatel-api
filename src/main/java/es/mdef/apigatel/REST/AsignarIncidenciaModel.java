package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;

import es.mdef.apigatel.entidades.PersonaConId;

public class AsignarIncidenciaModel extends RepresentationModel<AsignarIncidenciaModel> {

	private PersonaConId agenteResolutor;
	
	public PersonaConId getAgenteResolutor() {
		return agenteResolutor;
	}

	public void setAgenteResolutor(PersonaConId agenteResolutor) {
		this.agenteResolutor = agenteResolutor;
	}
	
		

}
