package com.eztornado.tornadocorebase.exceptions;


public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException(Long id) {
        super("Session not found with id: " + id);
    }
}

