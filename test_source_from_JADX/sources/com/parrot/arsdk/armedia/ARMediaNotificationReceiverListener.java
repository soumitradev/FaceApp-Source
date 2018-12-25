package com.parrot.arsdk.armedia;

public interface ARMediaNotificationReceiverListener {
    void onNotificationDictionaryIsInit();

    void onNotificationDictionaryIsUpdated(boolean z);

    void onNotificationDictionaryIsUpdating(double d);

    void onNotificationDictionaryMediaAdded(String str);
}
