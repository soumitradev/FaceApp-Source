package com.parrot.arsdk.arcommands;

public interface ARCommandRcStartCalibrationListener {
    void onRcStartCalibrationUpdate(ARCOMMANDS_RC_CALIBRATION_TYPE_ENUM arcommands_rc_calibration_type_enum, ARCOMMANDS_RC_CHANNEL_ACTION_ENUM arcommands_rc_channel_action_enum, ARCOMMANDS_RC_CHANNEL_TYPE_ENUM arcommands_rc_channel_type_enum);
}
