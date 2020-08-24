package org.clubmaratoncaceres.springboot.app.models.domain;

import javax.validation.constraints.NotBlank;

import org.clubmaratoncaceres.springboot.app.validation.NifNieCif;

public class RestaurarPassword {

	@NotBlank
	@NifNieCif
	private String nif;

	public RestaurarPassword() {

	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

}
