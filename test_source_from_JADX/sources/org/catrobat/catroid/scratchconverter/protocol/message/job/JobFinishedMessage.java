package org.catrobat.catroid.scratchconverter.protocol.message.job;

import java.util.Date;

public class JobFinishedMessage extends JobMessage {
    private final Date cachedDate;
    private final String downloadURL;

    public JobFinishedMessage(long jobID, String downloadURL, Date cachedDate) {
        super(jobID);
        this.downloadURL = downloadURL;
        this.cachedDate = cachedDate;
    }

    public String getDownloadURL() {
        return this.downloadURL;
    }

    public Date getCachedDate() {
        return this.cachedDate;
    }
}
