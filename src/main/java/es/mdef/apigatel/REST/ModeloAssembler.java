package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.ModeloImpl.TipoModelo;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mde.acing.gatel.EquipoInformatico;
import es.mde.acing.gatel.EquipoInformaticoImpl;
import es.mde.acing.gatel.WebCam;
import es.mde.acing.gatel.Auriculares;

@Component
public class ModeloAssembler implements RepresentationModelAssembler<ModeloConId, ModeloModel> {

	@Override
	public ModeloModel toModel(ModeloConId entity) {
		ModeloModel model = new ModeloModel();

		model.setId(entity.getId());
		model.setCategoria(entity.getCategoria());
		model.setImagen(entity.getImagen());
		model.setImgReducida(entity.getImgReducida());
		model.setDetalles(entity.getDetalles());
		model.setMarca(entity.getMarca());
		model.setNombreModelo(entity.getNombreModelo());

		if (entity.getTipoModelo() == TipoModelo.EquipoInformatico) {
			model.setPulgadas(((EquipoInformatico) entity).getPulgadas());
			model.setDiscoDuro(((EquipoInformatico) entity).getDiscoDuro());
			model.setSistemaOperativo(((EquipoInformatico) entity).getSistemaOperativo());
			model.setMemoria(((EquipoInformatico) entity).getMemoria());
			model.setTipoEquipoInformatico(((EquipoInformatico) entity).getTipoEquipoInformatico());
			model.setTipoModelo(TipoModelo.EquipoInformatico);
		} else if (entity.getTipoModelo() == TipoModelo.WebCam) {
			model.setResolucion(((WebCamAPI) entity).getResolucion());
			model.setTipoModelo(TipoModelo.WebCam);
		} else if (entity.getTipoModelo() == TipoModelo.Auriculares) {
			model.setStereo(((AuricularesAPI) entity).isStereo());
			model.setConexion(((AuricularesAPI) entity).getConexion());
			model.setTipoModelo(TipoModelo.Auriculares);
		}

//		model.add(linkTo(methodOn(TipoEquipoInformaticoController.class).one(((TipoEquipoInformaticoConId) entity.getTipoEquipoInf()).getId()))
//				.withRel("tipoEquipoInf"));
//		model.add(linkTo(
//				methodOn(DepartamentoController.class).one(((DepartamentoConId) entity.getDeptoOferta()).getId()))
//				.withRel("dptoOferta"));
//		if (entity.getDptoAdquisicion() != null) {
//			model.add(linkTo(methodOn(DepartamentoController.class)
//					.one(((DepartamentoConId) entity.getDptoAdquisicion()).getId())).withRel("dptoAdquisicion"));
//		}

		return model;
	}

	public ModeloConId toEntity(ModeloPostModel model) {
		ModeloConId modelo = new ModeloConId();

		System.out.println("tipoequipoinformatico::::lllllllllllllllllllllllllll::::::::::::::::::::::" + model.getTipoEquipoInformatico());

		switch (model.getTipoModelo()) {
		case EquipoInformatico:
			EquipoInformaticoAPI equipo = new EquipoInformaticoAPI();
			equipo.setPulgadas(model.getPulgadas());
			equipo.setMemoria(model.getMemoria());
			equipo.setDiscoDuro(model.getDiscoDuro());
			equipo.setSistemaOperativo(model.getSistemaOperativo());
			equipo.setTipoEquipoInformatico(model.getTipoEquipoInformatico());
			System.out.println("tipoequipoinformatico::::::::::::::::::::::::::" + model.getTipoEquipoInformatico());
			System.out.println("tipoequipoinformatico::::::::::::::::::::::::::" + model.getPulgadas());
			modelo = equipo;  
			break;
		case WebCam:
			WebCamAPI webCam = new WebCamAPI();
			webCam.setResolucion(model.getResolucion());
			modelo = webCam;
			break;
		case Auriculares:
			AuricularesAPI auriculares = new AuricularesAPI();
			auriculares.setConexion(model.getConexion());
			auriculares.setStereo(model.isStereo());
			modelo = auriculares;
			break;
		}

		modelo.setImagen(model.getImagen());
		modelo.setImgReducida(model.getImgReducida());
		modelo.setCategoria(model.getCategoria());
		modelo.setMarca(model.getMarca());
		modelo.setNombreModelo(model.getNombreModelo());
		modelo.setDetalles(model.getDetalles());
		
		return modelo;
	}
}
