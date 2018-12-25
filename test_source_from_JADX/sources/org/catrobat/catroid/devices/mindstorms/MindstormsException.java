package org.catrobat.catroid.devices.mindstorms;

public class MindstormsException extends RuntimeException {
    private final Exception innerException;

    public MindstormsException(String message) {
        this(null, message);
    }

    public MindstormsException(Exception innerException, String message) {
        super(message);
        this.innerException = innerException;
    }

    public Exception getInnerException() {
        return this.innerException;
    }
}
