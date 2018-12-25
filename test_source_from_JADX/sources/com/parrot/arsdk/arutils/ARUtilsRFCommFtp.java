package com.parrot.arsdk.arutils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Build.VERSION;
import com.parrot.arsdk.arnetworkal.ARNetworkALBLENetwork;
import com.parrot.arsdk.arsal.ARSALBLEManager;
import com.parrot.arsdk.arsal.ARSALBLEManager.ARSALManagerNotificationData;
import com.parrot.arsdk.arsal.ARSALPrint;
import com.parrot.arsdk.arsal.ARSAL_ERROR_ENUM;
import com.parrot.arsdk.arsal.ARUUID;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.jmdns.impl.constants.DNSConstants;

public class ARUtilsRFCommFtp {
    private static final String LOG_TAG = "ARUtilsRFCommFTP.java";
    private static final UUID MY_UUID = UUID.fromString("8b6814d3-6ce7-4498-9700-9312c1711f63");
    private static final Integer RFCOMM_CHANNEL = Integer.valueOf(21);
    public static final String RFCOMM_GETTING_KEY = "kARUTILS_BLERFComm_Getting";
    private static final String RFCOMM_UPDATE_KEY = "UPD";
    public static final String SOFTWARE_DOWNLOAD_SIZE_SET = "/api/software/download_size/set";
    protected static final int ST_CONNECTED = 2;
    protected static final int ST_CONNECTING = 1;
    protected static final int ST_NOT_CONNECTED = 0;
    private static final byte TYPE_MES_ACKNOWLEDGT = (byte) 2;
    public static final byte TYPE_MES_CLOSE_SESSION = (byte) 1;
    public static final byte TYPE_MES_DATA = Byte.MIN_VALUE;
    public static final byte TYPE_MES_OPEN_SESSION = (byte) 0;
    private ArrayList<BluetoothGattCharacteristic> arrayGetting;
    private ARSALBLEManager bleManager;
    private int connectionCount;
    private Lock connectionLock;
    private BluetoothGatt gattDevice;
    private BluetoothDevice mDevice;
    private InputStream mInStream;
    private boolean mIsOpeningSession;
    private OutputStream mOutStream;
    private BluetoothSocket mSocket;
    private int mState;
    private int port;
    private BluetoothGattCharacteristic rfCommReadCharac;
    private BluetoothGattCharacteristic rfCommWriteCharac;

    private static class ARUtilsRFCommFtpHolder {
        private static final ARUtilsRFCommFtp instance = new ARUtilsRFCommFtp();

        private ARUtilsRFCommFtpHolder() {
        }
    }

    private static native void nativeJNIInit();

    private native void nativeProgressCallback(long j, float f);

    static {
        nativeJNIInit();
    }

    private ARUtilsRFCommFtp() {
        this.bleManager = null;
        this.gattDevice = null;
        this.connectionCount = 0;
        this.connectionLock = new ReentrantLock();
        this.arrayGetting = null;
        this.mIsOpeningSession = false;
        this.mState = 0;
    }

    public static ARUtilsRFCommFtp getInstance(Context context) {
        ARUtilsRFCommFtp instance = ARUtilsRFCommFtpHolder.instance;
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        instance.setBLEManager(context);
        return instance;
    }

    private synchronized void setBLEManager(Context context) {
        if (this.bleManager == null) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            }
            this.bleManager = ARSALBLEManager.getInstance(context);
        }
    }

    public boolean registerDevice(BluetoothGatt gattDevice, int port) {
        String str = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("registerDevice ");
        stringBuilder.append(gattDevice.toString());
        stringBuilder.append(" port : ");
        stringBuilder.append(port);
        ARSALPrint.m530d(str, stringBuilder.toString());
        if (this.gattDevice == gattDevice) {
            if (this.port == port) {
                if (this.gattDevice == null) {
                    ARSALPrint.m532e(LOG_TAG, "registerDevice : Bad parameters");
                    return false;
                }
                ARSALPrint.m532e(LOG_TAG, "already on good device");
                return true;
            }
        }
        this.gattDevice = gattDevice;
        this.port = port;
        this.connectionCount++;
        searchForInterestingCharacs();
        return registerCharacteristics();
    }

    public boolean unregisterDevice() {
        if (this.connectionCount > 0) {
            if (this.connectionCount == 1) {
                this.gattDevice = null;
                this.port = 0;
                unregisterCharacteristics();
            }
            this.connectionCount--;
            return true;
        }
        ARSALPrint.m532e(LOG_TAG, "Bad parameters");
        return false;
    }

    @SuppressLint({"NewApi"})
    public void searchForInterestingCharacs() {
        List<BluetoothGattService> services = this.gattDevice.getServices();
        ARSAL_ERROR_ENUM error = ARSAL_ERROR_ENUM.ARSAL_OK;
        ARSALPrint.m530d(LOG_TAG, "registerCharacteristics");
        for (BluetoothGattService service : services) {
            String serviceUuid = ARUUID.getShortUuid(service.getUuid());
            String name = ARUUID.getShortUuid(service.getUuid());
            String str = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service ");
            stringBuilder.append(name);
            ARSALPrint.m532e(str, stringBuilder.toString());
            if (serviceUuid.startsWith(ARNetworkALBLENetwork.ARNETWORKAL_BLENETWORK_PARROT_SERVICE_PREFIX_UUID_RFCOMM)) {
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                    String characteristicUuid = ARUUID.getShortUuid(characteristic.getUuid());
                    String str2 = LOG_TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("characteristic ");
                    stringBuilder2.append(characteristicUuid);
                    ARSALPrint.m532e(str2, stringBuilder2.toString());
                    if (characteristicUuid.startsWith(ARNetworkALBLENetwork.f1775x139372ce)) {
                        this.rfCommReadCharac = characteristic;
                    } else if (characteristicUuid.startsWith(ARNetworkALBLENetwork.f1776x5f276587)) {
                        this.rfCommWriteCharac = characteristic;
                        this.rfCommReadCharac = characteristic;
                    }
                }
            }
        }
    }

    public boolean registerCharacteristics() {
        ARSALPrint.m530d(LOG_TAG, "registerCharacteristics");
        this.arrayGetting = null;
        if (this.rfCommReadCharac == null) {
            return false;
        }
        this.arrayGetting = new ArrayList();
        this.arrayGetting.add(this.rfCommReadCharac);
        this.bleManager.registerNotificationCharacteristics(this.arrayGetting, RFCOMM_GETTING_KEY);
        return true;
    }

    public boolean unregisterCharacteristics() {
        ARSALPrint.m530d(LOG_TAG, "unregisterCharacteristics");
        return this.bleManager.unregisterNotificationCharacteristics(RFCOMM_GETTING_KEY);
    }

    public boolean putFileAL(String remotePath, String localFile, long nativeCallbackObject, boolean resume, Semaphore cancelSem) {
        ARSALPrint.m532e(LOG_TAG, "putFileAL");
        this.connectionLock.lock();
        boolean ret = putFile(remotePath, localFile, nativeCallbackObject, resume, cancelSem);
        this.connectionLock.unlock();
        return ret;
    }

    public boolean cancelFileAL(Semaphore cancelSem) {
        return cancelFile(cancelSem);
    }

    public boolean isConnectionCanceledAL(Semaphore cancelSem) {
        return isConnectionCanceled(cancelSem);
    }

    public boolean resetConnectionAL(Semaphore cancelSem) {
        return resetConnection(cancelSem);
    }

    private boolean cancelFile(Semaphore cancelSem) {
        cancelSem.release();
        return true;
    }

    private boolean isConnectionCanceled(Semaphore cancelSem) {
        boolean ret = false;
        if (cancelSem != null) {
            ret = cancelSem.tryAcquire();
            if (ret) {
                cancelSem.release();
            }
        }
        return ret;
    }

    private boolean resetConnection(Semaphore cancelSem) {
        if (cancelSem != null) {
            while (cancelSem.tryAcquire()) {
            }
        }
        return true;
    }

    private boolean putFile(String remoteFile, String localFile, long nativeCallbackObject, boolean resume, Semaphore cancelSem) {
        boolean processIsOK = true;
        ARSALPrint.m530d(LOG_TAG, "putFile Begin");
        File fileToUpload = new File(localFile);
        if (fileToUpload == null || !fileToUpload.exists()) {
            processIsOK = false;
        }
        if (processIsOK) {
            String str = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Will send file with size = ");
            stringBuilder.append(fileToUpload.length());
            ARSALPrint.m530d(str, stringBuilder.toString());
            connectToRFCommDevice(fileToUpload.length(), cancelSem);
            if (this.mState != 1) {
                processIsOK = false;
            }
        }
        if (processIsOK) {
            openSession();
            if (this.mState != 2) {
                processIsOK = false;
            }
        }
        if (processIsOK) {
            processIsOK = sendFile(fileToUpload, nativeCallbackObject, cancelSem);
        }
        if (processIsOK && !isConnectionCanceled(cancelSem)) {
            try {
                Thread.sleep(DNSConstants.CLOSE_TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (processIsOK) {
            closeSession();
            if (this.mState != 0) {
                processIsOK = false;
            }
        }
        closeConnection();
        return processIsOK;
    }

    private void connectToRFCommDevice(long fileSize, Semaphore cancelSem) {
        String rfCommMacAddress = askRFCommMacAdress(fileSize, cancelSem);
        String str = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("rfCommMacAddress = ");
        stringBuilder.append(rfCommMacAddress);
        ARSALPrint.m530d(str, stringBuilder.toString());
        if (rfCommMacAddress != null) {
            connectToBluetoothDevice(rfCommMacAddress);
        }
    }

    private String askRFCommMacAdress(long fileSize, Semaphore cancelSem) {
        if (this.rfCommWriteCharac == null) {
            return null;
        }
        String key = new StringBuilder();
        key.append(RFCOMM_UPDATE_KEY);
        key.append(fileSize);
        key = key.toString();
        byte[] keyAsByteArr = key.getBytes();
        String str = LOG_TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Write in charac ");
        stringBuilder.append(key);
        ARSALPrint.m530d(str, stringBuilder.toString());
        this.bleManager.writeData(keyAsByteArr, this.rfCommWriteCharac);
        if (!isConnectionCanceled(cancelSem)) {
            return readRFCommMacAdress(cancelSem);
        }
        ARSALPrint.m532e(LOG_TAG, "Canceled received after having written in the BLE characteristic to get rfcomm mac address");
        return null;
    }

    private String readRFCommMacAdress(Semaphore cancelSem) {
        ArrayList<ARSALManagerNotificationData> receivedNotifications = new ArrayList();
        boolean readDataSucceed = false;
        if (receivedNotifications.size() == 0) {
            readDataSucceed = this.bleManager.readDataNotificationData(receivedNotifications, 1, RFCOMM_GETTING_KEY);
            ARSALPrint.m530d(LOG_TAG, "Data has been read");
        }
        if (isConnectionCanceled(cancelSem)) {
            ARSALPrint.m530d(LOG_TAG, "Canceled received after having read the rfcomm mac address");
            readDataSucceed = false;
        }
        if (!readDataSucceed || receivedNotifications.size() <= 0) {
            return null;
        }
        byte[] block = ((ARSALManagerNotificationData) receivedNotifications.get(0)).value;
        if (block == null) {
            return null;
        }
        StringBuilder strBld = new StringBuilder();
        for (int i = block.length - 1; i > 0; i--) {
            strBld.append(String.format("%02X", new Object[]{Byte.valueOf(block[i])}));
            if (i > 1) {
                strBld.append(":");
            }
        }
        return strBld.toString();
    }

    private void connectToBluetoothDevice(String macAddress) {
        this.mSocket = null;
        ARSALPrint.m530d(LOG_TAG, "Try to connect to bluetooth device");
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddress);
            try {
                if (VERSION.SDK_INT < 17) {
                    this.mSocket = (BluetoothSocket) device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class}).invoke(device, new Object[]{MY_UUID});
                } else {
                    this.mSocket = (BluetoothSocket) device.getClass().getMethod("createInsecureRfcommSocket", new Class[]{Integer.TYPE}).invoke(device, new Object[]{RFCOMM_CHANNEL});
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
            try {
                this.mSocket.connect();
            } catch (IOException e4) {
                this.mSocket = null;
                e4.printStackTrace();
            }
            if (this.mSocket != null) {
                this.mDevice = device;
                try {
                    this.mInStream = this.mSocket.getInputStream();
                    this.mOutStream = this.mSocket.getOutputStream();
                    this.mState = 1;
                } catch (IOException e5) {
                    closeConnection();
                }
            }
            return;
        }
        ARSALPrint.m532e(LOG_TAG, "Bluetooth adapter is not enabled");
    }

    private void openSession() {
        ARSALPrint.m530d(LOG_TAG, "open RFComm session");
        this.mIsOpeningSession = true;
        write(getHeaderFirst(0, (byte) 0));
        try {
            this.mInStream.read(new byte[4096]);
            this.mIsOpeningSession = false;
            this.mState = 2;
        } catch (IOException e) {
            closeConnection();
        }
    }

    private void closeSession() {
        ARSALPrint.m530d(LOG_TAG, "close RFComm session");
        this.mIsOpeningSession = true;
        write(getHeaderFirst(0, (byte) 1));
        try {
            this.mInStream.read(new byte[4096]);
            this.mState = 0;
        } catch (IOException e) {
            closeConnection();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            device.getClass().getMethod("removeBond", (Class[]) null).invoke(device, (Object[]) null);
        } catch (Exception e) {
            ARSALPrint.m532e(LOG_TAG, e.getMessage());
        }
    }

    private boolean sendFile(File file, long nativeCallbackObject, Semaphore cancelSem) {
        byte[] buffer;
        float f;
        Semaphore semaphore;
        FileNotFoundException e;
        ARUtilsRFCommFtp aRUtilsRFCommFtp = this;
        long j = nativeCallbackObject;
        int nbSstoredBytes = 0;
        boolean ret = true;
        try {
            try {
                Exception e2;
                Exception e3;
                int i;
                long j2;
                float f2;
                FileInputStream f3 = new FileInputStream(file);
                byte[] buffer2 = new byte[978];
                long total = 0;
                int id = 0;
                float percent = 0.0f;
                float lastPercent = 0.0f;
                if (null > null) {
                    while (nbSstoredBytes > 0) {
                        buffer = buffer2;
                        try {
                            nbSstoredBytes -= (int) f3.skip((long) nbSstoredBytes);
                            buffer2 = buffer;
                        } catch (Exception e32) {
                            e2 = e32;
                            i = nbSstoredBytes;
                            j2 = 0;
                            f = 0.0f;
                            f2 = 0.0f;
                            nbSstoredBytes = buffer;
                            lastPercent = cancelSem;
                        }
                    }
                }
                buffer = buffer2;
                try {
                    long fileSize = file.length();
                    while (ret) {
                        i = nbSstoredBytes;
                        nbSstoredBytes = buffer;
                        try {
                            int read = f3.read(nbSstoredBytes);
                            int len = read;
                            if (read <= 0) {
                                f2 = lastPercent;
                                lastPercent = cancelSem;
                                break;
                            }
                            f2 = lastPercent;
                            j = total + ((long) len);
                            try {
                                byte[] request = new byte[len];
                                System.arraycopy(nbSstoredBytes, 0, request, 0, len);
                                if (aRUtilsRFCommFtp.sendFirmwareOnDevice(request, id)) {
                                    percent = 100.0f * (((float) j) / ((float) fileSize));
                                    j2 = j;
                                    j = nativeCallbackObject;
                                    if (j != 0) {
                                        try {
                                            aRUtilsRFCommFtp.nativeProgressCallback(j, percent);
                                        } catch (Exception e4) {
                                            e32 = e4;
                                            semaphore = cancelSem;
                                        }
                                    }
                                    try {
                                        if (aRUtilsRFCommFtp.isConnectionCanceled(cancelSem)) {
                                            ARSALPrint.m530d(LOG_TAG, "Canceled received during file upload");
                                            ret = false;
                                        }
                                        id++;
                                        buffer = nbSstoredBytes;
                                        nbSstoredBytes = i;
                                        lastPercent = f2;
                                        total = j2;
                                        aRUtilsRFCommFtp = this;
                                    } catch (Exception e5) {
                                        e32 = e5;
                                    }
                                } else {
                                    try {
                                        ARSALPrint.m532e(LOG_TAG, "upload firmware, task was canceled");
                                        f3.close();
                                        return false;
                                    } catch (Exception e322) {
                                        semaphore = cancelSem;
                                        e2 = e322;
                                        j2 = j;
                                        j = nativeCallbackObject;
                                        e2.printStackTrace();
                                        try {
                                            f3.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                        return false;
                                    }
                                }
                            } catch (Exception e3222) {
                                semaphore = cancelSem;
                                j2 = j;
                                j = nativeCallbackObject;
                                e2 = e3222;
                            }
                        } catch (Exception e32222) {
                            f = percent;
                            f2 = lastPercent;
                            lastPercent = cancelSem;
                            e2 = e32222;
                            j2 = total;
                        }
                    }
                    f = percent;
                    f2 = lastPercent;
                    nbSstoredBytes = buffer;
                    lastPercent = cancelSem;
                    try {
                        f3.close();
                    } catch (IOException e62) {
                        e62.printStackTrace();
                    }
                    String str = LOG_TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Sending done. Sent ");
                    stringBuilder.append(total);
                    stringBuilder.append(" bytes");
                    ARSALPrint.m532e(str, stringBuilder.toString());
                    return ret;
                } catch (Exception e322222) {
                    i = nbSstoredBytes;
                    f2 = 0.0f;
                    nbSstoredBytes = buffer;
                    lastPercent = cancelSem;
                    e2 = e322222;
                    j2 = 0;
                    f = 0.0f;
                    e2.printStackTrace();
                    f3.close();
                    return false;
                }
                e2 = e322222;
                e2.printStackTrace();
                f3.close();
                return false;
            } catch (FileNotFoundException e7) {
                e = e7;
                semaphore = cancelSem;
                e.printStackTrace();
                return false;
            }
        } catch (FileNotFoundException e8) {
            e = e8;
            File file2 = file;
            semaphore = cancelSem;
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendFirmwareOnDevice(byte[] data, int id) {
        boolean success = this.mState == 2;
        if (!success) {
            return success;
        }
        try {
            return write(getUploadPacket(data, id));
        } catch (Exception e) {
            return false;
        }
    }

    private synchronized boolean write(byte[] buffer) {
        boolean success;
        String str;
        StringBuilder stringBuilder;
        success = false;
        try {
            this.mOutStream.write(buffer);
            Thread.sleep(40);
            success = true;
        } catch (IOException e) {
            str = LOG_TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Exception during write");
            stringBuilder.append(e.getMessage());
            ARSALPrint.m532e(str, stringBuilder.toString());
        } catch (InterruptedException e2) {
            str = LOG_TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Exception during sleep");
            stringBuilder.append(e2.getMessage());
            ARSALPrint.m532e(str, stringBuilder.toString());
        }
        return success;
    }

    private static byte[] getHeaderFirst(int length, byte type) {
        byte[] zz = sizeIntToByte(length + 3);
        byte[] t = new byte[]{type};
        byte[] header = new byte[(zz.length + t.length)];
        System.arraycopy(zz, 0, header, 0, zz.length);
        System.arraycopy(t, 0, header, zz.length, t.length);
        return header;
    }

    private static byte[] sizeIntToByte(int length) {
        zz = new byte[2];
        return new byte[]{(byte) (length >>> 8), (byte) length};
    }

    private static byte[] sizeIntToByte2(int length) {
        zz = new byte[2];
        return new byte[]{(byte) length, (byte) (length >>> 8)};
    }

    public synchronized void closeConnection() {
        ARSALPrint.m532e(LOG_TAG, "closeConnection");
        try {
            if (this.mInStream != null) {
                this.mInStream.close();
                this.mInStream = null;
            }
        } catch (IOException e) {
            ARSALPrint.m533e(LOG_TAG, "Closing of mInStream failed", e);
        }
        try {
            if (this.mOutStream != null) {
                this.mOutStream.close();
                this.mOutStream = null;
            }
        } catch (IOException e2) {
            ARSALPrint.m533e(LOG_TAG, "Closing of mOutStream failed", e2);
        }
        try {
            if (this.mSocket != null) {
                this.mSocket.close();
                this.mSocket = null;
            }
        } catch (IOException e22) {
            ARSALPrint.m533e(LOG_TAG, "Closing of mSocket failed", e22);
        }
        if (this.mDevice != null) {
            unpairDevice(this.mDevice);
            this.mDevice = null;
        }
        this.mState = 0;
        return;
    }

    private static byte[] getUploadPacket(byte[] data, int id) {
        Object obj = data;
        byte[] header = getHeaderFirst(obj.length + 9, -128);
        byte[] xy = new byte[]{(byte) 1, (byte) 1};
        byte[] zzDesordered = ByteBuffer.allocate(2).putShort((short) (obj.length + 9)).array();
        byte[] zz = new byte[]{zzDesordered[1], zzDesordered[0]};
        byte[] pktType = new byte[]{(byte) 0};
        byte[] pktIdDesordered = ByteBuffer.allocate(2).putShort((short) id).array();
        byte[] pktId = new byte[]{pktIdDesordered[1], pktIdDesordered[0]};
        byte[] request = new byte[(obj.length + 12)];
        System.arraycopy(header, 0, request, 0, header.length);
        System.arraycopy(xy, 0, request, header.length, xy.length);
        System.arraycopy(zz, 0, request, header.length + xy.length, zz.length);
        System.arraycopy(pktType, 0, request, (header.length + xy.length) + zz.length, pktType.length);
        System.arraycopy(pktId, 0, request, ((header.length + xy.length) + zz.length) + pktType.length, pktId.length);
        System.arraycopy(obj, 0, request, (((header.length + xy.length) + zz.length) + pktType.length) + pktId.length, obj.length);
        byte a = (byte) 0;
        byte b = (byte) 0;
        for (int i = header.length; i < request.length; i++) {
            a = (byte) (request[i] | a);
            b = (byte) (request[i] ^ b);
        }
        byte[] sign = new byte[]{a, b};
        System.arraycopy(sign, 0, request, ((((header.length + xy.length) + zz.length) + pktType.length) + pktId.length) + obj.length, sign.length);
        return request;
    }
}
