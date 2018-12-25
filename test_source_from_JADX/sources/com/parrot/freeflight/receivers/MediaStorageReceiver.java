package com.parrot.freeflight.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;

public class MediaStorageReceiver extends BroadcastReceiver {
    private MediaStorageReceiverDelegate delegate;

    public MediaStorageReceiver(MediaStorageReceiverDelegate delegate) {
        this.delegate = delegate;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.delegate != null) {
            String action = intent.getAction();
            if (!action.equals("android.intent.action.MEDIA_SCANNER_FINISHED")) {
                if (!action.equals("android.intent.action.MEDIA_MOUNTED")) {
                    if (action.equals("android.intent.action.MEDIA_UNMOUNTED")) {
                        this.delegate.onMediaStorageUnmounted();
                        return;
                    } else if (action.equals("android.intent.action.MEDIA_EJECT")) {
                        this.delegate.onMediaEject();
                        return;
                    } else {
                        return;
                    }
                }
            }
            this.delegate.onMediaStorageMounted();
        }
    }

    public void registerForEvents(Context context) {
        context.registerReceiver(this, createIntentFilter());
    }

    public void unregisterFromEvents(Context context) {
        context.unregisterReceiver(this);
    }

    protected IntentFilter createIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_EJECT");
        intentFilter.addDataScheme(PocketMusicActivity.ABSOLUTE_FILE_PATH);
        return intentFilter;
    }
}
