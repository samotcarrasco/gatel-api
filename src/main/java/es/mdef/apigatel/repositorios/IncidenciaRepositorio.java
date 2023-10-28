package es.mdef.apigatel.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.IncidenciaConId;
import es.mdef.apigatel.entidades.PersonaConId;

public interface IncidenciaRepositorio extends JpaRepository<IncidenciaConId, Long> {
	
	@Query(value = "SELECT * FROM public.incidencias WHERE equipo_id = :id", nativeQuery = true)
	List<IncidenciaConId> findByEquipoId(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM public.incidencias WHERE persona_id = :id", nativeQuery = true)
	List<IncidenciaConId> findIncidenciasByResolutor(Long id);
	 
}
