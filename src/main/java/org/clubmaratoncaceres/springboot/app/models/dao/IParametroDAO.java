package org.clubmaratoncaceres.springboot.app.models.dao;

import java.util.List;

import org.clubmaratoncaceres.springboot.app.models.entity.Parametro;
import org.springframework.data.repository.CrudRepository;

public interface IParametroDAO extends CrudRepository<Parametro, Long> {

	public Parametro findByParametroAndCodigo(String parametro, String codigo);
	
	public List<Parametro> findByParametroOrderByCodigo(String parametro);

}
