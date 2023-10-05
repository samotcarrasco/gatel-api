package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.Equipo;
import es.mde.acing.gatel.ModeloImpl;

public class ModeloConId extends ModeloImpl {
	private Long id;
	private String imgReducida;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImgReducida() {
		return imgReducida;
	}

	public void setImgReducida(String imgReducida) {
		this.imgReducida = imgReducida;
	}

//	public List<MaterialConId> algunos() {
//		//MaterialRepositorio repo = new MaterialRepositorio();
//	    List<MaterialConId> materiales = MaterialRepositorio.algunos();
//	    Collections.shuffle(materiales); 
//		return materiales;
//	}

	public int getStock() {
		
		int contador = 0;
		if (this.getEquipos() != null) {
		for (Equipo equipo : this.getEquipos()) {
			if (equipo.getPersona() == null && equipo.getUnidad() == null) {
				contador ++;
			}
			
		}
	}
		return contador;
	}
	
	
	@Override
	public String toString() {
		return "ModeloconId [id=" + id + " " + super.toString() + "]";
	}

}