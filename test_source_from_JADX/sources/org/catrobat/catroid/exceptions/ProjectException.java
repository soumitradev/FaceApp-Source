package org.catrobat.catroid.exceptions;

public abstract class ProjectException extends Exception {
    private static final long serialVersionUID = 1;
    private final String uiErrorMessage;

    public ProjectException() {
        this.uiErrorMessage = "";
    }

    public ProjectException(String message) {
        super(message);
        this.uiErrorMessage = message;
    }

    public String getUiErrorMessage() {
        return this.uiErrorMessage;
    }
}
