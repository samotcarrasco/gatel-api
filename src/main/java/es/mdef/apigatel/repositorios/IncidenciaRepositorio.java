package es.mdef.apigatel.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import es.mde.acing.gatel.Equipo;
import es.mdef.apigatel.entidades.IncidenciaConId;
import es.mdef.apigatel.entidades.PersonaConId;

public interface IncidenciaRepositorio extends JpaRepository<IncidenciaConId, Long> {

	List<IncidenciaConId> findByEquipo(Equipo equipo);

	List<IncidenciaConId> findByAgenteResolutor(PersonaConId persona);

}
