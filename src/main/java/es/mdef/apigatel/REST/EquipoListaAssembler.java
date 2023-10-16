package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.PersonaImpl.TipoPersona;
import es.mde.acing.gatel.Equipo;
import es.mde.acing.gatel.EquipoImpl.TipoEquipo;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.MiembroGCAPI;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;

@Component
public class EquipoListaAssembler<T extends Equipo> implements RepresentationModelAssembler<T, EquipoModel> {

	@Override
	public EquipoModel toModel(T entity) {
		EquipoModel model = new EquipoModel();

		model.setId(((EquipoConId) entity).getId());
		model.setNumeroSerie(entity.getNumeroSerie());
		model.setFechaAsignacion(entity.getFechaAsignacion());
		model.setFechaAdquisicion(entity.getFechaAdquisicion());
		model.setModeloN(entity.getModelo().getMarca() + " " + entity.getModelo().getNombreModelo());

		if (entity.getTipoEquipo() == TipoEquipo.EQUIPO_UNIDAD && entity.getUnidad() != null) {
			model.setTipoEquipo(TipoEquipo.EQUIPO_UNIDAD);
			model.setCodigoPropietario(entity.getUnidad().getCodigoUnidad());
			model.add(linkTo(methodOn(UnidadController.class).one(((UnidadConId) entity.getUnidad()).getId()))
					.withRel("unidad"));
		} else if (entity.getTipoEquipo() == TipoEquipo.EQUIPO_PERSONAL && entity.getPersona() != null) {
			if (entity.getPersona().getTipoPersona() == TipoPersona.MIEMBRO_GC) {
				model.setTipoEquipo(TipoEquipo.EQUIPO_PERSONAL);
				model.setCodigoPropietario(((MiembroGCAPI) entity.getPersona()).getTip());
			}
			model.add(linkTo(methodOn(PersonaController.class).one(((PersonaConId) entity.getPersona()).getId()))
					.withRel("persona"));
		}

		model.add(linkTo(methodOn(EquipoController.class).one(((EquipoConId) entity).getId())).withSelfRel());
		model.add(linkTo(methodOn(ModeloController.class).one(((ModeloConId) entity.getModelo()).getId()))
				.withRel("modelo"));
		model.add(linkTo(methodOn(EquipoController.class).incidenciasDeEquipo(((EquipoConId) entity).getId()))
				.withRel("incidencias"));

		return model;
	}

	public CollectionModel<EquipoModel> toCollection(List<T> list) {
		CollectionModel<EquipoModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
