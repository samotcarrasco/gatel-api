package es.mdef.apigatel.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import es.mdef.apigatel.entidades.UnidadConId;

public interface UnidadRepositorio extends JpaRepository<UnidadConId, Long> {

	
}
