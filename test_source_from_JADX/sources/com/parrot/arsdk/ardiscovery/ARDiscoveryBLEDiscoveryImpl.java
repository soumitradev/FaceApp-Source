package com.parrot.arsdk.ardiscovery;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ARDiscoveryBLEDiscoveryImpl implements ARDiscoveryBLEDiscovery {
    private static final int ARDISCOVERY_BT_VENDOR_ID = 67;
    private static final int ARDISCOVERY_USB_VENDOR_ID = 6607;
    private static final String TAG = ARDiscoveryBLEDiscoveryImpl.class.getSimpleName();
    private Boolean askForLeDiscovering = Boolean.valueOf(false);
    private HashMap<String, ARDiscoveryDeviceService> bleDeviceServicesHmap;
    private boolean bleIsAvailable;
    private BLEScanner bleScanner;
    private BluetoothAdapter bluetoothAdapter;
    private ARDiscoveryService broadcaster;
    private Context context;
    private Boolean isLeDiscovering = Boolean.valueOf(false);
    private Object leScanCallback;
    private Handler mHandler;
    private IntentFilter networkStateChangedFilter;
    private BroadcastReceiver networkStateIntentReceiver;
    private boolean opened;
    private final Set<ARDISCOVERY_PRODUCT_ENUM> supportedProducts;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryBLEDiscoveryImpl$1 */
    class C15731 extends BroadcastReceiver {
        C15731() {
        }

        public void onReceive(Context context, Intent intent) {
            ARSALPrint.m530d(ARDiscoveryBLEDiscoveryImpl.TAG, "BroadcastReceiver onReceive");
            if (intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                ARSALPrint.m530d(ARDiscoveryBLEDiscoveryImpl.TAG, "ACTION_STATE_CHANGED");
                if (ARDiscoveryBLEDiscoveryImpl.this.bleIsAvailable) {
                    switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE)) {
                        case 12:
                            if (ARDiscoveryBLEDiscoveryImpl.this.askForLeDiscovering.booleanValue()) {
                                ARDiscoveryBLEDiscoveryImpl.this.bleConnect();
                                ARDiscoveryBLEDiscoveryImpl.this.isLeDiscovering = Boolean.valueOf(true);
                                ARDiscoveryBLEDiscoveryImpl.this.askForLeDiscovering = Boolean.valueOf(false);
                                return;
                            }
                            return;
                        case 13:
                            ARDiscoveryBLEDiscoveryImpl.this.bleDeviceServicesHmap.clear();
                            if (ARDiscoveryBLEDiscoveryImpl.this.broadcaster != null) {
                                ARDiscoveryBLEDiscoveryImpl.this.broadcaster.broadcastDeviceServiceArrayUpdated();
                            }
                            if (ARDiscoveryBLEDiscoveryImpl.this.isLeDiscovering.booleanValue()) {
                                ARDiscoveryBLEDiscoveryImpl.this.askForLeDiscovering = Boolean.valueOf(true);
                            }
                            ARDiscoveryBLEDiscoveryImpl.this.bleDisconnect();
                            ARDiscoveryBLEDiscoveryImpl.this.bleDeviceServicesHmap.clear();
                            if (ARDiscoveryBLEDiscoveryImpl.this.broadcaster != null) {
                                ARDiscoveryBLEDiscoveryImpl.this.broadcaster.broadcastDeviceServiceArrayUpdated();
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryBLEDiscoveryImpl$2 */
    class C15742 implements LeScanCallback {
        C15742() {
        }

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            ARSALPrint.m530d(ARDiscoveryBLEDiscoveryImpl.TAG, "onLeScan");
            ARDiscoveryBLEDiscoveryImpl.this.bleScanner.bleCallback(device, rssi, scanRecord);
        }
    }

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryBLEDiscoveryImpl$3 */
    class C15753 implements Runnable {
        C15753() {
        }

        public void run() {
            ARSALPrint.m530d(ARDiscoveryBLEDiscoveryImpl.TAG, "BLE scan timeout ! clear BLE devices");
            ARDiscoveryBLEDiscoveryImpl.this.bleDeviceServicesHmap.clear();
            ARDiscoveryBLEDiscoveryImpl.this.broadcaster.broadcastDeviceServiceArrayUpdated();
        }
    }

    @TargetApi(18)
    private class BLEScanner {
        private static final int ARDISCOVERY_BLE_MANUFACTURER_DATA_ADTYPE = 255;
        private static final int ARDISCOVERY_BLE_MANUFACTURER_DATA_ADTYPE_OFFSET = 4;
        private static final int ARDISCOVERY_BLE_MANUFACTURER_DATA_LENGTH_OFFSET = 3;
        private static final int ARDISCOVERY_BLE_MANUFACTURER_DATA_LENGTH_WITH_ADTYPE = 9;
        private static final long ARDISCOVERY_BLE_SCAN_DURATION = 4000;
        private static final long ARDISCOVERY_BLE_SCAN_PERIOD = 10000;
        public static final long ARDISCOVERY_BLE_TIMEOUT_DURATION = 20000;
        private boolean isStart;
        private HashMap<String, ARDiscoveryDeviceService> newBLEDeviceServicesHmap;
        private boolean scanning;
        private Handler startBLEHandler = new Handler();
        private Runnable startScanningRunnable;
        private Handler stopBLEHandler = new Handler();
        private Runnable stopScanningRunnable;

        public BLEScanner() {
            ARSALPrint.m530d(ARDiscoveryBLEDiscoveryImpl.TAG, "BLEScanningTask constructor");
            this.startScanningRunnable = new Runnable(ARDiscoveryBLEDiscoveryImpl.this) {
                public void run() {
                    BLEScanner.this.startScanLeDevice();
                }
            };
            this.stopScanningRunnable = new Runnable(ARDiscoveryBLEDiscoveryImpl.this) {
                public void run() {
                    BLEScanner.this.periodScanLeDeviceEnd();
                }
            };
        }

        public void start() {
            if (!this.isStart) {
                this.isStart = true;
                this.startScanningRunnable.run();
            }
        }

        private void startScanLeDevice() {
            this.newBLEDeviceServicesHmap = new HashMap();
            this.stopBLEHandler.postDelayed(this.stopScanningRunnable, ARDISCOVERY_BLE_SCAN_DURATION);
            this.scanning = true;
            ARDiscoveryBLEDiscoveryImpl.this.bluetoothAdapter.startLeScan((LeScanCallback) ARDiscoveryBLEDiscoveryImpl.this.leScanCallback);
            this.startBLEHandler.postDelayed(this.startScanningRunnable, ARDISCOVERY_BLE_SCAN_PERIOD);
        }

        public void bleCallback(BluetoothDevice bleService, int rssi, byte[] scanRecord) {
            String access$000 = ARDiscoveryBLEDiscoveryImpl.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bleCallback : found BluetoothDevice : ");
            stringBuilder.append(bleService);
            stringBuilder.append(" (");
            stringBuilder.append(bleService.getName());
            stringBuilder.append(")");
            ARSALPrint.m536v(access$000, stringBuilder.toString());
            int productID = getParrotProductID(scanRecord);
            if (productID != 0) {
                ARDiscoveryDeviceBLEService deviceBLEService = new ARDiscoveryDeviceBLEService(bleService);
                deviceBLEService.setSignal(rssi);
                deviceBLEService.setConnectionState(getBLEProductConnectionState(scanRecord));
                ARDiscoveryDeviceService deviceService = new ARDiscoveryDeviceService(bleService.getName(), deviceBLEService, productID);
                this.newBLEDeviceServicesHmap.put(deviceService.getName(), deviceService);
            }
        }

        private int getParrotProductID(byte[] scanRecord) {
            int manufacturerDataLenght = Arrays.copyOfRange(scanRecord, 3, 4)[0] & 255;
            if (manufacturerDataLenght != 9) {
                return 0;
            }
            byte[] data = Arrays.copyOfRange(scanRecord, 4, manufacturerDataLenght + 4);
            if ((data[0] & 255) != 255) {
                return 0;
            }
            int usbVendorID = (data[3] & 255) + ((data[4] & 255) << 8);
            int usbProductID = (data[5] & 255) + ((255 & data[6]) << 8);
            if ((data[1] & 255) + ((data[2] & 255) << 8) == 67 && usbVendorID == ARDiscoveryBLEDiscoveryImpl.ARDISCOVERY_USB_VENDOR_ID && ARDiscoveryBLEDiscoveryImpl.this.supportedProducts.contains(ARDiscoveryService.getProductFromProductID(usbProductID))) {
                return usbProductID;
            }
            return 0;
        }

        private ARDISCOVERY_CONNECTION_STATE_ENUM getBLEProductConnectionState(byte[] scanRecord) {
            ARDISCOVERY_CONNECTION_STATE_ENUM connectionState = ARDISCOVERY_CONNECTION_STATE_ENUM.eARDISCOVERY_CONNECTION_STATE_UNKNOWN_ENUM_VALUE;
            int manufacturerDataLenght = Arrays.copyOfRange(scanRecord, 3, 4)[0] & 255;
            if (manufacturerDataLenght != 9) {
                return connectionState;
            }
            byte[] data = Arrays.copyOfRange(scanRecord, 4, manufacturerDataLenght + 4);
            if ((data[0] & 255) != 255) {
                return connectionState;
            }
            connectionState = ARDISCOVERY_CONNECTION_STATE_ENUM.getFromValue((data[7] & 255) + ((255 & data[8]) << 8));
            if (connectionState.equals(ARDISCOVERY_CONNECTION_STATE_ENUM.eARDISCOVERY_CONNECTION_STATE_UNKNOWN_ENUM_VALUE)) {
                return ARDISCOVERY_CONNECTION_STATE_ENUM.ARDISCOVERY_CONNECTION_STATE_UNKNOWN;
            }
            return connectionState;
        }

        private void periodScanLeDeviceEnd() {
            ARSALPrint.m530d(ARDiscoveryBLEDiscoveryImpl.TAG, "periodScanLeDeviceEnd");
            ARDiscoveryBLEDiscoveryImpl.this.notificationBLEServiceDeviceUpDate(this.newBLEDeviceServicesHmap);
            stopScanLeDevice();
        }

        private void stopScanLeDevice() {
            ARSALPrint.m530d(ARDiscoveryBLEDiscoveryImpl.TAG, "ScanLeDeviceAsyncTask stopLeScan");
            this.scanning = false;
            ARDiscoveryBLEDiscoveryImpl.this.bluetoothAdapter.stopLeScan((LeScanCallback) ARDiscoveryBLEDiscoveryImpl.this.leScanCallback);
        }

        public void stop() {
            ARSALPrint.m538w(ARDiscoveryBLEDiscoveryImpl.TAG, "BLEScanningTask stop");
            if (ARDiscoveryBLEDiscoveryImpl.this.leScanCallback != null) {
                try {
                    ARDiscoveryBLEDiscoveryImpl.this.bluetoothAdapter.stopLeScan((LeScanCallback) ARDiscoveryBLEDiscoveryImpl.this.leScanCallback);
                } catch (NullPointerException e) {
                    ARSALPrint.m532e(ARDiscoveryBLEDiscoveryImpl.TAG, "Cannot stop scan.  Unexpected NPE.");
                    e.printStackTrace();
                }
            }
            this.startBLEHandler.removeCallbacks(this.startScanningRunnable);
            this.stopBLEHandler.removeCallbacks(this.stopScanningRunnable);
            this.scanning = false;
            this.isStart = false;
        }

        public Boolean IsScanning() {
            return Boolean.valueOf(this.scanning);
        }

        public Boolean IsStart() {
            return Boolean.valueOf(this.isStart);
        }
    }

    public ARDiscoveryBLEDiscoveryImpl(Set<ARDISCOVERY_PRODUCT_ENUM> supportedProducts) {
        ARSALPrint.m538w(TAG, "ARDiscoveryBLEDiscoveryImpl new !!!!");
        this.supportedProducts = supportedProducts;
        this.opened = false;
        this.bleDeviceServicesHmap = new HashMap();
        this.networkStateChangedFilter = new IntentFilter();
        this.networkStateChangedFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.networkStateIntentReceiver = new C15731();
    }

    public synchronized void open(ARDiscoveryService broadcaster, Context c) {
        ARSALPrint.m530d(TAG, "Open BLE");
        this.broadcaster = broadcaster;
        this.context = c;
        if (!this.opened) {
            this.mHandler = new Handler();
            this.bleDeviceServicesHmap = new HashMap();
            this.bleIsAvailable = false;
            getBLEAvailability();
            if (this.bleIsAvailable) {
                initBLE();
            }
            this.context.registerReceiver(this.networkStateIntentReceiver, this.networkStateChangedFilter);
            this.opened = true;
        }
    }

    public synchronized void close() {
        ARSALPrint.m530d(TAG, "Close BLE");
        if (this.opened) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.context.unregisterReceiver(this.networkStateIntentReceiver);
            if (this.bleIsAvailable) {
                bleDisconnect();
            }
            this.context = null;
            this.broadcaster = null;
            this.opened = false;
        }
    }

    private void update() {
        if (this.bleIsAvailable && this.bluetoothAdapter.isEnabled()) {
            bleConnect();
        } else {
            bleDisconnect();
        }
    }

    public void start() {
        if (!this.isLeDiscovering.booleanValue()) {
            if (this.bleIsAvailable && this.bluetoothAdapter.isEnabled()) {
                bleConnect();
                this.isLeDiscovering = Boolean.valueOf(true);
                return;
            }
            this.askForLeDiscovering = Boolean.valueOf(true);
        }
    }

    public void stop() {
        if (this.isLeDiscovering.booleanValue()) {
            bleDisconnect();
            this.isLeDiscovering = Boolean.valueOf(false);
        }
        this.askForLeDiscovering = Boolean.valueOf(false);
    }

    private void getBLEAvailability() {
        if (this.context.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            this.bluetoothAdapter = ((BluetoothManager) this.context.getSystemService("bluetooth")).getAdapter();
            if (this.bluetoothAdapter == null) {
                ARSALPrint.m530d(TAG, "BLE Is NOT Available");
                this.bleIsAvailable = false;
                return;
            }
            ARSALPrint.m530d(TAG, "BLE Is Available");
            this.bleIsAvailable = true;
            return;
        }
        ARSALPrint.m530d(TAG, "BLE Is NOT Available");
        this.bleIsAvailable = false;
    }

    @TargetApi(18)
    private void initBLE() {
        this.bleScanner = new BLEScanner();
        this.leScanCallback = new C15742();
    }

    private void bleConnect() {
        if (this.bleIsAvailable) {
            this.bleScanner.start();
        }
    }

    private void bleDisconnect() {
        if (this.bleIsAvailable) {
            this.bleScanner.stop();
        }
    }

    @TargetApi(18)
    private void notificationBLEServiceDeviceUpDate(HashMap<String, ARDiscoveryDeviceService> newBLEDeviceServicesHmap) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("notificationBLEServiceDeviceUpDate : ");
        stringBuilder.append(newBLEDeviceServicesHmap);
        ARSALPrint.m530d(str, stringBuilder.toString());
        this.mHandler.removeCallbacksAndMessages(null);
        if (bleServicesListHasChanged(newBLEDeviceServicesHmap)) {
            this.bleDeviceServicesHmap = newBLEDeviceServicesHmap;
            this.broadcaster.broadcastDeviceServiceArrayUpdated();
        }
        this.mHandler.postDelayed(new C15753(), BLEScanner.ARDISCOVERY_BLE_TIMEOUT_DURATION);
    }

    private boolean bleServicesListHasChanged(HashMap<String, ARDiscoveryDeviceService> newBLEDeviceServicesHmap) {
        ARSALPrint.m530d(TAG, "bleServicesListHasChanged");
        boolean res = false;
        if (this.bleDeviceServicesHmap.size() != newBLEDeviceServicesHmap.size()) {
            return true;
        }
        if (!this.bleDeviceServicesHmap.keySet().equals(newBLEDeviceServicesHmap.keySet())) {
            return true;
        }
        for (ARDiscoveryDeviceService bleDevice : this.bleDeviceServicesHmap.values()) {
            if (!newBLEDeviceServicesHmap.containsValue(bleDevice)) {
                res = true;
            }
        }
        return res;
    }

    public List<ARDiscoveryDeviceService> getDeviceServicesArray() {
        return new ArrayList(this.bleDeviceServicesHmap.values());
    }
}
