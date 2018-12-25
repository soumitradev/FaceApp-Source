package org.catrobat.catroid.web;

import android.os.Bundle;
import android.os.ResultReceiver;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {
    public static final String TAG_ENDOFFILE = "endOfFileReached";
    public static final String TAG_NOTIFICATION_ID = "notificationId";
    public static final String TAG_PROGRAM_NAME = "programName";
    public static final String TAG_PROGRESS = "currentDownloadProgress";
    public static final String TAG_REQUEST_URL = "requestUrl";
    private BufferedSource bufferedSource;
    private final int notificationId;
    private final String programName;
    private final ResultReceiver receiver;
    private final String requestUrl;
    private final ResponseBody responseBody;

    public ProgressResponseBody(ResponseBody responseBody, ResultReceiver receiver, int notificationId, String programName, String requestUrl) throws IOException {
        this.responseBody = responseBody;
        this.receiver = receiver;
        this.notificationId = notificationId;
        this.programName = programName;
        this.requestUrl = requestUrl;
    }

    public MediaType contentType() {
        return this.responseBody.contentType();
    }

    public long contentLength() throws IOException {
        return this.responseBody.contentLength();
    }

    public BufferedSource source() throws IOException {
        if (this.bufferedSource == null) {
            this.bufferedSource = Okio.buffer(source(this.responseBody.source()));
        }
        return this.bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long lastProgress = -1;
            long totalBytesRead = 0;

            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                this.totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                long progress = (this.totalBytesRead * 100) / ProgressResponseBody.this.contentLength();
                boolean endOfFile = bytesRead == -1;
                if (progress > this.lastProgress || endOfFile) {
                    ProgressResponseBody.this.sendUpdateIntent(progress, endOfFile);
                    this.lastProgress = progress;
                }
                return bytesRead;
            }
        };
    }

    private void sendUpdateIntent(long progress, boolean endOfFileReached) {
        Bundle progressBundle = new Bundle();
        progressBundle.putLong(TAG_PROGRESS, progress);
        progressBundle.putBoolean(TAG_ENDOFFILE, endOfFileReached);
        progressBundle.putInt("notificationId", this.notificationId);
        progressBundle.putString("programName", this.programName);
        progressBundle.putString(TAG_REQUEST_URL, this.requestUrl);
        this.receiver.send(101, progressBundle);
    }
}
