package es.mdef.apigatel.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import es.mdef.apigatel.entidades.EquipoConId;

public interface EquipoRepositorio extends JpaRepository<EquipoConId, Long> {

	
}
