package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.Extravio;

public class ExtravioAPI extends IncidenciaConId implements Extravio {
	
	private String ultimaUbicacion;
	private boolean bloqueado;
	private boolean borrado;
	private boolean encontrado;
	
	

	@Override
	public TipoIncidencia getTipoIncidencia() {
		return TipoIncidencia.EXTRAVIO;
	}

	@Override
	public String getUltimaUbicacion() {
		return ultimaUbicacion;
	}

	public void setUltimaUbicacion(String ultimaUbicacion) {
		this.ultimaUbicacion = ultimaUbicacion;
	}

	@Override
	public Boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	@Override
	public Boolean isBorrado() {
		return borrado;
	}

	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}

	@Override
	public Boolean isEncontrado() {
		return encontrado;
	}

	public void setEncontrado(boolean encontrado) {
		this.encontrado = encontrado;
	}

}
