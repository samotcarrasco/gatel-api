package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mde.acing.gatel.IncidenciaImpl.EstadoIncidencia;
import es.mdef.apigatel.entidades.IncidenciaConId;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.SolicitudAPI;
import es.mdef.apigatel.entidades.ExtravioAPI;
import es.mdef.apigatel.entidades.AveriaAPI;
import es.mdef.apigatel.entidades.ConfiguracionAPI;
import es.mde.acing.gatel.Averia;
import es.mde.acing.gatel.Extravio;


@Component
public class IncidenciaAssembler implements RepresentationModelAssembler<IncidenciaConId, IncidenciaModel> {

	@Override
	public IncidenciaModel toModel(IncidenciaConId entity) {
		IncidenciaModel model = new IncidenciaModel();

		model.setId(entity.getId());
		model.setCodigo(entity.getCodigo());
		model.setFechaAlta(entity.getFechaAlta());
		model.setFechaResolucion(entity.getFechaResolucion());
		model.setEstado(entity.getEstado());
		model.setDescripcion(entity.getDescripcion());
		model.setEquipoN(entity.getEquipo().getModelo().getMarca() + " " + entity.getEquipo().getModelo().getNombreModelo() + 
				"-" +entity.getEquipo().getNumeroSerie());

		
		if (entity.getTipoIncidencia() == TipoIncidencia.AVERIA) {
			model.setComponente(((Averia) entity).getComponente());
			model.setReparable(((Averia) entity).isReparable());
			model.setTipoIncidencia(TipoIncidencia.AVERIA);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.EXTRAVIO) {
			model.setUltimaUbicacion(((Extravio) entity).getUltimaUbicacion());
			model.setBloqueado(((Extravio) entity).isBloqueado());
			model.setBorrado(((Extravio) entity).isBorrado());
			model.setEncontrado(((Extravio) entity).isEncontrado());
			model.setTipoIncidencia(TipoIncidencia.EXTRAVIO);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.CONFIGURACION) {
			model.setAplicacion(((ConfiguracionAPI) entity).getAplicacion());
			model.setTipoIncidencia(TipoIncidencia.CONFIGURACION);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.SOLICITUD) {
			model.setAceptado(((SolicitudAPI) entity).isAceptado());
			model.setTipoIncidencia(TipoIncidencia.SOLICITUD);
		}

		model.add(linkTo(methodOn(IncidenciaController.class).one(((IncidenciaConId) entity).getId())).withSelfRel());
		
		if (entity.getAgenteResolutor() != null) {
		model.add(linkTo(methodOn(PersonaController.class)
				.one(((PersonaConId) entity.getAgenteResolutor()).getId())).withRel("agenteResolutor"));
		}
		model.add(linkTo(methodOn(EquipoController.class)
				.one(((EquipoConId) entity.getEquipo()).getId())).withRel("equipo"));
		
		return model;
	}

	public IncidenciaConId toEntity(IncidenciaPostModel model) throws IOException {
		IncidenciaConId incidencia = new IncidenciaConId();
		
		Date fechaActual = new Date();
		// segundos desde 1970
		long segundosDesdeEpoch = fechaActual.getTime() / 1000;
		
		switch (model.getTipoIncidencia()) {
		case AVERIA:
			AveriaAPI averia = new AveriaAPI();
			averia.setComponente(model.getComponente());
			//averia.setReparable(model.getReparable());
			incidencia = averia;  
			incidencia.setCodigo("AV-" + model.getEquipo().getId() + "-" + segundosDesdeEpoch);
			break;
		case EXTRAVIO:
			ExtravioAPI extravio = new ExtravioAPI();
			//incidencia.setCodigo("EX-" + model.getEquipo().getId() + "-" +);
			extravio.setUltimaUbicacion(model.getUltimaUbicacion());
//			extravio.setBloqueado(model.isBloqueado());
//			extravio.setBorrado(model.isBorrado());			
//			extravio.setEncontrado(model.isEncontrado());
			incidencia = extravio;
			incidencia.setCodigo("EX-" + model.getEquipo().getId() + "-" + segundosDesdeEpoch);
			break;
		case SOLICITUD:
			SolicitudAPI solicitud = new SolicitudAPI();
			solicitud.setAceptado(false);
			incidencia = solicitud;
			incidencia.setCodigo("SOL-" + model.getEquipo().getId() + "-" + segundosDesdeEpoch);
			break;
		case CONFIGURACION:
			ConfiguracionAPI configuracion = new ConfiguracionAPI();
			configuracion.setAplicacion(model.getAplicacion());
			incidencia = configuracion;
			incidencia.setCodigo("CONF-" + model.getEquipo().getId() + "-" + segundosDesdeEpoch);
			break;
	}

		incidencia.setFechaAlta(LocalDate.now());
		//incidencia.setFechaResolucion(model.getFechaResolucion());
		incidencia.setEstado(EstadoIncidencia.NUEVA);
		incidencia.setDescripcion(model.getDescripcion());
		//incidencia.setAgenteResolutor(model.getAgenteResolutor());
		incidencia.setEquipo(model.getEquipo());
		
		return incidencia;
	}
}
