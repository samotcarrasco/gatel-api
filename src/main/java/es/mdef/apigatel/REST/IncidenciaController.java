package es.mdef.apigatel.REST;

import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.ExtravioAPI;
import es.mdef.apigatel.entidades.SolicitudAPI;
import es.mdef.apigatel.entidades.AveriaAPI;
import es.mdef.apigatel.entidades.ConfiguracionAPI;

import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.IncidenciaConId;

import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.repositorios.EquipoRepositorio;
import es.mdef.apigatel.repositorios.IncidenciaRepositorio;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {
	private final IncidenciaRepositorio repositorio;
	private final IncidenciaAssembler assembler;
	private final IncidenciaListaAssembler listaAssembler;
	private final PersonaRepositorio perRepositorio;
	private final EquipoRepositorio eqRepositorio;

	IncidenciaController(IncidenciaRepositorio repositorio, IncidenciaAssembler assembler,
			IncidenciaListaAssembler listaAssembler, PersonaRepositorio perRepositorio,
			EquipoRepositorio eqRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.perRepositorio = perRepositorio;
		this.eqRepositorio = eqRepositorio;
	}

	@GetMapping("{id}")
	public IncidenciaModel one(@PathVariable Long id) {
		IncidenciaConId Incidencia = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Incidencia"));
		return assembler.toModel(Incidencia);
	}

	@GetMapping
	public CollectionModel<IncidenciaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@PostMapping
	public IncidenciaModel add(@Valid @RequestBody IncidenciaPostModel model) throws IOException {
		IncidenciaConId incidencia = repositorio.save(assembler.toEntity(model));
		return assembler.toModel(incidencia);
	}

	@PutMapping("{id}")
	public IncidenciaModel edit(@Valid @PathVariable Long id, @RequestBody IncidenciaPostModel model) {
		
		IncidenciaConId incidencia = repositorio.findById(id).map(inc -> {
			
			switch (model.getTipoIncidencia()) {
			case AVERIA:
				AveriaAPI averia = new AveriaAPI();
				averia.setCompomente(model.getCompomente());
				averia.setReparable(model.getReparable());
				inc = averia;  
				break;
			case EXTRAVIO:
				ExtravioAPI extravio = new ExtravioAPI();
				extravio.setUltimaUbicacion(model.getUltimaUbicacion());
				extravio.setBloqueado(model.isBloqueado());
				extravio.setBorrado(model.isBorrado());
				extravio.setEncontrado(model.isEncontrado());
				inc = extravio;
				break;
			case SOLICITUD:
				SolicitudAPI solicitud = new SolicitudAPI();
				solicitud.setAceptado(model.isAceptado());
				inc = solicitud;
				break;
			}
			
			inc.setFechaResolucion(model.getFechaResolucion());
			inc.setEstado(model.getEstado());
			inc.setDescripcion(model.getDescripcion());
			inc.setAgenteResolutor(model.getAgenteResolutor());
			inc.setEquipo(model.getEquipo());
			
			return repositorio.save(inc);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "incidencia"));

		
		//IncidenciaConId incidencia = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Incidencia"));

		return assembler.toModel(incidencia);
	}
	


//	@Transactional
//	@PatchMapping("/asignarIncidenciaPersonal")
//	public IncidenciaModel asignarIncidenciaPersonal(@RequestBody AsignarIncidenciaPersonalModel model) {
//		
//		PersonaConId persona = perRepositorio.findById(model.getPersona().getId())
//			    .orElseThrow(() -> new NoSuchElementException("No se encontró la persona"));
//		
//		IncidenciaConId Incidencia = repositorio.findById(model.getIncidencia().getId()).map(equi -> {
//			repositorio.actualizarIncidenciaPersonalAsignacion(model.getIncidencia().getId());
//			equi.setFechaAsignacion(LocalDate.now());
//			equi.setPersona(persona);
//			//equi.setUnidad(null);
//			return repositorio.save(equi);
//		}).orElseThrow(() -> new RegisterNotFoundException(model.getIncidencia().getId(),"Incidencia"));
//		
//		//repositorio.actualizarIncidenciaPersonalAsignacion(model.getIncidencia().getId());
//		
//		IncidenciaConId IncidenciaFinal = repositorio.findById(model.getIncidencia().getId())
//		        .orElseThrow(() -> new RegisterNotFoundException(model.getIncidencia().getId(), "Incidencia"));
//		
//		return assembler.toModel(IncidenciaFinal);
//	}
//
//	
//	@PatchMapping("/asignarIncidenciaPersonal2/{idIncidencia}/{idPersona}")
//	public IncidenciaModel asignarIncidenciaPersonal2(@PathVariable Long idIncidencia, @PathVariable Long idPersona) {
//	
//	PersonaConId persona = perRepositorio.findById(idPersona)
//		    .orElseThrow(() -> new NoSuchElementException("No se encontró la persona"));
//	
//	IncidenciaConId Incidencia = repositorio.findById(idIncidencia).map(equi -> {
//		equi.setFechaAsignacion(LocalDate.now());
//		equi.setPersona(persona);
//		repositorio.actualizarIncidenciaPersonalAsignacion(idIncidencia);
//		return repositorio.save(equi);
//	}).orElseThrow(() -> new RegisterNotFoundException(idIncidencia,"Incidencia"));
//		
//	return assembler.toModel(Incidencia);
//	}


	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		IncidenciaConId Incidencia = (IncidenciaConId) repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Incidencia"));
	}

}