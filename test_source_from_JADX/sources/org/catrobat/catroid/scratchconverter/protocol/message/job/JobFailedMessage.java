package org.catrobat.catroid.scratchconverter.protocol.message.job;

public class JobFailedMessage extends JobMessage {
    private final String message;

    public JobFailedMessage(long jobID, String message) {
        super(jobID);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
