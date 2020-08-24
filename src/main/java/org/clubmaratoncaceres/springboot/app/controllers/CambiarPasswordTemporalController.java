package org.clubmaratoncaceres.springboot.app.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.clubmaratoncaceres.springboot.app.models.dao.IUsuarioDAO;
import org.clubmaratoncaceres.springboot.app.models.domain.CambiarPassword;
import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.clubmaratoncaceres.springboot.app.models.domain.SessionUsuario;
import org.clubmaratoncaceres.springboot.app.validation.CambiarPasswordValidar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CambiarPasswordTemporalController {
	
	@Autowired
	private SessionUsuario sessionUsuario;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CambiarPasswordValidar validador;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@GetMapping(value = {"/cambiar-password-temporal" })
	public String cambiarPasswordTemporal(Model model, RedirectAttributes flash, Locale locale) {
		
		// Si la password no es temporal, no hace falta cambiarla
		if (sessionUsuario.getUsuario().getEsPasswordTemporal()) {
			model.addAttribute("titulo", messageSource.getMessage("texto.cambiar-password-temporal.titulo", null, locale));
			model.addAttribute("usuario", sessionUsuario.getUsuario());
			model.addAttribute("cambiarPassword", new CambiarPassword());
			model.addAttribute("noMostrarPie", true);
			
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_WARNING, messageSource.getMessage("texto.cambiar-password-temporal.mensaje-advertencia", null, locale));
			
			return "cambiar-password-temporal";
		} else {
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_INFO, messageSource.getMessage("texto.cambiar-password-temporal.pass-no-temporal", null, locale));
			
			return "redirect:/";
		}
		
	}
	
	@PostMapping("/cambiar-password-temporal")
	public String guardar(@Valid @ModelAttribute("cambiarPassword") CambiarPassword cambiarPassword, BindingResult result, Model model,
			SessionStatus status, RedirectAttributes flash, Locale locale) {

		// Validar el cambio de password
		validador.validate(cambiarPassword, result);
		
		// Manejar si el formulario es válido o no
		if (result.hasErrors()) {

			model.addAttribute("titulo", messageSource.getMessage("texto.cambiar-password-temporal.titulo", null, locale));
			model.addAttribute("usuario", sessionUsuario.getUsuario());
			model.addAttribute("submitted", true);
			model.addAttribute("noMostrarPie", true);

			// Y volvemos al mismo formulario
			return "cambiar-password-temporal";
		}
		
		// Modificar la password del usuario
		
		sessionUsuario.getUsuario().setPassword(passwordEncoder.encode(cambiarPassword.getPasswordNueva()));
		sessionUsuario.getUsuario().setEsPasswordTemporal(false);
		usuarioDAO.save(sessionUsuario.getUsuario());
		flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_EXITO, messageSource.getMessage("texto.cambiar-password.mensaje-cambio-ok", null, locale));

		return "redirect:/";
	}
}
