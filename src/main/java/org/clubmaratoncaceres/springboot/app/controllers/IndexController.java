package org.clubmaratoncaceres.springboot.app.controllers;

import java.util.Locale;

import org.clubmaratoncaceres.springboot.app.models.domain.SessionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@Autowired 
	private SessionUsuario sessionUsuario;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value = {"/", "", "/index" })
	public String listar(Model model, Locale locale) {
		
		model.addAttribute("titulo", messageSource.getMessage("texto.index.titulo", null, locale));
		model.addAttribute("subtitulo", messageSource.getMessage("texto.index.inicio", null, locale));
		model.addAttribute("usuario", sessionUsuario.getUsuario());
		
		System.out.println(sessionUsuario.getUsuario().getSocio().toString());
		
		return "index";
	}
	
}