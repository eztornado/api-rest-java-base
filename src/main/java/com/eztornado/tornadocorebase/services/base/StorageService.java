package com.eztornado.tornadocorebase.services.base;


import com.eztornado.tornadocorebase.models.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    File storeFile(MultipartFile file, boolean isPublic);

    Resource readFile(Long fileId);

    String determineContentType(Resource file);

    boolean isTokenValid(String token, Long fileId, Long userId);

    boolean isFilePublic(Long fileId);
}