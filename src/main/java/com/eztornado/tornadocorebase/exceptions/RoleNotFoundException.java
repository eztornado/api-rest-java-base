package com.eztornado.tornadocorebase.exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super("Role not found with id: " + id);
    }

    public RoleNotFoundException(String name) {
        super("Role with name: " + name + " not found");
    }
}
