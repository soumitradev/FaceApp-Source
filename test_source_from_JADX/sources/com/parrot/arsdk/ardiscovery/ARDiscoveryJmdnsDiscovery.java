package com.parrot.arsdk.ardiscovery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Pair;
import com.parrot.arsdk.ardiscovery.ARDiscoveryService.eARDISCOVERY_SERVICE_EVENT_STATUS;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RejectedExecutionException;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

public class ARDiscoveryJmdnsDiscovery implements ARDiscoveryWifiDiscovery {
    private static final String TAG = ARDiscoveryJmdnsDiscovery.class.getSimpleName();
    private static InetAddress nullAddress;
    private Boolean askForNetDiscovering = Boolean.valueOf(false);
    private ARDiscoveryService broadcaster;
    private Context context;
    private final Map<String, ARDISCOVERY_PRODUCT_ENUM> devicesServices;
    private InetAddress hostAddress;
    private String hostIp;
    private Boolean isNetDiscovering = Boolean.valueOf(false);
    private AsyncTask<Object, Object, Object> jmdnsCreatorAsyncTask;
    private ServiceListener mDNSListener;
    private JmDNS mDNSManager;
    private final Object mJmDNSLock = new Object();
    private HashMap<String, ARDiscoveryDeviceService> netDeviceServicesHmap;
    private IntentFilter networkStateChangedFilter;
    private BroadcastReceiver networkStateIntentReceiver;
    private boolean opened = false;

    /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryJmdnsDiscovery$1 */
    class C15841 extends BroadcastReceiver {
        C15841() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ARDiscoveryJmdnsDiscovery.this.update();
            }
        }
    }

    private class JmdnsCreatorAsyncTask extends AsyncTask<Object, Object, Object> {

        /* renamed from: com.parrot.arsdk.ardiscovery.ARDiscoveryJmdnsDiscovery$JmdnsCreatorAsyncTask$1 */
        class C20221 implements ServiceListener {
            C20221() {
            }

            public void serviceAdded(ServiceEvent event) {
                String access$500 = ARDiscoveryJmdnsDiscovery.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Service Added: ");
                stringBuilder.append(event.getName());
                ARSALPrint.m530d(access$500, stringBuilder.toString());
                Pair<ServiceEvent, eARDISCOVERY_SERVICE_EVENT_STATUS> dataProgress = new Pair(event, eARDISCOVERY_SERVICE_EVENT_STATUS.ARDISCOVERY_SERVICE_EVENT_STATUS_ADD);
                JmdnsCreatorAsyncTask.this.publishProgress(new Object[]{dataProgress});
            }

            public void serviceRemoved(ServiceEvent event) {
                String access$500 = ARDiscoveryJmdnsDiscovery.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Service removed: ");
                stringBuilder.append(event.getName());
                ARSALPrint.m530d(access$500, stringBuilder.toString());
                Pair<ServiceEvent, eARDISCOVERY_SERVICE_EVENT_STATUS> dataProgress = new Pair(event, eARDISCOVERY_SERVICE_EVENT_STATUS.ARDISCOVERY_SERVICE_EVENT_STATUS_REMOVED);
                JmdnsCreatorAsyncTask.this.publishProgress(new Object[]{dataProgress});
            }

            public void serviceResolved(ServiceEvent event) {
                String access$500 = ARDiscoveryJmdnsDiscovery.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Service resolved: ");
                stringBuilder.append(event.getName());
                ARSALPrint.m530d(access$500, stringBuilder.toString());
                Pair<ServiceEvent, eARDISCOVERY_SERVICE_EVENT_STATUS> dataProgress = new Pair(event, eARDISCOVERY_SERVICE_EVENT_STATUS.ARDISCOVERY_SERVICE_EVENT_STATUS_RESOLVED);
                JmdnsCreatorAsyncTask.this.publishProgress(new Object[]{dataProgress});
            }
        }

        private JmdnsCreatorAsyncTask() {
        }

        protected Object doInBackground(Object... params) {
            try {
                if (ARDiscoveryJmdnsDiscovery.this.hostAddress == null || ARDiscoveryJmdnsDiscovery.this.hostAddress.equals(ARDiscoveryJmdnsDiscovery.nullAddress)) {
                    ARDiscoveryJmdnsDiscovery.this.mDNSManager = JmDNS.create();
                } else {
                    ARDiscoveryJmdnsDiscovery.this.mDNSManager = JmDNS.create(ARDiscoveryJmdnsDiscovery.this.hostAddress);
                }
                ARSALPrint.m530d(ARDiscoveryJmdnsDiscovery.TAG, "JmDNS.createed");
                ARDiscoveryJmdnsDiscovery.this.mDNSListener = new C20221();
            } catch (IOException e) {
                ARSALPrint.m532e(ARDiscoveryJmdnsDiscovery.TAG, "mDNSManager creation failed.");
                e.printStackTrace();
                ARDiscoveryJmdnsDiscovery.this.askForNetDiscovering = Boolean.valueOf(true);
            }
            return null;
        }

        protected void onProgressUpdate(Object... progress) {
            ARSALPrint.m530d(ARDiscoveryJmdnsDiscovery.TAG, "onProgressUpdate");
            Pair<ServiceEvent, eARDISCOVERY_SERVICE_EVENT_STATUS> dataProgress = progress[0];
            switch ((eARDISCOVERY_SERVICE_EVENT_STATUS) dataProgress.second) {
                case ARDISCOVERY_SERVICE_EVENT_STATUS_ADD:
                    ARSALPrint.m530d(ARDiscoveryJmdnsDiscovery.TAG, "ARDISCOVERY_SERVICE_EVENT_STATUS_ADD");
                    ARDiscoveryJmdnsDiscovery.this.notificationNetServiceDeviceAdd((ServiceEvent) dataProgress.first);
                    return;
                case ARDISCOVERY_SERVICE_EVENT_STATUS_RESOLVED:
                    ARSALPrint.m530d(ARDiscoveryJmdnsDiscovery.TAG, "ARDISCOVERY_SERVICE_EVENT_STATUS_RESOLVED");
                    ARDiscoveryJmdnsDiscovery.this.notificationNetServicesDevicesResolved((ServiceEvent) dataProgress.first);
                    return;
                case ARDISCOVERY_SERVICE_EVENT_STATUS_REMOVED:
                    ARSALPrint.m530d(ARDiscoveryJmdnsDiscovery.TAG, "ARDISCOVERY_SERVICE_EVENT_STATUS_REMOVED");
                    ARDiscoveryJmdnsDiscovery.this.notificationNetServiceDeviceRemoved((ServiceEvent) dataProgress.first);
                    return;
                default:
                    String access$500 = ARDiscoveryJmdnsDiscovery.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("error service event status ");
                    stringBuilder.append(dataProgress.second);
                    stringBuilder.append(" not known");
                    ARSALPrint.m530d(access$500, stringBuilder.toString());
                    return;
            }
        }

        protected void onPostExecute(Object result) {
            for (String devicesService : ARDiscoveryJmdnsDiscovery.this.devicesServices.keySet()) {
                String access$500 = ARDiscoveryJmdnsDiscovery.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("addServiceListener:");
                stringBuilder.append(devicesService);
                ARSALPrint.m530d(access$500, stringBuilder.toString());
                if (ARDiscoveryJmdnsDiscovery.this.mDNSManager != null) {
                    ARDiscoveryJmdnsDiscovery.this.mDNSManager.addServiceListener(devicesService, ARDiscoveryJmdnsDiscovery.this.mDNSListener);
                } else {
                    ARSALPrint.m538w(ARDiscoveryJmdnsDiscovery.TAG, "mDNSManager is null");
                }
            }
        }
    }

    public ARDiscoveryJmdnsDiscovery(Set<ARDISCOVERY_PRODUCT_ENUM> supportedProducts) {
        try {
            nullAddress = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.hostAddress = nullAddress;
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
        this.networkStateChangedFilter = new IntentFilter();
        this.networkStateChangedFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.networkStateIntentReceiver = new C15841();
    }

    public synchronized void open(ARDiscoveryService broadcaster, Context c) {
        this.broadcaster = broadcaster;
        this.context = c;
        if (!this.opened) {
            this.netDeviceServicesHmap = new HashMap();
            this.context.registerReceiver(this.networkStateIntentReceiver, this.networkStateChangedFilter);
            this.opened = true;
        }
    }

    public synchronized void close() {
        if (this.opened) {
            this.context.unregisterReceiver(this.networkStateIntentReceiver);
            mdnsDisconnect();
            this.broadcaster = null;
            this.context = null;
            this.opened = false;
        }
    }

    private void mdnsDestroy() {
        ARSALPrint.m530d(TAG, "mdnsDestroy");
        synchronized (this.mJmDNSLock) {
            if (this.mDNSManager != null) {
                if (this.mDNSListener != null) {
                    for (String devicesService : this.devicesServices.keySet()) {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("removeServiceListener:");
                        stringBuilder.append(devicesService);
                        ARSALPrint.m530d(str, stringBuilder.toString());
                        this.mDNSManager.removeServiceListener(devicesService, this.mDNSListener);
                    }
                    this.mDNSListener = null;
                }
                try {
                    this.mDNSManager.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.mDNSManager = null;
            }
        }
    }

    private void update() {
        ConnectivityManager connManager = (ConnectivityManager) this.context.getSystemService("connectivity");
        NetworkInfo mWifi = connManager.getNetworkInfo(1);
        NetworkInfo mEth = connManager.getNetworkInfo(9);
        if ((mWifi == null || !mWifi.isConnected()) && (mEth == null || !mEth.isConnected())) {
            if (this.isNetDiscovering.booleanValue()) {
                this.askForNetDiscovering = Boolean.valueOf(true);
            }
            mdnsDisconnect();
            Iterator it = new ArrayList(this.netDeviceServicesHmap.values()).iterator();
            while (it.hasNext()) {
                notificationNetServiceDeviceRemoved((ARDiscoveryDeviceService) it.next());
            }
        } else if (this.askForNetDiscovering.booleanValue()) {
            mdnsConnect();
            this.askForNetDiscovering = Boolean.valueOf(false);
        }
    }

    public void start() {
        if (!this.isNetDiscovering.booleanValue()) {
            if (((ConnectivityManager) this.context.getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
                mdnsConnect();
            } else {
                this.askForNetDiscovering = Boolean.valueOf(true);
            }
        }
    }

    public void stop() {
        if (this.isNetDiscovering.booleanValue()) {
            mdnsDisconnect();
        }
    }

    public void wifiAvailable(boolean wifiAvailable) {
    }

    private void mdnsConnect() {
        if (this.mDNSManager == null) {
            WifiManager wifi = (WifiManager) this.context.getSystemService("wifi");
            if (wifi != null) {
                try {
                    this.hostIp = Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress());
                    this.hostAddress = InetAddress.getByName(this.hostIp);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("hostIp: ");
                stringBuilder.append(this.hostIp);
                ARSALPrint.m530d(str, stringBuilder.toString());
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("hostAddress: ");
                stringBuilder.append(this.hostAddress);
                ARSALPrint.m530d(str, stringBuilder.toString());
            }
            if (!this.isNetDiscovering.booleanValue()) {
                this.isNetDiscovering = Boolean.valueOf(true);
                this.jmdnsCreatorAsyncTask = new JmdnsCreatorAsyncTask();
                this.jmdnsCreatorAsyncTask.execute(new Object[0]);
            }
        }
    }

    private void mdnsDisconnect() {
        this.hostAddress = nullAddress;
        mdnsDestroy();
        this.jmdnsCreatorAsyncTask = null;
        this.isNetDiscovering = Boolean.valueOf(false);
    }

    private void notificationNetServiceDeviceAdd(ServiceEvent serviceEvent) {
        String ip = null;
        int port = 0;
        String txtRecord = null;
        synchronized (this.mJmDNSLock) {
            if (this.mDNSManager != null) {
                ip = getServiceIP(serviceEvent);
                port = getServicePort(serviceEvent);
                txtRecord = getServiceTxtRecord(serviceEvent);
            }
        }
        if (ip != null) {
            ARDiscoveryDeviceNetService aRDiscoveryDeviceNetService = new ARDiscoveryDeviceNetService(serviceEvent.getName(), serviceEvent.getType(), ip, port, txtRecord);
            ARDISCOVERY_PRODUCT_ENUM product = (ARDISCOVERY_PRODUCT_ENUM) this.devicesServices.get(aRDiscoveryDeviceNetService.getType());
            if (product != null) {
                ARDiscoveryDeviceService deviceService = new ARDiscoveryDeviceService(serviceEvent.getName(), aRDiscoveryDeviceNetService, ARDiscoveryService.nativeGetProductID(product.getValue()));
                this.netDeviceServicesHmap.put(deviceService.getName(), deviceService);
                if (this.broadcaster != null) {
                    this.broadcaster.broadcastDeviceServiceArrayUpdated();
                }
                return;
            }
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Found an unknown service : ");
            stringBuilder.append(aRDiscoveryDeviceNetService);
            ARSALPrint.m530d(str, stringBuilder.toString());
        }
    }

    private void notificationNetServicesDevicesResolved(ServiceEvent serviceEvent) {
        ARSALPrint.m530d(TAG, "notificationServicesDevicesResolved");
        if (!this.netDeviceServicesHmap.containsKey(serviceEvent.getName())) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service Resolved not know : ");
            stringBuilder.append(serviceEvent);
            ARSALPrint.m530d(str, stringBuilder.toString());
            notificationNetServiceDeviceAdd(serviceEvent);
        }
    }

    private void notificationNetServiceDeviceRemoved(ServiceEvent serviceEvent) {
        ARSALPrint.m530d(TAG, "notificationServiceDeviceRemoved");
        if (((ARDiscoveryDeviceService) this.netDeviceServicesHmap.remove(serviceEvent.getName())) == null || this.broadcaster == null) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service: ");
            stringBuilder.append(serviceEvent.getInfo().getName());
            stringBuilder.append(" not known");
            ARSALPrint.m538w(str, stringBuilder.toString());
            return;
        }
        this.broadcaster.broadcastDeviceServiceArrayUpdated();
    }

    private void notificationNetServiceDeviceRemoved(ARDiscoveryDeviceService deviceService) {
        ARSALPrint.m530d(TAG, "notificationServiceDeviceRemoved");
        if (((ARDiscoveryDeviceService) this.netDeviceServicesHmap.remove(deviceService.getName())) == null || this.broadcaster == null) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service: ");
            stringBuilder.append(deviceService.getName());
            stringBuilder.append(" not known");
            ARSALPrint.m538w(str, stringBuilder.toString());
            return;
        }
        this.broadcaster.broadcastDeviceServiceArrayUpdated();
    }

    private String getServiceIP(ServiceEvent serviceEvent) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getServiceIP serviceEvent: ");
        stringBuilder.append(serviceEvent);
        ARSALPrint.m530d(str, stringBuilder.toString());
        ServiceInfo info = null;
        try {
            info = serviceEvent.getDNS().getServiceInfo(serviceEvent.getType(), serviceEvent.getName());
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
        if (info == null || info.getInet4Addresses().length <= 0) {
            return null;
        }
        return info.getInet4Addresses()[0].getHostAddress();
    }

    private String getServiceTxtRecord(ServiceEvent serviceEvent) {
        ServiceInfo info = null;
        try {
            info = serviceEvent.getDNS().getServiceInfo(serviceEvent.getType(), serviceEvent.getName());
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
        if (info == null || info.getTextBytes() == null) {
            return null;
        }
        byte[] data = info.getTextBytes();
        try {
            return new String(data, 1, data[0]);
        } catch (IndexOutOfBoundsException e2) {
            return null;
        }
    }

    private int getServicePort(ServiceEvent serviceEvent) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getServicePort serviceEvent: ");
        stringBuilder.append(serviceEvent);
        ARSALPrint.m530d(str, stringBuilder.toString());
        ServiceInfo info = null;
        try {
            info = serviceEvent.getDNS().getServiceInfo(serviceEvent.getType(), serviceEvent.getName());
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
        if (info != null) {
            return info.getPort();
        }
        return 0;
    }

    public List<ARDiscoveryDeviceService> getDeviceServicesArray() {
        return new ArrayList(this.netDeviceServicesHmap.values());
    }
}
