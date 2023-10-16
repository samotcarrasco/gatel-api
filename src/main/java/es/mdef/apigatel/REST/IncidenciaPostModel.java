package es.mdef.apigatel.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mdef.apigatel.entidades.EquipoConId;

@Relation(itemRelation = "incidencia")
public class IncidenciaPostModel extends RepresentationModel<IncidenciaPostModel> {

	private String descripcion;
	private EquipoConId equipo;
	private TipoIncidencia tipoIncidencia;
	
	//Averia
	private String componente;
	
	//Extravio
	private String ultimaUbicacion;
	
	//Configuracion
	private String aplicacion;

	//Solicitud
	//private Boolean aceptado;

	
	public String getDescripcion() {
		return descripcion;
	}

	public TipoIncidencia getTipoIncidencia() {
		return tipoIncidencia;
	}

	public String getComponente() {
		return componente;
	}

	public String getUltimaUbicacion() {
		return ultimaUbicacion;
	}

	public String getAplicacion() {
		return aplicacion;
	}

//	public Boolean isAceptado() {
//		return aceptado;
//	}

	public EquipoConId getEquipo() {
		return equipo;
	}

	@Override
	public String toString() {
		return "IncidenciaPostModel [descripcion=" + descripcion + ", equipo=" + equipo + ", tipoIncidencia="
				+ tipoIncidencia + ", componente=" + componente + ", ultimaUbicacion=" + ultimaUbicacion
				+ ", aplicacion=" + aplicacion + "]";
	}

}
