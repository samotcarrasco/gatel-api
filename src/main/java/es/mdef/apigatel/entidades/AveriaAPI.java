package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.Averia;

public class AveriaAPI extends IncidenciaConId implements Averia {
	
	private String componente;
	private Boolean reparable;
	
	@Override
	public TipoIncidencia getTipoIncidencia() {
		return TipoIncidencia.AVERIA;
	}

	@Override
	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}

	@Override
	public Boolean isReparable() {
		return reparable;
	}

	public void setReparable(Boolean reparable) {
		this.reparable = reparable;
	}

}
