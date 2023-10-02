package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.EquipoDeUnidad;

public class EquipoDeUnidadAPI extends EquipoConId implements EquipoDeUnidad {

	@Override
	public TipoEquipo getTipoEquipo() {
		return TipoEquipo.EQUIPO_UNIDAD;
	}
	@Override
	public String toString() {
		return "EquipoDeUnidadAPI [unidad=" +  super.toString() + "]";
	}


}
	
