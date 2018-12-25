package com.parrot.arsdk.arnetwork;

import com.parrot.arsdk.arnetworkal.ARNETWORKAL_FRAME_TYPE_ENUM;
import com.parrot.arsdk.arsal.ARSALPrint;

public class ARNetworkIOBufferParam implements Cloneable {
    public static int ARNETWORK_IOBUFFERPARAM_DATACOPYMAXSIZE_USE_MAX = nativeStaticGetDataCopyMaxSizeUseMax();
    public static int ARNETWORK_IOBUFFERPARAM_INFINITE_NUMBER = nativeStaticGetInfiniteNumber();
    private static final String TAG = ARNetworkIOBufferParam.class.getSimpleName();
    private int ackTimeoutMs;
    private long cIOBufferParam = nativeNew();
    private int copyMaxSize;
    private ARNETWORKAL_FRAME_TYPE_ENUM dataType;
    private int id;
    private boolean isOverwriting;
    private int numberOfCell;
    private int numberOfRetry;
    private int timeBetweenSend;

    private native void nativeDelete(long j);

    private native int nativeGetAckTimeoutMs(long j);

    private native int nativeGetDataCopyMaxSize(long j);

    private native int nativeGetDataType(long j);

    private native int nativeGetId(long j);

    private native boolean nativeGetIsOverwriting(long j);

    private native int nativeGetNumberOfCell(long j);

    private native int nativeGetNumberOfRetry(long j);

    private native int nativeGetTimeBetweenSend(long j);

    private native long nativeNew();

    private native void nativeSetAckTimeoutMs(long j, int i);

    private native void nativeSetDataCopyMaxSize(long j, int i);

    private native void nativeSetDataType(long j, int i);

    private native void nativeSetId(long j, int i);

    private native void nativeSetIsOverwriting(long j, boolean z);

    private native void nativeSetNumberOfCell(long j, int i);

    private native void nativeSetNumberOfRetry(long j, int i);

    private native void nativeSetTimeBetweenSend(long j, int i);

    private static native int nativeStaticGetDataCopyMaxSizeUseMax();

    private static native int nativeStaticGetInfiniteNumber();

    public ARNetworkIOBufferParam(int id, ARNETWORKAL_FRAME_TYPE_ENUM dataType, int timeBetweenSendMs, int ackTimeoutMs, int numberOfRetry, int numberOfCell, int dataCopyMaxSize, boolean isOverwriting) {
        nativeSetId(this.cIOBufferParam, id);
        nativeSetDataType(this.cIOBufferParam, dataType.getValue());
        nativeSetTimeBetweenSend(this.cIOBufferParam, timeBetweenSendMs);
        nativeSetAckTimeoutMs(this.cIOBufferParam, ackTimeoutMs);
        nativeSetNumberOfRetry(this.cIOBufferParam, numberOfRetry);
        nativeSetNumberOfCell(this.cIOBufferParam, numberOfCell);
        nativeSetDataCopyMaxSize(this.cIOBufferParam, dataCopyMaxSize);
        nativeSetIsOverwriting(this.cIOBufferParam, isOverwriting);
        this.id = id;
        this.dataType = dataType;
        this.timeBetweenSend = this.timeBetweenSend;
        this.ackTimeoutMs = ackTimeoutMs;
        this.numberOfRetry = numberOfRetry;
        this.numberOfCell = numberOfCell;
        this.copyMaxSize = dataCopyMaxSize;
        this.isOverwriting = isOverwriting;
    }

    public void updateFromNative() {
        this.id = nativeGetId(this.cIOBufferParam);
        this.dataType = ARNETWORKAL_FRAME_TYPE_ENUM.getFromValue(nativeGetDataType(this.cIOBufferParam));
        this.timeBetweenSend = nativeGetTimeBetweenSend(this.cIOBufferParam);
        this.ackTimeoutMs = nativeGetAckTimeoutMs(this.cIOBufferParam);
        this.numberOfRetry = nativeGetNumberOfRetry(this.cIOBufferParam);
        this.numberOfCell = nativeGetNumberOfCell(this.cIOBufferParam);
        this.copyMaxSize = nativeGetDataCopyMaxSize(this.cIOBufferParam);
        this.isOverwriting = nativeGetIsOverwriting(this.cIOBufferParam);
    }

    public void dispose() {
        if (this.cIOBufferParam != 0) {
            nativeDelete(this.cIOBufferParam);
            this.cIOBufferParam = 0;
        }
    }

    public void finalize() throws Throwable {
        try {
            if (this.cIOBufferParam != 0) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Object ");
                stringBuilder.append(toString());
                stringBuilder.append(" was not disposed by the application !");
                ARSALPrint.m532e(str, stringBuilder.toString());
                dispose();
            }
            super.finalize();
        } catch (Throwable th) {
            super.finalize();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("< id = ");
        stringBuilder.append(this.id);
        sb.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" | dataType = ");
        stringBuilder.append(this.dataType);
        sb.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" | timeBetweenSend = ");
        stringBuilder.append(this.timeBetweenSend);
        sb.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" | ackTimeoutMs = ");
        stringBuilder.append(this.ackTimeoutMs);
        sb.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" | numberOfRetry = ");
        stringBuilder.append(this.numberOfRetry);
        sb.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" | numberOfCell = ");
        stringBuilder.append(this.numberOfCell);
        sb.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" | copyMaxSize = ");
        stringBuilder.append(this.copyMaxSize);
        sb.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" | isOverwriting = ");
        stringBuilder.append(this.isOverwriting);
        stringBuilder.append(" >");
        sb.append(stringBuilder.toString());
        return sb.toString();
    }

    public long getNativePointer() {
        return this.cIOBufferParam;
    }

    public int getId() {
        return this.id;
    }

    public ARNETWORKAL_FRAME_TYPE_ENUM getDataType() {
        return this.dataType;
    }

    public int getTimeBetweenSend() {
        return this.timeBetweenSend;
    }

    public int getAckTimeoutMs() {
        return this.ackTimeoutMs;
    }

    public int getNumberOfRetry() {
        return this.numberOfRetry;
    }

    public int getNumberOfCell() {
        return this.numberOfCell;
    }

    public int getCopyMaxSize() {
        return this.copyMaxSize;
    }

    public boolean getIsOverwriting() {
        return this.isOverwriting;
    }

    public ARNetworkIOBufferParam clone() {
        return new ARNetworkIOBufferParam(this.id, this.dataType, this.timeBetweenSend, this.ackTimeoutMs, this.numberOfRetry, this.numberOfCell, this.copyMaxSize, this.isOverwriting);
    }
}
