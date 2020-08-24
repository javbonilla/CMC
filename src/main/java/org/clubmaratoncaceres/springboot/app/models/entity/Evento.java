package org.clubmaratoncaceres.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "eventos")
public class Evento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 10, max = 100)
	private String nombre;

	@Column(name = "f_evento")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	@NotNull
	private Date fEvento;

	@Column(nullable = false)
	@NotNull
	private Integer tipo;

	private String direccion;

	private String url;

	@Column(name = "com_autonoma")
	private Integer comAutonoma;

	private Integer pais;

	@Column(name = "f_desde_inscrip")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	@NotNull
	private Date fDesdeInscrip;

	@Column(name = "f_hasta_inscrip")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	@NotNull
	private Date fHastaInscrip;

	private String foto;

	@Column(name = "ts_ult_mod")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	private Date tsUltMod;

	@Column(name = "id_user_mo")
	private Long idUserMo;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "evento")
	private List<Inscripcion> inscripciones;

	public Evento() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getfEvento() {
		return fEvento;
	}

	public void setfEvento(Date fEvento) {
		this.fEvento = fEvento;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getComAutonoma() {
		return comAutonoma;
	}

	public void setComAutonoma(Integer comAutonoma) {
		this.comAutonoma = comAutonoma;
	}

	public Integer getPais() {
		return pais;
	}

	public void setPais(Integer pais) {
		this.pais = pais;
	}

	public Date getfDesdeInscrip() {
		return fDesdeInscrip;
	}

	public void setfDesdeInscrip(Date fDesdeInscrip) {
		this.fDesdeInscrip = fDesdeInscrip;
	}

	public Date getfHastaInscrip() {
		return fHastaInscrip;
	}

	public void setfHastaInscrip(Date fHastaInscrip) {
		this.fHastaInscrip = fHastaInscrip;
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

	public List<Inscripcion> getInscripciones() {
		return inscripciones;
	}

	public void setInscripciones(List<Inscripcion> inscripciones) {
		this.inscripciones = inscripciones;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
