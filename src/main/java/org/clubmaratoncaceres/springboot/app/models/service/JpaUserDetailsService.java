package org.clubmaratoncaceres.springboot.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.clubmaratoncaceres.springboot.app.models.dao.IUsuarioDAO;
import org.clubmaratoncaceres.springboot.app.models.entity.Role;
import org.clubmaratoncaceres.springboot.app.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDAO.findByNif(username);
		
		// Si no se ha encontrado al usuario, se lanza excepcion
		if (usuario == null) {
			logger.error("Error login: no existe el usuario " + username + " en la Base de Datos");
			throw new UsernameNotFoundException("Usuario " + username + " no existe en la aplicación.");
		}
		
		// Informacion del usuario
		logger.info("Socio logado: " + usuario.getSocio().toString());
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (Role role : usuario.getRoles()) {
			logger.info("Usuario: " + username + " | Rol: " + role.getAuthority());
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}
		
		// Si no tiene ningun rol, se lanza excepcion
		if (authorities.isEmpty()) {
				logger.error("Error login: usuario " + username + " sin roles en la Base de Datos");
				throw new UsernameNotFoundException("Usuario " + username + " sin roles activos en la aplicación.");
		}
		
		return new User(usuario.getSocio().getNif(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}
}
