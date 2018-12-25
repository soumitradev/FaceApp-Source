package com.parrot.arsdk.arcommands;

public interface ARCommandRcReceiverStateListener {
    void onRcReceiverStateUpdate(ARCOMMANDS_RC_RECEIVER_STATE_ENUM arcommands_rc_receiver_state_enum, String str, byte b);
}
