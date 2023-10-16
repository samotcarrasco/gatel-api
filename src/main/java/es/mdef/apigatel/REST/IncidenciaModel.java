package es.mdef.apigatel.REST;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.IncidenciaImpl.EstadoIncidencia;
import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;

@Relation(itemRelation = "incidencia")
public class IncidenciaModel extends RepresentationModel<IncidenciaModel> {

	private Long id;
	private String codigo;
	private LocalDate fechaAlta;
	private LocalDate fechaResolucion;
	private EstadoIncidencia estado;
	private String descripcion;
	private String equipoN;
	
	private TipoIncidencia tipoIncidencia;
	
	//Averia
	private String componente;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public LocalDate getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public LocalDate getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(LocalDate fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public EstadoIncidencia getEstado() {
		return estado;
	}

	public void setEstado(EstadoIncidencia estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoIncidencia getTipoIncidencia() {
		return tipoIncidencia;
	}

	public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}

	public Boolean getReparable() {
		return reparable;
	}

	public void setReparable(Boolean reparable) {
		this.reparable = reparable;
	}

	public String getUltimaUbicacion() {
		return ultimaUbicacion;
	}

	public void setUltimaUbicacion(String ultimaUbicacion) {
		this.ultimaUbicacion = ultimaUbicacion;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public boolean isBorrado() {
		return borrado;
	}

	public void setBorrado(boolean borrado) {
		this.borrado = borrado;
	}

	public boolean isEncontrado() {
		return encontrado;
	}

	public void setEncontrado(boolean encontrado) {
		this.encontrado = encontrado;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	public Boolean isAceptado() {
		return aceptado;
	}

	public void setAceptado(Boolean aceptado) {
		this.aceptado = aceptado;
	}
	
	

	public String getEquipoN() {
		return equipoN;
	}

	public void setEquipoN(String equipoN) {
		this.equipoN = equipoN;
	}

	@Override
	public String toString() {
		return "IncidenciaModel [id=" + id + ", codigo=" + codigo + ", fechaAlta=" + fechaAlta + ", fechaResolucion="
				+ fechaResolucion + ", estado=" + estado + ", descripcion=" + descripcion + ", tipoIncidencia="
				+ tipoIncidencia + ", componente=" + componente
				+ ", reparable=" + reparable + ", ultimaUbicacion=" + ultimaUbicacion + ", bloqueado=" + bloqueado
				+ ", borrado=" + borrado + ", encontrado=" + encontrado + ", aplicacion=" + aplicacion + ", aceptacion="
				+ aceptado + "]";
	}

	
	
	
}
