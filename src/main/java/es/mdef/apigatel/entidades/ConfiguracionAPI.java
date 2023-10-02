package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.Configuracion;

public class ConfiguracionAPI extends IncidenciaConId implements Configuracion {
	
	private String aplicacion;

	@Override
	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	@Override
	public TipoIncidencia getTipoIncidencia() {
	    return TipoIncidencia.CONFIGURACION;
	}

	@Override
	public String toString() {
		return "ConfiguracionAPI [aplicacion=" + aplicacion + "]";
	}

	
}
