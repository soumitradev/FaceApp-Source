package android.support.v4.media;

import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase;
import android.util.Log;

class MediaBrowserCompat$MediaBrowserImplBase$2 implements Runnable {
    final /* synthetic */ MediaBrowserImplBase this$0;

    MediaBrowserCompat$MediaBrowserImplBase$2(MediaBrowserImplBase this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (this.this$0.mCallbacksMessenger != null) {
            try {
                this.this$0.mServiceBinderWrapper.disconnect(this.this$0.mCallbacksMessenger);
            } catch (RemoteException e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("RemoteException during connect for ");
                stringBuilder.append(this.this$0.mServiceComponent);
                Log.w("MediaBrowserCompat", stringBuilder.toString());
            }
        }
        int state = this.this$0.mState;
        this.this$0.forceCloseConnection();
        if (state != 0) {
            this.this$0.mState = state;
        }
        if (MediaBrowserCompat.DEBUG) {
            Log.d("MediaBrowserCompat", "disconnect...");
            this.this$0.dump();
        }
    }
}
