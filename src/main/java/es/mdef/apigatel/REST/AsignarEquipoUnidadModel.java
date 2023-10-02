package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;

import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.UnidadConId;

public class AsignarEquipoUnidadModel extends RepresentationModel<AsignarEquipoUnidadModel> {

	private UnidadConId unidad;
	
	private EquipoConId equipo;

	public UnidadConId getUnidad() {
		return unidad;
	}

	public void setUnidad(UnidadConId unidad) {
		this.unidad = unidad;
	}

	public EquipoConId getEquipo() {
		return equipo;
	}

	public void setEquipo(EquipoConId equipo) {
		this.equipo = equipo;
	}
	
	

}
