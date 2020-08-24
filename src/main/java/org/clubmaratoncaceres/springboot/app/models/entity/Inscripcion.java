package org.clubmaratoncaceres.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "inscripciones")
public class Inscripcion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Evento evento;

	@ManyToOne(fetch = FetchType.LAZY)
	private Socio socio;

	@Column(name = "f_alta")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	@NotNull
	private Date fAlta;

	@Size(max = 10)
	private String talla;

	@Size(max = 50)
	private String dorsal;

	@Column(columnDefinition = "TEXT")
	@Size(max = 2000)
	private String observaciones;

	@Column(name = "ts_ult_mod")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	private Date tsUltMod;

	@Column(name = "id_user_mo")
	private Long idUserMo;

	public Inscripcion() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public Date getfAlta() {
		return fAlta;
	}

	public void setfAlta(Date fAlta) {
		this.fAlta = fAlta;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public String getDorsal() {
		return dorsal;
	}

	public void setDorsal(String dorsal) {
		this.dorsal = dorsal;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
