package org.catrobat.catroid.scratchconverter.protocol;

import org.catrobat.catroid.scratchconverter.Client.ConvertCallback;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;

public interface MessageListener {
    int getNumberOfJobsInProgress();

    boolean isJobInProgress(long j);

    void onUserCanceledConversion(long j);

    DownloadCallback restoreJobIfRunning(Job job, ConvertCallback convertCallback);

    boolean scheduleJob(Job job, boolean z, ConvertCallback convertCallback);

    void setBaseMessageHandler(BaseMessageHandler baseMessageHandler);
}
