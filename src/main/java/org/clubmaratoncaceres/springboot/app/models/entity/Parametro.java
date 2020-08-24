package org.clubmaratoncaceres.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "parametros")
public class Parametro implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 3, max = 20)
	private String parametro;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 1, max = 20)
	private String codigo;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 1, max = 150)
	private String valor;

	@Column(name = "ts_ult_mod")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	private Date tsUltMod;

	@Column(name = "id_user_mo")
	private Long idUserMo;

	public Parametro() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Date getTsUltMod() {
		return tsUltMod;
	}

	public void setTsUltMod(Date tsUltMod) {
		this.tsUltMod = tsUltMod;
	}

	public Long getIdUserMo() {
		return idUserMo;
	}

	public void setIdUserMo(Long idUserMo) {
		this.idUserMo = idUserMo;
	}

	@Override
	public String toString() {
		return "Parametro [id=" + id + ", parametro=" + parametro + ", codigo=" + codigo + ", valor=" + valor
				+ ", tsUltMod=" + tsUltMod + ", idUserMo=" + idUserMo + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
