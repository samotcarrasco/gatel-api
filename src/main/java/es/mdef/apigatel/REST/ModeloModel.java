package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.ModeloImpl.TipoModelo;



@Relation(itemRelation = "modelo")
public class ModeloModel extends RepresentationModel<ModeloModel> {

	private Long id;
	private String marca;
	private String categoria;
	private String nombreModelo;
	private String detalles;
	private String imagen;
	// private List<Equipo> equipos;
	private String imgReducida;
	private Boolean stereo;
	private String conexion;
	private Integer resolucion;
	private Integer pulgadas;
	private Integer discoDuro;
	private Integer memoria;
	private String sistemaOperativo;
	private TipoModelo tipoModelo;
	private String tipoEquipoInformatico;

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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getNombreModelo() {
		return nombreModelo;
	}

	public void setNombreModelo(String nombreModelo) {
		this.nombreModelo = nombreModelo;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

//    public List<Equipo> getEquipos() {
//        return equipos;
//    }
//    
//    public void setEquipos(List<Equipo> equipos) {
//		this.equipos = equipos;
//	}

	public Integer getPulgadas() {
		return pulgadas;
	}

	public void setPulgadas(Integer pulgadas) {
		this.pulgadas = pulgadas;
	}

	public Integer getDiscoDuro() {
		return discoDuro;
	}

	public void setDiscoDuro(Integer discoDuro) {
		this.discoDuro = discoDuro;
	}

	public Integer getMemoria() {
		return memoria;
	}

	public void setMemoria(Integer memoria) {
		this.memoria = memoria;
	}

	public String getSistemaOperativo() {
		return sistemaOperativo;
	}

	public void setSistemaOperativo(String sistemaOperativo) {
		this.sistemaOperativo = sistemaOperativo;
	}

	public Integer getResolucion() {
		return resolucion;
	}

	public void setResolucion(Integer resolucion) {
		this.resolucion = resolucion;
	}

	public Boolean isStereo() {
		return stereo;
	}

	public void setStereo(Boolean stereo) {
		this.stereo = stereo;
	}

	public String getConexion() {
		return conexion;
	}

	public void setConexion(String conexion) {
		this.conexion = conexion;
	}

	public TipoModelo getTipoModelo() {
		return tipoModelo;
	}

	public void setTipoModelo(TipoModelo tipoModelo) {
		this.tipoModelo = tipoModelo;
	}

	public String getTipoEquipoInformatico() {
		return tipoEquipoInformatico;
	}

	public void setTipoEquipoInformatico(String tipoEquipoInf) {
		this.tipoEquipoInformatico = tipoEquipoInf;
	}
	
	
	@Override
	public String toString() {
		return "ModeloModel [id=" + id + ", marca=" + marca + ", categoria=" + categoria + ", nombreModelo="
				+ nombreModelo + ", detalles=" + detalles + ", imagen=" + imagen // + ", equipos=" + equipos
				+ ", imgReducida=" + imgReducida + ", stereo=" + stereo + ", conexion=" + conexion + ", resolucion="
				+ resolucion + ", pulgadas=" + pulgadas + ", discoDuro=" + discoDuro + ", memoria=" + memoria
				+ ", sistemaOperativo=" + sistemaOperativo + ", tipoModelo=" + tipoModelo + "]";
	}

}
