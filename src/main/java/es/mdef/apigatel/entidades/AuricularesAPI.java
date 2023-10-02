package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.Auriculares;

public class AuricularesAPI extends ModeloConId implements Auriculares {

	private Boolean stereo;
	private String conexion;

	@Override
	public Boolean isStereo() {
		return stereo;
	}

	public void setStereo(Boolean stereo) {
		this.stereo = stereo;
	}

	@Override
	public String getConexion() {
		return conexion;
	}

	public void setConexion(String conexion) {
		this.conexion = conexion;
	}

	@Override
	public TipoModelo getTipoModelo() {
		return TipoModelo.AURICULARES;
	}

	@Override
	public String toString() {
		return "AuricularesAPI [stereo=" + stereo + ", conexion=" + conexion + " Modelo " + super.toString() + "]";
	}

}
