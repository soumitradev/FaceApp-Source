package org.catrobat.catroid.scratchconverter;

import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.common.images.WebImage;
import org.catrobat.catroid.scratchconverter.Client.ConnectAuthCallback;
import org.catrobat.catroid.scratchconverter.Client.ConvertCallback;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;
import org.catrobat.catroid.ui.scratchconverter.BaseInfoViewListener;
import org.catrobat.catroid.ui.scratchconverter.JobViewListener;

public interface ConversionManager extends ConnectAuthCallback, ConvertCallback, DownloadCallback {
    void addBaseInfoViewListener(BaseInfoViewListener baseInfoViewListener);

    void addGlobalDownloadCallback(DownloadCallback downloadCallback);

    void addGlobalJobViewListener(JobViewListener jobViewListener);

    void addJobViewListener(long j, JobViewListener jobViewListener);

    void connectAndAuthenticate();

    void convertProgram(long j, String str, WebImage webImage, boolean z);

    int getNumberOfJobsInProgress();

    boolean isJobDownloading(long j);

    boolean isJobInProgress(long j);

    boolean removeBaseInfoViewListener(BaseInfoViewListener baseInfoViewListener);

    boolean removeGlobalDownloadCallback(DownloadCallback downloadCallback);

    boolean removeGlobalJobViewListener(JobViewListener jobViewListener);

    boolean removeJobViewListener(long j, JobViewListener jobViewListener);

    void setCurrentActivity(AppCompatActivity appCompatActivity);

    void shutdown();
}
