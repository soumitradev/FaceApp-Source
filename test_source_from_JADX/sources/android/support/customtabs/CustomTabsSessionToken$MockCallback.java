package android.support.customtabs;

import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.customtabs.ICustomTabsCallback.Stub;

class CustomTabsSessionToken$MockCallback extends Stub {
    CustomTabsSessionToken$MockCallback() {
    }

    public void onNavigationEvent(int navigationEvent, Bundle extras) {
    }

    public void extraCallback(String callbackName, Bundle args) {
    }

    public void onMessageChannelReady(Bundle extras) {
    }

    public void onPostMessage(String message, Bundle extras) {
    }

    public void onRelationshipValidationResult(int relation, Uri requestedOrigin, boolean result, Bundle extras) {
    }

    public IBinder asBinder() {
        return this;
    }
}
