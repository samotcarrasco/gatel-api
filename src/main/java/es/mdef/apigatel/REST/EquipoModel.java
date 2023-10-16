package es.mdef.apigatel.REST;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.EquipoImpl.TipoEquipo;

@Relation(itemRelation = "equipo", collectionRelation = "equipos")
public class EquipoModel extends RepresentationModel<EquipoModel> {

	private Long id;
	private String numeroSerie;
	private LocalDate fechaAdquisicion;
	private LocalDate fechaAsignacion;
	private TipoEquipo tipoEquipo;
	private String modeloN;
	private String codigoPropietario;
	
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

	public String getModeloN() {
		return modeloN;
	}

	public void setModeloN(String modeloN) {
		this.modeloN = modeloN;
	}
	
	

	public String getCodigoPropietario() {
		return codigoPropietario;
	}

	public void setCodigoPropietario(String codigoPropietario) {
		this.codigoPropietario = codigoPropietario;
	}

	@Override
	public String toString() {
		return "EquipoModel [numeroSerie=" + numeroSerie + ", fechaAdquisicion=" + fechaAdquisicion
				+ ", fechaAsignacion=" + fechaAsignacion  ; //+ ", incidencias=" + incidencias + "]";
	}

	

}
