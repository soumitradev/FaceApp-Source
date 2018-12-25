package org.catrobat.catroid.drone.jumpingsumo;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DICTIONARY_KEY_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_ERROR_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerArgumentDictionary;
import com.parrot.arsdk.arcontroller.ARControllerCodec;
import com.parrot.arsdk.arcontroller.ARControllerDictionary;
import com.parrot.arsdk.arcontroller.ARControllerException;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import com.parrot.arsdk.arcontroller.ARDeviceControllerListener;
import com.parrot.arsdk.arcontroller.ARDeviceControllerStreamListener;
import com.parrot.arsdk.arcontroller.ARFeatureCommon;
import com.parrot.arsdk.arcontroller.ARFrame;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDevice;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceNetService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.arsdk.ardiscovery.ARDiscoveryException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDiscoverer.Listener;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDiscoverer.ListenerPicture;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.stage.StageActivity;

public class JumpingSumoInitializer {
    private static final int CONNECTION_TIME = 10000;
    private static final List<ARDiscoveryDeviceService> DRONELIST = new ArrayList();
    private static final int JUMPING_SUMO_BATTERY_THRESHOLD = 3;
    private static final String TAG = JumpingSumoInitializer.class.getSimpleName();
    private static JumpingSumoInitializer instance;
    private static int jumpingSumoCount = 0;
    private ARDeviceController deviceController;
    private final ARDeviceControllerListener deviceControllerListener = new C21026();
    private ARCONTROLLER_DEVICE_STATE_ENUM deviceState = ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_STOPPED;
    private final Listener discovererListener = new C21002();
    private final Handler handler = new Handler(CatroidApplication.getAppContext().getMainLooper());
    public JumpingSumoDiscoverer jsDiscoverer;
    private boolean messageShown = false;
    public final ListenerPicture pictureListener = new C21013();
    private PreStageActivity prestageStageActivity;
    private StageActivity stageActivity = null;
    private final ARDeviceControllerStreamListener streamListener = new C21037();

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer$1 */
    class C18321 implements Runnable {
        C18321() {
        }

        public void run() {
            if (JumpingSumoInitializer.jumpingSumoCount == 0) {
                JumpingSumoInitializer.showUnCancellableErrorDialog(JumpingSumoInitializer.this.prestageStageActivity, JumpingSumoInitializer.this.prestageStageActivity.getString(R.string.error_no_jumpingsumo_connected_title), JumpingSumoInitializer.this.prestageStageActivity.getString(R.string.error_no_jumpingsumo_connected));
            } else {
                JumpingSumoInitializer.this.prestageStageActivity.resourceInitialized();
            }
        }
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer$2 */
    class C21002 implements Listener {
        C21002() {
        }

        public void onDronesListUpdated(List<ARDiscoveryDeviceService> dronesList) {
            JumpingSumoInitializer.DRONELIST.clear();
            JumpingSumoInitializer.DRONELIST.addAll(dronesList);
            String access$300 = JumpingSumoInitializer.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("JumpingSumo: ");
            stringBuilder.append(dronesList.size());
            stringBuilder.append(" Drones found");
            Log.d(access$300, stringBuilder.toString());
            JumpingSumoInitializer.jumpingSumoCount = dronesList.size();
            if (JumpingSumoInitializer.jumpingSumoCount > 0) {
                ARDiscoveryDeviceService service = (ARDiscoveryDeviceService) dronesList.get(0);
                JumpingSumoInitializer.this.deviceController = JumpingSumoInitializer.this.createDeviceController(JumpingSumoInitializer.this.createDiscoveryDevice(service, ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_JS));
                ARCONTROLLER_DEVICE_STATE_ENUM state = ARCONTROLLER_DEVICE_STATE_ENUM.eARCONTROLLER_DEVICE_STATE_UNKNOWN_ENUM_VALUE;
                ARCONTROLLER_ERROR_ENUM error = JumpingSumoInitializer.this.deviceController.start();
                try {
                    state = JumpingSumoInitializer.this.deviceController.getState();
                } catch (ARControllerException e) {
                    String access$3002 = JumpingSumoInitializer.TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Exception ");
                    stringBuilder2.append(e);
                    Log.e(access$3002, stringBuilder2.toString());
                }
                if (error != ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
                    String access$3003 = JumpingSumoInitializer.TAG;
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Exception ");
                    stringBuilder3.append(error);
                    Log.e(access$3003, stringBuilder3.toString());
                    access$3003 = JumpingSumoInitializer.TAG;
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("State: ");
                    stringBuilder3.append(state);
                    Log.d(access$3003, stringBuilder3.toString());
                }
                JumpingSumoDeviceController.getInstance().setDeviceController(JumpingSumoInitializer.this.deviceController);
                JumpingSumoInitializer.this.jsDiscoverer.getInfoDevice(service);
            }
        }
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer$3 */
    class C21013 implements ListenerPicture {
        C21013() {
        }

        public void onPictureCount(int pictureCount) {
        }

        public void onMatchingMediasFound(int matchingMedias) {
        }

        public void onDownloadProgressed(String mediaName, int progress) {
        }

        public void onDownloadComplete(String mediaName) {
            Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            stringBuilder.append("/JumpingSumo/");
            stringBuilder.append(mediaName);
            mediaScanIntent.setData(Uri.fromFile(new File(stringBuilder.toString())));
            CatroidApplication.getAppContext().sendBroadcast(mediaScanIntent);
        }
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer$6 */
    class C21026 implements ARDeviceControllerListener {
        C21026() {
        }

        public void onStateChanged(ARDeviceController deviceController, ARCONTROLLER_DEVICE_STATE_ENUM newState, ARCONTROLLER_ERROR_ENUM error) {
            JumpingSumoInitializer.this.deviceState = newState;
            if (JumpingSumoInitializer.this.deviceState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING)) {
                JumpingSumoInitializer.this.jsDiscoverer.removeListener(JumpingSumoInitializer.this.discovererListener);
            } else if (JumpingSumoInitializer.this.deviceState.equals(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_STOPPED)) {
                Log.e(JumpingSumoInitializer.TAG, "Jumping Sumo Connection Lost");
                JumpingSumoInitializer.this.onConnectionLost(JumpingSumoInitializer.this.stageActivity);
            }
        }

        public void onExtensionStateChanged(ARDeviceController deviceController, ARCONTROLLER_DEVICE_STATE_ENUM newState, ARDISCOVERY_PRODUCT_ENUM product, String name, ARCONTROLLER_ERROR_ENUM error) {
        }

        public void onCommandReceived(ARDeviceController deviceController, ARCONTROLLER_DICTIONARY_KEY_ENUM commandKey, ARControllerDictionary elementDictionary) {
            if (commandKey == ARCONTROLLER_DICTIONARY_KEY_ENUM.ARCONTROLLER_DICTIONARY_KEY_COMMON_COMMONSTATE_BATTERYSTATECHANGED && elementDictionary != null) {
                ARControllerArgumentDictionary<Object> args = elementDictionary.get(ARControllerDictionary.ARCONTROLLER_DICTIONARY_SINGLE_KEY);
                if (args != null) {
                    final int battery = ((Integer) args.get(ARFeatureCommon.f1427xd58cdac0)).intValue();
                    JumpingSumoInitializer.this.handler.post(new Runnable() {
                        public void run() {
                            JumpingSumoInitializer.this.notifyBatteryChanged(battery);
                        }
                    });
                }
            }
        }
    }

    /* renamed from: org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer$7 */
    class C21037 implements ARDeviceControllerStreamListener {
        C21037() {
        }

        public ARCONTROLLER_ERROR_ENUM configureDecoder(ARDeviceController deviceController, ARControllerCodec codec) {
            JumpingSumoInitializer.this.notifyConfigureDecoder(codec);
            return ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        }

        public ARCONTROLLER_ERROR_ENUM onFrameReceived(ARDeviceController deviceController, ARFrame frame) {
            return ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        }

        public void onFrameTimeout(ARDeviceController deviceController) {
        }
    }

    public static JumpingSumoInitializer getInstance() {
        if (instance == null) {
            instance = new JumpingSumoInitializer();
        }
        return instance;
    }

    public void setPreStageActivity(PreStageActivity prestageStageActivity) {
        this.prestageStageActivity = prestageStageActivity;
    }

    public boolean disconnect() {
        this.jsDiscoverer.removeListener(this.discovererListener);
        if (this.deviceController == null || this.deviceController.stop() != ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK) {
            return false;
        }
        JumpingSumoDeviceController.getInstance().setDeviceController(null);
        return true;
    }

    public void initialise() {
        this.jsDiscoverer = new JumpingSumoDiscoverer(CatroidApplication.getAppContext());
        if (checkRequirements()) {
            this.jsDiscoverer.setup();
            this.jsDiscoverer.addListener(this.discovererListener);
            this.jsDiscoverer.addListenerPicture(this.pictureListener);
        }
    }

    public void checkJumpingSumoAvailability(PreStageActivity prestageStageActivityNow) {
        setPreStageActivity(prestageStageActivityNow);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JumpSumo Count: ");
        stringBuilder.append(jumpingSumoCount);
        Log.d(str, stringBuilder.toString());
        new Handler().postDelayed(new C18321(), 10000);
    }

    private void notifyConfigureDecoder(ARControllerCodec codec) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Codec ");
        stringBuilder.append(codec.getType());
        Log.d(str, stringBuilder.toString());
    }

    public void setStageActivity(StageActivity stageActivity) {
        this.stageActivity = stageActivity;
    }

    private void notifyBatteryChanged(int battery) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Jumping Sumo Battery: ");
        stringBuilder.append(battery);
        Log.d(str, stringBuilder.toString());
        if (battery < 3 && !this.messageShown) {
            this.messageShown = true;
            if (!(this.stageActivity instanceof StageActivity) || this.stageActivity == null) {
                checkJumpingSumoAvailability(this.prestageStageActivity);
                Log.e(TAG, "Jumping Sumo Battery too low");
                return;
            }
            showUnCancellableErrorDialog(this.stageActivity, this.stageActivity.getString(R.string.error_jumpingsumo_battery_title), this.stageActivity.getString(R.string.error_jumpingsumo_battery));
            Log.e(TAG, "Jumping Sumo Battery too low");
        }
    }

    private ARDeviceController createDeviceController(@NonNull ARDiscoveryDevice discoveryDevice) {
        ARDeviceController deviceController = null;
        try {
            deviceController = new ARDeviceController(discoveryDevice);
            deviceController.addListener(this.deviceControllerListener);
            deviceController.addStreamListener(this.streamListener);
        } catch (ARControllerException e) {
            Log.e(TAG, "Exception", e);
        }
        return deviceController;
    }

    private ARDiscoveryDevice createDiscoveryDevice(@NonNull ARDiscoveryDeviceService service, ARDISCOVERY_PRODUCT_ENUM productType) {
        ARDiscoveryDevice device = null;
        try {
            device = new ARDiscoveryDevice();
            ARDiscoveryDeviceNetService netDeviceService = (ARDiscoveryDeviceNetService) service.getDevice();
            device.initWifi(productType, netDeviceService.getName(), netDeviceService.getIp(), netDeviceService.getPort());
        } catch (ARDiscoveryException e) {
            Log.e(TAG, "Exception", e);
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error: ");
            stringBuilder.append(e.getError());
            Log.e(str, stringBuilder.toString());
        }
        return device;
    }

    public boolean checkRequirements() {
        if (CatroidApplication.loadSDKLib()) {
            return true;
        }
        showUnCancellableErrorDialog(this.prestageStageActivity, this.prestageStageActivity.getString(R.string.error_jumpingsumo_wrong_platform_title), this.prestageStageActivity.getString(R.string.error_jumpingsumo_wrong_platform));
        return false;
    }

    private void onConnectionLost(StageActivity context) {
        if ((this.stageActivity instanceof StageActivity) && this.stageActivity != null) {
            context.jsDestroy();
        }
    }

    public static void showUnCancellableErrorDialog(final StageActivity context, String title, String message) {
        AlertDialog$Builder builder = new AlertDialog$Builder(context);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setNeutralButton(R.string.close, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                context.jumpingSumoDisconnect();
                context.jsDestroy();
            }
        });
        builder.show();
    }

    public void takePicture() {
        if (this.deviceController != null) {
            this.deviceController.getFeatureJumpingSumo().sendMediaRecordPicture((byte) 0);
        }
    }

    public void getLastFlightMedias() {
        this.jsDiscoverer.notifyPic();
        this.jsDiscoverer.download();
    }

    public static void showUnCancellableErrorDialog(final PreStageActivity context, String title, String message) {
        AlertDialog$Builder builder = new AlertDialog$Builder(context);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setNeutralButton(R.string.close, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                context.resourceFailed();
            }
        });
        builder.show();
    }
}
