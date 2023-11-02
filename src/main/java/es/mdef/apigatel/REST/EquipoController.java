package es.mdef.apigatel.REST;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.mde.acing.gatel.Incidencia;
import es.mde.acing.gatel.PersonaImpl.Perfil;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.EquipoDeUnidadAPI;
import es.mdef.apigatel.entidades.EquipoPersonalAPI;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.repositorios.EquipoRepositorio;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.repositorios.UnidadRepositorio;
import es.mdef.apigatel.validation.ArgumentNotValidException;
import es.mdef.apigatel.validation.RegisterNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/equipos")
public class EquipoController {
	private final EquipoRepositorio repositorio;
	private final EquipoAssembler assembler;
	private final EquipoListaAssembler<EquipoConId> listaAssembler;
	private final IncidenciaListaAssembler<Incidencia> incListaAssembler;
	private final PersonaRepositorio perRepositorio;
	private final UnidadRepositorio uniRepositorio;
	private final Logger log;

	EquipoController(EquipoRepositorio repositorio, EquipoAssembler assembler,
			EquipoListaAssembler<EquipoConId> listaAssembler, PersonaRepositorio perRepositorio,
			IncidenciaListaAssembler<Incidencia> incListaAssembler, UnidadRepositorio uniRepositorio) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.perRepositorio = perRepositorio;
		this.incListaAssembler = incListaAssembler;
		this.uniRepositorio = uniRepositorio;
		log = ApiGatelApp.log;
	}

	@GetMapping("{id}")
	public EquipoModel one(@PathVariable Long id) {
		EquipoConId equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
		EquipoModel equipoObtenido = null;

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

//		System.out.println(SecurityContextHolder.getContext().getAuthentication());
//		System.out.println("principal = " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
//		System.out.println("rol = " + rolesUsuario.iterator().next());
//			
		Optional<PersonaConId> usuario = perRepositorio
				.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

		switch (rol) {
		case USUARIO:
			if (((PersonaConId) equipo.getPersona()).getId() == usuario.get().getId()) {
				equipoObtenido = assembler.toModel(equipo);
			}
			break;
		case ADMIN_UNIDAD:
			if (equipo.getPersona() != null) {
				if (((UnidadConId) equipo.getPersona().getUnidad()).getId() == ((UnidadConId) usuario.get().getUnidad())
						.getId()) {
					equipoObtenido = assembler.toModel(equipo);
				}
			} else if (equipo.getUnidad() != null) {
				List<EquipoConId> equiposUnidad = repositorio
						.findByUnidadId(((UnidadConId) usuario.get().getUnidad()).getId());
				for (EquipoConId equipoUnidad : equiposUnidad) {
					if (((UnidadConId) equipoUnidad.getUnidad()).getId() == ((UnidadConId) usuario.get().getUnidad())
							.getId()) {
						equipoObtenido = assembler.toModel(equipoUnidad);
						break;
					}
				}
			}
			break;
		case ADMIN_CENTRAL:
			equipoObtenido = assembler.toModel(equipo);
			break;
		case RESOLUTOR:
			break;
		}

		if (equipoObtenido != null) {
			return equipoObtenido;
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}
	}

	@GetMapping
	public CollectionModel<EquipoModel> all() {
		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		Optional<PersonaConId> usuario = perRepositorio
				.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

		List<EquipoConId> equiposObtenidos = new ArrayList<>();

		switch (rol) {
		case USUARIO:
			List<EquipoConId> equiposPersona = repositorio.findByPersonaId(usuario.get().getId());
			for (EquipoConId equipo : equiposPersona) {
				equiposObtenidos.add(equipo);
			}
			break;

		case ADMIN_UNIDAD:
			List<EquipoConId> equiposDeUnidad = repositorio.findByUnidadId(((UnidadConId) usuario.get().getUnidad()).getId());
			List<PersonaConId> personasDeUnidad = perRepositorio.findPersonasByUnidad(((UnidadConId) usuario.get().getUnidad()).getId());
			
			for (PersonaConId persona: personasDeUnidad) {
					List<EquipoConId> equiposPersonaUnidad = repositorio.findByPersonaId(persona.getId());
						equiposObtenidos.addAll(equiposPersonaUnidad);
			}
			for (EquipoConId equipo: equiposDeUnidad) {
				equiposObtenidos.add(equipo);
			}
			
			break;

		case ADMIN_CENTRAL:
			List<EquipoConId> equipos = repositorio.findAll();
			for (EquipoConId equipo : equipos) {
				equiposObtenidos.add(equipo);
			}
			break;

		case RESOLUTOR:
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		
		default:
			System.out.println("Rol desconocido");
		}

		return listaAssembler.toCollection(equiposObtenidos);
	}

	@PostMapping
	public EquipoModel add(@Valid @RequestBody EquipoPostModel model) {

		EquipoModel equipoGenerado = null;

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		if (rol.equals(Perfil.ADMIN_CENTRAL) || rol.equals(Perfil.ADMIN_UNIDAD)) {

			if ((model.getNumeroSerie() == null) || (model.getFechaAdquisicion() == null)) {
				throw new ArgumentNotValidException("Datos nulos, no se puede dar de alta");
			}

			model.setFechaAsignacion(null);
			model.setTipoEquipo(null);
			model.setUnidad(null);
			model.setPersona(null);

			EquipoConId equipo = repositorio.save(assembler.toEntity(model));
			equipoGenerado = assembler.toModel(equipo);
			// System.out.println(equipoGenerado.toString());
		}

		if (equipoGenerado != null) {
			return equipoGenerado;
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}
	}

//	@PutMapping("{id}")
//	public EquipoModel edit(@Valid @PathVariable Long id, @RequestBody EquipoPostModel model) {
//
//		if (model.getTipoEquipo() == TipoEquipo.EQUIPO_UNIDAD) {
//			repositorio.actualizarEquipoDeUnidad(model.getNumeroSerie(), model.getFechaAdquisicion(),
//					model.getFechaAsignacion(),	model.getModelo().getId(), model.getUnidad().getId(), id);
//		} else if (model.getTipoEquipo() == TipoEquipo.EQUIPO_PERSONAL) {
//			repositorio.actualizarEquipoPersonal(model.getNumeroSerie(), model.getFechaAdquisicion(),
//					model.getFechaAsignacion(), model.getModelo().getId(), model.getPersona().getId(), id);
//		}	
//
//		EquipoConId equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "equipo"));
//
////		return assembler.toModel(equipo);
//		
//		int n_regs = 0;
//
//		if (model.getTipoEquipo() == TipoEquipo.EQUIPO_UNIDAD) {
//			if ((model.getFechaAsignacion() == null)||(model.getUnidad() == null)) {
//				new RegisterNotFoundException(id, "Error en la asignación del equipo");
//			} else {
//				n_regs = repositorio.update('U', model.getNumeroSerie(), model.getFechaAdquisicion(),
//						model.getFechaAsignacion(), model.getModelo().getId(), model.getUnidad().getId(),
//						null, id);
//			}
//		} else if (model.getTipoEquipo() == TipoEquipo.EQUIPO_PERSONAL) {
//			if ((model.getFechaAsignacion() == null)||(model.getPersona() == null)) {
//				new RegisterNotFoundException(id, "Error en la asignación del equipo");
//			} else {
//				n_regs = repositorio.update('P', model.getNumeroSerie(), model.getFechaAdquisicion(),
//						model.getFechaAsignacion(), model.getModelo().getId(), model.getUnidad().getId(),
//						null, id);
//			}
//		}
//		
//		if (n_regs == 0) {
//			throw new RegisterNotFoundException(id, "comision");
//		}
//		
////		} else if (model.getTipo() == Tipo.VIOGEN) {
////			n_regs = repositorio.update(model.getPuesto(), model.getLocalidad(), model.getEspecialidad(),
////					model.getEmpleo(), model.getFechaLimite(), model.getDuracion(), model.getDetalles(), 'V', null,
////					model.getRiesgo(), id);
////		} else {
////			n_regs = repositorio.update(model.getPuesto(), model.getLocalidad(), model.getEspecialidad(),
////					model.getEmpleo(), model.getFechaLimite(), model.getDuracion(), model.getDetalles(), null, null,
////					null, id);
////		}
////
//		
////
////		ComisionApi comision = repositorio.findById(id)
////				.orElseThrow(() -> new RegisterNotFoundException(id, "comision"));
////
////		log.info("Actualizado " + comision);
////		return assembler.toModel(comision);
//		
//		
//		
//	}

	@PatchMapping("/asignarEquipo")
	public EquipoModel asignarEquipo(@RequestBody EquipoAsignarModel model) {

		
		EquipoConId equipo = new EquipoConId();

		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		if (rol.equals(Perfil.ADMIN_CENTRAL) || rol.equals(Perfil.ADMIN_UNIDAD)) {

		int n_regs = 0;
		LocalDate fechaAsignacion = LocalDate.now();
		
		if ((model.getEquipo() != null) || (model.getPersona() != null)) {
			throw new ArgumentNotValidException(" asignar el equipo");
		}

		if (model.getPersona() != null) {
			equipo = new EquipoPersonalAPI();

			equipo.setPersona(model.getPersona());
			equipo.setUnidad(null);

			n_regs = repositorio.asignar('P', fechaAsignacion, model.getPersona().getId(), null,
					model.getEquipo().getId());

		} else if (model.getUnidad() != null) {
			equipo = new EquipoDeUnidadAPI();

			equipo.setPersona(null);
			equipo.setUnidad(model.getUnidad());
			
			n_regs = repositorio.asignar('U', fechaAsignacion, null, model.getUnidad().getId(),
					model.getEquipo().getId());
		}

		if (n_regs == 0) {
			throw new ArgumentNotValidException("asignar el equipo");
		}

		equipo.setNumeroSerie(model.getEquipo().getNumeroSerie());
		equipo.setFechaAdquisicion(model.getEquipo().getFechaAdquisicion());
		equipo.setFechaAsignacion(fechaAsignacion);
		equipo.setModelo(model.getEquipo().getModelo());
		equipo.setId(model.getEquipo().getId());
		
		return assembler.toModel(equipo);
		}
//		else {
//			throw new ArgumentNotValidException("El equipo ya está asignado, debe desasignarlo antes de asignarlo");					
//		}
//		}
	else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}	
	
	}

	@PatchMapping("/desasignarEquipo/{idEquipo}")
	public EquipoModel desasignarEquipo(@PathVariable Long idEquipo) {

		EquipoConId equipo = repositorio.findById(idEquipo)
				.orElseThrow(() -> new RegisterNotFoundException(idEquipo, "Equipo"));
		
		Collection<? extends GrantedAuthority> rolesUsuario = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());

		if (rol.equals(Perfil.ADMIN_CENTRAL) || rol.equals(Perfil.ADMIN_UNIDAD)) {

			int n_regs = 0;
			EquipoConId equipoDesasignado = new EquipoConId();
			
			
			if (equipo.getPersona() != null || equipo.getUnidad() != null){
			n_regs = repositorio.desasignar(idEquipo);
	
			if (n_regs == 0) {
				throw new RegisterNotFoundException(idEquipo, "Equipo");
			}
	
			equipoDesasignado.setPersona(null);
			equipoDesasignado.setUnidad(null);
			equipoDesasignado.setNumeroSerie(equipo.getNumeroSerie());
			equipoDesasignado.setFechaAdquisicion(equipo.getFechaAdquisicion());
			equipoDesasignado.setFechaAsignacion(null);
			equipoDesasignado.setModelo(equipo.getModelo());
			equipoDesasignado.setId(equipo.getId());
			
			return assembler.toModel(equipoDesasignado);
		}
		else {
			throw new ArgumentNotValidException("El equipo no está asignado, no se puede desasignar");					
		}
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		}	
	
	}

	@GetMapping("{id}/incidencias")
	public CollectionModel<IncidenciaListaModel> incidenciasDeEquipo(@PathVariable Long id) {
		EquipoConId equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "equipo"));
		return incListaAssembler.toCollection(equipo.getIncidencias());
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		EquipoConId equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
		
		Collection<? extends GrantedAuthority> rolesUsuario =  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Perfil rol = Perfil.valueOf(rolesUsuario.iterator().next().toString());
	
		Optional<PersonaConId> usuario = perRepositorio.findByNombreUsuario(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		
		  switch (rol) {
	        case ADMIN_CENTRAL:
	        	repositorio.deleteById(equipo.getId());
	            break;
	     	        
		    case ADMIN_UNIDAD:
		    	 if (equipo.getPersona() != null) {  		
	           		  if (((UnidadConId) equipo.getPersona().getUnidad()).getId() == ((UnidadConId) usuario.get().getUnidad()).getId() ) {
	                		repositorio.deleteById(equipo.getId());
	           		  }
	           	  	}		
		       	  else if (equipo.getUnidad() != null) {	
		       		  List<EquipoConId> equiposUnidad = repositorio.findByUnidadId(((UnidadConId) usuario.get().getUnidad()).getId());
		       		 for (EquipoConId equipoUnidad : equiposUnidad) {
		         		  if (((UnidadConId) equipoUnidad.getUnidad()).getId() == ((UnidadConId) usuario.get().getUnidad()).getId() ) {
		                	  repositorio.deleteById(equipo.getId());
		        	         break;
		         		  }
		       		 }	
		       	  }
		          break;
		   default:
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acceso denegado");
		    }
		}
	
}