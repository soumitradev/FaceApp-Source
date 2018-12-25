package com.parrot.arsdk.ardiscovery;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.DiscoveryListener;
import android.net.nsd.NsdManager.ResolveListener;
import android.net.nsd.NsdServiceInfo;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ARDiscoveryNsdDiscovery implements ARDiscoveryWifiDiscovery {
    private static final String TAG = ARDiscoveryNsdDiscovery.class.getSimpleName();
    private ARDiscoveryService broadcaster;
    private Context context;
    private final Map<String, ARDISCOVERY_PRODUCT_ENUM> devicesServices = new HashMap();
    private Boolean isNetDiscovering = Boolean.valueOf(false);
    private HashMap<String, DiscoveryListener> mDiscoveryListeners;
    private NsdManager mNsdManager;
    private ResolveListener mResolveListener;
    private final HashMap<String, ARDiscoveryDeviceService> netDeviceServicesHmap;
    private boolean opened = false;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryNsdDiscovery$1 */
    class C15871 implements DiscoveryListener {
        C15871() {
        }

        public void onDiscoveryStarted(String regType) {
            ARSALPrint.m534i(ARDiscoveryNsdDiscovery.TAG, "Service discovery started");
        }

        public void onServiceFound(NsdServiceInfo service) {
            String access$000 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Service discovery success");
            stringBuilder.append(service);
            ARSALPrint.m534i(access$000, stringBuilder.toString());
            if (ARDiscoveryNsdDiscovery.this.devicesServices.containsKey(service.getServiceType())) {
                ARDiscoveryNsdDiscovery.this.mNsdManager.resolveService(service, ARDiscoveryNsdDiscovery.this.mResolveListener);
            }
        }

        public void onServiceLost(NsdServiceInfo service) {
            String access$000 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service lost");
            stringBuilder.append(service);
            ARSALPrint.m534i(access$000, stringBuilder.toString());
            if (((ARDiscoveryDeviceService) ARDiscoveryNsdDiscovery.this.netDeviceServicesHmap.remove(service.getServiceName())) != null) {
                ARDiscoveryNsdDiscovery.this.broadcaster.broadcastDeviceServiceArrayUpdated();
                return;
            }
            String access$0002 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("service: ");
            stringBuilder2.append(service.getServiceName());
            stringBuilder2.append(" not known");
            ARSALPrint.m532e(access$0002, stringBuilder2.toString());
        }

        public void onDiscoveryStopped(String serviceType) {
            String access$000 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Discovery stopped: ");
            stringBuilder.append(serviceType);
            ARSALPrint.m534i(access$000, stringBuilder.toString());
        }

        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            String access$000 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onStartDiscoveryFailed ... Discovery failed: Error code:");
            stringBuilder.append(errorCode);
            ARSALPrint.m532e(access$000, stringBuilder.toString());
        }

        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            String access$000 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onStopDiscoveryFailed ... Discovery failed: Error code:");
            stringBuilder.append(errorCode);
            ARSALPrint.m532e(access$000, stringBuilder.toString());
        }
    }

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryNsdDiscovery$2 */
    class C15882 implements ResolveListener {
        C15882() {
        }

        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            String access$000 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resolve failed ");
            stringBuilder.append(errorCode);
            ARSALPrint.m532e(access$000, stringBuilder.toString());
        }

        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            String access$000 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Resolve Succeeded. ");
            stringBuilder.append(serviceInfo);
            ARSALPrint.m534i(access$000, stringBuilder.toString());
            int port = serviceInfo.getPort();
            String ip = serviceInfo.getHost().getHostAddress();
            boolean known = ARDiscoveryNsdDiscovery.this.netDeviceServicesHmap.containsKey(serviceInfo.getServiceName());
            String access$0002 = ARDiscoveryNsdDiscovery.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("IP = ");
            stringBuilder2.append(ip);
            stringBuilder2.append(", Port = ");
            stringBuilder2.append(port);
            stringBuilder2.append(", Known ? ");
            stringBuilder2.append(known);
            ARSALPrint.m534i(access$0002, stringBuilder2.toString());
            if (ip != null && !known) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(serviceInfo.getServiceType().substring(1, serviceInfo.getServiceType().length()));
                stringBuilder3.append(".");
                ARDiscoveryDeviceNetService deviceNetService = new ARDiscoveryDeviceNetService(serviceInfo.getServiceName(), stringBuilder3.toString(), ip, port, null);
                ARDISCOVERY_PRODUCT_ENUM product = (ARDISCOVERY_PRODUCT_ENUM) ARDiscoveryNsdDiscovery.this.devicesServices.get(deviceNetService.getType());
                if (product != null) {
                    ARDiscoveryDeviceService deviceService = new ARDiscoveryDeviceService(serviceInfo.getServiceName(), deviceNetService, ARDiscoveryService.nativeGetProductID(product.getValue()));
                    ARDiscoveryNsdDiscovery.this.netDeviceServicesHmap.put(deviceService.getName(), deviceService);
                    ARDiscoveryNsdDiscovery.this.broadcaster.broadcastDeviceServiceArrayUpdated();
                    return;
                }
                String access$0003 = ARDiscoveryNsdDiscovery.TAG;
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append("Found an unknown service : ");
                stringBuilder4.append(deviceNetService);
                ARSALPrint.m532e(access$0003, stringBuilder4.toString());
            }
        }
    }

    public ARDiscoveryNsdDiscovery(Set<ARDISCOVERY_PRODUCT_ENUM> supportedProducts) {
        for (ARDISCOVERY_PRODUCT_ENUM product : ARDISCOVERY_PRODUCT_ENUM.values()) {
            if (product != ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_MAX) {
                if (product != ARDISCOVERY_PRODUCT_ENUM.eARDISCOVERY_PRODUCT_UNKNOWN_ENUM_VALUE) {
                    if (supportedProducts.contains(product)) {
                        this.devicesServices.put(String.format(ARDiscoveryService.ARDISCOVERY_SERVICE_NET_DEVICE_FORMAT, new Object[]{Integer.valueOf(ARDiscoveryService.getProductID(product))}), product);
                    }
                }
            }
        }
        this.netDeviceServicesHmap = new HashMap();
        initializeDiscoveryListeners();
        initializeResolveListener();
    }

    public synchronized void open(ARDiscoveryService broadcaster, Context c) {
        this.broadcaster = broadcaster;
        this.context = c;
        if (!this.opened) {
            this.netDeviceServicesHmap.clear();
            this.mNsdManager = (NsdManager) this.context.getSystemService("servicediscovery");
            this.opened = true;
        }
    }

    public synchronized void close() {
        if (this.opened) {
            for (String type : this.devicesServices.keySet()) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Will stop searching for devices of type <");
                stringBuilder.append(type);
                stringBuilder.append(">");
                ARSALPrint.m534i(str, stringBuilder.toString());
                this.mNsdManager.stopServiceDiscovery((DiscoveryListener) this.mDiscoveryListeners.get(type));
            }
            this.broadcaster = null;
            this.context = null;
            this.opened = false;
        }
    }

    public synchronized void start() {
        if (!(this.isNetDiscovering.booleanValue() || this.mNsdManager == null || this.mDiscoveryListeners == null)) {
            for (String type : this.devicesServices.keySet()) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Will start searching for devices of type <");
                stringBuilder.append(type);
                stringBuilder.append(">");
                ARSALPrint.m534i(str, stringBuilder.toString());
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("NsdManager.PROTOCOL_DNS_SD:1 mDiscoveryListeners.get(type):");
                stringBuilder.append(this.mDiscoveryListeners.get(type));
                ARSALPrint.m534i(str, stringBuilder.toString());
                this.mNsdManager.discoverServices(type, 1, (DiscoveryListener) this.mDiscoveryListeners.get(type));
            }
            this.isNetDiscovering = Boolean.valueOf(true);
        }
    }

    public synchronized void stop() {
        if (!(!this.isNetDiscovering.booleanValue() || this.mNsdManager == null || this.mDiscoveryListeners == null)) {
            for (String type : this.devicesServices.keySet()) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Will stop searching for devices of type <");
                stringBuilder.append(type);
                stringBuilder.append(">");
                ARSALPrint.m534i(str, stringBuilder.toString());
                this.mNsdManager.stopServiceDiscovery((DiscoveryListener) this.mDiscoveryListeners.get(type));
            }
            this.isNetDiscovering = Boolean.valueOf(false);
        }
    }

    public void wifiAvailable(boolean wifiAvailable) {
    }

    private void initializeDiscoveryListeners() {
        this.mDiscoveryListeners = new HashMap();
        for (String type : this.devicesServices.keySet()) {
            this.mDiscoveryListeners.put(type, new C15871());
        }
    }

    private void initializeResolveListener() {
        this.mResolveListener = new C15882();
    }

    public List<ARDiscoveryDeviceService> getDeviceServicesArray() {
        return new ArrayList(this.netDeviceServicesHmap.values());
    }
}
