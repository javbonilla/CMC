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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.clubmaratoncaceres.springboot.app.validation.CuentaBancaria;
import org.clubmaratoncaceres.springboot.app.validation.MayorEdad;
import org.clubmaratoncaceres.springboot.app.validation.NifNieCif;
import org.clubmaratoncaceres.springboot.app.validation.Telefono;
import org.iban4j.Iban;
import org.springframework.format.annotation.DateTimeFormat;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

@Entity
@Table(name = "socios")
public class Socio implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 2, max = 50)
	private String nombre;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 2, max = 150)
	private String apellidos;

	@Column(nullable = false)
	@NotBlank
	@Size(min = 9, max = 10)
	@NifNieCif
	private String nif;

	@Column(nullable = false)
	@NotNull
	private Integer sexo;

	@Column(name = "f_nacimiento")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	@Past
	@MayorEdad
	private Date fNacimiento;

	@Column(nullable = false)
	@NotBlank
	@Telefono
	private String telefono;

	@Column(nullable = false)
	@NotBlank
	@Email
	private String email;

	@Column(nullable = false)
	@NotNull
	private Integer cuota;

	@Column(nullable = false, name = "cuenta_bancaria")
	@NotBlank
	@Size(min = 10, max = 50)
	@CuentaBancaria
	private String cuentaBancaria;

	@Column(nullable = false)
	@NotNull
	private Integer estado;

	@Column(name = "f_baja")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fBaja;

	@Column(name = "f_alta")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date fAlta;

	@Column(name = "ts_ult_conexion")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	private Date tsUltConexion;

	@Column(name = "ts_ult_mod")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZZ")
	private Date tsUltMod;

	@Column(name = "id_user_mo")
	private Long idUserMo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Usuario usuario;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "socio")
	private List<Inscripcion> inscripciones;

	private String foto;

	@Column(name = "talla")
	@Size(max = 10)
	private String talla;

	@Column(name = "dorsal_personalizado")
	@Size(max = 50)
	private String dorsalPersonalizado;
	
	@Lob
	@Column(name = "observaciones", length = 512)
	@Size(max = 512)
	private String observaciones;

	public Socio() {

	}
	
	@PrePersist
	@PreUpdate
	public void prePersist() {
		
		// IBAN
		Iban iban = Iban.valueOf(this.cuentaBancaria.replaceAll("\\s+", ""));
		this.cuentaBancaria = iban.toFormattedString();
		
		// Telefono: se guarda con formato internacional		
		try {
			PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
			PhoneNumber number;
			number = phoneUtil.parse(this.telefono, "ES");
			System.out.println("Telefono a guardar: " + Long.toString(number.getNationalNumber()));
			this.telefono = phoneUtil.format(number, PhoneNumberFormat.INTERNATIONAL);
		} catch (NumberParseException e) {
			System.out.println(e.toString());
		}
		
		// Email: todo en minuscula
		this.email = this.email.toLowerCase();
		
		// Auditoria
		this.tsUltMod = new Date();
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Integer getSexo() {
		return sexo;
	}

	public void setSexo(Integer sexo) {
		this.sexo = sexo;
	}

	public Date getfNacimiento() {
		return fNacimiento;
	}

	public void setfNacimiento(Date fNacimiento) {
		this.fNacimiento = fNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getCuota() {
		return cuota;
	}

	public void setCuota(Integer cuota) {
		this.cuota = cuota;
	}

	public String getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Date getfBaja() {
		return fBaja;
	}

	public void setfBaja(Date fBaja) {
		this.fBaja = fBaja;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getTsUltConexion() {
		return tsUltConexion;
	}

	public void setTsUltConexion(Date tsUltConexion) {
		this.tsUltConexion = tsUltConexion;
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

	public String getDorsalPersonalizado() {
		return dorsalPersonalizado;
	}

	public void setDorsalPersonalizado(String dorsalPersonalizado) {
		this.dorsalPersonalizado = dorsalPersonalizado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean esSocio() {
		return (this.sexo == Constantes.SEXO_HOMBRE);
	}

	public boolean esSocia() {
		return (this.sexo == Constantes.SEXO_MUJER);
	}
	
	public String enmascararCuenta() {
		return this.cuentaBancaria.substring(0, 4)
				.concat(" **** **** **** **** ")
				.concat(this.cuentaBancaria.substring(this.cuentaBancaria.length()-4, this.cuentaBancaria.length()));
	}

	@Override
	public String toString() {
		return "Socio [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", nif=" + nif + ", sexo=" + sexo
				+ ", fNacimiento=" + fNacimiento + ", telefono=" + telefono + ", email=" + email + ", cuota=" + cuota
				+ ", cuentaBancaria=" + cuentaBancaria + ", estado=" + estado + ", fBaja=" + fBaja + ", f_alta=" + fAlta
				+ ", tsUltConexion=" + tsUltConexion + ", tsUltMod=" + tsUltMod + ", idUserMo=" + idUserMo
				+ ", usuario=" + usuario + ", foto=" + foto + ", talla=" + talla + ", dorsal=" + dorsalPersonalizado
				+ ", observaciones=" + observaciones + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
