package com.parrot.freeflight.drone;

import android.content.Context;
import android.content.Intent;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.parrot.freeflight.utils.FileUtils;
import java.io.File;
import org.catrobat.catroid.common.BrickValues;

public class DroneProxy {
    public static final String DRONE_PROXY_CONFIG_CHANGED_ACTION = "drone.proxy.config.changed.action";
    public static final String DRONE_PROXY_CONNECTED_ACTION = "drone.proxy.connected.action";
    public static final String DRONE_PROXY_CONNECTION_FAILED_ACTION = "drone.proxy.connection.failed";
    public static final String DRONE_PROXY_DISCONNECTED_ACTION = "drone.proxy.disconnected.action";
    private static final String TAG = "DroneProxy";
    private static volatile DroneProxy instance;
    private String DCIMdirPath = null;
    private DroneAcademyMediaListener academyMediaListener;
    private Context applicationContext;
    private DroneConfig config = new DroneConfig();
    private NavData navdata = new NavData();

    public enum ARDRONE_LED_ANIMATION {
        ARDRONE_LED_ANIMATION_BLINK_GREEN_RED,
        ARDRONE_LED_ANIMATION_BLINK_GREEN,
        ARDRONE_LED_ANIMATION_BLINK_RED,
        ARDRONE_LED_ANIMATION_BLINK_ORANGE,
        ARDRONE_LED_ANIMATION_SNAKE_GREEN_RED,
        ARDRONE_LED_ANIMATION_FIRE,
        ARDRONE_LED_ANIMATION_STANDARD,
        ARDRONE_LED_ANIMATION_RED,
        ARDRONE_LED_ANIMATION_GREEN,
        ARDRONE_LED_ANIMATION_RED_SNAKE,
        ARDRONE_LED_ANIMATION_BLANK,
        ARDRONE_LED_ANIMATION_RIGHT_MISSILE,
        ARDRONE_LED_ANIMATION_LEFT_MISSILE,
        ARDRONE_LED_ANIMATION_DOUBLE_MISSILE,
        ARDRONE_LED_ANIMATION_FRONT_LEFT_GREEN_OTHERS_RED,
        ARDRONE_LED_ANIMATION_FRONT_RIGHT_GREEN_OTHERS_RED,
        ARDRONE_LED_ANIMATION_REAR_RIGHT_GREEN_OTHERS_RED,
        ARDRONE_LED_ANIMATION_REAR_LEFT_GREEN_OTHERS_RED,
        ARDRONE_LED_ANIMATION_LEFT_GREEN_RIGHT_RED,
        ARDRONE_LED_ANIMATION_LEFT_RED_RIGHT_GREEN,
        ARDRONE_LED_ANIMATION_BLINK_STANDARD
    }

    public enum DroneProgressiveCommandFlag {
        ARDRONE_PROGRESSIVE_CMD_ENABLE,
        ARDRONE_PROGRESSIVE_CMD_COMBINED_YAW_ACTIVE,
        ARDRONE_MAGNETO_CMD_ENABLE
    }

    public enum EVideoRecorderCapability {
        NOT_SUPPORTED,
        VIDEO_360P,
        VIDEO_720P
    }

    private native void connect(String str, String str2, String str3, String str4, int i, int i2);

    private native void disconnect();

    private native void pause();

    private native void resume();

    private native DroneConfig takeConfigSnapshot(DroneConfig droneConfig);

    private native NavData takeNavDataSnapshot(NavData navData);

    public native void calibrateMagneto();

    public native void doFlip();

    public native void flatTrimNative();

    public native void initNavdata();

    public native void playLedAnimation(float f, int i, int i2);

    public native void record();

    public native void resetConfigToDefaults();

    public native void setCommandFlag(int i, boolean z);

    public native void setControlValue(int i, float f);

    public native void setDefaultConfigurationNative();

    public native void setDeviceOrientation(int i, int i2);

    public native void setLocation(double d, double d2, double d3);

    public native void setMagnetoEnabled(boolean z);

    public native void switchCamera();

    public native void takePhoto();

    public native void triggerConfigUpdateNative();

    public native void triggerEmergency();

    public native void triggerTakeOff();

    public static DroneProxy getInstance(Context appContext) {
        if (instance == null) {
            instance = new DroneProxy(appContext);
        } else {
            instance.setAppContext(appContext);
        }
        return instance;
    }

    public DroneProxy(Context appContext) {
    }

    public void doConnect(Context context, EVideoRecorderCapability recordVideoResolution) {
        Exception e;
        DroneProxy droneProxy;
        try {
            Log.d(TAG, "Connecting...");
            String uid = Secure.getString(context.getContentResolver(), "android_id");
            String packageName = getClass().getPackage().getName();
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder("AppName: ");
            stringBuilder.append(packageName);
            stringBuilder.append(", UserID: ");
            stringBuilder.append(uid);
            Log.d(str, stringBuilder.toString());
            File dcimDir = FileUtils.getMediaFolder(context);
            if (dcimDir != null) {
                try {
                    this.DCIMdirPath = dcimDir.getAbsolutePath();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                }
            }
            droneProxy = this;
            File cacheDir = context.getExternalCacheDir();
            File mediaDir = dcimDir;
            if (cacheDir == null) {
                cacheDir = context.getCacheDir();
            }
            if (mediaDir == null) {
                Log.w(TAG, "Cache/Media dir is unavailable.");
                droneProxy.connect(packageName.trim(), uid.trim(), cacheDir.getAbsolutePath(), cacheDir.getAbsolutePath(), 0, EVideoRecorderCapability.NOT_SUPPORTED.ordinal());
                return;
            }
            StatFs stat = new StatFs(cacheDir.getPath());
            long blockSize = (long) stat.getBlockSize();
            long availableBlocks = (long) stat.getAvailableBlocks();
            long usableSpace = blockSize * availableBlocks;
            int spaceToUse = (int) Math.round((((double) usableSpace) * BrickValues.SET_COLOR_TO) / 1048576.0d);
            droneProxy.connect(packageName.trim(), uid.trim(), cacheDir.getAbsolutePath(), mediaDir.getAbsolutePath(), spaceToUse, recordVideoResolution.ordinal());
        } catch (Exception e3) {
            e = e3;
            droneProxy = this;
            e.printStackTrace();
        }
    }

    public void doDisconnect() {
        disconnect();
    }

    public NavData getNavdata() {
        return this.navdata;
    }

    public void doPause() {
        pause();
    }

    public void doResume() {
        resume();
    }

    public void updateNavdata() {
        this.navdata = takeNavDataSnapshot(this.navdata);
    }

    public void onConnected() {
        LocalBroadcastManager.getInstance(this.applicationContext).sendBroadcast(new Intent(DRONE_PROXY_CONNECTED_ACTION));
    }

    public void onDisconnected() {
        LocalBroadcastManager.getInstance(this.applicationContext).sendBroadcast(new Intent(DRONE_PROXY_DISCONNECTED_ACTION));
    }

    public void onConnectionFailed(int reason) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder("OnConnectionFailed. Reason: ");
        stringBuilder.append(reason);
        Log.w(str, stringBuilder.toString());
        LocalBroadcastManager.getInstance(this.applicationContext).sendBroadcast(new Intent(DRONE_PROXY_CONNECTION_FAILED_ACTION));
    }

    public void onConfigChanged() {
        Log.d(TAG, "Drone config changed");
        LocalBroadcastManager.getInstance(this.applicationContext).sendBroadcast(new Intent(DRONE_PROXY_CONFIG_CHANGED_ACTION));
    }

    public DroneConfig getConfig() {
        this.config = takeConfigSnapshot(this.config);
        return this.config;
    }

    public void setAcademyMediaListener(DroneAcademyMediaListener listener) {
        this.academyMediaListener = listener;
    }

    private static void onConnectedStatic() {
        Log.d(TAG, "onConnectedStatic called");
    }

    public void onAcademyNewMediaReady(String path, boolean addToQueue) {
        if (path != null) {
            if (path.length() != 0) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder("New media file available: ");
                stringBuilder.append(path);
                Log.d(str, stringBuilder.toString());
                boolean isFileOk = false;
                File file = new File(path);
                if (file.exists() && file.isFile()) {
                    if (0 < file.length()) {
                        isFileOk = true;
                    } else {
                        Log.d(TAG, "New media has a size of zero --> delete it");
                        file.delete();
                    }
                } else if (!file.exists()) {
                    String str2 = TAG;
                    StringBuilder stringBuilder2 = new StringBuilder("File ");
                    stringBuilder2.append(path);
                    stringBuilder2.append(" doesn't exists but reported as new media");
                    Log.w(str2, stringBuilder2.toString());
                }
                if (isFileOk && this.academyMediaListener != null) {
                    if (addToQueue) {
                        this.academyMediaListener.onNewMediaToQueue(path);
                        return;
                    } else {
                        this.academyMediaListener.onNewMediaIsAvailable(path);
                        return;
                    }
                }
                return;
            }
        }
        if (this.academyMediaListener != null) {
            this.academyMediaListener.onQueueComplete();
        }
    }

    private void setAppContext(Context appContext) {
        this.applicationContext = appContext;
    }
}
