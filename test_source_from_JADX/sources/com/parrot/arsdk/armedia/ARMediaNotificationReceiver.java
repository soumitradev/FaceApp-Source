package com.parrot.arsdk.armedia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ARMediaNotificationReceiver extends BroadcastReceiver {
    private ARMediaNotificationReceiverListener listener;

    public ARMediaNotificationReceiver(ARMediaNotificationReceiverListener listener) {
        this.listener = listener;
    }

    public void onReceive(Context context, Intent intent) {
        Bundle dictionary = intent.getExtras();
        if (this.listener == null) {
            return;
        }
        if (dictionary.containsKey(ARMediaManager.ARMediaManagerNotificationDictionaryIsInitKey)) {
            this.listener.onNotificationDictionaryIsInit();
        } else if (dictionary.containsKey(ARMediaManager.ARMediaManagerNotificationDictionaryUpdatedKey)) {
            this.listener.onNotificationDictionaryIsUpdated(((Boolean) dictionary.get(ARMediaManager.ARMediaManagerNotificationDictionaryUpdatedKey)).booleanValue());
        } else if (dictionary.containsKey(ARMediaManager.ARMediaManagerNotificationDictionaryUpdatingKey)) {
            this.listener.onNotificationDictionaryIsUpdating(((Double) dictionary.get(ARMediaManager.ARMediaManagerNotificationDictionaryUpdatingKey)).doubleValue());
        } else if (dictionary.containsKey(ARMediaManager.ARMediaManagerNotificationDictionaryMediaAddedKey)) {
            this.listener.onNotificationDictionaryMediaAdded((String) dictionary.get(ARMediaManager.ARMediaManagerNotificationDictionaryMediaAddedKey));
        }
    }
}
