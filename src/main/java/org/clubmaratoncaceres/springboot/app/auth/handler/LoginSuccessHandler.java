package org.clubmaratoncaceres.springboot.app.auth.handler;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.clubmaratoncaceres.springboot.app.models.dao.IUsuarioDAO;
import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.clubmaratoncaceres.springboot.app.models.domain.SessionUsuario;
import org.clubmaratoncaceres.springboot.app.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Autowired
	private SessionUsuario sesionUsuario;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// Obtenemos el locale para poder trabajar con traducciones
		Locale locale = localeResolver.resolveLocale(request);

		if (authentication != null) {
			// Lo primero es obtener el objeto usuario a partir de su username
			Usuario usuario = usuarioDAO.findByNif(authentication.getName());

			if (usuario != null && usuario.getId() > 0) {

				// Se guarda al usuario en sesion
				sesionUsuario.setUsuario(usuario);

				// Obtener el objeto con el que podremos registrar el mensaje flash
				// En este caso, no podemos inyectar en el metodo el RedirectAttributes como
				// hacemos en los controladores
				SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
				FlashMap flashMap = new FlashMap();

				flashMap.put(Constantes.COMUNICACION_VISTA_TIPO_EXITO, messageSource.getMessage("texto.login-success.hola", new Object[] {sesionUsuario.getUsuario().getSocio().getNombre()}, locale));
				
				System.out.println("password temporal: " + usuario.getEsPasswordTemporal());

				// Y se registra en el manager
				flashMapManager.saveOutputFlashMap(flashMap, request, response);

				super.onAuthenticationSuccess(request, response, authentication);
			}
		}

	}

}
