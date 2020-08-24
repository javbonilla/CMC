package org.clubmaratoncaceres.springboot.app.controllers;

import java.security.Principal;
import java.util.Locale;

import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.clubmaratoncaceres.springboot.app.models.domain.SessionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
	
	@Autowired
	private SessionUsuario sessionUsuario;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required = false) String error, @RequestParam(value="logout", required = false) String logout, 
			Model model, Principal principal, RedirectAttributes flash, Locale locale) {
		
		model.addAttribute("noMostrarPie", true);
		model.addAttribute("titulo", messageSource.getMessage("texto.login.titulo", null, locale));
		
		if (principal != null) {
			// Ya habia iniciado sesion anteriormente
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_INFO, "ya inició sesión anteriormente.");
			return "redirect:/";
		}
		
		if (error != null) {
			// Mostrar eror por pantalla
			model.addAttribute("error", messageSource.getMessage("texto.login.mensaje-error-datos-login", null, locale));
		}
		
		if (logout != null) {
			// Mostrar informacion de logout por pantalla
			model.addAttribute("success", " Sesión cerrada correctamente.");
			sessionUsuario.setUsuario(null);
		}
		
		return "login";
	}
}
