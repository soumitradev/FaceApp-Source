package org.catrobat.catroid.stage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARControllerException;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService.ConnectDeviceResult;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.drone.ardrone.DroneInitializer;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoServiceWrapper;
import org.catrobat.catroid.facedetection.FaceDetectionHandler;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.sensing.GatherCollisionInformationTask;
import org.catrobat.catroid.sensing.GatherCollisionInformationTask.OnPolygonLoadedListener;
import org.catrobat.catroid.ui.BaseActivity;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.FlashUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.TouchUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.utils.VibratorUtil;

public class PreStageActivity extends BaseActivity implements OnPolygonLoadedListener {
    private static final int REQUEST_CONNECT_DEVICE = 1000;
    public static final int REQUEST_GPS = 1;
    public static final int REQUEST_RESOURCES_INIT = 101;
    private static final String TAG = PreStageActivity.class.getSimpleName();
    private static OnUtteranceCompletedListenerContainer onUtteranceCompletedListenerContainer;
    private static TextToSpeech textToSpeech;
    private DroneInitializer droneInitializer = null;
    private Set<Integer> failedResources;
    private JumpingSumoInitializer jumpingSumoInitializer = null;
    private int requiredResourceCounter;
    private ResourcesSet requiredResourcesSet;
    private Intent returnToActivityIntent = null;

    /* renamed from: org.catrobat.catroid.stage.PreStageActivity$1 */
    class C18801 implements OnInitListener {

        /* renamed from: org.catrobat.catroid.stage.PreStageActivity$1$1 */
        class C18781 implements OnClickListener {
            C18781() {
            }

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                PreStageActivity.this.resourceFailed(1);
            }
        }

        /* renamed from: org.catrobat.catroid.stage.PreStageActivity$1$2 */
        class C18792 implements OnClickListener {
            C18792() {
            }

            public void onClick(DialogInterface dialog, int id) {
                Intent installIntent = new Intent();
                installIntent.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
                PreStageActivity.this.startActivity(installIntent);
                PreStageActivity.this.resourceFailed(1);
            }
        }

        C18801() {
        }

        public void onInit(int status) {
            if (status == 0) {
                PreStageActivity.onUtteranceCompletedListenerContainer = new OnUtteranceCompletedListenerContainer();
                PreStageActivity.textToSpeech.setOnUtteranceCompletedListener(PreStageActivity.onUtteranceCompletedListenerContainer);
                PreStageActivity.this.resourceInitialized();
                return;
            }
            AlertDialog$Builder builder = new AlertDialog$Builder(PreStageActivity.this);
            builder.setMessage(R.string.prestage_text_to_speech_engine_not_installed).setCancelable(false).setPositiveButton(R.string.yes, new C18792()).setNegativeButton(R.string.no, new C18781());
            builder.create().show();
        }
    }

    /* renamed from: org.catrobat.catroid.stage.PreStageActivity$2 */
    class C18812 implements OnClickListener {
        C18812() {
        }

        public void onClick(DialogInterface dialog, int id) {
            PreStageActivity.this.nfcInitialize();
        }
    }

    /* renamed from: org.catrobat.catroid.stage.PreStageActivity$3 */
    class C18823 implements OnDismissListener {
        C18823() {
        }

        public void onDismiss(DialogInterface dialog) {
            PreStageActivity.this.resourceFailed();
        }
    }

    /* renamed from: org.catrobat.catroid.stage.PreStageActivity$4 */
    class C18834 implements OnClickListener {
        C18834() {
        }

        public void onClick(DialogInterface dialog, int which) {
            PreStageActivity.this.resourceFailed();
        }
    }

    /* renamed from: org.catrobat.catroid.stage.PreStageActivity$5 */
    class C18845 implements OnClickListener {
        C18845() {
        }

        public void onClick(DialogInterface dialog, int which) {
            PreStageActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
        }
    }

    /* renamed from: org.catrobat.catroid.stage.PreStageActivity$6 */
    class C18856 implements OnClickListener {
        C18856() {
        }

        public void onClick(DialogInterface dialog, int id) {
            PreStageActivity.this.resourceFailed();
        }
    }

    /* renamed from: org.catrobat.catroid.stage.PreStageActivity$7 */
    class C18867 implements OnClickListener {
        C18867() {
        }

        public void onClick(DialogInterface dialog, int id) {
            PreStageActivity.this.resourceFailed();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.returnToActivityIntent = new Intent();
        if (!isFinishing()) {
            setContentView(R.layout.activity_prestage);
            TouchUtil.reset();
            SensorHandler sensorHandler = SensorHandler.getInstance(getApplicationContext());
            this.failedResources = new HashSet();
            this.requiredResourcesSet = ProjectManager.getInstance().getCurrentProject().getRequiredResources();
            this.requiredResourceCounter = this.requiredResourcesSet.size();
            if (this.requiredResourcesSet.contains(Integer.valueOf(13))) {
                if (sensorHandler.accelerationAvailable()) {
                    resourceInitialized();
                } else {
                    resourceFailed(13);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(14))) {
                if (sensorHandler.inclinationAvailable()) {
                    resourceInitialized();
                } else {
                    resourceFailed(14);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(15))) {
                if (sensorHandler.compassAvailable()) {
                    resourceInitialized();
                } else {
                    resourceFailed(15);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(18))) {
                if (SensorHandler.gpsAvailable()) {
                    resourceInitialized();
                } else {
                    Intent checkIntent = new Intent();
                    checkIntent.setAction("android.settings.LOCATION_SOURCE_SETTINGS");
                    startActivityForResult(checkIntent, 1);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(1))) {
                textToSpeech = new TextToSpeech(this, new C18801());
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(2))) {
                connectBTDevice(BluetoothDevice.LEGO_NXT);
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(20))) {
                connectBTDevice(BluetoothDevice.LEGO_EV3);
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(10))) {
                connectBTDevice(BluetoothDevice.PHIRO);
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(6))) {
                connectBTDevice(BluetoothDevice.ARDUINO);
            }
            if (DroneServiceWrapper.checkARDroneAvailability()) {
                CatroidApplication.loadNativeLibs();
                if (CatroidApplication.parrotLibrariesLoaded) {
                    this.droneInitializer = getDroneInitialiser();
                    this.droneInitializer.initialise();
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(23))) {
                CatroidApplication.loadSDKLib();
                if (CatroidApplication.parrotJSLibrariesLoaded) {
                    JumpingSumoServiceWrapper.initJumpingSumo(this);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(11))) {
                if (CameraManager.getInstance().hasBackCamera()) {
                    resourceInitialized();
                } else {
                    resourceFailed(11);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(12))) {
                if (CameraManager.getInstance().hasFrontCamera()) {
                    resourceInitialized();
                } else {
                    resourceFailed(12);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(17))) {
                if (!CameraManager.getInstance().hasFrontCamera()) {
                    if (!CameraManager.getInstance().hasBackCamera()) {
                        resourceFailed(17);
                    }
                }
                resourceInitialized();
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(8))) {
                flashInitialize();
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(9))) {
                if (((Vibrator) getSystemService("vibrator")) != null) {
                    VibratorUtil.setContext(getBaseContext());
                    VibratorUtil.activateVibratorThread();
                    resourceInitialized();
                } else {
                    resourceFailed(9);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(16))) {
                if (this.requiredResourcesSet.contains(Integer.valueOf(4))) {
                    AlertDialog$Builder builder = new AlertDialog$Builder(this);
                    builder.setMessage(getString(R.string.nfc_facedetection_support)).setCancelable(false).setPositiveButton(getString(R.string.ok), new C18812());
                    builder.create().show();
                } else {
                    nfcInitialize();
                }
            }
            FaceDetectionHandler.resetFaceDedection();
            if (this.requiredResourcesSet.contains(Integer.valueOf(4))) {
                if (FaceDetectionHandler.startFaceDetection()) {
                    resourceInitialized();
                } else {
                    resourceFailed(4);
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(22))) {
                if (CastManager.getInstance().isConnected()) {
                    resourceInitialized();
                } else {
                    if (!SettingsFragment.isCastSharedPreferenceEnabled(this)) {
                        ToastUtil.showError((Context) this, getString(R.string.cast_enable_cast_feature));
                    } else if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                        ToastUtil.showError((Context) this, getString(R.string.cast_error_not_connected_msg));
                    } else {
                        ToastUtil.showError((Context) this, getString(R.string.cast_error_cast_bricks_in_no_cast_project));
                    }
                    resourceFailed();
                }
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(19))) {
                new GatherCollisionInformationTask(this).execute(new Void[0]);
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(21))) {
                if (Utils.isNetworkAvailable(this)) {
                    resourceInitialized();
                } else {
                    new AlertDialog$Builder(this).setTitle(R.string.error_no_network_title).setPositiveButton(R.string.preference_title, new C18845()).setNegativeButton(R.string.cancel, new C18834()).setOnDismissListener(new C18823()).create().show();
                }
            }
            if (this.requiredResourceCounter == 0) {
                startStageActivity();
            }
            if (this.requiredResourcesSet.contains(Integer.valueOf(7))) {
                RaspberryPiService.getInstance().enableRaspberryInterruptPinsForProject(ProjectManager.getInstance().getCurrentProject());
                connectRaspberrySocket();
            }
        }
    }

    private void connectBTDevice(Class<? extends BluetoothDevice> service) {
        if (((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).connectDevice(service, this, 1000) == ConnectDeviceResult.ALREADY_CONNECTED) {
            resourceInitialized();
        }
    }

    private void connectRaspberrySocket() {
        if (RaspberryPiService.getInstance().connect(SettingsFragment.getRaspiHost(getBaseContext()), SettingsFragment.getRaspiPort(getBaseContext()))) {
            resourceInitialized();
            return;
        }
        ToastUtil.showError((Context) this, getString(R.string.error_connecting_to, new Object[]{SettingsFragment.getRaspiHost(getBaseContext()), Integer.valueOf(SettingsFragment.getRaspiPort(getBaseContext()))}));
        resourceFailed();
    }

    public DroneInitializer getDroneInitialiser() {
        if (this.droneInitializer == null) {
            this.droneInitializer = new DroneInitializer(this);
        }
        return this.droneInitializer;
    }

    public JumpingSumoInitializer getJumpingSumoInitialiser() {
        if (this.jumpingSumoInitializer == null) {
            this.jumpingSumoInitializer = JumpingSumoInitializer.getInstance();
            this.jumpingSumoInitializer.setPreStageActivity(this);
        }
        return this.jumpingSumoInitializer;
    }

    public void onResume() {
        if (this.droneInitializer != null) {
            this.droneInitializer.onPrestageActivityResume();
        }
        super.onResume();
        if (this.requiredResourceCounter == 0 && this.failedResources.isEmpty()) {
            Log.d(TAG, "onResume()");
            finish();
        }
    }

    protected void onPause() {
        if (this.droneInitializer != null) {
            this.droneInitializer.onPrestageActivityPause();
        }
        super.onPause();
    }

    protected void onDestroy() {
        if (this.droneInitializer != null) {
            this.droneInitializer.onPrestageActivityDestroy();
        }
        super.onDestroy();
    }

    public static void shutdownResources() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).pause();
        if (FaceDetectionHandler.isFaceDetectionRunning()) {
            FaceDetectionHandler.stopFaceDetection();
        }
        if (VibratorUtil.isActive()) {
            VibratorUtil.pauseVibrator();
        }
        RaspberryPiService.getInstance().disconnect();
    }

    public static void shutdownPersistentResources() {
        ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).disconnectDevices();
        deleteSpeechFiles();
        if (FlashUtil.isAvailable()) {
            FlashUtil.destroy();
        }
        if (VibratorUtil.isActive()) {
            VibratorUtil.destroy();
        }
    }

    private static void deleteSpeechFiles() {
        File pathToSpeechFiles = new File(Constants.TEXT_TO_SPEECH_TMP_PATH);
        if (pathToSpeechFiles.isDirectory()) {
            for (File file : pathToSpeechFiles.listFiles()) {
                file.delete();
            }
        }
    }

    public void resourceFailed() {
        setResult(0, this.returnToActivityIntent);
        finish();
    }

    public void showResourceFailedErrorDialog() {
        String failedResourcesMessage = getString(R.string.prestage_resource_not_available_text);
        for (Integer intValue : this.failedResources) {
            int intValue2 = intValue.intValue();
            StringBuilder stringBuilder;
            if (intValue2 == 1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(failedResourcesMessage);
                stringBuilder.append(getString(R.string.prestage_text_to_speech_error));
                failedResourcesMessage = stringBuilder.toString();
            } else if (intValue2 == 4) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(failedResourcesMessage);
                stringBuilder.append(getString(R.string.prestage_no_camera_available));
                failedResourcesMessage = stringBuilder.toString();
            } else if (intValue2 == 18) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(failedResourcesMessage);
                stringBuilder.append(getString(R.string.prestage_no_gps_sensor_available));
                failedResourcesMessage = stringBuilder.toString();
            } else if (intValue2 != 23) {
                switch (intValue2) {
                    case 8:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(failedResourcesMessage);
                        stringBuilder.append(getString(R.string.prestage_no_flash_available));
                        failedResourcesMessage = stringBuilder.toString();
                        break;
                    case 9:
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(failedResourcesMessage);
                        stringBuilder.append(getString(R.string.prestage_no_vibrator_available));
                        failedResourcesMessage = stringBuilder.toString();
                        break;
                    default:
                        switch (intValue2) {
                            case 11:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(failedResourcesMessage);
                                stringBuilder.append(getString(R.string.prestage_no_back_camera_available));
                                failedResourcesMessage = stringBuilder.toString();
                                break;
                            case 12:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(failedResourcesMessage);
                                stringBuilder.append(getString(R.string.prestage_no_front_camera_available));
                                failedResourcesMessage = stringBuilder.toString();
                                break;
                            case 13:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(failedResourcesMessage);
                                stringBuilder.append(getString(R.string.prestage_no_acceleration_sensor_available));
                                failedResourcesMessage = stringBuilder.toString();
                                break;
                            case 14:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(failedResourcesMessage);
                                stringBuilder.append(getString(R.string.prestage_no_inclination_sensor_available));
                                failedResourcesMessage = stringBuilder.toString();
                                break;
                            case 15:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(failedResourcesMessage);
                                stringBuilder.append(getString(R.string.prestage_no_compass_sensor_available));
                                failedResourcesMessage = stringBuilder.toString();
                                break;
                            default:
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(failedResourcesMessage);
                                stringBuilder.append(getString(R.string.prestage_default_resource_not_available));
                                failedResourcesMessage = stringBuilder.toString();
                                break;
                        }
                }
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(failedResourcesMessage);
                stringBuilder.append(getString(R.string.prestage_no_jumping_sumo_available));
                failedResourcesMessage = stringBuilder.toString();
            }
        }
        AlertDialog$Builder failedResourceAlertBuilder = new AlertDialog$Builder(this);
        failedResourceAlertBuilder.setTitle(R.string.prestage_resource_not_available_title);
        failedResourceAlertBuilder.setMessage(failedResourcesMessage).setCancelable(false).setPositiveButton(getString(R.string.ok), new C18856());
        failedResourceAlertBuilder.create().show();
    }

    public void showResourceInUseErrorDialog() {
        String failedResourcesMessage = getString(R.string.prestage_resource_in_use_text);
        AlertDialog$Builder failedResourceAlertBuilder = new AlertDialog$Builder(this);
        failedResourceAlertBuilder.setTitle(R.string.prestage_resource_not_available_title);
        failedResourceAlertBuilder.setMessage(failedResourcesMessage).setCancelable(false).setPositiveButton(getString(R.string.ok), new C18867());
        failedResourceAlertBuilder.create().show();
    }

    public synchronized void resourceFailed(int failedResource) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("resourceFailed: ");
        stringBuilder.append(failedResource);
        Log.d(str, stringBuilder.toString());
        this.failedResources.add(Integer.valueOf(failedResource));
        resourceInitialized();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void resourceInitialized() {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.requiredResourceCounter;	 Catch:{ all -> 0x0039 }
        r0 = r0 + -1;
        r2.requiredResourceCounter = r0;	 Catch:{ all -> 0x0039 }
        r0 = r2.requiredResourceCounter;	 Catch:{ all -> 0x0039 }
        if (r0 != 0) goto L_0x0037;
    L_0x000b:
        r0 = r2.failedResources;	 Catch:{ all -> 0x0039 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0039 }
        if (r0 == 0) goto L_0x0034;
    L_0x0013:
        r0 = TAG;	 Catch:{ all -> 0x0039 }
        r1 = "Start Stage";
        android.util.Log.d(r0, r1);	 Catch:{ all -> 0x0039 }
        r0 = r2.requiredResourcesSet;	 Catch:{ all -> 0x0039 }
        r1 = 23;
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ all -> 0x0039 }
        r0 = r0.contains(r1);	 Catch:{ all -> 0x0039 }
        if (r0 == 0) goto L_0x0030;
    L_0x0028:
        r0 = r2.verifyJSConnection();	 Catch:{ all -> 0x0039 }
        if (r0 != 0) goto L_0x0030;
    L_0x002e:
        monitor-exit(r2);
        return;
    L_0x0030:
        r2.startStageActivity();	 Catch:{ all -> 0x0039 }
        goto L_0x0037;
    L_0x0034:
        r2.showResourceFailedErrorDialog();	 Catch:{ all -> 0x0039 }
    L_0x0037:
        monitor-exit(r2);
        return;
    L_0x0039:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.stage.PreStageActivity.resourceInitialized():void");
    }

    public void startStageActivity() {
        for (Scene scene : ProjectManager.getInstance().getCurrentProject().getSceneList()) {
            scene.firstStart = true;
            scene.getDataContainer().resetUserData();
        }
        setResult(-1, this.returnToActivityIntent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("requestcode ");
        stringBuilder.append(requestCode);
        stringBuilder.append(" result code");
        stringBuilder.append(resultCode);
        Log.i(str, stringBuilder.toString());
        if (requestCode != 1) {
            if (requestCode != 1000) {
                resourceFailed();
                return;
            }
            switch (resultCode) {
                case -1:
                    resourceInitialized();
                    break;
                case 0:
                    resourceFailed();
                    break;
                default:
                    break;
            }
        } else if (resultCode == 0 && SensorHandler.gpsAvailable()) {
            resourceInitialized();
        } else {
            resourceFailed(18);
        }
    }

    public static void textToSpeech(String text, File speechFile, OnUtteranceCompletedListener listener, HashMap<String, String> speakParameter) {
        if (text == null) {
            text = "";
        }
        if (onUtteranceCompletedListenerContainer.addOnUtteranceCompletedListener(speechFile, listener, (String) speakParameter.get("utteranceId")) && textToSpeech.synthesizeToFile(text, speakParameter, speechFile.getAbsolutePath()) == -1) {
            Log.e(TAG, "File synthesizing failed");
        }
    }

    private void flashInitialize() {
        if (CameraManager.getInstance().switchToCameraWithFlash()) {
            FlashUtil.initializeFlash();
            resourceInitialized();
            return;
        }
        resourceFailed(8);
    }

    private void nfcInitialize() {
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (adapter != null && !adapter.isEnabled()) {
            ToastUtil.showError((Context) this, (int) R.string.nfc_not_activated);
            startActivity(new Intent("android.settings.NFC_SETTINGS"));
        } else if (adapter == null) {
            ToastUtil.showError((Context) this, (int) R.string.no_nfc_available);
        }
        resourceInitialized();
    }

    private boolean verifyJSConnection() {
        ARCONTROLLER_DEVICE_STATE_ENUM state = ARCONTROLLER_DEVICE_STATE_ENUM.eARCONTROLLER_DEVICE_STATE_UNKNOWN_ENUM_VALUE;
        try {
            state = JumpingSumoDeviceController.getInstance().getDeviceController().getState();
        } catch (ARControllerException e) {
            Log.e(TAG, "Error could not connect to drone", e);
        }
        if (state == ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING) {
            return true;
        }
        resourceFailed(23);
        showResourceInUseErrorDialog();
        return false;
    }

    public void onFinished() {
        resourceInitialized();
    }
}
