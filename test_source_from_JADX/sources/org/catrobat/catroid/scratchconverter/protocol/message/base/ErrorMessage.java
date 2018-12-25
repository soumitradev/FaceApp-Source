package org.catrobat.catroid.scratchconverter.protocol.message.base;

public class ErrorMessage extends BaseMessage {
    private final String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
