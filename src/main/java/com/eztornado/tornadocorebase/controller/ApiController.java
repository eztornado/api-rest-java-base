package com.eztornado.tornadocorebase.controller;

import com.eztornado.tornadocorebase.payload.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/ping")
    public ApiResponse<String> ping() {
        String data = "TornadoCoreBase Java Beta";
        return new ApiResponse<>("success", data, "");
    }
}
