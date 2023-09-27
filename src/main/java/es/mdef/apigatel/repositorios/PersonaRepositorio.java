package es.mdef.apigatel.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.apigatel.entidades.PersonaConId;

public interface PersonaRepositorio extends JpaRepository<PersonaConId, Long> {

}
