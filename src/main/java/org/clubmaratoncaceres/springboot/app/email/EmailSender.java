package org.clubmaratoncaceres.springboot.app.email;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.clubmaratoncaceres.springboot.app.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Component
public class EmailSender {

	private InternetAddress addressFrom;

	private InternetAddress replyTo;
	
	private Locale locale;
	
	//private JavaMailSenderImpl mailSender;
	
	private Session session;
	
	private SpringTemplateEngine templateEngine;

	@Autowired
	private MessageSource messageSource;

	public EmailSender() {
		
	}

	private void configurarParametrosEmail() throws UnsupportedEncodingException, NoSuchMessageException {
		this.emailTemplateEngine();
		this.locale = LocaleContextHolder.getLocale();
		
		this.addressFrom = new InternetAddress(Constantes.EMAIL_FROM, messageSource.getMessage("texto.email.email-from", null, this.locale));
		this.replyTo = new InternetAddress(Constantes.EMAIL_REPLY_TO, Constantes.EMAIL_CMC);
		
		// creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constantes.EMAIL_FROM, Constantes.EMAIL_PASSWORD);
            }
        };
        
        Properties properties = new Properties();
        properties.put("mail.smtp.host", Constantes.EMAIL_HOST);
        properties.put("mail.smtp.port", Constantes.EMAIL_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true");
 
        this.session = Session.getInstance(properties, auth);
	}
	
	private void emailTemplateEngine() {
        templateEngine = new SpringTemplateEngine();
        // Resolver for TEXT emails
        templateEngine.addTemplateResolver(textTemplateResolver());
        // Resolver for HTML emails (except the editable one)
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        // Resolver for HTML editable emails (which will be treated as a String)
        templateEngine.addTemplateResolver(stringTemplateResolver());
        // Message source, internationalization specific to emails
        templateEngine.setTemplateEngineMessageSource(messageSource);
    }
	
	private ITemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(1));
        //templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
        templateResolver.setPrefix("/templates/mail/text/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(2));
        //templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/templates/mail/html/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }

    private ITemplateResolver stringTemplateResolver() {
        final StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(3));
        // No resolvable pattern, will simply process as a String template everything not previously matched
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }

	public void enviarEmailRestaurarPassword(Usuario usuario, String password) throws Exception {

		this.configurarParametrosEmail();
		
		MimeMessage message = new MimeMessage(this.session);

		//message.setHeader("Content-Type", "text/plain; charset=UTF-8");
		message.setRecipients(Message.RecipientType.TO, new InternetAddress(usuario.getSocio().getEmail(), usuario.getSocio().getNombre() + " " + usuario.getSocio().getApellidos()).toString());
		message.setFrom(this.addressFrom);
		message.setReplyTo(new InternetAddress[] {this.replyTo});
		message.setSubject(messageSource.getMessage("texto.email.recuperar-password.subject", null, this.locale));
		
		//String mensaje = messageSource.getMessage("texto.email.recuperar-password.text", new Object[] {usuario.getSocio().getNombre(), usuario.getSocio().getNif(), password}, locale);
		//System.out.println("email a enviar: " + mensaje);
		
		//message.setContent(mensaje, "text/html");
		
		// Voy a tratar de enviar el e-mail desde una plantilla de Thymeleaf
		final Context ctx = new Context(locale);
	    ctx.setVariable("nombre", usuario.getSocio().getNombre());
	    ctx.setVariable("usuario", usuario.getSocio().getNif());
	    ctx.setVariable("password", password);

	    // Create the HTML body using Thymeleaf
	    final String htmlContent = this.templateEngine.process("restaurar-password.html", ctx);
	    System.out.println("Mensaje a enviar: " + htmlContent);
	    message.setContent(htmlContent, "text/html");

		Transport.send(message);

	}
}
