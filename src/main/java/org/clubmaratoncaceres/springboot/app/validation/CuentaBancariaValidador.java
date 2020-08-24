package org.clubmaratoncaceres.springboot.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;


public class CuentaBancariaValidador implements ConstraintValidator<CuentaBancaria, String> {

	@Override
	public void initialize(CuentaBancaria constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value != null && !value.isBlank()) {
			try {
				value = value.replaceAll("\\s+", "");
				IbanUtil.validate(value);
				// valid
				return true;
			} catch (IbanFormatException | InvalidCheckDigitException | UnsupportedCountryException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			return false;
		}
	}
	
}