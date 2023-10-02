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
import es.mde.acing.gatel.Modelo;
import es.mde.acing.gatel.WebCam;
import es.mde.acing.gatel.ModeloImpl.TipoModelo;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.WebCamAPI;

@Component
public class ModeloListaAssembler<T extends Modelo> implements RepresentationModelAssembler<T, ModeloListaModel> {

	@Override
	public ModeloListaModel toModel(T entity) {
		ModeloListaModel model = new ModeloListaModel();

		model.setId(((ModeloConId) entity).getId());
		model.setMarca(((ModeloConId) entity).getMarca());
		model.setNombreModelo(((ModeloConId) entity).getNombreModelo());
		model.setCategoria(((ModeloConId) entity).getCategoria());
		model.setImgReducida(((ModeloConId) entity).getImgReducida());
		
		if (entity.getTipoModelo() == TipoModelo.EQUIPO_INFORMATICO) {
			model.setTipoModelo(TipoModelo.EQUIPO_INFORMATICO);
		} else if (entity.getTipoModelo() == TipoModelo.WEBCAM) {
			model.setTipoModelo(TipoModelo.WEBCAM);
		} else if (entity.getTipoModelo() == TipoModelo.AURICULARES) {
			model.setTipoModelo(TipoModelo.AURICULARES);
		}

		model.add(linkTo(methodOn(ModeloController.class).one(((ModeloConId) entity).getId())).withSelfRel());
		model.add(linkTo(methodOn(ModeloController.class).equipos(((ModeloConId) entity).getId()))
						.withRel("equipos"));

		return model;
	}

	public CollectionModel<ModeloListaModel> toCollection(List<T> list) {
		CollectionModel<ModeloListaModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
