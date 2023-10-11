package es.mdef.apigatel.REST;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.IncidenciaImpl.EstadoIncidencia;
import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;

@Relation(collectionRelation = "incidencias")
public class IncidenciaListaModel extends RepresentationModel<IncidenciaListaModel> {

	private Long id;
	private String codigo;
	private LocalDate fechaAlta;
	private LocalDate fechaResolucion;
	private String equipoN;
	private EstadoIncidencia estado;
	
	private TipoIncidencia tipoIncidencia;
	
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

	public TipoIncidencia getTipoIncidencia() {
		return tipoIncidencia;
	}

	public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
	
	

	public String getEquipoN() {
		return equipoN;
	}

	public void setEquipoN(String equipoN) {
		this.equipoN = equipoN;
	}

	@Override
	public String toString() {
		return "IncidenciaPostModel [id=" + id + ", codigo=" + codigo + ", fechaAlta=" + fechaAlta + ", fechaResolucion="
				+ fechaResolucion + ", estado=" + estado ;
	}
	
}
