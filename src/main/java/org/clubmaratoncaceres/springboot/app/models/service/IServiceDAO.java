package org.clubmaratoncaceres.springboot.app.models.service;

import java.util.List;

import org.clubmaratoncaceres.springboot.app.models.entity.Parametro;
import org.clubmaratoncaceres.springboot.app.models.entity.Role;
import org.clubmaratoncaceres.springboot.app.models.entity.Socio;


public interface IServiceDAO {
	
	/* SOCIOS */
	// Retornar todo 
	public List<Socio> findAllSocio();

	// Crear un nuevo socio
	public void saveSocio(Socio socio);

	// Buscar un socio por su id
	public Socio findOneSocio(Long id);
	
	// Buscar un socio por su id. Traer todo
	public Socio fetchSocioByIdWithInscripciones(Long id);

	// Borrar un socio
	public void deleteSocio(Long id);

	// Contar los socios
	public Long countSocio();

	/* PARAMETROS */ 
	public Parametro findByParametroAndCodigo(String parametro, String codigo);
	public List<Parametro> findByParametro(String parametro);
	
	/* ROLES */
	public Role findOneRole(Long id);
}
