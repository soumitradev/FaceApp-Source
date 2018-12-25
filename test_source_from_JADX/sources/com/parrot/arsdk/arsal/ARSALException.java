package com.parrot.arsdk.arsal;

public class ARSALException extends Exception {
    private ARSAL_ERROR_ENUM error;

    public ARSALException() {
        this.error = ARSAL_ERROR_ENUM.ARSAL_ERROR;
    }

    public ARSALException(ARSAL_ERROR_ENUM error) {
        this.error = error;
    }

    public ARSALException(int error) {
        this.error = ARSAL_ERROR_ENUM.getFromValue(error);
    }

    public ARSAL_ERROR_ENUM getError() {
        return this.error;
    }

    public void setError(ARSAL_ERROR_ENUM error) {
        this.error = error;
    }

    public String toString() {
        if (this.error == null) {
            return super.toString();
        }
        String str = new StringBuilder();
        str.append("ARSALException [");
        str.append(this.error.toString());
        str.append("]");
        return str.toString();
    }
}
