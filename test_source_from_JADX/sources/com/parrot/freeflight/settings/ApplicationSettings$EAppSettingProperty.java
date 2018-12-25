package com.parrot.freeflight.settings;

public enum ApplicationSettings$EAppSettingProperty {
    FIRST_LAUNCH_PROP("first_launch"),
    LEFT_HANDED_PROP("left_handed"),
    FORCE_COMBINED_CTRL_PROP("force_combined_control"),
    CONTROL_MODE_PROP("control_mode"),
    INTERFACE_OPACITY_PROP("interface_opacity"),
    MAGNETO_ENABLED_PROP("magneto_enabled"),
    LOOPING_ENABLED_PROP("looping_enabled"),
    ASK_FOR_GPS("ask_for_gps");
    
    private final String propname;

    private ApplicationSettings$EAppSettingProperty(String propname) {
        this.propname = propname;
    }

    public String toString() {
        return this.propname;
    }
}
