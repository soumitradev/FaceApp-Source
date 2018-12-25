package com.parrot.arsdk.ardiscovery;

import java.io.UnsupportedEncodingException;

public abstract class ARDiscoveryConnection {
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_CLIENT_CONTROL_PORT_KEY = nativeGetDefineJsonARStream2ClientControlPortKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_CLIENT_STREAM_PORT_KEY = nativeGetDefineJsonARStream2ClientStreamPortKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_MAX_BITRATE_KEY = nativeGetDefineJsonARStream2MaxBitrateKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_MAX_LATENCY_KEY = nativeGetDefineJsonARStream2MaxLatencyKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_MAX_NETWORK_LATENCY_KEY = nativeGetDefineJsonARStream2MaxNetworkLatencyKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_MAX_PACKET_SIZE_KEY = nativeGetDefineJsonARStream2MaxPacketSizeKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_PARAMETER_SETS_KEY = nativeGetDefineJsonARStream2ParameterSetsKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_SERVER_CONTROL_PORT_KEY = nativeGetDefineJsonARStream2ServerControlPortKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_SERVER_STREAM_PORT_KEY = nativeGetDefineJsonARStream2ServerStreamPortKey();
    /* renamed from: ARDISCOVERY_CONNECTION_JSON_ARSTREAM2_SUPPORTED_METADATA_VERSION_KEY */
    public static final String f1713x98fe726d = nativeGetDefineJsonARStream2SupportedMetadataVersionKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM_FRAGMENT_MAXIMUM_NUMBER_KEY = nativeGetDefineJsonARStreamFragmentMaximumNumberKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM_FRAGMENT_SIZE_KEY = nativeGetDefineJsonARStreamFragmentSizeKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_ARSTREAM_MAX_ACK_INTERVAL_KEY = nativeGetDefineJsonARStreamMaxAckIntervalKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_AUDIO_CODEC_VERSION_KEY = nativeGetDefineJsonAudioCodecVersionKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_C2DPORT_KEY = nativeGetDefineJsonC2DPortKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_C2D_UPDATE_PORT_KEY = nativeGetDefineJsonC2DUpdatePortKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_C2D_USER_PORT_KEY = nativeGetDefineJsonC2DUserPortKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_CONTROLLER_NAME_KEY = nativeGetDefineJsonControllerNameKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_CONTROLLER_TYPE_KEY = nativeGetDefineJsonControllerTypeKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_D2CPORT_KEY = nativeGetDefineJsonD2CPortKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_DEVICE_ID_KEY = nativeGetDefineJsonDeviceNameIdKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_FEATURES_KEY = nativeGetDefineJsonFeaturesKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_QOS_MODE_KEY = nativeGetDefineJsonQosModeKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_SKYCONTROLLER_VERSION = nativeGetDefineJsonSkyControllerVersionKey();
    public static final String ARDISCOVERY_CONNECTION_JSON_STATUS_KEY = nativeGetDefineJsonStatusKey();
    public static final int ARDISCOVERY_CONNECTION_SEND_JSON_SIZE = (nativeGetDefineTxBufferSize() - 1);
    private static String TAG = "ARDiscoveryConnection";
    private boolean initOk = false;
    private long nativeARDiscoveryConnection = nativeNew();

    private class ARDiscoveryConnectionCallbackReturn {
        private String dataTx;
        private int error;

        public ARDiscoveryConnectionCallbackReturn() {
            this.error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK.getValue();
            this.dataTx = null;
        }

        public ARDiscoveryConnectionCallbackReturn(String dataTx, int error) {
            this.error = error;
            this.dataTx = dataTx;
        }

        public void setError(int error) {
            this.error = error;
        }

        public void setDataTx(String dataTx) {
            this.dataTx = dataTx;
        }

        public int getError() {
            return this.error;
        }

        public String getDataTx() {
            return this.dataTx;
        }
    }

    private native int nativeControllerConnection(long j, int i, String str);

    private native void nativeControllerConnectionAbort(long j);

    private native int nativeDelete(long j);

    private native int nativeDeviceListeningLoop(long j, int i);

    private native int nativeDeviceStopListening(long j);

    private static native String nativeGetDefineJsonARStream2ClientControlPortKey();

    private static native String nativeGetDefineJsonARStream2ClientStreamPortKey();

    private static native String nativeGetDefineJsonARStream2MaxBitrateKey();

    private static native String nativeGetDefineJsonARStream2MaxLatencyKey();

    private static native String nativeGetDefineJsonARStream2MaxNetworkLatencyKey();

    private static native String nativeGetDefineJsonARStream2MaxPacketSizeKey();

    private static native String nativeGetDefineJsonARStream2ParameterSetsKey();

    private static native String nativeGetDefineJsonARStream2ServerControlPortKey();

    private static native String nativeGetDefineJsonARStream2ServerStreamPortKey();

    private static native String nativeGetDefineJsonARStream2SupportedMetadataVersionKey();

    private static native String nativeGetDefineJsonARStreamFragmentMaximumNumberKey();

    private static native String nativeGetDefineJsonARStreamFragmentSizeKey();

    private static native String nativeGetDefineJsonARStreamMaxAckIntervalKey();

    private static native String nativeGetDefineJsonAudioCodecVersionKey();

    private static native String nativeGetDefineJsonC2DPortKey();

    private static native String nativeGetDefineJsonC2DUpdatePortKey();

    private static native String nativeGetDefineJsonC2DUserPortKey();

    private static native String nativeGetDefineJsonControllerNameKey();

    private static native String nativeGetDefineJsonControllerTypeKey();

    private static native String nativeGetDefineJsonD2CPortKey();

    private static native String nativeGetDefineJsonDeviceNameIdKey();

    private static native String nativeGetDefineJsonFeaturesKey();

    private static native String nativeGetDefineJsonQosModeKey();

    private static native String nativeGetDefineJsonSkyControllerVersionKey();

    private static native String nativeGetDefineJsonStatusKey();

    private static native int nativeGetDefineTxBufferSize();

    private native long nativeNew();

    private static native void nativeStaticInit();

    protected abstract ARDISCOVERY_ERROR_ENUM onReceiveJson(String str, String str2);

    protected abstract String onSendJson();

    static {
        nativeStaticInit();
    }

    public ARDiscoveryConnection() {
        if (this.nativeARDiscoveryConnection != 0) {
            this.initOk = true;
        }
    }

    public ARDISCOVERY_ERROR_ENUM dispose() {
        ARDISCOVERY_ERROR_ENUM error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARDISCOVERY_ERROR_ENUM.getFromValue(nativeDelete(this.nativeARDiscoveryConnection));
                if (error == ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK) {
                    this.nativeARDiscoveryConnection = 0;
                    this.initOk = false;
                }
            }
        }
        return error;
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public ARDISCOVERY_ERROR_ENUM ControllerConnection(int port, String ip) {
        return ARDISCOVERY_ERROR_ENUM.getFromValue(nativeControllerConnection(this.nativeARDiscoveryConnection, port, ip));
    }

    public void ControllerConnectionAbort() {
        synchronized (this) {
            if (this.initOk) {
                nativeControllerConnectionAbort(this.nativeARDiscoveryConnection);
            }
        }
    }

    public ARDISCOVERY_ERROR_ENUM DeviceListeningLoop(int port) {
        if (this.initOk) {
            nativeDeviceListeningLoop(this.nativeARDiscoveryConnection, port);
        }
        return ARDISCOVERY_ERROR_ENUM.getFromValue(0);
    }

    public void DeviceStopListening() {
        if (this.initOk) {
            nativeDeviceStopListening(this.nativeARDiscoveryConnection);
        }
    }

    private ARDiscoveryConnectionCallbackReturn sendJsonCallback() {
        ARDiscoveryConnectionCallbackReturn callbackReturn = new ARDiscoveryConnectionCallbackReturn();
        ARDISCOVERY_ERROR_ENUM callbackError = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK;
        callbackReturn.setDataTx(onSendJson());
        return callbackReturn;
    }

    private int receiveJsonCallback(byte[] dataRx, String ip) {
        ARDISCOVERY_ERROR_ENUM callbackError = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK;
        String dataRxString = null;
        if (dataRx != null) {
            try {
                dataRxString = new String(dataRx, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                callbackError = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_ERROR;
                e.printStackTrace();
            }
            callbackError = onReceiveJson(dataRxString, ip);
        }
        return callbackError.getValue();
    }
}
