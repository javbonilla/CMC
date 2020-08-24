package org.clubmaratoncaceres.springboot.app.models.domain;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class PasswordTemporalGenerador {
	
	private Integer clave;
	
	public String generarPasswordTemporal() {
		
		// Se genera un numero aleatorio entre 1000 y 9999
		clave = new Random().nextInt((Constantes.PASSWORD_TEMPORAL_MAX - Constantes.PASSWORD_TEMPORAL_MIN) + 1) + Constantes.PASSWORD_TEMPORAL_MIN;
		return Constantes.PASSWORD_TEMPORAL_PREFIJO + clave.toString();
	}
	
}
