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

import es.mde.acing.gatel.PersonaImpl.Perfil;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.MiembroGCAPI;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.repositorios.UnidadRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/unidades")
public class UnidadController {
	private final UnidadRepositorio repositorio;
	private final PersonaRepositorio perRepositorio;
	private final UnidadAssembler assembler;
	private final UnidadListaAssembler<UnidadConId> listaAssembler;
	private final EquipoListaAssembler<EquipoConId> equipoListaAssembler;
	private final PersonaListaAssembler<MiembroGCAPI> perListaAssembler;

	UnidadController(UnidadRepositorio repositorio, UnidadAssembler assembler,
			UnidadListaAssembler<UnidadConId> listaAssembler, EquipoListaAssembler<EquipoConId> equipoListaAssembler,
			PersonaListaAssembler<MiembroGCAPI> perListaAssembler, PersonaRepositorio perRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.equipoListaAssembler = equipoListaAssembler;
		this.perListaAssembler = perListaAssembler;
		this.perRepositorio = perRepositorio;
	}

	@GetMapping("{id}")
	public UnidadModel one(@PathVariable Long id) {
		UnidadConId Unidad = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Unidad"));
		return assembler.toModel(Unidad);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("{id}/equipos")
	public CollectionModel<EquipoModel> equiposDeUnidad(@PathVariable Long id) {
		UnidadConId unidad = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "unidad"));
		return equipoListaAssembler.toCollection((List<EquipoConId>) (List<?>) unidad.getEquiposDeUnidad());
	}

	@SuppressWarnings("unchecked")
	@GetMapping("{id}/miembros")
	public CollectionModel<PersonaListaModel> miembros(@PathVariable Long id) {
		UnidadConId unidad = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "unidad"));
		return perListaAssembler.toCollection((List<MiembroGCAPI>) (List<?>) unidad.getMiembrosGC());
	}

	@GetMapping
	public CollectionModel<UnidadModel> all() {

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		Optional<PersonaConId> usuario = perRepositorio
				.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

		List<UnidadConId> unidades = new ArrayList<>();

		switch (rol) {
		case ADMIN_CENTRAL:
			unidades = repositorio.findAll();
			break;
		case ADMIN_UNIDAD:
			unidades.add((UnidadConId) usuario.get().getUnidad());
			break;

		default:
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}
		return listaAssembler.toCollection(unidades);
	}

}