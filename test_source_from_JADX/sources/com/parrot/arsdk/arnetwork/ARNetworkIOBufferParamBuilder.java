package com.parrot.arsdk.arnetwork;

import com.parrot.arsdk.arnetworkal.ARNETWORKAL_FRAME_TYPE_ENUM;

public class ARNetworkIOBufferParamBuilder {
    private static final String TAG = ARNetworkIOBufferParamBuilder.class.getSimpleName();
    private int ID;
    private int ackTimeoutMs = ARNetworkIOBufferParam.ARNETWORK_IOBUFFERPARAM_INFINITE_NUMBER;
    private int dataCopyMaxSize = 0;
    private ARNETWORKAL_FRAME_TYPE_ENUM dataType = ARNETWORKAL_FRAME_TYPE_ENUM.ARNETWORKAL_FRAME_TYPE_DATA;
    private boolean isOverwriting = false;
    private int numberOfCell = 1;
    private int numberOfRetry = ARNetworkIOBufferParam.ARNETWORK_IOBUFFERPARAM_INFINITE_NUMBER;
    private int timeBetweenSendMs = 1;

    public ARNetworkIOBufferParamBuilder(int id) {
        this.ID = id;
    }

    public ARNetworkIOBufferParamBuilder setDataType(ARNETWORKAL_FRAME_TYPE_ENUM type) {
        this.dataType = type;
        return this;
    }

    public ARNetworkIOBufferParamBuilder setTimeBetweenSendMs(int time) {
        this.timeBetweenSendMs = time;
        return this;
    }

    public ARNetworkIOBufferParamBuilder setAckTimeoutMs(int time) {
        this.ackTimeoutMs = time;
        return this;
    }

    public ARNetworkIOBufferParamBuilder setNumberOfRetry(int number) {
        this.numberOfRetry = number;
        return this;
    }

    public ARNetworkIOBufferParamBuilder setNumberOfCell(int number) {
        this.numberOfCell = number;
        return this;
    }

    public ARNetworkIOBufferParamBuilder setDataCopyMaxSize(int size) {
        this.dataCopyMaxSize = size;
        return this;
    }

    public ARNetworkIOBufferParamBuilder setOverwriting(boolean enable) {
        this.isOverwriting = enable;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\n    ID = ");
        stringBuilder.append(this.ID);
        stringBuilder.append("\n");
        stringBuilder.append("    dataType = ");
        stringBuilder.append(this.dataType);
        stringBuilder.append("\n");
        stringBuilder.append("    timeBetweenSendMs = ");
        stringBuilder.append(this.timeBetweenSendMs);
        stringBuilder.append("\n");
        stringBuilder.append("    ackTimeoutMs = ");
        stringBuilder.append(this.ackTimeoutMs);
        stringBuilder.append("\n");
        stringBuilder.append("    numberOfRetry = ");
        stringBuilder.append(this.numberOfRetry);
        stringBuilder.append("\n");
        stringBuilder.append("    numberOfCell = ");
        stringBuilder.append(this.numberOfCell);
        stringBuilder.append("\n");
        stringBuilder.append("    dataCopyMaxSize = ");
        stringBuilder.append(this.dataCopyMaxSize);
        stringBuilder.append("\n");
        stringBuilder.append("    isOverwriting = ");
        stringBuilder.append(this.isOverwriting);
        stringBuilder.append("\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public ARNetworkIOBufferParam build() {
        return new ARNetworkIOBufferParam(this.ID, this.dataType, this.timeBetweenSendMs, this.ackTimeoutMs, this.numberOfRetry, this.numberOfCell, this.dataCopyMaxSize, this.isOverwriting);
    }
}
