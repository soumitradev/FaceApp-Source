package org.catrobat.catroid.exceptions;

public class OutdatedVersionProjectException extends ProjectException {
    private static final long serialVersionUID = 1;

    public OutdatedVersionProjectException(String message) {
        super(message);
    }
}
