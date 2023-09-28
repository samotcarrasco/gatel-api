package es.mdef.apigatel.REST;

import java.io.IOException;

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
import es.mdef.apigatel.ReductorImagen;
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
	public CollectionModel<EquipoModel> equipos(@PathVariable Long id) {
		ModeloConId modelo = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "modelo"));
		return equipoListaAssembler.toCollection(modelo.getEquipos());
	}


	@PostMapping
	public ModeloModel add(@Valid @RequestBody ModeloPostModel model) throws IOException {
		ModeloConId modelo = repositorio.save(assembler.toEntity(model));
		return assembler.toModel(modelo);
	}

	@PutMapping("{id}")
	public ModeloModel edit(@Valid @PathVariable Long id, @RequestBody ModeloPostModel model) throws IOException {
		int n_regs = 0;
		if (model.getTipoModelo() == TipoModelo.EquipoInformatico) {
				//EquipoInformaticoAPI equipo = new EquipoInformaticoAPI();
				String imgReducida = ReductorImagen.reducirImagen(model.getImagen(),150,150);
				repositorio.actualizarEquipo(model.getMarca(), model.getCategoria(), model.getNombreModelo(), 
						model.getDetalles(), model.getImagen(), 
						imgReducida, model.getMemoria(), model.getDiscoDuro(), 
				model.getSistemaOperativo(), model.getPulgadas(), model.getTipoEquipoInformatico(), id);
		} else if (model.getTipoModelo() == TipoModelo.Auriculares) {
				//AuricularesAPI equipo = new AuricularesAPI();
				repositorio.actualizarAuriculares(model.getMarca(), model.getCategoria(), model.getNombreModelo(), 
						model.getDetalles(), model.getImagen(), 
						ReductorImagen.reducirImagen(model.getImagen(),150,150),model.isStereo(), model.getConexion(), id);
			} else if (model.getTipoModelo() == TipoModelo.WebCam) {
				//WebCamAPI equipo = new WebCamAPI();
				repositorio.actualizarWebCam(model.getMarca(), model.getCategoria(), model.getNombreModelo(), 
						model.getDetalles(), model.getImagen(), 
						ReductorImagen.reducirImagen(model.getImagen(),150,150) ,model.getResolucion(), id);
			}	


		ModeloConId modelo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "modelo"));

		log.info("Actualizado " + modelo);
		return assembler.toModel(modelo);

	}


	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		ModeloConId modelo = (ModeloConId) repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Modelo"));
	}

}