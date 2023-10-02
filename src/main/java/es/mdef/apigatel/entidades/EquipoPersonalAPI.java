package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.EquipoPersonal;

public class EquipoPersonalAPI extends EquipoConId implements EquipoPersonal {

	@Override
	public TipoEquipo getTipoEquipo() {
		return TipoEquipo.EQUIPO_PERSONAL;
	}
	
	@Override
	public String toString() {
		return "EquipoPersonalAPI [unidad=" +  super.toString() + "]";
	}


}
	
