package org.catrobat.catroid.drone.ardrone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog$Builder;
import android.util.Log;
import com.parrot.freeflight.receivers.DroneBatteryChangedReceiver;
import com.parrot.freeflight.receivers.DroneBatteryChangedReceiverDelegate;
import com.parrot.freeflight.receivers.DroneEmergencyChangeReceiver;
import com.parrot.freeflight.receivers.DroneEmergencyChangeReceiverDelegate;
import com.parrot.freeflight.service.DroneControlService;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.utils.ToastUtil;

public class DroneStageActivity extends StageActivity implements DroneBatteryChangedReceiverDelegate, DroneEmergencyChangeReceiverDelegate {
    private boolean droneBatteryMessageShown = false;
    private DroneBatteryChangedReceiver droneBatteryReceiver;
    private DroneConnection droneConnection = null;
    private DroneEmergencyChangeReceiver droneEmergencyReceiver;

    /* renamed from: org.catrobat.catroid.drone.ardrone.DroneStageActivity$1 */
    class C18241 implements OnClickListener {
        C18241() {
        }

        public void onClick(DialogInterface dialogInterface, int id) {
        }
    }

    private enum EmergencyMethod {
        NOTHING,
        TOAST,
        ALERT
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.droneConnection == null && DroneServiceWrapper.checkARDroneAvailability()) {
            this.droneConnection = new DroneConnection(this);
            try {
                this.droneConnection.initialise();
                this.droneBatteryReceiver = new DroneBatteryChangedReceiver(this);
                this.droneEmergencyReceiver = new DroneEmergencyChangeReceiver(this);
            } catch (RuntimeException runtimeException) {
                Log.e(TAG, "Failure during drone service startup", runtimeException);
                ToastUtil.showError((Context) this, (int) R.string.error_no_drone_connected);
                finish();
            }
        }
    }

    public void onPause() {
        super.onPause();
        DroneControlService droneControlService = DroneServiceWrapper.getInstance().getDroneService();
        if (droneControlService != null && droneControlService.getDroneNavData().flying) {
            droneControlService.triggerTakeOff();
        }
        if (droneControlService != null) {
            for (int i = 0; i < 30; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e(getClass().getSimpleName(), "Error in Thread.sleep method!");
                }
                if (!droneControlService.getDroneNavData().flying) {
                    break;
                }
            }
        }
        if (this.droneConnection != null) {
            this.droneConnection.pause();
        }
        unregisterReceivers();
    }

    public void onResume() {
        super.onResume();
        if (this.droneConnection != null) {
            this.droneConnection.start();
        }
        registerReceivers();
    }

    protected void onDestroy() {
        Log.d(getClass().getSimpleName(), "DroneStageActivity: onDestroy() wurde aufgerufen");
        if (this.droneConnection != null) {
            this.droneConnection.destroy();
        }
        this.droneBatteryMessageShown = false;
        super.onDestroy();
    }

    private void registerReceivers() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.registerReceiver(this.droneBatteryReceiver, new IntentFilter(DroneControlService.DRONE_BATTERY_CHANGED_ACTION));
        manager.registerReceiver(this.droneEmergencyReceiver, new IntentFilter(DroneControlService.DRONE_EMERGENCY_STATE_CHANGED_ACTION));
    }

    private void unregisterReceivers() {
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.unregisterReceiver(this.droneBatteryReceiver);
        manager.unregisterReceiver(this.droneEmergencyReceiver);
    }

    public void onDroneBatteryChanged(int value) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Battery Status = ");
        stringBuilder.append(Integer.toString(value));
        Log.d(str, stringBuilder.toString());
        DroneControlService dcs = DroneServiceWrapper.getInstance().getDroneService();
        if (dcs != null && value < 10 && dcs.getDroneNavData().flying && !this.droneBatteryMessageShown) {
            ToastUtil.showError((Context) this, getString(R.string.notification_low_battery_with_value, new Object[]{Integer.valueOf(value)}));
            this.droneBatteryMessageShown = true;
        }
    }

    public void onDroneEmergencyChanged(int code) {
        EmergencyMethod method = EmergencyMethod.NOTHING;
        String simpleName = getClass().getSimpleName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("message code integer value: ");
        stringBuilder.append(Integer.toString(code));
        Log.d(simpleName, stringBuilder.toString());
        if (code != 0) {
            if (code != 2) {
                int messageID;
                String simpleName2;
                StringBuilder stringBuilder2;
                switch (code) {
                    case 3:
                        messageID = R.string.drone_emergency_cutout;
                        method = EmergencyMethod.ALERT;
                        simpleName2 = getClass().getSimpleName();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("message code: ");
                        stringBuilder2.append(getResources().getString(R.string.drone_emergency_cutout));
                        Log.d(simpleName2, stringBuilder2.toString());
                        break;
                    case 5:
                    case 13:
                        messageID = R.string.drone_emergency_camera;
                        method = EmergencyMethod.TOAST;
                        simpleName2 = getClass().getSimpleName();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("message code: ");
                        stringBuilder2.append(getResources().getString(R.string.drone_emergency_camera));
                        Log.d(simpleName2, stringBuilder2.toString());
                        break;
                    case 8:
                        messageID = R.string.drone_emergency_angle;
                        method = EmergencyMethod.ALERT;
                        simpleName2 = getClass().getSimpleName();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("message code: ");
                        stringBuilder2.append(getResources().getString(R.string.drone_emergency_angle));
                        Log.d(simpleName2, stringBuilder2.toString());
                        break;
                    case 9:
                        messageID = R.string.drone_emergency_battery_low;
                        method = EmergencyMethod.ALERT;
                        simpleName2 = getClass().getSimpleName();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("message code: ");
                        stringBuilder2.append(getResources().getString(R.string.drone_emergency_battery_low));
                        Log.d(simpleName2, stringBuilder2.toString());
                        break;
                    case 11:
                        messageID = R.string.drone_emergency_ultrasound;
                        method = EmergencyMethod.ALERT;
                        simpleName2 = getClass().getSimpleName();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("message code: ");
                        stringBuilder2.append(getResources().getString(R.string.drone_emergency_ultrasound));
                        Log.d(simpleName2, stringBuilder2.toString());
                        break;
                    case 14:
                        messageID = R.string.drone_alert_battery_low;
                        method = EmergencyMethod.TOAST;
                        simpleName2 = getClass().getSimpleName();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("message code: ");
                        stringBuilder2.append(getResources().getString(R.string.drone_alert_battery_low));
                        Log.d(simpleName2, stringBuilder2.toString());
                        break;
                    case 15:
                        messageID = R.string.drone_alert_ultrasound;
                        method = EmergencyMethod.NOTHING;
                        simpleName2 = getClass().getSimpleName();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("message code: ");
                        stringBuilder2.append(getResources().getString(R.string.drone_alert_ultrasound));
                        Log.d(simpleName2, stringBuilder2.toString());
                        break;
                    case 16:
                        messageID = R.string.drone_alert_vision;
                        method = EmergencyMethod.TOAST;
                        simpleName2 = getClass().getSimpleName();
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("message code: ");
                        stringBuilder2.append(getResources().getString(R.string.drone_alert_vision));
                        Log.d(simpleName2, stringBuilder2.toString());
                        break;
                    default:
                        simpleName = getClass().getSimpleName();
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("message code (number): ");
                        stringBuilder.append(code);
                        Log.d(simpleName, stringBuilder.toString());
                        return;
                }
                switch (method) {
                    case ALERT:
                        new AlertDialog$Builder(this).setTitle(R.string.drone_emergency_title).setMessage(messageID).setPositiveButton(17039370, new C18241()).setCancelable(false).show();
                        break;
                    case TOAST:
                        ToastUtil.showError((Context) this, messageID);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
