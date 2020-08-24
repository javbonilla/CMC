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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CambiarPasswordController {
	
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
	
	@GetMapping(value = {"/cambiar-password" })
	public String cambiarPassword(Model model, RedirectAttributes flash, Locale locale) {
		
		model.addAttribute("titulo", messageSource.getMessage("texto.cambiar-password.titulo", null, locale));
		model.addAttribute("subtitulo", messageSource.getMessage("texto.index.cambiar-password", null, locale));
		model.addAttribute("usuario", sessionUsuario.getUsuario());
		model.addAttribute("cambiarPassword", new CambiarPassword());
		
		return "cambiar-password";
	}
	
	@PostMapping("/cambiar-password")
	public String guardar(@Valid @ModelAttribute("cambiarPassword") CambiarPassword cambiarPassword, BindingResult result, Model model,
			RedirectAttributes flash, Locale locale) {

		// Validar el cambio de password
		validador.validate(cambiarPassword, result);
		
		// Manejar si el formulario es válido o no
		if (result.hasErrors()) {

			model.addAttribute("titulo", messageSource.getMessage("texto.cambiar-password.titulo", null, locale));
			model.addAttribute("subtitulo", messageSource.getMessage("texto.index.cambiar-password", null, locale));
			model.addAttribute("usuario", sessionUsuario.getUsuario());
			model.addAttribute("submitted", true);

			// Y volvemos al mismo formulario
			return "cambiar-password";
		}
		
		// Modificar la password del usuario
		
		sessionUsuario.getUsuario().setPassword(passwordEncoder.encode(cambiarPassword.getPasswordNueva()));
		sessionUsuario.getUsuario().setEsPasswordTemporal(false);
		usuarioDAO.save(sessionUsuario.getUsuario());
		flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_EXITO, messageSource.getMessage("texto.cambiar-password.mensaje-cambio-ok", null, locale));

		return "redirect:/";
	}
}
