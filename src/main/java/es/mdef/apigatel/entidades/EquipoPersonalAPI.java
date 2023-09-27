package es.mdef.apigatel.entidades;

import java.time.LocalDate;

import es.mde.acing.gatel.EquipoPersonal;

public class EquipoPersonalAPI extends EquipoConId implements EquipoPersonal {

	@Override
	public TipoEquipo getTipoEquipo() {
		return TipoEquipo.EquipoPersonal;
	}
	
	@Override
	public String toString() {
		return "EquipoPersonalAPI [unidad=" +  super.toString() + "]";
	}


}
	
