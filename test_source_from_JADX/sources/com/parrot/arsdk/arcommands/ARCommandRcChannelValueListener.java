package com.parrot.arsdk.arcommands;

public interface ARCommandRcChannelValueListener {
    void onRcChannelValueUpdate(byte b, ARCOMMANDS_RC_CHANNEL_ACTION_ENUM arcommands_rc_channel_action_enum, short s, byte b2);
}
