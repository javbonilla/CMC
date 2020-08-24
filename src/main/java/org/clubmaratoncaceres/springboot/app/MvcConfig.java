package org.clubmaratoncaceres.springboot.app;

import java.nio.file.Paths;
import java.util.Locale;

import org.clubmaratoncaceres.springboot.app.interceptors.PasswordTemporalInterceptor;
import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	@Qualifier("sessionInterceptor")
	private HandlerInterceptor sessionInterceptor;
	
	@Autowired
	@Qualifier("passwordTemporalInterceptor")
	private PasswordTemporalInterceptor passwordTemporalInterceptor;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		WebMvcConfigurer.super.addResourceHandlers(registry);

		// DIRECTORIO_UPLOADS = "uploads" String resourcePath =
		String resourcePath = Paths.get(Constantes.DIRECTORIO_UPLOADS).toAbsolutePath().toUri().toString();
		System.out.println(resourcePath);
		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor).excludePathPatterns("/login", "/logout", "/restaurar-password", "/socio/crear");
		registry.addInterceptor(passwordTemporalInterceptor).excludePathPatterns("/login", "/cambiar-password-temporal", "/restaurar-password", "/socio/crear");
		registry.addInterceptor(this.localeChangeInterceptor());
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es", "ES"));
		
		return localeResolver;
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang");
		
		return localeInterceptor;
	}	

}