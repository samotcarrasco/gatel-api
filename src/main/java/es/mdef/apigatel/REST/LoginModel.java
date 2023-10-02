package es.mdef.apigatel.REST;


import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mde.acing.gatel.MiembroGCImpl.Empleo;
import es.mde.acing.gatel.PersonaImpl.TipoPersona;

@Relation(itemRelation = "login")
public class LoginModel extends RepresentationModel<LoginModel> {

	private String nombreUsuario;
	private String password;
	private String perfil;
	
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}
