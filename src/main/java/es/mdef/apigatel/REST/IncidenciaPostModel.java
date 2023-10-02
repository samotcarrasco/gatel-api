package es.mdef.apigatel.REST;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mde.acing.gatel.ModeloImpl.TipoModelo;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.PersonaConId;

@Relation(itemRelation = "incidencia")
public class IncidenciaPostModel extends RepresentationModel<IncidenciaPostModel> {

	private LocalDate fechaResolucion;
	private String estado;
	private String descripcion;
	private PersonaConId agenteResolutor;
	private EquipoConId equipo;
	private TipoIncidencia tipoIncidencia;
	
	//Averia
	private String compomente;
	private Boolean reparable;
	
	//Extravio
	private String ultimaUbicacion;
	private boolean bloqueado;
	private boolean borrado;
	private boolean encontrado;

	
	//Configuracion
	private String aplicacion;

	//Solicitud
	private Boolean aceptado;

	public LocalDate getFechaResolucion() {
		return fechaResolucion;
	}

	public String getEstado() {
		return estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public TipoIncidencia getTipoIncidencia() {
		return tipoIncidencia;
	}

	public String getCompomente() {
		return compomente;
	}

	public Boolean getReparable() {
		return reparable;
	}

	public String getUltimaUbicacion() {
		return ultimaUbicacion;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public boolean isBorrado() {
		return borrado;
	}

	public boolean isEncontrado() {
		return encontrado;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public Boolean isAceptado() {
		return aceptado;
	}

	public PersonaConId getAgenteResolutor() {
		return agenteResolutor;
	}

	public EquipoConId getEquipo() {
		return equipo;
	}

	@Override
	public String toString() {
		return "IncidenciaPostModel [fechaResolucion="
				+ fechaResolucion + ", estado=" + estado + ", descripcion=" + descripcion + ", tipoIncidencia="
				+ tipoIncidencia + ", compomente=" + compomente
				+ ", reparable=" + reparable + ", ultimaUbicacion=" + ultimaUbicacion + ", bloqueado=" + bloqueado
				+ ", borrado=" + borrado + ", encontrado=" + encontrado + ", aplicacion=" + aplicacion + ", aceptacion="
				+ aceptado + "]";
	}

}
