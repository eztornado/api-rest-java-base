package com.eztornado.tornadocorebase.controller;

import com.eztornado.tornadocorebase.exceptions.CustomMenuNotFoundException;
import com.eztornado.tornadocorebase.models.CustomMenu;
import com.eztornado.tornadocorebase.payload.response.ApiResponse;
import com.eztornado.tornadocorebase.security.services.UserDetailsImpl;
import com.eztornado.tornadocorebase.services.CustomMenuService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/statics")
public class StaticsController {

    private final CustomMenuService customMenuService;

    public StaticsController(CustomMenuService customMenuService) {
        this.customMenuService = customMenuService;
    }

    @GetMapping("/custom-menus/{name}")
    public ResponseEntity<ApiResponse<Object>> getMenu(@PathVariable("name") String name, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Optional<CustomMenu> customMenu = customMenuService.findByName(name);
        if(customMenu == null) {
            throw new CustomMenuNotFoundException(name);
        }
        CustomMenu customMenuObj = customMenu.get();
        return ResponseEntity.ok(new ApiResponse<>("success",customMenuObj.toJson(userDetails),""));
    }

}