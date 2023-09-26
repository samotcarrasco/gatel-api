package es.mdef.apigatel.REST;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
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

import es.mde.acing.gatel.EquipoImpl.TipoEquipo;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.repositorios.EquipoRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/equipos")
public class EquipoController {
	private final EquipoRepositorio repositorio;
	private final EquipoAssembler assembler;
	//private final EquipoListaAssembler listaAssembler;

	private Logger log;

	EquipoController(EquipoRepositorio repositorio, EquipoAssembler assembler) {
			//EquipoListaAssembler listaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		//this.listaAssembler = listaAssembler;
		log = ApiGatelApp.log;
	}

	@GetMapping("{id}")
	public EquipoModel one(@PathVariable Long id) {
		EquipoConId Equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
		log.info("Recuperada " + Equipo);
		return assembler.toModel(Equipo);
	}

//	@GetMapping
//	public CollectionModel<EquipoListaModel> all() {
//		return listaAssembler.toCollection(repositorio.findAll());
//	}

	@PostMapping
	public EquipoModel add(@Valid @RequestBody EquipoPostModel model) {
		EquipoConId Equipo = repositorio.save(assembler.toEntity(model));
		return assembler.toModel(Equipo);
	}

//	@PutMapping("{id}")
//	public EquipoModel edit(@Valid @PathVariable Long id, @RequestBody EquipoPostModel model) {
//		int n_regs = 0;
//		if (model.getTipoEquipo() == TipoEquipo.EquipoInformatico) {
//				EquipoInformaticoAPI equipo = new EquipoInformaticoAPI();
//				repositorio.actualizarEquipo(model.getMarca(), model.getCategoria(), model.getNombreEquipo(), 
//						model.getDetalles(), model.getImagen(), 
//						model.getImgReducida(), model.getMemoria(), model.getDiscoDuro(), 
//				model.getSistemaOperativo(), model.getPulgadas(), model.getTipoEquipoInformatico(), id);
//		} else if (model.getTipoEquipo() == TipoEquipo.Auriculares) {
//				AuricularesAPI equipo = new AuricularesAPI();
//				repositorio.actualizarAuriculares(model.getMarca(), model.getCategoria(), model.getNombreEquipo(), 
//						model.getDetalles(), model.getImagen(), 
//						model.getImgReducida(),model.isStereo(), model.getConexion(), id);
//			
//			} else if (model.getTipoEquipo() == TipoEquipo.WebCam) {
//				WebCamAPI equipo = new WebCamAPI();
//				repositorio.actualizarWebCam(model.getMarca(), model.getCategoria(), model.getNombreEquipo(), 
//						model.getDetalles(), model.getImagen(), 
//						model.getImgReducida(),model.getResolucion(), id);
//			}	
//
//
//		EquipoConId Equipo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "comision"));
//
//		log.info("Actualizado " + Equipo);
//		return assembler.toModel(Equipo);
//
//	}

	
//	@PutMapping("{id}")
//	public EquipoModel edit(@Valid @PathVariable Long id, @RequestBody EquipoPostModel model) {
//		EquipoConId Equipo = repositorio.findById(id).map(mod -> {
//
//			if (model.getTipoEquipo() == TipoEquipo.EquipoInformatico) {
//				EquipoInformaticoAPI equipo = (EquipoInformaticoAPI) new EquipoConId();
//				repositorio.actualizarEquipo(model.getMemoria(), model.getDiscoDuro(), 
//						model.getSistemaOperativo(), model.getPulgadas(), id);
//				equipo.setMemoria(model.getMemoria());
//				equipo.setDiscoDuro(model.getDiscoDuro());
//				equipo.setSistemaOperativo(model.getSistemaOperativo());
//				equipo.setPulgadas(model.getPulgadas());
//				//mod = equipo;
//				
//			} else if (model.getTipoEquipo() == TipoEquipo.Auriculares) {
//				AuricularesAPI equipo = (AuricularesAPI) new EquipoConId();
//				repositorio.actualizarAuriculares(model.isStereo(), model.getConexion(), id);
//				equipo.setConexion(model.getConexion());
//				equipo.setStereo(model.isStereo());
//				//mod = equipo;
//			
//			} else if (model.getTipoEquipo() == TipoEquipo.WebCam) {
//				WebCamAPI equipo = (WebCamAPI) new EquipoConId();
//				repositorio.actualizarWebCam(model.getResolucion(), id);
//				equipo.setResolucion(model.getResolucion());
//				//mod = equipo;
//			}
//
//			mod.setMarca(model.getMarca());
//			mod.setCategoria(model.getCategoria());
//			mod.setNombreEquipo(model.getNombreEquipo());
//			mod.setDetalles(model.getDetalles());
//			mod.setImagen(model.getImagen());
//			mod.setImgReducida(model.getImgReducida());			
//
//			return repositorio.save(mod);
//		}).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
//		log.info("Actualizado " + Equipo);
//		return assembler.toModel(Equipo);
//	}

	
//	@PatchMapping("/{id}/fechaentrega")
//	public EquipoModel patchFechaentrega(@PathVariable Long id, @RequestBody FechaEntregaModel model) {
//		EquipoConId Equipo = repositorio.findById(id).map(mod -> {
//			mod.setFechaEngregaFisica(model.getFechaEntrega());
//			mod.setEstado(EstadoEquipo.entregado);
//			return repositorio.save(mod);
//		}).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
//		log.info("Actualizado " + Equipo);
//		return assembler.toModel(Equipo);
//	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		EquipoConId Equipo = (EquipoConId) repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Equipo"));
	}

}