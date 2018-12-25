package com.parrot.arsdk.ardiscovery;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ARDiscoveryService extends Service {
    public static String ARDISCOVERY_SERVICE_NET_DEVICE_DOMAIN = null;
    public static String ARDISCOVERY_SERVICE_NET_DEVICE_FORMAT = null;
    private static final String TAG = ARDiscoveryService.class.getSimpleName();
    public static final String kARDiscoveryServiceNotificationServicesDevicesListUpdated = "kARDiscoveryServiceNotificationServicesDevicesListUpdated";
    private static Set<ARDISCOVERY_PRODUCT_ENUM> supportedProducts;
    private static boolean usePublisher;
    private static ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_ENUM wifiDiscoveryType;
    private final IBinder binder = new LocalBinder();
    private ARDiscoveryBLEDiscovery bleDiscovery;
    private HashMap<String, Intent> intentCache;
    private boolean mWifiAvailable;
    private ARDiscoveryUsbDiscovery usbDiscovery;
    private ARDiscoveryWifiDiscovery wifiDiscovery;
    private ARDiscoveryNsdPublisher wifiPublisher;

    public enum ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_ENUM {
        ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_JMDNS,
        ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_NSD,
        ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_MDSNSDMIN
    }

    public class LocalBinder extends Binder {
        public ARDiscoveryService getService() {
            ARSALPrint.m530d(ARDiscoveryService.TAG, "getService");
            return ARDiscoveryService.this;
        }
    }

    public enum eARDISCOVERY_SERVICE_EVENT_STATUS {
        ARDISCOVERY_SERVICE_EVENT_STATUS_ADD,
        ARDISCOVERY_SERVICE_EVENT_STATUS_REMOVED,
        ARDISCOVERY_SERVICE_EVENT_STATUS_RESOLVED
    }

    private static native String nativeGetDefineNetDeviceDomain();

    private static native String nativeGetDefineNetDeviceFormat();

    private static native int nativeGetProductFamily(int i);

    private static native int nativeGetProductFromName(String str);

    private static native int nativeGetProductFromProductID(int i);

    public static native int nativeGetProductID(int i);

    private static native String nativeGetProductName(int i);

    private static native String nativeGetProductPathName(int i);

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nativeGetDefineNetDeviceFormat());
        stringBuilder.append(".");
        ARDISCOVERY_SERVICE_NET_DEVICE_FORMAT = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(nativeGetDefineNetDeviceDomain());
        stringBuilder.append(".");
        ARDISCOVERY_SERVICE_NET_DEVICE_DOMAIN = stringBuilder.toString();
    }

    public static void setWifiPreferredWifiDiscoveryType(ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_ENUM discoveryType) {
        synchronized (ARDiscoveryService.class) {
            if (wifiDiscoveryType != null) {
                throw new RuntimeException("setWifiPreferredWifiDiscoveryType must be called before stating ARDiscoveryService");
            }
            wifiDiscoveryType = discoveryType;
        }
    }

    public static void setSupportedProducts(Set<ARDISCOVERY_PRODUCT_ENUM> products) {
        synchronized (ARDiscoveryService.class) {
            if (supportedProducts != null) {
                throw new RuntimeException("setWifiPreferredWifiDiscoveryType must be called before stating ARDiscoveryService");
            }
            supportedProducts = products;
        }
    }

    public static void setUsePublisher(boolean use) {
        usePublisher = use;
    }

    public IBinder onBind(Intent intent) {
        ARSALPrint.m530d(TAG, "onBind");
        return this.binder;
    }

    public void onCreate() {
        initIntents();
        synchronized (getClass()) {
            if (wifiDiscoveryType == null) {
                wifiDiscoveryType = ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_ENUM.ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_MDSNSDMIN;
            }
            if (supportedProducts == null) {
                supportedProducts = EnumSet.allOf(ARDISCOVERY_PRODUCT_ENUM.class);
            }
        }
        this.bleDiscovery = new ARDiscoveryBLEDiscoveryImpl(supportedProducts);
        this.bleDiscovery.open(this, this);
        initWifiDiscovery();
        this.usbDiscovery = new ARDiscoveryUsbDiscovery();
        this.usbDiscovery.open(this, this);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            ARSALPrint.m538w(TAG, "recreated by the system, don't need! stop it");
            stopSelf();
        }
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.usbDiscovery != null) {
            this.usbDiscovery.close();
            this.usbDiscovery = null;
        }
        if (this.bleDiscovery != null) {
            this.bleDiscovery.close();
            this.bleDiscovery = null;
        }
        if (this.wifiDiscovery != null) {
            this.wifiDiscovery.close();
            this.wifiDiscovery = null;
        }
        if (this.wifiPublisher != null) {
            this.wifiPublisher.close();
            this.wifiPublisher = null;
        }
    }

    private void initIntents() {
        ARSALPrint.m530d(TAG, "initIntents");
        this.intentCache = new HashMap();
        this.intentCache.put(kARDiscoveryServiceNotificationServicesDevicesListUpdated, new Intent(kARDiscoveryServiceNotificationServicesDevicesListUpdated));
    }

    public boolean onUnbind(Intent intent) {
        ARSALPrint.m530d(TAG, "onUnbind");
        return true;
    }

    public void onRebind(Intent intent) {
        ARSALPrint.m530d(TAG, "onRebind");
    }

    private synchronized void initWifiDiscovery() {
        String str;
        StringBuilder stringBuilder;
        switch (wifiDiscoveryType) {
            case ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_NSD:
                if (VERSION.SDK_INT < 16) {
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("NSD can't run on ");
                    stringBuilder.append(VERSION.SDK_INT);
                    stringBuilder.append(" MdnsSdMin will be used");
                    ARSALPrint.m538w(str, stringBuilder.toString());
                    this.wifiDiscovery = new ARDiscoveryMdnsSdMinDiscovery(supportedProducts);
                    this.wifiPublisher = null;
                    ARSALPrint.m530d(TAG, "Wifi discovery asked is nsd and it will be ARDiscoveryMdnsSdMinDiscovery");
                    break;
                }
                this.wifiDiscovery = new ARDiscoveryNsdDiscovery(supportedProducts);
                if (usePublisher) {
                    this.wifiPublisher = new ARDiscoveryNsdPublisher();
                }
                ARSALPrint.m530d(TAG, "Wifi discovery asked is nsd and it will be ARDiscoveryNsdDiscovery");
                break;
            case ARDISCOVERYSERVICE_WIFI_DISCOVERY_TYPE_MDSNSDMIN:
                this.wifiDiscovery = new ARDiscoveryMdnsSdMinDiscovery(supportedProducts);
                ARSALPrint.m530d(TAG, "Wifi discovery asked is MDSNSDMIN and it will be ARDiscoveryMdnsSdMinDiscovery");
                if (VERSION.SDK_INT >= 16) {
                    if (usePublisher) {
                        this.wifiPublisher = new ARDiscoveryNsdPublisher();
                        break;
                    }
                }
                this.wifiPublisher = null;
                break;
                break;
            default:
                this.wifiDiscovery = new ARDiscoveryJmdnsDiscovery(supportedProducts);
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Wifi discovery asked is ");
                stringBuilder.append(wifiDiscoveryType);
                stringBuilder.append(" and it will be ARDiscoveryJmdnsDiscovery");
                ARSALPrint.m530d(str, stringBuilder.toString());
                this.wifiPublisher = null;
                break;
        }
        ARSALPrint.m536v(TAG, "Opening wifi discovery");
        this.wifiDiscovery.open(this, this);
        if (this.wifiPublisher != null) {
            ARSALPrint.m536v(TAG, "Opening wifi publisher");
            this.wifiPublisher.open(this, this);
        } else {
            ARSALPrint.m534i(TAG, "No wifi publisher available");
        }
    }

    public void wifiAvailable(boolean wifiAvailable) {
        this.mWifiAvailable = wifiAvailable;
        if (this.wifiDiscovery != null) {
            this.wifiDiscovery.wifiAvailable(this.mWifiAvailable);
        }
    }

    public synchronized void start() {
        ARSALPrint.m530d(TAG, "Start discoveries");
        this.bleDiscovery.start();
        this.wifiDiscovery.start();
        this.usbDiscovery.start();
    }

    public synchronized void stop() {
        ARSALPrint.m530d(TAG, "Stop discoveries");
        this.bleDiscovery.stop();
        this.wifiDiscovery.stop();
        this.usbDiscovery.stop();
    }

    public synchronized void startWifiDiscovering() {
        if (this.wifiDiscovery != null) {
            ARSALPrint.m530d(TAG, "Start wifi discovery");
            this.wifiDiscovery.start();
        }
    }

    public synchronized void stopWifiDiscovering() {
        if (this.wifiDiscovery != null) {
            ARSALPrint.m530d(TAG, "Stop wifi discovery");
            this.wifiDiscovery.stop();
        }
    }

    public synchronized void startUsbDiscovering() {
        if (this.usbDiscovery != null) {
            ARSALPrint.m530d(TAG, "Start Usb discovery");
            this.usbDiscovery.start();
        }
    }

    public synchronized void stopUsbDiscovering() {
        if (this.usbDiscovery != null) {
            ARSALPrint.m530d(TAG, "Stop Usb discovery");
            this.usbDiscovery.stop();
        }
    }

    public synchronized void startBLEDiscovering() {
        if (this.bleDiscovery != null) {
            ARSALPrint.m530d(TAG, "Start BLE discovery");
            this.bleDiscovery.start();
        }
    }

    public synchronized void stopBLEDiscovering() {
        if (this.bleDiscovery != null) {
            ARSALPrint.m530d(TAG, "Stop BLE discovery");
            this.bleDiscovery.stop();
        }
    }

    public void broadcastDeviceServiceArrayUpdated() {
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast((Intent) this.intentCache.get(kARDiscoveryServiceNotificationServicesDevicesListUpdated));
    }

    public List<ARDiscoveryDeviceService> getDeviceServicesArray() {
        List<ARDiscoveryDeviceService> deviceServicesArray = new ArrayList();
        if (this.wifiDiscovery != null) {
            deviceServicesArray.addAll(this.wifiDiscovery.getDeviceServicesArray());
        }
        if (this.bleDiscovery != null) {
            deviceServicesArray.addAll(this.bleDiscovery.getDeviceServicesArray());
        }
        if (this.usbDiscovery != null) {
            ARDiscoveryDeviceService deviceService = this.usbDiscovery.getDeviceService();
            if (deviceService != null) {
                deviceServicesArray.add(deviceService);
            }
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getDeviceServicesArray: ");
        stringBuilder.append(deviceServicesArray);
        ARSALPrint.m530d(str, stringBuilder.toString());
        return deviceServicesArray;
    }

    public boolean publishService(ARDISCOVERY_PRODUCT_ENUM product, String name, int port) {
        if (this.wifiPublisher != null) {
            return this.wifiPublisher.publishService(product, name, port);
        }
        return false;
    }

    public boolean publishService(int product_id, String name, int port) {
        if (this.wifiPublisher != null) {
            return this.wifiPublisher.publishService(product_id, name, port);
        }
        return false;
    }

    public void unpublishServices() {
        if (this.wifiPublisher != null) {
            this.wifiPublisher.unpublishService();
        }
    }

    public static int getProductID(ARDISCOVERY_PRODUCT_ENUM product) {
        if (product != ARDISCOVERY_PRODUCT_ENUM.eARDISCOVERY_PRODUCT_UNKNOWN_ENUM_VALUE) {
            return nativeGetProductID(product.getValue());
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getProductID:Unknown product : ");
        stringBuilder.append(product);
        ARSALPrint.m532e(str, stringBuilder.toString());
        return 0;
    }

    public static ARDISCOVERY_PRODUCT_ENUM getProductFromProductID(int productID) {
        return ARDISCOVERY_PRODUCT_ENUM.getFromValue(nativeGetProductFromProductID(productID));
    }

    public static String getProductName(ARDISCOVERY_PRODUCT_ENUM product) {
        if (product != ARDISCOVERY_PRODUCT_ENUM.eARDISCOVERY_PRODUCT_UNKNOWN_ENUM_VALUE) {
            return nativeGetProductName(product.getValue());
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getProductName:Unknown product : ");
        stringBuilder.append(product);
        ARSALPrint.m532e(str, stringBuilder.toString());
        return "UNKNOWN";
    }

    public static String getProductPathName(ARDISCOVERY_PRODUCT_ENUM product) {
        return nativeGetProductPathName(product.getValue());
    }

    public static ARDISCOVERY_PRODUCT_ENUM getProductFromName(String name) {
        return ARDISCOVERY_PRODUCT_ENUM.getFromValue(nativeGetProductFromName(name));
    }

    public static ARDISCOVERY_PRODUCT_FAMILY_ENUM getProductFamily(ARDISCOVERY_PRODUCT_ENUM product) {
        return ARDISCOVERY_PRODUCT_FAMILY_ENUM.getFromValue(nativeGetProductFamily(product.getValue()));
    }
}
