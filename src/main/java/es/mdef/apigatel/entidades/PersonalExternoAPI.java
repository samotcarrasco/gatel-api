package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.PersonalExterno;

public class PersonalExternoAPI extends PersonaConId implements PersonalExterno {
	
	private String dni;
	private String empresa;
	
	@Override
	public TipoPersona getTipoPersona() {
		return TipoPersona.PERSONAL_EXTERNO;
	}
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	public String getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	@Override
	public String toString() {
		return "PersonalExternoAPI [dni=" + dni + ", empresa=" + empresa + super.toString() +"]";
	}
	
}
	
