package org.catrobat.catroid.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog$Builder;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class LegoSensorConfigInfoDialog extends DialogFragment {
    private static final String BUNDLE_KEY_SENSOR_TYPE = "legoSensorType";
    public static final String DIALOG_FRAGMENT_TAG = LegoSensorConfigInfoDialog.class.getSimpleName();

    public static LegoSensorConfigInfoDialog newInstance(int legoSensorType) {
        LegoSensorConfigInfoDialog dialog = new LegoSensorConfigInfoDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_KEY_SENSOR_TYPE, legoSensorType);
        dialog.setArguments(bundle);
        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int titleStringResId;
        int infoStringResId;
        Enum[] sensorMapping;
        String[] sensorMappingStrings;
        if (getArguments() == null) {
            dismiss();
        }
        final int legoSensorType = getArguments().getInt(BUNDLE_KEY_SENSOR_TYPE, 0);
        switch (legoSensorType) {
            case 0:
                titleStringResId = R.string.lego_nxt_sensor_config_info_title;
                infoStringResId = R.string.lego_nxt_sensor_config_info_text;
                sensorMapping = SettingsFragment.getLegoNXTSensorMapping(getActivity());
                sensorMappingStrings = getResources().getStringArray(R.array.nxt_sensor_chooser);
                break;
            case 1:
                titleStringResId = R.string.lego_ev3_sensor_config_info_title;
                infoStringResId = R.string.lego_ev3_sensor_config_info_text;
                sensorMapping = SettingsFragment.getLegoEV3SensorMapping(getActivity());
                sensorMappingStrings = getResources().getStringArray(R.array.ev3_sensor_chooser);
                break;
            default:
                throw new IllegalArgumentException("LegoSensorConfigInfoDialog: Constructor called with invalid sensor type");
        }
        View dialogView = View.inflate(getActivity(), R.layout.dialog_lego_sensor_config_info, null);
        final CheckBox disableShowInfoDialog = (CheckBox) dialogView.findViewById(R.id.lego_sensor_config_info_disable_show_dialog);
        ((TextView) dialogView.findViewById(R.id.lego_sensor_config_info_text)).setText(infoStringResId);
        ((TextView) dialogView.findViewById(R.id.lego_sensor_config_info_port_1_mapping)).setText(sensorMappingStrings[sensorMapping[0].ordinal()]);
        ((TextView) dialogView.findViewById(R.id.lego_sensor_config_info_port_2_mapping)).setText(sensorMappingStrings[sensorMapping[1].ordinal()]);
        ((TextView) dialogView.findViewById(R.id.lego_sensor_config_info_port_3_mapping)).setText(sensorMappingStrings[sensorMapping[2].ordinal()]);
        ((TextView) dialogView.findViewById(R.id.lego_sensor_config_info_port_4_mapping)).setText(sensorMappingStrings[sensorMapping[3].ordinal()]);
        return new AlertDialog$Builder(getActivity()).setTitle(titleStringResId).setView(dialogView).setPositiveButton(R.string.ok, new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (disableShowInfoDialog.isChecked()) {
                    PreferenceManager.getDefaultSharedPreferences(LegoSensorConfigInfoDialog.this.getActivity()).edit().putBoolean(legoSensorType == 0 ? SettingsFragment.SETTINGS_MINDSTORMS_NXT_SHOW_SENSOR_INFO_BOX_DISABLED : SettingsFragment.SETTINGS_MINDSTORMS_EV3_SHOW_SENSOR_INFO_BOX_DISABLED, true).commit();
                }
            }
        }).create();
    }
}
