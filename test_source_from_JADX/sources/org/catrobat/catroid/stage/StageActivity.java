package org.catrobat.catroid.stage;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.widget.EditText;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.AskAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoInitializer;
import org.catrobat.catroid.facedetection.FaceDetectionHandler;
import org.catrobat.catroid.formulaeditor.SensorHandler;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.io.StageAudioFocus;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.ui.MarketingActivity;
import org.catrobat.catroid.ui.dialogs.StageDialog;
import org.catrobat.catroid.utils.FlashUtil;
import org.catrobat.catroid.utils.ScreenValueHandler;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.VibratorUtil;

public class StageActivity extends AndroidApplication {
    public static final int ASK_MESSAGE = 0;
    private static final int PERFORM_INTENT = 2;
    public static final int REGISTER_INTENT = 1;
    public static final int STAGE_ACTIVITY_FINISH = 7777;
    public static final String TAG = StageActivity.class.getSimpleName();
    public static SparseArray<IntentListener> intentListeners = new SparseArray();
    public static Handler messageHandler;
    private static NdefMessage nfcTagMessage;
    private static int numberOfSpritesCloned;
    public static Random randomGenerator = new Random();
    public static StageListener stageListener;
    private boolean askDialogUnanswered = false;
    AndroidApplicationConfiguration configuration = null;
    private JumpingSumoDeviceController controller;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private boolean resizePossible;
    private StageAudioFocus stageAudioFocus;
    private StageDialog stageDialog;

    /* renamed from: org.catrobat.catroid.stage.StageActivity$2 */
    class C18882 implements OnKeyListener {
        C18882() {
        }

        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode != 4) {
                return false;
            }
            StageActivity.this.onBackPressed();
            return true;
        }
    }

    /* renamed from: org.catrobat.catroid.stage.StageActivity$4 */
    class C18904 implements OnClickListener {
        C18904() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
            StageActivity.this.onDestroy();
            StageActivity.this.exit();
        }
    }

    public interface IntentListener {
        Intent getTargetIntent();

        void onIntentResult(int i, Intent intent);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        if (ProjectManager.getInstance().getCurrentProject() == null) {
            finish();
            Log.d(TAG, "no current project set, cowardly refusing to run");
            return;
        }
        numberOfSpritesCloned = 0;
        setupAskHandler();
        this.controller = JumpingSumoDeviceController.getInstance();
        if (ProjectManager.getInstance().isCurrentProjectLandscapeMode()) {
            setRequestedOrientation(0);
        } else {
            setRequestedOrientation(1);
        }
        getWindow().addFlags(128);
        stageListener = new StageListener();
        this.stageDialog = new StageDialog(this, stageListener, R.style.StageDialog);
        calculateScreenSizes();
        this.configuration = new AndroidApplicationConfiguration();
        AndroidApplicationConfiguration androidApplicationConfiguration = this.configuration;
        AndroidApplicationConfiguration androidApplicationConfiguration2 = this.configuration;
        AndroidApplicationConfiguration androidApplicationConfiguration3 = this.configuration;
        this.configuration.f55a = 8;
        androidApplicationConfiguration3.f56b = 8;
        androidApplicationConfiguration2.f57g = 8;
        androidApplicationConfiguration.f58r = 8;
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            setRequestedOrientation(0);
            setContentView(R.layout.activity_stage_gamepad);
            CastManager.getInstance().initializeGamepadActivity(this);
            CastManager.getInstance().addStageViewToLayout((GLSurfaceView20) initializeForView(stageListener, this.configuration));
        } else {
            initialize(stageListener, this.configuration);
        }
        if (this.graphics.getView() instanceof SurfaceView) {
            ((SurfaceView) this.graphics.getView()).getHolder().setFormat(-3);
        }
        this.pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(536870912), 0);
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Log.d(TAG, "onCreate()");
        if (this.nfcAdapter == null) {
            Log.d(TAG, "could not get nfc adapter :(");
        }
        ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).initialise();
        this.stageAudioFocus = new StageAudioFocus(this);
        CameraManager.getInstance().setStageActivity(this);
        JumpingSumoInitializer.getInstance().setStageActivity(this);
        SnackbarUtil.showHintSnackbar(this, R.string.hint_stage);
    }

    private void setupAskHandler() {
        final StageActivity currentStage = this;
        messageHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                ArrayList params = (ArrayList) message.obj;
                switch (message.what) {
                    case 0:
                        StageActivity.this.showDialog((String) params.get(1), (AskAction) params.get(0));
                        return;
                    case 1:
                        currentStage.queueIntent((IntentListener) params.get(0));
                        return;
                    case 2:
                        currentStage.startQueuedIntent(((Integer) params.get(0)).intValue());
                        return;
                    default:
                        String str = StageActivity.TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unhandled message in messagehandler, case ");
                        stringBuilder.append(message.what);
                        Log.e(str, stringBuilder.toString());
                        return;
                }
            }
        };
    }

    private void showDialog(String question, final AskAction askAction) {
        pause();
        AlertDialog$Builder alertBuilder = new AlertDialog$Builder(new ContextThemeWrapper(this, R.style.Theme.AppCompat.Dialog));
        final EditText edittext = new EditText(getContext());
        alertBuilder.setView(edittext);
        alertBuilder.setMessage(getContext().getString(R.string.brick_ask_dialog_hint));
        alertBuilder.setTitle(question);
        alertBuilder.setCancelable(false);
        alertBuilder.setOnKeyListener(new C18882());
        alertBuilder.setPositiveButton(getContext().getString(R.string.brick_ask_dialog_submit), new OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                askAction.setAnswerText(edittext.getText().toString());
                StageActivity.this.askDialogUnanswered = false;
                StageActivity.this.resume();
            }
        });
        AlertDialog dialog = alertBuilder.create();
        dialog.getWindow().setSoftInputMode(4);
        this.askDialogUnanswered = true;
        dialog.show();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "processIntent");
        NfcHandler.processIntent(intent);
        if (nfcTagMessage != null) {
            Tag currentTag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
            synchronized (StageActivity.class) {
                NfcHandler.writeTag(currentTag, nfcTagMessage);
                setNfcTagMessage(null);
            }
        }
    }

    public void onBackPressed() {
        PreStageActivity.shutdownPersistentResources();
        startActivity(new Intent(this, MarketingActivity.class));
        finish();
    }

    public void manageLoadAndFinish() {
        stageListener.pause();
        stageListener.finish();
        PreStageActivity.shutdownResources();
    }

    public void onPause() {
        if (this.nfcAdapter != null) {
            try {
                this.nfcAdapter.disableForegroundDispatch(this);
            } catch (IllegalStateException illegalStateException) {
                Log.e(TAG, "Disabling NFC foreground dispatching went wrong!", illegalStateException);
            }
        }
        SensorHandler.stopSensorListeners();
        this.stageAudioFocus.releaseAudioFocus();
        FlashUtil.pauseFlash();
        FaceDetectionHandler.pauseFaceDetection();
        CameraManager.getInstance().pausePreview();
        CameraManager.getInstance().releaseCamera();
        VibratorUtil.pauseVibrator();
        super.onPause();
        ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).pause();
    }

    public void onResume() {
        resumeResources();
        super.onResume();
    }

    public void pause() {
        if (this.nfcAdapter != null) {
            this.nfcAdapter.disableForegroundDispatch(this);
        }
        SensorHandler.stopSensorListeners();
        stageListener.menuPause();
        FlashUtil.pauseFlash();
        VibratorUtil.pauseVibrator();
        FaceDetectionHandler.pauseFaceDetection();
        CameraManager.getInstance().pausePreviewAsync();
        ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).pause();
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            CastManager.getInstance().setRemoteLayoutToPauseScreen(getApplicationContext());
        }
    }

    public boolean jumpingSumoDisconnect() {
        if (this.controller.isConnected()) {
            return JumpingSumoInitializer.getInstance().disconnect();
        }
        return true;
    }

    public void resume() {
        if (!this.askDialogUnanswered) {
            stageListener.menuResume();
            resumeResources();
        }
    }

    public void resumeResources() {
        ResourcesSet resourcesSet = ProjectManager.getInstance().getCurrentProject().getRequiredResources();
        List<Sprite> spriteList = ProjectManager.getInstance().getCurrentlyPlayingScene().getSpriteList();
        SensorHandler.startSensorListener(this);
        for (Sprite sprite : spriteList) {
            if (sprite.getPlaySoundBricks().size() > 0) {
                this.stageAudioFocus.requestAudioFocus();
                break;
            }
        }
        if (resourcesSet.contains(Integer.valueOf(8))) {
            FlashUtil.resumeFlash();
        }
        if (resourcesSet.contains(Integer.valueOf(9))) {
            VibratorUtil.resumeVibrator();
        }
        if (resourcesSet.contains(Integer.valueOf(4))) {
            FaceDetectionHandler.resumeFaceDetection();
        }
        if (resourcesSet.contains(Integer.valueOf(2)) || resourcesSet.contains(Integer.valueOf(10)) || resourcesSet.contains(Integer.valueOf(6))) {
            ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).start();
        }
        if (resourcesSet.contains(Integer.valueOf(11)) || resourcesSet.contains(Integer.valueOf(12)) || resourcesSet.contains(Integer.valueOf(17))) {
            CameraManager.getInstance().resumePreviewAsync();
        }
        if (resourcesSet.contains(Integer.valueOf(1))) {
            this.stageAudioFocus.requestAudioFocus();
        }
        if (resourcesSet.contains(Integer.valueOf(16)) && this.nfcAdapter != null) {
            this.nfcAdapter.enableForegroundDispatch(this, this.pendingIntent, null, (String[][]) null);
        }
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            CastManager.getInstance().resumeRemoteLayoutFromPauseScreen();
        }
    }

    public boolean getResizePossible() {
        return this.resizePossible;
    }

    private void calculateScreenSizes() {
        ScreenValueHandler.updateScreenWidthAndHeight(getContext());
        int virtualScreenWidth = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenWidth;
        int virtualScreenHeight = ProjectManager.getInstance().getCurrentProject().getXmlHeader().virtualScreenHeight;
        if (virtualScreenHeight > virtualScreenWidth) {
            iflandscapeModeSwitchWidthAndHeight();
        } else {
            ifPortraitSwitchWidthAndHeight();
        }
        float aspectRatio = ((float) virtualScreenWidth) / ((float) virtualScreenHeight);
        float screenAspectRatio = ScreenValues.getAspectRatio();
        if (!((virtualScreenWidth == ScreenValues.SCREEN_WIDTH && virtualScreenHeight == ScreenValues.SCREEN_HEIGHT) || Float.compare(screenAspectRatio, aspectRatio) == 0)) {
            if (!ProjectManager.getInstance().getCurrentProject().isCastProject()) {
                this.resizePossible = true;
                float ratioHeight = ((float) ScreenValues.SCREEN_HEIGHT) / ((float) virtualScreenHeight);
                float ratioWidth = ((float) ScreenValues.SCREEN_WIDTH) / ((float) virtualScreenWidth);
                float scale;
                if (aspectRatio < screenAspectRatio) {
                    scale = ratioHeight / ratioWidth;
                    stageListener.maximizeViewPortWidth = (int) (((float) ScreenValues.SCREEN_WIDTH) * scale);
                    stageListener.maximizeViewPortX = (int) (((float) (ScreenValues.SCREEN_WIDTH - stageListener.maximizeViewPortWidth)) / 2.0f);
                    stageListener.maximizeViewPortHeight = ScreenValues.SCREEN_HEIGHT;
                } else if (aspectRatio > screenAspectRatio) {
                    scale = ratioWidth / ratioHeight;
                    stageListener.maximizeViewPortHeight = (int) (((float) ScreenValues.SCREEN_HEIGHT) * scale);
                    stageListener.maximizeViewPortY = (int) (((float) (ScreenValues.SCREEN_HEIGHT - stageListener.maximizeViewPortHeight)) / 2.0f);
                    stageListener.maximizeViewPortWidth = ScreenValues.SCREEN_WIDTH;
                }
                return;
            }
        }
        this.resizePossible = false;
        stageListener.maximizeViewPortWidth = ScreenValues.SCREEN_WIDTH;
        stageListener.maximizeViewPortHeight = ScreenValues.SCREEN_HEIGHT;
    }

    private void iflandscapeModeSwitchWidthAndHeight() {
        if (ScreenValues.SCREEN_WIDTH > ScreenValues.SCREEN_HEIGHT) {
            int tmp = ScreenValues.SCREEN_HEIGHT;
            ScreenValues.SCREEN_HEIGHT = ScreenValues.SCREEN_WIDTH;
            ScreenValues.SCREEN_WIDTH = tmp;
        }
    }

    private void ifPortraitSwitchWidthAndHeight() {
        if (ScreenValues.SCREEN_WIDTH < ScreenValues.SCREEN_HEIGHT) {
            int tmp = ScreenValues.SCREEN_HEIGHT;
            ScreenValues.SCREEN_HEIGHT = ScreenValues.SCREEN_WIDTH;
            ScreenValues.SCREEN_WIDTH = tmp;
        }
    }

    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        jumpingSumoDisconnect();
        ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).destroy();
        FlashUtil.destroy();
        VibratorUtil.destroy();
        FaceDetectionHandler.stopFaceDetection();
        CameraManager.getInstance().stopPreviewAsync();
        CameraManager.getInstance().releaseCamera();
        CameraManager.getInstance().setToDefaultCamera();
        ProjectManager.getInstance().setCurrentlyPlayingScene(ProjectManager.getInstance().getCurrentlyEditedScene());
        if (ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            CastManager.getInstance().onStageDestroyed();
        }
        super.onDestroy();
    }

    public ApplicationListener getApplicationListener() {
        return stageListener;
    }

    public void log(String tag, String message, Throwable exception) {
        Log.d(tag, message, exception);
    }

    public int getLogLevel() {
        return 0;
    }

    public void post(Runnable r) {
        this.handler.post(r);
    }

    public void destroy() {
        stageListener.finish();
        manageLoadAndFinish();
        final AlertDialog$Builder builder = new AlertDialog$Builder(this);
        builder.setMessage(R.string.error_flash_camera).setCancelable(false).setPositiveButton(R.string.yes, new C18904());
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    builder.create().show();
                } catch (Exception e) {
                    String str = StageActivity.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error while showing dialog. ");
                    stringBuilder.append(e.getMessage());
                    Log.e(str, stringBuilder.toString());
                }
            }
        });
    }

    public void jsDestroy() {
        stageListener.finish();
        manageLoadAndFinish();
        exit();
    }

    public static int getAndIncrementNumberOfClonedSprites() {
        int i = numberOfSpritesCloned + 1;
        numberOfSpritesCloned = i;
        return i;
    }

    public static void resetNumberOfClonedSprites() {
        numberOfSpritesCloned = 0;
    }

    public static void setNfcTagMessage(NdefMessage message) {
        nfcTagMessage = message;
    }

    public static NdefMessage getNfcTagMessage() {
        return nfcTagMessage;
    }

    public synchronized void queueIntent(IntentListener asker) {
        if (messageHandler != null) {
            int newIdentId;
            do {
                newIdentId = randomGenerator.nextInt(Integer.MAX_VALUE);
            } while (intentListeners.indexOfKey(newIdentId) >= 0);
            intentListeners.put(newIdentId, asker);
            ArrayList<Object> params = new ArrayList();
            params.add(Integer.valueOf(newIdentId));
            messageHandler.obtainMessage(2, params).sendToTarget();
        }
    }

    private void startQueuedIntent(int intentKey) {
        if (intentListeners.indexOfKey(intentKey) >= 0) {
            Intent i = ((IntentListener) intentListeners.get(intentKey)).getTargetIntent();
            i.putExtra("calling_package", getClass().getPackage().getName());
            startActivityForResult(i, intentKey);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (intentListeners.indexOfKey(requestCode) < 0) {
            Log.e(TAG, "Unknown intent result recieved!");
            return;
        }
        ((IntentListener) intentListeners.get(requestCode)).onIntentResult(resultCode, data);
        intentListeners.remove(requestCode);
    }
}
