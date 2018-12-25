package org.catrobat.catroid.ui.scratchconverter;

import android.support.annotation.NonNull;
import org.catrobat.catroid.scratchconverter.protocol.Job;

public interface JobViewListener {
    void onJobFailed(Job job);

    void onJobFinished(Job job);

    void onJobOutput(Job job, @NonNull String[] strArr);

    void onJobProgress(Job job, short s);

    void onJobReady(Job job);

    void onJobScheduled(Job job);

    void onJobStarted(Job job);

    void onUserCanceledJob(Job job);
}
