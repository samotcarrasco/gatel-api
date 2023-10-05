package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import es.mde.acing.gatel.ModeloImpl.TipoModelo;

@Relation(itemRelation = "modelo")
public class ModeloPostModel extends RepresentationModel<ModeloPostModel> {

	private String marca;
	private String nombreModelo;
	private String detalles;
	private String imagen;
	private String imgReducida;
	private Boolean stereo;
	private String conexion;
	private String resolucion;
	private Integer pulgadas;
	private Integer discoDuro;
	private Integer memoria;
	private String sistemaOperativo;
	private TipoModelo tipoModelo;
	private String tipoEquipoInformatico;
 

	public String getMarca() {
		return marca;
	}


	public String getNombreModelo() {
		return nombreModelo;
	}

	public String getDetalles() {
		return detalles;
	}

	public String getImagen() {
		return imagen;
	}

	public String getImgReducida() {
		return imgReducida;
	}

	public Integer getPulgadas() {
		return pulgadas;
	}

	public Integer getDiscoDuro() {
		return discoDuro;
	}

	public Integer getMemoria() {
		return memoria;
	}

	public String getSistemaOperativo() {
		return sistemaOperativo;
	}

	public String getResolucion() {
		return resolucion;
	}

	public Boolean isStereo() {
		return stereo;
	}

	public String getConexion() {
		return conexion;
	}
	
	public String getTipoEquipoInformatico() {
		return tipoEquipoInformatico;
	}

	public TipoModelo getTipoModelo() {
		return tipoModelo;
	}

	@Override
	public String toString() {
		return "ModeloPostModel [marca=" + marca + ", nombreModelo="
				+ nombreModelo + ", detalles=" + detalles + ", imagen=" 
				+ ", stereo=" + stereo + ", conexion=" + conexion + ", resolucion=" + resolucion + ", pulgadas="
				+ pulgadas + ", discoDuro=" + discoDuro + ", memoria=" + memoria + ", sistemaOperativo="
				+ sistemaOperativo + ", tipoModelo=" + tipoModelo + ", tipoEquipoInformatico=" + tipoEquipoInformatico
				+ "]";
	}
	
}
