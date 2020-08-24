package org.clubmaratoncaceres.springboot.app.validation;


import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;

public class MayorEdadValidador implements ConstraintValidator<MayorEdad, Date> {

	@Override
	public void initialize(MayorEdad constraintAnnotation) {

	}

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		LocalDate fNacimiento = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate ahora = LocalDate.now();
		
		return Period.between(fNacimiento, ahora).getYears() >= Constantes.MAYOR_EDAD;
	}
	
}
