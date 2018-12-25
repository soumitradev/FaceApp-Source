package com.parrot.arsdk.arnetwork;

import com.parrot.arsdk.arnetworkal.ARNetworkALManager;
import com.parrot.arsdk.arsal.ARNativeData;
import com.parrot.arsdk.arsal.ARSALPrint;

public abstract class ARNetworkManager {
    private static final String TAG = "NetworkManager";
    private ARNetworkALManager alManager;
    private boolean m_initOk = false;
    public ReceivingRunnable m_receivingRunnable;
    public SendingRunnable m_sendingRunnable;
    private long nativeManager;

    private native int nativeDelete(long j);

    private native int nativeFlush(long j);

    private native long nativeNew(long j, int i, Object[] objArr, int i2, Object[] objArr2, int i3, int i4);

    private native int nativeReadData(long j, int i, long j2, int i2, ARNativeData aRNativeData);

    private native int nativeReadDataWithTimeout(long j, int i, long j2, int i2, ARNativeData aRNativeData, int i3);

    private native int nativeSendData(long j, int i, ARNativeData aRNativeData, long j2, int i2, Object obj, int i3);

    private static native void nativeStaticInit();

    private native void nativeStop(long j);

    private native int nativeTryReadData(long j, int i, long j2, int i2, ARNativeData aRNativeData);

    public abstract ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM onCallback(int i, ARNativeData aRNativeData, ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM arnetwork_manager_callback_status_enum, Object obj);

    public abstract void onDisconnect(ARNetworkALManager aRNetworkALManager);

    static {
        nativeStaticInit();
    }

    public ARNetworkManager(ARNetworkALManager osSpecificManager, ARNetworkIOBufferParam[] inputParamArray, ARNetworkIOBufferParam[] outputParamArray, int timeBetweenPingsMs) {
        int error = ARNETWORK_ERROR_ENUM.ARNETWORK_OK.getValue();
        this.nativeManager = nativeNew(osSpecificManager.getManager(), inputParamArray.length, inputParamArray, outputParamArray.length, outputParamArray, timeBetweenPingsMs, error);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error:");
        stringBuilder.append(error);
        ARSALPrint.m530d(str, stringBuilder.toString());
        if (this.nativeManager != 0) {
            this.m_initOk = true;
            this.m_sendingRunnable = new SendingRunnable(this.nativeManager);
            this.m_receivingRunnable = new ReceivingRunnable(this.nativeManager);
            this.alManager = osSpecificManager;
        }
    }

    public void dispose() {
        if (this.m_initOk) {
            nativeDelete(this.nativeManager);
            this.nativeManager = 0;
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

    public void stop() {
        if (this.m_initOk) {
            nativeStop(this.nativeManager);
        }
    }

    public ARNETWORK_ERROR_ENUM Flush() {
        ARNETWORK_ERROR_ENUM error = ARNETWORK_ERROR_ENUM.ARNETWORK_OK;
        if (this.m_initOk) {
            return ARNETWORK_ERROR_ENUM.getFromValue(nativeFlush(this.nativeManager));
        }
        return ARNETWORK_ERROR_ENUM.ARNETWORK_ERROR_BAD_PARAMETER;
    }

    public ARNETWORK_ERROR_ENUM sendData(int inputBufferID, ARNativeData arData, Object customData, boolean doDataCopy) {
        ARNativeData aRNativeData = arData;
        ARNETWORK_ERROR_ENUM error = ARNETWORK_ERROR_ENUM.ARNETWORK_OK;
        boolean doDataCopyInt = doDataCopy;
        if (!this.m_initOk || aRNativeData == null) {
            return ARNETWORK_ERROR_ENUM.ARNETWORK_ERROR_BAD_PARAMETER;
        }
        ARNativeData jData;
        long dataPtr = arData.getData();
        int dataSize = arData.getDataSize();
        if (doDataCopy) {
            jData = new ARNativeData(aRNativeData);
            jData.setUsedSize(arData.getDataSize());
        } else {
            jData = aRNativeData;
        }
        ARNativeData jData2 = jData;
        ARNativeData jData3 = jData2;
        ARNETWORK_ERROR_ENUM error2 = ARNETWORK_ERROR_ENUM.getFromValue(nativeSendData(r10.nativeManager, inputBufferID, jData2, dataPtr, dataSize, customData, doDataCopyInt));
        if (error2 != ARNETWORK_ERROR_ENUM.ARNETWORK_OK && doDataCopy) {
            jData3.dispose();
        }
        return error2;
    }

    public ARNETWORK_ERROR_ENUM readData(int outputBufferID, ARNativeData data) {
        ARNETWORK_ERROR_ENUM error = ARNETWORK_ERROR_ENUM.ARNETWORK_OK;
        if (!this.m_initOk) {
            return ARNETWORK_ERROR_ENUM.ARNETWORK_ERROR_BAD_PARAMETER;
        }
        return ARNETWORK_ERROR_ENUM.getFromValue(nativeReadData(this.nativeManager, outputBufferID, data.getData(), data.getCapacity(), data));
    }

    public ARNETWORK_ERROR_ENUM tryReadData(int outputBufferID, ARNativeData data) {
        ARNETWORK_ERROR_ENUM error = ARNETWORK_ERROR_ENUM.ARNETWORK_OK;
        if (!this.m_initOk) {
            return ARNETWORK_ERROR_ENUM.ARNETWORK_ERROR_BAD_PARAMETER;
        }
        return ARNETWORK_ERROR_ENUM.getFromValue(nativeTryReadData(this.nativeManager, outputBufferID, data.getData(), data.getCapacity(), data));
    }

    public ARNETWORK_ERROR_ENUM readDataWithTimeout(int outputBufferID, ARNativeData data, int timeoutMs) {
        ARNETWORK_ERROR_ENUM error = ARNETWORK_ERROR_ENUM.ARNETWORK_OK;
        if (!this.m_initOk) {
            return ARNETWORK_ERROR_ENUM.ARNETWORK_ERROR_BAD_PARAMETER;
        }
        return ARNETWORK_ERROR_ENUM.getFromValue(nativeReadDataWithTimeout(this.nativeManager, outputBufferID, data.getData(), data.getCapacity(), data, timeoutMs));
    }

    public long getManager() {
        return this.nativeManager;
    }

    public boolean isCorrectlyInitialized() {
        return this.m_initOk;
    }

    private int callback(int IoBufferId, ARNativeData data, int status, Object customData) {
        ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM jStatus = ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM.getFromValue(status);
        ARNETWORK_MANAGER_CALLBACK_RETURN_ENUM retVal = onCallback(IoBufferId, data, jStatus, customData);
        if (jStatus == ARNETWORK_MANAGER_CALLBACK_STATUS_ENUM.ARNETWORK_MANAGER_CALLBACK_STATUS_DONE) {
            data.dispose();
        }
        return retVal.getValue();
    }

    private void disconnectCallback() {
        onDisconnect(this.alManager);
    }

    public String getCId() {
        return String.format("0x%08x", new Object[]{Long.valueOf(this.nativeManager)});
    }
}
