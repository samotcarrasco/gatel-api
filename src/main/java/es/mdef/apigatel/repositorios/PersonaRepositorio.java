package es.mdef.apigatel.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mdef.apigatel.entidades.PersonaConId;

public interface PersonaRepositorio extends JpaRepository<PersonaConId, Long> {
	
	@RepositoryRestResource(path = "login", collectionResourceRel = "usuarios")
	public interface UsuarioRepositorio extends JpaRepository<PersonaConId, Long> {

//	    Optional<PersonaConId> findByNombreUsuarioAndPassword(String nombreUsuario, String password);
//	    Optional<PersonaConId> findByTip(String tip);
	}

	Optional<PersonaConId> findByNombreUsuarioAndPassword(String nombreUsuario, String password);

}