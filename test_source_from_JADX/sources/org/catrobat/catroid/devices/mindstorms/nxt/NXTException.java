package org.catrobat.catroid.devices.mindstorms.nxt;

import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.nxt.NXTError.ErrorCode;

public class NXTException extends MindstormsException {
    private final ErrorCode error;

    public ErrorCode getError() {
        return this.error;
    }

    public NXTException(ErrorCode error, CommandByte commandByte) {
        super(String.format("NXTException: Error caused by  '%s' on command '%s'", new Object[]{error.name(), commandByte.name()}));
        this.error = error;
    }

    public NXTException(String message) {
        super(message);
        this.error = null;
    }
}
