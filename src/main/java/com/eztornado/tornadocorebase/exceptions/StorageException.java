package com.eztornado.tornadocorebase.exceptions;

import java.io.IOException;

public class StorageException extends RuntimeException {
    public StorageException(String message, IOException e) {
        super(message, e);
    }

    public StorageException(String message) {
        super(message);
    }
}
