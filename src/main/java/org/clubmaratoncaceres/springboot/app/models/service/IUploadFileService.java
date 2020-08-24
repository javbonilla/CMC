package org.clubmaratoncaceres.springboot.app.models.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	/**
	 * Cargar una imagen
	 * 
	 * @param filename: nombre del archivo
	 * @return: Resource: recurso cargado
	 */
	public Resource loadSocio(String filename) throws MalformedURLException;

	public String copySocio(MultipartFile file, String idSocio) throws IOException;

	public boolean deleteSocio(String filename);
	
	// Borrar todo el directorio
	public void deleteAllSocio();
	
	// Crear el directorio
	public void initSocio() throws IOException;
}
