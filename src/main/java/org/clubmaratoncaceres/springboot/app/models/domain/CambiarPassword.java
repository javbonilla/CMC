package org.clubmaratoncaceres.springboot.app.models.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CambiarPassword {

	@NotBlank
	private String passwordActual;

	@NotBlank
	@Size(min = 6, max = 50)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^.,_+=!*()@%&]).{6,50}$")
	private String passwordNueva;

	@NotBlank
	@Size(min = 6, max = 50)
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^.,_+=!*()@%&]).{6,50}$")
	private String passwordNuevaRepite;

	public CambiarPassword() {

	}

	public String getPasswordActual() {
		return passwordActual;
	}

	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}

	public String getPasswordNueva() {
		return passwordNueva;
	}

	public void setPasswordNueva(String passwordNueva) {
		this.passwordNueva = passwordNueva;
	}

	public String getPasswordNuevaRepite() {
		return passwordNuevaRepite;
	}

	public void setPasswordNuevaRepite(String passwordNuevaRepite) {
		this.passwordNuevaRepite = passwordNuevaRepite;
	}

}
