package es.mdef.apigatel.REST;

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

import es.mde.acing.gatel.EquipoImpl.TipoEquipo;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.EquipoPersonalAPI;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.EquipoDeUnidadAPI;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.repositorios.EquipoRepositorio;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/equipos")
public class EquipoController {
	private final EquipoRepositorio repositorio;
	private final EquipoAssembler assembler;
	private final EquipoListaAssembler listaAssembler;
	private final PersonaRepositorio perRepositorio;

	private Logger log;

	EquipoController(EquipoRepositorio repositorio, EquipoAssembler assembler,
			EquipoListaAssembler listaAssembler, PersonaRepositorio perRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.perRepositorio = perRepositorio;
		log = ApiGatelApp.log;
	}

	@GetMapping("{id}")
	public EquipoModel one(@PathVariable Long id) {
		EquipoConId Equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
		log.info("Recuperada " + Equipo);
		return assembler.toModel(Equipo);
	}

	@GetMapping
	public CollectionModel<EquipoModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@PostMapping
	public EquipoModel add(@Valid @RequestBody EquipoPostModel model) {
		EquipoConId equipo = repositorio.save(assembler.toEntity(model));
		return assembler.toModel(equipo);
	}

	@PutMapping("{id}")
	public EquipoModel edit(@Valid @PathVariable Long id, @RequestBody EquipoPostModel model) {
		int n_regs = 0;
		if (model.getTipoEquipo() == TipoEquipo.EquipoDeUnidad) {
				EquipoDeUnidadAPI equipoUnidad = new EquipoDeUnidadAPI();
				repositorio.actualizarEquipoDeUnidad(model.getNumeroSerie(), model.getFechaAdquisicion(), model.getFechaAsignacion(), 
					 model.getModelo().getId(), 
					 model.getUnidad().getId(),
					 id);
		} else if (model.getTipoEquipo() == TipoEquipo.EquipoPersonal) {
			EquipoPersonalAPI equipoPersonal = new EquipoPersonalAPI();
			repositorio.actualizarEquipoPersonal(model.getNumeroSerie(), model.getFechaAdquisicion(), model.getFechaAsignacion(), 
				 model.getModelo().getId(), 
				 model.getPersona().getId(),
				 id);
		}	

		EquipoConId equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "equipo"));

		log.info("Actualizado " + equipo);
		return assembler.toModel(equipo);
	}
	


	@PatchMapping("/asignarEquipoPersonal")
	public EquipoModel asignarEquipoPersonal(@RequestBody AsignarEquipoPersonalModel model) {
		
		PersonaConId persona = perRepositorio.findById(model.getPersona().getId())
			    .orElseThrow(() -> new NoSuchElementException("No se encontró la persona"));
		
		EquipoConId equipo = repositorio.findById(model.getEquipo().getId()).map(equi -> {
			equi.setFechaAsignacion(LocalDate.now());
			equi.setPersona(persona);
			repositorio.actualizarEquipoPersonalAsignacion(model.getEquipo().getId());
			return repositorio.save(equi);
		}).orElseThrow(() -> new RegisterNotFoundException(model.getEquipo().getId(),"Equipo"));
			
		return assembler.toModel(equipo);
	}

	
	@PatchMapping("/asignarEquipoPersonal2/{idEquipo}/{idPersona}")
	public EquipoModel asignarEquipoPersonal2(@PathVariable Long idEquipo, @PathVariable Long idPersona) {
	
	PersonaConId persona = perRepositorio.findById(idPersona)
		    .orElseThrow(() -> new NoSuchElementException("No se encontró la persona"));
	
	EquipoConId equipo = repositorio.findById(idEquipo).map(equi -> {
		equi.setFechaAsignacion(LocalDate.now());
		equi.setPersona(persona);
		repositorio.actualizarEquipoPersonalAsignacion(idEquipo);
		return repositorio.save(equi);
	}).orElseThrow(() -> new RegisterNotFoundException(idEquipo,"Equipo"));
		
	return assembler.toModel(equipo);
	}


	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		EquipoConId Equipo = (EquipoConId) repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
	}

}