package es.mdef.apigatel.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import es.mdef.apigatel.entidades.IncidenciaConId;

public interface IncidenciaRepositorio extends JpaRepository<IncidenciaConId, Long> {

	 
}
