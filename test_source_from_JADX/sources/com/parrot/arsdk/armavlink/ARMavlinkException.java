package com.parrot.arsdk.armavlink;

public class ARMavlinkException extends Exception {
    private ARMAVLINK_ERROR_ENUM error;

    public ARMavlinkException() {
        this.error = ARMAVLINK_ERROR_ENUM.ARMAVLINK_ERROR;
    }

    public ARMavlinkException(ARMAVLINK_ERROR_ENUM error) {
        this.error = error;
    }

    public ARMavlinkException(int error) {
        this.error = ARMAVLINK_ERROR_ENUM.getFromValue(error);
    }

    public ARMAVLINK_ERROR_ENUM getError() {
        return this.error;
    }

    public void setError(ARMAVLINK_ERROR_ENUM error) {
        this.error = error;
    }

    public String toString() {
        if (this.error == null) {
            return super.toString();
        }
        String str = new StringBuilder();
        str.append("ARMavlinkException [");
        str.append(this.error.toString());
        str.append("]");
        return str.toString();
    }
}
