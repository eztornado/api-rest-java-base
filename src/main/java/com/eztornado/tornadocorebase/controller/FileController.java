package com.eztornado.tornadocorebase.controller;

import com.eztornado.tornadocorebase.models.File;
import com.eztornado.tornadocorebase.payload.response.ApiResponse;
import com.eztornado.tornadocorebase.security.services.UserDetailsImpl;
import com.eztornado.tornadocorebase.services.base.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/files")
public class FileController extends BaseController {
    private final StorageService storageService;

    // Constructor con @Autowired (opcional en las versiones más recientes de Spring)
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<Object>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "isPublic", defaultValue = "true") boolean isPublic) {
        File fileSaved = storageService.storeFile(file, isPublic);
        return ResponseEntity.ok(new ApiResponse<>("success",fileSaved,"File uploaded successfully!"));
    }

    @GetMapping("/public-files/{fileId}")
    public ResponseEntity<Resource> serveFile(@PathVariable Long fileId) {
        // Suponiendo que tienes un método que verifica si el archivo es público
        if (!storageService.isFilePublic(fileId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Resource file = storageService.readFile(fileId);
        String contentType = storageService.determineContentType(file);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/private-files/{fileId}")
    public ResponseEntity<Resource> servePrivateFile(@PathVariable Long fileId, @RequestParam("token") String token, Authentication authentication) {
        UserDetailsImpl currentUser = (UserDetailsImpl) authentication.getPrincipal();
        // Comprueba si el token es válido y si no ha caducado
        if (storageService.isTokenValid(token, fileId, currentUser.getId())) {
            Resource file = storageService.readFile(fileId);
            String contentType = storageService.determineContentType(file);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }


}