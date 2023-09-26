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

import es.mde.acing.gatel.ModeloImpl.TipoModelo;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.ModeloConId;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.repositorios.ModeloRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/modelos")
public class ModeloController {
	private final ModeloRepositorio repositorio;
	private final ModeloAssembler assembler;
	private final ModeloListaAssembler listaAssembler;
	private final EquipoListaAssembler equipoListaAssembler;

	private Logger log;

	ModeloController(ModeloRepositorio repositorio, ModeloAssembler assembler,
			ModeloListaAssembler listaAssembler, EquipoListaAssembler equipoListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.equipoListaAssembler = equipoListaAssembler;
		log = ApiGatelApp.log;
	}

	@GetMapping("{id}")
	public ModeloModel one(@PathVariable Long id) {
		ModeloConId modelo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "modelo"));
		log.info("Recuperada " + modelo);
		return assembler.toModel(modelo);
	}

	@GetMapping
	public CollectionModel<ModeloListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@GetMapping("{id}/equipos")
	public CollectionModel<EquipoModel> equiposDeUnidad(@PathVariable Long id) {
		ModeloConId modelo = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "modelo"));
		return equipoListaAssembler.toCollection(modelo.getEquipos());
	}


	@PostMapping
	public ModeloModel add(@Valid @RequestBody ModeloPostModel model) {
		ModeloConId modelo = repositorio.save(assembler.toEntity(model));
		return assembler.toModel(modelo);
	}

	@PutMapping("{id}")
	public ModeloModel edit(@Valid @PathVariable Long id, @RequestBody ModeloPostModel model) {
		int n_regs = 0;
		if (model.getTipoModelo() == TipoModelo.EquipoInformatico) {
				EquipoInformaticoAPI equipo = new EquipoInformaticoAPI();
				repositorio.actualizarEquipo(model.getMarca(), model.getCategoria(), model.getNombreModelo(), 
						model.getDetalles(), model.getImagen(), 
						model.getImgReducida(), model.getMemoria(), model.getDiscoDuro(), 
				model.getSistemaOperativo(), model.getPulgadas(), model.getTipoEquipoInformatico(), id);
		} else if (model.getTipoModelo() == TipoModelo.Auriculares) {
				AuricularesAPI equipo = new AuricularesAPI();
				repositorio.actualizarAuriculares(model.getMarca(), model.getCategoria(), model.getNombreModelo(), 
						model.getDetalles(), model.getImagen(), 
						model.getImgReducida(),model.isStereo(), model.getConexion(), id);
			
			} else if (model.getTipoModelo() == TipoModelo.WebCam) {
				WebCamAPI equipo = new WebCamAPI();
				repositorio.actualizarWebCam(model.getMarca(), model.getCategoria(), model.getNombreModelo(), 
						model.getDetalles(), model.getImagen(), 
						model.getImgReducida(),model.getResolucion(), id);
			}	


		ModeloConId modelo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "comision"));

		log.info("Actualizado " + modelo);
		return assembler.toModel(modelo);

	}

	
//	@PutMapping("{id}")
//	public ModeloModel edit(@Valid @PathVariable Long id, @RequestBody ModeloPostModel model) {
//		ModeloConId modelo = repositorio.findById(id).map(mod -> {
//
//			if (model.getTipoModelo() == TipoModelo.EquipoInformatico) {
//				EquipoInformaticoAPI equipo = (EquipoInformaticoAPI) new ModeloConId();
//				repositorio.actualizarEquipo(model.getMemoria(), model.getDiscoDuro(), 
//						model.getSistemaOperativo(), model.getPulgadas(), id);
//				equipo.setMemoria(model.getMemoria());
//				equipo.setDiscoDuro(model.getDiscoDuro());
//				equipo.setSistemaOperativo(model.getSistemaOperativo());
//				equipo.setPulgadas(model.getPulgadas());
//				//mod = equipo;
//				
//			} else if (model.getTipoModelo() == TipoModelo.Auriculares) {
//				AuricularesAPI equipo = (AuricularesAPI) new ModeloConId();
//				repositorio.actualizarAuriculares(model.isStereo(), model.getConexion(), id);
//				equipo.setConexion(model.getConexion());
//				equipo.setStereo(model.isStereo());
//				//mod = equipo;
//			
//			} else if (model.getTipoModelo() == TipoModelo.WebCam) {
//				WebCamAPI equipo = (WebCamAPI) new ModeloConId();
//				repositorio.actualizarWebCam(model.getResolucion(), id);
//				equipo.setResolucion(model.getResolucion());
//				//mod = equipo;
//			}
//
//			mod.setMarca(model.getMarca());
//			mod.setCategoria(model.getCategoria());
//			mod.setNombreModelo(model.getNombreModelo());
//			mod.setDetalles(model.getDetalles());
//			mod.setImagen(model.getImagen());
//			mod.setImgReducida(model.getImgReducida());			
//
//			return repositorio.save(mod);
//		}).orElseThrow(() -> new RegisterNotFoundException(id, "Modelo"));
//		log.info("Actualizado " + modelo);
//		return assembler.toModel(modelo);
//	}

	
//	@PatchMapping("/{id}/fechaentrega")
//	public ModeloModel patchFechaentrega(@PathVariable Long id, @RequestBody FechaEntregaModel model) {
//		ModeloConId modelo = repositorio.findById(id).map(mod -> {
//			mod.setFechaEngregaFisica(model.getFechaEntrega());
//			mod.setEstado(EstadoModelo.entregado);
//			return repositorio.save(mod);
//		}).orElseThrow(() -> new RegisterNotFoundException(id, "Modelo"));
//		log.info("Actualizado " + modelo);
//		return assembler.toModel(modelo);
//	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		ModeloConId modelo = (ModeloConId) repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Modelo"));
	}

}