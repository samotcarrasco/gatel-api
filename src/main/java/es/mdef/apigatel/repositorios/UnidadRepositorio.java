package es.mdef.apigatel.repositorios;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.apigatel.entidades.UnidadConId;

public interface UnidadRepositorio extends JpaRepository<UnidadConId, Long> {

	Optional<UnidadConId> findById(Long id);
	Optional<UnidadConId> findByCodigoUnidad(String codigo);

}
