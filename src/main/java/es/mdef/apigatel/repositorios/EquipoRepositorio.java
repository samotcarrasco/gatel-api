package es.mdef.apigatel.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.mde.acing.gatel.Persona;
import es.mde.acing.gatel.Unidad;
import es.mdef.apigatel.entidades.EquipoConId;
import jakarta.transaction.Transactional;

public interface EquipoRepositorio extends JpaRepository<EquipoConId, Long> {


	@Modifying
	@Transactional
	@Query(value = "UPDATE public.equipos SET " + "tipo_equipo = :tipoEquipo," + "fecha_asignacion = :fechaAsignacion, "
			+ "persona_id = :personaId, " + "unidad_id = :unidadId " + "WHERE id = :id", nativeQuery = true)
	int asignar(@Param("tipoEquipo") Character tipoEquipo, @Param("fechaAsignacion") LocalDate fechaAsignacion,
			@Param("personaId") Long personaId, @Param("unidadId") Long unidadId, @Param("id") Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE public.equipos SET " + "tipo_equipo = null," + "fecha_asignacion = null, "
			+ "persona_id = null, " + "unidad_id = null " + "WHERE id = :id", nativeQuery = true)
	int desasignar(@Param("id") Long id);

	List<EquipoConId> findByPersona(Persona persona);

	List<EquipoConId> findByUnidad(Unidad unidad);

}
