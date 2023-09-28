package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.Auriculares;
import es.mde.acing.gatel.EquipoInformatico;
import es.mde.acing.gatel.Unidad;
import es.mde.acing.gatel.WebCam;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.WebCamAPI;

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
		
		model.add(linkTo(methodOn(UnidadController.class).one(((UnidadConId) entity).getId())).withSelfRel(),
				linkTo(methodOn(UnidadController.class).equiposDeUnidad(((UnidadConId) entity).getId()))
						.withRel("equiposDeUnidad"));


		return model;
	}

	public CollectionModel<UnidadModel> toCollection(List<T> list) {
		CollectionModel<UnidadModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
