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
		
		if (entity.getTipoModelo() == TipoModelo.EquipoInformatico) {
			model.setPulgadas(((EquipoInformaticoAPI) entity).getPulgadas());
			model.setDiscoDuro(((EquipoInformaticoAPI) entity).getDiscoDuro());
			model.setSistemaOperativo(((EquipoInformaticoAPI) entity).getSistemaOperativo());
			model.setMemoria(((EquipoInformaticoAPI) entity).getMemoria());
			model.setTipoModelo(TipoModelo.EquipoInformatico);
		} else if (entity.getTipoModelo() == TipoModelo.WebCam) {
			model.setResolucion(((WebCamAPI) entity).getResolucion());
			model.setTipoModelo(TipoModelo.WebCam);
		} else if (entity.getTipoModelo() == TipoModelo.Auriculares) {
			model.setStereo(((AuricularesAPI) entity).isStereo());
			model.setConexion(((AuricularesAPI) entity).getConexion());
			model.setTipoModelo(TipoModelo.Auriculares);
		}

//		String nombreDptoOferta = entity.getDptoAdquisicion() != null
//				? ((DepartamentoConId) entity.getDptoAdquisicion()).getAbreviatura()
//				: "-";

//		model.add(linkTo(methodOn(CategoriaController.class).one(((CategoriaConId) entity.getCategoria()).getId()))
//				.withRel("categoria"));
//		model.add(linkTo(
//				methodOn(DepartamentoController.class).one(((DepartamentoConId) entity.getDeptoOferta()).getId()))
//				.withRel("dptoOferta"));
//		if (entity.getDptoAdquisicion() != null) {
//			model.add(linkTo(methodOn(DepartamentoController.class)
//					.one(((DepartamentoConId) entity.getDptoAdquisicion()).getId())).withRel("dptoAdquisicion"));
//		}
		return model;
	}

	public CollectionModel<ModeloListaModel> toCollection(List<T> list) {
		CollectionModel<ModeloListaModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
