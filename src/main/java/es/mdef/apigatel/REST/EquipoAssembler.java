package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.EquipoImpl.TipoEquipo;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.EquipoDeUnidadAPI;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mde.acing.gatel.EquipoInformatico;
import es.mde.acing.gatel.EquipoInformaticoImpl;
import es.mde.acing.gatel.WebCam;
import es.mde.acing.gatel.Auriculares;
import es.mde.acing.gatel.EquipoDeUnidad;

@Component
public class EquipoAssembler implements RepresentationModelAssembler<EquipoConId, EquipoModel> {

	@Override
	public EquipoModel toModel(EquipoConId entity) {
		EquipoModel model = new EquipoModel();

		model.setId(entity.getId());
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
			model.add(linkTo(methodOn(ModeloController.class)
					.one(((ModeloConId) entity.getModelo()).getId())).withRel("modelo"));
		return model;
	}

	public EquipoConId toEntity(EquipoPostModel model) {
		EquipoConId equipo = new EquipoConId();
		
		switch (model.getTipoEquipo()) {
		case EquipoDeUnidad:
			EquipoDeUnidadAPI equipoDeUnidad = new EquipoDeUnidadAPI();
			equipoDeUnidad.setUnidad(model.getUnidad());
			equipo = equipoDeUnidad;			
		break;
		case EquipoPersonal:
			
		break;
			
		}
		
		equipo.setNumeroSerie(model.getNumeroSerie());
		equipo.setFechaAdquisicion(model.getFechaAdquisicion());
		equipo.setFechaAsignacion(model.getFechaAsignacion());
		equipo.setModelo(model.getModelo());
	
		return equipo;
		

	}
}
