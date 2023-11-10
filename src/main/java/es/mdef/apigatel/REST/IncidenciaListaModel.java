package es.mdef.apigatel.REST;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.IncidenciaImpl.EstadoIncidencia;
import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mde.acing.gatel.ModeloImpl.TipoModelo;

@Relation(collectionRelation = "incidencias")
public class IncidenciaListaModel extends RepresentationModel<IncidenciaListaModel> {

	private Long id;
	private String codigo;
	private LocalDate fechaAlta;
	private LocalDate fechaResolucion;
	private String equipoN;
	private EstadoIncidencia estado;
	private LocalDate fechaCierre;
	private LocalDate fechaAsignacion;
	private String detalles;
	private String descripcion;

	private TipoIncidencia tipoIncidencia;
	
    private TipoModelo tipoModelo;
    private String tipoEquipoInformatico;
	

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
		return "IncidenciaPostModel [id=" + id + ", codigo=" + codigo + ", fechaAlta=" + fechaAlta
				+ ", fechaResolucion=" + fechaResolucion + ", estado=" + estado;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public LocalDate getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(LocalDate fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public void setTipoEquipoInformatico(String tipoEquipoInformatico) {
		this.tipoEquipoInformatico = tipoEquipoInformatico;
	}

}
