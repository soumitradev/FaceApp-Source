package com.parrot.arsdk.arutils;

public class ARUtilsException extends Exception {
    private ARUTILS_ERROR_ENUM error;

    public ARUtilsException() {
        this.error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
    }

    public ARUtilsException(ARUTILS_ERROR_ENUM error) {
        this.error = error;
    }

    public ARUtilsException(int error) {
        this.error = ARUTILS_ERROR_ENUM.getFromValue(error);
    }

    public ARUTILS_ERROR_ENUM getError() {
        return this.error;
    }

    public void setError(ARUTILS_ERROR_ENUM error) {
        this.error = error;
    }

    public String toString() {
        if (this.error == null) {
            return super.toString();
        }
        String str = new StringBuilder();
        str.append("ARUtilsException [");
        str.append(this.error.toString());
        str.append("]");
        return str.toString();
    }
}
