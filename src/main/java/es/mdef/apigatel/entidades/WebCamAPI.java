package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.WebCam;

public class WebCamAPI extends ModeloConId implements WebCam {

	private Integer resolucion;

	@Override
	public Integer getResolucion() {
		return resolucion;
	}

	public void setResolucion(Integer resolucion) {
		this.resolucion = resolucion;
	}

	@Override
	public TipoModelo getTipoModelo() {
		return TipoModelo.WebCam;
	}

	@Override
	public String toString() {
		return "WebCamImpl [resolucion=" + resolucion + " Modelo " + super.toString() + "]";
	}

}