package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.Persona;
import es.mde.acing.gatel.PersonaImpl.TipoPersona;
import es.mdef.apigatel.entidades.PersonalExternoAPI;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.entidades.MiembroGCAPI;
import es.mdef.apigatel.entidades.PersonaConId;

@Component
public class PersonaListaAssembler<T extends Persona> implements RepresentationModelAssembler<T, PersonaListaModel> {

	@Override
	public PersonaListaModel toModel(T entity) {
		PersonaListaModel model = new PersonaListaModel();

		model.setNombre(entity.getNombre());
		model.setApellidos(entity.getApellidos());
		model.setEmail(entity.getEmail());
		
		if (entity.getTipoPersona() == TipoPersona.MIEMBRO_GC) {
			model.setTipoPersona(TipoPersona.MIEMBRO_GC);
			model.setTip(((MiembroGCAPI) entity).getTip());
			model.add(linkTo(
					methodOn(UnidadController.class).one(((UnidadConId) entity.getUnidad()).getId()))
					.withRel("unidad"));
			
			model.add(linkTo(methodOn(PersonaController.class).equiposDePersona(((PersonaConId) entity).getId()))
							.withRel("equiposPersonales"));

		} else if (entity.getTipoPersona() == TipoPersona.PERSONAL_EXTERNO) {
			model.setTipoPersona(TipoPersona.PERSONAL_EXTERNO);
			model.setDni(((PersonalExternoAPI) entity).getDni());
		} 
		
		model.add(linkTo(methodOn(PersonaController.class).one(((PersonaConId) entity).getId())).withSelfRel());

	return model; 

	}

	public CollectionModel<PersonaListaModel> toCollection(List<T> list) {
		CollectionModel<PersonaListaModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
