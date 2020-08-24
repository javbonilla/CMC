package org.clubmaratoncaceres.springboot.app.controllers;

import java.util.Locale;

import javax.validation.Valid;

import org.clubmaratoncaceres.springboot.app.email.EmailSender;
import org.clubmaratoncaceres.springboot.app.models.dao.IUsuarioDAO;
import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.clubmaratoncaceres.springboot.app.models.domain.PasswordTemporalGenerador;
import org.clubmaratoncaceres.springboot.app.models.domain.RestaurarPassword;
import org.clubmaratoncaceres.springboot.app.models.entity.Usuario;
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
public class RestaurarPasswordController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Autowired
	private PasswordTemporalGenerador passwordTemporalGenerador;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping(value = { "/restaurar-password" })
	public String cambiarPassword(Model model, RedirectAttributes flash, Locale locale) {

		model.addAttribute("titulo", messageSource.getMessage("texto.restaurar-password.titulo", null, locale));
		model.addAttribute("noMostrarPie", true);
		model.addAttribute("restaurarPassword", new RestaurarPassword());

		return "restaurar-password";
	}

	@PostMapping("/restaurar-password")
	public String guardar(@Valid @ModelAttribute("restaurarPassword") RestaurarPassword restaurarPassword,
			BindingResult result, Model model, RedirectAttributes flash, Locale locale) {
		
		boolean validado = true;

		// Manejar si el formulario es válido o no
		if (result.hasErrors()) {
			validado = false;
		}
		
		// Se busca al usuario a traves de su DNI. Si no se encuentra, se devuelve error
		Usuario usuario = usuarioDAO.findByNif(restaurarPassword.getNif());
		if (usuario == null) {
			result.rejectValue("nif", "texto.restaurar-password.nif-no-encontrado");
			validado = false;
		}
		
		if (!validado) {
			model.addAttribute("titulo", messageSource.getMessage("texto.restaurar-password.titulo", null, locale));
			model.addAttribute("noMostrarPie", true);
			model.addAttribute("submitted", true);

			// Y volvemos al mismo formulario
			return "restaurar-password";
		}
		
		// Se genera la password temporal y se envia por email
		String passwordTemporal = passwordTemporalGenerador.generarPasswordTemporal();
		System.out.println("passwordTemporal: " + passwordTemporal);
		try {
			emailSender.enviarEmailRestaurarPassword(usuario, passwordTemporal);
			usuario.setPassword(passwordEncoder.encode(passwordTemporal));
			usuario.setEsPasswordTemporal(true);
			usuarioDAO.save(usuario);
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_EXITO, messageSource.getMessage("texto.restaurar-password.exito", new Object[] {usuario.getSocio().getEmail()}, locale));
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.general.error-email", null, locale));
		}

		return "redirect:/login";
	}

}
