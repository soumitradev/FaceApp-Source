package org.catrobat.catroid.ui.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog$Builder;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.catrobat.catroid.devices.mindstorms.ev3.sensors.EV3Sensor;
import org.catrobat.catroid.devices.mindstorms.nxt.sensors.NXTSensor.Sensor;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public final class LegoSensorPortConfigDialog extends AlertDialog {

    public interface OnClickListener {
        void onPositiveButtonClick(DialogInterface dialogInterface, int i, Enum enumR);
    }

    public static class Builder extends AlertDialog$Builder {
        private SensorInfo sensorInfo;
        private Map<Integer, SensorInfo> sensorInfoMap = ImmutableMap.builder().put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_touch), new SensorInfo(R.string.nxt_sensor_touch, Sensor.TOUCH)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_sound), new SensorInfo(R.string.nxt_sensor_sound, Sensor.SOUND)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_light), new SensorInfo(R.string.nxt_sensor_light, Sensor.LIGHT_INACTIVE)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_light_active), new SensorInfo(R.string.nxt_sensor_light_active, Sensor.LIGHT_ACTIVE)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_nxt_ultrasonic), new SensorInfo(R.string.nxt_sensor_ultrasonic, Sensor.ULTRASONIC)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_touch), new SensorInfo(R.string.ev3_sensor_touch, EV3Sensor.Sensor.TOUCH)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_infrared), new SensorInfo(R.string.ev3_sensor_infrared, EV3Sensor.Sensor.INFRARED)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color), new SensorInfo(R.string.ev3_sensor_color, EV3Sensor.Sensor.COLOR)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color_ambient), new SensorInfo(R.string.ev3_sensor_color_ambient, EV3Sensor.Sensor.COLOR_AMBIENT)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_color_reflected), new SensorInfo(R.string.ev3_sensor_color_reflected, EV3Sensor.Sensor.COLOR_REFLECT)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_hitechnic_color), new SensorInfo(R.string.ev3_sensor_hitechnic_color, EV3Sensor.Sensor.HT_NXT_COLOR)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_temperature_c), new SensorInfo(R.string.ev3_sensor_nxt_temperature_c, EV3Sensor.Sensor.NXT_TEMPERATURE_C)).put(Integer.valueOf(R.string.formula_editor_sensor_lego_ev3_sensor_nxt_temperature_f), new SensorInfo(R.string.ev3_sensor_nxt_temperature_f, EV3Sensor.Sensor.NXT_TEMPERATURE_F)).build();

        /* renamed from: org.catrobat.catroid.ui.dialogs.LegoSensorPortConfigDialog$Builder$1 */
        class C19201 implements android.content.DialogInterface.OnClickListener {
            C19201() {
            }

            public void onClick(DialogInterface dialog, int selectedPort) {
                ((AlertDialog) dialog).getButton(-1).setEnabled(true);
            }
        }

        private class SensorInfo {
            Enum sensor;
            int titleResId;

            SensorInfo(int titleResId, Enum sensor) {
                this.titleResId = titleResId;
                this.sensor = sensor;
            }
        }

        SensorInfo getSensorInfo(int selectedItem, int type) {
            SensorInfo info = (SensorInfo) this.sensorInfoMap.get(Integer.valueOf(selectedItem));
            return info != null ? info : new SensorInfo(R.string.nxt_sensor_not_found, type == 0 ? Sensor.NO_SENSOR : EV3Sensor.Sensor.NO_SENSOR);
        }

        public Builder(@NonNull Context context, int legoType, @StringRes int selectedItem) {
            Enum[] sensorsByPort;
            super(context);
            this.sensorInfo = getSensorInfo(selectedItem, legoType);
            if (legoType == 0) {
                sensorsByPort = SettingsFragment.getLegoNXTSensorMapping(context);
            } else {
                sensorsByPort = SettingsFragment.getLegoEV3SensorMapping(context);
            }
            String[] portNames = context.getResources().getStringArray(R.array.port_chooser);
            String[] dialogItems = new String[portNames.length];
            String[] sensorNames = context.getResources().getStringArray(legoType == 0 ? R.array.nxt_sensor_chooser : R.array.ev3_sensor_chooser);
            for (int portNumber = 0; portNumber < portNames.length; portNumber++) {
                int sensorNameIndex = sensorsByPort[portNumber].ordinal();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(portNames[portNumber]);
                stringBuilder.append(": ");
                stringBuilder.append(sensorNames[sensorNameIndex]);
                dialogItems[portNumber] = stringBuilder.toString();
            }
            setTitle(context.getString(R.string.lego_sensor_port_config_dialog_title, new Object[]{context.getString(this.sensorInfo.titleResId)}));
            setSingleChoiceItems(dialogItems, -1, new C19201());
        }

        public Builder setPositiveButton(CharSequence text, final OnClickListener listener) {
            setPositiveButton(text, new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    listener.onPositiveButtonClick(dialog, ((AlertDialog) dialog).getListView().getCheckedItemPosition(), Builder.this.sensorInfo.sensor);
                }
            });
            return this;
        }

        public AlertDialog create() {
            final AlertDialog alertDialog = super.create();
            alertDialog.setOnShowListener(new OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    alertDialog.getButton(-1).setEnabled(false);
                }
            });
            return alertDialog;
        }
    }

    private LegoSensorPortConfigDialog(@NonNull Context context) {
        super(context);
    }
}
