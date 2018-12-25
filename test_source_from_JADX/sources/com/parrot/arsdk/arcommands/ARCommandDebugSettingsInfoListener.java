package com.parrot.arsdk.arcommands;

public interface ARCommandDebugSettingsInfoListener {
    void onDebugSettingsInfoUpdate(byte b, short s, String str, ARCOMMANDS_DEBUG_SETTING_TYPE_ENUM arcommands_debug_setting_type_enum, ARCOMMANDS_DEBUG_SETTING_MODE_ENUM arcommands_debug_setting_mode_enum, String str2, String str3, String str4, String str5);
}
