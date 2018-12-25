package com.parrot.arsdk.arcommands;

public interface ARCommandRcCalibrationStateListener {
    void onRcCalibrationStateUpdate(ARCOMMANDS_RC_CALIBRATION_TYPE_ENUM arcommands_rc_calibration_type_enum, ARCOMMANDS_RC_CHANNEL_ACTION_ENUM arcommands_rc_channel_action_enum, int i, int i2, byte b);
}
