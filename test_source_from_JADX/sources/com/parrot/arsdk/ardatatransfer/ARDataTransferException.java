package com.parrot.arsdk.ardatatransfer;

public class ARDataTransferException extends Exception {
    private ARDATATRANSFER_ERROR_ENUM error;

    public ARDataTransferException() {
        this.error = ARDATATRANSFER_ERROR_ENUM.ARDATATRANSFER_ERROR;
    }

    public ARDataTransferException(ARDATATRANSFER_ERROR_ENUM error) {
        this.error = error;
    }

    public ARDataTransferException(int error) {
        this.error = ARDATATRANSFER_ERROR_ENUM.getFromValue(error);
    }

    public ARDATATRANSFER_ERROR_ENUM getError() {
        return this.error;
    }

    public void setError(ARDATATRANSFER_ERROR_ENUM error) {
        this.error = error;
    }

    public String toString() {
        if (this.error == null) {
            return super.toString();
        }
        String str = new StringBuilder();
        str.append("ARDataTransferException [");
        str.append(this.error.toString());
        str.append("]");
        return str.toString();
    }
}
