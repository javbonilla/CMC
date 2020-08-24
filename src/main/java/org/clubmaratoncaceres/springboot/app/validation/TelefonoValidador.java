package org.clubmaratoncaceres.springboot.app.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class TelefonoValidador implements ConstraintValidator<Telefono, String> {

	@Override
	public void initialize(Telefono constraintAnnotation) {

	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		// Se comprueba el formato del telefono. Se usa la libreria libphonenumber para ello
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
		  return phoneUtil.isValidNumber(phoneUtil.parse(value, "ES"));
		} catch (NumberParseException e) {
		  System.err.println("NumberParseException was thrown: " + e.toString());
		  return false;
		}
	}
	
}