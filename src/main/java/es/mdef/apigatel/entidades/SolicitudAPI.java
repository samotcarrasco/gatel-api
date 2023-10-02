package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.Solicitud;

public class SolicitudAPI extends IncidenciaConId implements Solicitud {
	
	private Boolean aceptado;

	@Override
	public Boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(Boolean aceptado) {
		this.aceptado = aceptado;
	}

	@Override
	public TipoIncidencia getTipoIncidencia() {
	    return TipoIncidencia.SOLICITUD;
	}

}
