package org.catrobat.catroid.drone.ardrone;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import com.parrot.freeflight.receivers.DroneAvailabilityDelegate;
import com.parrot.freeflight.receivers.DroneAvailabilityReceiver;
import com.parrot.freeflight.receivers.DroneConnectionChangeReceiverDelegate;
import com.parrot.freeflight.receivers.DroneConnectionChangedReceiver;
import com.parrot.freeflight.receivers.DroneReadyReceiver;
import com.parrot.freeflight.receivers.DroneReadyReceiverDelegate;
import com.parrot.freeflight.service.DroneControlService;
import com.parrot.freeflight.service.DroneControlService.LocalBinder;
import com.parrot.freeflight.service.intents.DroneStateManager;
import com.parrot.freeflight.tasks.CheckDroneNetworkAvailabilityTask;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.stage.PreStageActivity;
import org.catrobat.catroid.ui.dialogs.TermsOfUseDialogFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class DroneInitializer implements DroneReadyReceiverDelegate, DroneConnectionChangeReceiverDelegate, DroneAvailabilityDelegate {
    public static final int DRONE_BATTERY_THRESHOLD = 10;
    private static final String TAG = DroneInitializer.class.getSimpleName();
    private static DroneControlService droneControlService = null;
    private CheckDroneNetworkAvailabilityTask checkDroneConnectionTask;
    private DroneConnectionChangedReceiver droneConnectionChangeReceiver;
    private BroadcastReceiver droneReadyReceiver = null;
    private ServiceConnection droneServiceConnection = new C18232();
    private BroadcastReceiver droneStateReceiver = null;
    private PreStageActivity prestageStageActivity;

    /* renamed from: org.catrobat.catroid.drone.ardrone.DroneInitializer$2 */
    class C18232 implements ServiceConnection {
        C18232() {
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            DroneInitializer.this.onDroneServiceConnected(service);
        }

        public void onServiceDisconnected(ComponentName name) {
            DroneInitializer.droneControlService = null;
            DroneServiceWrapper.getInstance().setDroneService(DroneInitializer.droneControlService);
        }
    }

    /* renamed from: org.catrobat.catroid.drone.ardrone.DroneInitializer$3 */
    class C20973 extends CheckDroneNetworkAvailabilityTask {
        C20973() {
        }

        protected void onPostExecute(Boolean result) {
            DroneInitializer.this.onDroneAvailabilityChanged(result.booleanValue());
        }
    }

    public DroneInitializer(PreStageActivity prestageStageActivity) {
        this.prestageStageActivity = prestageStageActivity;
    }

    private void showTermsOfUseDialog() {
        Bundle args = new Bundle();
        args.putBoolean("dialog_terms_of_use_accept", true);
        TermsOfUseDialogFragment termsOfUseDialog = new TermsOfUseDialogFragment();
        termsOfUseDialog.setArguments(args);
        termsOfUseDialog.show(this.prestageStageActivity.getSupportFragmentManager(), TermsOfUseDialogFragment.TAG);
    }

    public void initialise() {
        if (!SettingsFragment.areTermsOfServiceAgreedPermanently(this.prestageStageActivity.getApplicationContext())) {
            showTermsOfUseDialog();
        } else if (checkRequirements()) {
            checkDroneConnectivity();
        }
    }

    public boolean checkRequirements() {
        if (!CatroidApplication.OS_ARCH.startsWith("arm")) {
            showUnCancellableErrorDialog(this.prestageStageActivity, this.prestageStageActivity.getString(R.string.error_drone_wrong_platform_title), this.prestageStageActivity.getString(R.string.error_drone_wrong_platform));
            return false;
        } else if (CatroidApplication.loadNativeLibs()) {
            return true;
        } else {
            showUnCancellableErrorDialog(this.prestageStageActivity, this.prestageStageActivity.getString(R.string.error_drone_wrong_platform_title), this.prestageStageActivity.getString(R.string.error_drone_wrong_platform));
            return false;
        }
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

    private void onDroneServiceConnected(IBinder service) {
        Log.d(TAG, "onDroneServiceConnected");
        droneControlService = ((LocalBinder) service).getService();
        DroneServiceWrapper.getInstance().setDroneService(droneControlService);
        droneControlService.resume();
        droneControlService.requestDroneStatus();
    }

    public void onDroneReady() {
        Log.d(TAG, "onDroneReady -> check battery -> go to stage");
        int droneBatteryCharge = droneControlService.getDroneNavData().batteryStatus;
        if (droneControlService != null) {
            if (droneBatteryCharge < 10) {
                showUnCancellableErrorDialog(this.prestageStageActivity, String.format(this.prestageStageActivity.getString(R.string.error_drone_low_battery_title), new Object[]{Integer.valueOf(droneBatteryCharge)}), this.prestageStageActivity.getString(R.string.error_drone_low_battery));
                return;
            }
            DroneConfigManager.getInstance().setDroneConfig(SettingsFragment.getDronePreferenceMapping(CatroidApplication.getAppContext()));
            droneControlService.flatTrim();
            this.prestageStageActivity.resourceInitialized();
        }
    }

    public void onDroneConnected() {
        Log.d(getClass().getSimpleName(), "onDroneConnected()");
        droneControlService.requestConfigUpdate();
    }

    public void onDroneDisconnected() {
        Log.d(getClass().getSimpleName(), "onDroneDisconnected()");
    }

    public void onDroneAvailabilityChanged(boolean isDroneOnNetwork) {
        if (isDroneOnNetwork) {
            this.prestageStageActivity.startService(new Intent(this.prestageStageActivity, DroneControlService.class));
            this.prestageStageActivity.bindService(new Intent(this.prestageStageActivity, DroneControlService.class), this.droneServiceConnection, 1);
            return;
        }
        showUnCancellableErrorDialog(this.prestageStageActivity, this.prestageStageActivity.getString(R.string.error_no_drone_connected_title), this.prestageStageActivity.getString(R.string.error_no_drone_connected));
    }

    public void onPrestageActivityDestroy() {
        if (droneControlService != null) {
            this.prestageStageActivity.unbindService(this.droneServiceConnection);
            droneControlService = null;
        }
    }

    public void onPrestageActivityResume() {
        this.droneReadyReceiver = new DroneReadyReceiver(this);
        this.droneStateReceiver = new DroneAvailabilityReceiver(this);
        this.droneConnectionChangeReceiver = new DroneConnectionChangedReceiver(this);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this.prestageStageActivity.getApplicationContext());
        manager.registerReceiver(this.droneReadyReceiver, new IntentFilter(DroneControlService.DRONE_STATE_READY_ACTION));
        manager.registerReceiver(this.droneConnectionChangeReceiver, new IntentFilter(DroneControlService.DRONE_CONNECTION_CHANGED_ACTION));
        manager.registerReceiver(this.droneStateReceiver, new IntentFilter(DroneStateManager.ACTION_DRONE_STATE_CHANGED));
    }

    @SuppressLint({"NewApi"})
    public void checkDroneConnectivity() {
        if (!(this.checkDroneConnectionTask == null || this.checkDroneConnectionTask.getStatus() == Status.FINISHED)) {
            this.checkDroneConnectionTask.cancel(true);
        }
        this.checkDroneConnectionTask = new C20973();
        this.checkDroneConnectionTask.executeOnExecutor(CheckDroneNetworkAvailabilityTask.THREAD_POOL_EXECUTOR, new Context[]{this.prestageStageActivity});
    }

    public void onPrestageActivityPause() {
        if (droneControlService != null) {
            droneControlService.pause();
        }
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this.prestageStageActivity.getApplicationContext());
        manager.unregisterReceiver(this.droneReadyReceiver);
        manager.unregisterReceiver(this.droneConnectionChangeReceiver);
        manager.unregisterReceiver(this.droneStateReceiver);
        if (taskRunning(this.checkDroneConnectionTask)) {
            this.checkDroneConnectionTask.cancelAnyFtpOperation();
        }
    }

    private boolean taskRunning(AsyncTask<?, ?, ?> checkMediaTask2) {
        return (checkMediaTask2 == null || checkMediaTask2.getStatus() == Status.FINISHED) ? false : true;
    }
}
