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
import es.mde.acing.gatel.Equipo;
import es.mde.acing.gatel.WebCam;
import es.mde.acing.gatel.EquipoImpl.TipoEquipo;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.WebCamAPI;

@Component
public class EquipoListaAssembler<T extends Equipo> implements RepresentationModelAssembler<T, EquipoModel> {

	@Override
	public EquipoModel toModel(T entity) {
		EquipoModel model = new EquipoModel();

		model.setId(((EquipoConId) entity).getId());
		model.setNumeroSerie(entity.getNumeroSerie());
		model.setFechaAsignacion(entity.getFechaAsignacion());
		model.setFechaAdquisicion(entity.getFechaAdquisicion());

		if (entity.getTipoEquipo() == TipoEquipo.EquipoDeUnidad) {
			model.add(linkTo(
					methodOn(UnidadController.class).one(((UnidadConId) entity.getUnidad()).getId()))
					.withRel("unidad"));
		} //else if (entity.getTipoEquipo() == TipoEquipo.EquipoPersonal) {
		//}
//		model.add(linkTo(methodOn(TipoEquipoInformaticoController.class).one(((TipoEquipoInformaticoConId) entity.getTipoEquipoInf()).getId()))
//				.withRel("tipoEquipoInf"));
//	
//		if (entity.getDptoAdquisicion() != null) {
//			model.add(linkTo(methodOn(DepartamentoController.class)
//					.one(((DepartamentoConId) entity.getDptoAdquisicion()).getId())).withRel("dptoAdquisicion"));
//		}


		return model;
	}

	public CollectionModel<EquipoModel> toCollection(List<T> list) {
		CollectionModel<EquipoModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
