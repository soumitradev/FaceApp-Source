package org.catrobat.catroid.transfers;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.StatusBarNotificationManager;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;

public class ProjectUploadService extends IntentService {
    private static final String TAG = ProjectUploadService.class.getSimpleName();
    public static final String UPLOAD_FILE_NAME = "upload.catrobat";
    private Integer notificationId;
    private String projectDescription;
    private String projectName;
    private String projectPath;
    private String provider;
    public ResultReceiver receiver;
    private boolean result;
    private String[] sceneNames;
    private String serverAnswer;
    private int statusCode;
    private String token;
    private Bundle uploadBackupBundle;
    private String username;

    public ProjectUploadService() {
        super("ProjectUploadService");
    }

    public int onStartCommand(Intent intent, int flags, int startID) {
        int returnCode = super.onStartCommand(intent, flags, startID);
        this.projectPath = intent.getStringExtra("projectPath");
        this.projectName = intent.getStringExtra("uploadName");
        this.projectDescription = intent.getStringExtra("projectDescription");
        this.sceneNames = intent.getStringArrayExtra("sceneNames");
        this.token = intent.getStringExtra(Constants.TOKEN);
        this.username = intent.getStringExtra(Constants.USERNAME);
        this.provider = intent.getStringExtra("provider");
        this.serverAnswer = "";
        this.result = true;
        this.notificationId = Integer.valueOf(intent.getIntExtra("notificationId", 0));
        this.uploadBackupBundle = new Bundle();
        return returnCode;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onHandleIntent(android.content.Intent r24) {
        /*
        r23 = this;
        r1 = r23;
        r2 = org.catrobat.catroid.io.XstreamSerializer.getInstance();
        r3 = org.catrobat.catroid.ProjectManager.getInstance();
        r3 = r3.getCurrentProject();
        r2.saveProject(r3);
        r2 = "receiver";
        r3 = r24;
        r2 = r3.getParcelableExtra(r2);
        r2 = (android.os.ResultReceiver) r2;
        r1.receiver = r2;
        r2 = 0;
        r4 = r1.projectPath;	 Catch:{ IOException -> 0x01b3, WebconnectionException -> 0x019a }
        if (r4 != 0) goto L_0x0031;
    L_0x0022:
        r1.result = r2;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r4 = TAG;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r5 = "project path is null";
        android.util.Log.e(r4, r5);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        return;
    L_0x002c:
        r0 = move-exception;
        r2 = r0;
        r3 = 0;
        goto L_0x01b6;
    L_0x0031:
        r4 = new java.io.File;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r5 = r1.projectPath;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r4.<init>(r5);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r5 = r4.listFiles();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r5 = r5.length;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        if (r5 != 0) goto L_0x0042;
    L_0x003f:
        r1.result = r2;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        return;
    L_0x0042:
        r5 = new org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r6 = r23.getApplicationContext();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r5.<init>(r6);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r6 = r1.sceneNames;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r7 = r6.length;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r8 = 0;
    L_0x004f:
        if (r8 >= r7) goto L_0x007d;
    L_0x0051:
        r9 = r6[r8];	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r10 = r1.projectName;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r10 = r5.getScreenshotFile(r10, r9, r2);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r10.exists();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        if (r11 == 0) goto L_0x007a;
    L_0x005f:
        r11 = r10.length();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r13 = 0;
        r15 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1));
        if (r15 <= 0) goto L_0x007a;
    L_0x0069:
        r11 = 480; // 0x1e0 float:6.73E-43 double:2.37E-321;
        org.catrobat.catroid.utils.ImageEditing.scaleImageFile(r10, r11, r11);	 Catch:{ FileNotFoundException -> 0x006f }
        goto L_0x007a;
    L_0x006f:
        r0 = move-exception;
        r11 = r0;
        r12 = TAG;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r13 = android.util.Log.getStackTraceString(r11);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        android.util.Log.e(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
    L_0x007a:
        r8 = r8 + 1;
        goto L_0x004f;
    L_0x007d:
        r6 = new java.io.File;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r7 = 2;
        r8 = new java.lang.String[r7];	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r9 = org.catrobat.catroid.common.Constants.TMP_PATH;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r8[r2] = r9;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r9 = "upload.catrobat";
        r10 = 1;
        r8[r10] = r9;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r8 = org.catrobat.catroid.utils.PathBuilder.buildPath(r8);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r6.<init>(r8);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r8 = new org.catrobat.catroid.io.ZipArchiver;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r8.<init>();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r9 = r4.listFiles();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r8.zip(r6, r9);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r8 = r23.getApplicationContext();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r9 = android.preference.PreferenceManager.getDefaultSharedPreferences(r8);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = "email";
        r12 = "no_email";
        r11 = r9.getString(r11, r12);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = r1.provider;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r13 = -1;
        r14 = r12.hashCode();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r15 = -1999289321; // 0xffffffff88d54417 float:-1.2835479E-33 double:NaN;
        if (r14 == r15) goto L_0x00d9;
    L_0x00ba:
        r7 = 68029025; // 0x40e0a61 float:1.6696786E-36 double:3.3610804E-316;
        if (r14 == r7) goto L_0x00cf;
    L_0x00bf:
        r7 = 1279756998; // 0x4c478ac6 float:5.230876E7 double:6.322839677E-315;
        if (r14 == r7) goto L_0x00c5;
    L_0x00c4:
        goto L_0x00e2;
    L_0x00c5:
        r7 = "FACEBOOK";
        r7 = r12.equals(r7);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        if (r7 == 0) goto L_0x00e2;
    L_0x00cd:
        r13 = 0;
        goto L_0x00e2;
    L_0x00cf:
        r7 = "GPLUS";
        r7 = r12.equals(r7);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        if (r7 == 0) goto L_0x00e2;
    L_0x00d7:
        r13 = 1;
        goto L_0x00e2;
    L_0x00d9:
        r10 = "NATIVE";
        r10 = r12.equals(r10);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        if (r10 == 0) goto L_0x00e2;
    L_0x00e1:
        r13 = 2;
    L_0x00e2:
        switch(r13) {
            case 0: goto L_0x00fa;
            case 1: goto L_0x00f0;
            case 2: goto L_0x00e6;
            default: goto L_0x00e5;
        };	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
    L_0x00e5:
        goto L_0x0104;
    L_0x00e6:
        r7 = "email";
        r10 = "no_email";
        r7 = r9.getString(r7, r10);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r7;
        goto L_0x0104;
    L_0x00f0:
        r7 = "GOOGLE_EMAIL";
        r10 = "NO_GOOGLE_EMAIL";
        r7 = r9.getString(r7, r10);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r7;
        goto L_0x0104;
    L_0x00fa:
        r7 = "FACEBOOK_EMAIL";
        r10 = "NO_FACEBOOK_EMAIL";
        r7 = r9.getString(r7, r10);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r7;
    L_0x0104:
        r7 = "no_email";
        r7 = r11.equals(r7);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        if (r7 == 0) goto L_0x0111;
    L_0x010c:
        r7 = org.catrobat.catroid.utils.UtilDeviceInfo.getUserEmail(r23);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        goto L_0x0112;
    L_0x0111:
        r7 = r11;
    L_0x0112:
        r10 = org.catrobat.catroid.utils.UtilDeviceInfo.getUserLanguageCode();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "projectName";
        r13 = r1.projectName;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11.putString(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "projectDescription";
        r13 = r1.projectDescription;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11.putString(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "projectPath";
        r13 = r1.projectPath;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11.putString(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "sceneNames";
        r13 = r1.sceneNames;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11.putStringArray(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "userEmail";
        r11.putString(r12, r7);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "language";
        r11.putString(r12, r10);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "token";
        r13 = r1.token;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11.putString(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "username";
        r13 = r1.username;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11.putString(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "notificationId";
        r13 = r1.notificationId;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r13 = r13.intValue();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11.putInt(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = r1.uploadBackupBundle;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = "receiver";
        r13 = r1.receiver;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11.putParcelable(r12, r13);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r11 = org.catrobat.catroid.web.ServerCalls.getInstance();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r12 = r1.projectName;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r13 = r1.projectDescription;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r14 = r6.getAbsolutePath();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r15 = r1.token;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r2 = r1.username;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r3 = r1.receiver;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r22 = r4;
        r4 = r1.notificationId;	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r17 = r15;
        r15 = r7;
        r16 = r10;
        r18 = r2;
        r19 = r3;
        r20 = r4;
        r21 = r8;
        r11.uploadProject(r12, r13, r14, r15, r16, r17, r18, r19, r20, r21);	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        r6.delete();	 Catch:{ IOException -> 0x002c, WebconnectionException -> 0x019a }
        goto L_0x01c1;
    L_0x019a:
        r0 = move-exception;
        r2 = r0;
        r3 = r2.getMessage();
        r1.serverAnswer = r3;
        r3 = r2.getStatusCode();
        r1.statusCode = r3;
        r3 = TAG;
        r4 = r1.serverAnswer;
        android.util.Log.e(r3, r4);
        r3 = 0;
        r1.result = r3;
        goto L_0x01c2;
    L_0x01b3:
        r0 = move-exception;
        r3 = 0;
        r2 = r0;
    L_0x01b6:
        r4 = TAG;
        r5 = android.util.Log.getStackTraceString(r2);
        android.util.Log.e(r4, r5);
        r1.result = r3;
    L_0x01c2:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.transfers.ProjectUploadService.onHandleIntent(android.content.Intent):void");
    }

    public void onDestroy() {
        if (this.result) {
            ToastUtil.showSuccess((Context) this, (int) R.string.notification_upload_finished);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getResources().getText(R.string.error_project_upload).toString());
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(this.serverAnswer);
            ToastUtil.showError((Context) this, stringBuilder.toString());
            StatusBarNotificationManager.getInstance().showUploadRejectedNotification(this.notificationId.intValue(), this.statusCode, this.serverAnswer, this.uploadBackupBundle);
        }
        Utils.invalidateLoginTokenIfUserRestricted(getApplicationContext());
        super.onDestroy();
    }
}
