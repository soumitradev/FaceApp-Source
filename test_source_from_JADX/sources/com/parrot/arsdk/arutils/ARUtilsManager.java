package com.parrot.arsdk.arutils;

import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_NETWORK_TYPE_ENUM;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_FAMILY_ENUM;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceNetService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryService;
import com.parrot.arsdk.ardiscovery.UsbAccessoryMux;
import com.parrot.arsdk.arsal.ARSALBLEManager;
import com.parrot.mux.Mux;
import com.parrot.mux.Mux.Ref;
import java.util.concurrent.Semaphore;

public class ARUtilsManager {
    public static final String FTP_ANONYMOUS = "anonymous";
    private static final int FTP_FLIGHTPLAN = 61;
    private static final int FTP_FLIGHTPLAN_SKY = 161;
    private static final int FTP_GENERIC = 21;
    private static final int FTP_GENERIC_SKY = 121;
    private static final int FTP_UPDATE = 51;
    private static final int FTP_UPDATE_SKY = 151;
    private static final String TAG = "ARUtilsManager";
    private boolean mIsBLEFtpInited = false;
    private boolean mIsRFCommFtpInited = false;
    private boolean mIsWifiFtpInited = false;
    private boolean m_initOk = false;
    private long m_managerPtr = nativeNew();

    private native int nativeBLEFtpConnectionCancel(long j);

    private native int nativeBLEFtpConnectionDisconnect(long j);

    private native int nativeBLEFtpConnectionReconnect(long j);

    private native int nativeBLEFtpConnectionReset(long j);

    private native int nativeBLEFtpDelete(long j, String str);

    private native int nativeBLEFtpGet(long j, String str, String str2, ARUtilsFtpProgressListener aRUtilsFtpProgressListener, Object obj, boolean z);

    private native byte[] nativeBLEFtpGetWithBuffer(long j, String str, ARUtilsFtpProgressListener aRUtilsFtpProgressListener, Object obj);

    private native int nativeBLEFtpIsConnectionCanceled(long j);

    private native String nativeBLEFtpList(long j, String str) throws ARUtilsException;

    private native int nativeBLEFtpPut(long j, String str, String str2, ARUtilsFtpProgressListener aRUtilsFtpProgressListener, Object obj, boolean z);

    private native int nativeBLEFtpRename(long j, String str, String str2);

    private native double nativeBLEFtpSize(long j, String str) throws ARUtilsException;

    private native void nativeCloseBLEFtp(long j);

    private native void nativeCloseRFCommFtp(long j);

    private native int nativeCloseWifiFtp(long j);

    private native int nativeDelete(long j);

    private native int nativeInitBLEFtp(long j, ARUtilsBLEFtp aRUtilsBLEFtp, Semaphore semaphore);

    private native int nativeInitRFCommFtp(long j, ARUtilsRFCommFtp aRUtilsRFCommFtp, Semaphore semaphore);

    private native int nativeInitWifiFtp(long j, String str, int i, String str2, String str3);

    private native int nativeInitWifiFtpOverMux(long j, String str, int i, long j2, String str2, String str3);

    private native long nativeNew() throws ARUtilsException;

    private native int nativeRFCommFtpConnectionCancel(long j);

    private native int nativeRFCommFtpConnectionDisconnect(long j);

    private native int nativeRFCommFtpConnectionReconnect(long j);

    private native int nativeRFCommFtpConnectionReset(long j);

    private native int nativeRFCommFtpIsConnectionCanceled(long j);

    private native int nativeRFCommFtpPut(long j, String str, String str2, ARUtilsFtpProgressListener aRUtilsFtpProgressListener, Object obj, boolean z);

    private static native boolean nativeStaticInit();

    static {
        nativeStaticInit();
    }

    public ARUtilsManager() throws ARUtilsException {
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

    public ARUTILS_ERROR_ENUM initFtp(Context ctx, ARDiscoveryDeviceService device, ARUTILS_DESTINATION_ENUM destination, ARUTILS_FTP_TYPE_ENUM type) {
        ARUtilsManager aRUtilsManager = this;
        Context context = ctx;
        ARUTILS_DESTINATION_ENUM arutils_destination_enum = destination;
        ARUTILS_FTP_TYPE_ENUM arutils_ftp_type_enum = type;
        if (device != null) {
            if (context != null) {
                ARDISCOVERY_PRODUCT_ENUM product = ARDiscoveryService.getProductFromProductID(device.getProductID());
                boolean z = false;
                boolean sky = ARDiscoveryService.getProductFamily(product) == ARDISCOVERY_PRODUCT_FAMILY_ENUM.ARDISCOVERY_PRODUCT_FAMILY_SKYCONTROLLER;
                boolean z2 = sky && !(product == ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_SKYCONTROLLER);
                boolean new_sky = z2;
                if (device.getNetworkType() == ARDISCOVERY_NETWORK_TYPE_ENUM.ARDISCOVERY_NETWORK_TYPE_NET) {
                    z = true;
                }
                boolean wifi = z;
                if (!new_sky && arutils_destination_enum != ARUTILS_DESTINATION_ENUM.ARUTILS_DESTINATION_DRONE) {
                    return ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
                }
                if (arutils_destination_enum == ARUTILS_DESTINATION_ENUM.ARUTILS_DESTINATION_SKYCONTROLLER && arutils_ftp_type_enum == ARUTILS_FTP_TYPE_ENUM.ARUTILS_FTP_TYPE_FLIGHTPLAN) {
                    return ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
                }
                ARUTILS_ERROR_ENUM ret;
                switch (type) {
                    case ARUTILS_FTP_TYPE_GENERIC:
                        z2 = (new_sky && wifi && arutils_destination_enum == ARUTILS_DESTINATION_ENUM.ARUTILS_DESTINATION_DRONE) ? true : true;
                        break;
                    case ARUTILS_FTP_TYPE_UPDATE:
                        z2 = (new_sky && wifi && arutils_destination_enum == ARUTILS_DESTINATION_ENUM.ARUTILS_DESTINATION_DRONE) ? true : true;
                        break;
                    case ARUTILS_FTP_TYPE_FLIGHTPLAN:
                        z2 = (new_sky && wifi) ? true : true;
                        break;
                    default:
                        return ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
                }
                boolean port = z2;
                boolean z3;
                ARDISCOVERY_PRODUCT_ENUM ardiscovery_product_enum;
                switch (device.getNetworkType()) {
                    case ARDISCOVERY_NETWORK_TYPE_NET:
                        z3 = sky;
                        ret = initWifiFtp(((ARDiscoveryDeviceNetService) device.getDevice()).getIp(), port, "anonymous", "");
                        break;
                    case ARDISCOVERY_NETWORK_TYPE_BLE:
                        z3 = sky;
                        sky = port;
                        if (arutils_destination_enum != ARUTILS_DESTINATION_ENUM.ARUTILS_DESTINATION_DRONE || arutils_ftp_type_enum != ARUTILS_FTP_TYPE_ENUM.ARUTILS_FTP_TYPE_UPDATE) {
                            ret = initBLEFtp(context, ARSALBLEManager.getInstance(ctx).getGatt(), sky);
                            break;
                        }
                        ret = initRFCommFtp(context, ARSALBLEManager.getInstance(ctx).getGatt(), sky);
                        break;
                        break;
                    case ARDISCOVERY_NETWORK_TYPE_USBMUX:
                        Mux mux = UsbAccessoryMux.get(ctx.getApplicationContext()).getMux();
                        if (mux == null) {
                            ardiscovery_product_enum = product;
                            z3 = sky;
                            sky = port;
                            Log.w(TAG, "initFtp ARDISCOVERY_NETWORK_TYPE_USB failed, mux is null");
                            ret = ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
                            break;
                        }
                        Ref muxref = mux.newMuxRef();
                        ardiscovery_product_enum = product;
                        Ref product2 = muxref;
                        z3 = sky;
                        sky = port;
                        ret = initWifiFtp(muxref, arutils_destination_enum == ARUTILS_DESTINATION_ENUM.ARUTILS_DESTINATION_DRONE ? "drone" : "skycontroller", port, "anonymous", "");
                        product2.release();
                        break;
                    default:
                        ardiscovery_product_enum = product;
                        z3 = sky;
                        sky = port;
                        ret = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
                        break;
                }
                return ret;
            }
        }
        return ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
    }

    public ARUTILS_ERROR_ENUM closeFtp(Context ctx, ARDiscoveryDeviceService device) {
        if (device == null) {
            return ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        ARUTILS_ERROR_ENUM ret;
        switch (device.getNetworkType()) {
            case ARDISCOVERY_NETWORK_TYPE_NET:
                ret = closeWifiFtp();
                break;
            case ARDISCOVERY_NETWORK_TYPE_BLE:
                if (!this.mIsBLEFtpInited) {
                    if (!this.mIsRFCommFtpInited) {
                        ret = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
                        break;
                    }
                    ret = closeRFCommFtp(ctx);
                } else {
                    ret = closeBLEFtp(ctx);
                }
                break;
            case ARDISCOVERY_NETWORK_TYPE_USBMUX:
                ret = closeWifiFtp();
                break;
            default:
                ret = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
                break;
        }
        return ret;
    }

    public ARUTILS_ERROR_ENUM initWifiFtp(String addr, int port, String username, String password) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("initWifiFtp on ip:port ");
        stringBuilder.append(addr);
        stringBuilder.append(":");
        stringBuilder.append(port);
        Log.i(str, stringBuilder.toString());
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
        if (TextUtils.isEmpty(addr)) {
            return ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        if (this.mIsWifiFtpInited) {
            Log.e(TAG, "wifi FTP is already inited");
            return error;
        }
        error = ARUTILS_ERROR_ENUM.getFromValue(nativeInitWifiFtp(this.m_managerPtr, addr, port, username, password));
        this.mIsWifiFtpInited = error == ARUTILS_ERROR_ENUM.ARUTILS_OK;
        return error;
    }

    public ARUTILS_ERROR_ENUM initWifiFtp(Ref muxRef, String dest, int port, String username, String password) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("initWifiFtp on mux, port ");
        int i = port;
        stringBuilder.append(i);
        Log.i(str, stringBuilder.toString());
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
        if (this.mIsWifiFtpInited) {
            Log.e(TAG, "wifi FTP is already inited");
        } else {
            muxRef.getCPtr();
            error = ARUTILS_ERROR_ENUM.getFromValue(nativeInitWifiFtpOverMux(r9.m_managerPtr, dest, i, muxRef.getCPtr(), username, password));
            r9.mIsWifiFtpInited = error == ARUTILS_ERROR_ENUM.ARUTILS_OK;
        }
        return error;
    }

    public ARUTILS_ERROR_ENUM closeWifiFtp() {
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
        if (this.mIsWifiFtpInited) {
            error = ARUTILS_ERROR_ENUM.getFromValue(nativeCloseWifiFtp(this.m_managerPtr));
            this.mIsWifiFtpInited = false;
            return error;
        }
        Log.e(TAG, "we haven't successfully initWifiFtp");
        return error;
    }

    public ARUTILS_ERROR_ENUM initBLEFtp(Context context, BluetoothGatt deviceGatt, int port) {
        if (this.mIsBLEFtpInited) {
            Log.e(TAG, "BLE FTP is already inited");
            return ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
        }
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.ARUTILS_OK;
        ARUtilsBLEFtp bleFtp = null;
        if (context == null) {
            error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        if (deviceGatt == null) {
            error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        boolean z = true;
        if (port == 0 || port % 10 != 1) {
            error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        if (error == ARUTILS_ERROR_ENUM.ARUTILS_OK && !context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_NETWORK_TYPE;
        }
        ARUTILS_ERROR_ENUM arutils_error_enum = ARUTILS_ERROR_ENUM.ARUTILS_OK;
        if (error == ARUTILS_ERROR_ENUM.ARUTILS_OK) {
            bleFtp = ARUtilsBLEFtp.getInstance(context);
            if (!bleFtp.registerDevice(deviceGatt, port)) {
                error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BLE_FAILED;
            }
        }
        if (error != ARUTILS_ERROR_ENUM.ARUTILS_OK) {
            z = false;
        }
        this.mIsBLEFtpInited = z;
        if (this.mIsBLEFtpInited) {
            nativeInitBLEFtp(this.m_managerPtr, bleFtp, new Semaphore(0));
        }
        return error;
    }

    public ARUTILS_ERROR_ENUM closeBLEFtp(Context context) {
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
        if (context == null) {
            return ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        if (this.mIsBLEFtpInited) {
            if (ARUtilsBLEFtp.getInstance(context).unregisterDevice()) {
                error = ARUTILS_ERROR_ENUM.ARUTILS_OK;
            }
            nativeCloseBLEFtp(this.m_managerPtr);
            this.mIsBLEFtpInited = false;
            return error;
        }
        Log.e(TAG, "we haven't successfully initBLEFtp");
        return error;
    }

    public ARUTILS_ERROR_ENUM BLEFtpConnectionDisconnect() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpConnectionDisconnect(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM BLEFtpConnectionReconnect() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpConnectionReconnect(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM BLEFtpConnectionCancel() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpConnectionCancel(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM BLEFtpIsConnectionCanceled() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpIsConnectionCanceled(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM BLEFtpConnectionReset() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpConnectionReset(this.m_managerPtr));
    }

    public String BLEFtpListFile(String remotePath) throws ARUtilsException {
        return nativeBLEFtpList(this.m_managerPtr, remotePath);
    }

    public double BLEFtpSize(String remotePath) throws ARUtilsException {
        return nativeBLEFtpSize(this.m_managerPtr, remotePath);
    }

    public ARUTILS_ERROR_ENUM BLEFtpPut(String remotePath, String srcFile, ARUtilsFtpProgressListener progressListener, Object progressArg, boolean resume) {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpPut(this.m_managerPtr, remotePath, srcFile, progressListener, progressArg, resume));
    }

    public ARUTILS_ERROR_ENUM BLEFtpGet(String remotePath, String destFile, ARUtilsFtpProgressListener progressListener, Object progressArg, boolean resume) {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpGet(this.m_managerPtr, remotePath, destFile, progressListener, progressArg, resume));
    }

    public byte[] BLEFtpGetWithBuffer(String remotePath, ARUtilsFtpProgressListener progressListener, Object progressArg) {
        return nativeBLEFtpGetWithBuffer(this.m_managerPtr, remotePath, progressListener, progressArg);
    }

    public ARUTILS_ERROR_ENUM BLEFtpDelete(String remotePath) {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpDelete(this.m_managerPtr, remotePath));
    }

    public ARUTILS_ERROR_ENUM BLEFtpRename(String oldNamePath, String newNamePath) {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeBLEFtpRename(this.m_managerPtr, oldNamePath, newNamePath));
    }

    public ARUTILS_ERROR_ENUM initRFCommFtp(Context context, BluetoothGatt deviceGatt, int port) {
        if (this.mIsRFCommFtpInited) {
            Log.e(TAG, "RFComm FTP is already inited");
            return ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
        }
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.ARUTILS_OK;
        ARUtilsRFCommFtp rfcommFtp = null;
        if (context == null) {
            error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        if (deviceGatt == null) {
            error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        boolean z = true;
        if (port == 0 || port % 10 != 1) {
            error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        if (error == ARUTILS_ERROR_ENUM.ARUTILS_OK && !context.getPackageManager().hasSystemFeature("android.hardware.bluetooth")) {
            error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_NETWORK_TYPE;
        }
        ARUTILS_ERROR_ENUM arutils_error_enum = ARUTILS_ERROR_ENUM.ARUTILS_OK;
        if (error == ARUTILS_ERROR_ENUM.ARUTILS_OK) {
            rfcommFtp = ARUtilsRFCommFtp.getInstance(context);
            if (!rfcommFtp.registerDevice(deviceGatt, port)) {
                error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR_RFCOMM_FAILED;
            }
        }
        if (error != ARUTILS_ERROR_ENUM.ARUTILS_OK) {
            z = false;
        }
        this.mIsRFCommFtpInited = z;
        if (this.mIsRFCommFtpInited) {
            nativeInitRFCommFtp(this.m_managerPtr, rfcommFtp, new Semaphore(0));
        }
        return error;
    }

    public ARUTILS_ERROR_ENUM closeRFCommFtp(Context context) {
        ARUTILS_ERROR_ENUM error = ARUTILS_ERROR_ENUM.ARUTILS_ERROR;
        if (context == null) {
            return ARUTILS_ERROR_ENUM.ARUTILS_ERROR_BAD_PARAMETER;
        }
        if (this.mIsRFCommFtpInited) {
            if (ARUtilsRFCommFtp.getInstance(context).unregisterDevice()) {
                error = ARUTILS_ERROR_ENUM.ARUTILS_OK;
            }
            nativeCloseRFCommFtp(this.m_managerPtr);
            this.mIsRFCommFtpInited = false;
            return error;
        }
        Log.e(TAG, "we haven't successfully initRFCommFtp");
        return error;
    }

    public ARUTILS_ERROR_ENUM RFCommFtpConnectionDisconnect() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeRFCommFtpConnectionDisconnect(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM RFCommFtpConnectionReconnect() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeRFCommFtpConnectionReconnect(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM RFCommFtpConnectionCancel() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeRFCommFtpConnectionCancel(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM RFCommFtpIsConnectionCanceled() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeRFCommFtpIsConnectionCanceled(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM RFCommFtpConnectionReset() {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeRFCommFtpConnectionReset(this.m_managerPtr));
    }

    public ARUTILS_ERROR_ENUM RFCommFtpPut(String remotePath, String srcFile, ARUtilsFtpProgressListener progressListener, Object progressArg, boolean resume) {
        return ARUTILS_ERROR_ENUM.getFromValue(nativeRFCommFtpPut(this.m_managerPtr, remotePath, srcFile, progressListener, progressArg, resume));
    }
}
