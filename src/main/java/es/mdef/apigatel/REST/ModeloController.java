package es.mdef.apigatel.REST;

import java.io.IOException;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mde.acing.gatel.ModeloImpl.TipoModelo;
import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.ModeloConId;
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
	private final EquipoListaAssembler<EquipoConId> equipoListaAssembler;

	ModeloController(ModeloRepositorio repositorio, ModeloAssembler assembler, ModeloListaAssembler listaAssembler,
			EquipoListaAssembler<EquipoConId> equipoListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.equipoListaAssembler = equipoListaAssembler;
	}

	@GetMapping("{id}")
	public ModeloModel one(@PathVariable Long id) {
		ModeloConId modelo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "modelo"));
		return assembler.toModel(modelo);
	}

	@GetMapping
	public CollectionModel<ModeloListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}

	@GetMapping("{id}/equipos")
	public CollectionModel<EquipoModel> equipos(@PathVariable Long id) {
		ModeloConId modelo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "modelo"));
		return equipoListaAssembler.toCollection((List<EquipoConId>) (List<?>) modelo.getEquipos());
	}

	@PostMapping
	public ModeloModel add(@Valid @RequestBody ModeloPostModel model) throws IOException {
		ModeloConId modelo = repositorio.save(assembler.toEntity(model));
		return assembler.toModel(modelo);
	}

	@PutMapping("{id}")
	public ModeloModel edit(@Valid @PathVariable Long id, @RequestBody ModeloPostModel model) throws IOException {

		if (model.getTipoModelo() == TipoModelo.EQUIPO_INFORMATICO) {
			// EquipoInformaticoAPI equipo = new EquipoInformaticoAPI();
//			String imgReducida = ReductorImagen.reducirImagen(model.getImagen(), 150, 150);
//			repositorio.actualizarEquipo(model.getMarca(), model.getNombreModelo(), model.getDetalles(),
//					model.getImagen(), imgReducida, model.getMemoria(), model.getDiscoDuro(),
//					model.getSistemaOperativo(), model.getPulgadas(), model.getTipoEquipoInformatico(), id);
			
			repositorio.actualizarEquipo(model.getMarca(), model.getNombreModelo(), model.getDetalles(),
					model.getImagen(), model.getImgReducida(), model.getMemoria(), model.getDiscoDuro(),
					model.getSistemaOperativo(), model.getPulgadas(), model.getTipoEquipoInformatico(), id);
		} else if (model.getTipoModelo() == TipoModelo.AURICULARES) {
			// AuricularesAPI equipo = new AuricularesAPI();
//			repositorio.actualizarAuriculares(model.getMarca(), model.getNombreModelo(), model.getDetalles(),
//					model.getImagen(), ReductorImagen.reducirImagen(model.getImagen(), 150, 150), model.isStereo(),
//					model.getConexion(), id);

			repositorio.actualizarAuriculares(model.getMarca(), model.getNombreModelo(), model.getDetalles(),
					model.getImagen(), model.getImgReducida(), model.isStereo(), model.getConexion(), id);
			
			
		} else if (model.getTipoModelo() == TipoModelo.WEBCAM) {
			repositorio.actualizarWebCam(model.getMarca(), model.getNombreModelo(), model.getDetalles(),
					model.getImagen(), model.getImgReducida(), model.getResolucion(), id);
		}

		ModeloConId modelo = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "modelo"));

		return assembler.toModel(modelo);

	}

	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		repositorio.findById(id).map(mod -> {
			repositorio.deleteById(id);
			return mod;
		}).orElseThrow(() -> new RegisterNotFoundException(id, "Modelo"));
	}

}