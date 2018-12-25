package com.parrot.arsdk.arstream2;

import android.os.Build;
import com.parrot.arsdk.arsal.ARSAL_SOCKET_CLASS_SELECTOR_ENUM;
import name.antonsmirnov.firmata.FormatHelper;

public class ARStream2Resender {
    private static final String TAG = ARStream2Resender.class.getSimpleName();
    private final ARStream2Manager manager;
    private final long nativeRef;

    private native long nativeInit(long j, String str, String str2, String str3, int i, int i2, int i3, int i4, String str4, String str5, int i5, int i6);

    private native boolean nativeStop(long j, long j2);

    public ARStream2Resender(ARStream2Manager manager, String clientAddress, String mcastAddress, String mcastIfaceAddress, int serverStreamPort, int serverControlPort, int clientStreamPort, int clientControlPort, String canonicalName, ARSAL_SOCKET_CLASS_SELECTOR_ENUM classSelector, int maxNetworkLatency) {
        this.manager = manager;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Build.MODEL);
        stringBuilder.append(FormatHelper.SPACE);
        stringBuilder.append(Build.DEVICE);
        stringBuilder.append(FormatHelper.SPACE);
        String str = canonicalName;
        stringBuilder.append(str);
        this.nativeRef = nativeInit(manager.getNativeRef(), clientAddress, mcastAddress, mcastIfaceAddress, serverStreamPort, serverControlPort, clientStreamPort, clientControlPort, str, stringBuilder.toString(), classSelector.getValue(), maxNetworkLatency);
    }

    private boolean isValid() {
        return this.nativeRef != 0;
    }

    public void stop() {
        if (isValid()) {
            nativeStop(this.manager.getNativeRef(), this.nativeRef);
        }
    }
}
