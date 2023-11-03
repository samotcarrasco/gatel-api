package es.mdef.apigatel.repositorios;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.mde.acing.gatel.PersonaImpl.Perfil;
import es.mde.acing.gatel.Unidad;
import es.mdef.apigatel.entidades.PersonaConId;


public interface PersonaRepositorio extends JpaRepository<PersonaConId, Long> {

	Optional<PersonaConId> findByNombreUsuario(String username);

	@Query(value = "SELECT * FROM public.personas WHERE tip = :tip", nativeQuery = true)
	PersonaConId findByTip(String tip);
	
	List<PersonaConId> findPersonasByUnidad(Unidad unidad);

	List<PersonaConId> findByPerfil(Perfil perfil);
	
}