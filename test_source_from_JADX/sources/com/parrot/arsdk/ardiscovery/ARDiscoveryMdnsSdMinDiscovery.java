package com.parrot.arsdk.ardiscovery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Bundle;
import com.parrot.arsdk.ardiscovery.mdnssdmin.MdnsSdMin;
import com.parrot.arsdk.ardiscovery.mdnssdmin.MdnsSdMin.Listener;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ARDiscoveryMdnsSdMinDiscovery implements ARDiscoveryWifiDiscovery {
    private static final String TAG = ARDiscoveryMdnsSdMinDiscovery.class.getSimpleName();
    private ARDiscoveryService broadcaster;
    private Context context;
    private final Map<String, ARDISCOVERY_PRODUCT_ENUM> devicesServices;
    private boolean mWifiAvailable;
    private final MdnsSdMin mdnsSd;
    private final Listener mdsnSdListener = new C20232();
    private MulticastLock multicastLock;
    private final Map<String, ARDiscoveryDeviceService> netDeviceServicesHmap;
    private final IntentFilter networkStateChangedFilter;
    private final BroadcastReceiver networkStateIntentReceiver = new C15861();
    private boolean started;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryMdnsSdMinDiscovery$1 */
    class C15861 extends BroadcastReceiver {
        C15861() {
        }

        public void onReceive(Context context, Intent intent) {
            if (!ARDiscoveryMdnsSdMinDiscovery.this.mWifiAvailable && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ARSALPrint.m530d(ARDiscoveryMdnsSdMinDiscovery.TAG, "Receive CONNECTIVITY_ACTION intent, extras are :");
                Bundle extras = intent.getExtras();
                for (String key : extras.keySet()) {
                    String access$100 = ARDiscoveryMdnsSdMinDiscovery.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Key : ");
                    stringBuilder.append(key);
                    stringBuilder.append(", value = ");
                    stringBuilder.append(extras.get(key) != null ? extras.get(key).toString() : "NULL");
                    ARSALPrint.m530d(access$100, stringBuilder.toString());
                }
                ARSALPrint.m530d(ARDiscoveryMdnsSdMinDiscovery.TAG, "End of extras");
                boolean needFlush = extras.getBoolean("noConnectivity", false);
                if (needFlush) {
                    ARSALPrint.m530d(ARDiscoveryMdnsSdMinDiscovery.TAG, "Extra noConnectivity set to true, need flush");
                }
                NetworkInfo netInfos = (NetworkInfo) extras.get("networkInfo");
                if (netInfos != null && netInfos.getState().equals(State.DISCONNECTED)) {
                    ARSALPrint.m530d(ARDiscoveryMdnsSdMinDiscovery.TAG, "NetworkInfo.State is DISCONNECTED, need flush");
                    needFlush = true;
                }
                if (needFlush) {
                    ARDiscoveryMdnsSdMinDiscovery.this.stopWifi();
                    return;
                }
                ARDiscoveryMdnsSdMinDiscovery.this.mdnsSd.stop();
                ARDiscoveryMdnsSdMinDiscovery.this.startWifi();
            }
        }
    }

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryMdnsSdMinDiscovery$2 */
    class C20232 implements Listener {
        C20232() {
        }

        public void onServiceAdded(String name, String serviceType, String ipAddress, int port, String[] txts) {
            String access$100 = ARDiscoveryMdnsSdMinDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onServiceAdded : ");
            stringBuilder.append(name);
            stringBuilder.append(" |type : ");
            stringBuilder.append(serviceType);
            stringBuilder.append(" |ip : ");
            stringBuilder.append(ipAddress);
            stringBuilder.append(" |port : ");
            stringBuilder.append(port);
            stringBuilder.append(" |txts = ");
            stringBuilder.append(txts);
            ARSALPrint.m530d(access$100, stringBuilder.toString());
            access$100 = null;
            if (txts != null && txts.length >= 1) {
                access$100 = txts[0];
            }
            ARDiscoveryDeviceNetService deviceNetService = new ARDiscoveryDeviceNetService(name, serviceType, ipAddress, port, access$100);
            ARDISCOVERY_PRODUCT_ENUM product = (ARDISCOVERY_PRODUCT_ENUM) ARDiscoveryMdnsSdMinDiscovery.this.devicesServices.get(deviceNetService.getType());
            if (product != null) {
                ARDiscoveryDeviceService deviceService = new ARDiscoveryDeviceService(name, deviceNetService, ARDiscoveryService.nativeGetProductID(product.getValue()));
                ARDiscoveryMdnsSdMinDiscovery.this.netDeviceServicesHmap.put(deviceService.getName(), deviceService);
                if (ARDiscoveryMdnsSdMinDiscovery.this.broadcaster != null) {
                    ARDiscoveryMdnsSdMinDiscovery.this.mdnsSd.cancelSendQueries();
                    ARDiscoveryMdnsSdMinDiscovery.this.broadcaster.broadcastDeviceServiceArrayUpdated();
                }
            }
        }

        public void onServiceRemoved(String name, String serviceType) {
            String access$100 = ARDiscoveryMdnsSdMinDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onServiceRemoved : ");
            stringBuilder.append(name);
            stringBuilder.append(" |type : ");
            stringBuilder.append(serviceType);
            ARSALPrint.m530d(access$100, stringBuilder.toString());
            if (((ARDiscoveryDeviceService) ARDiscoveryMdnsSdMinDiscovery.this.netDeviceServicesHmap.remove(name)) != null) {
                ARDiscoveryMdnsSdMinDiscovery.this.broadcaster.broadcastDeviceServiceArrayUpdated();
            }
        }
    }

    public ARDiscoveryMdnsSdMinDiscovery(Set<ARDISCOVERY_PRODUCT_ENUM> supportedProducts) {
        ARSALPrint.m530d(TAG, "Creating MdsnSd based ARDiscovery");
        this.devicesServices = new HashMap();
        for (ARDISCOVERY_PRODUCT_ENUM product : ARDISCOVERY_PRODUCT_ENUM.values()) {
            if (product != ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_MAX) {
                if (product != ARDISCOVERY_PRODUCT_ENUM.eARDISCOVERY_PRODUCT_UNKNOWN_ENUM_VALUE) {
                    if (supportedProducts.contains(product)) {
                        String devicesService = String.format(ARDiscoveryService.ARDISCOVERY_SERVICE_NET_DEVICE_FORMAT, new Object[]{Integer.valueOf(ARDiscoveryService.getProductID(product))});
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(devicesService);
                        stringBuilder.append(ARDiscoveryService.ARDISCOVERY_SERVICE_NET_DEVICE_DOMAIN);
                        this.devicesServices.put(stringBuilder.toString(), product);
                    }
                }
            }
        }
        this.netDeviceServicesHmap = new HashMap();
        this.mdnsSd = new MdnsSdMin((String[]) this.devicesServices.keySet().toArray(new String[this.devicesServices.keySet().size()]), this.mdsnSdListener);
        this.networkStateChangedFilter = new IntentFilter();
        this.networkStateChangedFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
    }

    public synchronized void open(ARDiscoveryService broadcaster, Context c) {
        ARSALPrint.m530d(TAG, "Opening MdsnSd based ARDiscovery");
        this.broadcaster = broadcaster;
        this.context = c;
        this.multicastLock = ((WifiManager) this.context.getSystemService("wifi")).createMulticastLock("ARDiscovery");
    }

    public synchronized void close() {
        ARSALPrint.m530d(TAG, "Closing MdsnSd based ARDiscovery");
        if (this.started) {
            stop();
        }
        this.mdnsSd.stop();
        this.broadcaster = null;
        this.context = null;
    }

    public synchronized void start() {
        if (!this.started) {
            ARSALPrint.m530d(TAG, "Starting MdsnSd based ARDiscovery");
            if (!this.multicastLock.isHeld()) {
                this.multicastLock.acquire();
            }
            this.context.registerReceiver(this.networkStateIntentReceiver, this.networkStateChangedFilter);
            this.started = true;
        }
    }

    public synchronized void stop() {
        if (this.started) {
            ARSALPrint.m530d(TAG, "Stopping MdsnSd based ARDiscovery");
            this.started = false;
            if (this.multicastLock.isHeld()) {
                this.multicastLock.release();
            }
            this.context.unregisterReceiver(this.networkStateIntentReceiver);
            this.mdnsSd.stop();
            this.netDeviceServicesHmap.clear();
            this.broadcaster.broadcastDeviceServiceArrayUpdated();
        }
    }

    public void wifiAvailable(boolean wifiAvailable) {
        if (this.mWifiAvailable != wifiAvailable) {
            this.mWifiAvailable = wifiAvailable;
            if (this.mWifiAvailable) {
                startWifi();
            } else {
                stopWifi();
            }
        }
    }

    public List<ARDiscoveryDeviceService> getDeviceServicesArray() {
        return new ArrayList(this.netDeviceServicesHmap.values());
    }

    private void startWifi() {
        ConnectivityManager connManager = (ConnectivityManager) this.context.getSystemService("connectivity");
        NetworkInfo mWifi = connManager.getNetworkInfo(1);
        NetworkInfo mEth = connManager.getNetworkInfo(9);
        NetworkInterface netInterface = null;
        if (mWifi != null && (this.mWifiAvailable || mWifi.isConnected())) {
            WifiManager wifiManager = (WifiManager) this.context.getSystemService("wifi");
            if (wifiManager != null) {
                int ipAddressInt = wifiManager.getConnectionInfo().getIpAddress();
                try {
                    InetAddress addr = InetAddress.getByName(String.format(Locale.US, "%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddressInt & 255), Integer.valueOf((ipAddressInt >> 8) & 255), Integer.valueOf((ipAddressInt >> 16) & 255), Integer.valueOf((ipAddressInt >> 24) & 255)}));
                    Enumeration<NetworkInterface> intfs = NetworkInterface.getNetworkInterfaces();
                    while (netInterface == null && intfs.hasMoreElements()) {
                        NetworkInterface intf = (NetworkInterface) intfs.nextElement();
                        Enumeration<InetAddress> interfaceAddresses = intf.getInetAddresses();
                        while (netInterface == null && interfaceAddresses.hasMoreElements()) {
                            if (((InetAddress) interfaceAddresses.nextElement()).equals(addr)) {
                                netInterface = intf;
                            }
                        }
                    }
                } catch (Exception e) {
                    ARSALPrint.m533e(TAG, "Unable to get the wifi network interface", e);
                }
            }
        }
        if ((mWifi == null || !(this.mWifiAvailable || mWifi.isConnected())) && (mEth == null || !mEth.isConnected())) {
            ARSALPrint.m530d(TAG, "Not connected to either wifi or ethernet, need flush list");
            stopWifi();
            return;
        }
        ARSALPrint.m530d(TAG, "Restaring MdsnSd");
        this.mdnsSd.start(netInterface);
    }

    private void stopWifi() {
        ARSALPrint.m530d(TAG, "Clearing devices list");
        this.mdnsSd.stop();
        this.netDeviceServicesHmap.clear();
        this.broadcaster.broadcastDeviceServiceArrayUpdated();
    }
}
