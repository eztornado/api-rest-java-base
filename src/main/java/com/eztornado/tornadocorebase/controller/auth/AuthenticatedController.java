package com.eztornado.tornadocorebase.controller.auth;


import com.eztornado.tornadocorebase.payload.response.ApiResponse;
import com.eztornado.tornadocorebase.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticatedController {

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Object>> getAuthenticatedUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // El @AuthenticationPrincipal inyecta directamente el usuario autenticado
        return ResponseEntity.ok(new ApiResponse<>("success", userDetails, ""));
    }
}