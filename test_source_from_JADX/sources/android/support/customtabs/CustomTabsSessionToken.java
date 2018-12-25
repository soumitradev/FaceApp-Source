package android.support.customtabs;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.customtabs.ICustomTabsCallback.Stub;
import android.support.v4.app.BundleCompat;

public class CustomTabsSessionToken {
    private static final String TAG = "CustomTabsSessionToken";
    private final CustomTabsCallback mCallback = new CustomTabsSessionToken$1(this);
    private final ICustomTabsCallback mCallbackBinder;

    public static CustomTabsSessionToken getSessionTokenFromIntent(Intent intent) {
        IBinder binder = BundleCompat.getBinder(intent.getExtras(), CustomTabsIntent.EXTRA_SESSION);
        if (binder == null) {
            return null;
        }
        return new CustomTabsSessionToken(Stub.asInterface(binder));
    }

    @NonNull
    public static CustomTabsSessionToken createMockSessionTokenForTesting() {
        return new CustomTabsSessionToken(new CustomTabsSessionToken$MockCallback());
    }

    CustomTabsSessionToken(ICustomTabsCallback callbackBinder) {
        this.mCallbackBinder = callbackBinder;
    }

    IBinder getCallbackBinder() {
        return this.mCallbackBinder.asBinder();
    }

    public int hashCode() {
        return getCallbackBinder().hashCode();
    }

    public boolean equals(Object o) {
        if (o instanceof CustomTabsSessionToken) {
            return ((CustomTabsSessionToken) o).getCallbackBinder().equals(this.mCallbackBinder.asBinder());
        }
        return false;
    }

    public CustomTabsCallback getCallback() {
        return this.mCallback;
    }

    public boolean isAssociatedWith(CustomTabsSession session) {
        return session.getBinder().equals(this.mCallbackBinder);
    }
}
