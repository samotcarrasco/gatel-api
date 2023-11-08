package es.mdef.apigatel.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.mdef.apigatel.repositorios.PersonaRepositorio;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private PersonaRepositorio repositorio;

	public UserDetailsServiceImpl(PersonaRepositorio repositorio) {
		this.repositorio = repositorio;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repositorio.findByNombreUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario " + username + " no encontrado"));
	}

}