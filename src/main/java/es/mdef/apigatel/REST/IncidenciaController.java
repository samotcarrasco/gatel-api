package es.mdef.apigatel.REST;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.server.ResponseStatusException;

import es.mde.acing.gatel.Incidencia;
import es.mde.acing.gatel.IncidenciaImpl.EstadoIncidencia;
import es.mde.acing.gatel.Persona;
import es.mde.acing.gatel.PersonaImpl.Perfil;
import es.mdef.apigatel.entidades.ExtravioAPI;
import es.mdef.apigatel.entidades.SolicitudAPI;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.ApiGatelApp;
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
		
		IncidenciaConId incidencia = null;
		Boolean incidenciaEncontrada = false;	
		
		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		Optional<PersonaConId> usuario = perRepositorio
				.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

		switch (rol) {
		case USUARIO:
			incidencia = repositorio.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró la incidencia"));
			
			if (((PersonaConId) incidencia.getEquipo().getPersona()).getId() == usuario.get().getId()) {
				incidenciaEncontrada = true;
			}
			break;
		case ADMIN_CENTRAL:
			incidencia = repositorio.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró la incidencia"));
			incidenciaEncontrada = true;
			break;
		case ADMIN_UNIDAD:
			incidencia = repositorio.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró la incidencia"));
			incidenciaEncontrada = true;
			break;
		}
		
		if (incidenciaEncontrada) {
			return assembler.toModel(incidencia);
		}  
		else	
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
			List <EquipoConId> equiposPersona = eqRepositorio.findByPersonaId(usuario.get().getId());
			for (EquipoConId equipo : equiposPersona) {
				for (Incidencia incidencia : equipo.getIncidencias()) {
					incidenciasObtenidas.add((IncidenciaConId) incidencia);
				}
			}
			break;

		case ADMIN_UNIDAD:
			List<PersonaConId> personasDeUnidad = perRepositorio.findPersonasByUnidad(((UnidadConId) usuario.get().getUnidad()).getId());
			List<EquipoConId> equiposDeUnidad = eqRepositorio.findByUnidadId(((UnidadConId) usuario.get().getUnidad()).getId());
			
			System.out.println("personas de la unidad" + personasDeUnidad.size());
			for (PersonaConId persona: personasDeUnidad) {
					List<EquipoConId> equiposPersonaUnidad = eqRepositorio.findByPersonaId(persona.getId());
					for (EquipoConId equipo: equiposPersonaUnidad) {
						List<IncidenciaConId> incidenciasDeEquipo = repositorio.findByEquipoId(equipo.getId());
						for (IncidenciaConId incidencia: incidenciasDeEquipo) {
						incidenciasObtenidas.add(incidencia);
						}
					}
			}
			for (EquipoConId equipo: equiposDeUnidad) {
				List<IncidenciaConId> incidenciasDeEquipo = repositorio.findByEquipoId(equipo.getId());
				for (IncidenciaConId incidencia: incidenciasDeEquipo) {
				incidenciasObtenidas.add(incidencia);
				}
			}	
			
			break;

		case ADMIN_CENTRAL:
			incidenciasObtenidas = repositorio.findAll();
			break;

		case RESOLUTOR:
			List <IncidenciaConId> incidencias = repositorio.findIncidenciasByResolutor(usuario.get().getId());
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
				ConfiguracionAPI configuracion = new ConfiguracionAPI();
				configuracion.setAplicacion(model.getAplicacion());
				inc = configuracion;
				break;
			default:
				break;
			}

			inc.setFechaResolucion(model.getFechaResolucion());
			inc.setEstado(model.getEstado());
			inc.setDescripcion(model.getDescripcion());
			inc.setAgenteResolutor(model.getAgenteResolutor());
			inc.setEquipo(model.getEquipo());
			inc.setDetalles(model.getDetalles());

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

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		String rol = rolesUsuario.iterator().next().toString();
		if (rol.equals(Perfil.ADMIN_CENTRAL)) {
	
		IncidenciaConId incidencia = (IncidenciaConId) repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Incidencia"));
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}
	}

}