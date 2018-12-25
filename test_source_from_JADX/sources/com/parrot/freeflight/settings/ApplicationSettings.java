package com.parrot.freeflight.settings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.util.Log;
import java.util.Hashtable;
import java.util.Map;

public class ApplicationSettings {
    private static final int DEFAULT_CONTROL_MODE = ApplicationSettings$ControlMode.ACCELERO_MODE.ordinal();
    public static final int DEFAULT_INTERFACE_OPACITY = 50;
    public static final boolean DEFAULT_LOOPING_ENABLED = false;
    private static final boolean DEFAULT_MAGNETO_ENABLED = false;
    private static final String[] GPU_BLACK_LIST_GPU_FROYO = new String[]{"Adreno"};
    public static final int INTERFACE_OPACITY_MAX = 100;
    public static final int INTERFACE_OPACITY_MIN = 0;
    public static final long MEMORY_USAGE = 0;
    private static final String NAME = "Preferences";
    private static final String TAG = "ApplicationSettings";
    private Map<Integer, String[]> blacklist = new Hashtable();
    private SharedPreferences prefs;
    private boolean useOpenGL;

    public ApplicationSettings(Context context) {
        this.prefs = context.getSharedPreferences(NAME, 0);
        this.blacklist.put(Integer.valueOf(8), GPU_BLACK_LIST_GPU_FROYO);
    }

    @SuppressLint({"NewApi"})
    protected void applyChanges(Editor editor) {
        if (VERSION.SDK_INT >= 9) {
            editor.apply();
        } else if (!editor.commit()) {
            Log.w(TAG, "Can't save properties. Can't commit.");
        }
    }

    @Deprecated
    public boolean isUseOpenGL() {
        return this.useOpenGL;
    }

    @Deprecated
    public void setUseOpenGL(boolean useOpenGL) {
        this.useOpenGL = useOpenGL;
    }

    public boolean isLeftHanded() {
        return getProperty(ApplicationSettings$EAppSettingProperty.LEFT_HANDED_PROP, false);
    }

    public void setLeftHanded(boolean leftHanded) {
        saveProperty(ApplicationSettings$EAppSettingProperty.LEFT_HANDED_PROP, leftHanded);
    }

    public ApplicationSettings$ControlMode getControlMode() {
        return ApplicationSettings$ControlMode.values()[getProperty(ApplicationSettings$EAppSettingProperty.CONTROL_MODE_PROP, DEFAULT_CONTROL_MODE)];
    }

    public void setControlMode(ApplicationSettings$ControlMode controlMode) {
        saveProperty(ApplicationSettings$EAppSettingProperty.CONTROL_MODE_PROP, controlMode.ordinal());
    }

    public String[] getGpuBlackList(int androidVersion) {
        if (this.blacklist.containsKey(Integer.valueOf(androidVersion))) {
            return (String[]) this.blacklist.get(Integer.valueOf(androidVersion));
        }
        return null;
    }

    public void setAbsoluteControlEnabled(boolean enabled) {
        saveProperty(ApplicationSettings$EAppSettingProperty.MAGNETO_ENABLED_PROP, enabled);
    }

    public boolean isAbsoluteControlEnabled() {
        return getProperty(ApplicationSettings$EAppSettingProperty.MAGNETO_ENABLED_PROP, false);
    }

    public int getInterfaceOpacity() {
        return getProperty(ApplicationSettings$EAppSettingProperty.INTERFACE_OPACITY_PROP, 50);
    }

    public void setInterfaceOpacity(int opacity) {
        if (opacity >= 0) {
            if (opacity <= 100) {
                saveProperty(ApplicationSettings$EAppSettingProperty.INTERFACE_OPACITY_PROP, opacity);
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean isFirstLaunch() {
        return getProperty(ApplicationSettings$EAppSettingProperty.FIRST_LAUNCH_PROP, true);
    }

    public boolean isFlipEnabled() {
        return getProperty(ApplicationSettings$EAppSettingProperty.LOOPING_ENABLED_PROP, false);
    }

    public void setFlipEnabled(boolean enabled) {
        saveProperty(ApplicationSettings$EAppSettingProperty.LOOPING_ENABLED_PROP, enabled);
    }

    public void setFirstLaunch(boolean firstLaunch) {
        saveProperty(ApplicationSettings$EAppSettingProperty.FIRST_LAUNCH_PROP, firstLaunch);
    }

    @Deprecated
    public void setForceCombinedControl(boolean forceCombinedControl) {
        saveProperty(ApplicationSettings$EAppSettingProperty.FORCE_COMBINED_CTRL_PROP, forceCombinedControl);
    }

    @Deprecated
    public boolean isCombinedControlForced() {
        return getProperty(ApplicationSettings$EAppSettingProperty.FORCE_COMBINED_CTRL_PROP, false);
    }

    public void setAskForGPS(boolean show) {
        saveProperty(ApplicationSettings$EAppSettingProperty.ASK_FOR_GPS, show);
    }

    public boolean isAskForGPS() {
        return getProperty(ApplicationSettings$EAppSettingProperty.ASK_FOR_GPS, true);
    }

    protected void saveProperty(ApplicationSettings$EAppSettingProperty property, boolean value) {
        Editor editor = this.prefs.edit();
        editor.putBoolean(property.toString(), value);
        applyChanges(editor);
    }

    protected void saveProperty(ApplicationSettings$EAppSettingProperty property, int value) {
        Editor editor = this.prefs.edit();
        editor.putInt(property.toString(), value);
        applyChanges(editor);
    }

    protected boolean getProperty(ApplicationSettings$EAppSettingProperty property, boolean defValue) {
        return this.prefs.getBoolean(property.toString(), defValue);
    }

    protected int getProperty(ApplicationSettings$EAppSettingProperty property, int defValue) {
        return this.prefs.getInt(property.toString(), defValue);
    }
}
