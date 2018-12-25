package com.parrot.arsdk.ardiscovery;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.AppTask;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;

public abstract class UsbAccessoryActivity extends Activity {
    protected abstract Class getBaseActivity();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if ("android.hardware.usb.action.USB_ACCESSORY_ATTACHED".equals(intent.getAction())) {
            boolean found = false;
            ActivityManager am = (ActivityManager) getSystemService("activity");
            if (VERSION.SDK_INT < 21) {
                for (RunningTaskInfo rti : am.getRunningTasks(10)) {
                    if (rti.baseActivity.getPackageName().equals(getPackageName()) && !rti.baseActivity.getClassName().equals(getComponentName().getClassName())) {
                        am.moveTaskToFront(rti.id, 0);
                        found = true;
                        break;
                    }
                }
            } else {
                for (AppTask at : am.getAppTasks()) {
                    if (at.getTaskInfo().id != getTaskId()) {
                        at.moveToFront();
                        found = true;
                        break;
                    }
                }
            }
            Intent broadcastIntent;
            if (found) {
                broadcastIntent = new Intent(UsbAccessoryMux.ACTION_USB_ACCESSORY_ATTACHED);
                broadcastIntent.putExtras(intent.getExtras());
                sendBroadcast(broadcastIntent);
            } else {
                broadcastIntent = new Intent(this, getBaseActivity());
                broadcastIntent.addFlags(268435456);
                startActivity(broadcastIntent);
            }
        }
        finish();
    }
}
