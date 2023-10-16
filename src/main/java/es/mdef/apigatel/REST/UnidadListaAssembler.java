package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.Unidad;
import es.mdef.apigatel.entidades.UnidadConId;

@Component
public class UnidadListaAssembler<T extends Unidad> implements RepresentationModelAssembler<T, UnidadModel> {

	@Override
	public UnidadModel toModel(T entity) {
		UnidadModel model = new UnidadModel();

		model.setId(((UnidadConId) entity).getId());
		model.setNombre(entity.getNombre());
		model.setCodigoUnidad(entity.getCodigoUnidad());
		model.setCorreoOficial(entity.getCorreoOficial());
		model.setTelefono(entity.getTelefono());
		
		model.add(linkTo(methodOn(UnidadController.class).one(((UnidadConId) entity).getId())).withSelfRel());
		model.add(linkTo(methodOn(UnidadController.class).equiposDeUnidad(((UnidadConId) entity).getId()))
				.withRel("equiposDeUnidad"));
		model.add(linkTo(methodOn(UnidadController.class).miembros(((UnidadConId) entity).getId()))
				.withRel("miembrosGC"));


		return model;
	}

	public CollectionModel<UnidadModel> toCollection(List<T> list) {
		CollectionModel<UnidadModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
