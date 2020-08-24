package org.clubmaratoncaceres.springboot.app.models.dao;

import org.clubmaratoncaceres.springboot.app.models.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface IRoleDAO extends CrudRepository<Role, Long> {

	
}