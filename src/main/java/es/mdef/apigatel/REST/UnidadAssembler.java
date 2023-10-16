package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.apigatel.entidades.UnidadConId;

@Component
public class UnidadAssembler implements RepresentationModelAssembler<UnidadConId, UnidadModel> {

	@Override
	public UnidadModel toModel(UnidadConId entity) {
		UnidadModel model = new UnidadModel();

		model.setId(entity.getId());
		model.setNombre(entity.getNombre());
		model.setCodigoUnidad(entity.getCodigoUnidad());
		model.setCorreoOficial(entity.getCorreoOficial());
		model.setTelefono(entity.getTelefono());

		model.add(linkTo(methodOn(UnidadController.class).one(((UnidadConId) entity).getId())).withSelfRel());
		model.add(linkTo(methodOn(UnidadController.class).equiposDeUnidad(entity.getId()))
				.withRel("equiposDeUnidad"));
		model.add(linkTo(methodOn(UnidadController.class).miembros(entity.getId()))
				.withRel("miembros"));

		return model;
	}

}
