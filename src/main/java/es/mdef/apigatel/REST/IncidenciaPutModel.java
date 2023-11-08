package es.mdef.apigatel.REST;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.IncidenciaImpl.EstadoIncidencia;
import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.PersonaConId;

@Relation(itemRelation = "incidencia")
public class IncidenciaPutModel extends RepresentationModel<IncidenciaPutModel> {

	private LocalDate fechaResolucion;
	private EstadoIncidencia estado;
	private String descripcion;
	private PersonaConId agenteResolutor;
	private EquipoConId equipo;
	private TipoIncidencia tipoIncidencia;
	private String detalles;

	// Averia
	private String compomente;
	private Boolean reparable;

	// Extravio
	private String ultimaUbicacion;
	private boolean bloqueado;
	private boolean borrado;
	private boolean encontrado;

	public Boolean getAceptado() {
		return aceptado;
	}

	public void setAceptado(Boolean aceptado) {
		this.aceptado = aceptado;
	}

	public void setFechaResolucion(LocalDate fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setAgenteResolutor(PersonaConId agenteResolutor) {
		this.agenteResolutor = agenteResolutor;
	}

	public void setEquipo(EquipoConId equipo) {
		this.equipo = equipo;
	}

	public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	public void setCompomente(String compomente) {
		this.compomente = compomente;
	}

	public void setReparable(Boolean reparable) {
		this.reparable = reparable;
	}

	public void setUltimaUbicacion(String ultimaUbicacion) {
		this.ultimaUbicacion = ultimaUbicacion;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}

	public void setEncontrado(boolean encontrado) {
		this.encontrado = encontrado;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	// Configuracion
	private String aplicacion;

	// Solicitud
	private Boolean aceptado;

	public LocalDate getFechaResolucion() {
		return fechaResolucion;
	}

	public EstadoIncidencia getEstado() {
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

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
}