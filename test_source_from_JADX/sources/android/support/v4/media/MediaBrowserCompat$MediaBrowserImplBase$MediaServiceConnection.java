package android.support.v4.media;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat.MediaBrowserImplBase;
import android.support.v4.media.MediaBrowserCompat.ServiceBinderWrapper;
import android.util.Log;

class MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection implements ServiceConnection {
    final /* synthetic */ MediaBrowserImplBase this$0;

    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection(MediaBrowserImplBase mediaBrowserImplBase) {
        this.this$0 = mediaBrowserImplBase;
    }

    public void onServiceConnected(final ComponentName name, final IBinder binder) {
        postOrRun(new Runnable() {
            public void run() {
                if (MediaBrowserCompat.DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("MediaServiceConnection.onServiceConnected name=");
                    stringBuilder.append(name);
                    stringBuilder.append(" binder=");
                    stringBuilder.append(binder);
                    Log.d("MediaBrowserCompat", stringBuilder.toString());
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.dump();
                }
                if (MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.isCurrent("onServiceConnected")) {
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mServiceBinderWrapper = new ServiceBinderWrapper(binder, MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mRootHints);
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mCallbacksMessenger = new Messenger(MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mHandler);
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mHandler.setCallbacksMessenger(MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mCallbacksMessenger);
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mState = 2;
                    try {
                        if (MediaBrowserCompat.DEBUG) {
                            Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                            MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.dump();
                        }
                        MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mServiceBinderWrapper.connect(MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mContext, MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mCallbacksMessenger);
                    } catch (RemoteException e) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("RemoteException during connect for ");
                        stringBuilder2.append(MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mServiceComponent);
                        Log.w("MediaBrowserCompat", stringBuilder2.toString());
                        if (MediaBrowserCompat.DEBUG) {
                            Log.d("MediaBrowserCompat", "ServiceCallbacks.onConnect...");
                            MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.dump();
                        }
                    }
                }
            }
        });
    }

    public void onServiceDisconnected(final ComponentName name) {
        postOrRun(new Runnable() {
            public void run() {
                if (MediaBrowserCompat.DEBUG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("MediaServiceConnection.onServiceDisconnected name=");
                    stringBuilder.append(name);
                    stringBuilder.append(" this=");
                    stringBuilder.append(this);
                    stringBuilder.append(" mServiceConnection=");
                    stringBuilder.append(MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mServiceConnection);
                    Log.d("MediaBrowserCompat", stringBuilder.toString());
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.dump();
                }
                if (MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.isCurrent("onServiceDisconnected")) {
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mServiceBinderWrapper = null;
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mCallbacksMessenger = null;
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mHandler.setCallbacksMessenger(null);
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mState = 4;
                    MediaBrowserCompat$MediaBrowserImplBase$MediaServiceConnection.this.this$0.mCallback.onConnectionSuspended();
                }
            }
        });
    }

    private void postOrRun(Runnable r) {
        if (Thread.currentThread() == this.this$0.mHandler.getLooper().getThread()) {
            r.run();
        } else {
            this.this$0.mHandler.post(r);
        }
    }

    boolean isCurrent(String funcName) {
        if (this.this$0.mServiceConnection == this && this.this$0.mState != 0) {
            if (this.this$0.mState != 1) {
                return true;
            }
        }
        if (!(this.this$0.mState == 0 || this.this$0.mState == 1)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(funcName);
            stringBuilder.append(" for ");
            stringBuilder.append(this.this$0.mServiceComponent);
            stringBuilder.append(" with mServiceConnection=");
            stringBuilder.append(this.this$0.mServiceConnection);
            stringBuilder.append(" this=");
            stringBuilder.append(this);
            Log.i("MediaBrowserCompat", stringBuilder.toString());
        }
        return false;
    }
}
