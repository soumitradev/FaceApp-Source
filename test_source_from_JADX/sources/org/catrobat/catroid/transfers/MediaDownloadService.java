package org.catrobat.catroid.transfers;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import java.io.IOException;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.web.ServerCalls;
import org.catrobat.catroid.web.WebconnectionException;

public class MediaDownloadService extends IntentService {
    public static final String MEDIA_FILE_PATH = "path";
    public static final String RECEIVER_TAG = "receiver";
    public static final String TAG = MediaDownloadService.class.getSimpleName();
    public static final String URL_TAG = "url";
    private Handler handler;
    public ResultReceiver receiver;

    public MediaDownloadService() {
        super(MediaDownloadService.class.getSimpleName());
    }

    public void onCreate() {
        super.onCreate();
        this.handler = new Handler();
    }

    protected void onHandleIntent(Intent intent) {
        boolean result = true;
        String url = intent.getStringExtra("url");
        String fileString = intent.getStringExtra(MEDIA_FILE_PATH);
        int errorMessage = R.string.error_unknown_error;
        this.receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {
            ServerCalls.getInstance().downloadMedia(url, fileString, this.receiver);
        } catch (IOException ioException) {
            Log.e(TAG, Log.getStackTraceString(ioException));
            result = false;
            this.receiver.send(105, null);
        } catch (WebconnectionException webconnectionException) {
            Log.e(TAG, Log.getStackTraceString(webconnectionException));
            result = false;
            errorMessage = R.string.error_internet_connection;
            this.receiver.send(105, null);
        }
        if (result) {
            showToast(R.string.notification_download_finished, false);
        } else {
            showToast(errorMessage, true);
        }
    }

    private void showToast(final int messageId, boolean error) {
        if (error) {
            this.handler.post(new Runnable() {
                public void run() {
                    ToastUtil.showError(MediaDownloadService.this.getBaseContext(), messageId);
                }
            });
        } else {
            this.handler.post(new Runnable() {
                public void run() {
                    ToastUtil.showSuccess(MediaDownloadService.this.getBaseContext(), messageId);
                }
            });
        }
    }
}
