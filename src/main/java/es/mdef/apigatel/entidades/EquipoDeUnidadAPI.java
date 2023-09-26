package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.EquipoDeUnidad;

public class EquipoDeUnidadAPI extends EquipoConId implements EquipoDeUnidad {

	@Override
	public TipoEquipo getTipoEquipo() {
		return TipoEquipo.EquipoDeUnidad;
	}
	@Override
	public String toString() {
		return "EquipoDeUnidadAPI [unidad=" +  super.toString() + "]";
	}


}
	
