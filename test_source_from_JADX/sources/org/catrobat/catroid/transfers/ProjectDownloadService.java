package org.catrobat.catroid.transfers;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import java.io.File;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.utils.DownloadUtil;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.web.ServerCalls;

public class ProjectDownloadService extends IntentService {
    private static final String DOWNLOAD_FILE_NAME = "down.catrobat";
    public static final String DOWNLOAD_NAME_TAG = "downloadName";
    public static final String ID_TAG = "notificationId";
    public static final String RECEIVER_TAG = "receiver";
    public static final String RENAME_AFTER_DOWNLOAD = "renameAfterDownload";
    public static final String TAG = ProjectDownloadService.class.getSimpleName();
    public static final String URL_TAG = "url";
    private Handler handler;
    public ResultReceiver receiver;

    public ProjectDownloadService() {
        super(ProjectDownloadService.class.getSimpleName());
    }

    public void onCreate() {
        super.onCreate();
        this.handler = new Handler();
    }

    protected void onHandleIntent(Intent intent) {
        String projectName = intent.getStringExtra(DOWNLOAD_NAME_TAG);
        String zipFileString = PathBuilder.buildPath(Constants.TMP_PATH, DOWNLOAD_FILE_NAME);
        String url = intent.getStringExtra("url");
        Integer notificationId = Integer.valueOf(intent.getIntExtra("notificationId", -1));
        this.receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {
            ServerCalls.getInstance().downloadProject(url, zipFileString, projectName, this.receiver, notificationId.intValue());
            new ZipArchiver().unzip(new File(zipFileString), new File(PathBuilder.buildProjectPath(projectName)));
            if (intent.getBooleanExtra(RENAME_AFTER_DOWNLOAD, false)) {
                Project projectTBRenamed = XstreamSerializer.getInstance().loadProject(projectName, getBaseContext());
                if (projectTBRenamed != null) {
                    projectTBRenamed.setName(projectName);
                    XstreamSerializer.getInstance().saveProject(projectTBRenamed);
                }
            }
            XstreamSerializer.getInstance().updateCodeFileOnDownload(projectName);
        } catch (Exception e) {
            showToast(R.string.error_project_download, true);
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (Throwable th) {
            DownloadUtil.getInstance().downloadFinished(projectName, url);
        }
        DownloadUtil.getInstance().downloadFinished(projectName, url);
        showToast(R.string.notification_download_finished, false);
    }

    private void showToast(final int messageId, boolean error) {
        if (error) {
            this.handler.post(new Runnable() {
                public void run() {
                    ToastUtil.showError(ProjectDownloadService.this.getBaseContext(), messageId);
                }
            });
        } else {
            this.handler.post(new Runnable() {
                public void run() {
                    ToastUtil.showSuccess(ProjectDownloadService.this.getBaseContext(), messageId);
                }
            });
        }
    }
}
