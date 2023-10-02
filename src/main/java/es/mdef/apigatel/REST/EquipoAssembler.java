package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.EquipoImpl.TipoEquipo;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.EquipoDeUnidadAPI;
import es.mdef.apigatel.entidades.EquipoPersonalAPI;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;

@Component
public class EquipoAssembler implements RepresentationModelAssembler<EquipoConId, EquipoModel> {

	@Override
	public EquipoModel toModel(EquipoConId entity) {
		EquipoModel model = new EquipoModel();

		model.setId(entity.getId());
		model.setNumeroSerie(entity.getNumeroSerie());
		model.setFechaAsignacion(entity.getFechaAsignacion());
		model.setFechaAdquisicion(entity.getFechaAdquisicion());
		model.setModeloN(entity.getModelo().getNombreModelo());
		

		if (entity.getTipoEquipo() == TipoEquipo.EQUIPO_UNIDAD && entity.getUnidad() != null) {
			model.setTipoEquipo(TipoEquipo.EQUIPO_UNIDAD);
			model.add(linkTo(
					methodOn(UnidadController.class).one(((UnidadConId) entity.getUnidad()).getId()))
					.withRel("unidad"));
		} else if (entity.getTipoEquipo() == TipoEquipo.EQUIPO_PERSONAL && entity.getPersona() != null) {
			model.setTipoEquipo(TipoEquipo.EQUIPO_PERSONAL);
			model.add(linkTo(
					methodOn(PersonaController.class).one(((PersonaConId) entity.getPersona()).getId()))
					.withRel("persona"));
		}
    		
		model.add(linkTo(methodOn(EquipoController.class).one(((EquipoConId) entity).getId())).withSelfRel());
		model.add(linkTo(methodOn(EquipoController.class).incidenciasDeEquipo(entity.getId()))
						.withRel("incidencias"));

		model.add(linkTo(methodOn(ModeloController.class)
					.one(((ModeloConId) entity.getModelo()).getId())).withRel("modelo"));
		return model;
	}

	public EquipoConId toEntity(EquipoPostModel model) {
		EquipoConId equipo = new EquipoConId();
		
		if (model.getTipoEquipo() ==  TipoEquipo.EQUIPO_UNIDAD) {
			EquipoDeUnidadAPI equipoDeUnidad = new EquipoDeUnidadAPI();
			equipoDeUnidad.setUnidad(model.getUnidad());
			equipo = equipoDeUnidad;			
		}
		else if(model.getTipoEquipo() == TipoEquipo.EQUIPO_PERSONAL) {
			EquipoPersonalAPI equipoPersonal = new EquipoPersonalAPI();
			equipoPersonal.setPersona(model.getPersona());
			equipo = equipoPersonal;
		}
		
		equipo.setNumeroSerie(model.getNumeroSerie());
		equipo.setFechaAdquisicion(model.getFechaAdquisicion());
		equipo.setFechaAsignacion(model.getFechaAsignacion());
		equipo.setModelo(model.getModelo());
	
		return equipo;
		
	}
}
