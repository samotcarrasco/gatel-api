package es.mdef.apigatel.REST;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.apigatel.ApiGatelApp;
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
	private final EquipoListaAssembler equipoListaAssembler;
	

	private Logger log;

	UnidadController(UnidadRepositorio repositorio, UnidadAssembler assembler,
			UnidadListaAssembler listaAssembler, EquipoListaAssembler equipoListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.equipoListaAssembler = equipoListaAssembler;
		log = ApiGatelApp.log;
	}

	@GetMapping("{id}")
	public UnidadModel one(@PathVariable Long id) {
		UnidadConId Unidad = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Unidad"));
		log.info("Recuperada " + Unidad);
		return assembler.toModel(Unidad);
	}
	
	@GetMapping("{id}/equipos")
	public CollectionModel<EquipoModel> equiposDeUnidad(@PathVariable Long id) {
		UnidadConId unidad = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "unidad"));
		return equipoListaAssembler.toCollection(unidad.getEquiposDeUnidad());
	}

	@GetMapping
	public CollectionModel<UnidadModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

}