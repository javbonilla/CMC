package org.clubmaratoncaceres.springboot.app.models.domain;

import java.io.Serializable;

import org.clubmaratoncaceres.springboot.app.models.entity.Usuario;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class SessionUsuario implements Serializable {

	private Usuario usuario;

	public SessionUsuario() {
		this.usuario = null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
