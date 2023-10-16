package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mdef.apigatel.entidades.IncidenciaConId;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mde.acing.gatel.Incidencia;

@Component
public class IncidenciaListaAssembler<T extends Incidencia> implements RepresentationModelAssembler<T, IncidenciaListaModel> {

	@Override
	public IncidenciaListaModel toModel(T entity) {
		IncidenciaListaModel model = new IncidenciaListaModel();

		model.setId(((IncidenciaConId) entity).getId());
		model.setCodigo(entity.getCodigo());
		model.setFechaAlta(entity.getFechaAlta());
		model.setFechaResolucion(entity.getFechaResolucion());
		model.setEstado(((IncidenciaConId) entity).getEstado());
		model.setEquipoN(entity.getEquipo().getModelo().getMarca() + " " + entity.getEquipo().getModelo().getNombreModelo() + 
				"-" +entity.getEquipo().getNumeroSerie());

		
		if (entity.getTipoIncidencia() == TipoIncidencia.AVERIA) {
			model.setTipoIncidencia(TipoIncidencia.AVERIA);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.EXTRAVIO) {
			model.setTipoIncidencia(TipoIncidencia.EXTRAVIO);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.CONFIGURACION) {
			model.setTipoIncidencia(TipoIncidencia.CONFIGURACION);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.SOLICITUD) {
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
	
	public CollectionModel<IncidenciaListaModel> toCollection(List<T> list) {
		CollectionModel<IncidenciaListaModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
