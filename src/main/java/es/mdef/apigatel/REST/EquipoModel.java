package es.mdef.apigatel.REST;

import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.Modelo;
import es.mde.acing.gatel.EquipoImpl.TipoEquipo;
import es.mde.acing.gatel.ModeloImpl.TipoModelo;

@Relation(itemRelation = "equipo")
public class EquipoModel extends RepresentationModel<EquipoModel> {

	private Long id;
	private String numeroSerie;
	private LocalDate fechaAdquisicion;
	private LocalDate fechaAsignacion;
	private TipoEquipo tipoEquipo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}
	
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	
	public LocalDate getFechaAdquisicion() {
		return fechaAdquisicion;
	}
	
	public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
		this.fechaAdquisicion = fechaAdquisicion;
	}
	
	public LocalDate getFechaAsignacion() {
		return fechaAsignacion;
	}
	
	public void setFechaAsignacion(LocalDate fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	
//	@Override
//	public List<Incidencia> getIncidencias() {
//		return incidencias;
//	}
//	
//	public void setIncidencias(List<Incidencia> incidencias) {
//		this.incidencias = incidencias;
//	}
	
	
	public TipoEquipo getTipoEquipo() {
		return tipoEquipo;
	}
	
	public void setTipoEquipo(TipoEquipo tipoEquipo) {
		this.tipoEquipo = tipoEquipo;
	}
	
	@Override
	public String toString() {
		return "EquipoModel [numeroSerie=" + numeroSerie + ", fechaAdquisicion=" + fechaAdquisicion
				+ ", fechaAsignacion=" + fechaAsignacion  ; //+ ", incidencias=" + incidencias + "]";
	}

	

}
