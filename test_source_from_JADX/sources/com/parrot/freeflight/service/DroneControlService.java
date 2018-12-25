package com.parrot.freeflight.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.media.ExifInterface;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.parrot.arsdk.armedia.ARMediaManager;
import com.parrot.freeflight.drone.DroneAcademyMediaListener;
import com.parrot.freeflight.drone.DroneConfig;
import com.parrot.freeflight.drone.DroneConfig.EDroneVersion;
import com.parrot.freeflight.drone.DroneProxy;
import com.parrot.freeflight.drone.DroneProxy.DroneProgressiveCommandFlag;
import com.parrot.freeflight.drone.NavData;
import com.parrot.freeflight.service.commands.DroneServiceCommand;
import com.parrot.freeflight.service.intents.DroneStateManager;
import com.parrot.freeflight.service.listeners.DroneDebugListener;
import com.parrot.freeflight.service.states.ConnectedServiceState;
import com.parrot.freeflight.service.states.DisconnectedServiceState;
import com.parrot.freeflight.service.states.PausedServiceState;
import com.parrot.freeflight.tasks.MoveFileTask;
import com.parrot.freeflight.utils.ARDroneMediaGallery;
import com.parrot.freeflight.utils.FTPUtils;
import com.parrot.freeflight.utils.FileUtils;
import com.parrot.freeflight.utils.GPSHelper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import name.antonsmirnov.firmata.FormatHelper;

public class DroneControlService extends Service implements DroneControlServiceInterface, Runnable, DroneAcademyMediaListener, LocationListener {
    public static final String CAMERA_READY_CHANGED_ACTION = "com.parrot.camera.ready.changed";
    private static final int CONTROL_SET_GAZ = 1;
    private static final int CONTROL_SET_PITCH = 2;
    private static final int CONTROL_SET_ROLL = 3;
    private static final int CONTROL_SET_YAW = 0;
    public static final String DRONE_BATTERY_CHANGED_ACTION = "com.parrot.battery.changed";
    public static final String DRONE_CONFIG_STATE_CHANGED_ACTION = "com.parrot.config.changed";
    public static final String DRONE_CONNECTION_CHANGED_ACTION = "com.parrot.drone.connection.changed";
    public static final String DRONE_EMERGENCY_STATE_CHANGED_ACTION = "com.parrot.emergency.changed";
    public static final String DRONE_FIRMWARE_CHECK_ACTION = "com.parrot.firmware.checked";
    public static final String DRONE_FLYING_STATE_CHANGED_ACTION = "com.parrot.flying.changed";
    public static final String DRONE_STATE_READY_ACTION = "com.parrot.drone.ready";
    public static final String EXTRA_CAMERA_READY = "com.parrot.extra.camera.ready";
    public static final String EXTRA_CONNECTION_STATE = "connection.state";
    public static final String EXTRA_DRONE_BATTERY = "com.parrot.battery.extra.value";
    public static final String EXTRA_DRONE_FLYING = "com.parrot.flying.extra";
    public static final String EXTRA_EMERGENCY_CODE = "com.parrot.emergency.extra.code";
    public static final String EXTRA_FIRMWARE_UPDATE_REQUIRED = "updateRequired";
    public static final String EXTRA_MEDIA_PATH = "controlservice.media.path";
    public static final String EXTRA_RECORDING_STATE = "com.parrot.recording.extra.state";
    public static final String EXTRA_RECORD_READY = "com.parrot.extra.record.ready";
    public static final String EXTRA_USB_ACTIVE = "com.parrot.extra.usbactive";
    public static final String EXTRA_USB_REMAINING_TIME = "com.parrot.extra.usbremaining";
    public static final String NEW_MEDIA_IS_AVAILABLE_ACTION = "com.parrot.controlservice.media.available";
    public static final float POWER = 0.2f;
    public static final String RECORD_READY_CHANGED_ACTION = "com.parrot.record.ready.changed";
    private static final String TAG = "DroneControlService";
    public static final String VIDEO_RECORDING_STATE_CHANGED_ACTION = "com.parrot.recording.changed";
    private final IBinder binder = new LocalBinder();
    private Queue<DroneServiceCommand> commandQueue;
    private Object commandQueueLock = new Object();
    private Object configLock;
    private ServiceStateBase currState;
    private DroneDebugListener debugListener;
    private DroneProxy droneProxy;
    private EDroneVersion droneVersion;
    private ARDroneMediaGallery gallery;
    private HashMap<String, Intent> intentCache;
    private ArrayList<String> mediaDownloaded;
    private Object navDataLock;
    private Object navdataThreadLock;
    private Runnable navdataUpdateRunnable = new C16311();
    private Thread navdataUpdateThread;
    private NavData prevNavData;
    private long prevVideoFrames;
    private long startTime;
    private boolean stopThreads;
    private boolean usbActive;
    private Thread workerThread;
    private Object workerThreadLock;

    /* renamed from: com.parrot.freeflight.service.DroneControlService$1 */
    class C16311 implements Runnable {
        C16311() {
        }

        public void run() {
            DroneControlService.this.droneProxy.initNavdata();
            while (!DroneControlService.this.stopThreads) {
                DroneControlService.this.droneProxy.updateNavdata();
                NavData navData = DroneControlService.this.droneProxy.getNavdata();
                if (navData.emergencyState != DroneControlService.this.prevNavData.emergencyState) {
                    DroneControlService.this.onEmergencyStateChanged(navData.emergencyState);
                }
                if (navData.batteryStatus != DroneControlService.this.prevNavData.batteryStatus) {
                    DroneControlService.this.onBatteryStateChanged(navData.batteryStatus);
                }
                if (!(navData.recording == DroneControlService.this.prevNavData.recording && navData.usbRemainingTime == DroneControlService.this.prevNavData.usbRemainingTime && navData.usbActive == DroneControlService.this.prevNavData.usbActive && navData.cameraReady == DroneControlService.this.prevNavData.cameraReady && navData.recordReady == DroneControlService.this.prevNavData.recordReady && navData.flying == DroneControlService.this.prevNavData.flying)) {
                    DroneControlService.this.onRecordChanged(navData.recording, navData.usbActive, navData.usbRemainingTime);
                    DroneControlService.this.onCameraReadyChanged(navData.cameraReady);
                    DroneControlService.this.onRecordReadyChanged(navData.recordReady);
                    if (navData.flying) {
                        DroneControlService.this.onTookOff();
                    } else {
                        DroneControlService.this.onLanded();
                    }
                    if (navData.recording != DroneControlService.this.prevNavData.recording && navData.recording && navData.usbActive && navData.usbRemainingTime == 0 && DroneControlService.this.droneProxy.getConfig().isRecordOnUsb()) {
                        Log.i(DroneControlService.TAG, "Not enough space left on USB drive. Stopping recording.");
                        DroneControlService.this.droneProxy.record();
                    }
                }
                if (navData.initialized != DroneControlService.this.prevNavData.initialized && navData.initialized) {
                    DroneControlService.this.onDroneReady();
                }
                if (DroneControlService.this.debugListener != null) {
                    if (DroneControlService.this.prevVideoFrames == 0) {
                        DroneControlService.this.startTime = System.currentTimeMillis();
                        DroneControlService.this.prevVideoFrames = (long) navData.numFrames;
                    } else {
                        float fps = ((float) (navData.numFrames - DroneControlService.this.prevNavData.numFrames)) / (((float) (System.currentTimeMillis() - DroneControlService.this.startTime)) / 1000.0f);
                        DroneControlService.this.startTime = System.currentTimeMillis();
                        DroneControlService.this.debugListener.onShowFps((int) fps);
                    }
                }
                DroneControlService.this.prevNavData.copyFrom(navData);
                try {
                    Thread.sleep(100);
                    if ((DroneControlService.this.currState instanceof PausedServiceState) && !DroneControlService.this.stopThreads) {
                        synchronized (DroneControlService.this.navdataThreadLock) {
                            try {
                                DroneControlService.this.navdataThreadLock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }
    }

    public class LocalBinder extends Binder {
        public DroneControlService getService() {
            return DroneControlService.this;
        }
    }

    public native void setControls(float f, float f2, float f3, float f4, int i, int i2);

    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    public void onCreate() {
        super.onCreate();
        this.droneVersion = EDroneVersion.UNKNOWN;
        this.configLock = new Object();
        this.workerThreadLock = new Object();
        this.navdataThreadLock = new Object();
        this.droneProxy = DroneProxy.getInstance(getApplicationContext());
        this.stopThreads = false;
        this.prevNavData = new NavData();
        this.workerThread = new Thread(this, "Drone Worker Thread");
        this.navdataUpdateThread = new Thread(this.navdataUpdateRunnable, "Navdata Update Thread");
        this.commandQueue = new LinkedList();
        setState(new DisconnectedServiceState(this));
        this.workerThread.start();
        this.gallery = new ARDroneMediaGallery(this);
        this.mediaDownloaded = new ArrayList();
        initIntents();
        connect();
    }

    public void onDestroy() {
        super.onDestroy();
        disconnect();
        Log.d(TAG, "All threads have been stopped");
        stopWorkerThreads();
    }

    private void initIntents() {
        this.intentCache = new HashMap(11);
        this.intentCache.put(VIDEO_RECORDING_STATE_CHANGED_ACTION, new Intent(VIDEO_RECORDING_STATE_CHANGED_ACTION));
        this.intentCache.put(DRONE_EMERGENCY_STATE_CHANGED_ACTION, new Intent(DRONE_EMERGENCY_STATE_CHANGED_ACTION));
        this.intentCache.put(DRONE_FLYING_STATE_CHANGED_ACTION, new Intent(DRONE_FLYING_STATE_CHANGED_ACTION));
        this.intentCache.put(DRONE_BATTERY_CHANGED_ACTION, new Intent(DRONE_BATTERY_CHANGED_ACTION));
        this.intentCache.put(DRONE_FIRMWARE_CHECK_ACTION, new Intent(DRONE_FIRMWARE_CHECK_ACTION));
        this.intentCache.put(DRONE_STATE_READY_ACTION, new Intent(DRONE_STATE_READY_ACTION));
        this.intentCache.put(DRONE_CONNECTION_CHANGED_ACTION, new Intent(DRONE_CONNECTION_CHANGED_ACTION));
        this.intentCache.put(NEW_MEDIA_IS_AVAILABLE_ACTION, new Intent(NEW_MEDIA_IS_AVAILABLE_ACTION));
        this.intentCache.put(DRONE_CONFIG_STATE_CHANGED_ACTION, new Intent(DRONE_CONFIG_STATE_CHANGED_ACTION));
        this.intentCache.put(RECORD_READY_CHANGED_ACTION, new Intent(RECORD_READY_CHANGED_ACTION));
        this.intentCache.put(CAMERA_READY_CHANGED_ACTION, new Intent(CAMERA_READY_CHANGED_ACTION));
        this.intentCache.put(DroneStateManager.ACTION_DRONE_STATE_CHANGED, new Intent(DroneStateManager.ACTION_DRONE_STATE_CHANGED));
    }

    protected void connect() {
        this.currState.connect();
    }

    protected void disconnect() {
        this.currState.disconnect();
    }

    public void pause() {
        this.currState.pause();
    }

    public void resume() {
        this.currState.resume();
        setPitch(0.0f);
        setRoll(0.0f);
        setGaz(0.0f);
        setYaw(0.0f);
        setDeviceOrientation(0, 0);
    }

    public void triggerTakeOff() {
        this.droneProxy.triggerTakeOff();
    }

    public void calibrateMagneto() {
        this.droneProxy.calibrateMagneto();
    }

    public void doLeftFlip() {
        this.droneProxy.doFlip();
    }

    public void triggerEmergency() {
        this.droneProxy.triggerEmergency();
    }

    public void triggerConfigUpdate() {
        this.droneProxy.triggerConfigUpdateNative();
    }

    public void turnLeft(float power) {
        this.droneProxy.setControlValue(0, -power);
    }

    public void turnRight(float power) {
        this.droneProxy.setControlValue(0, power);
    }

    public void moveForward(float power) {
        this.droneProxy.setControlValue(2, -power);
    }

    public void moveBackward(float power) {
        this.droneProxy.setControlValue(2, power);
    }

    public void moveUp(float power) {
        this.droneProxy.setControlValue(1, power);
    }

    public void moveDown(float power) {
        this.droneProxy.setControlValue(1, -power);
    }

    public void moveLeft(float power) {
        this.droneProxy.setControlValue(3, -power);
    }

    public void moveRight(float power) {
        this.droneProxy.setControlValue(3, power);
    }

    public void switchCamera() {
        this.droneProxy.switchCamera();
    }

    public DroneConfig getDroneConfig() {
        DroneConfig config;
        synchronized (this.configLock) {
            config = this.droneProxy.getConfig();
        }
        return new DroneConfig(config);
    }

    public NavData getDroneNavData() {
        return this.droneProxy.getNavdata();
    }

    public void requestNavDataUpdate() {
        this.droneProxy.updateNavdata();
    }

    public void resetConfigToDefaults() {
        this.droneProxy.resetConfigToDefaults();
    }

    public void requestConfigUpdate() {
        this.droneProxy.triggerConfigUpdateNative();
    }

    public void takePhoto() {
        this.droneProxy.takePhoto();
    }

    public void record() {
        this.droneProxy.record();
    }

    public boolean isMediaStorageAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public void flatTrim() {
        this.droneProxy.flatTrimNative();
    }

    public void playLedAnimation(float frequency, int duration, int animationMode) {
        this.droneProxy.playLedAnimation(frequency, duration, animationMode);
    }

    public void setGaz(float value) {
        this.droneProxy.setControlValue(1, value);
    }

    public void setRoll(float value) {
        this.droneProxy.setControlValue(3, value);
    }

    public void setPitch(float value) {
        this.droneProxy.setControlValue(2, value);
    }

    public void setYaw(float value) {
        this.droneProxy.setControlValue(0, value);
    }

    public void setDeviceOrientation(int heading, int accuracy) {
        this.droneProxy.setDeviceOrientation(heading, accuracy);
    }

    public void setDroneDebugListener(DroneDebugListener listener) {
        this.debugListener = listener;
    }

    public void onCommandFinished(DroneServiceCommand command) {
        this.currState.onCommandFinished(command);
    }

    public void onLowMemory() {
        Log.w(TAG, "Low memory alert!");
        super.onLowMemory();
    }

    protected void setState(ServiceStateBase state) {
        if (!(this.currState == null || state == null)) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("== PREV STATE: ");
            stringBuilder.append(this.currState.getStateName());
            stringBuilder.append(" NEW STATE: ");
            stringBuilder.append(state.getStateName());
            Log.d(str, stringBuilder.toString());
        }
        if (this.currState != null) {
            this.currState.onFinalize();
        }
        this.currState = state;
        if (state != null) {
            state.onPrepare();
        }
    }

    protected void onConnected() {
        Log.d(TAG, "====>>> DRONE CONTROL SERVICE CONNECTED");
        if (this.droneVersion == EDroneVersion.UNKNOWN) {
            this.droneVersion = getDroneVersion();
        }
        this.droneProxy.setDefaultConfigurationNative();
        if (!(this.navdataUpdateThread == null || this.navdataUpdateThread.isAlive())) {
            this.navdataUpdateThread.start();
        }
        Intent intent = (Intent) this.intentCache.get(DRONE_CONNECTION_CHANGED_ACTION);
        intent.putExtra(EXTRA_CONNECTION_STATE, "connected");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void onDisconnected() {
        synchronized (this.navdataThreadLock) {
            this.navdataThreadLock.notify();
        }
        Log.d(TAG, "====>>> DRONE CONTROL SERVICE DISCONNECTED");
        Intent intent = (Intent) this.intentCache.get(DRONE_CONNECTION_CHANGED_ACTION);
        intent.putExtra(EXTRA_CONNECTION_STATE, "disconnected");
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void onPaused() {
        Log.d(TAG, "====>>> DRONE CONTROL SERVICE PAUSED");
    }

    protected void onResumed() {
        synchronized (this.navdataThreadLock) {
            this.navdataThreadLock.notify();
        }
        Log.d(TAG, "====>>> DRONE CONTROL SERVICE RESUMED");
    }

    protected void onTookOff() {
        Intent intent = (Intent) this.intentCache.get(DRONE_FLYING_STATE_CHANGED_ACTION);
        intent.putExtra(EXTRA_DRONE_FLYING, true);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void onLanded() {
        Intent intent = (Intent) this.intentCache.get(DRONE_FLYING_STATE_CHANGED_ACTION);
        intent.putExtra(EXTRA_DRONE_FLYING, false);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    protected void onBatteryStateChanged(int batteryStatus) {
        Intent intent = (Intent) this.intentCache.get(DRONE_BATTERY_CHANGED_ACTION);
        intent.putExtra(EXTRA_DRONE_BATTERY, batteryStatus);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void onConfigStateChanged() {
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast((Intent) this.intentCache.get(DRONE_CONFIG_STATE_CHANGED_ACTION));
    }

    private void onEmergencyStateChanged(int emergency) {
        Intent intent = (Intent) this.intentCache.get(DRONE_EMERGENCY_STATE_CHANGED_ACTION);
        intent.putExtra(EXTRA_EMERGENCY_CODE, emergency);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void onRecordChanged(boolean inProgress, boolean usbActive, int remaining) {
        this.usbActive = usbActive;
        Intent intent = (Intent) this.intentCache.get(VIDEO_RECORDING_STATE_CHANGED_ACTION);
        intent.putExtra(EXTRA_USB_ACTIVE, usbActive);
        intent.putExtra(EXTRA_RECORDING_STATE, inProgress);
        intent.putExtra(EXTRA_USB_REMAINING_TIME, remaining);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void onRecordReadyChanged(boolean recordReady) {
        Intent intent = (Intent) this.intentCache.get(RECORD_READY_CHANGED_ACTION);
        intent.putExtra(EXTRA_RECORD_READY, recordReady);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void onCameraReadyChanged(boolean cameraReady) {
        Intent intent = (Intent) this.intentCache.get(CAMERA_READY_CHANGED_ACTION);
        intent.putExtra(EXTRA_CAMERA_READY, cameraReady);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void onDroneReady() {
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast((Intent) this.intentCache.get(DRONE_STATE_READY_ACTION));
    }

    protected void startCommand(DroneServiceCommand cmd) {
        synchronized (this.commandQueue) {
            this.commandQueue.add(cmd);
        }
        synchronized (this.commandQueueLock) {
            this.commandQueueLock.notify();
        }
    }

    protected void stopWorkerThreads() {
        this.stopThreads = true;
        synchronized (this.navdataThreadLock) {
            this.navdataThreadLock.notify();
        }
        try {
            this.navdataUpdateThread.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this.commandQueueLock) {
            this.commandQueueLock.notify();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r5 = this;
    L_0x0000:
        r0 = r5.stopThreads;
        if (r0 == 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r0 = r5.commandQueueLock;
        monitor-enter(r0);
        r1 = r5.commandQueue;	 Catch:{ InterruptedException -> 0x006d }
        r1 = r1.isEmpty();	 Catch:{ InterruptedException -> 0x006d }
        if (r1 == 0) goto L_0x001a;
    L_0x0010:
        r1 = r5.stopThreads;	 Catch:{ InterruptedException -> 0x006d }
        if (r1 != 0) goto L_0x001a;
    L_0x0014:
        r1 = r5.commandQueueLock;	 Catch:{ InterruptedException -> 0x006d }
        r1.wait();	 Catch:{ InterruptedException -> 0x006d }
    L_0x001a:
        r1 = r5.stopThreads;	 Catch:{ all -> 0x006b }
        if (r1 == 0) goto L_0x0020;
    L_0x001e:
        monitor-exit(r0);	 Catch:{ all -> 0x006b }
        return;
    L_0x0020:
        monitor-exit(r0);	 Catch:{ all -> 0x006b }
        r0 = 0;
        r1 = r5.commandQueue;
        monitor-enter(r1);
        r2 = r5.commandQueue;	 Catch:{ all -> 0x0068 }
        r2 = r2.poll();	 Catch:{ all -> 0x0068 }
        r2 = (com.parrot.freeflight.service.commands.DroneServiceCommand) r2;	 Catch:{ all -> 0x0068 }
        r0 = r2;
        monitor-exit(r1);	 Catch:{ all -> 0x0068 }
        if (r0 == 0) goto L_0x0000;
    L_0x0031:
        r1 = r5.workerThreadLock;	 Catch:{ Exception -> 0x003c }
        monitor-enter(r1);	 Catch:{ Exception -> 0x003c }
        r0.execute();	 Catch:{ all -> 0x0039 }
        monitor-exit(r1);	 Catch:{ all -> 0x0039 }
        goto L_0x0000;
    L_0x0039:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0039 }
        throw r2;	 Catch:{ Exception -> 0x003c }
    L_0x003c:
        r1 = move-exception;
        r2 = "DroneControlService";
        r3 = new java.lang.StringBuilder;
        r4 = "Commang ";
        r3.<init>(r4);
        r4 = r0.getClass();
        r4 = r4.getSimpleName();
        r3.append(r4);
        r4 = " has failed with exception ";
        r3.append(r4);
        r4 = r1.toString();
        r3.append(r4);
        r3 = r3.toString();
        android.util.Log.e(r2, r3);
        r1.printStackTrace();
        goto L_0x0000;
    L_0x0068:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0068 }
        throw r2;
    L_0x006b:
        r1 = move-exception;
        goto L_0x0070;
    L_0x006d:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x006b }
        return;
    L_0x0070:
        monitor-exit(r0);	 Catch:{ all -> 0x006b }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.parrot.freeflight.service.DroneControlService.run():void");
    }

    public void setMagnetoEnabled(boolean absoluteControlEnabled) {
        this.droneProxy.setMagnetoEnabled(absoluteControlEnabled);
    }

    public boolean isDroneConnected() {
        return (this.currState instanceof ConnectedServiceState) || (this.currState instanceof PausedServiceState);
    }

    public void requestDroneStatus() {
        onBatteryStateChanged(this.prevNavData.batteryStatus);
        onRecordChanged(this.prevNavData.recording, this.prevNavData.usbActive, this.prevNavData.usbRemainingTime);
        onCameraReadyChanged(this.prevNavData.cameraReady);
        onRecordReadyChanged(this.prevNavData.recordReady);
        onEmergencyStateChanged(this.prevNavData.emergencyState);
        if (this.prevNavData.initialized) {
            onDroneReady();
        }
    }

    public EDroneVersion getDroneVersion() {
        if (this.droneVersion == EDroneVersion.UNKNOWN) {
            EDroneVersion version = getDroneConfig().getDroneVersion();
            if (version == EDroneVersion.UNKNOWN) {
                String strVersion = FTPUtils.downloadFile(this, DroneConfig.getHost(), DroneConfig.getFtpPort(), "version.txt");
                if (strVersion != null && strVersion.startsWith("1.")) {
                    return EDroneVersion.DRONE_1;
                }
                if (strVersion != null && strVersion.startsWith("2.")) {
                    return EDroneVersion.DRONE_2;
                }
            }
            this.droneVersion = version;
        }
        return this.droneVersion;
    }

    public File getMediaDir() {
        return FileUtils.getMediaFolder(this);
    }

    public void setProgressiveCommandEnabled(boolean b) {
        this.droneProxy.setCommandFlag(DroneProgressiveCommandFlag.ARDRONE_PROGRESSIVE_CMD_ENABLE.ordinal(), b);
    }

    public void setProgressiveCommandCombinedYawEnabled(boolean b) {
        this.droneProxy.setCommandFlag(DroneProgressiveCommandFlag.ARDRONE_PROGRESSIVE_CMD_COMBINED_YAW_ACTIVE.ordinal(), b);
    }

    @SuppressLint({"NewApi"})
    public void onNewMediaIsAvailable(String path) {
        final File file = new File(path);
        if (path.endsWith(ARMediaManager.ARMEDIA_MANAGER_JPG)) {
            try {
                ExifInterface eif = new ExifInterface(path);
                String dir_file = file.getParentFile().getName();
                String dir_date = new StringBuilder(String.valueOf(dir_file.substring(6, 10)));
                dir_date.append(":");
                dir_date.append(dir_file.substring(10, 12));
                dir_date.append(":");
                dir_date.append(dir_file.substring(12, 14));
                dir_date.append(FormatHelper.SPACE);
                dir_date.append(dir_file.substring(15, 17));
                dir_date.append(":");
                dir_date.append(dir_file.substring(17, 19));
                dir_date.append(":");
                dir_date.append(dir_file.substring(19));
                eif.setAttribute("GPSTimeStamp", dir_date.toString());
                eif.saveAttributes();
            } catch (IOException ioe) {
                Log.w(TAG, "Error opening exif interface", ioe);
            }
        }
        File dcimDir = getMediaDir();
        if (dcimDir != null) {
            File newFile = new File(dcimDir, file.getName());
            new MoveFileTask() {
                protected void onPostExecute(Boolean result) {
                    if (result.equals(Boolean.TRUE)) {
                        file.getParentFile().delete();
                        File newFile = getResultFile();
                        DroneControlService.this.gallery.insertMedia(newFile);
                        Intent intent = (Intent) DroneControlService.this.intentCache.get(DroneControlService.NEW_MEDIA_IS_AVAILABLE_ACTION);
                        intent.putExtra(DroneControlService.EXTRA_MEDIA_PATH, newFile.getAbsolutePath());
                        LocalBroadcastManager.getInstance(DroneControlService.this.getApplicationContext()).sendBroadcast(intent);
                    }
                }
            }.execute(new File[]{file, newFile});
        }
    }

    public void onNewMediaToQueue(String path) {
        this.mediaDownloaded.add(path);
    }

    public void onQueueComplete() {
        Iterator it = this.mediaDownloaded.iterator();
        while (it.hasNext()) {
            onNewMediaIsAvailable((String) it.next());
        }
        this.mediaDownloaded.clear();
    }

    public void onLocationChanged(Location location) {
        if (location.hasAltitude() && location.hasAccuracy() && location.getAccuracy() < 100.0f) {
            this.droneProxy.setLocation(location.getLatitude(), location.getLongitude(), location.getAltitude());
            GPSHelper.getInstance(this).stopListening(this);
            return;
        }
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder("Skipped location value as it doesn't have desired accuracy. Accuracy: ");
        stringBuilder.append(location.getAccuracy());
        stringBuilder.append(" meters");
        Log.d(str, stringBuilder.toString());
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public boolean isUSBInserted() {
        return this.usbActive;
    }
}
