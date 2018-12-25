package com.parrot.arsdk.arnetworkal;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.SystemClock;
import com.parrot.arsdk.arsal.ARSALBLEManager;
import com.parrot.arsdk.arsal.ARSALBLEManager.ARSALManagerNotificationData;
import com.parrot.arsdk.arsal.ARSALBLEManagerListener;
import com.parrot.arsdk.arsal.ARSALPrint;
import com.parrot.arsdk.arsal.ARSAL_ERROR_ENUM;
import com.parrot.arsdk.arsal.ARUUID;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import name.antonsmirnov.firmata.FormatHelper;

public class ARNetworkALBLENetwork implements ARSALBLEManagerListener {
    private static int ARNETWORKAL_BLENETWORK_BW_NB_ELEMS = 10;
    private static int ARNETWORKAL_BLENETWORK_BW_PROGRESS_EACH_SEC = 1;
    public static int ARNETWORKAL_BLENETWORK_HEADER_SIZE = 0;
    public static int ARNETWORKAL_BLENETWORK_MEDIA_MTU = 0;
    private static String ARNETWORKAL_BLENETWORK_NOTIFICATIONS_KEY = "ARNETWORKAL_BLENETWORK_NOTIFICATIONS_KEY";
    private static String ARNETWORKAL_BLENETWORK_PARROT_CHARACTERISTIC_PREFIX_UUID_FTP_21 = "fd23";
    private static String ARNETWORKAL_BLENETWORK_PARROT_CHARACTERISTIC_PREFIX_UUID_FTP_51 = "fd53";
    /* renamed from: ARNETWORKAL_BLENETWORK_PARROT_CHARACTERISTIC_PREFIX_UUID_RFCOMM_READ */
    public static final String f1775x139372ce = "fe02";
    /* renamed from: ARNETWORKAL_BLENETWORK_PARROT_CHARACTERISTIC_PREFIX_UUID_RFCOMM_WRITE */
    public static final String f1776x5f276587 = "fe01";
    private static String ARNETWORKAL_BLENETWORK_PARROT_SERVICE_PREFIX_UUID = "f";
    public static final String ARNETWORKAL_BLENETWORK_PARROT_SERVICE_PREFIX_UUID_RFCOMM = "fe00";
    private static final long BASIC_TEST_SLEEP = 2000;
    private static String TAG = "ARNetworkALBLENetwork";
    private ARSALBLEManager bleManager;
    private int bwCurrentDown;
    private int bwCurrentUp;
    private int[] bwElementDown;
    private int[] bwElementUp;
    private int bwIndex;
    private Semaphore bwSem;
    private Semaphore bwThreadRunning;
    private BluetoothDevice deviceBLEService;
    private long jniARNetworkALBLENetwork;
    private ArrayList<ARSALManagerNotificationData> recvArray = new ArrayList();
    private BluetoothGattService recvService;
    private BluetoothGattService sendService;

    private class DataPop {
        byte[] data = null;
        int id = null;
        int result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_DEFAULT.getValue();

        DataPop() {
        }

        void setData(byte[] data) {
            this.data = data;
        }

        void setId(int id) {
            this.id = id;
        }

        void setResult(int result) {
            this.result = result;
        }

        byte[] getData() {
            return this.data;
        }

        int getId() {
            return this.id;
        }

        int getResult() {
            return this.result;
        }
    }

    private static native int nativeGetHeaderSize();

    private static native int nativeGetMediaMTU();

    private static native void nativeJNIInit();

    private static native void nativeJNIOnDisconect(long j);

    static {
        ARNETWORKAL_BLENETWORK_MEDIA_MTU = 0;
        ARNETWORKAL_BLENETWORK_HEADER_SIZE = 0;
        ARNETWORKAL_BLENETWORK_MEDIA_MTU = nativeGetMediaMTU();
        ARNETWORKAL_BLENETWORK_HEADER_SIZE = nativeGetHeaderSize();
        nativeJNIInit();
    }

    public ARNetworkALBLENetwork(long jniARNetworkALBLENetwork, Context context) {
        this.bleManager = ARSALBLEManager.getInstance(context);
        this.jniARNetworkALBLENetwork = jniARNetworkALBLENetwork;
        this.bwElementUp = new int[ARNETWORKAL_BLENETWORK_BW_NB_ELEMS];
        this.bwElementDown = new int[ARNETWORKAL_BLENETWORK_BW_NB_ELEMS];
        this.bwSem = new Semaphore(0);
        this.bwThreadRunning = new Semaphore(0);
    }

    public int connect(BluetoothDevice deviceBLEService, int[] notificationIDArray) {
        ARSAL_ERROR_ENUM resultAL;
        List<BluetoothGattService> serviesArray;
        int i;
        String str;
        StringBuilder stringBuilder;
        ARNetworkALBLENetwork aRNetworkALBLENetwork = this;
        BluetoothDevice bluetoothDevice = deviceBLEService;
        int[] iArr = notificationIDArray;
        ARNETWORKAL_ERROR_ENUM result = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK;
        BluetoothGattService senderService = null;
        BluetoothGattService receiverService = null;
        if (bluetoothDevice == null) {
            result = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BAD_PARAMETER;
        }
        if (result == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK) {
            SystemClock.sleep(BASIC_TEST_SLEEP);
            resultAL = aRNetworkALBLENetwork.bleManager.connect(bluetoothDevice);
            if (resultAL == ARSAL_ERROR_ENUM.ARSAL_OK) {
                result = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK;
            } else if (resultAL == ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_STACK) {
                result = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BLE_STACK;
            } else {
                result = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BLE_CONNECTION;
            }
        }
        if (result == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK) {
            SystemClock.sleep(BASIC_TEST_SLEEP);
            result = aRNetworkALBLENetwork.bleManager.discoverBLENetworkServices() == ARSAL_ERROR_ENUM.ARSAL_OK ? ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK : ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BLE_SERVICES_DISCOVERING;
        }
        if (result == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK) {
            SystemClock.sleep(BASIC_TEST_SLEEP);
            if (aRNetworkALBLENetwork.bleManager.getGatt() != null) {
                serviesArray = aRNetworkALBLENetwork.bleManager.getServices();
                if (serviesArray != null) {
                    BluetoothGattService receiverService2 = null;
                    receiverService = null;
                    for (int index = 0; index < serviesArray.size() && (receiverService == null || receiverService2 == null); index++) {
                        BluetoothGattService gattService = (BluetoothGattService) serviesArray.get(index);
                        if (ARUUID.getShortUuid(gattService.getUuid()).startsWith(ARNETWORKAL_BLENETWORK_PARROT_SERVICE_PREFIX_UUID) && gattService.getCharacteristics().size() > 0) {
                            BluetoothGattCharacteristic gattCharacteristic = (BluetoothGattCharacteristic) gattService.getCharacteristics().get(0);
                            if (receiverService == null && (gattCharacteristic.getProperties() & 4) == 4) {
                                receiverService = gattService;
                            }
                            if (receiverService2 == null && (gattCharacteristic.getProperties() & 16) == 16) {
                                receiverService2 = gattService;
                            }
                        }
                    }
                    senderService = receiverService;
                    receiverService = receiverService2;
                } else {
                    ARSALPrint.m532e(TAG, "no service");
                }
                if (senderService == null || receiverService == null) {
                    result = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BLE_SERVICES_DISCOVERING;
                }
            } else {
                result = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BLE_NOT_CONNECTED;
            }
        }
        if (result == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK) {
            List<BluetoothGattCharacteristic> notificationCharacteristics;
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("senderService: ");
            stringBuilder2.append(senderService.getUuid());
            ARSALPrint.m530d(str2, stringBuilder2.toString());
            str2 = TAG;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("receiverService: ");
            stringBuilder2.append(receiverService.getUuid());
            ARSALPrint.m530d(str2, stringBuilder2.toString());
            aRNetworkALBLENetwork.deviceBLEService = bluetoothDevice;
            aRNetworkALBLENetwork.recvService = receiverService;
            aRNetworkALBLENetwork.sendService = senderService;
            aRNetworkALBLENetwork.bleManager.setListener(aRNetworkALBLENetwork);
            if (iArr != null) {
                notificationCharacteristics = new ArrayList();
                for (int id : iArr) {
                    if (id < receiverService.getCharacteristics().size()) {
                        notificationCharacteristics.add(receiverService.getCharacteristics().get(id));
                    } else {
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("error receiverService.getCharacteristics().size(): ");
                        stringBuilder.append(receiverService.getCharacteristics().size());
                        stringBuilder.append(" id to notify: ");
                        stringBuilder.append(id);
                        ARSALPrint.m532e(str, stringBuilder.toString());
                    }
                }
            } else {
                notificationCharacteristics = receiverService.getCharacteristics();
            }
            ARSAL_ERROR_ENUM setNotifCharacteristicResult = ARSAL_ERROR_ENUM.ARSAL_OK;
            for (BluetoothGattCharacteristic gattCharacteristic2 : notificationCharacteristics) {
                if ((gattCharacteristic2.getProperties() & 16) == 16) {
                    setNotifCharacteristicResult = aRNetworkALBLENetwork.bleManager.setCharacteristicNotification(receiverService, gattCharacteristic2);
                }
                switch (setNotifCharacteristicResult) {
                    case ARSAL_OK:
                        break;
                    case ARSAL_ERROR_BLE_CHARACTERISTIC_CONFIGURING:
                        break;
                    case ARSAL_ERROR_BLE_NOT_CONNECTED:
                        result = ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_ERROR_BLE_CONNECTION;
                        break;
                    default:
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("error ");
                        stringBuilder.append(setNotifCharacteristicResult);
                        stringBuilder.append(" unexpected :  ");
                        stringBuilder.append(setNotifCharacteristicResult);
                        ARSALPrint.m532e(str, stringBuilder.toString());
                        break;
                }
            }
            aRNetworkALBLENetwork.bleManager.registerNotificationCharacteristics(notificationCharacteristics, ARNETWORKAL_BLENETWORK_NOTIFICATIONS_KEY);
        }
        if (result == ARNETWORKAL_ERROR_ENUM.ARNETWORKAL_OK) {
            resultAL = ARSAL_ERROR_ENUM.ARSAL_OK;
            serviesArray = aRNetworkALBLENetwork.bleManager.getServices();
            ARSAL_ERROR_ENUM resultSal = resultAL;
            int index2 = 0;
            while (index2 < serviesArray.size() && resultSal == ARSAL_ERROR_ENUM.ARSAL_OK) {
                BluetoothGattService gattService2 = (BluetoothGattService) serviesArray.get(index2);
                List<BluetoothGattCharacteristic> characteristicsArray = gattService2.getCharacteristics();
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("ARNetwork service ");
                stringBuilder.append(ARUUID.getShortUuid(gattService2.getUuid()));
                ARSALPrint.m538w(str, stringBuilder.toString());
                ARSAL_ERROR_ENUM resultSal2 = resultSal;
                for (i = 0; i < characteristicsArray.size() && resultSal2 == ARSAL_ERROR_ENUM.ARSAL_OK; i++) {
                    BluetoothGattCharacteristic gattCharacteristic3 = (BluetoothGattCharacteristic) characteristicsArray.get(i);
                    String str3 = TAG;
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("ARNetwork service ");
                    stringBuilder3.append(ARUUID.getShortUuid(gattCharacteristic3.getUuid()));
                    ARSALPrint.m538w(str3, stringBuilder3.toString());
                    if (ARUUID.getShortUuid(gattCharacteristic3.getUuid()).startsWith(ARNETWORKAL_BLENETWORK_PARROT_CHARACTERISTIC_PREFIX_UUID_FTP_21) || ARUUID.getShortUuid(gattCharacteristic3.getUuid()).startsWith(ARNETWORKAL_BLENETWORK_PARROT_CHARACTERISTIC_PREFIX_UUID_FTP_51) || ARUUID.getShortUuid(gattCharacteristic3.getUuid()).startsWith(f1775x139372ce)) {
                        resultSal2 = aRNetworkALBLENetwork.bleManager.setCharacteristicNotification(gattService2, gattCharacteristic3);
                        String str4 = TAG;
                        StringBuilder stringBuilder4 = new StringBuilder();
                        stringBuilder4.append("ARNetwork ====setCharacteristicNotification ");
                        stringBuilder4.append(ARUUID.getShortUuid(gattService2.getUuid()));
                        stringBuilder4.append(FormatHelper.SPACE);
                        stringBuilder4.append(resultSal2);
                        ARSALPrint.m538w(str4, stringBuilder4.toString());
                    }
                }
                index2++;
                resultSal = resultSal2;
            }
        }
        return result.getValue();
    }

    public void cancel() {
        disconnect();
        this.bleManager.reset();
    }

    public void disconnect() {
        synchronized (this) {
            ARSALPrint.m530d(TAG, "disconnect");
            this.bleManager.disconnect();
            cleanup();
            this.bleManager.setListener(null);
        }
    }

    private void cleanup() {
        this.deviceBLEService = null;
        this.recvService = null;
        this.sendService = null;
        this.recvArray.clear();
    }

    private void unlock() {
        ARSALPrint.m530d(TAG, "unlock");
        this.bleManager.unlock();
    }

    private int receive() {
        ARNETWORKAL_MANAGER_RETURN_ENUM result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_DEFAULT;
        if (!this.bleManager.readDataNotificationData(this.recvArray, Integer.MAX_VALUE, ARNETWORKAL_BLENETWORK_NOTIFICATIONS_KEY)) {
            result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_NO_DATA_AVAILABLE;
        }
        return result.getValue();
    }

    private DataPop popFrame() {
        DataPop dataPop = new DataPop();
        ARNETWORKAL_MANAGER_RETURN_ENUM result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_DEFAULT;
        ARSALManagerNotificationData notification = null;
        if (this.recvArray.size() == 0) {
            result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_BUFFER_EMPTY;
        }
        if (result == ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_DEFAULT) {
            notification = (ARSALManagerNotificationData) this.recvArray.get(0);
            if (notification.value.length == 0) {
                result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_BAD_FRAME;
            }
        }
        if (result == ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_DEFAULT) {
            byte[] currentFrame = notification.value;
            int frameId = Integer.parseInt(ARUUID.getShortUuid(notification.characteristic.getUuid()), 16);
            if (result == ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_DEFAULT) {
                dataPop.setId(frameId);
                dataPop.setData(currentFrame);
                this.bwCurrentDown += currentFrame.length;
            }
        }
        if (result != ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_BUFFER_EMPTY) {
            this.recvArray.remove(0);
        }
        dataPop.setResult(result.getValue());
        return dataPop;
    }

    private int pushFrame(int type, int id, int seq, int size, byte[] byteData) {
        ARNETWORKAL_MANAGER_RETURN_ENUM result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_DEFAULT;
        if (result == ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_DEFAULT) {
            byte[] data = new byte[(byteData.length + ARNETWORKAL_BLENETWORK_HEADER_SIZE)];
            data[0] = (byte) type;
            data[1] = (byte) seq;
            System.arraycopy(byteData, 0, data, 2, byteData.length);
            if (this.sendService == null) {
                result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_NETWORK_ERROR;
            } else if (id < 0 || id >= this.sendService.getCharacteristics().size()) {
                result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_BAD_PARAMETERS;
            } else {
                if (this.bleManager.writeData(data, (BluetoothGattCharacteristic) this.sendService.getCharacteristics().get(id))) {
                    this.bwCurrentUp += data.length;
                } else {
                    result = ARNETWORKAL_MANAGER_RETURN_ENUM.ARNETWORKAL_MANAGER_RETURN_BAD_FRAME;
                }
            }
        }
        return result.getValue();
    }

    public void onBLEDisconnect() {
        nativeJNIOnDisconect(this.jniARNetworkALBLENetwork);
        cleanup();
    }
}
