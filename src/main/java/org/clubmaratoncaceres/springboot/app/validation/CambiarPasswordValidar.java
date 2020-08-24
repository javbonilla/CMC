package org.clubmaratoncaceres.springboot.app.validation;

import org.clubmaratoncaceres.springboot.app.models.domain.CambiarPassword;
import org.clubmaratoncaceres.springboot.app.models.domain.SessionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CambiarPasswordValidar implements Validator {

	@Autowired
	private SessionUsuario sessionUsuario;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CambiarPassword.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// Cast del objeto recibido como entrada
		CambiarPassword cambiarPassword = (CambiarPassword) target;

		// Y ahora procedemos a validar sus atributos
		// 1. La contraseña actual debe coincidir con la del usuario
		if (!passwordEncoder.matches(cambiarPassword.getPasswordActual(), sessionUsuario.getUsuario().getPassword())) {
			errors.rejectValue("passwordActual", "texto.cambiar-password.validacion.password-actual");
		}
		
		// 2. Las contraseñas nuevas deben coincidir
		if (!cambiarPassword.getPasswordNueva().equals(cambiarPassword.getPasswordNuevaRepite())) {
			errors.rejectValue("passwordNueva", "texto.cambiar-password.validacion.password-nueva");
			errors.rejectValue("passwordNuevaRepite", "texto.cambiar-password.validacion.password-nueva");
		}
		
	}

}
