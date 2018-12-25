package com.parrot.arsdk.arupdater;

public class ARUpdaterException extends Exception {
    private ARUPDATER_ERROR_ENUM error;

    public ARUpdaterException() {
        this.error = ARUPDATER_ERROR_ENUM.ARUPDATER_ERROR;
    }

    public ARUpdaterException(ARUPDATER_ERROR_ENUM error) {
        this.error = error;
    }

    public ARUpdaterException(int error) {
        this.error = ARUPDATER_ERROR_ENUM.getFromValue(error);
    }

    public ARUPDATER_ERROR_ENUM getError() {
        return this.error;
    }

    public void setError(ARUPDATER_ERROR_ENUM error) {
        this.error = error;
    }

    public String toString() {
        if (this.error == null) {
            return super.toString();
        }
        String str = new StringBuilder();
        str.append("ARUpdaterException [");
        str.append(this.error.toString());
        str.append("]");
        return str.toString();
    }
}
