package org.clubmaratoncaceres.springboot.app;

import org.clubmaratoncaceres.springboot.app.models.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCmcApplication implements CommandLineRunner { 
	
	@Autowired
	private IUploadFileService uploadFileService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCmcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Borrar toda la informacion que pueda haber en el directorio uploads
		uploadFileService.deleteAllSocio();
				
		// Crear, si no existe, el directorio uploads
		uploadFileService.initSocio();
		
	}

}
