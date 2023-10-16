package es.mdef.apigatel.REST;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.EquipoImpl.TipoEquipo;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;

@Relation(itemRelation = "equipo")
public class EquipoPostModel extends RepresentationModel<EquipoPostModel> {

	private String numeroSerie;
	private LocalDate fechaAdquisicion;
	private LocalDate fechaAsignacion;
	private TipoEquipo tipoEquipo;
	private UnidadConId unidad;
	private PersonaConId Persona;
	private ModeloConId modelo;

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

	public TipoEquipo getTipoEquipo() {
		return tipoEquipo;
	}

	public void setTipoEquipo(TipoEquipo tipoEquipo) {
		this.tipoEquipo = tipoEquipo;
	}

	public UnidadConId getUnidad() {
		return unidad;
	}

	public void setUnidad(UnidadConId unidad) {
		this.unidad = unidad;
	}

	public PersonaConId getPersona() {
		return Persona;
	}

	public void setPersona(PersonaConId persona) {
		Persona = persona;
	}

	public ModeloConId getModelo() {
		return modelo;
	}

	public void setModelo(ModeloConId modelo) {
		this.modelo = modelo;
	}

	@Override
	public String toString() {
		return "EquipoModel [numeroSerie=" + numeroSerie + ", fechaAdquisicion=" + fechaAdquisicion
				+ ", fechaAsignacion=" + fechaAsignacion; // + ", incidencias=" + incidencias + "]";
	}

}
