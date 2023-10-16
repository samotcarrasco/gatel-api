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
import org.springframework.web.bind.annotation.RestController;

import es.mde.acing.gatel.IncidenciaImpl.EstadoIncidencia;
import es.mdef.apigatel.entidades.ExtravioAPI;
import es.mdef.apigatel.entidades.SolicitudAPI;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.AveriaAPI;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.IncidenciaConId;

import es.mdef.apigatel.repositorios.EquipoRepositorio;
import es.mdef.apigatel.repositorios.IncidenciaRepositorio;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {
	private final IncidenciaRepositorio repositorio;
	private final IncidenciaAssembler assembler;
	private final IncidenciaListaAssembler<IncidenciaConId> listaAssembler;
	private final PersonaRepositorio perRepositorio;
	private final EquipoRepositorio eqRepositorio;
	private final Logger log;

	IncidenciaController(IncidenciaRepositorio repositorio, IncidenciaAssembler assembler,
			IncidenciaListaAssembler<IncidenciaConId> listaAssembler, PersonaRepositorio perRepositorio,
			EquipoRepositorio eqRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.perRepositorio = perRepositorio;
		this.eqRepositorio = eqRepositorio;
		log = ApiGatelApp.log;
	}

	@GetMapping("{id}")
	public IncidenciaModel one(@PathVariable Long id) {
		IncidenciaConId Incidencia = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "Incidencia"));
		return assembler.toModel(Incidencia);
	}

	@GetMapping
	public CollectionModel<IncidenciaListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@PostMapping
	public IncidenciaModel add(@Valid @RequestBody IncidenciaPostModel model) throws IOException {
		IncidenciaConId incidencia = repositorio.save(assembler.toEntity(model));
		return assembler.toModel(incidencia);
	}

	@PutMapping("{id}")
	public IncidenciaModel edit(@Valid @PathVariable Long id, @RequestBody IncidenciaPutModel model) {

		IncidenciaConId incidencia = repositorio.findById(id).map(inc -> {

			switch (model.getTipoIncidencia()) {
			case AVERIA:
				AveriaAPI averia = new AveriaAPI();
				averia.setComponente(model.getCompomente());
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
			case CONFIGURACION:
				//TODO
				break;
			default:
				//TODO
			}

			inc.setFechaResolucion(model.getFechaResolucion());
			inc.setEstado(model.getEstado());
			inc.setDescripcion(model.getDescripcion());
			inc.setAgenteResolutor(model.getAgenteResolutor());
			inc.setEquipo(model.getEquipo());

			return repositorio.save(inc);
		}).orElseThrow(() -> new RegisterNotFoundException(id, "incidencia"));

		return assembler.toModel(incidencia);
	}

	@Transactional
	@PatchMapping("/asignarIncidencia/{idIncidencia}")
	public IncidenciaModel asignarEquipoPersonal(@PathVariable Long idIncidencia,
			@RequestBody AsignarIncidenciaModel model) {

		PersonaConId agenteResolutor = perRepositorio.findById(model.getAgenteResolutor().getId())
				.orElseThrow(() -> new NoSuchElementException("No se encontró la persona"));

		IncidenciaConId incidencia = repositorio.findById(idIncidencia).map(inc -> {
			inc.setAgenteResolutor(agenteResolutor);
			inc.setEstado(EstadoIncidencia.ASIGNADA);

			return repositorio.save(inc);
		}).orElseThrow(() -> new RegisterNotFoundException(idIncidencia, "Incidencia"));

		return assembler.toModel(incidencia);
	}

	@Transactional
	@PatchMapping("/resolverIncidencia/{idIncidencia}")
	public IncidenciaModel resolverIncidencia(@PathVariable Long idIncidencia) {
		IncidenciaConId incidencia = repositorio.findById(idIncidencia).map(inc -> {
			inc.setEstado(EstadoIncidencia.RESUELTA);
			inc.setFechaResolucion(LocalDate.now());
			return repositorio.save(inc);
		}).orElseThrow(() -> new RegisterNotFoundException(idIncidencia, "Incidencia"));

		return assembler.toModel(incidencia);
	}

	@Transactional
	@PatchMapping("/cerrarIncidencia/{idIncidencia}")
	public IncidenciaModel cerrarIncidencia(@PathVariable Long idIncidencia) {
		IncidenciaConId incidencia = repositorio.findById(idIncidencia).map(inc -> {
			inc.setEstado(EstadoIncidencia.CERRADA);
			return repositorio.save(inc);
		}).orElseThrow(() -> new RegisterNotFoundException(idIncidencia, "Incidencia"));

		return assembler.toModel(incidencia);
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		IncidenciaConId incidencia = (IncidenciaConId) repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Incidencia"));
		log.info("Borrada incidencia con ID: " + incidencia.getId());
	}

}