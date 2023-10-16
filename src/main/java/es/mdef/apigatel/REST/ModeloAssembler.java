package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.ModeloImpl.TipoModelo;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.entidades.AuricularesAPI;

@Component
public class ModeloAssembler implements RepresentationModelAssembler<ModeloConId, ModeloModel> {

	@Override
	public ModeloModel toModel(ModeloConId entity) {
		ModeloModel model = new ModeloModel();

		model.setId(entity.getId());
		model.setImagen(entity.getImagen());
		model.setImgReducida(entity.getImgReducida());
		model.setDetalles(entity.getDetalles());
		model.setMarca(entity.getMarca());
		model.setNombreModelo(entity.getNombreModelo());
		model.setStock(entity.getStock());

		if (entity.getTipoModelo() == TipoModelo.EQUIPO_INFORMATICO) {
			model.setPulgadas(((EquipoInformaticoAPI) entity).getPulgadas());
			model.setDiscoDuro(((EquipoInformaticoAPI) entity).getDiscoDuro());
			model.setSistemaOperativo(((EquipoInformaticoAPI) entity).getSistemaOperativo());
			model.setMemoria(((EquipoInformaticoAPI) entity).getMemoria());
			model.setTipoEquipoInformatico(((EquipoInformaticoAPI) entity).getTipoEquipoInformatico());
			model.setTipoModelo(TipoModelo.EQUIPO_INFORMATICO);
		} else if (entity.getTipoModelo() == TipoModelo.WEBCAM) {
			model.setResolucion(((WebCamAPI) entity).getResolucion());
			model.setTipoModelo(TipoModelo.WEBCAM);
		} else if (entity.getTipoModelo() == TipoModelo.AURICULARES) {
			model.setStereo(((AuricularesAPI) entity).isStereo());
			model.setConexion(((AuricularesAPI) entity).getConexion());
			model.setTipoModelo(TipoModelo.AURICULARES);
		}

		model.add(linkTo(methodOn(ModeloController.class).one(((ModeloConId) entity).getId())).withSelfRel());
		model.add(linkTo(methodOn(ModeloController.class).equipos(entity.getId())).withRel("equipos"));
		

		return model;
	}

	public ModeloConId toEntity(ModeloPostModel model) throws IOException {
		ModeloConId modelo = new ModeloConId();

		switch (model.getTipoModelo()) {
		case EQUIPO_INFORMATICO:
			EquipoInformaticoAPI equipo = new EquipoInformaticoAPI();
			equipo.setPulgadas(model.getPulgadas());
			equipo.setMemoria(model.getMemoria());
			equipo.setDiscoDuro(model.getDiscoDuro());
			equipo.setSistemaOperativo(model.getSistemaOperativo());
			equipo.setTipoEquipoInformatico(model.getTipoEquipoInformatico());
			modelo = equipo;
			break;
		case WEBCAM:
			WebCamAPI webCam = new WebCamAPI();
			webCam.setResolucion(model.getResolucion());
			modelo = webCam;
			break;
		case AURICULARES:
			AuricularesAPI auriculares = new AuricularesAPI();
			auriculares.setConexion(model.getConexion());
			auriculares.setStereo(model.isStereo());
			modelo = auriculares;
			break;
		}

		modelo.setImagen(model.getImagen());
		//modelo.setImgReducida(ReductorImagen.reducirImagen(model.getImagen(), 150, 150));
		modelo.setImgReducida(model.getImgReducida());
		modelo.setMarca(model.getMarca());
		modelo.setNombreModelo(model.getNombreModelo());
		modelo.setDetalles(model.getDetalles());

		return modelo;
	}
}
