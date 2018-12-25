package org.catrobat.catroid.scratchconverter.protocol.message.base;

import java.util.Arrays;
import org.catrobat.catroid.scratchconverter.protocol.Job;

public class InfoMessage extends BaseMessage {
    private final float catrobatLanguageVersion;
    private final Job[] jobList;

    public InfoMessage(float catrobatLanguageVersion, Job[] jobList) {
        this.catrobatLanguageVersion = catrobatLanguageVersion;
        this.jobList = jobList != null ? (Job[]) Arrays.copyOf(jobList, jobList.length) : new Job[0];
    }

    public float getCatrobatLanguageVersion() {
        return this.catrobatLanguageVersion;
    }

    public Job[] getJobList() {
        return (Job[]) Arrays.copyOf(this.jobList, this.jobList.length);
    }
}
