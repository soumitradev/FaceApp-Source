package org.catrobat.catroid.drone.jumpingsumo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceNetService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryService.LocalBinder;
import com.parrot.arsdk.ardiscovery.receivers.ARDiscoveryServicesDevicesListUpdatedReceiver;
import com.parrot.arsdk.ardiscovery.receivers.ARDiscoveryServicesDevicesListUpdatedReceiverDelegate;
import com.parrot.arsdk.arutils.ARUtilsException;
import com.parrot.arsdk.arutils.ARUtilsManager;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.CatroidApplication;

public class JumpingSumoDiscoverer {
    private static final int DEVICE_PORT = 21;
    private static final String TAG = JumpingSumoDiscoverer.class.getSimpleName();
    private ARDiscoveryService ardiscoveryService;
    private ServiceConnection ardiscoveryServiceConnection;
    private final ARDiscoveryServicesDevicesListUpdatedReceiver ardiscoveryServicesDevicesListUpdatedReceiver;
    private final Context context;
    private final ARDiscoveryServicesDevicesListUpdatedReceiverDelegate discoveryListener = new C20983();
    private ARUtilsManager ftplistModule;
    private ARUtilsManager ftpqueueManager;
    private final Handler handler = new Handler(CatroidApplication.getAppContext().getMainLooper());
    private final List<ListenerPicture> listenerPictures;
    private final List<Listener> listeners;
    private final List<ARDiscoveryDeviceService> matchingDrones;
    private SDCardModule sdcardModule;
    private final org.catrobat.catroid.drone.jumpingsumo.SDCardModule.Listener sdcardModulelistener = new C20994();
    private boolean startDiscoveryAfterConnection = true;

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDiscoverer$1 */
    class C18271 implements ServiceConnection {
        C18271() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            JumpingSumoDiscoverer.this.ardiscoveryService = ((LocalBinder) service).getService();
            if (JumpingSumoDiscoverer.this.startDiscoveryAfterConnection) {
                JumpingSumoDiscoverer.this.startDiscovering();
                JumpingSumoDiscoverer.this.startDiscoveryAfterConnection = false;
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            JumpingSumoDiscoverer.this.ardiscoveryService = null;
        }
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDiscoverer$2 */
    class C18282 implements Runnable {
        C18282() {
        }

        public void run() {
            JumpingSumoDiscoverer.this.ardiscoveryService.stop();
            JumpingSumoDiscoverer.this.context.unbindService(JumpingSumoDiscoverer.this.ardiscoveryServiceConnection);
            JumpingSumoDiscoverer.this.ardiscoveryService = null;
        }
    }

    public interface Listener {
        void onDronesListUpdated(List<ARDiscoveryDeviceService> list);
    }

    public interface ListenerPicture {
        void onDownloadComplete(String str);

        void onDownloadProgressed(String str, int i);

        void onMatchingMediasFound(int i);

        void onPictureCount(int i);
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDiscoverer$3 */
    class C20983 implements ARDiscoveryServicesDevicesListUpdatedReceiverDelegate {
        C20983() {
        }

        public void onServicesDevicesListUpdated() {
            if (JumpingSumoDiscoverer.this.ardiscoveryService != null) {
                JumpingSumoDiscoverer.this.matchingDrones.clear();
                List<ARDiscoveryDeviceService> deviceList = JumpingSumoDiscoverer.this.ardiscoveryService.getDeviceServicesArray();
                if (deviceList != null) {
                    for (ARDiscoveryDeviceService service : deviceList) {
                        JumpingSumoDiscoverer.this.matchingDrones.add(service);
                    }
                }
                JumpingSumoDiscoverer.this.notifyServiceDiscovered(JumpingSumoDiscoverer.this.matchingDrones);
            }
        }
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDiscoverer$4 */
    class C20994 implements org.catrobat.catroid.drone.jumpingsumo.SDCardModule.Listener {
        C20994() {
        }

        public void onMatchingMediasFound(final int matchingMedias) {
            JumpingSumoDiscoverer.this.handler.post(new Runnable() {
                public void run() {
                    JumpingSumoDiscoverer.this.notifyMatchingMediasFound(matchingMedias);
                }
            });
        }

        public void onDownloadProgressed(final String mediaName, final int progress) {
            JumpingSumoDiscoverer.this.handler.post(new Runnable() {
                public void run() {
                    JumpingSumoDiscoverer.this.notifyDownloadProgressed(mediaName, progress);
                }
            });
        }

        public void onDownloadComplete(final String mediaName) {
            JumpingSumoDiscoverer.this.handler.post(new Runnable() {
                public void run() {
                    JumpingSumoDiscoverer.this.notifyDownloadComplete(mediaName);
                }
            });
            JumpingSumoDiscoverer.this.onDeleteFile(mediaName);
        }
    }

    public JumpingSumoDiscoverer(Context contextDrone) {
        this.context = contextDrone;
        this.listeners = new ArrayList();
        this.matchingDrones = new ArrayList();
        this.ardiscoveryServicesDevicesListUpdatedReceiver = new ARDiscoveryServicesDevicesListUpdatedReceiver(this.discoveryListener);
        this.listenerPictures = new ArrayList();
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
        notifyServiceDiscovered(this.matchingDrones);
    }

    public void addListenerPicture(ListenerPicture listenerPicture) {
        this.listenerPictures.add(listenerPicture);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public void setup() {
        LocalBroadcastManager.getInstance(this.context).registerReceiver(this.ardiscoveryServicesDevicesListUpdatedReceiver, new IntentFilter(ARDiscoveryService.kARDiscoveryServiceNotificationServicesDevicesListUpdated));
        if (this.ardiscoveryServiceConnection == null) {
            this.ardiscoveryServiceConnection = new C18271();
        }
        if (this.ardiscoveryService == null) {
            this.context.bindService(new Intent(this.context, ARDiscoveryService.class), this.ardiscoveryServiceConnection, 1);
        }
    }

    public void cleanup() {
        stopDiscovering();
        if (this.ardiscoveryService != null) {
            new Thread(new C18282()).start();
        }
        LocalBroadcastManager.getInstance(this.context).unregisterReceiver(this.ardiscoveryServicesDevicesListUpdatedReceiver);
    }

    public void startDiscovering() {
        if (this.ardiscoveryService != null) {
            this.discoveryListener.onServicesDevicesListUpdated();
            this.ardiscoveryService.start();
            this.startDiscoveryAfterConnection = false;
            return;
        }
        this.startDiscoveryAfterConnection = true;
    }

    public void stopDiscovering() {
        if (this.ardiscoveryService != null) {
            this.ardiscoveryService.stop();
        }
        this.startDiscoveryAfterConnection = false;
    }

    private void notifyServiceDiscovered(List<ARDiscoveryDeviceService> dronesList) {
        for (Listener listener : new ArrayList(this.listeners)) {
            listener.onDronesListUpdated(dronesList);
        }
    }

    public void getInfoDevice(@NonNull ARDiscoveryDeviceService deviceService) {
        try {
            this.ftplistModule = new ARUtilsManager();
            this.ftpqueueManager = new ARUtilsManager();
            String productIP = ((ARDiscoveryDeviceNetService) deviceService.getDevice()).getIp();
            this.ftplistModule.initWifiFtp(productIP, 21, "anonymous", "");
            this.ftpqueueManager.initWifiFtp(productIP, 21, "anonymous", "");
            this.sdcardModule = new SDCardModule(this.ftplistModule, this.ftpqueueManager);
            this.sdcardModule.addListener(this.sdcardModulelistener);
            notifyPictureCount(this.sdcardModule.getPictureCount());
        } catch (ARUtilsException e) {
            Log.e(TAG, "Exception", e);
        }
    }

    public void download() {
        this.sdcardModule.getallFlightMedias();
    }

    public void notifyPic() {
        notifyPictureCount(this.sdcardModule.getPictureCount());
    }

    public void onDeleteFile(String mediaName) {
        this.sdcardModule.deleteLastReceivedPic(mediaName);
        notifyPictureCount(this.sdcardModule.getPictureCount());
    }

    private void notifyPictureCount(int pictureCount) {
        for (ListenerPicture listener : new ArrayList(this.listenerPictures)) {
            listener.onPictureCount(pictureCount);
        }
    }

    private void notifyMatchingMediasFound(int matchingMedias) {
        for (ListenerPicture listener : new ArrayList(this.listenerPictures)) {
            listener.onMatchingMediasFound(matchingMedias);
        }
    }

    private void notifyDownloadProgressed(String mediaName, int progress) {
        for (ListenerPicture listener : new ArrayList(this.listenerPictures)) {
            listener.onDownloadProgressed(mediaName, progress);
        }
    }

    private void notifyDownloadComplete(String mediaName) {
        for (ListenerPicture listener : new ArrayList(this.listenerPictures)) {
            listener.onDownloadComplete(mediaName);
        }
    }
}
