package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "unidad", collectionRelation = "unidades")
public class UnidadModel extends RepresentationModel<UnidadModel> {

	private Long id;
	private String nombre;
	private String codigoUnidad;
	private String correoOficial;
	private String telefono;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoUnidad() {
		return codigoUnidad;
	}

	public void setCodigoUnidad(String codigoUnidad) {
		this.codigoUnidad = codigoUnidad;
	}

	public String getCorreoOficial() {
		return correoOficial;
	}

	public void setCorreoOficial(String correoOficial) {
		this.correoOficial = correoOficial;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String toString() {
		return "UnidadModel [id=" + id + ", nombre=" + nombre + ", codigoUnidad=" + codigoUnidad + ", correoOficial="
				+ correoOficial + ", telefono=" + telefono + "]";
	}

}
