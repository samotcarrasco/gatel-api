package es.mdef.apigatel.REST;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.mde.acing.gatel.EquipoPersonal;
import es.mde.acing.gatel.Incidencia;
import es.mde.acing.gatel.PersonaImpl.Perfil;
import es.mde.acing.gatel.PersonaImpl.TipoPersona;
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

	@GetMapping("nombreUsuario/{nombreUsuario}")
	public PersonaModel oneByNombreUsuario(@PathVariable String nombreUsuario) {
		PersonaConId persona = repositorio.findByNombreUsuario(nombreUsuario)
				.orElseThrow(() -> new RegisterNotFoundException(nombreUsuario, "Persona"));

		return assembler.toModel(persona);
	}

	@GetMapping("tip/{tip}")
	public PersonaModel oneByTip(@PathVariable String tip) {
		PersonaConId persona = repositorio.findByTip(tip);

		if (persona == null) {
			throw new RegisterNotFoundException(tip, "TIP");
		}

		return assembler.toModel(persona);
	}

	@GetMapping
	public CollectionModel<PersonaListaModel> all() {

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		List<PersonaConId> personas = new ArrayList<>();

		switch (rol) {
		case ADMIN_CENTRAL:
			personas = repositorio.findAll();
			break;
		default:
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}
		return listaAssembler.toCollection(personas);

	}

	@GetMapping("/personal")
	public CollectionModel<PersonaListaModel> personal() {

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		Optional<PersonaConId> usuario = repositorio
				.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

		List<PersonaConId> personasObtenidas = new ArrayList<>();

		switch (rol) {

		case ADMIN_UNIDAD:
			List<PersonaConId> personasDeUnidad = repositorio.findPersonasByUnidad(usuario.get().getUnidad());
			for (PersonaConId persona : personasDeUnidad) {
				if ((persona.getTipoPersona() == TipoPersona.MIEMBRO_GC)
						&& (persona.getEquiposPersonales().size() > 0)) {
					personasObtenidas.add(persona);
				}
			}

			break;

		case ADMIN_CENTRAL:
			List<PersonaConId> personas = repositorio.findAll();
			for (PersonaConId persona : personas) {
				if ((persona.getTipoPersona() == TipoPersona.MIEMBRO_GC)
						&& (persona.getEquiposPersonales().size() > 0)) {
					personasObtenidas.add(persona);
				}
			}
			break;

		default:
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}

		return listaAssembler.toCollection(personasObtenidas);
	}

	@GetMapping("/resolutores")
	public CollectionModel<PersonaListaModel> resolutores() {
		return listaAssembler.toCollection(repositorio.findByPerfil(Perfil.RESOLUTOR));
	}

	@SuppressWarnings("unchecked")
	@GetMapping("{id}/equipos")
	public CollectionModel<EquipoModel> equiposDePersona(@PathVariable Long id) {
		PersonaConId persona = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Persona"));
		return equipoListaAssembler.toCollection((List<EquipoConId>) (List<?>) persona.getEquiposPersonales());
	}

	@SuppressWarnings("unchecked")
	@GetMapping("{id}/incidencias")
	public CollectionModel<IncidenciaListaModel> incidenciasDePersona(@PathVariable Long id) {
		PersonaConId persona = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Persona"));

		List<Incidencia> incidencias = new ArrayList<>();

		for (EquipoPersonal equipo : persona.getEquiposPersonales()) {
			for (Incidencia incidencia : ((EquipoConId) equipo).getIncidencias()) {
				incidencias.add((Incidencia) incidencia);
			}
		}
		return incListaAssembler.toCollection((List<IncidenciaConId>) (List<?>) incidencias);
	}

}