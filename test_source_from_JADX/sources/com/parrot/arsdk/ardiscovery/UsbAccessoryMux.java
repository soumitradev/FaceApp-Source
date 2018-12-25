package com.parrot.arsdk.ardiscovery;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.parrot.arsdk.ardiscovery.ARDiscoveryMux.ConnectCallback;
import com.parrot.arsdk.ardiscovery.ARDiscoveryMux.Listener;
import com.parrot.mux.Mux;
import com.parrot.mux.Mux.IOnClosedListener;
import java.io.IOException;

public class UsbAccessoryMux {
    public static final String ACTION_USB_ACCESSORY_ATTACHED = "com.parrot.arsdk.USB_ACCESSORY_ATTACHED";
    private static final String ACTION_USB_PERMISSION = "com.parrot.arsdk.USB_ACCESSORY_PERMISSION";
    private static final String MANUFACTURER_ID = "Parrot";
    private static final String SKYCONTROLLER2_MODEL_ID = "Skycontroller 2";
    private static final String SKYCONTROLLER_NG_MODEL_ID = "Skycontroller";
    private static final String TAG = "UsbAccessoryMux";
    private static UsbAccessoryMux sInstance;
    private final Context context;
    private ARDiscoveryMux discoveryChannel;
    private Listener mDiscoveryListener;
    private final Handler mHandler;
    private BroadcastReceiver mUsbAccessoryReceiver = new C15943();
    private ParcelFileDescriptor muxFileDescriptor;
    private Thread muxThread;
    private final IOnClosedListener onCloseListener = new C20252();
    private final UsbManager usbManager;
    private Mux usbMux;

    /* renamed from: com.parrot.arsdk.ardiscovery.UsbAccessoryMux$1 */
    class C15921 implements Runnable {
        C15921() {
        }

        public void run() {
            UsbAccessoryMux.this.usbMux.runReader();
        }
    }

    /* renamed from: com.parrot.arsdk.ardiscovery.UsbAccessoryMux$3 */
    class C15943 extends BroadcastReceiver {
        C15943() {
        }

        public void onReceive(Context context, Intent intent) {
            boolean permissionGranted = intent.getBooleanExtra("permission", true);
            UsbAccessory accessory = (UsbAccessory) intent.getParcelableExtra("accessory");
            String str = UsbAccessoryMux.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UsbAccessoryReceiver has received intent for accessory ");
            stringBuilder.append(accessory);
            stringBuilder.append(" and has permission ");
            stringBuilder.append(permissionGranted);
            Log.i(str, stringBuilder.toString());
            if (accessory != null) {
                UsbManager manager = (UsbManager) context.getSystemService("usb");
                String accessoryModel = accessory.getModel();
                if (UsbAccessoryMux.this.usbMux == null && permissionGranted && UsbAccessoryMux.MANUFACTURER_ID.equals(accessory.getManufacturer()) && UsbAccessoryMux.this.isValidModel(accessoryModel) && manager.hasPermission(accessory)) {
                    UsbAccessoryMux.this.startMux(accessory);
                }
            }
        }
    }

    /* renamed from: com.parrot.arsdk.ardiscovery.UsbAccessoryMux$2 */
    class C20252 implements IOnClosedListener {

        /* renamed from: com.parrot.arsdk.ardiscovery.UsbAccessoryMux$2$1 */
        class C15931 implements Runnable {
            C15931() {
            }

            public void run() {
                UsbAccessoryMux.this.closeMux();
            }
        }

        C20252() {
        }

        public void onClosed() {
            UsbAccessoryMux.this.mHandler.post(new C15931());
        }
    }

    public static UsbAccessoryMux get(Context appContext) {
        UsbAccessoryMux usbAccessoryMux;
        synchronized (UsbAccessoryMux.class) {
            if (sInstance == null) {
                sInstance = new UsbAccessoryMux(appContext);
            }
            usbAccessoryMux = sInstance;
        }
        return usbAccessoryMux;
    }

    private UsbAccessoryMux(Context appContext) {
        Log.i(TAG, "create UsbAccessoryMux");
        this.context = appContext;
        this.usbManager = (UsbManager) this.context.getSystemService("usb");
        this.mHandler = new Handler(Looper.getMainLooper());
        IntentFilter filter = new IntentFilter(ACTION_USB_ACCESSORY_ATTACHED);
        filter.addAction(ACTION_USB_PERMISSION);
        this.context.registerReceiver(this.mUsbAccessoryReceiver, filter);
        UsbAccessory[] accessoryList = this.usbManager.getAccessoryList();
        if (accessoryList != null) {
            for (UsbAccessory accessory : accessoryList) {
                String accessoryModel = accessory.getModel();
                if (MANUFACTURER_ID.equals(accessory.getManufacturer()) && isValidModel(accessoryModel)) {
                    this.usbManager.requestPermission(accessory, PendingIntent.getBroadcast(this.context, 0, new Intent(ACTION_USB_PERMISSION), 0));
                }
            }
        }
    }

    public void setDiscoveryListener(Listener listener) {
        this.mDiscoveryListener = listener;
        if (this.discoveryChannel != null) {
            this.discoveryChannel.setListener(this.mDiscoveryListener);
        }
    }

    public int connect(String device, String model, String id, String json, ConnectCallback callback) {
        if (this.discoveryChannel != null) {
            return this.discoveryChannel.connect(device, model, id, json, callback);
        }
        return -1;
    }

    public void cancelConnect() {
        if (this.discoveryChannel != null) {
            this.discoveryChannel.cancelConnect();
        }
    }

    private void startMux(UsbAccessory accessory) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Accessory connected ");
        stringBuilder.append(accessory);
        Log.i(str, stringBuilder.toString());
        if (this.usbMux == null) {
            this.muxFileDescriptor = this.usbManager.openAccessory(accessory);
            if (this.muxFileDescriptor != null) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Opening mux, fd=");
                stringBuilder.append(this.muxFileDescriptor.getFd());
                Log.i(str, stringBuilder.toString());
                this.usbMux = new Mux(this.muxFileDescriptor, this.onCloseListener);
                if (this.usbMux.isValid()) {
                    this.muxThread = new Thread(new C15921(), "muxThread");
                    this.muxThread.start();
                    this.discoveryChannel = new ARDiscoveryMux(this.usbMux);
                    if (this.mDiscoveryListener != null) {
                        this.discoveryChannel.setListener(this.mDiscoveryListener);
                        return;
                    }
                    return;
                }
                Log.i(TAG, "Error opening usb mux");
                this.usbMux = null;
                try {
                    this.muxFileDescriptor.close();
                } catch (IOException e) {
                }
                return;
            }
            Log.e(TAG, "Error opening USB Accessory");
        }
    }

    private void closeMux() {
        if (this.discoveryChannel != null) {
            this.discoveryChannel.destroy();
            this.discoveryChannel = null;
        }
        if (this.usbMux != null) {
            this.usbMux.stop();
            this.usbMux.destroy();
            this.usbMux = null;
        }
        if (this.muxFileDescriptor != null) {
            try {
                this.muxFileDescriptor.close();
            } catch (IOException e) {
            }
            this.muxFileDescriptor = null;
        }
    }

    private boolean isValidModel(String accessoryModel) {
        if (!SKYCONTROLLER2_MODEL_ID.equals(accessoryModel)) {
            if (!SKYCONTROLLER_NG_MODEL_ID.equals(accessoryModel)) {
                return false;
            }
        }
        return true;
    }

    public Mux getMux() {
        return this.usbMux;
    }
}
