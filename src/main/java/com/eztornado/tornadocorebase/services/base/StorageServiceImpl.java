package com.eztornado.tornadocorebase.services.base;

import com.eztornado.tornadocorebase.exceptions.StorageException;
import com.eztornado.tornadocorebase.exceptions.TCFileNotFoundException;
import com.eztornado.tornadocorebase.models.File;
import com.eztornado.tornadocorebase.models.FileToken;
import com.eztornado.tornadocorebase.repositories.FileRepository;
import com.eztornado.tornadocorebase.repositories.FileTokenRepository;
import com.eztornado.tornadocorebase.services.base.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {

    private final FileRepository repository;
    private final FileTokenRepository tokenRepository;

    private String uploadsDir = "uploads/";

    private final Path rootLocation;

    // Constructor con @Autowired (opcional en las versiones más recientes de Spring)
    @Autowired
    public StorageServiceImpl(FileRepository repository, FileTokenRepository fileTokenRepository) {
        this.repository = repository;
        this.tokenRepository = fileTokenRepository;
        this.rootLocation = Paths.get(uploadsDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public File storeFile(MultipartFile file, boolean isPublic) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String mimeType = file.getContentType(); // Obtener el tipo MIME del archivo
        File metadata = new File();
        String filename = originalFilename;
        int counter = 0;

        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

            // Generar un nombre de archivo único
            while(Files.exists(this.rootLocation.resolve(filename))) {
                counter++;
                String fileExtension = StringUtils.getFilenameExtension(originalFilename);
                String baseName = StringUtils.stripFilenameExtension(originalFilename);
                filename = baseName + "(" + counter + ")" + (fileExtension != null ? "." + fileExtension : "");
            }

            Path destinationFile = this.rootLocation.resolve(filename);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            // Guardar metadatos en la base de datos
            metadata.setName(filename);
            metadata.setLocation(destinationFile.toString());
            metadata.setMimeType(mimeType);
            metadata.setPublic(isPublic);
            metadata = repository.save(metadata);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }

        return metadata;


    }

    @Override
    public Resource readFile(Long fileId) {
        File metadata = repository.findById(fileId)
                .orElseThrow(() -> new TCFileNotFoundException("File not found with id " + fileId));

        try {
            Path filePath = Paths.get(metadata.getLocation());
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new TCFileNotFoundException("Could not read file: " + filePath);
            }
        } catch (MalformedURLException ex) {
            throw new TCFileNotFoundException("Could not read file: " + metadata.getName(), ex);
        }
    }

    public boolean isTokenValid(String token, Long fileId, Long userId) {
        Optional<FileToken> fileTokenOptional = tokenRepository.findByTokenAndFileIdAndUserId(token, fileId, userId);

        if (!fileTokenOptional.isPresent()) {
            // No se encontró el token, por lo que es inválido
            return false;
        }

        FileToken fileToken = fileTokenOptional.get();
        // Comprueba si el token ha expirado
        if (fileToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            // El token ha expirado
            return false;
        }

        // El token es válido y no ha expirado
        return true;
    }
    @Override
    public boolean isFilePublic(Long fileId) {
        return false;
    }

    // Método auxiliar para determinar el tipo de contenido de un Resource
    public String determineContentType(Resource file) {
        String contentType = "application/octet-stream"; // Tipo MIME predeterminado
        try {
            contentType = Files.probeContentType(Paths.get(file.getURI()));
        } catch (IOException ex) {
            // Log the exception or handle it as appropriate
        }
        return contentType;
    }
}
