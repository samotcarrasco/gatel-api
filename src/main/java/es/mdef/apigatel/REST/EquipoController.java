package es.mdef.apigatel.REST;

import java.time.LocalDate;
import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mde.acing.gatel.Incidencia;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.EquipoDeUnidadAPI;
import es.mdef.apigatel.entidades.EquipoPersonalAPI;
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
		EquipoConId Equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
		return assembler.toModel(Equipo);
	}

	@GetMapping
	public CollectionModel<EquipoModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@PostMapping
	public EquipoModel add(@Valid @RequestBody EquipoPostModel model) {

		if ((model.getNumeroSerie() == null) || (model.getFechaAdquisicion() == null)) {
			throw new ArgumentNotValidException("dar de alta equipo");
		}

		model.setFechaAsignacion(null);
		model.setTipoEquipo(null);
		model.setUnidad(null);
		model.setPersona(null);

		EquipoConId equipo = repositorio.save(assembler.toEntity(model));

		return assembler.toModel(equipo);
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

		int n_regs = 0;
		EquipoConId equipo = new EquipoConId();
		LocalDate fechaAsignacion = LocalDate.now();

		if ((model.getEquipo() == null) || !((model.getPersona() == null) ^ (model.getUnidad() == null))) {
			throw new ArgumentNotValidException("asignar el equipo");
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
		
		log.info("Asignado equipo " + model.getEquipo().getId());
		return assembler.toModel(equipo);
	}

	@PatchMapping("/desasignarEquipo/{idEquipo}")
	public EquipoModel desasignarEquipo(@PathVariable Long idEquipo) {

		int n_regs = 0;
		EquipoConId equipoDesasignado = new EquipoConId();
		
		EquipoConId equipo = repositorio.findById(idEquipo)
				.orElseThrow(() -> new RegisterNotFoundException(idEquipo, "Equipo"));

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

		log.info("Desasignado " + equipo.getId());
		return assembler.toModel(equipoDesasignado);
	}

	@GetMapping("{id}/incidencias")
	public CollectionModel<IncidenciaListaModel> incidenciasDeEquipo(@PathVariable Long id) {
		EquipoConId equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "equipo"));
		return incListaAssembler.toCollection(equipo.getIncidencias());
	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		EquipoConId equipo = (EquipoConId) repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
		log.info("Borrado el equipo: " + equipo.toString());
	}

}