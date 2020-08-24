package org.clubmaratoncaceres.springboot.app.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 60)
	private String password;

	@Column(name = "es_password_temporal", columnDefinition = "boolean default true")
	private Boolean esPasswordTemporal;

	private Boolean enabled;

	@OneToOne(mappedBy = "usuario")
	private Socio socio;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "usuario_id")
	private List<Role> roles;

	public Usuario() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role rol) {
		int i = this.buscarRolPorAuthority(rol);
		
		if (i == -1) {
			this.roles.add(rol);
		}
	}
	
	public void deleteRole(Role rol) {
		int i = this.buscarRolPorAuthority(rol);
		
		if (i > -1) {
			this.roles.remove(i);
		}
	}
	
	public void addRoleByAuthority(String authority) {
		Role rol = new Role();
		rol.setAuthority(authority);
		
		int i = this.buscarRolPorAuthority(rol);
		
		if (i == -1) {
			this.roles.add(rol);
		}
	}
	
	public void deleteRoleByAuthority(String authority) {
		Role rol = new Role();
		rol.setAuthority(authority);
		
		int i = this.buscarRolPorAuthority(rol);
		
		if (i > -1) {
			this.roles.remove(i);
		}
	}
	
	private int buscarRolPorAuthority(Role rol) {
		int retorno = -1;
		
		for (int i = 0; i < this.roles.size(); i++) {
			if (this.roles.get(i).getAuthority().equals(rol.getAuthority())) {
				retorno = i;
				break;
			}
		}
		
		return retorno;
	}

	public Boolean getEsPasswordTemporal() {
		return esPasswordTemporal;
	}

	public void setEsPasswordTemporal(Boolean esPasswordTemporal) {
		this.esPasswordTemporal = esPasswordTemporal;
	}
	
	public Boolean usuarioTieneRol(String role) {
		Boolean retorno = false;
		
		for (Role rol : this.roles) {
			if (rol.getAuthority().equals(role)) {
				retorno = true;
				break;
			}
		}
		
		return retorno;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
