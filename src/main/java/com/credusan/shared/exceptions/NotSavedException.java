package com.credusan.shared.exceptions;

import javax.validation.ValidationException;

public class NotSavedException extends ValidationException {
    private static final String DESCRIPTION = "No se pudo registrar la información";


    public NotSavedException() {
        super(DESCRIPTION);
    }

    public NotSavedException(String table) {
        super(DESCRIPTION + " en " + table);
    }

}