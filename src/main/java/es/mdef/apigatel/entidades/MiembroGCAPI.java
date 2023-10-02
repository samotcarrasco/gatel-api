package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.MiembroGC;
import es.mde.acing.gatel.MiembroGCImpl.Empleo;

public class MiembroGCAPI extends PersonaConId implements MiembroGC {
	
	private String tip;
	private Empleo empleo;
	
	@Override
	public TipoPersona getTipoPersona() {
		return TipoPersona.MIEMBRO_GC;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public Empleo getEmpleo() {
		return empleo;
	}

	public void setEmpleo(Empleo empleo) {
		this.empleo = empleo;
	}

	@Override
	public String toString() {
		return "MiembroGCAPI [tip=" + tip + ", empleo=" + empleo + ", getTipoPersona()=" + getTipoPersona()
				+ super.toString() ;
	}

	

}
	
