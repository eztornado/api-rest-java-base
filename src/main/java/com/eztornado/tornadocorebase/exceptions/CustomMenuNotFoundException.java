package com.eztornado.tornadocorebase.exceptions;

public class CustomMenuNotFoundException extends RuntimeException {

    public CustomMenuNotFoundException(Long id) {
        super("CustomMenu not found with id: " + id);
    }

    public CustomMenuNotFoundException(String name) {
        super("CustomMenu with name: " + name + " not found");
    }
}
