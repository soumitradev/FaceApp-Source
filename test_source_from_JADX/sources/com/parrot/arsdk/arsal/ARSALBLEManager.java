package com.parrot.arsdk.arsal;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import name.antonsmirnov.firmata.FormatHelper;

@TargetApi(18)
public class ARSALBLEManager {
    /* renamed from: ARSALBLEMANAGER_CHARACTERISTIC_UPDATE_NOTIFICATION_DESCRIPTOR_UUID */
    private static final UUID f1723x25a302e9 = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private static final int ARSALBLEMANAGER_CONNECTION_TIMEOUT_SEC = 30;
    private static final int GATT_CONN_FAIL_ESTABLISH = 62;
    private static final int GATT_INTERNAL_ERROR = 133;
    private static final int GATT_INTERRUPT_ERROR = 8;
    private static String TAG = "ARSALBLEManager";
    private BluetoothGatt activeGatt;
    private boolean askDisconnection;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private ARSAL_ERROR_ENUM configurationCharacteristicError;
    private Semaphore configurationSem;
    private ARSAL_ERROR_ENUM connectionError;
    private BluetoothGatt connectionGatt;
    private Semaphore connectionSem;
    private Context context;
    private BluetoothDevice deviceBLEService;
    private Semaphore disconnectionSem;
    private ARSAL_ERROR_ENUM discoverServicesError;
    private Semaphore discoverServicesSem;
    private final BluetoothGattCallback gattCallback;
    private boolean isConfiguringCharacteristics;
    private boolean isDeviceConnected;
    private boolean isDiscoveringServices;
    private ARSALBLEManagerListener listener;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics;
    private Handler mUIHandler;
    private Semaphore mWriteDataSemaphore;
    private HashMap<String, ARSALManagerNotification> registeredNotificationCharacteristics;
    private ARSAL_ERROR_ENUM writeCharacteristicError;

    /* renamed from: com.parrot.arsdk.arsal.ARSALBLEManager$1 */
    class C16031 implements Runnable {
        C16031() {
        }

        public void run() {
            ARSALBLEManager.this.connectionGatt = ARSALBLEManager.this.deviceBLEService.connectGatt(ARSALBLEManager.this.context, false, ARSALBLEManager.this.gattCallback);
            if (ARSALBLEManager.this.connectionGatt == null) {
                ARSALPrint.m532e(ARSALBLEManager.TAG, "connect (connectionGatt == null)");
                ARSALBLEManager.this.connectionError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_NOT_CONNECTED;
            }
        }
    }

    /* renamed from: com.parrot.arsdk.arsal.ARSALBLEManager$2 */
    class C16042 implements Runnable {
        C16042() {
        }

        public void run() {
            if (ARSALBLEManager.this.activeGatt != null) {
                ARSALBLEManager.this.connectionGatt = null;
            } else if (ARSALBLEManager.this.connectionGatt != null) {
                ARSALBLEManager.this.disconnectGatt();
                ARSALBLEManager.this.connectionGatt.close();
                ARSALBLEManager.this.connectionGatt = null;
            }
        }
    }

    /* renamed from: com.parrot.arsdk.arsal.ARSALBLEManager$3 */
    class C16053 implements Runnable {
        C16053() {
        }

        public void run() {
            boolean manualDisconnectGatt = true;
            if (ARSALBLEManager.this.bluetoothManager != null) {
                BluetoothGatt gatt = ARSALBLEManager.this.activeGatt;
                if (gatt != null && ARSALBLEManager.this.bluetoothManager.getConnectionState(gatt.getDevice(), 7) == 2) {
                    manualDisconnectGatt = false;
                    gatt.disconnect();
                }
            }
            if (manualDisconnectGatt) {
                String access$600 = ARSALBLEManager.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("disconnect gatt manually ");
                stringBuilder.append(ARSALBLEManager.this.bluetoothManager);
                stringBuilder.append(FormatHelper.SPACE);
                stringBuilder.append(ARSALBLEManager.this.activeGatt);
                ARSALPrint.m530d(access$600, stringBuilder.toString());
                ARSALBLEManager.this.disconnectionSem.release();
            }
        }
    }

    /* renamed from: com.parrot.arsdk.arsal.ARSALBLEManager$4 */
    class C16064 implements Runnable {
        C16064() {
        }

        public void run() {
            boolean discoveryRes = false;
            if (ARSALBLEManager.this.activeGatt != null) {
                discoveryRes = ARSALBLEManager.this.activeGatt.discoverServices();
            }
            if (!discoveryRes) {
                ARSALBLEManager.this.discoverServicesError = ARSAL_ERROR_ENUM.ARSAL_ERROR;
                ARSALBLEManager.this.discoverServicesSem.release();
            }
        }
    }

    /* renamed from: com.parrot.arsdk.arsal.ARSALBLEManager$6 */
    class C16096 extends BluetoothGattCallback {
        C16096() {
        }

        public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
            String access$600 = ARSALBLEManager.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConnectionStateChange : status = ");
            stringBuilder.append(status);
            stringBuilder.append(" newState = ");
            stringBuilder.append(newState);
            ARSALPrint.m538w(access$600, stringBuilder.toString());
            if (newState == 0) {
                if (ARSALBLEManager.this.activeGatt == null || gatt != ARSALBLEManager.this.activeGatt) {
                    ARSALPrint.m538w(ARSALBLEManager.TAG, "Disconnection of another gatt");
                    gatt.close();
                } else {
                    ARSALBLEManager.this.isDeviceConnected = false;
                    ARSALBLEManager.this.onDisconectGatt();
                }
            }
            if (status != 0) {
                if (status == 8) {
                    access$600 = ARSALBLEManager.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("On connection state change: GATT_INTERRUPT_ERROR (8 status) newState:");
                    stringBuilder.append(newState);
                    ARSALPrint.m532e(access$600, stringBuilder.toString());
                    ARSALBLEManager.this.connectionError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_CONNECTION;
                    ARSALBLEManager.this.connectionSem.release();
                } else if (status == 62) {
                    access$600 = ARSALBLEManager.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("On connection state change: GATT_CONN_FAIL_ESTABLISH (62 status) newState:");
                    stringBuilder.append(newState);
                    ARSALPrint.m532e(access$600, stringBuilder.toString());
                    ARSALBLEManager.this.connectionError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_CONNECTION;
                    ARSALBLEManager.this.connectionSem.release();
                } else if (status == 133) {
                    access$600 = ARSALBLEManager.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("On connection state change: GATT_INTERNAL_ERROR (133 status) newState:");
                    stringBuilder.append(newState);
                    ARSALPrint.m532e(access$600, stringBuilder.toString());
                    ARSALBLEManager.this.connectionError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_CONNECTION;
                    ARSALBLEManager.this.connectionSem.release();
                } else if (status != 257) {
                    access$600 = ARSALBLEManager.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("unknown status : ");
                    stringBuilder.append(status);
                    ARSALPrint.m532e(access$600, stringBuilder.toString());
                } else {
                    access$600 = ARSALBLEManager.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("On connection state change: GATT_FAILURE newState:");
                    stringBuilder.append(newState);
                    ARSALPrint.m538w(access$600, stringBuilder.toString());
                    ARSALBLEManager.this.connectionSem.release();
                }
            } else if (newState == 2) {
                ARSALBLEManager.this.mUIHandler.post(new Runnable() {
                    public void run() {
                        ARSALBLEManager.this.activeGatt = gatt;
                        ARSALBLEManager.this.isDeviceConnected = true;
                        ARSALBLEManager.this.connectionError = ARSAL_ERROR_ENUM.ARSAL_OK;
                        ARSALBLEManager.this.connectionSem.release();
                        ARSALBLEManager.this.mWriteDataSemaphore.release();
                    }
                });
            } else {
                access$600 = ARSALBLEManager.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("On connection state change: GATT_SUCCESS but newState:");
                stringBuilder.append(newState);
                ARSALPrint.m532e(access$600, stringBuilder.toString());
                ARSALBLEManager.this.connectionError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_NOT_CONNECTED;
                ARSALBLEManager.this.connectionSem.release();
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (!(status == 0 && ARSALBLEManager.this.isGattConnected(gatt))) {
                String access$600 = ARSALBLEManager.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("On services discovered state:");
                stringBuilder.append(status);
                ARSALPrint.m538w(access$600, stringBuilder.toString());
                ARSALBLEManager.this.discoverServicesError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_SERVICES_DISCOVERING;
            }
            ARSALBLEManager.this.discoverServicesSem.release();
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            if (!(status == 0 && ARSALBLEManager.this.isGattConnected(gatt))) {
                ARSALBLEManager.this.configurationCharacteristicError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_CHARACTERISTIC_CONFIGURING;
            }
            ARSALPrint.m530d(ARSALBLEManager.TAG, "Releasing configurationSem");
            ARSALBLEManager.this.configurationSem.release();
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            ARSALManagerNotification foundNotification = null;
            for (String key : ARSALBLEManager.this.registeredNotificationCharacteristics.keySet()) {
                ARSALManagerNotification notification = (ARSALManagerNotification) ARSALBLEManager.this.registeredNotificationCharacteristics.get(key);
                for (BluetoothGattCharacteristic characteristicItem : notification.characteristics) {
                    if (characteristicItem.getUuid().toString().contentEquals(characteristic.getUuid().toString())) {
                        foundNotification = notification;
                        break;
                    }
                }
                if (foundNotification != null) {
                    break;
                }
            }
            if (foundNotification != null) {
                byte[] newValue;
                byte[] value = characteristic.getValue();
                if (value != null) {
                    newValue = new byte[value.length];
                    System.arraycopy(value, 0, newValue, 0, value.length);
                } else {
                    newValue = new byte[0];
                }
                foundNotification.addNotification(new ARSALManagerNotificationData(characteristic, newValue));
                foundNotification.signalNotification();
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            ARSALBLEManager.this.mWriteDataSemaphore.release();
        }
    }

    /* renamed from: com.parrot.arsdk.arsal.ARSALBLEManager$7 */
    class C16107 implements Runnable {
        C16107() {
        }

        public void run() {
            if (ARSALBLEManager.this.activeGatt != null) {
                ARSALBLEManager.this.activeGatt.close();
                ARSALBLEManager.this.activeGatt = null;
            }
        }
    }

    private static class ARSALBLEManagerHolder {
        private static final ARSALBLEManager instance = new ARSALBLEManager();

        private ARSALBLEManagerHolder() {
        }
    }

    public class ARSALManagerNotification {
        List<BluetoothGattCharacteristic> characteristics = null;
        ArrayList<ARSALManagerNotificationData> notificationsArray = new ArrayList();
        private Lock readCharacteristicMutex = new ReentrantLock();
        private Semaphore readCharacteristicSem = new Semaphore(0);

        public ARSALManagerNotification(List<BluetoothGattCharacteristic> characteristicsArray) {
            this.characteristics = characteristicsArray;
        }

        void addNotification(ARSALManagerNotificationData notificationData) {
            this.readCharacteristicMutex.lock();
            this.notificationsArray.add(notificationData);
            this.readCharacteristicMutex.unlock();
            synchronized (this.readCharacteristicSem) {
                this.readCharacteristicSem.notify();
            }
        }

        int getAllNotification(List<ARSALManagerNotificationData> getNoticationsArray, int maxCount) {
            ArrayList<ARSALManagerNotificationData> removeNotifications = new ArrayList();
            this.readCharacteristicMutex.lock();
            int i = 0;
            while (i < maxCount && i < this.notificationsArray.size()) {
                ARSALManagerNotificationData notificationData = (ARSALManagerNotificationData) this.notificationsArray.get(i);
                getNoticationsArray.add(notificationData);
                removeNotifications.add(notificationData);
                i++;
            }
            for (int i2 = 0; i2 < removeNotifications.size(); i2++) {
                this.notificationsArray.remove(removeNotifications.get(i2));
            }
            this.readCharacteristicMutex.unlock();
            return getNoticationsArray.size();
        }

        boolean waitNotification(long timeout) {
            if (timeout == 0) {
                try {
                    this.readCharacteristicSem.acquire();
                } catch (InterruptedException e) {
                    return false;
                }
            }
            this.readCharacteristicSem.tryAcquire(timeout, TimeUnit.MILLISECONDS);
            return true;
        }

        boolean waitNotification() {
            return waitNotification(0);
        }

        void signalNotification() {
            this.readCharacteristicSem.release();
        }
    }

    public class ARSALManagerNotificationData {
        public BluetoothGattCharacteristic characteristic = null;
        public byte[] value = null;

        public ARSALManagerNotificationData(BluetoothGattCharacteristic _characteristic, byte[] _value) {
            this.characteristic = _characteristic;
            this.value = _value;
        }
    }

    private static class RequestLock {
        boolean finished;

        private RequestLock() {
        }
    }

    public static ARSALBLEManager getInstance(Context context) {
        ARSALBLEManager manager = ARSALBLEManagerHolder.instance;
        manager.setContext(context);
        return manager;
    }

    private synchronized void setContext(Context context) {
        if (this.context == null) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null");
            }
            this.context = context;
        }
        initialize();
    }

    public boolean initialize() {
        boolean result = true;
        if (this.bluetoothManager == null) {
            this.bluetoothManager = (BluetoothManager) this.context.getSystemService("bluetooth");
            if (this.bluetoothManager == null) {
                ARSALPrint.m532e(TAG, "Unable to initialize BluetoothManager.");
                ARSALPrint.m532e(TAG, "initialize: Unable to initialize BluetoothManager.");
                result = false;
            }
        }
        this.bluetoothAdapter = this.bluetoothManager.getAdapter();
        if (this.bluetoothAdapter != null) {
            return result;
        }
        ARSALPrint.m532e(TAG, "Unable to obtain a BluetoothAdapter.");
        ARSALPrint.m532e(TAG, "initialize: Unable to obtain a BluetoothAdapter.");
        return false;
    }

    private ARSALBLEManager() {
        this.registeredNotificationCharacteristics = new HashMap();
        this.isDeviceConnected = false;
        this.gattCallback = new C16096();
        this.context = null;
        this.deviceBLEService = null;
        this.activeGatt = null;
        this.mUIHandler = new Handler(Looper.getMainLooper());
        this.listener = null;
        this.connectionSem = new Semaphore(0);
        this.disconnectionSem = new Semaphore(0);
        this.discoverServicesSem = new Semaphore(0);
        this.configurationSem = new Semaphore(0);
        this.mWriteDataSemaphore = new Semaphore(0);
        this.askDisconnection = false;
        this.isDiscoveringServices = false;
        this.isConfiguringCharacteristics = false;
        this.connectionError = ARSAL_ERROR_ENUM.ARSAL_OK;
        this.discoverServicesError = ARSAL_ERROR_ENUM.ARSAL_OK;
        this.configurationCharacteristicError = ARSAL_ERROR_ENUM.ARSAL_OK;
    }

    public void finalize() throws Throwable {
        try {
            disconnect();
        } finally {
            super.finalize();
        }
    }

    public boolean isDeviceConnected() {
        boolean ret = false;
        synchronized (this) {
            if (this.activeGatt != null && this.isDeviceConnected) {
                ret = true;
            }
        }
        return ret;
    }

    @TargetApi(18)
    public ARSAL_ERROR_ENUM connect(BluetoothDevice deviceBLEService) {
        String str;
        StringBuilder stringBuilder;
        String str2 = TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("connecting to ");
        stringBuilder2.append(deviceBLEService);
        ARSALPrint.m532e(str2, stringBuilder2.toString());
        ARSAL_ERROR_ENUM result = ARSAL_ERROR_ENUM.ARSAL_OK;
        synchronized (this) {
            if (this.activeGatt != null) {
                disconnect();
            }
            ARSALPrint.m530d(TAG, "resetting connection objects");
            reset();
            this.connectionError = ARSAL_ERROR_ENUM.ARSAL_OK;
            ARSALPrint.m532e(TAG, "connection to the new activeGatt");
            this.deviceBLEService = this.bluetoothAdapter.getRemoteDevice(deviceBLEService.getAddress());
            this.mUIHandler.post(new C16031());
            try {
                String str3;
                StringBuilder stringBuilder3;
                ARSALPrint.m530d(TAG, "try acquiring connection semaphore ");
                if (this.connectionSem.tryAcquire(30, TimeUnit.SECONDS)) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    result = this.connectionError;
                    str3 = TAG;
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("aquired: ");
                    stringBuilder3.append(result);
                    ARSALPrint.m530d(str3, stringBuilder3.toString());
                } else {
                    ARSALPrint.m538w(TAG, "failed acquiring connection semaphore");
                    result = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_NOT_CONNECTED;
                }
                str3 = TAG;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("result : ");
                stringBuilder3.append(result);
                ARSALPrint.m530d(str3, stringBuilder3.toString());
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("activeGatt : ");
            stringBuilder.append(this.activeGatt);
            stringBuilder.append(", result: ");
            stringBuilder.append(result);
            ARSALPrint.m530d(str, stringBuilder.toString());
            this.mUIHandler.post(new C16042());
        }
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("connect ends with result: ");
        stringBuilder.append(result);
        ARSALPrint.m530d(str, stringBuilder.toString());
        return result;
    }

    private void disconnectGatt() {
        this.mUIHandler.post(new C16053());
    }

    public void disconnect() {
        if (this.activeGatt != null) {
            this.askDisconnection = true;
            disconnectGatt();
            ARSALPrint.m530d(TAG, "wait the disconnect semaphore");
            try {
                if (!this.disconnectionSem.tryAcquire(30, TimeUnit.SECONDS)) {
                    ARSALPrint.m530d(TAG, "disconnect semaphore not acquired. Manually disconnect Gatt");
                    onDisconectGatt();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.askDisconnection = false;
        }
    }

    public ARSAL_ERROR_ENUM discoverBLENetworkServices() {
        ARSAL_ERROR_ENUM result = ARSAL_ERROR_ENUM.ARSAL_OK;
        synchronized (this) {
            if (this.activeGatt == null || !this.isDeviceConnected) {
                result = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_NOT_CONNECTED;
            } else {
                this.isDiscoveringServices = true;
                this.discoverServicesError = ARSAL_ERROR_ENUM.ARSAL_OK;
                this.mUIHandler.post(new C16064());
                try {
                    this.discoverServicesSem.acquire();
                    result = this.discoverServicesError;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    result = ARSAL_ERROR_ENUM.ARSAL_ERROR;
                }
                this.isDiscoveringServices = false;
            }
        }
        return result;
    }

    public BluetoothGatt getGatt() {
        return this.activeGatt;
    }

    public void setListener(ARSALBLEManagerListener listener) {
        this.listener = listener;
    }

    public List<BluetoothGattService> getServices() {
        final List<BluetoothGattService> serviceList = new ArrayList();
        final RequestLock lock = new RequestLock();
        this.mUIHandler.post(new Runnable() {
            public void run() {
                ARSALPrint.m530d(ARSALBLEManager.TAG, "running getServices request");
                if (ARSALBLEManager.this.activeGatt != null) {
                    serviceList.addAll(ARSALBLEManager.this.activeGatt.getServices());
                }
                synchronized (lock) {
                    lock.finished = true;
                    lock.notifyAll();
                }
            }
        });
        synchronized (lock) {
            ARSALPrint.m530d(TAG, "wait the getServices request lock");
            while (!lock.finished) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ARSALPrint.m530d(TAG, "getServices request finished");
        }
        return serviceList;
    }

    public ARSAL_ERROR_ENUM setCharacteristicNotification(BluetoothGattService service, BluetoothGattCharacteristic characteristic) {
        ARSAL_ERROR_ENUM result = ARSAL_ERROR_ENUM.ARSAL_OK;
        synchronized (this) {
            BluetoothGatt localActiveGatt = this.activeGatt;
            if (isGattConnected(localActiveGatt)) {
                this.isConfiguringCharacteristics = true;
                this.configurationCharacteristicError = ARSAL_ERROR_ENUM.ARSAL_OK;
                boolean notifSet = localActiveGatt.setCharacteristicNotification(characteristic, true);
                BluetoothGattDescriptor descriptor = characteristic.getDescriptor(f1723x25a302e9);
                if (descriptor != null) {
                    boolean valueSet = descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    boolean descriptorWriten = localActiveGatt.writeDescriptor(descriptor);
                    try {
                        ARSALPrint.m530d(TAG, "acquiring configurationSem");
                        this.configurationSem.acquire();
                        ARSALPrint.m530d(TAG, "configurationSem acquired");
                        result = this.configurationCharacteristicError;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        result = ARSAL_ERROR_ENUM.ARSAL_ERROR;
                    }
                } else {
                    String str = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("setCharacteristicNotification ");
                    stringBuilder.append(characteristic.getUuid());
                    stringBuilder.append(" - BluetoothGattDescriptor ");
                    stringBuilder.append(f1723x25a302e9);
                    stringBuilder.append(" is null.");
                    ARSALPrint.m538w(str, stringBuilder.toString());
                    result = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_CHARACTERISTIC_CONFIGURING;
                }
                this.isConfiguringCharacteristics = false;
            } else {
                String str2 = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("setCharacteristicNotification ");
                stringBuilder2.append(characteristic.getUuid());
                stringBuilder2.append(" - not connected (gatt = ");
                stringBuilder2.append(localActiveGatt);
                stringBuilder2.append(")");
                ARSALPrint.m538w(str2, stringBuilder2.toString());
                result = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_NOT_CONNECTED;
            }
        }
        return result;
    }

    public boolean writeData(byte[] data, BluetoothGattCharacteristic characteristic) {
        boolean result = false;
        BluetoothGatt localActiveGatt = this.activeGatt;
        if (!(localActiveGatt == null || characteristic == null || data == null || !isGattConnected(localActiveGatt))) {
            try {
                this.mWriteDataSemaphore.acquire();
                characteristic.setValue(data);
                characteristic.setWriteType(1);
                result = localActiveGatt.writeCharacteristic(characteristic);
                if (!result) {
                    this.mWriteDataSemaphore.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void registerNotificationCharacteristics(List<BluetoothGattCharacteristic> characteristicsArray, String readCharacteristicKey) {
        this.registeredNotificationCharacteristics.put(readCharacteristicKey, new ARSALManagerNotification(characteristicsArray));
    }

    public boolean unregisterNotificationCharacteristics(String readCharacteristicKey) {
        ARSALManagerNotification notification = (ARSALManagerNotification) this.registeredNotificationCharacteristics.get(readCharacteristicKey);
        if (notification == null) {
            return false;
        }
        this.registeredNotificationCharacteristics.remove(notification);
        return true;
    }

    public boolean cancelReadNotification(String readCharacteristicKey) {
        ARSALManagerNotification notification = (ARSALManagerNotification) this.registeredNotificationCharacteristics.get(readCharacteristicKey);
        if (notification == null) {
            return false;
        }
        notification.signalNotification();
        return true;
    }

    public boolean readData(BluetoothGattCharacteristic characteristic) {
        return this.activeGatt.readCharacteristic(characteristic);
    }

    public boolean readDataNotificationData(List<ARSALManagerNotificationData> notificationsArray, int maxCount, String readCharacteristicKey, long timeout) {
        ARSALManagerNotification notification = (ARSALManagerNotification) this.registeredNotificationCharacteristics.get(readCharacteristicKey);
        if (notification == null) {
            return false;
        }
        notification.waitNotification(timeout);
        if (notification.notificationsArray.size() <= 0) {
            return false;
        }
        notification.getAllNotification(notificationsArray, maxCount);
        return true;
    }

    public boolean readDataNotificationData(List<ARSALManagerNotificationData> notificationsArray, int maxCount, String readCharacteristicKey) {
        return readDataNotificationData(notificationsArray, maxCount, readCharacteristicKey, 0);
    }

    public void unlock() {
        this.connectionSem.release();
        this.configurationSem.release();
        this.discoverServicesSem.release();
        this.mWriteDataSemaphore.release();
        for (String key : this.registeredNotificationCharacteristics.keySet()) {
            ((ARSALManagerNotification) this.registeredNotificationCharacteristics.get(key)).signalNotification();
        }
    }

    public void reset() {
        synchronized (this) {
            while (this.connectionSem.tryAcquire()) {
            }
            while (this.disconnectionSem.tryAcquire()) {
            }
            while (this.discoverServicesSem.tryAcquire()) {
            }
            while (this.configurationSem.tryAcquire()) {
            }
            while (this.mWriteDataSemaphore.tryAcquire()) {
            }
            for (String key : this.registeredNotificationCharacteristics.keySet()) {
                ((ARSALManagerNotification) this.registeredNotificationCharacteristics.get(key)).signalNotification();
            }
            this.registeredNotificationCharacteristics.clear();
            closeGatt();
        }
    }

    private void onDisconectGatt() {
        ARSALPrint.m530d(TAG, "activeGatt disconnected");
        if (this.askDisconnection) {
            this.disconnectionSem.release();
        }
        if (this.isDiscoveringServices) {
            this.discoverServicesError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_NOT_CONNECTED;
        }
        this.discoverServicesSem.release();
        if (this.isConfiguringCharacteristics) {
            this.configurationCharacteristicError = ARSAL_ERROR_ENUM.ARSAL_ERROR_BLE_NOT_CONNECTED;
        }
        this.configurationSem.release();
        if (!this.askDisconnection && this.listener != null) {
            this.listener.onBLEDisconnect();
        }
    }

    private void closeGatt() {
        this.mUIHandler.post(new C16107());
    }

    private boolean isGattConnected(BluetoothGatt gatt) {
        return (gatt == null || this.bluetoothManager == null || this.bluetoothManager.getConnectionState(gatt.getDevice(), 7) != 2) ? false : true;
    }
}
