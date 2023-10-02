package es.mdef.apigatel.REST;

import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mde.acing.gatel.PersonaImpl.TipoPersona;
import es.mdef.apigatel.ApiGatelApp;
import es.mdef.apigatel.entidades.AuricularesAPI;
import es.mdef.apigatel.entidades.EquipoInformaticoAPI;
import es.mdef.apigatel.entidades.PersonaConId;
import es.mdef.apigatel.entidades.UnidadConId;
import es.mdef.apigatel.entidades.WebCamAPI;
import es.mdef.apigatel.repositorios.PersonaRepositorio;
import es.mdef.apigatel.validation.RegisterNotFoundException;
import jakarta.validation.Valid;
import net.minidev.json.JSONObject;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/login")
public class LoginController {
	private final PersonaRepositorio repositorio;
	private final PersonaAssembler assembler;
	
	LoginController(PersonaRepositorio repositorio, PersonaAssembler assembler
			) {
		this.repositorio = repositorio;
		this.assembler = assembler;
	}


	@PostMapping
	public ResponseEntity<String> login(@RequestParam String nombreUsuario, @RequestParam String password) {
    Optional<PersonaConId> usuario = repositorio.findByNombreUsuarioAndPassword(nombreUsuario, password);
    
    if (usuario.isPresent()) {
        return ResponseEntity.ok("Credenciales correctas");
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
	}
	
	@PostMapping("/login2")
	public ResponseEntity<String> login2(@RequestBody LoginModel model) {
	    Optional<PersonaConId> usuario = repositorio.findByNombreUsuarioAndPassword(model.getNombreUsuario(), model.getPassword());
	    
	    if (usuario.isPresent()) {
	        PersonaConId persona = usuario.get();
	        String perfil = persona.getPerfil(); 
	        
	        JSONObject responseJson = new JSONObject();
	        responseJson.put("perfil", perfil);
	        
	        return ResponseEntity.ok(responseJson.toString());
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
	    }
	}

}
	
//	@GetMapping("/login")
//	public PersonaModel login(@RequestBody LoginModel model) {
//
//		PersonaConId usuario = (repositorio.findByNombreUsuarioAndPassword(model.getNombreUsuario(), model.getPassword()).orElseThrow(() -> new RegisterNotFoundException(model.getNombreUsuario(), "nombreUsuario"));
//		return assembler.toModel(usuario);
//	}
	
//	@PostMapping("")
//	public PersonaModel login(@RequestBody LoginModel model) {
//
//		PersonaConId usuario = repositorio.findByTip(model.getNombreUsuario()).orElseThrow(() -> new RegisterNotFoundException(model.getNombreUsuario(), "nombreUsuario"));
//		return assembler.toModel(usuario);
//	}