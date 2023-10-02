package es.mdef.apigatel.REST;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mde.acing.gatel.EquipoDeUnidad;
import es.mde.acing.gatel.EquipoPersonal;
import es.mde.acing.gatel.Persona;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.repositorios.UnidadRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/unidades")
public class UnidadController {
	private final UnidadRepositorio repositorio;
	private final UnidadAssembler assembler;
	private final UnidadListaAssembler listaAssembler;
	private final IncidenciaListaAssembler incListaAssembler;
	private final EquipoListaAssembler equipoListaAssembler;
	private final PersonaListaAssembler perListaAssembler;

	UnidadController(UnidadRepositorio repositorio, UnidadAssembler assembler,
			UnidadListaAssembler listaAssembler, EquipoListaAssembler equipoListaAssembler,
			IncidenciaListaAssembler incListaAssembler, PersonaListaAssembler perListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.equipoListaAssembler = equipoListaAssembler;
		this.incListaAssembler = incListaAssembler;
		this.perListaAssembler = perListaAssembler;
	}

	@GetMapping("{id}")
	public UnidadModel one(@PathVariable Long id) {
		UnidadConId Unidad = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Unidad"));
		return assembler.toModel(Unidad);
	}
	
	@GetMapping("{id}/equipos")
	public CollectionModel<EquipoModel> equiposDeUnidad(@PathVariable Long id) {
		UnidadConId unidad = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "unidad"));
		return equipoListaAssembler.toCollection(unidad.getEquiposDeUnidad());
	}
	
	@GetMapping("{id}/miembros")
	public CollectionModel<PersonaModel> miembros(@PathVariable Long id) {
		UnidadConId unidad = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "unidad"));
		return perListaAssembler.toCollection(unidad.getMiembrosGC());
	}
	
//	@GetMapping("{id}/incidencias")
//	public CollectionModel<IncidenciaModel> incidenciasDeUnidad(@PathVariable Long id) {
//		UnidadConId unidad = repositorio.findById(id)
//				.orElseThrow(() -> new RegisterNotFoundException(id, "unidad"));
//		
//		   List<IncidenciaModel> incidencias = new ArrayList<>();
//		    
//		   for (Persona miembroGC : unidad.getMiembrosGC()) {
//			   for (EquipoPersonal equipo : miembroGC.getEquiposPersonales()) 
//			   {
//		        incidencias.addAll(equipo.getIncidencias());
//			   }
//		   }
//		    
//		   return incListaAssembler.toCollection(incidencias);
//	}
	
	@GetMapping
	public CollectionModel<UnidadModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

}