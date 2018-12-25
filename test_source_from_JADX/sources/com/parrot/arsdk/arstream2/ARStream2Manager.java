package com.parrot.arsdk.arstream2;

import android.os.Build;
import android.os.Process;
import android.util.Log;
import com.parrot.arsdk.arsal.ARSAL_SOCKET_CLASS_SELECTOR_ENUM;
import com.parrot.mux.Mux;
import com.parrot.mux.Mux.Ref;
import name.antonsmirnov.firmata.FormatHelper;

public class ARStream2Manager {
    private static final String TAG = ARStream2Manager.class.getSimpleName();
    private final long nativeRef;
    private final Thread networkThread;
    private final Thread outputThread;

    /* renamed from: com.parrot.arsdk.arstream2.ARStream2Manager$1 */
    class C16191 implements Runnable {
        C16191() {
        }

        public void run() {
            Process.setThreadPriority(-4);
            ARStream2Manager.this.nativeRunNetworkThread(ARStream2Manager.this.nativeRef);
        }
    }

    /* renamed from: com.parrot.arsdk.arstream2.ARStream2Manager$2 */
    class C16202 implements Runnable {
        C16202() {
        }

        public void run() {
            Process.setThreadPriority(-4);
            ARStream2Manager.this.nativeRunOutputThread(ARStream2Manager.this.nativeRef);
        }
    }

    /* renamed from: com.parrot.arsdk.arstream2.ARStream2Manager$3 */
    class C16213 implements Runnable {
        C16213() {
        }

        public void run() {
            Process.setThreadPriority(-4);
            ARStream2Manager.this.nativeRunNetworkThread(ARStream2Manager.this.nativeRef);
        }
    }

    /* renamed from: com.parrot.arsdk.arstream2.ARStream2Manager$4 */
    class C16224 implements Runnable {
        C16224() {
        }

        public void run() {
            Process.setThreadPriority(-4);
            ARStream2Manager.this.nativeRunOutputThread(ARStream2Manager.this.nativeRef);
        }
    }

    private native boolean nativeFree(long j);

    private native long nativeMuxInit(long j, String str, String str2, int i);

    private native long nativeNetInit(String str, int i, int i2, int i3, int i4, String str2, String str3, int i5, int i6);

    private native void nativeRunNetworkThread(long j);

    private native void nativeRunOutputThread(long j);

    private native boolean nativeStop(long j);

    public ARStream2Manager(Mux mux, String canonicalName, int maxPacketSize) {
        String friendlyName = new StringBuilder();
        friendlyName.append(Build.MODEL);
        friendlyName.append(FormatHelper.SPACE);
        friendlyName.append(Build.DEVICE);
        friendlyName.append(FormatHelper.SPACE);
        friendlyName.append(canonicalName);
        friendlyName = friendlyName.toString();
        Ref muxRef = mux.newMuxRef();
        this.nativeRef = nativeMuxInit(muxRef.getCPtr(), canonicalName, friendlyName, maxPacketSize);
        muxRef.release();
        this.networkThread = new Thread(new C16191(), "ARStream2Stream");
        this.outputThread = new Thread(new C16202(), "ARStream2Filter");
    }

    public ARStream2Manager(String serverAddress, int serverStreamPort, int serverControlPort, int clientStreamPort, int clientControlPort, String canonicalName, int maxPacketSize, ARSAL_SOCKET_CLASS_SELECTOR_ENUM classSelector) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Build.MODEL);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(Build.DEVICE);
        stringBuilder.append(FormatHelper.SPACE);
        String str = canonicalName;
        stringBuilder.append(str);
        this.nativeRef = nativeNetInit(serverAddress, serverStreamPort, serverControlPort, clientStreamPort, clientControlPort, str, stringBuilder.toString(), maxPacketSize, classSelector.getValue());
        this.networkThread = new Thread(new C16213(), "ARStream2Stream");
        this.outputThread = new Thread(new C16224(), "ARStream2Filter");
    }

    public boolean isValid() {
        return this.nativeRef != 0;
    }

    public void start() {
        if (isValid()) {
            this.networkThread.start();
            this.outputThread.start();
            return;
        }
        Log.e(TAG, "unable to start, arstream2 manager is not valid! ");
    }

    public void stop() {
        if (isValid()) {
            nativeStop(this.nativeRef);
        }
    }

    public void dispose() {
        if (isValid()) {
            try {
                this.networkThread.join();
                this.outputThread.join();
            } catch (InterruptedException e) {
            }
            nativeFree(this.nativeRef);
        }
    }

    long getNativeRef() {
        return this.nativeRef;
    }
}
