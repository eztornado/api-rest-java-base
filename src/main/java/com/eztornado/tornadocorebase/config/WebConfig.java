package com.eztornado.tornadocorebase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Si agregas tus propios convertidores sin añadir los predeterminados,
        // puedes excluir los convertidores necesarios para formularios URL-encoded
        // Asegúrate de incluir el convertidor para formularios URL-encoded
        converters.add(new FormHttpMessageConverter());
    }
}