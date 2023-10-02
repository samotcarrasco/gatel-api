package es.mdef.apigatel.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mdef.apigatel.entidades.IncidenciaConId;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.ReductorImagen;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.SolicitudAPI;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.ExtravioAPI;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.AveriaAPI;
import es.mdef.apigatel.entidades.ConfiguracionAPI;
import es.mde.acing.gatel.EquipoInformatico;
import es.mde.acing.gatel.EquipoInformaticoImpl;
import es.mde.acing.gatel.WebCam;
import es.mde.acing.gatel.Auriculares;
import es.mde.acing.gatel.Averia;
import es.mde.acing.gatel.Extravio;
import es.mde.acing.gatel.Incidencia;
import es.mde.acing.gatel.Configuracion;
import es.mde.acing.gatel.Equipo;
import es.mde.acing.gatel.Solicitud;

@Component
public class IncidenciaListaAssembler<T extends Incidencia> implements RepresentationModelAssembler<T, IncidenciaListaModel> {

	@Override
	public IncidenciaListaModel toModel(T entity) {
		IncidenciaListaModel model = new IncidenciaListaModel();

		model.setId(((IncidenciaConId) entity).getId());
		model.setCodigo(entity.getCodigo());
		model.setFechaAlta(entity.getFechaAlta());
		model.setFechaResolucion(entity.getFechaResolucion());
		model.setEstado(entity.getEstado());
		
		if (entity.getTipoIncidencia() == TipoIncidencia.AVERIA) {
			model.setTipoIncidencia(TipoIncidencia.AVERIA);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.EXTRAVIO) {
			model.setTipoIncidencia(TipoIncidencia.EXTRAVIO);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.CONFIGURACION) {
			model.setTipoIncidencia(TipoIncidencia.CONFIGURACION);
		} else if (entity.getTipoIncidencia() == TipoIncidencia.SOLICITUD) {
			model.setTipoIncidencia(TipoIncidencia.SOLICITUD);
		}

		model.add(linkTo(methodOn(IncidenciaController.class).one(((IncidenciaConId) entity).getId())).withSelfRel());
		model.add(linkTo(methodOn(PersonaController.class)
				.one(((PersonaConId) entity.getAgenteResolutor()).getId())).withRel("persona"));
		model.add(linkTo(methodOn(EquipoController.class)
				.one(((EquipoConId) entity.getEquipo()).getId())).withRel("equipo"));
		
		return model;
	}
	
	public CollectionModel<IncidenciaListaModel> toCollection(List<T> list) {
		CollectionModel<IncidenciaListaModel> collection = CollectionModel
				.of(list.stream().map(this::toModel).collect(Collectors.toList()));
		return collection;
	}

}
