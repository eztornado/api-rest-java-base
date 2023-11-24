package com.eztornado.tornadocorebase.controller;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {

    // Método para obtener la dirección IP del cliente desde la solicitud HTTP
    protected String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    protected String getClientAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

}
