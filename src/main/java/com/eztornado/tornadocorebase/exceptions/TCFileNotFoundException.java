package com.eztornado.tornadocorebase.exceptions;

public class TCFileNotFoundException extends RuntimeException {
    public TCFileNotFoundException(String message) {
        super(message);
    }

    public TCFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}