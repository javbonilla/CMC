package org.clubmaratoncaceres.springboot.app.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.clubmaratoncaceres.springboot.app.models.domain.SessionUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("passwordTemporalInterceptor")
public class PasswordTemporalInterceptor implements HandlerInterceptor {

	@Autowired
	private SessionUsuario sessionUsuario;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		if (sessionUsuario.getUsuario().getEsPasswordTemporal()) {
			response.sendRedirect(request.getContextPath().concat("/cambiar-password-temporal"));
			return false;
		} else {
			return true;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
	
}
