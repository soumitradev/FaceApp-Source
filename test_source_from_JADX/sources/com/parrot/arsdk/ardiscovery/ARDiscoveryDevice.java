package com.parrot.arsdk.ardiscovery;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.parrot.arsdk.arnetworkal.ARNETWORKAL_ERROR_ENUM;
import com.parrot.arsdk.arnetworkal.ARNetworkALManager;
import com.parrot.arsdk.arsal.ARSALPrint;
import com.parrot.mux.Mux;
import com.parrot.mux.Mux.Ref;

public class ARDiscoveryDevice {
    public static int ROLLINGSPIDER_CONTROLLER_TO_DEVICE_ACK_ID;
    public static int ROLLINGSPIDER_CONTROLLER_TO_DEVICE_EMERGENCY_ID;
    public static int ROLLINGSPIDER_CONTROLLER_TO_DEVICE_NONACK_ID;
    public static int ROLLINGSPIDER_DEVICE_TO_CONTROLLER_EVENT_ID;
    public static int ROLLINGSPIDER_DEVICE_TO_CONTROLLER_NAVDATA_ID;
    private static String TAG = "ARDiscoveryDevice";
    BLEPart blePart;
    private boolean initOk;
    private long nativeARDiscoveryDevice;

    private class BLEPart {
        public ARNetworkALManager alManager;
        public BluetoothDevice bleDevice;
        public int[] bleNotificationIDs;
        Context context;

        public long newARNetworkAL() {
            this.alManager = new ARNetworkALManager();
            ARNETWORKAL_ERROR_ENUM netALError = this.alManager.initBLENetwork(this.context.getApplicationContext(), this.bleDevice, 1, this.bleNotificationIDs);
            return this.alManager.getManager();
        }

        public int deleteARNetworkAL() {
            ARDISCOVERY_ERROR_ENUM error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK;
            if (this.alManager != null) {
                this.alManager.unlock();
                this.alManager.closeBLENetwork(this.context);
                this.alManager.dispose();
                this.alManager = null;
            } else {
                error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_ERROR_BAD_PARAMETER;
            }
            return error.getValue();
        }
    }

    private native void nativeDelete(long j);

    private static native int nativeGetCToDAckId();

    private static native int nativeGetCToDEmergencyId();

    private static native int nativeGetCToDNonAckId();

    private static native int nativeGetDToCEventId();

    private static native int nativeGetDToCNavDataId();

    private native int nativeInitBLE(long j, int i, BLEPart bLEPart);

    private native int nativeInitUsb(long j, int i, long j2);

    private native int nativeInitWifi(long j, int i, String str, String str2, int i2);

    private native long nativeNew() throws ARDiscoveryException;

    private static native void nativeStaticInit();

    static {
        ROLLINGSPIDER_CONTROLLER_TO_DEVICE_NONACK_ID = 0;
        ROLLINGSPIDER_CONTROLLER_TO_DEVICE_ACK_ID = 0;
        ROLLINGSPIDER_CONTROLLER_TO_DEVICE_EMERGENCY_ID = 0;
        ROLLINGSPIDER_DEVICE_TO_CONTROLLER_NAVDATA_ID = 0;
        ROLLINGSPIDER_DEVICE_TO_CONTROLLER_EVENT_ID = 0;
        nativeStaticInit();
        ROLLINGSPIDER_CONTROLLER_TO_DEVICE_NONACK_ID = nativeGetCToDNonAckId();
        ROLLINGSPIDER_CONTROLLER_TO_DEVICE_ACK_ID = nativeGetCToDAckId();
        ROLLINGSPIDER_CONTROLLER_TO_DEVICE_EMERGENCY_ID = nativeGetCToDEmergencyId();
        ROLLINGSPIDER_DEVICE_TO_CONTROLLER_NAVDATA_ID = nativeGetDToCNavDataId();
        ROLLINGSPIDER_DEVICE_TO_CONTROLLER_EVENT_ID = nativeGetDToCEventId();
    }

    public ARDiscoveryDevice() throws ARDiscoveryException {
        ARSALPrint.m530d(TAG, "ARDiscoveryDevice ...");
        this.initOk = false;
        this.nativeARDiscoveryDevice = nativeNew();
        if (this.nativeARDiscoveryDevice != 0) {
            this.initOk = true;
        }
    }

    public ARDiscoveryDevice(Context ctx, ARDiscoveryDeviceService service) throws ARDiscoveryException {
        ARDISCOVERY_ERROR_ENUM err;
        ARDISCOVERY_ERROR_ENUM err2;
        this();
        ARDISCOVERY_PRODUCT_ENUM product = ARDiscoveryService.getProductFromProductID(service.getProductID());
        switch (service.getNetworkType()) {
            case ARDISCOVERY_NETWORK_TYPE_NET:
                ARDiscoveryDeviceNetService ns = (ARDiscoveryDeviceNetService) service.getDevice();
                err = initWifi(product, service.getName(), ns.getIp(), ns.getPort());
                break;
            case ARDISCOVERY_NETWORK_TYPE_BLE:
                err = initBLE(product, ctx.getApplicationContext(), ((ARDiscoveryDeviceBLEService) service.getDevice()).getBluetoothDevice());
                break;
            case ARDISCOVERY_NETWORK_TYPE_USBMUX:
                err2 = initUSB(product, UsbAccessoryMux.get(ctx.getApplicationContext()).getMux());
                break;
            default:
                err2 = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_ERROR_BAD_PARAMETER;
                break;
        }
        err2 = err;
        if (err2 != ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK) {
            throw new ARDiscoveryException(err2.getValue());
        }
    }

    public void dispose() {
        ARSALPrint.m530d(TAG, "dispose ...");
        ARDISCOVERY_ERROR_ENUM error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK;
        synchronized (this) {
            if (this.initOk) {
                nativeDelete(this.nativeARDiscoveryDevice);
                this.nativeARDiscoveryDevice = 0;
                this.initOk = false;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public ARDISCOVERY_ERROR_ENUM initWifi(ARDISCOVERY_PRODUCT_ENUM product, String name, String address, int port) {
        ARSALPrint.m530d(TAG, "initWifi ...");
        ARDISCOVERY_ERROR_ENUM error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK;
        synchronized (this) {
            if (this.initOk) {
                if (product != null) {
                    error = ARDISCOVERY_ERROR_ENUM.getFromValue(nativeInitWifi(this.nativeARDiscoveryDevice, product.getValue(), name, address, port));
                } else {
                    error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_ERROR_BAD_PARAMETER;
                }
            }
        }
        return error;
    }

    public ARDISCOVERY_ERROR_ENUM initBLE(ARDISCOVERY_PRODUCT_ENUM product, Context context, BluetoothDevice bleDevice) {
        ARDISCOVERY_ERROR_ENUM error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK;
        synchronized (this) {
            if (this.initOk) {
                if (product != null) {
                    this.blePart = new BLEPart();
                    int ackOffset = ARNetworkALManager.ARNETWORKAL_MANAGER_BLE_ID_MAX / 2;
                    int[] iArr = new int[]{ROLLINGSPIDER_DEVICE_TO_CONTROLLER_NAVDATA_ID, ROLLINGSPIDER_DEVICE_TO_CONTROLLER_EVENT_ID, ROLLINGSPIDER_CONTROLLER_TO_DEVICE_ACK_ID + ackOffset, ROLLINGSPIDER_CONTROLLER_TO_DEVICE_EMERGENCY_ID + ackOffset};
                    this.blePart.bleNotificationIDs = iArr;
                    this.blePart.context = context;
                    this.blePart.bleDevice = bleDevice;
                    error = ARDISCOVERY_ERROR_ENUM.getFromValue(nativeInitBLE(this.nativeARDiscoveryDevice, product.getValue(), this.blePart));
                } else {
                    error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_ERROR_BAD_PARAMETER;
                }
            }
        }
        return error;
    }

    public ARDISCOVERY_ERROR_ENUM initUSB(ARDISCOVERY_PRODUCT_ENUM product, Mux mux) {
        ARDISCOVERY_ERROR_ENUM error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_OK;
        synchronized (this) {
            if (this.initOk) {
                if (product != null) {
                    Ref muxRef = mux.newMuxRef();
                    int nativeError = nativeInitUsb(this.nativeARDiscoveryDevice, product.getValue(), muxRef.getCPtr());
                    muxRef.release();
                    error = ARDISCOVERY_ERROR_ENUM.getFromValue(nativeError);
                } else {
                    error = ARDISCOVERY_ERROR_ENUM.ARDISCOVERY_ERROR_BAD_PARAMETER;
                }
            }
        }
        return error;
    }

    public long getNativeDevice() {
        return this.nativeARDiscoveryDevice;
    }
}
