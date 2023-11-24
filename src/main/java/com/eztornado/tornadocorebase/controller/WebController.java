package com.eztornado.tornadocorebase.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class WebController {

    @Value("${tornadocore.environment}")
    private String environment;

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/error")
    public String error(Model model) {
        return "not-found";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        if ("development".equals(environment)) {
            modelAndView = new ModelAndView("exception");
            modelAndView.addObject("errorMessage", "Something went wrong!");
            modelAndView.addObject("errorStackTrace", ex.getMessage());

            // Obtener la traza de pila
            StackTraceElement[] stackTrace = ex.getStackTrace();
            if (stackTrace.length > 0) {
                // Obtener el primer elemento de la traza (que es donde se originó la excepción)
                StackTraceElement firstStackTraceElement = stackTrace[0];
                modelAndView.addObject("errorFile", firstStackTraceElement.getFileName()); // Nombre del archivo
                modelAndView.addObject("errorLine", firstStackTraceElement.getLineNumber()); // Número de línea
                modelAndView.addObject("errorMethod", firstStackTraceElement.getMethodName()); // Nombre del método
                modelAndView.addObject("errorMessage", ex.getMessage());
            }
        } else {
            // En el entorno de desarrollo, muestra una vista específica
            modelAndView.setViewName("error-500");
        }

        return modelAndView;
    }
}
