package org.clubmaratoncaceres.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.clubmaratoncaceres.springboot.app.models.domain.Constantes;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Primary
public class UploadFileService implements IUploadFileService {

	@Override
	public Resource loadSocio(String filename) throws MalformedURLException {
		Path pathFoto = getPathSocio(filename);
		Resource recurso = new UrlResource(pathFoto.toUri());

		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}

		return recurso;
	}

	@Override
	public String copySocio(MultipartFile file, String idSocio) throws IOException {
		// Si no existe el directorio de uploads, se crea
		// Constantes.DIRECTORIO_UPLOADS = "uploads"
		//String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		String uniqueFileName = "foto_socio_" + idSocio + "_" + UUID.randomUUID().toString() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		Path dirUploadAbsolutePath = getPathSocio(uniqueFileName);
		
		// Obtener y guardar la imagen
		Files.copy(file.getInputStream(), dirUploadAbsolutePath, StandardCopyOption.REPLACE_EXISTING);
		
		return uniqueFileName;
	}

	@Override
	public boolean deleteSocio(String filename) {
		// Borrar su foto
		Path rootPath = getPathSocio(filename);
		File foto = rootPath.toFile();
		
		if (foto.exists() && foto.canRead()) {
			if (foto.delete()) {
				return true;
			}
		}
		
		return false;
	}

	public Path getPathSocio(String filename) {
		return Paths.get(Constantes.DIRECTORIO_UPLOADS_SOCIO).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAllSocio() {
		FileSystemUtils.deleteRecursively(Paths.get(Constantes.DIRECTORIO_UPLOADS_SOCIO).toFile());
		
	}

	@Override
	public void initSocio() throws IOException {
		Files.createDirectory(Paths.get(Constantes.DIRECTORIO_UPLOADS_SOCIO));
		
	}

}