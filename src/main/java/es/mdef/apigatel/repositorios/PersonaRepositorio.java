package es.mdef.apigatel.repositorios;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mdef.apigatel.entidades.PersonaConId;


public interface PersonaRepositorio extends JpaRepository<PersonaConId, Long> {

	
//	@RepositoryRestResource(path = "login", collectionResourceRel = "usuarios")
//	public interface UsuarioRepositorio extends JpaRepository<PersonaConId, Long> {
//
////	    Optional<PersonaConId> findByNombreUsuarioAndPassword(String nombreUsuario, String password);
////	    Optional<PersonaConId> findByTip(String tip);
//	}
//	
//	
//
//	Optional<PersonaConId> findByNombreUsuarioAndPassword(String nombreUsuario, String password);
//	

	Optional<PersonaConId> findByNombreUsuario(String username);

	@Query(value = "SELECT * FROM public.personas WHERE tip = :tip", nativeQuery = true)
	PersonaConId buscaPorTip(String tip);

	@Query(value = "SELECT * FROM public.personas WHERE unidad_id = :unidad_id", nativeQuery = true)
	List<PersonaConId> findPersonasByUnidad(Long unidad_id);
}