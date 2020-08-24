package org.clubmaratoncaceres.springboot.app.controllers;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.clubmaratoncaceres.springboot.app.models.domain.SessionUsuario;
import org.clubmaratoncaceres.springboot.app.models.entity.Parametro;
import org.clubmaratoncaceres.springboot.app.models.entity.Socio;
import org.clubmaratoncaceres.springboot.app.models.service.IServiceDAO;
import org.clubmaratoncaceres.springboot.app.models.service.IUploadFileService;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("socio")
@RequestMapping("/socio")
public class SocioController {

	@Autowired
	private SessionUsuario sessionUsuario;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IServiceDAO serviceDAO;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@ModelAttribute("paramSexo")
	public Map<String, String> cargarSexosMap() {
		Map<String, String> sexosMap = new HashMap<>();
		
		for (Parametro param : serviceDAO.findByParametro(Constantes.PARAMETRO_SEXO)) {
			sexosMap.put(param.getCodigo(), param.getValor());
		}

		return sexosMap;
	}
	
	@ModelAttribute("paramRol")
	public Map<String, String> cargarRolesMap() {
		
		Map<String, String> map = new HashMap<>();
		
		for (Parametro param : serviceDAO.findByParametro(Constantes.PARAMETRO_ROL)) {
			map.put(param.getCodigo(), param.getValor());
		}

		return map;
	}
	
	@ModelAttribute("paramCuota")
	public Map<String, String> cargarCuotasMap() {
		Map<String, String> cuotasMap = new HashMap<>();
		
		for (Parametro param : serviceDAO.findByParametro(Constantes.PARAMETRO_CUOTA)) {
			cuotasMap.put(param.getCodigo(), param.getValor());
		}

		return cuotasMap;
	}
	
	@ModelAttribute("paramTalla")
	public Map<String, String> cargarTallasMap() {
		Map<String, String> map = new HashMap<>();
		
		for (Parametro param : serviceDAO.findByParametro(Constantes.PARAMETRO_TALLA)) {
			map.put(param.getCodigo(), param.getValor());
		}

		return map;
	}
	
	@GetMapping(value = {"/ver/{id}" })
	public String ver(@PathVariable(value = "id") String id, Model model, RedirectAttributes flash, Locale locale) {
		
		try {
			// Lo primero es tratar de reconocer el Id como Long. Si no lo es, se redirige
			// al listado.
			Long idNum = Long.parseLong(id);

			if (idNum > 0) {
				// Obtener el cliente a consultar
				//Cliente cliente = clienteService.findOne(idNum);
				Socio socio = serviceDAO.findOneSocio(Long.parseLong(id));
				
				if (socio != null) {
					// Un socio solo se puede consultar a si mismo o a otros socios si tiene el rol ADMIN
					if (socio.getId() == sessionUsuario.getUsuario().getSocio().getId() ||
						sessionUsuario.getUsuario().usuarioTieneRol(Constantes.PARAMETRO_ROL_ADMIN)) {
						// Pasamos el cliente a la vista junto con su foto.
						model.addAttribute("titulo", messageSource.getMessage("texto.socio.ver.titulo", new Object[] {sessionUsuario.getUsuario().getSocio().getNombre() + " " + sessionUsuario.getUsuario().getSocio().getApellidos()}, locale));
						model.addAttribute("subtitulo", messageSource.getMessage("texto.index.area-personal.mi-perfil", null, locale));
						model.addAttribute("socio", socio);
						model.addAttribute("usuario", sessionUsuario.getUsuario());
						
						return "socio/ver";
					} else {
						flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.general.acceso-no-autorizado", null, locale));
						return "redirect:/";
					}
				} else {
					flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.socio.mensaje-no-encontrado", new Object [] {id}, locale));
					return "redirect:/";
				}

			} else {
				flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.general.identificador-no-valido", new Object[] {id}, locale));
				return "redirect:/";
			}

		} catch (NumberFormatException e) {
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.general.identificador-no-numerico", new Object[] {id}, locale));
			return "redirect:/";
		}

	}
	
	@GetMapping("/crear")
	public String crear(Model model, Locale locale) {
		Socio socio = new Socio();

		model.addAttribute("titulo", messageSource.getMessage("texto.socio.crear.titulo", null, locale));
		model.addAttribute("subtitulo", messageSource.getMessage("texto.socio.crear.subtitulo", null, locale));
		model.addAttribute("socio", socio);
		model.addAttribute("noMostrarPie", true);

		return "socio/crear";
	}
	
	@GetMapping("/gestionar/{id}")
	public String editar(@PathVariable(value = "id") String id, Model model, RedirectAttributes flash, Locale locale) {

		try {
			// Lo primero es tratar de reconocer el Id como Long. Si no lo es, se redirige
			// al listado.
			Long idNum = Long.parseLong(id);

			Socio socio = null;

			if (idNum > 0) {
				if ((socio = serviceDAO.fetchSocioByIdWithInscripciones(idNum)) != null) {
					if (socio.getId() == sessionUsuario.getUsuario().getSocio().getId() ||
						sessionUsuario.getUsuario().usuarioTieneRol("ROLE_ADMIN")) {
						// Pasamos el cliente a la vista junto con su foto.
						model.addAttribute("titulo", messageSource.getMessage("texto.socio.editar.titulo", new Object[] {sessionUsuario.getUsuario().getSocio().getNombre() + " " + sessionUsuario.getUsuario().getSocio().getApellidos()}, locale));
						model.addAttribute("subtitulo", messageSource.getMessage("texto.socio.editar.subtitulo", null, locale));
						model.addAttribute("socio", socio);
						model.addAttribute("cuentaeditar", socio.enmascararCuenta());
						model.addAttribute("cuentaeditarvalidado", "0");
						model.addAttribute("usuario", sessionUsuario.getUsuario());
						
						// Rol del usuario: 1 USER, 2 MANAGER, 3 ADMIN
						if (socio.getUsuario().usuarioTieneRol(Constantes.PARAMETRO_ROL_ADMIN)) {
							model.addAttribute("rolusuario", Constantes.PARAMETRO_ROL_ADMIN);
							model.addAttribute("rolusuarioAnt", Constantes.PARAMETRO_ROL_ADMIN_NUM);
						} else if (socio.getUsuario().usuarioTieneRol(Constantes.PARAMETRO_ROL_MANAGER)) {
							model.addAttribute("rolusuario", Constantes.PARAMETRO_ROL_MANAGER);
							model.addAttribute("rolusuarioAnt", Constantes.PARAMETRO_ROL_MANAGER_NUM);
						} else {
							model.addAttribute("rolusuario", Constantes.PARAMETRO_ROL_USER);
							model.addAttribute("rolusuarioAnt", Constantes.PARAMETRO_ROL_USER_NUM);
						}
							
						return "socio/gestionar";
					} else {
						flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.general.acceso-no-autorizado", null, locale));
						return "redirect:/";
					}
				} else {
					flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.socio.mensaje-no-encontrado", new Object [] {id}, locale));
					return "redirect:/";
				}

			} else {
				flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.general.identificador-no-valido", new Object[] {id}, locale));
				return "redirect:/";
			}

		} catch (NumberFormatException e) {
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR, messageSource.getMessage("texto.general.identificador-no-numerico", new Object[] {id}, locale));
			return "redirect:/";
		}
	}
	
	@PostMapping("/gestionar")
	public String guardar(@Valid @ModelAttribute("socio") Socio socio, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, @RequestParam(name = "cuentaeditar", required = false) String cuentaEditar,
			@RequestParam(name = "rol", required = false) String rolUsuario, @RequestParam(name = "rolusuario", required = false) String rolUsuarioAnt, 
			SessionStatus status, RedirectAttributes flash, Locale locale) {
		
		// Validacion del IBAN, lo primero
		if (cuentaEditar != null && !cuentaEditar.isBlank() && !cuentaEditar.equals(socio.enmascararCuenta())) {
			try {
				cuentaEditar = cuentaEditar.replaceAll("\\s+", "");
			     IbanUtil.validate(cuentaEditar);
			     model.addAttribute("cuentaeditarvalidado", "1");
			     // valid
			     socio.setCuentaBancaria(cuentaEditar);
			 } catch (IbanFormatException |
			          InvalidCheckDigitException |
			          UnsupportedCountryException e) {
			     result.reject("");
			     model.addAttribute("cuentaeditar", cuentaEditar);
			     model.addAttribute("cuentaeditarvalidado", "2");
			 }
		} else {
			if (cuentaEditar == null) {
				// No ha habido cambio de cuenta bancaria
				cuentaEditar = socio.enmascararCuenta();
				model.addAttribute("cuentaeditarvalidado", "0");
			}
		}
		
		// Cambio de rol
		System.out.println("rol recibido: " + rolUsuario);
		if (rolUsuario != rolUsuarioAnt) {
			// Lo primero es borrar los roles anteriores del usuario
			
			if (rolUsuario == null || rolUsuario.isBlank()) {
				result.reject("");
			} else {
				// Se comprueba el nuevo rol asignado al usuario
				if (rolUsuario.equals(Constantes.PARAMETRO_ROL_USER_NUM)) {
					socio.getUsuario().deleteRoleByAuthority(Constantes.PARAMETRO_ROL_MANAGER);
					socio.getUsuario().deleteRoleByAuthority(Constantes.PARAMETRO_ROL_ADMIN);
				}
				
				if (rolUsuario.equals(Constantes.PARAMETRO_ROL_MANAGER_NUM)) {
					socio.getUsuario().addRoleByAuthority(Constantes.PARAMETRO_ROL_MANAGER);
					socio.getUsuario().deleteRoleByAuthority(Constantes.PARAMETRO_ROL_ADMIN);
				}
				
				if (rolUsuario.equals(Constantes.PARAMETRO_ROL_ADMIN_NUM)) {
					socio.getUsuario().addRoleByAuthority(Constantes.PARAMETRO_ROL_MANAGER);
					socio.getUsuario().addRoleByAuthority(Constantes.PARAMETRO_ROL_ADMIN);
				}
			}
		}

		// Manejar si el formulario es válido o no
		if (result.hasErrors()) {

			// Pasamos el cliente a la vista junto con su foto.
			model.addAttribute("titulo", messageSource.getMessage("texto.socio.editar.titulo", new Object[] {sessionUsuario.getUsuario().getSocio().getNombre() + " " + sessionUsuario.getUsuario().getSocio().getApellidos()}, locale));
			model.addAttribute("subtitulo", messageSource.getMessage("texto.socio.editar.subtitulo", null, locale));
			model.addAttribute("socio", socio);
			model.addAttribute("usuario", sessionUsuario.getUsuario());
			model.addAttribute("cuentaeditar", cuentaEditar);

			model.addAttribute("submitted", true);

			// Y volvemos al mismo formulario
			return "socio/gestionar";
		}

		try {
			
			// Tratamiento de la foto
			if (!foto.isEmpty()) {
				
				// Si tenia una foto previa, se elimina
				if (socio.getId() != null && socio.getId() > 0 && socio.getFoto() != null && !socio.getFoto().isBlank()) {
					// Borrar su foto
					uploadFileService.deleteSocio(socio.getFoto());
				}
				
				// Guardar la foto nueva
				String uniqueFileName = uploadFileService.copySocio(foto, socio.getId().toString());
				socio.setFoto(uniqueFileName);
				flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_INFO, " Imagen de perfil actualizada correctamente: '" + uniqueFileName + "'");
			}
			
			// Se actualiza el socio y, si es necesario, la sesion:
			socio.setIdUserMo(sessionUsuario.getUsuario().getId());
			serviceDAO.saveSocio(socio);
			if (socio.getId() == sessionUsuario.getUsuario().getSocio().getId()) {
				sessionUsuario.getUsuario().setSocio(socio);
			}
			
			System.out.println("cuenta editar: " + cuentaEditar);
			
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_EXITO, " Datos actualizados correctamente");

			status.setComplete();
		} catch (Exception e) {
			flash.addFlashAttribute(Constantes.COMUNICACION_VISTA_TIPO_ERROR,
					" Ocurrió un error al guardar el socio. Por favor, inténtelo de nuevo más tarde.");
			
			e.printStackTrace();
		}

		return "redirect:/socio/ver/" + socio.getId();		
	}
}