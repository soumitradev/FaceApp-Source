package org.catrobat.catroid.utils;

import android.app.IntentService;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.util.SparseArray;
import com.google.common.primitives.Ints;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.transfers.ProjectUploadService;
import org.catrobat.catroid.ui.MainMenuActivity;

public final class StatusBarNotificationManager {
    public static final String ACTION_CANCEL_UPLOAD = "cancel_upload";
    public static final String ACTION_RETRY_UPLOAD = "retry_upload";
    public static final String ACTION_UPDATE_POCKET_CODE_VERSION = "update_pocket_code_version";
    public static final String EXTRA_PROJECT_NAME = "projectName";
    private static final StatusBarNotificationManager INSTANCE = new StatusBarNotificationManager();
    public static final int MAXIMUM_PERCENT = 100;
    private static final String TAG = StatusBarNotificationManager.class.getSimpleName();
    private Context context;
    private SparseArray<NotificationData> notificationDataMap = new SparseArray();
    private int notificationId;
    private NotificationManager notificationManager;
    private int progressPercent;

    public static class NotificationActionService extends IntentService {
        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();
            String access$000 = StatusBarNotificationManager.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Received notification, action is: ");
            stringBuilder.append(action);
            Log.d(access$000, stringBuilder.toString());
            if (StatusBarNotificationManager.ACTION_UPDATE_POCKET_CODE_VERSION.equals(action)) {
                access$000 = getPackageName();
                StatusBarNotificationManager.getInstance().cancelNotification(intent.getIntExtra("notificationId", 0));
                try {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("market://details?id=");
                    stringBuilder2.append(access$000);
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder2.toString())).addFlags(268435456));
                } catch (ActivityNotFoundException e) {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("https://play.google.com/store/apps/details?id=");
                    stringBuilder3.append(access$000);
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder3.toString())));
                }
                closeNotificationBar();
            }
            if (StatusBarNotificationManager.ACTION_RETRY_UPLOAD.equals(action)) {
                StatusBarNotificationManager.getInstance().cancelNotification(intent.getBundleExtra("bundle").getInt("notificationId"));
                Intent reuploadIntent = prepareReuploadIntent(intent);
                reuploadIntent.putExtra("notificationId", StatusBarNotificationManager.getInstance().createUploadNotification(getApplicationContext(), intent.getBundleExtra("bundle").getString("projectName")));
                getApplicationContext().startService(reuploadIntent);
            }
            if (StatusBarNotificationManager.ACTION_CANCEL_UPLOAD.equals(action)) {
                StatusBarNotificationManager.getInstance().cancelNotification(intent.getBundleExtra("bundle").getInt("notificationId"));
                closeNotificationBar();
            }
        }

        private Intent prepareReuploadIntent(Intent intent) {
            String projectName = intent.getBundleExtra("bundle").getString("projectName");
            String projectDescription = intent.getBundleExtra("bundle").getString("projectDescription");
            String projectPath = intent.getBundleExtra("bundle").getString("projectPath");
            String[] sceneNames = intent.getBundleExtra("bundle").getStringArray("sceneNames");
            String token = intent.getBundleExtra("bundle").getString(Constants.TOKEN);
            String username = intent.getBundleExtra("bundle").getString(Constants.USERNAME);
            ResultReceiver receiver = (ResultReceiver) intent.getBundleExtra("bundle").getParcelable("receiver");
            Intent reuploadIntent = new Intent(getApplicationContext(), ProjectUploadService.class);
            reuploadIntent.putExtra("receiver", receiver);
            reuploadIntent.putExtra("uploadName", projectName);
            reuploadIntent.putExtra("projectDescription", projectDescription);
            reuploadIntent.putExtra("projectPath", projectPath);
            reuploadIntent.putExtra(Constants.USERNAME, username);
            reuploadIntent.putExtra(Constants.TOKEN, token);
            reuploadIntent.putExtra("sceneNames", sceneNames);
            return reuploadIntent;
        }

        private void closeNotificationBar() {
            sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        }
    }

    private StatusBarNotificationManager() {
    }

    public static StatusBarNotificationManager getInstance() {
        return INSTANCE;
    }

    private void initNotificationManager(Context context) {
        if (this.notificationManager == null) {
            this.notificationManager = (NotificationManager) context.getSystemService("notification");
        }
        this.context = context;
    }

    public int createUploadNotification(Context context, String programName) {
        if (context != null) {
            if (programName != null) {
                initNotificationManager(context);
                Intent uploadIntent = new Intent(context, MainMenuActivity.class);
                uploadIntent.setAction("android.intent.action.MAIN");
                int id = createNotification(context, new NotificationData(context, PendingIntent.getActivity(context, this.notificationId, uploadIntent.setFlags(2097152), 268435456), R.drawable.ic_stat, programName, R.string.notification_upload_title_pending, R.string.notification_upload_title_finished, R.string.notification_upload_pending, R.string.notification_upload_finished));
                showOrUpdateNotification(id, 0);
                return id;
            }
        }
        return -1;
    }

    public int createCopyNotification(Context context, String programName) {
        if (context != null) {
            if (programName != null) {
                initNotificationManager(context);
                Intent copyIntent = new Intent(context, MainMenuActivity.class);
                copyIntent.setAction("android.intent.action.MAIN").setFlags(67108864).putExtra("projectName", programName);
                int id = createNotification(context, new NotificationData(context, PendingIntent.getActivity(context, this.notificationId, copyIntent, 268435456), R.drawable.ic_stat, programName, R.string.notification_copy_title_pending, R.string.notification_title_open, R.string.notification_copy_pending, R.string.notification_copy_finished));
                showOrUpdateNotification(id, 0);
                return id;
            }
        }
        return -1;
    }

    public int createDownloadNotification(Context context, String programName) {
        if (context != null) {
            if (programName != null) {
                initNotificationManager(context);
                Intent downloadIntent = new Intent(context, MainMenuActivity.class);
                downloadIntent.setAction("android.intent.action.MAIN").setFlags(67108864).putExtra("projectName", programName);
                return createNotification(context, new NotificationData(context, PendingIntent.getActivity(context, this.notificationId, downloadIntent, 268435456), R.drawable.ic_stat, programName, R.string.notification_download_title_pending, R.string.notification_title_open, R.string.notification_download_pending, R.string.notification_download_finished));
            }
        }
        return -1;
    }

    private int createNotification(Context context, NotificationData data) {
        initNotificationManager(context);
        PendingIntent doesNothingPendingIntent = PendingIntent.getActivity(context, -1, new Intent(), Ints.MAX_POWER_OF_TWO);
        Builder notificationBuilder = new Builder(context);
        notificationBuilder.setContentTitle(data.getNotificationTitleWorking()).setContentText(data.getNotificationTextWorking()).setSmallIcon(data.getNotificationIcon()).setOngoing(true).setContentIntent(doesNothingPendingIntent);
        data.setNotificationBuilder(notificationBuilder);
        this.notificationDataMap.put(this.notificationId, data);
        int i = this.notificationId;
        this.notificationId = i + 1;
        return i;
    }

    public void showOrUpdateNotification(int id, int progressInPercent) {
        NotificationData notificationData = (NotificationData) this.notificationDataMap.get(id);
        if (notificationData != null) {
            this.progressPercent = progressInPercent;
            Builder notificationBuilder = notificationData.getNotificationBuilder();
            notificationBuilder.setProgress(100, progressInPercent, false);
            this.notificationManager.notify(id, notificationBuilder.build());
            if (progressInPercent == 100) {
                notificationBuilder.setContentTitle(notificationData.getNotificationTitleDone()).setContentText(notificationData.getNotificationTextDone()).setProgress(0, 0, false).setAutoCancel(true).setContentIntent(notificationData.getPendingIntent()).setOngoing(false);
                this.notificationManager.notify(id, notificationBuilder.build());
            }
        }
    }

    public void abortProgressNotificationWithMessage(int id, String changeDoneText) {
        NotificationData notificationData = (NotificationData) this.notificationDataMap.get(id);
        if (notificationData != null) {
            notificationData.setNotificationTextDone(changeDoneText);
            this.notificationDataMap.put(id, notificationData);
            showOrUpdateNotification(id, 100);
        }
    }

    public void showUploadRejectedNotification(int id, int statusCode, String serverAnswer, Bundle bundle) {
        NotificationData notificationData = (NotificationData) this.notificationDataMap.get(id);
        if (notificationData == null) {
            Log.d(TAG, "NotificationData is null.");
            return;
        }
        Builder notificationBuilder = notificationData.getNotificationBuilder();
        notificationData.setNotificationTextDone(serverAnswer);
        notificationBuilder.setContentTitle(this.context.getResources().getText(R.string.notification_upload_rejected)).setContentText(serverAnswer).setTicker(this.context.getResources().getText(R.string.notification_upload_rejected)).setSound(RingtoneManager.getDefaultUri(2)).setStyle(new BigTextStyle().bigText(serverAnswer)).setProgress(0, 0, false).setAutoCancel(true).setPriority(2).setOngoing(false);
        switch (statusCode) {
            case 500:
            case 501:
            case 504:
            case 505:
            case Constants.STATUS_CODE_UPLOAD_UNZIP_FAILED /*506*/:
            case 507:
            case 513:
            case 514:
                Intent actionIntentRetryUpload = new Intent(this.context, NotificationActionService.class).setAction(ACTION_RETRY_UPLOAD);
                actionIntentRetryUpload.putExtra("bundle", bundle);
                notificationBuilder.addAction(17301599, this.context.getResources().getString(R.string.notification_upload_retry), PendingIntent.getService(this.context, id, actionIntentRetryUpload, 268435456));
                Intent actionIntentCancelUpload = new Intent(this.context, NotificationActionService.class).setAction(ACTION_CANCEL_UPLOAD);
                actionIntentCancelUpload.putExtra("bundle", bundle);
                notificationBuilder.addAction(17301560, this.context.getResources().getString(R.string.cancel), PendingIntent.getService(this.context, id, actionIntentCancelUpload, Ints.MAX_POWER_OF_TWO));
                break;
            case 503:
            case 518:
            case 519:
                notificationBuilder.addAction(R.drawable.ic_launcher, this.context.getResources().getString(R.string.notification_open_play_store), PendingIntent.getService(this.context, id, new Intent(this.context, NotificationActionService.class).setAction(ACTION_UPDATE_POCKET_CODE_VERSION).putExtra("notificationId", id), Ints.MAX_POWER_OF_TWO));
                break;
            default:
                this.notificationDataMap.put(id, notificationData);
                Intent openIntent = new Intent(this.context, MainMenuActivity.class);
                openIntent.setAction("android.intent.action.MAIN").setFlags(67108864).putExtra("projectName", bundle.getString("projectName"));
                notificationData.setPendingIntent(PendingIntent.getActivity(this.context, this.notificationId, openIntent, 268435456));
                showOrUpdateNotification(id, 100);
                return;
        }
        notificationBuilder.setContentIntent(notificationData.getPendingIntent());
        this.notificationDataMap.put(id, notificationData);
        this.notificationManager.notify(id, notificationBuilder.build());
    }

    public void cancelNotification(int id) {
        this.notificationDataMap.remove(id);
        this.notificationManager.cancel(id);
    }

    public int getProgressPercent() {
        return this.progressPercent;
    }
}
