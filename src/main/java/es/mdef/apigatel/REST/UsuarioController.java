package es.mdef.apigatel.REST;

import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	private final PersonaRepositorio repositorio;
	private final PersonaAssembler assembler;
	private final Logger log;

	UsuarioController(PersonaRepositorio repositorio, PersonaAssembler assembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		log = ApiGatelApp.log;
	}

	@GetMapping
	public PersonaModel user() {
		String nombreUsuario = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PersonaConId persona = repositorio.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new RegisterNotFoundException(nombreUsuario, "Persona"));

		log.info("Recuperados datos del usuario " + nombreUsuario);
		return assembler.toModel(persona);
	}

}