package android.support.customtabs;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.customtabs.IPostMessageService.Stub;

public class PostMessageService extends Service {
    private Stub mBinder = new C12041();

    /* renamed from: android.support.customtabs.PostMessageService$1 */
    class C12041 extends Stub {
        C12041() {
        }

        public void onMessageChannelReady(ICustomTabsCallback callback, Bundle extras) throws RemoteException {
            callback.onMessageChannelReady(extras);
        }

        public void onPostMessage(ICustomTabsCallback callback, String message, Bundle extras) throws RemoteException {
            callback.onPostMessage(message, extras);
        }
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
