package android.support.v4.media;

import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase;
import android.util.Log;

class MediaBrowserCompat$MediaBrowserImplBase$1 implements Runnable {
    final /* synthetic */ MediaBrowserImplBase this$0;

    MediaBrowserCompat$MediaBrowserImplBase$1(MediaBrowserImplBase this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if (this.this$0.mState != 0) {
            this.this$0.mState = 2;
            StringBuilder stringBuilder;
            if (MediaBrowserCompat.DEBUG && this.this$0.mServiceConnection != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("mServiceConnection should be null. Instead it is ");
                stringBuilder.append(this.this$0.mServiceConnection);
                throw new RuntimeException(stringBuilder.toString());
            } else if (this.this$0.mServiceBinderWrapper != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("mServiceBinderWrapper should be null. Instead it is ");
                stringBuilder.append(this.this$0.mServiceBinderWrapper);
                throw new RuntimeException(stringBuilder.toString());
            } else if (this.this$0.mCallbacksMessenger != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("mCallbacksMessenger should be null. Instead it is ");
                stringBuilder.append(this.this$0.mCallbacksMessenger);
                throw new RuntimeException(stringBuilder.toString());
            } else {
                Intent intent = new Intent(MediaBrowserServiceCompat.SERVICE_INTERFACE);
                intent.setComponent(this.this$0.mServiceComponent);
                this.this$0.mServiceConnection = new MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection(this.this$0);
                boolean bound = false;
                try {
                    bound = this.this$0.mContext.bindService(intent, this.this$0.mServiceConnection, 1);
                } catch (Exception e) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Failed binding to service ");
                    stringBuilder2.append(this.this$0.mServiceComponent);
                    Log.e("MediaBrowserCompat", stringBuilder2.toString());
                }
                if (!bound) {
                    this.this$0.forceCloseConnection();
                    this.this$0.mCallback.onConnectionFailed();
                }
                if (MediaBrowserCompat.DEBUG) {
                    Log.d("MediaBrowserCompat", "connect...");
                    this.this$0.dump();
                }
            }
        }
    }
}
