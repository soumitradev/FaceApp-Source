package com.parrot.arsdk.ardiscovery;

import android.util.Log;
import com.parrot.mux.Mux;
import com.parrot.mux.Mux.Ref;
import name.antonsmirnov.firmata.FormatHelper;

public class ARDiscoveryMux {
    private final String TAG = "ARDiscoveryMux";
    private long cPtr;
    private ConnectCallback connectCallback;
    private final Object connectLock = new Object();
    private String deviceId;
    private String deviceName;
    private int deviceType;
    private Listener listener;
    private Ref muxRef;

    public interface ConnectCallback {
        void onConnected(int i, String str);
    }

    public interface Listener {
        void onDeviceAdded(String str, int i, String str2);

        void onDeviceRemoved();
    }

    private static native void nativeClInit();

    private static native void nativeClInitConnection();

    private native void nativeDispose(long j);

    private native void nativeDisposeConnection(long j);

    private native long nativeNew(long j);

    private native long nativeNewConnection(long j);

    private native int nativeSendConnectRequest(long j, String str, String str2, String str3, String str4);

    static {
        nativeClInit();
        nativeClInitConnection();
    }

    public ARDiscoveryMux(Mux mux) {
        this.muxRef = mux.newMuxRef();
        this.cPtr = nativeNew(this.muxRef.getCPtr());
    }

    public boolean isValid() {
        return this.cPtr != 0;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
        if (listener != null && this.deviceName != null) {
            listener.onDeviceAdded(this.deviceName, this.deviceType, this.deviceId);
        }
    }

    public int connect(String device, String model, String id, String json, ConnectCallback connectCallback) {
        Throwable th;
        Throwable th2;
        int ret = -1;
        if (isValid()) {
            synchronized (r8.connectLock) {
                try {
                    if (r8.connectCallback != null) {
                        throw new RuntimeException("Connection already in progress!");
                    }
                    r8.connectCallback = connectCallback;
                    long muxConn = nativeNewConnection(r8.muxRef.getCPtr());
                    if (nativeSendConnectRequest(muxConn, device, model, id, json) == 0) {
                        try {
                            r8.connectLock.wait();
                        } catch (InterruptedException e) {
                        }
                        ret = 0;
                    }
                    nativeDisposeConnection(muxConn);
                } catch (Throwable th3) {
                    th = th3;
                    th2 = th;
                    throw th2;
                }
            }
        }
        r11 = connectCallback;
        return ret;
    }

    public void cancelConnect() {
        synchronized (this.connectLock) {
            this.connectLock.notifyAll();
        }
    }

    public void destroy() {
        Log.d("ARDiscoveryMux", "ARDiscoveryMux destroy");
        synchronized (this.connectLock) {
            this.connectLock.notifyAll();
        }
        nativeDispose(this.cPtr);
        this.cPtr = 0;
        this.deviceName = null;
        if (this.listener != null) {
            this.listener.onDeviceRemoved();
        }
        this.listener = null;
        this.muxRef.release();
        Log.d("ARDiscoveryMux", "ARDiscoveryMux destroy done");
    }

    protected void onDeviceAdded(String name, int type, String id) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDeviceAdded ");
            stringBuilder.append(name);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(type);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(id);
            Log.d("ARDiscoveryMux", stringBuilder.toString());
            this.deviceType = type;
            this.deviceName = name;
            this.deviceId = id;
            Listener l = this.listener;
            if (l != null) {
                l.onDeviceAdded(this.deviceName, this.deviceType, this.deviceId);
            }
        } catch (Throwable t) {
            Log.e("ARDiscoveryMux", "exception in onDeviceRemoved", t);
        }
    }

    protected void onDeviceRemoved(String name, int type, String id) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDeviceRemoved ");
            stringBuilder.append(name);
            stringBuilder.append("  ");
            stringBuilder.append(type);
            stringBuilder.append(FormatHelper.SPACE);
            stringBuilder.append(id);
            Log.d("ARDiscoveryMux", stringBuilder.toString());
            synchronized (this.connectLock) {
                this.connectLock.notify();
            }
            this.deviceName = null;
            if (this.listener != null) {
                this.listener.onDeviceRemoved();
            }
        } catch (Throwable t) {
            Log.e("ARDiscoveryMux", "exception in onDeviceRemoved", t);
        }
    }

    protected void onDeviceConnected(int status, String json) {
        try {
            synchronized (this.connectLock) {
                if (this.connectCallback != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onDeviceConnected ");
                    stringBuilder.append(status);
                    stringBuilder.append("  ");
                    stringBuilder.append(json);
                    Log.d("ARDiscoveryMux", stringBuilder.toString());
                    this.connectCallback.onConnected(status, json);
                    this.connectCallback = null;
                    this.connectLock.notify();
                }
            }
        } catch (Throwable t) {
            Log.e("ARDiscoveryMux", "exception in onDeviceConnected", t);
        }
    }

    protected void onReset() {
        try {
            Log.d("ARDiscoveryMux", "onReset");
            synchronized (this.connectLock) {
                this.connectLock.notify();
            }
            this.deviceName = null;
            if (this.listener != null) {
                this.listener.onDeviceRemoved();
            }
        } catch (Throwable t) {
            Log.e("ARDiscoveryMux", "exception in onReset", t);
        }
    }
}
