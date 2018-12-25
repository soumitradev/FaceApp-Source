package org.catrobat.catroid.scratchconverter.protocol.message.job;

public class JobProgressMessage extends JobMessage {
    private final short progress;

    public JobProgressMessage(long jobID, short progress) {
        super(jobID);
        this.progress = progress;
    }

    public short getProgress() {
        return this.progress;
    }
}
