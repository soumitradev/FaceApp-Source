package android.support.v4.app;

import android.app.Activity;
import android.content.pm.PackageManager;

class ActivityCompat$1 implements Runnable {
    final /* synthetic */ Activity val$activity;
    final /* synthetic */ String[] val$permissions;
    final /* synthetic */ int val$requestCode;

    ActivityCompat$1(String[] strArr, Activity activity, int i) {
        this.val$permissions = strArr;
        this.val$activity = activity;
        this.val$requestCode = i;
    }

    public void run() {
        int[] grantResults = new int[this.val$permissions.length];
        PackageManager packageManager = this.val$activity.getPackageManager();
        String packageName = this.val$activity.getPackageName();
        int permissionCount = this.val$permissions.length;
        for (int i = 0; i < permissionCount; i++) {
            grantResults[i] = packageManager.checkPermission(this.val$permissions[i], packageName);
        }
        ((ActivityCompat$OnRequestPermissionsResultCallback) this.val$activity).onRequestPermissionsResult(this.val$requestCode, this.val$permissions, grantResults);
    }
}
