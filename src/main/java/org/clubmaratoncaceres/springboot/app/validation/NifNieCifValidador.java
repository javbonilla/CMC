package org.clubmaratoncaceres.springboot.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NifNieCifValidador implements ConstraintValidator<NifNieCif, String> {

	@Override
	public void initialize(NifNieCif constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			NifNieCifValidar validadorNif = new NifNieCifValidar();
			boolean validar = validadorNif.isvalido(value);
			return validar;
		} catch (Exception e) {
			return false;
		}
	}

}
