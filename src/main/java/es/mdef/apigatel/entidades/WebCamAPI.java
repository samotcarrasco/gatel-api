package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.WebCam;

public class WebCamAPI extends ModeloConId implements WebCam {

	private String resolucion;

	@Override
	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	@Override
	public TipoModelo getTipoModelo() {
		return TipoModelo.WEBCAM;
	}

	@Override
	public String toString() {
		return "WebCamImpl [resolucion=" + resolucion + " Modelo " + super.toString() + "]";
	}

}
