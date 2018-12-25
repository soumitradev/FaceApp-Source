package org.catrobat.catroid.scratchconverter;

import android.support.annotation.Nullable;
import com.google.android.gms.common.images.WebImage;
import java.util.Date;
import org.catrobat.catroid.scratchconverter.protocol.Job;

public interface Client {
    public static final long INVALID_CLIENT_ID = -1;

    public interface ConnectAuthCallback {
        void onAuthenticationFailure(ClientException clientException);

        void onConnectionClosed(ClientException clientException);

        void onConnectionFailure(ClientException clientException);

        void onSuccess(long j);
    }

    public interface ConvertCallback {
        void onConversionAlreadyFinished(Job job, DownloadCallback downloadCallback, String str);

        void onConversionFailure(@Nullable Job job, ClientException clientException);

        void onConversionFinished(Job job, DownloadCallback downloadCallback, String str, Date date);

        void onConversionReady(Job job);

        void onConversionStart(Job job);

        void onError(String str);

        void onInfo(float f, Job[] jobArr);

        void onJobOutput(Job job, String[] strArr);

        void onJobProgress(Job job, short s);

        void onJobScheduled(Job job);
    }

    public interface DownloadCallback {
        void onDownloadFinished(String str, String str2);

        void onDownloadProgress(short s, String str);

        void onDownloadStarted(String str);

        void onUserCanceledDownload(String str);
    }

    public enum State {
        NOT_CONNECTED,
        CONNECTED,
        CONNECTED_AUTHENTICATED
    }

    void close();

    void connectAndAuthenticate(ConnectAuthCallback connectAuthCallback);

    void convertProgram(long j, String str, WebImage webImage, boolean z, boolean z2);

    int getNumberOfJobsInProgress();

    boolean isAuthenticated();

    boolean isClosed();

    boolean isJobInProgress(long j);

    void onUserCanceledConversion(long j);

    void retrieveInfo();

    void setConvertCallback(ConvertCallback convertCallback);
}
