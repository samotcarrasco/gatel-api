package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

//import es.mde.acing.gatel.Equipo;
import es.mde.acing.gatel.ModeloImpl.TipoModelo;

@Relation(collectionRelation = "modelos")
public class ModeloListaModel extends RepresentationModel<ModeloListaModel> {

	private Long id;
	private String marca;
	private String nombreModelo;
	// private List<Equipo> equipos;
	private String imgReducida;
	private Integer stock;
	private TipoModelo tipoModelo;

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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNombreModelo() {
		return nombreModelo;
	}

	public void setNombreModelo(String nombreModelo) {
		this.nombreModelo = nombreModelo;
	}

//    public List<Equipo> getEquipos() {
//        return equipos;
//    }
//    
//    public void setEquipos(List<Equipo> equipos) {
//		this.equipos = equipos;
//	}



	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public TipoModelo getTipoModelo() {
		return tipoModelo;
	}

	public void setTipoModelo(TipoModelo tipoModelo) {
		this.tipoModelo = tipoModelo;
	}
	
	@Override
	public String toString() {
		return "ModeloListaModel [id=" + id + ", marca=" + marca + ", nombreModelo="
				+ nombreModelo // + ", equipos=" + equipos
				+ ", imgReducida=" + imgReducida + ", tipoModelo=" + tipoModelo + "]";
	}

}
