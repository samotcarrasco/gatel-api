package es.mdef.apigatel.REST;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.mde.acing.gatel.Incidencia;
import es.mde.acing.gatel.IncidenciaImpl.EstadoIncidencia;
import es.mde.acing.gatel.IncidenciaImpl.TipoIncidencia;
import es.mde.acing.gatel.PersonaImpl.Perfil;
import es.mdef.apigatel.entidades.ExtravioAPI;
import es.mdef.apigatel.entidades.SolicitudAPI;
import es.mdef.apigatel.entidades.AveriaAPI;
import es.mdef.apigatel.entidades.ConfiguracionAPI;
import es.mdef.apigatel.entidades.EquipoConId;
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

	IncidenciaController(IncidenciaRepositorio repositorio, IncidenciaAssembler assembler,
			IncidenciaListaAssembler<IncidenciaConId> listaAssembler, PersonaRepositorio perRepositorio,
			EquipoRepositorio eqRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.perRepositorio = perRepositorio;
		this.eqRepositorio = eqRepositorio;
	}

	@GetMapping("{id}")
	public IncidenciaModel one(@PathVariable Long id) {

		IncidenciaConId incidencia = null;
		Boolean incidenciaEncontrada = false;

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		Optional<PersonaConId> usuario = perRepositorio
				.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

		switch (rol) {
		case USUARIO:
			incidencia = repositorio.findById(id)
					.orElseThrow(() -> new NoSuchElementException("No se encontró la incidencia"));

			if (((PersonaConId) incidencia.getEquipo().getPersona()).getId() == usuario.get().getId()) {
				incidenciaEncontrada = true;
			}
			break;
		case ADMIN_CENTRAL:
			incidencia = repositorio.findById(id)
					.orElseThrow(() -> new NoSuchElementException("No se encontró la incidencia"));
			incidenciaEncontrada = true;
			break;
		case ADMIN_UNIDAD:
			incidencia = repositorio.findById(id)
					.orElseThrow(() -> new NoSuchElementException("No se encontró la incidencia"));
			incidenciaEncontrada = true;
			break;

		case RESOLUTOR:
			incidencia = repositorio.findById(id)
					.orElseThrow(() -> new NoSuchElementException("No se encontró la incidencia"));

			if (((PersonaConId) incidencia.getAgenteResolutor()).getId() == usuario.get().getId()) {
				incidenciaEncontrada = true;
			}
			break;
		}

		if (incidenciaEncontrada) {
			return assembler.toModel(incidencia);
		} else
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");

	}

	@GetMapping
	public CollectionModel<IncidenciaListaModel> all() {

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		Optional<PersonaConId> usuario = perRepositorio
				.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

		List<IncidenciaConId> incidenciasObtenidas = new ArrayList<>();

		switch (rol) {
		case USUARIO:
			List<EquipoConId> equiposPersona = eqRepositorio.findByPersona(usuario.get());
			for (EquipoConId equipo : equiposPersona) {
				for (Incidencia incidencia : equipo.getIncidencias()) {
					incidenciasObtenidas.add((IncidenciaConId) incidencia);
				}
			}
			break;

		case ADMIN_UNIDAD:
			List<PersonaConId> personasDeUnidad = perRepositorio.findPersonasByUnidad(usuario.get().getUnidad());
			List<EquipoConId> equiposDeUnidad = eqRepositorio.findByUnidad(usuario.get().getUnidad());

			System.out.println("personas de la unidad" + personasDeUnidad.size());
			for (PersonaConId persona : personasDeUnidad) {
				List<EquipoConId> equiposPersonaUnidad = eqRepositorio.findByPersona(persona);
				for (EquipoConId equipo : equiposPersonaUnidad) {
					List<IncidenciaConId> incidenciasDeEquipo = repositorio.findByEquipo(equipo);
					for (IncidenciaConId incidencia : incidenciasDeEquipo) {
						incidenciasObtenidas.add(incidencia);
					}
				}
			}
			for (EquipoConId equipo : equiposDeUnidad) {
				List<IncidenciaConId> incidenciasDeEquipo = repositorio.findByEquipo(equipo);
				for (IncidenciaConId incidencia : incidenciasDeEquipo) {
					incidenciasObtenidas.add(incidencia);
				}
			}

			break;

		case ADMIN_CENTRAL:
			incidenciasObtenidas = repositorio.findAll();
			break;

		case RESOLUTOR:
			List<IncidenciaConId> incidencias = repositorio.findByAgenteResolutor(usuario.get());
			incidenciasObtenidas.addAll(incidencias);
			break;
		default:
			System.out.println("Rol desconocido");
		}

		return listaAssembler.toCollection(incidenciasObtenidas);
	}

	@PostMapping
	public IncidenciaModel add(@Valid @RequestBody IncidenciaPostModel model) throws IOException {

		IncidenciaConId incidencia = repositorio.save(assembler.toEntity(model));
		return assembler.toModel(incidencia);
	}

	@PatchMapping("{id}")
	public IncidenciaModel edit(@Valid @PathVariable Long id, @RequestBody IncidenciaPutModel model) {

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());
		if (rol.equals(Perfil.RESOLUTOR)) {

			IncidenciaConId incidencia = repositorio.findById(id).map(inc -> {

				String detallesAnteriores = (inc.getDetalles() != null) ? inc.getDetalles() : "";

				inc.setDetalles(detallesAnteriores + " \n ---- " + inc.getAgenteResolutor().getNombre() + " "
						+ inc.getAgenteResolutor().getApellidos() + LocalDate.now() + "---- \n" + model.getDetalles());

				if (model.getTipoIncidencia() == TipoIncidencia.AVERIA) {
					((AveriaAPI) inc).setReparable(model.getReparable());
				} else if (model.getTipoIncidencia() == TipoIncidencia.EXTRAVIO) {
					((ExtravioAPI) inc).setUltimaUbicacion(model.getUltimaUbicacion());
					((ExtravioAPI) inc).setBloqueado(model.isBloqueado());
					((ExtravioAPI) inc).setBorrado(model.isBorrado());
					((ExtravioAPI) inc).setEncontrado(model.isEncontrado());
				} else if (model.getTipoIncidencia() == TipoIncidencia.SOLICITUD) {
					((SolicitudAPI) inc).setAceptado(model.isAceptado());
				} else if (model.getTipoIncidencia() == TipoIncidencia.CONFIGURACION) {
					((ConfiguracionAPI) inc).setAplicacion(model.getAplicacion());
				}

				return repositorio.save(inc);

			}).orElseThrow(() -> new RegisterNotFoundException(id, "incicencia"));

			return assembler.toModel(incidencia);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}
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
			inc.setFechaAsignacion(LocalDate.now());
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
			inc.setFechaCierre(LocalDate.now());
			return repositorio.save(inc);
		}).orElseThrow(() -> new RegisterNotFoundException(idIncidencia, "Incidencia"));

		return assembler.toModel(incidencia);
	}

}