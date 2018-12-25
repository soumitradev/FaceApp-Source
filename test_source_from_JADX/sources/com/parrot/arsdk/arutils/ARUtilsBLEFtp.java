package com.parrot.arsdk.arutils;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import com.parrot.arsdk.arsal.ARSALBLEManager;
import com.parrot.arsdk.arsal.ARSALPrint;
import com.parrot.arsdk.arsal.ARSAL_ERROR_ENUM;
import com.parrot.arsdk.arsal.ARUUID;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.UrlUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.BrickValues;

public class ARUtilsBLEFtp {
    private static final String APP_TAG = "BLEFtp ";
    public static final byte BLE_BLOCK_HEADER_CONTINUE = (byte) 0;
    public static final byte BLE_BLOCK_HEADER_SINGLE = (byte) 3;
    public static final byte BLE_BLOCK_HEADER_START = (byte) 2;
    public static final byte BLE_BLOCK_HEADER_STOP = (byte) 1;
    public static final String BLE_GETTING_KEY = "kARUTILS_BLEFtp_Getting";
    public static final int BLE_MTU_SIZE = 20;
    public static final int BLE_PACKET_BLOCK_GETTING_COUNT = 100;
    public static final int BLE_PACKET_BLOCK_PUTTING_COUNT = 500;
    public static final String BLE_PACKET_DELETE_SUCCESS = "Delete successful";
    public static final String BLE_PACKET_EOF = "End of Transfer";
    public static final int BLE_PACKET_MAX_SIZE = 132;
    public static final String BLE_PACKET_NOT_WRITTEN = "FILE NOT WRITTEN";
    public static final String BLE_PACKET_RENAME_FROM_SUCCESS = "Rename successful";
    public static final String BLE_PACKET_RENAME_SUCCESS = "Rename successful";
    public static final long BLE_PACKET_WRITE_SLEEP = 35;
    public static final String BLE_PACKET_WRITTEN = "FILE WRITTEN";
    private ArrayList<BluetoothGattCharacteristic> arrayGetting;
    private ARSALBLEManager bleManager;
    private int connectionCount;
    private Lock connectionLock;
    private BluetoothGatt gattDevice;
    private BluetoothGattCharacteristic getting;
    private BluetoothGattCharacteristic handling;
    private volatile boolean isListing;
    private int port;
    private BluetoothGattCharacteristic transferring;

    private static class ARUtilsBLEFtpHolder {
        private static final ARUtilsBLEFtp instance = new ARUtilsBLEFtp();

        private ARUtilsBLEFtpHolder() {
        }
    }

    private static native void nativeJNIInit();

    private native void nativeProgressCallback(long j, float f);

    static {
        nativeJNIInit();
    }

    private ARUtilsBLEFtp() {
        this.bleManager = null;
        this.gattDevice = null;
        this.connectionCount = 0;
        this.connectionLock = new ReentrantLock();
        this.transferring = null;
        this.getting = null;
        this.handling = null;
        this.arrayGetting = null;
    }

    public static ARUtilsBLEFtp getInstance(Context context) {
        ARUtilsBLEFtp instance = ARUtilsBLEFtpHolder.instance;
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
        if (this.connectionCount == 0) {
            this.gattDevice = gattDevice;
            this.port = port;
            this.connectionCount++;
            return registerCharacteristics();
        } else if (this.gattDevice == gattDevice && this.port == port) {
            this.connectionCount++;
            return true;
        } else {
            String str = APP_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("registerDevice Bad parameters : ");
            stringBuilder.append(this.connectionCount);
            stringBuilder.append("\nthis.gattDevice = ");
            stringBuilder.append(this.gattDevice);
            stringBuilder.append("\ngattDevice = ");
            stringBuilder.append(gattDevice);
            stringBuilder.append("\nthis.port = ");
            stringBuilder.append(this.port);
            stringBuilder.append("\nport = ");
            stringBuilder.append(port);
            ARSALPrint.m532e(str, stringBuilder.toString());
            return false;
        }
    }

    public boolean unregisterDevice() {
        if (this.connectionCount > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unregisterDevice : connection count is ");
            stringBuilder.append(this.connectionCount);
            ARSALPrint.m532e("DBG", stringBuilder.toString());
            if (this.connectionCount == 1) {
                this.gattDevice = null;
                this.port = 0;
                this.transferring = null;
                this.getting = null;
                this.handling = null;
                unregisterCharacteristics();
            }
            this.connectionCount--;
            return true;
        }
        ARSALPrint.m532e("DBG", "BLEFtp Bad parameters");
        return false;
    }

    public boolean registerCharacteristics() {
        List<BluetoothGattService> services = this.gattDevice.getServices();
        ARSAL_ERROR_ENUM error = ARSAL_ERROR_ENUM.ARSAL_OK;
        ARSALPrint.m530d("DBG", "BLEFtp registerCharacteristics");
        for (BluetoothGattService service : services) {
            String serviceUuid = ARUUID.getShortUuid(service.getUuid());
            String name = ARUUID.getShortUuid(service.getUuid());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("BLEFtp service ");
            stringBuilder.append(name);
            ARSALPrint.m530d("DBG", stringBuilder.toString());
            if (serviceUuid.startsWith(String.format("fd%02d", new Object[]{Integer.valueOf(r0.port)}))) {
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                    String characteristicUuid = ARUUID.getShortUuid(characteristic.getUuid());
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("BLEFtp characteristic ");
                    stringBuilder2.append(characteristicUuid);
                    ARSALPrint.m530d("DBG", stringBuilder2.toString());
                    if (characteristicUuid.startsWith(String.format("fd%02d", new Object[]{Integer.valueOf(r0.port + 1)}))) {
                        r0.transferring = characteristic;
                    } else {
                        if (characteristicUuid.startsWith(String.format("fd%02d", new Object[]{Integer.valueOf(r0.port + 2)}))) {
                            r0.arrayGetting = new ArrayList();
                            r0.arrayGetting.add(characteristic);
                            r0.getting = characteristic;
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("BLEFtp set ");
                            stringBuilder3.append(error.toString());
                            ARSALPrint.m530d("DBG", stringBuilder3.toString());
                        } else {
                            if (characteristicUuid.startsWith(String.format("fd%02d", new Object[]{Integer.valueOf(r0.port + 3)}))) {
                                r0.handling = characteristic;
                            }
                        }
                    }
                }
            }
        }
        if (!(r0.transferring == null || r0.getting == null || r0.handling == null || 1 != 1)) {
            r0.bleManager.registerNotificationCharacteristics(r0.arrayGetting, BLE_GETTING_KEY);
        }
        return true;
    }

    public boolean unregisterCharacteristics() {
        ARSALPrint.m530d("DBG", "BLEFtp unregisterCharacteristics");
        return this.bleManager.unregisterNotificationCharacteristics(BLE_GETTING_KEY);
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

    public boolean listFilesAL(String remotePath, String[] resultList) {
        this.connectionLock.lock();
        boolean ret = listFiles(remotePath, resultList);
        this.connectionLock.unlock();
        return ret;
    }

    public boolean sizeFileAL(String remoteFile, double[] fileSize) {
        this.connectionLock.lock();
        boolean ret = sizeFile(remoteFile, fileSize);
        this.connectionLock.unlock();
        return ret;
    }

    public boolean getFileAL(String remotePath, String localFile, long nativeCallbackObject, boolean wantProgress, Semaphore cancelSem) {
        this.connectionLock.lock();
        boolean ret = getFile(remotePath, localFile, nativeCallbackObject, wantProgress, cancelSem);
        this.connectionLock.unlock();
        return ret;
    }

    public boolean getFileWithBufferAL(String remotePath, byte[][] data, long nativeCallbackObject, boolean wantProgress, Semaphore cancelSem) {
        this.connectionLock.lock();
        boolean ret = getFileWithBuffer(remotePath, data, nativeCallbackObject, wantProgress, cancelSem);
        this.connectionLock.unlock();
        return ret;
    }

    public boolean putFileAL(String remotePath, String localFile, long nativeCallbackObject, boolean resume, Semaphore cancelSem) {
        this.connectionLock.lock();
        boolean ret = putFile(remotePath, localFile, nativeCallbackObject, resume, cancelSem);
        this.connectionLock.unlock();
        return ret;
    }

    public boolean deleteFileAL(String remoteFile) {
        this.connectionLock.lock();
        boolean ret = deleteFile(remoteFile);
        this.connectionLock.unlock();
        return ret;
    }

    public boolean renameFileAL(String oldNamePath, String newNamePath) {
        this.connectionLock.lock();
        boolean ret = renameFile(oldNamePath, newNamePath);
        this.connectionLock.unlock();
        return ret;
    }

    private boolean cancelFile(Semaphore cancelSem) {
        if (this.isListing) {
            return true;
        }
        cancelSem.release();
        return this.bleManager.cancelReadNotification(BLE_GETTING_KEY);
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

    private boolean sizeFile(String remoteFile, double[] fileSize) {
        boolean ret;
        double[] dArr = fileSize;
        String[] resultList = new String[]{null};
        String remotePath = null;
        String remoteFileName = null;
        int idx = 0;
        int endIdx = -1;
        boolean found = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BLEFtp sizeFile ");
        stringBuilder.append(remoteFile);
        ARSALPrint.m530d("DBG", stringBuilder.toString());
        String remoteFile2 = normalizePathName(remoteFile);
        dArr[0] = BrickValues.SET_COLOR_TO;
        while (true) {
            int indexOf = remoteFile2.indexOf(47, idx);
            idx = indexOf;
            if (indexOf == -1) {
                break;
            }
            idx++;
            endIdx = idx;
        }
        if (endIdx != -1) {
            remotePath = remoteFile2.substring(0, endIdx);
            remoteFileName = remoteFile2.substring(endIdx, remoteFile2.length());
        }
        if (listFiles(remotePath, resultList) && resultList[0] != null) {
            String[] nextItem = new String[]{null};
            int[] indexItem = new int[]{0};
            int[] itemLen = new int[]{0};
            while (!found) {
                int[] itemLen2 = itemLen;
                int[] iArr = indexItem;
                String listNextItem = getListNextItem(resultList[0], nextItem, null, false, iArr, itemLen2);
                String fileName = listNextItem;
                if (listNextItem == null) {
                    break;
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("BLEFtp file ");
                stringBuilder2.append(fileName);
                ARSALPrint.m530d("DBG", stringBuilder2.toString());
                if (remoteFileName.contentEquals(fileName)) {
                    if (getListItemSize(resultList[0], iArr[0], itemLen2[0], dArr) != null) {
                        found = true;
                    }
                }
                itemLen = itemLen2;
                indexItem = iArr;
            }
        }
        if (found) {
            ret = true;
        } else {
            ret = false;
        }
        return ret;
    }

    private boolean listFiles(String remotePath, String[] resultList) {
        this.isListing = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BLEFtp listFiles ");
        stringBuilder.append(remotePath);
        ARSALPrint.m530d("DBG", stringBuilder.toString());
        boolean ret = sendCommand("LIS", normalizePathName(remotePath), this.handling);
        if (ret) {
            byte[][] data = new byte[1][];
            ret = readGetData(0, null, data, 0, null);
            if (data[0] != null) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("BLEFtp listFiles==");
                stringBuilder2.append(new String(data[0]));
                stringBuilder2.append("==");
                ARSALPrint.m530d("DBG", stringBuilder2.toString());
            }
            if (ret && data[0] != null) {
                resultList[0] = new String(data[0]);
            }
        }
        this.isListing = false;
        return ret;
    }

    private boolean getFile(String remoteFile, String localFile, long nativeCallbackObject, boolean wantProgress, Semaphore cancelSem) {
        return getFileInternal(remoteFile, localFile, (byte[][]) null, nativeCallbackObject, wantProgress, cancelSem);
    }

    private boolean getFileWithBuffer(String remoteFile, byte[][] data, long nativeCallbackObject, boolean wantProgress, Semaphore cancelSem) {
        return getFileInternal(remoteFile, null, data, nativeCallbackObject, wantProgress, cancelSem);
    }

    private boolean getFileInternal(String remoteFile, String localFile, byte[][] data, long nativeCallbackObject, boolean wantProgress, Semaphore cancelSem) {
        String str = localFile;
        FileOutputStream dst = null;
        boolean ret = true;
        double[] totalSize = new double[]{BrickValues.SET_COLOR_TO};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BLEFtp getFile ");
        String str2 = remoteFile;
        stringBuilder.append(str2);
        ARSALPrint.m530d("DBG", stringBuilder.toString());
        String remoteFile2 = normalizePathName(str2);
        if (wantProgress) {
            ret = sizeFile(remoteFile2, totalSize);
        }
        if (ret && str != null) {
            try {
                dst = new FileOutputStream(str);
            } catch (FileNotFoundException e) {
                FileNotFoundException e2 = e;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(APP_TAG);
                stringBuilder2.append(e2.toString());
                ARSALPrint.m532e("DBG", stringBuilder2.toString());
                ret = false;
            }
        }
        FileOutputStream dst2 = dst;
        if (ret) {
            ret = sendCommand("GET", remoteFile2, r8.handling);
        }
        boolean ret2 = ret;
        if (ret2) {
            ret2 = readGetData((int) totalSize[0], dst2, data, nativeCallbackObject, cancelSem);
        }
        if (dst2 != null) {
            try {
                dst2.close();
            } catch (IOException e3) {
            }
        }
        return ret2;
    }

    private boolean abortPutFile(String remoteFile) {
        boolean resume;
        int[] resumeIndex = new int[]{0};
        int[] remoteSize = new int[]{0};
        String remoteFile2 = normalizePathName(remoteFile);
        boolean ret = readPutResumeIndex(remoteFile2, resumeIndex, remoteSize);
        if (!ret || resumeIndex[0] <= 0) {
            resume = false;
        } else {
            resume = true;
        }
        if (resume) {
            boolean ret2 = sendCommand("PUT", remoteFile2, r9.handling);
            if (ret2) {
                ret = sendPutData(0, null, resumeIndex[0], false, true, 0, null);
            } else {
                ret = ret2;
            }
        }
        deleteFile(remoteFile2);
        return ret;
    }

    private boolean putFile(String remoteFile, String localFile, long nativeCallbackObject, boolean resume, Semaphore cancelSem) {
        boolean resume2;
        int totalSize;
        ARUtilsBLEFtp aRUtilsBLEFtp = this;
        String str = localFile;
        long j = nativeCallbackObject;
        int[] resumeIndex = new int[]{0};
        int[] remoteSize = new int[]{0};
        boolean ret = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BLEFtp putFile ");
        stringBuilder.append(remoteFile);
        ARSALPrint.m530d("DBG", stringBuilder.toString());
        String remoteFile2 = normalizePathName(remoteFile);
        if (resume) {
            boolean z;
            if (true) {
                ret = readPutResumeIndex(remoteFile2, resumeIndex, remoteSize);
                if (!ret) {
                    ret = true;
                    resumeIndex[0] = 0;
                    z = false;
                    if (resumeIndex[0] > 0) {
                        z = true;
                    }
                    resume2 = z;
                }
            }
            z = resume;
            if (resumeIndex[0] > 0) {
                z = true;
            }
            resume2 = z;
        } else {
            abortPutFile(remoteFile2);
            resume2 = resume;
        }
        ARUtilsFileSystem fileSys = new ARUtilsFileSystem();
        try {
            totalSize = (int) fileSys.getFileSize(str);
            ret = ret;
        } catch (ARUtilsException e) {
            ARUtilsException e2 = e;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(APP_TAG);
            stringBuilder2.append(e2.toString());
            ARSALPrint.m532e("DBG", stringBuilder2.toString());
            ret = false;
            totalSize = 0;
        }
        int i;
        boolean z2;
        ARUtilsFileSystem aRUtilsFileSystem;
        String str2;
        if (ret && resume2 && remoteSize[0] == totalSize) {
            ARSALPrint.m530d("DBG", "BLEFtp full resume");
            if (j != 0) {
                nativeProgressCallback(j, 100.0f);
            }
            i = totalSize;
            z2 = resume2;
            aRUtilsFileSystem = fileSys;
            str2 = remoteFile2;
        } else {
            boolean ret2;
            FileInputStream src;
            FileInputStream src2;
            if (ret) {
                ret = sendCommand("PUT", remoteFile2, aRUtilsBLEFtp.handling);
            }
            if (ret) {
                try {
                    ret2 = ret;
                    src = new FileInputStream(str);
                } catch (FileNotFoundException e3) {
                    FileNotFoundException e4 = e3;
                    StringBuilder stringBuilder3 = new StringBuilder();
                    FileInputStream src3 = null;
                    stringBuilder3.append(APP_TAG);
                    stringBuilder3.append(e4.toString());
                    ARSALPrint.m532e("DBG", stringBuilder3.toString());
                    ret2 = false;
                    src = src3;
                }
            } else {
                ret2 = ret;
                src = null;
            }
            if (ret2) {
                src2 = src;
                ret = sendPutData(totalSize, src, resumeIndex[0], resume2, false, j, cancelSem);
            } else {
                src2 = src;
                i = totalSize;
                z2 = resume2;
                aRUtilsFileSystem = fileSys;
                str2 = remoteFile2;
                ret = ret2;
            }
            FileInputStream src4 = src2;
            if (src4 != null) {
                try {
                    src4.close();
                } catch (IOException e5) {
                }
            }
        }
        return ret;
    }

    private boolean deleteFile(String remoteFile) {
        boolean ret = sendCommand("DEL", remoteFile, this.handling);
        if (ret) {
            return readDeleteData();
        }
        return ret;
    }

    private boolean renameFile(String oldNamePath, String newNamePath) {
        String param = new StringBuilder();
        param.append(oldNamePath);
        param.append(FormatHelper.SPACE);
        param.append(newNamePath);
        if ("REN".length() + param.toString().length() > 132) {
            return renameLongFile(oldNamePath, newNamePath);
        }
        return renameShortFile(oldNamePath, newNamePath);
    }

    private boolean renameShortFile(String oldNamePath, String newNamePath) {
        String param = new StringBuilder();
        param.append(oldNamePath);
        param.append(FormatHelper.SPACE);
        param.append(newNamePath);
        boolean ret = sendCommand("REN", param.toString(), this.handling);
        if (ret) {
            return readRenameData();
        }
        return ret;
    }

    private boolean renameLongFile(String oldNamePath, String newNamePath) {
        boolean ret = sendCommand("RNFR", oldNamePath, this.handling);
        if (ret) {
            ret = readRenameData();
        }
        if (ret) {
            ret = sendCommand("RNTO", newNamePath, this.handling);
        }
        if (ret) {
            return readRenameData();
        }
        return ret;
    }

    private boolean sendCommand(String cmd, String param, BluetoothGattCharacteristic characteristic) {
        boolean ret = true;
        byte[] bufferParam = null;
        byte[] bufferCmd = null;
        int indexBuffer = 0;
        try {
            bufferCmd = cmd.getBytes(UrlUtils.UTF8);
        } catch (UnsupportedEncodingException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(APP_TAG);
            stringBuilder.append(e.toString());
            ARSALPrint.m532e("DBG", stringBuilder.toString());
            ret = false;
        }
        if (ret && param != null) {
            try {
                bufferParam = param.getBytes(UrlUtils.UTF8);
            } catch (UnsupportedEncodingException e2) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(APP_TAG);
                stringBuilder2.append(e2.toString());
                ARSALPrint.m532e("DBG", stringBuilder2.toString());
                ret = false;
            }
        }
        if (ret && bufferParam != null && (cmd.length() + bufferParam.length) + 1 > 132) {
            ARSALPrint.m532e("DBG", "BLEFtp Block size error");
            ret = false;
        }
        if (!ret) {
            return ret;
        }
        byte[] buffer;
        if (bufferParam == null) {
            buffer = new byte[(bufferCmd.length + 1)];
        } else {
            buffer = new byte[((bufferCmd.length + bufferParam.length) + 1)];
        }
        System.arraycopy(bufferCmd, 0, buffer, 0, bufferCmd.length);
        indexBuffer = bufferCmd.length;
        if (bufferParam != null) {
            System.arraycopy(bufferParam, 0, buffer, indexBuffer, bufferParam.length);
            indexBuffer += bufferParam.length;
        }
        buffer[indexBuffer] = (byte) 0;
        return sendBufferBlocks(buffer, characteristic);
    }

    private boolean sendResponse(String cmd, BluetoothGattCharacteristic characteristic) {
        boolean ret = true;
        byte[] bufferCmd = null;
        int indexBuffer = 0;
        try {
            bufferCmd = cmd.getBytes(UrlUtils.UTF8);
        } catch (UnsupportedEncodingException e) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(APP_TAG);
                stringBuilder.append(e.toString());
                ARSALPrint.m532e("DBG", stringBuilder.toString());
                ret = false;
            } catch (InterruptedException e2) {
            }
        }
        if (ret && cmd.length() + 1 > 132) {
            ARSALPrint.m532e("DBG", "BLEFtp Block size error");
            ret = false;
        }
        if (ret) {
            byte[] buffer = new byte[(bufferCmd.length + 1)];
            System.arraycopy(bufferCmd, 0, buffer, 0, bufferCmd.length);
            buffer[bufferCmd.length] = (byte) 0;
            Thread.sleep(35, 0);
            ret = this.bleManager.writeData(buffer, characteristic);
        }
        return ret;
    }

    private boolean sendResponse(byte[] buffer, BluetoothGattCharacteristic characteristic) {
        try {
            Thread.sleep(35, 0);
            return this.bleManager.writeData(buffer, characteristic);
        } catch (InterruptedException e) {
            return true;
        }
    }

    private boolean sendBufferBlocks(byte[] buffer, BluetoothGattCharacteristic characteristic) {
        boolean ret = true;
        int bufferIndex = 0;
        try {
            if (buffer.length == 0) {
                byte[] block = new byte[]{(byte) 3};
                Thread.sleep(35, 0);
                ret = this.bleManager.writeData(block, characteristic);
                ARSALPrint.m530d("DBG", "BLEFtp block 1, 0");
            } else {
                while (ret && bufferIndex < buffer.length) {
                    int blockSize = 20;
                    if (buffer.length - bufferIndex <= 19) {
                        blockSize = (buffer.length - bufferIndex) + 1;
                    }
                    byte[] block2 = new byte[blockSize];
                    if (buffer.length < 20) {
                        block2[0] = (byte) 3;
                    } else if (bufferIndex == 0) {
                        block2[0] = (byte) 2;
                    } else if (bufferIndex + 19 >= buffer.length) {
                        block2[0] = (byte) 1;
                    } else {
                        block2[0] = (byte) 0;
                    }
                    System.arraycopy(buffer, bufferIndex, block2, 1, blockSize - 1);
                    bufferIndex += blockSize - 1;
                    Thread.sleep(35, 0);
                    ret = this.bleManager.writeData(block2, characteristic);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("BLEFtp block ");
                    stringBuilder.append(blockSize);
                    stringBuilder.append(", ");
                    stringBuilder.append(bufferIndex);
                    ARSALPrint.m530d("DBG", stringBuilder.toString());
                }
            }
        } catch (InterruptedException e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(APP_TAG);
            stringBuilder2.append(e.toString());
            ARSALPrint.m532e("DBG", stringBuilder2.toString());
        }
        return ret;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean readBufferBlocks(byte[][] r18) {
        /*
        r17 = this;
        r0 = r17;
        r1 = r18;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r3 = 1;
        r4 = 0;
        r5 = 0;
        r6 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        r7 = new byte[r6];
        r8 = 0;
        r9 = r7;
        r7 = r5;
        r5 = r4;
        r4 = r3;
        r3 = 0;
    L_0x0016:
        r10 = r2.size();
        r11 = 1;
        if (r10 != 0) goto L_0x002d;
    L_0x001d:
        r10 = r0.bleManager;
        r12 = "kARUTILS_BLEFtp_Getting";
        r4 = r10.readDataNotificationData(r2, r11, r12);
        if (r4 != 0) goto L_0x002d;
    L_0x0027:
        r10 = r0.bleManager;
        r4 = r10.isDeviceConnected();
    L_0x002d:
        if (r4 != r11) goto L_0x00ea;
    L_0x002f:
        r10 = r2.size();
        if (r10 <= 0) goto L_0x00ea;
    L_0x0035:
        r10 = 0;
        r12 = r2.get(r8);
        r10 = r12;
        r10 = (com.parrot.arsdk.arsal.ARSALBLEManager.ARSALManagerNotificationData) r10;
        r12 = r10.value;
        r12 = r12.length;
        r13 = r10.value;
        r14 = 0;
        r15 = "DBG";
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r11 = "BLEFtp block length ";
        r6.append(r11);
        r6.append(r12);
        r6 = r6.toString();
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r15, r6);
        if (r12 <= 0) goto L_0x00e0;
    L_0x005b:
        r6 = r13[r8];
        switch(r6) {
            case 0: goto L_0x0075;
            case 1: goto L_0x0069;
            case 2: goto L_0x0075;
            case 3: goto L_0x0069;
            default: goto L_0x0060;
        };
    L_0x0060:
        r6 = "DBG";
        r11 = "BLEFtp Block state error";
        com.parrot.arsdk.arsal.ARSALPrint.m532e(r6, r11);
        r4 = 0;
        goto L_0x007a;
    L_0x0069:
        r6 = "DBG";
        r11 = "BLEFtp this is the last block.";
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r6, r11);
        r5 = 1;
        r12 = r12 + -1;
        r6 = 1;
        goto L_0x0079;
    L_0x0075:
        r12 = r12 + -1;
        r6 = 1;
    L_0x0079:
        r14 = r6;
    L_0x007a:
        r6 = 1;
        if (r4 != r6) goto L_0x00e7;
    L_0x007d:
        r6 = r7 + r12;
        r11 = r9.length;
        if (r6 <= r11) goto L_0x00b3;
    L_0x0082:
        r6 = r7 + r12;
        r11 = r9.length;
        r15 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        r11 = r11 + r15;
        if (r11 >= r6) goto L_0x008b;
    L_0x008a:
        r11 = r6;
    L_0x008b:
        r15 = "DBG";
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r0 = "BLEFtp buffer alloc size ";
        r8.append(r0);
        r0 = r9.length;
        r8.append(r0);
        r0 = " -> ";
        r8.append(r0);
        r8.append(r11);
        r0 = r8.toString();
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r15, r0);
        r0 = r9;
        r8 = new byte[r11];
        r9 = r0.length;
        r15 = 0;
        java.lang.System.arraycopy(r0, r15, r8, r15, r9);
        r9 = r8;
    L_0x00b3:
        java.lang.System.arraycopy(r13, r14, r9, r7, r12);
        r7 = r7 + r12;
        r3 = r3 + 1;
        r0 = "DBG";
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r8 = "BLEFtp block ";
        r6.append(r8);
        r6.append(r3);
        r8 = ", ";
        r6.append(r8);
        r6.append(r12);
        r8 = ", ";
        r6.append(r8);
        r6.append(r7);
        r6 = r6.toString();
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r0, r6);
        goto L_0x00e7;
    L_0x00e0:
        r0 = "DBG";
        r6 = "BLEFtp Empty block ";
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r0, r6);
    L_0x00e7:
        r2.remove(r10);
    L_0x00ea:
        r0 = 1;
        if (r4 != r0) goto L_0x00f7;
    L_0x00ed:
        if (r5 == 0) goto L_0x00f0;
    L_0x00ef:
        goto L_0x00f7;
    L_0x00f0:
        r0 = r17;
        r6 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        r8 = 0;
        goto L_0x0016;
    L_0x00f7:
        if (r7 <= 0) goto L_0x0108;
    L_0x00f9:
        r0 = new byte[r7];
        r6 = 0;
        java.lang.System.arraycopy(r9, r6, r0, r6, r7);
        if (r1 == 0) goto L_0x0107;
    L_0x0101:
        r8 = r1.length;
        if (r8 <= 0) goto L_0x0107;
    L_0x0104:
        r1[r6] = r0;
        goto L_0x0108;
    L_0x0107:
        r4 = 0;
    L_0x0108:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.arsdk.arutils.ARUtilsBLEFtp.readBufferBlocks(byte[][]):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean sendPutData(int r34, java.io.FileInputStream r35, int r36, boolean r37, boolean r38, long r39, java.util.concurrent.Semaphore r41) {
        /*
        r33 = this;
        r1 = r33;
        r2 = r38;
        r3 = r39;
        r5 = new java.io.BufferedInputStream;
        r6 = r35;
        r5.<init>(r6);
        r7 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        r8 = new byte[r7];
        r9 = 1;
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = new com.parrot.arsdk.arutils.ARUtilsMD5;
        r15.<init>();
        r7 = new com.parrot.arsdk.arutils.ARUtilsMD5;
        r7.<init>();
        r6 = 1;
        r17 = r9;
        r9 = new java.lang.String[r6];
        r18 = "";
        r6 = 0;
        r9[r6] = r18;
        r18 = 0;
        r19 = 0;
        r20 = 0;
        r21 = 0;
        r6 = 1;
        if (r2 != r6) goto L_0x0040;
    L_0x0036:
        r14 = 1;
        r6 = 0;
        r22 = 0;
        r23 = r10;
        r10 = r6;
        r6 = r22;
        goto L_0x0046;
    L_0x0040:
        r6 = r37;
        r23 = r10;
        r10 = r36;
    L_0x0046:
        if (r2 != 0) goto L_0x0061;
    L_0x0048:
        r24 = r14;
        r2 = 0;
        r14 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        r22 = r5.read(r8, r2, r14);	 Catch:{ IOException -> 0x0054 }
        r2 = r22;
        goto L_0x0065;
    L_0x0054:
        r0 = move-exception;
        r1 = r0;
        r25 = r5;
        r5 = r7;
        r2 = r23;
        r14 = r24;
    L_0x005d:
        r7 = r34;
        goto L_0x031d;
    L_0x0061:
        r24 = r14;
        r2 = r23;
    L_0x0065:
        if (r2 <= 0) goto L_0x0184;
    L_0x0067:
        r11 = r11 + 1;
        r12 = r12 + 1;
        r13 = r13 + r2;
        r14 = 0;
        r7.update(r8, r14, r2);	 Catch:{ IOException -> 0x0176 }
        if (r6 == 0) goto L_0x00ac;
    L_0x0072:
        r14 = 1;
        if (r6 != r14) goto L_0x007c;
    L_0x0075:
        if (r12 <= r10) goto L_0x007c;
    L_0x0077:
        r25 = r5;
        r26 = r7;
        goto L_0x00b0;
    L_0x007c:
        r14 = "DBG";
        r25 = r5;
        r5 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0098 }
        r5.<init>();	 Catch:{ IOException -> 0x0098 }
        r26 = r7;
        r7 = "BLEFtp resume ";
        r5.append(r7);	 Catch:{ IOException -> 0x016c }
        r5.append(r11);	 Catch:{ IOException -> 0x016c }
        r5 = r5.toString();	 Catch:{ IOException -> 0x016c }
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r14, r5);	 Catch:{ IOException -> 0x016c }
        goto L_0x00f6;
    L_0x0098:
        r0 = move-exception;
        r1 = r0;
        r5 = r7;
        r14 = r24;
        r7 = r34;
        goto L_0x031d;
    L_0x00a1:
        r0 = move-exception;
        r25 = r5;
        r1 = r0;
        r5 = r7;
        r14 = r24;
        r7 = r34;
        goto L_0x0182;
    L_0x00ac:
        r25 = r5;
        r26 = r7;
    L_0x00b0:
        r5 = 0;
        r15.update(r8, r5, r2);	 Catch:{ IOException -> 0x016c }
        r19 = r8;
        r5 = 132; // 0x84 float:1.85E-43 double:6.5E-322;
        if (r2 == r5) goto L_0x00ca;
    L_0x00ba:
        r7 = new byte[r2];	 Catch:{ IOException -> 0x016c }
        r14 = 0;
        java.lang.System.arraycopy(r8, r14, r7, r14, r2);	 Catch:{ IOException -> 0x00c1 }
        goto L_0x00cc;
    L_0x00c1:
        r0 = move-exception;
        r1 = r0;
        r19 = r7;
        r14 = r24;
        r5 = r26;
        goto L_0x005d;
    L_0x00ca:
        r7 = r19;
    L_0x00cc:
        r14 = r1.transferring;	 Catch:{ IOException -> 0x015e }
        r14 = r1.sendBufferBlocks(r7, r14);	 Catch:{ IOException -> 0x015e }
        r17 = r14;
        r14 = "DBG";
        r5 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x015e }
        r5.<init>();	 Catch:{ IOException -> 0x015e }
        r27 = r7;
        r7 = "BLEFtp packet ";
        r5.append(r7);	 Catch:{ IOException -> 0x0152 }
        r5.append(r11);	 Catch:{ IOException -> 0x0152 }
        r7 = ", ";
        r5.append(r7);	 Catch:{ IOException -> 0x0152 }
        r5.append(r2);	 Catch:{ IOException -> 0x0152 }
        r5 = r5.toString();	 Catch:{ IOException -> 0x0152 }
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r14, r5);	 Catch:{ IOException -> 0x0152 }
        r19 = r27;
    L_0x00f6:
        r22 = 0;
        r5 = (r3 > r22 ? 1 : (r3 == r22 ? 0 : -1));
        if (r5 == 0) goto L_0x0123;
    L_0x00fc:
        r5 = (float) r13;
        r7 = r34;
        r14 = (float) r7;
        r5 = r5 / r14;
        r14 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r5 = r5 * r14;
        r14 = 1;
        if (r6 != r14) goto L_0x0118;
    L_0x0108:
        if (r12 >= r10) goto L_0x0118;
    L_0x010a:
        r14 = r5 - r21;
        r16 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r14 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r14 <= 0) goto L_0x011b;
    L_0x0112:
        r21 = r5;
        r1.nativeProgressCallback(r3, r5);	 Catch:{ IOException -> 0x011e }
        goto L_0x011b;
    L_0x0118:
        r1.nativeProgressCallback(r3, r5);	 Catch:{ IOException -> 0x011e }
    L_0x011b:
        r20 = r5;
        goto L_0x0125;
    L_0x011e:
        r0 = move-exception;
        r1 = r0;
        r20 = r5;
        goto L_0x0170;
    L_0x0123:
        r7 = r34;
    L_0x0125:
        r5 = r41;
        r14 = r1.isConnectionCanceled(r5);	 Catch:{ IOException -> 0x0150 }
        if (r14 == 0) goto L_0x0194;
    L_0x012d:
        r14 = "DBG";
        r3 = "BLEFtp Canceled received";
        com.parrot.arsdk.arsal.ARSALPrint.m532e(r14, r3);	 Catch:{ IOException -> 0x0150 }
        r3 = 0;
        r4 = new byte[r3];	 Catch:{ IOException -> 0x0150 }
        r3 = r4;
        r4 = 0;
        r14 = r1.transferring;	 Catch:{ IOException -> 0x014b }
        r14 = r1.sendResponse(r3, r14);	 Catch:{ IOException -> 0x014b }
        r4 = r14;
        r14 = r1.readPutMd5(r9);	 Catch:{ IOException -> 0x014b }
        r4 = r14;
        r17 = 0;
        r19 = r3;
        goto L_0x0194;
    L_0x014b:
        r0 = move-exception;
        r1 = r0;
        r19 = r3;
        goto L_0x0170;
    L_0x0150:
        r0 = move-exception;
        goto L_0x016f;
    L_0x0152:
        r0 = move-exception;
        r7 = r34;
        r1 = r0;
        r14 = r24;
        r5 = r26;
        r19 = r27;
        goto L_0x031d;
    L_0x015e:
        r0 = move-exception;
        r27 = r7;
        r7 = r34;
        r1 = r0;
        r14 = r24;
        r5 = r26;
        r19 = r27;
        goto L_0x031d;
    L_0x016c:
        r0 = move-exception;
        r7 = r34;
    L_0x016f:
        r1 = r0;
    L_0x0170:
        r14 = r24;
        r5 = r26;
        goto L_0x031d;
    L_0x0176:
        r0 = move-exception;
        r25 = r5;
        r26 = r7;
        r7 = r34;
        r1 = r0;
        r14 = r24;
        r5 = r26;
    L_0x0182:
        goto L_0x031d;
    L_0x0184:
        r25 = r5;
        r26 = r7;
        r7 = r34;
        r5 = r41;
        r3 = -1;
        if (r2 != r3) goto L_0x0194;
    L_0x018f:
        r3 = 1;
        r14 = r3;
        r3 = r17;
        goto L_0x0198;
    L_0x0194:
        r3 = r17;
        r14 = r24;
    L_0x0198:
        r4 = 1;
        if (r3 != r4) goto L_0x024a;
    L_0x019b:
        r4 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r11 >= r4) goto L_0x01ab;
    L_0x019f:
        r4 = 1;
        if (r14 != r4) goto L_0x01a5;
    L_0x01a2:
        if (r11 <= 0) goto L_0x01a5;
    L_0x01a4:
        goto L_0x01ab;
    L_0x01a5:
        r28 = r2;
        r29 = r3;
        goto L_0x024e;
    L_0x01ab:
        r11 = 0;
        if (r6 == 0) goto L_0x01b9;
    L_0x01ae:
        r4 = 1;
        if (r6 != r4) goto L_0x01b4;
    L_0x01b1:
        if (r12 <= r10) goto L_0x01b4;
    L_0x01b3:
        goto L_0x01b9;
    L_0x01b4:
        r28 = r2;
        r4 = r3;
        goto L_0x0250;
    L_0x01b9:
        r4 = r15.digest();	 Catch:{ IOException -> 0x023e }
        r15.initialize();	 Catch:{ IOException -> 0x0235 }
        r28 = r2;
        r2 = "DBG";
        r29 = r3;
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x021b }
        r3.<init>();	 Catch:{ IOException -> 0x021b }
        r5 = "BLEFtp sending md5 ";
        r3.append(r5);	 Catch:{ IOException -> 0x021b }
        r3.append(r4);	 Catch:{ IOException -> 0x021b }
        r3 = r3.toString();	 Catch:{ IOException -> 0x021b }
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r2, r3);	 Catch:{ IOException -> 0x021b }
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x021b }
        r2.<init>();	 Catch:{ IOException -> 0x021b }
        r3 = "MD5";
        r2.append(r3);	 Catch:{ IOException -> 0x021b }
        r2.append(r4);	 Catch:{ IOException -> 0x021b }
        r2 = r2.toString();	 Catch:{ IOException -> 0x021b }
        r3 = "UTF8";
        r3 = r2.getBytes(r3);	 Catch:{ IOException -> 0x0216 }
        r4 = r1.transferring;	 Catch:{ IOException -> 0x020f }
        r4 = r1.sendBufferBlocks(r3, r4);	 Catch:{ IOException -> 0x020f }
        r5 = 1;
        if (r4 != r5) goto L_0x020a;
    L_0x01fa:
        r5 = r33.readPudDataWritten();	 Catch:{ IOException -> 0x0200 }
        r4 = r5;
        goto L_0x020a;
    L_0x0200:
        r0 = move-exception;
        r1 = r0;
        r18 = r2;
        r19 = r3;
        r17 = r4;
        goto L_0x0281;
    L_0x020a:
        r18 = r2;
        r19 = r3;
        goto L_0x0250;
    L_0x020f:
        r0 = move-exception;
        r1 = r0;
        r18 = r2;
        r19 = r3;
        goto L_0x021f;
    L_0x0216:
        r0 = move-exception;
        r1 = r0;
        r18 = r2;
        goto L_0x021f;
    L_0x021b:
        r0 = move-exception;
        r1 = r0;
        r18 = r4;
    L_0x021f:
        r5 = r26;
        r2 = r28;
        r17 = r29;
        goto L_0x031d;
    L_0x0227:
        r0 = move-exception;
        r29 = r3;
        r1 = r0;
        r18 = r4;
        r5 = r26;
        r2 = r28;
        r17 = r29;
        goto L_0x031d;
    L_0x0235:
        r0 = move-exception;
        r28 = r2;
        r29 = r3;
        r1 = r0;
        r18 = r4;
        goto L_0x0244;
    L_0x023e:
        r0 = move-exception;
        r28 = r2;
        r29 = r3;
        r1 = r0;
    L_0x0244:
        r5 = r26;
        r17 = r29;
        goto L_0x031d;
    L_0x024a:
        r28 = r2;
        r29 = r3;
    L_0x024e:
        r4 = r29;
    L_0x0250:
        r2 = 1;
        if (r4 != r2) goto L_0x0264;
    L_0x0253:
        if (r14 == 0) goto L_0x0256;
    L_0x0255:
        goto L_0x0264;
    L_0x0256:
        r17 = r4;
        r5 = r25;
        r7 = r26;
        r23 = r28;
        r2 = r38;
        r3 = r39;
        goto L_0x0046;
    L_0x0264:
        if (r4 != r2) goto L_0x033b;
    L_0x0266:
        if (r14 != r2) goto L_0x033b;
    L_0x0268:
        r2 = 0;
        r3 = new byte[r2];	 Catch:{ IOException -> 0x0315 }
        r2 = r3;
        r3 = r1.transferring;	 Catch:{ IOException -> 0x0308 }
        r3 = r1.sendResponse(r2, r3);	 Catch:{ IOException -> 0x0308 }
        r4 = 1;
        if (r3 != r4) goto L_0x0287;
    L_0x0275:
        r5 = r1.readPutMd5(r9);	 Catch:{ IOException -> 0x027b }
        r3 = r5;
        goto L_0x0287;
    L_0x027b:
        r0 = move-exception;
        r1 = r0;
        r19 = r2;
        r17 = r3;
    L_0x0281:
        r5 = r26;
        r2 = r28;
        goto L_0x031d;
    L_0x0287:
        if (r3 != r4) goto L_0x02fd;
    L_0x0289:
        r5 = r26;
        r4 = r5.digest();	 Catch:{ IOException -> 0x02f0 }
        r1 = "DBG";
        r30 = r2;
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x02da }
        r2.<init>();	 Catch:{ IOException -> 0x02da }
        r31 = r3;
        r3 = "BLEFtp md5 end";
        r2.append(r3);	 Catch:{ IOException -> 0x02cf }
        r2.append(r4);	 Catch:{ IOException -> 0x02cf }
        r2 = r2.toString();	 Catch:{ IOException -> 0x02cf }
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r1, r2);	 Catch:{ IOException -> 0x02cf }
        r1 = 0;
        r1 = r9[r1];	 Catch:{ IOException -> 0x02cf }
        r1 = r1.compareTo(r4);	 Catch:{ IOException -> 0x02cf }
        if (r1 == 0) goto L_0x02c0;
    L_0x02b2:
        r1 = "DBG";
        r2 = "BLEFtp md5 end Failed";
        com.parrot.arsdk.arsal.ARSALPrint.m532e(r1, r2);	 Catch:{ IOException -> 0x02cf }
        r1 = 0;
        r18 = r4;
        r19 = r30;
        goto L_0x033e;
    L_0x02c0:
        r1 = "DBG";
        r2 = "BLEFtp md5 end ok";
        com.parrot.arsdk.arsal.ARSALPrint.m530d(r1, r2);	 Catch:{ IOException -> 0x02cf }
        r18 = r4;
        r19 = r30;
        r1 = r31;
        goto L_0x033e;
    L_0x02cf:
        r0 = move-exception;
        r1 = r0;
        r18 = r4;
        r2 = r28;
        r19 = r30;
        r17 = r31;
        goto L_0x031d;
    L_0x02da:
        r0 = move-exception;
        r31 = r3;
        r1 = r0;
        r18 = r4;
        r2 = r28;
        r19 = r30;
        r17 = r31;
        goto L_0x031d;
    L_0x02e7:
        r0 = move-exception;
        r30 = r2;
        r31 = r3;
        r1 = r0;
        r18 = r4;
        goto L_0x02f6;
    L_0x02f0:
        r0 = move-exception;
        r30 = r2;
        r31 = r3;
        r1 = r0;
    L_0x02f6:
        r2 = r28;
        r19 = r30;
        r17 = r31;
        goto L_0x031d;
    L_0x02fd:
        r30 = r2;
        r31 = r3;
        r5 = r26;
        r19 = r30;
        r1 = r31;
        goto L_0x033e;
    L_0x0308:
        r0 = move-exception;
        r30 = r2;
        r5 = r26;
        r1 = r0;
        r17 = r4;
        r2 = r28;
        r19 = r30;
        goto L_0x031d;
    L_0x0315:
        r0 = move-exception;
        r5 = r26;
        r1 = r0;
        r17 = r4;
        r2 = r28;
    L_0x031d:
        r3 = "DBG";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r32 = r2;
        r2 = "BLEFtp ";
        r4.append(r2);
        r2 = r1.toString();
        r4.append(r2);
        r2 = r4.toString();
        com.parrot.arsdk.arsal.ARSALPrint.m532e(r3, r2);
        r1 = 0;
        goto L_0x0340;
    L_0x033b:
        r5 = r26;
        r1 = r4;
    L_0x033e:
        r32 = r28;
    L_0x0340:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.arsdk.arutils.ARUtilsBLEFtp.sendPutData(int, java.io.FileInputStream, int, boolean, boolean, long, java.util.concurrent.Semaphore):boolean");
    }

    private boolean readPutResumeIndex(String remoteFile, int[] resumeIndex, int[] totalSize) {
        double[] fileSize = new double[]{BrickValues.SET_COLOR_TO};
        resumeIndex[0] = 0;
        boolean ret = sizeFile(remoteFile, fileSize);
        if (ret) {
            resumeIndex[0] = ((int) fileSize[0]) / 132;
            totalSize[0] = (int) fileSize[0];
        }
        return ret;
    }

    private boolean readGetData(int fileSize, FileOutputStream dst, byte[][] data, long nativeCallbackObject, Semaphore cancelSem) {
        int i;
        Semaphore semaphore;
        byte[][] bArr;
        ARUtilsBLEFtp aRUtilsBLEFtp = this;
        int i2 = fileSize;
        FileOutputStream fileOutputStream = dst;
        long j = nativeCallbackObject;
        Semaphore semaphore2 = cancelSem;
        byte[][] notificationArray = new byte[1][];
        boolean endMD5 = false;
        String md5Txt = null;
        ARUtilsMD5 md5 = new ARUtilsMD5();
        boolean ret = true;
        ARUtilsMD5 md5End = new ARUtilsMD5();
        int packetCount = 0;
        boolean ret2 = ret;
        ret = false;
        int totalSize = 0;
        String md5Msg = null;
        int totalPacket = 0;
        while (true) {
            StringBuilder stringBuilder;
            int packetCount2 = packetCount;
            if (!ret2 || endMD5) {
                i = i2;
                semaphore = semaphore2;
                bArr = notificationArray;
            } else {
                String md5Msg2;
                boolean blockMD5 = false;
                md5.initialize();
                boolean z = ret;
                String md5Msg3 = md5Msg;
                boolean endFile = z;
                while (true) {
                    long j2;
                    long j3;
                    ret2 = readBufferBlocks(notificationArray);
                    if (ret2) {
                        boolean z2;
                        boolean z3;
                        boolean z4;
                        if (!ret2 || !ret2 || notificationArray[0] == null || blockMD5 || endMD5) {
                            bArr = notificationArray;
                            z2 = ret2;
                            z3 = blockMD5;
                            z4 = endMD5;
                            j2 = j;
                            i = i2;
                            j3 = j2;
                        } else {
                            z2 = ret2;
                            ret2 = notificationArray[0].length;
                            z3 = blockMD5;
                            blockMD5 = notificationArray[0];
                            int i3 = packetCount2 + 1;
                            z4 = endMD5;
                            endMD5 = totalPacket + 1;
                            bArr = notificationArray;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("BLEFtp == packet ");
                            stringBuilder2.append(ret2);
                            stringBuilder2.append(", ");
                            stringBuilder2.append(i3);
                            stringBuilder2.append(", ");
                            stringBuilder2.append(endMD5);
                            stringBuilder2.append(", ");
                            stringBuilder2.append(totalSize);
                            ARSALPrint.m530d("DBG", stringBuilder2.toString());
                            if (ret2 <= false) {
                                String md5Msg4;
                                StringBuilder stringBuilder3;
                                boolean z5;
                                if (endFile) {
                                    boolean endMD52;
                                    if (ret2) {
                                        md5Msg4 = new String(blockMD5, 0, ret2);
                                        stringBuilder3 = new StringBuilder();
                                        endMD52 = true;
                                        stringBuilder3.append("BLEFtp md5 end received ");
                                        stringBuilder3.append(md5Msg4);
                                        ARSALPrint.m530d("DBG", stringBuilder3.toString());
                                        md5Msg2 = md5Msg4;
                                        packetCount2 = i3;
                                        totalPacket = endMD5;
                                        ret2 = z2;
                                    } else {
                                        endMD52 = true;
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append("BLEFtp md5 end failed size ");
                                        stringBuilder.append(ret2);
                                        ARSALPrint.m530d("DBG", stringBuilder.toString());
                                        ret2 = false;
                                        packetCount2 = i3;
                                        z5 = endMD5;
                                        md5Msg2 = md5Msg3;
                                    }
                                    blockMD5 = z3;
                                    endMD5 = endMD52;
                                } else {
                                    if (!compareToString(blockMD5, ret2, BLE_PACKET_EOF)) {
                                        boolean z6;
                                        if (compareToString(blockMD5, ret2, CommonUtils.MD5_INSTANCE)) {
                                            if (i3 > 101) {
                                                StringBuilder stringBuilder4 = new StringBuilder();
                                                stringBuilder4.append("BLEFtp md5 failed packet count ");
                                                stringBuilder4.append(i3);
                                                ARSALPrint.m530d("DBG", stringBuilder4.toString());
                                            }
                                            if (ret2) {
                                                md5Msg4 = new String(blockMD5, 3, ret2 - 3);
                                                stringBuilder3 = new StringBuilder();
                                                boolean blockMD52 = true;
                                                stringBuilder3.append("BLEFtp md5 received ");
                                                stringBuilder3.append(md5Msg4);
                                                ARSALPrint.m530d("DBG", stringBuilder3.toString());
                                                md5Msg2 = md5Msg4;
                                                packetCount2 = i3;
                                                z5 = endMD5;
                                                ret2 = z2;
                                                endMD5 = z4;
                                                blockMD5 = blockMD52;
                                            } else {
                                                z6 = false;
                                                stringBuilder = new StringBuilder();
                                                stringBuilder.append("BLEFtp md5 failed size ");
                                                stringBuilder.append(ret2);
                                                ARSALPrint.m530d("DBG", stringBuilder.toString());
                                            }
                                        } else if (compareToStringIgnoreCase(blockMD5, ret2, "error")) {
                                            ARSALPrint.m532e("DBG", "BLEFtp Error received");
                                            z6 = false;
                                        } else {
                                            totalSize += ret2;
                                            md5.update(blockMD5, 0, ret2);
                                            md5End.update(blockMD5, 0, ret2);
                                            if (fileOutputStream != null) {
                                                try {
                                                    fileOutputStream.write(blockMD5, 0, ret2);
                                                } catch (IOException e) {
                                                    IOException e2 = e;
                                                    stringBuilder = new StringBuilder();
                                                    stringBuilder.append("BLEFtp failed writting file ");
                                                    stringBuilder.append(e2.toString());
                                                    ARSALPrint.m532e("DBG", stringBuilder.toString());
                                                    z2 = false;
                                                }
                                            } else {
                                                byte[] newData = new byte[totalSize];
                                                if (data[0] != null) {
                                                    System.arraycopy(data[0], 0, newData, 0, totalSize - ret2);
                                                }
                                                System.arraycopy(blockMD5, 0, newData, totalSize - ret2, ret2);
                                                data[0] = newData;
                                            }
                                            j3 = nativeCallbackObject;
                                            if (j3 != 0) {
                                                i = fileSize;
                                                if (i != 0) {
                                                    nativeProgressCallback(j3, (((float) totalSize) / ((float) i)) * 100.0f);
                                                }
                                                packetCount2 = i3;
                                                totalPacket = endMD5;
                                            }
                                        }
                                        ret2 = z6;
                                        packetCount2 = i3;
                                        z5 = endMD5;
                                        md5Msg2 = md5Msg3;
                                    } else if (ret2 == BLE_PACKET_EOF.length() + 1) {
                                        ARSALPrint.m530d("DBG", "BLEFtp End of file received ");
                                        endFile = true;
                                        packetCount2 = i3;
                                        totalPacket = endMD5;
                                        md5Msg2 = md5Msg3;
                                        ret2 = z2;
                                    } else {
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append("BLEFtp End of file failed size ");
                                        stringBuilder.append(ret2);
                                        ARSALPrint.m530d("DBG", stringBuilder.toString());
                                        endFile = true;
                                        ret2 = false;
                                        packetCount2 = i3;
                                        z5 = endMD5;
                                        md5Msg2 = md5Msg3;
                                    }
                                    blockMD5 = z3;
                                    endMD5 = z4;
                                }
                                j3 = nativeCallbackObject;
                                i = fileSize;
                            } else {
                                j3 = nativeCallbackObject;
                            }
                            i = fileSize;
                            packetCount2 = i3;
                            totalPacket = endMD5;
                        }
                        md5Msg2 = md5Msg3;
                        ret2 = z2;
                        blockMD5 = z3;
                        endMD5 = z4;
                    } else {
                        ret2 = aRUtilsBLEFtp.bleManager.isDeviceConnected();
                        bArr = notificationArray;
                        j2 = j;
                        i = i2;
                        j3 = j2;
                        md5Msg2 = md5Msg3;
                    }
                    bArr[0] = null;
                    if (!ret2 || blockMD5) {
                        break;
                    } else if (endMD5) {
                        break;
                    } else {
                        md5Msg3 = md5Msg2;
                        notificationArray = bArr;
                        semaphore2 = cancelSem;
                        j2 = j3;
                        i2 = i;
                        j = j2;
                        fileOutputStream = dst;
                    }
                }
                if (ret2 && blockMD5) {
                    boolean ret3;
                    packetCount = md5.digest();
                    if (md5Msg2.contentEquals(packetCount)) {
                        ARSALPrint.m530d("DBG", "BLEFtp md5 block ok");
                    } else {
                        ARSALPrint.m530d("DBG", "BLEFtp md5 block failed");
                    }
                    semaphore = cancelSem;
                    if (isConnectionCanceled(semaphore)) {
                        ARSALPrint.m532e("DBG", "BLEFtp Canceled received");
                        ret2 = false;
                    }
                    if (ret2) {
                        ret3 = sendResponse("MD5 OK", aRUtilsBLEFtp.getting);
                    } else {
                        ret3 = sendResponse("CANCEL", aRUtilsBLEFtp.getting);
                    }
                    ret2 = ret3;
                    md5Txt = packetCount;
                    packetCount = 0;
                } else {
                    semaphore = cancelSem;
                    packetCount = packetCount2;
                }
                semaphore2 = semaphore;
                i2 = i;
                ret = endFile;
                notificationArray = bArr;
                fileOutputStream = dst;
                md5Msg = md5Msg2;
                j = nativeCallbackObject;
            }
        }
        i = i2;
        semaphore = semaphore2;
        bArr = notificationArray;
        String md5Txt2;
        if (endMD5) {
            md5Txt2 = md5End.digest();
            stringBuilder = new StringBuilder();
            stringBuilder.append("BLEFtp md5 end computed ");
            stringBuilder.append(md5Txt2);
            ARSALPrint.m530d("DBG", stringBuilder.toString());
            if (md5Msg.contentEquals(md5Txt2)) {
                ARSALPrint.m530d("DBG", "BLEFtp md5 end OK");
            } else {
                ARSALPrint.m530d("DBG", "BLEFtp md5 end Failed");
                ret2 = false;
            }
        } else {
            ret2 = false;
            md5Txt2 = md5Txt;
        }
        if (!isConnectionCanceled(semaphore)) {
            return ret2;
        }
        ARSALPrint.m532e("DBG", "BLEFtp Canceled received");
        return false;
    }

    private boolean readPudDataWritten() {
        boolean ret = true;
        byte[][] notificationArray = new byte[1][];
        boolean ret2 = readBufferBlocks(notificationArray);
        if (notificationArray[0] != null) {
            int packetLen = notificationArray[0].length;
            byte[] packet = notificationArray[0];
            if (packetLen > 0) {
                if (compareToString(packet, packetLen, BLE_PACKET_WRITTEN)) {
                    ARSALPrint.m530d("DBG", "BLEFtp Written OK");
                    ret = true;
                } else if (compareToString(packet, packetLen, BLE_PACKET_NOT_WRITTEN)) {
                    ARSALPrint.m532e("DBG", "BLEFtp NOT Written");
                    ret = false;
                } else {
                    ARSALPrint.m532e("DBG", "BLEFtp UNKNOWN Written");
                    ret = false;
                }
            }
            return ret;
        }
        ARSALPrint.m532e("DBG", "BLEFtp UNKNOWN Written");
        return false;
    }

    private boolean readPutMd5(String[] md5Txt) {
        byte[][] notificationArray = new byte[][]{""};
        try {
            boolean ret = readBufferBlocks(notificationArray);
            if (ret) {
                if (notificationArray[0] != null) {
                    int packetLen = notificationArray[0].length;
                    byte[] packet = notificationArray[0];
                    if (packetLen <= 0) {
                        ARSALPrint.m532e("DBG", "BLEFtp md5 end failed");
                        ret = false;
                    } else if (packetLen == 32) {
                        md5Txt[0] = new String(packet, 0, packetLen, UrlUtils.UTF8);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("BLEFtp md5 end received ");
                        stringBuilder.append(md5Txt[0]);
                        ARSALPrint.m530d("DBG", stringBuilder.toString());
                    } else {
                        ARSALPrint.m532e("DBG", "BLEFtp md5 size failed");
                        ret = false;
                    }
                } else {
                    ARSALPrint.m532e("DBG", "BLEFtp md5 end size failed");
                    ret = false;
                }
            }
            return ret;
        } catch (UnsupportedEncodingException e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(APP_TAG);
            stringBuilder2.append(e.toString());
            ARSALPrint.m532e("DBG", stringBuilder2.toString());
            return false;
        }
    }

    private boolean readRenameData() {
        byte[][] notificationArray = new byte[1][];
        boolean ret = readBufferBlocks(notificationArray);
        if (!ret) {
            return ret;
        }
        if (notificationArray[0] != null) {
            int packetLen = notificationArray[0].length;
            byte[] packet = notificationArray[0];
            if (packetLen <= 0) {
                ARSALPrint.m532e("DBG", "BLEFtp Rename Failed");
                ret = false;
            } else if (compareToString(packet, packetLen, "Rename successful")) {
                ARSALPrint.m530d("DBG", "BLEFtp Rename Success");
                ret = true;
            } else {
                ARSALPrint.m532e("DBG", "BLEFtp Rename Failed");
                ret = false;
            }
            return ret;
        }
        ARSALPrint.m532e("DBG", "BLEFtp Rename Failed");
        return false;
    }

    private boolean readDeleteData() {
        byte[][] notificationArray = new byte[1][];
        boolean ret = readBufferBlocks(notificationArray);
        if (!ret) {
            return ret;
        }
        if (notificationArray[0] != null) {
            int packetLen = notificationArray[0].length;
            byte[] packet = notificationArray[0];
            if (packetLen <= 0) {
                ARSALPrint.m532e("DBG", "BLEFtp Delete Failed");
                ret = false;
            } else if (compareToString(packet, packetLen, BLE_PACKET_DELETE_SUCCESS)) {
                ARSALPrint.m530d("DBG", "BLEFtp Delete Success");
                ret = true;
            } else {
                ARSALPrint.m532e("DBG", "BLEFtp Delete Failed");
                ret = false;
            }
            return ret;
        }
        ARSALPrint.m532e("DBG", "BLEFtp Delete Failed");
        return false;
    }

    public static String getListNextItem(String list, String[] nextItem, String prefix, boolean isDirectory, int[] indexItem, int[] itemLen) {
        String str = prefix;
        String item = null;
        int endLine = 0;
        boolean z;
        int i;
        String lineData;
        if (list == null || nextItem == null) {
            z = isDirectory;
            i = 0;
            lineData = null;
        } else {
            int i2 = 0;
            if (nextItem[0] == null) {
                nextItem[0] = list;
                if (indexItem != null) {
                    indexItem[0] = 0;
                }
            } else if (indexItem != null) {
                indexItem[0] = indexItem[0] + itemLen[0];
            }
            i = 0;
            lineData = null;
            int ptr = 0;
            while (item == null && ptr != -1) {
                String line = nextItem[i2];
                endLine = line.length();
                ptr = line.indexOf(10);
                if (ptr == -1) {
                    ptr = line.indexOf(13);
                }
                if (ptr != -1) {
                    endLine = ptr;
                    if (line.charAt(endLine - 1) == '\r') {
                        endLine--;
                    }
                    ptr++;
                    nextItem[i2] = line.substring(ptr);
                    i = 0;
                    if (line.charAt(i2) == (isDirectory ? 'd' : '-')) {
                        int ptr2;
                        ptr = 0;
                        while (true) {
                            i2 = line.indexOf(32, i);
                            ptr2 = i2;
                            if (i2 != -1 && ptr2 < endLine && ptr < 8) {
                                if (line.charAt(ptr2 + 1) != ' ') {
                                    ptr++;
                                }
                                i = ptr2 + 1;
                            } else if (!(str == null || str.length() == 0 || line.indexOf(str, i) == -1)) {
                                i = -1;
                            }
                        }
                        i = -1;
                        if (i != -1) {
                            item = line.substring(i, i + (endLine - i));
                        }
                        ptr = ptr2;
                        i2 = 0;
                    }
                } else {
                    z = isDirectory;
                }
            }
            z = isDirectory;
            if (itemLen != null) {
                itemLen[0] = endLine;
            }
        }
        return item;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getListItemSize(java.lang.String r14, int r15, int r16, double[] r17) {
        /*
        r1 = r14;
        r2 = 0;
        r3 = 0;
        if (r1 == 0) goto L_0x007e;
    L_0x0005:
        if (r17 == 0) goto L_0x007e;
    L_0x0007:
        r4 = 0;
        r6 = 0;
        r17[r6] = r4;
        r7 = r15 + r16;
        r8 = -1;
        r9 = r2;
        r2 = r15;
    L_0x0011:
        r10 = 32;
        r11 = r1.indexOf(r10, r2);
        r12 = r11;
        r4 = -1;
        if (r11 == r4) goto L_0x007f;
    L_0x001b:
        if (r12 >= r7) goto L_0x007f;
    L_0x001d:
        r2 = 3;
        if (r3 >= r2) goto L_0x007f;
    L_0x0020:
        r5 = r12 + -1;
        r5 = r1.charAt(r5);
        if (r5 != r10) goto L_0x0078;
    L_0x0028:
        r5 = r12 + 1;
        r5 = r1.charAt(r5);
        if (r5 == r10) goto L_0x0078;
    L_0x0030:
        r3 = r3 + 1;
        r5 = r1.charAt(r6);
        r10 = 45;
        if (r5 != r10) goto L_0x0078;
    L_0x003a:
        if (r3 != r2) goto L_0x0078;
    L_0x003c:
        if (r8 != r4) goto L_0x0078;
    L_0x003e:
        r2 = r12 + 1;
        r4 = r1.substring(r2);
        r5 = new java.util.Scanner;
        r5.<init>(r4);
        r10 = r5.nextDouble();	 Catch:{ InputMismatchException -> 0x006b, IllegalStateException -> 0x0064, NoSuchElementException -> 0x005d }
        r17[r6] = r10;	 Catch:{ InputMismatchException -> 0x0058, IllegalStateException -> 0x0053, NoSuchElementException -> 0x005d }
        r10 = 0;
        goto L_0x0072;
    L_0x0053:
        r0 = move-exception;
        r8 = r0;
        r10 = 0;
        goto L_0x0068;
    L_0x0058:
        r0 = move-exception;
        r8 = r0;
        r10 = 0;
        goto L_0x006f;
    L_0x005d:
        r0 = move-exception;
        r8 = r0;
        r10 = 0;
        r17[r6] = r10;
        goto L_0x0072;
    L_0x0064:
        r0 = move-exception;
        r10 = 0;
        r8 = r0;
    L_0x0068:
        r17[r6] = r10;
        goto L_0x0071;
    L_0x006b:
        r0 = move-exception;
        r10 = 0;
        r8 = r0;
    L_0x006f:
        r17[r6] = r10;
    L_0x0072:
        r5.close();
        r9 = r4;
        r8 = r2;
        goto L_0x007a;
    L_0x0078:
        r10 = 0;
    L_0x007a:
        r2 = r12 + 1;
        r4 = r10;
        goto L_0x0011;
    L_0x007e:
        r9 = r2;
    L_0x007f:
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.arsdk.arutils.ARUtilsBLEFtp.getListItemSize(java.lang.String, int, int, double[]):java.lang.String");
    }

    private boolean compareToStringIgnoreCase(byte[] buffer, int len, String str) {
        try {
            byte[] strBytes = str.toLowerCase().getBytes(UrlUtils.UTF8);
            if (len < strBytes.length) {
                return false;
            }
            int i = 0;
            while (i < strBytes.length) {
                if (buffer[i] != strBytes[i] && buffer[i] != strBytes[i] - 32) {
                    return false;
                }
                i++;
            }
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private boolean compareToString(byte[] buffer, int len, String str) {
        try {
            boolean ret;
            byte[] strBytes = str.getBytes(UrlUtils.UTF8);
            if (len >= strBytes.length) {
                ret = true;
                for (int i = 0; i < strBytes.length; i++) {
                    if (buffer[i] != strBytes[i]) {
                        ret = false;
                        break;
                    }
                }
            } else {
                ret = false;
            }
            return ret;
        } catch (UnsupportedEncodingException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(APP_TAG);
            stringBuilder.append(e.toString());
            ARSALPrint.m532e("DBG", stringBuilder.toString());
            return false;
        }
    }

    private String normalizePathName(String name) {
        String newName = name;
        if (name.charAt(0) == '/') {
            return newName;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/");
        stringBuilder.append(name);
        return stringBuilder.toString();
    }
}
