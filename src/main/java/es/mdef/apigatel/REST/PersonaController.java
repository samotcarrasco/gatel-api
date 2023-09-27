package es.mdef.apigatel.REST;

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

import es.mde.acing.gatel.PersonaImpl.TipoPersona;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/personas")
public class PersonaController {
	private final PersonaRepositorio repositorio;
	private final PersonaAssembler assembler;
	//private final PersonaListaAssembler listaAssembler;
	private final EquipoListaAssembler equipoListaAssembler;

	private Logger log;

	PersonaController(PersonaRepositorio repositorio, PersonaAssembler assembler,
			// PersonaListaAssembler listaAssembler, 
			EquipoListaAssembler equipoListaAssembler
			) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		//this.listaAssembler = listaAssembler;
		this.equipoListaAssembler = equipoListaAssembler;
		log = ApiGatelApp.log;
	}

	@GetMapping("{id}")
	public PersonaModel one(@PathVariable Long id) {
		PersonaConId Persona = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Persona"));
		log.info("Recuperada " + Persona);
		return assembler.toModel(Persona);
	}

//	@GetMapping
//	public CollectionModel<PersonaListaModel> all() {
//		return listaAssembler.toCollection(repositorio.findAll());
//	}
	
	@GetMapping("{id}/equipos")
	public CollectionModel<EquipoModel> equiposDePersona(@PathVariable Long id) {
		PersonaConId persona = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "Persona"));
		return equipoListaAssembler.toCollection(persona.getEquiposPersonales());
	}

}