package org.clubmaratoncaceres.springboot.app.models.service;

import java.util.List;

import org.clubmaratoncaceres.springboot.app.models.dao.IParametroDAO;
import org.clubmaratoncaceres.springboot.app.models.dao.IRoleDAO;
import org.clubmaratoncaceres.springboot.app.models.dao.ISocioDAO;
import org.clubmaratoncaceres.springboot.app.models.entity.Parametro;
import org.clubmaratoncaceres.springboot.app.models.entity.Role;
import org.clubmaratoncaceres.springboot.app.models.entity.Socio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ServiceDAO")
@Primary
public class ServiceDAO implements IServiceDAO {

	@Autowired
	private IParametroDAO parametroDAO;
	
	@Autowired
	private ISocioDAO socioDAO;
	
	@Autowired
	private IRoleDAO roleDAO;

	@Override
	@Transactional(readOnly = true)
	public Parametro findByParametroAndCodigo(String parametro, String codigo) {
		return parametroDAO.findByParametroAndCodigo(parametro, codigo);
	}
	
	@Override
	public List<Parametro> findByParametro(String parametro) {
		return parametroDAO.findByParametroOrderByCodigo(parametro);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Socio> findAllSocio() {
		return (List<Socio>) socioDAO.findAll();
	}

	@Override
	@Transactional
	public void saveSocio(Socio socio) {
		socioDAO.save(socio);
	}

	@Override
	@Transactional(readOnly = true)
	public Socio findOneSocio(Long id) {
		return socioDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteSocio(Long id) {
		socioDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countSocio() {
		return socioDAO.count();
	}

	@Override
	@Transactional(readOnly = true)
	public Socio fetchSocioByIdWithInscripciones(Long id) {
		return socioDAO.fetchByIdWithInscripciones(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Role findOneRole(Long id) {
		return roleDAO.findById(id).orElse(null);
	}
	
	
}
