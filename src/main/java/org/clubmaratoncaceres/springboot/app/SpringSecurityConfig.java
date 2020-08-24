package org.clubmaratoncaceres.springboot.app;

import org.clubmaratoncaceres.springboot.app.auth.handler.LoginSuccessHandler;
import org.clubmaratoncaceres.springboot.app.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		 // JPA
		 builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**", "/scss/**", "/vendor/**").permitAll()
		.antMatchers("/restaurar-password").permitAll()
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/", "/index").hasAnyRole("USER")
		.antMatchers("/socio/ver/**").hasAnyRole("USER")
		.antMatchers("/socio/gestionar/**").hasAnyRole("USER")
		.antMatchers("/socio/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/socio/crear/**").permitAll()
		.anyRequest().authenticated()
		.and()
			.formLogin()
				.successHandler(loginSuccessHandler)
				.loginPage("/login")
				.permitAll()
		.and()
		.logout().permitAll()
		.and()
		//.exceptionHandling().accessDeniedPage("/error_403");
		;
	}
	
}