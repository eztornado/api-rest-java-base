package com.eztornado.tornadocorebase.config;

import com.eztornado.tornadocorebase.models.TCException;
import com.eztornado.tornadocorebase.repositories.TCExceptionRepository;
import com.eztornado.tornadocorebase.payload.response.ApiErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private TCExceptionRepository tcExceptionRepository;

    @Value("${tornadocore.environment}")
    private String ENVIRONMENT;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {

        ApiErrorResponse response = new ApiErrorResponse();
        if(ENVIRONMENT.equals("development")) {
            response.setTimestamp(LocalDateTime.now());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError("Internal Server error");
            response.setMessage(ex.getMessage());
            response.setFile(ex.getStackTrace()[0].getFileName());
            response.setMethod(ex.getStackTrace()[0].getMethodName());
            response.setLine(ex.getStackTrace()[0].getLineNumber());
            response.setTrace(ex.getStackTrace());
            response.setCause(ex.getCause());
            response.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());

        } else {
            return new ResponseEntity<>("{\"error\":\"Internal server error\"}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Volcado en BD
        this.ExceptionToTCException(ex, request);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void ExceptionToTCException(Exception e, WebRequest request) {
        TCException tcException = new TCException();
        tcException.setMethod(e.getStackTrace()[0].getMethodName());
        tcException.setFile(e.getStackTrace()[0].getFileName());
        tcException.setLine(e.getStackTrace()[0].getLineNumber());
        tcException.setCause(e.getCause());
        tcException.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
        tcExceptionRepository.save(tcException);
    }
}