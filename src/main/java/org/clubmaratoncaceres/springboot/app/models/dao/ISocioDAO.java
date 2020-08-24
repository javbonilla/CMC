package org.clubmaratoncaceres.springboot.app.models.dao;

import org.clubmaratoncaceres.springboot.app.models.entity.Socio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ISocioDAO extends CrudRepository<Socio, Long> {

	@Query("select s from Socio s left join fetch s.inscripciones i where s.id = ?1")
	public Socio fetchByIdWithInscripciones(Long id);
}
