package es.mdef.apigatel.repositorios;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mdef.apigatel.entidades.EquipoConId;
import jakarta.transaction.Transactional;

public interface EquipoRepositorio extends JpaRepository<EquipoConId, Long> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.equipos SET " + "tipo_equipo = 'U'," +
	"numero_serie = :numeroSerie, " +
	"fecha_adquisicion = :fechaAdquisicion, " +
	"fecha_asignacion = :fechaAsignacion, " +
	"modelo_id = :modeloId, " +
	"unidad_id = :unidadId, " +
	"persona_id = null " + 
	"WHERE id = :id", nativeQuery = true) 
	void actualizarEquipoDeUnidad( 
			  @Param("numeroSerie") String marca,	  
			  @Param("fechaAdquisicion") LocalDate fechaAdquisicion,	  
			  @Param("fechaAsignacion") LocalDate fechaAsignacion,	  
			  @Param("modeloId") Long modeloId,	  
			  @Param("unidadId") Long unidadId,	  
			  @Param("id") Long id 
	  );

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.equipos SET " + "tipo_equipo = 'P'," +
	"numero_serie = :numeroSerie, " +
	"fecha_adquisicion = :fechaAdquisicion, " +
	"fecha_asignacion = :fechaAsignacion, " +
	"modelo_id = :modeloId, " +
	"unidad_id = null, " +
	"persona_id = :personaId " + 
	"WHERE id = :id", nativeQuery = true) 
	void actualizarEquipoPersonal( 
			  @Param("numeroSerie") String marca,	  
			  @Param("fechaAdquisicion") LocalDate fechaAdquisicion,	  
			  @Param("fechaAsignacion") LocalDate fechaAsignacion,	  
			  @Param("modeloId") Long modeloId,	  
			  @Param("personaId") Long personaId,	  
			  @Param("id") Long id 
	  );

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.equipos SET " + 
	"tipo_equipo = 'P', " +
	"unidad_id = null " +
	"WHERE id = :id", nativeQuery = true) 
	void actualizarEquipoPersonalAsignacion( 
			  @Param("id") Long id 
	  );
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE public.equipos SET " + 
	"tipo_equipo = 'U', " +
	"persona_id = null " +		
	"WHERE id = :id", nativeQuery = true) 
	void actualizarEquipoUnidadAsignacion( 
			  @Param("id") Long id 
	  );

	

}
