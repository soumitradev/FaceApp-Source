package org.catrobat.catroid.scratchconverter.protocol.message.job;

public class JobAlreadyRunningMessage extends JobMessage {
    private final String jobImageURL;
    private final String jobTitle;

    public JobAlreadyRunningMessage(long jobID, String jobTitle, String jobImageURL) {
        super(jobID);
        this.jobTitle = jobTitle;
        this.jobImageURL = jobImageURL;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public String getJobImageURL() {
        return this.jobImageURL;
    }
}
