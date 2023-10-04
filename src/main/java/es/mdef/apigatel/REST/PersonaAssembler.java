package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.PersonaImpl.TipoPersona;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.MiembroGCAPI;
import es.mdef.apigatel.entidades.PersonalExternoAPI;
import es.mdef.apigatel.entidades.UnidadConId;

@Component
public class PersonaAssembler implements RepresentationModelAssembler<PersonaConId, PersonaModel> {

	@Override
	public PersonaModel toModel(PersonaConId entity) {
		PersonaModel model = new PersonaModel();

		model.setId(entity.getId());
		model.setNombre(entity.getNombre());
		model.setApellidos(entity.getApellidos());
		model.setEmail(entity.getEmail());
		model.setTelefono(entity.getTelefono());
		model.setNombreUsuario(entity.getNombreUsuario());
		model.setPassword(entity.getPassword());
		model.setPerfil(entity.getPerfil());
		
		if (entity.getTipoPersona() == TipoPersona.MIEMBRO_GC) {
			model.setTipoPersona(TipoPersona.MIEMBRO_GC);
			model.setTip(((MiembroGCAPI) entity).getTip());
			model.setEmpleo(((MiembroGCAPI) entity).getEmpleo());
			model.add(linkTo(
					methodOn(UnidadController.class).one(((UnidadConId) entity.getUnidad()).getId()))
					.withRel("unidad"));
			
			model.add(linkTo(methodOn(PersonaController.class).equiposDePersona(entity.getId()))
							.withRel("equiposPersonales"));

		} else if (entity.getTipoPersona() == TipoPersona.PERSONAL_EXTERNO) {
			model.setTipoPersona(TipoPersona.PERSONAL_EXTERNO);
			model.setDni(((PersonalExternoAPI) entity).getDni());
			model.setEmpresa(((PersonalExternoAPI) entity).getDni());
		} 
		
		model.add(linkTo(methodOn(PersonaController.class).one(((PersonaConId) entity).getId())).withSelfRel());

		return model;
	}

}
