package com.parrot.arsdk.arnetworkal;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.parrot.arsdk.arsal.ARNativeData;
import com.parrot.arsdk.arsal.ARSAL_SOCKET_CLASS_SELECTOR_ENUM;
import com.parrot.mux.Mux;
import com.parrot.mux.Mux.Ref;

public class ARNetworkALManager {
    public static int ARNETWORKAL_MANAGER_BLE_ID_MAX = 0;
    public static int ARNETWORKAL_MANAGER_DEFAULT_ID_MAX = 0;
    public static int ARNETWORKAL_MANAGER_WIFI_ID_MAX = 0;
    private static final String TAG = "ARNetworkALManager";
    private boolean m_initOk = false;
    private long m_managerPtr = nativeNew();

    private native int nativeCancelBLENetwork(long j);

    private native int nativeCloseBLENetwork(long j);

    private native int nativeCloseMuxNetwork(long j);

    private native int nativeCloseWifiNetwork(long j);

    private native int nativeDelete(long j);

    private native int nativeDumpData(long j, byte b, long j2, int i, int i2);

    private native int nativeEnableDataDump(long j, String str, String str2);

    private static native int nativeGetDefineBleIdMAX();

    private static native int nativeGetDefineDefaultIdMAX();

    private static native int nativeGetDefineWifiIdMAX();

    private native int nativeInitBLENetwork(long j, Object obj, BluetoothDevice bluetoothDevice, int i, int[] iArr);

    private native int nativeInitMuxNetwork(long j, long j2);

    private native int nativeInitWifiNetwork(long j, String str, int i, int i2, int i3);

    private native long nativeNew();

    private native int nativeSetRecvBufferSize(long j, int i);

    private native int nativeSetRecvClassSelector(long j, int i);

    private native int nativeSetSendBufferSize(long j, int i);

    private native int nativeSetSendClassSelector(long j, int i);

    private native int nativeUnlock(long j);

    static {
        ARNETWORKAL_MANAGER_DEFAULT_ID_MAX = 0;
        ARNETWORKAL_MANAGER_WIFI_ID_MAX = 0;
        ARNETWORKAL_MANAGER_BLE_ID_MAX = 0;
        ARNETWORKAL_MANAGER_DEFAULT_ID_MAX = nativeGetDefineDefaultIdMAX();
        ARNETWORKAL_MANAGER_WIFI_ID_MAX = nativeGetDefineWifiIdMAX();
        ARNETWORKAL_MANAGER_BLE_ID_MAX = nativeGetDefineBleIdMAX();
    }

    public ARNetworkALManager() {
        if (this.m_managerPtr != 0) {
            this.m_initOk = true;
        }
    }

    public void dispose() {
        if (this.m_initOk) {
            nativeDelete(this.m_managerPtr);
            this.m_managerPtr = 0;
            this.m_initOk = false;
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public long getManager() {
        return this.m_managerPtr;
    }

    public boolean isCorrectlyInitialized() {
        return this.m_initOk;
    }

    public ARNETWORKAL_ERROR_ENUM initWifiNetwork(String addr, int sendingPort, int receivingPort, int recvTimeoutSec) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        if (addr != null) {
            return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeInitWifiNetwork(this.m_managerPtr, addr, sendingPort, receivingPort, recvTimeoutSec));
        }
        return error;
    }

    public ARNETWORKAL_ERROR_ENUM unlock() {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeUnlock(this.m_managerPtr));
    }

    public ARNETWORKAL_ERROR_ENUM closeWifiNetwork() {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeCloseWifiNetwork(this.m_managerPtr));
    }

    public ARNETWORKAL_ERROR_ENUM setSendBufferSize(int bufferSize) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeSetSendBufferSize(this.m_managerPtr, bufferSize));
    }

    public ARNETWORKAL_ERROR_ENUM setRecvBufferSize(int bufferSize) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeSetRecvBufferSize(this.m_managerPtr, bufferSize));
    }

    public ARNETWORKAL_ERROR_ENUM setSendClassSelector(ARSAL_SOCKET_CLASS_SELECTOR_ENUM cs) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeSetSendClassSelector(this.m_managerPtr, cs.getValue()));
    }

    public ARNETWORKAL_ERROR_ENUM setRecvClassSelector(ARSAL_SOCKET_CLASS_SELECTOR_ENUM cs) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeSetRecvClassSelector(this.m_managerPtr, cs.getValue()));
    }

    public ARNETWORKAL_ERROR_ENUM enableDataDump(String logDir, String name) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeEnableDataDump(this.m_managerPtr, logDir, name));
    }

    public ARNETWORKAL_ERROR_ENUM dumpData(ARNativeData data, byte tag) {
        return dumpData(data, data.getDataSize(), tag);
    }

    public ARNETWORKAL_ERROR_ENUM dumpData(ARNativeData data, int maxSize, byte tag) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        int totalsize = data.getDataSize();
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeDumpData(this.m_managerPtr, tag, data.getData(), totalsize, maxSize < totalsize ? maxSize : totalsize));
    }

    public ARNETWORKAL_ERROR_ENUM initBLENetwork(Context context, BluetoothDevice device, int recvTimeoutSec, int[] notificationIDArray) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK;
        if (context == null) {
            error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BAD_PARAMETER;
        }
        if (error == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK && !context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_NETWORK_TYPE;
        }
        if (error == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK) {
            return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeInitBLENetwork(this.m_managerPtr, context.getApplicationContext(), device, recvTimeoutSec, notificationIDArray));
        }
        return error;
    }

    public ARNETWORKAL_ERROR_ENUM cancelBLENetwork() {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK;
        if (!this.m_initOk) {
            error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        }
        if (error == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK) {
            return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeCancelBLENetwork(this.m_managerPtr));
        }
        return error;
    }

    public ARNETWORKAL_ERROR_ENUM closeBLENetwork(Context context) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK;
        if (context == null) {
            error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BAD_PARAMETER;
        }
        if (error == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK && !context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_NETWORK_TYPE;
        }
        if (error == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK) {
            return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeCloseBLENetwork(this.m_managerPtr));
        }
        return error;
    }

    public ARNETWORKAL_ERROR_ENUM initMuxNetwork(Mux mux) {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        if (mux == null) {
            return error;
        }
        Ref muxref = mux.newMuxRef();
        int intError = nativeInitMuxNetwork(this.m_managerPtr, muxref.getCPtr());
        muxref.release();
        return ARNETWORKAL_ERROR_ENUM.getFromValue(intError);
    }

    public ARNETWORKAL_ERROR_ENUM closeMuxNetwork() {
        ARNETWORKAL_ERROR_ENUM error = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR;
        return ARNETWORKAL_ERROR_ENUM.getFromValue(nativeCloseMuxNetwork(this.m_managerPtr));
    }

    public String getCId() {
        return String.format("0x%08x", new Object[]{Long.valueOf(this.m_managerPtr)});
    }
}
