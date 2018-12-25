package com.parrot.arsdk.ardiscovery;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdManager.RegistrationListener;
import android.net.nsd.NsdServiceInfo;

public class ARDiscoveryNsdPublisher implements ARDiscoveryWifiPublisher {
    private static final String TAG = ARDiscoveryNsdPublisher.class.getSimpleName();
    private ARDiscoveryService broadcaster;
    private Context context;
    private NsdManager mNsdManager;
    private RegistrationListener mRegistrationListener;
    private String mServiceName;
    private boolean opened = false;
    private boolean published;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryNsdPublisher$1 */
    class C15891 implements RegistrationListener {
        C15891() {
        }

        public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
            ARDiscoveryNsdPublisher.this.mServiceName = NsdServiceInfo.getServiceName();
        }

        public void onRegistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
        }

        public void onServiceUnregistered(NsdServiceInfo arg0) {
        }

        public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
        }
    }

    public ARDiscoveryNsdPublisher() {
        initializeRegistrationListener();
    }

    public synchronized void open(ARDiscoveryService broadcaster, Context c) {
        this.broadcaster = broadcaster;
        this.context = c;
        if (!this.opened) {
            this.mNsdManager = (NsdManager) this.context.getSystemService("servicediscovery");
            this.opened = true;
        }
    }

    public synchronized void close() {
        if (this.opened) {
            this.broadcaster = null;
            this.context = null;
            this.opened = false;
        }
    }

    public void update() {
    }

    public boolean publishService(ARDISCOVERY_PRODUCT_ENUM product, String name, int port) {
        return publishService(ARDiscoveryService.getProductID(product), name, port);
    }

    public boolean publishService(int product_id, String name, int port) {
        if (this.opened) {
            unpublishService();
            NsdServiceInfo serviceInfo = new NsdServiceInfo();
            serviceInfo.setServiceName(name);
            serviceInfo.setPort(port);
            serviceInfo.setServiceType(String.format(ARDiscoveryService.ARDISCOVERY_SERVICE_NET_DEVICE_FORMAT, new Object[]{Integer.valueOf(product_id)}));
            this.mNsdManager.registerService(serviceInfo, 1, this.mRegistrationListener);
            this.published = true;
        }
        return this.published;
    }

    public void unpublishService() {
        if (this.published) {
            this.mNsdManager.unregisterService(this.mRegistrationListener);
            this.mServiceName = null;
            this.published = false;
        }
    }

    private void initializeRegistrationListener() {
        this.mRegistrationListener = new C15891();
    }
}
