package es.mdef.apigatel.REST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mde.acing.gatel.EquipoPersonal;
import es.mde.acing.gatel.Incidencia;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.IncidenciaConId;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/personas")
public class PersonaController {
	private final PersonaRepositorio repositorio;
	private final PersonaAssembler assembler;
	private final PersonaListaAssembler<PersonaConId> listaAssembler;
	private final IncidenciaListaAssembler<IncidenciaConId> incListaAssembler;
	private final EquipoListaAssembler<EquipoConId> equipoListaAssembler;

	PersonaController(PersonaRepositorio repositorio, PersonaAssembler assembler,
			PersonaListaAssembler<PersonaConId> listaAssembler, EquipoListaAssembler<EquipoConId> equipoListaAssembler,
			IncidenciaListaAssembler<IncidenciaConId> incListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.equipoListaAssembler = equipoListaAssembler;
		this.incListaAssembler = incListaAssembler;
	}

	@GetMapping("{id}")
	public PersonaModel one(@PathVariable Long id) {
		PersonaConId persona = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Persona"));
		return assembler.toModel(persona);
	}
	
	@GetMapping("tip/{tip}")
	public PersonaModel oneByTip(@PathVariable String tip) {
		PersonaConId persona = repositorio.buscaPorTip(tip);
		
		if(persona == null) {
			throw new RegisterNotFoundException(tip, "TIP");
		}
		
		return assembler.toModel(persona);
	}

	@GetMapping
	public CollectionModel<PersonaListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@GetMapping("{id}/equipos")
	public CollectionModel<EquipoModel> equiposDePersona(@PathVariable Long id) {
		PersonaConId persona = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "Persona"));
		return equipoListaAssembler.toCollection((List<EquipoConId>)(List<?>)persona.getEquiposPersonales());
	}
	
	@GetMapping("{id}/incidencias")
	public CollectionModel<IncidenciaListaModel> incidenciasDePersona(@PathVariable Long id) {
	    PersonaConId persona = repositorio.findById(id)
	            .orElseThrow(() -> new RegisterNotFoundException(id, "Persona"));

	    List<Incidencia> incidencias = new ArrayList<>();

	    for (EquipoPersonal equipo : persona.getEquiposPersonales()) {
	        for (Incidencia incidencia : ((EquipoConId) equipo).getIncidencias()) {
	            incidencias.add((Incidencia) incidencia);
	        }
	    }
	    return incListaAssembler.toCollection((List<IncidenciaConId>)(List<?>)incidencias);
	}

}