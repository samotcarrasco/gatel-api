package es.mdef.apigatel.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mdef.apigatel.entidades.EquipoConId;
import es.mdef.apigatel.entidades.IncidenciaConId;
import es.mdef.apigatel.entidades.PersonaConId;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

public interface IncidenciaRepositorio extends JpaRepository<IncidenciaConId, Long> {
	
	@Query(value = "SELECT * FROM public.incidencias WHERE equipo_id = :id", nativeQuery = true)
	List<IncidenciaConId> findByEquipoId(@Param("id") Long id);
	
	@Query(value = "SELECT * FROM public.incidencias WHERE persona_id = :id", nativeQuery = true)
	List<IncidenciaConId> findIncidenciasByResolutor(Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.incidencias SET " +
	"detalles = :detallesActuales, " +
	"reparable = :reparable " +
	"WHERE id = :id", nativeQuery = true) 
	void actualizarAveria( 
			  @Param("detallesActuales") String detallesActuales,	  
			  @Param("reparable") Boolean reparable,	  
			  @Param("id") Long id 
	  );

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.incidencias SET " +
	"detalles = :detallesActuales, " +
	"bloqueado = :bloqueado, " +
	"borrado = :borrado, " +
	"encontrado = :encontrado " +
	"WHERE id = :id", nativeQuery = true) 
	void actualizarExtravio( 
			  @Param("detallesActuales") String detallesActuales,
			  @Param("bloqueado") Boolean bloqueado,	  
			  @Param("borrado") Boolean borrado,	  
			  @Param("encontrado") Boolean encontrado,	  
			  @Param("id") Long id 
	  );

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.incidencias SET " +
	"detalles = :detallesActuales, " +
	"reparable = :reparable " +
	"WHERE id = :id", nativeQuery = true) 
	void actualizarConfiguracion( 
			  @Param("detallesActuales") String detallesActuales,	  
			  @Param("reparable") Boolean reparable,	  
			  @Param("id") Long id 
	  );

}
