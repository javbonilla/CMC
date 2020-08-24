package org.clubmaratoncaceres.springboot.app.models.dao;

import org.clubmaratoncaceres.springboot.app.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {

	@Query("select u from Usuario u join fetch u.socio s join fetch u.roles r where s.nif = ?1")
	public Usuario findByNif(String username);
	
}