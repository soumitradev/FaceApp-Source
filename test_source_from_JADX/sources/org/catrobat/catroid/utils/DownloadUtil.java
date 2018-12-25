package org.catrobat.catroid.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;
import org.catrobat.catroid.transfers.MediaDownloadService;
import org.catrobat.catroid.transfers.ProjectDownloadService;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment;
import org.catrobat.catroid.web.ProgressResponseBody;

public final class DownloadUtil {
    private static final String FILENAME_TAG = "fname=";
    private static final DownloadUtil INSTANCE = new DownloadUtil();
    private static final String TAG = DownloadUtil.class.getSimpleName();
    ArrayList<Integer> notificationIdArray = new ArrayList();
    private DownloadCallback programDownloadCallback = null;
    private Set<String> programDownloadQueue = Collections.synchronizedSet(new HashSet());
    private WebViewActivity webViewActivity = null;

    @SuppressLint({"ParcelCreator"})
    private class DownloadMediaReceiver extends ResultReceiver {
        DownloadMediaReceiver(Handler handler) {
            super(handler);
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            if (resultCode == 101) {
                long progress = resultData.getLong(ProgressResponseBody.TAG_PROGRESS);
                if (resultData.getBoolean(ProgressResponseBody.TAG_ENDOFFILE)) {
                    progress = 100;
                }
                DownloadUtil.this.webViewActivity.updateProgressDialog(progress);
            } else if (resultCode == 105) {
                DownloadUtil.this.webViewActivity.dismissProgressDialog();
            }
        }
    }

    @SuppressLint({"ParcelCreator"})
    private class DownloadProjectReceiver extends ResultReceiver {
        DownloadProjectReceiver(Handler handler) {
            super(handler);
        }

        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Integer notificationId = Integer.valueOf(resultData.getInt("notificationId"));
            if (!DownloadUtil.this.notificationIdArray.contains(notificationId)) {
                DownloadUtil.this.notificationIdArray.add(notificationId);
            }
            if (DownloadUtil.this.notificationIdArray.size() - 1 == notificationId.intValue()) {
                super.onReceiveResult(resultCode, resultData);
                if (resultCode == 101) {
                    long progress = resultData.getLong(ProgressResponseBody.TAG_PROGRESS);
                    if (resultData.getBoolean(ProgressResponseBody.TAG_ENDOFFILE)) {
                        progress = 100;
                    }
                    String requestUrl = resultData.getString(ProgressResponseBody.TAG_REQUEST_URL);
                    if (DownloadUtil.this.programDownloadCallback != null) {
                        DownloadUtil.this.programDownloadCallback.onDownloadProgress((short) ((int) progress), requestUrl);
                    }
                    StatusBarNotificationManager.getInstance().showOrUpdateNotification(notificationId.intValue(), Long.valueOf(progress).intValue());
                }
            }
        }
    }

    private DownloadUtil() {
    }

    public static DownloadUtil getInstance() {
        return INSTANCE;
    }

    public void setDownloadCallback(DownloadCallback callback) {
        this.programDownloadCallback = callback;
    }

    public void prepareDownloadAndStartIfPossible(AppCompatActivity activity, String url) {
        String programName = getProjectNameFromUrl(url);
        if (programName != null) {
            if (Utils.checkIfProjectExistsOrIsDownloadingIgnoreCase(programName)) {
                ReplaceExistingProjectDialogFragment.newInstance(programName, url).show(activity.getSupportFragmentManager(), ReplaceExistingProjectDialogFragment.TAG);
            } else {
                startDownload(activity, url, programName, false);
            }
        }
    }

    public void startMediaDownload(WebViewActivity activity, String url, String mediaName, String filePath) {
        if (mediaName != null) {
            this.webViewActivity = activity;
            Intent downloadIntent = new Intent(activity, MediaDownloadService.class);
            downloadIntent.putExtra("receiver", new DownloadMediaReceiver(new Handler()));
            downloadIntent.putExtra("url", url);
            downloadIntent.putExtra(MediaDownloadService.MEDIA_FILE_PATH, filePath);
            this.webViewActivity.createProgressDialog(mediaName);
            this.webViewActivity.setResultIntent(this.webViewActivity.getResultIntent().putExtra(WebViewActivity.MEDIA_FILE_PATH, filePath));
            activity.startService(downloadIntent);
        }
    }

    public void startDownload(Context context, String url, String programName, boolean renameProject) {
        this.programDownloadQueue.add(programName.toLowerCase(Locale.getDefault()));
        if (this.programDownloadCallback != null) {
            this.programDownloadCallback.onDownloadStarted(url);
        }
        Intent downloadIntent = new Intent(context, ProjectDownloadService.class);
        downloadIntent.putExtra("receiver", new DownloadProjectReceiver(new Handler()));
        downloadIntent.putExtra(ProjectDownloadService.DOWNLOAD_NAME_TAG, programName);
        downloadIntent.putExtra("url", url);
        downloadIntent.putExtra(ProjectDownloadService.RENAME_AFTER_DOWNLOAD, renameProject);
        downloadIntent.putExtra("notificationId", StatusBarNotificationManager.getInstance().createDownloadNotification(context, programName));
        context.startService(downloadIntent);
    }

    public void downloadFinished(String programName, String url) {
        this.programDownloadQueue.remove(programName.toLowerCase(Locale.getDefault()));
        if (this.programDownloadCallback != null) {
            this.programDownloadCallback.onDownloadFinished(programName, url);
        }
    }

    public boolean isProgramNameInDownloadQueueIgnoreCase(String programName) {
        return this.programDownloadQueue.contains(programName.toLowerCase(Locale.getDefault()));
    }

    public void downloadCanceled(String url) {
        if (this.programDownloadCallback != null) {
            this.programDownloadCallback.onUserCanceledDownload(url);
        }
    }

    public String getProjectNameFromUrl(String url) {
        String programName = url.substring(url.lastIndexOf(FILENAME_TAG) + FILENAME_TAG.length());
        try {
            return URLDecoder.decode(programName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not decode program name: ");
            stringBuilder.append(programName);
            Log.e(str, stringBuilder.toString(), e);
            return null;
        }
    }
}
