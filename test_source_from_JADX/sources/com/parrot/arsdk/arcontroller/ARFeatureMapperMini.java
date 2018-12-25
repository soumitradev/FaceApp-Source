package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM;

public class ARFeatureMapperMini {
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_ACTION;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_AXIS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_BUTTONS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_MODES;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_UID;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_ACTION;
    /* renamed from: ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_BUTTONS */
    public static String f1561xbe213150;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_MODES;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_UID;
    private static String TAG = "ARFeatureMapperMini";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendMapAxisAction(long j, byte b, int i, byte b2, int i2);

    private native int nativeSendMapButtonAction(long j, byte b, int i, int i2);

    private native int nativeSendResetMapping(long j, byte b);

    private static native String nativeStaticGetKeyMapperMiniAxisMappingItemAction();

    private static native String nativeStaticGetKeyMapperMiniAxisMappingItemAxis();

    private static native String nativeStaticGetKeyMapperMiniAxisMappingItemButtons();

    private static native String nativeStaticGetKeyMapperMiniAxisMappingItemListflags();

    private static native String nativeStaticGetKeyMapperMiniAxisMappingItemModes();

    private static native String nativeStaticGetKeyMapperMiniAxisMappingItemUid();

    private static native String nativeStaticGetKeyMapperMiniButtonMappingItemAction();

    private static native String nativeStaticGetKeyMapperMiniButtonMappingItemButtons();

    private static native String nativeStaticGetKeyMapperMiniButtonMappingItemListflags();

    private static native String nativeStaticGetKeyMapperMiniButtonMappingItemModes();

    private static native String nativeStaticGetKeyMapperMiniButtonMappingItemUid();

    static {
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_UID = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_MODES = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_ACTION = "";
        f1561xbe213150 = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_UID = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_MODES = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_ACTION = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_AXIS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_BUTTONS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_UID = nativeStaticGetKeyMapperMiniButtonMappingItemUid();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_MODES = nativeStaticGetKeyMapperMiniButtonMappingItemModes();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_BUTTONMAPPINGITEM_ACTION = nativeStaticGetKeyMapperMiniButtonMappingItemAction();
        f1561xbe213150 = nativeStaticGetKeyMapperMiniButtonMappingItemButtons();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_UID = nativeStaticGetKeyMapperMiniAxisMappingItemUid();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_MODES = nativeStaticGetKeyMapperMiniAxisMappingItemModes();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_ACTION = nativeStaticGetKeyMapperMiniAxisMappingItemAction();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_AXIS = nativeStaticGetKeyMapperMiniAxisMappingItemAxis();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_MINI_AXISMAPPINGITEM_BUTTONS = nativeStaticGetKeyMapperMiniAxisMappingItemButtons();
    }

    public ARFeatureMapperMini(long nativeFeature) {
        if (nativeFeature != 0) {
            this.jniFeature = nativeFeature;
            this.initOk = true;
        }
    }

    public void dispose() {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                this.jniFeature = 0;
                this.initOk = false;
            }
        }
    }

    public void finalize() throws Throwable {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    public ARCONTROLLER_ERROR_ENUM sendMapButtonAction(byte _modes, ARCOMMANDS_MAPPER_MINI_BUTTON_ACTION_ENUM _action, int _buttons) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMapButtonAction(this.jniFeature, _modes, _action.getValue(), _buttons));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMapAxisAction(byte _modes, ARCOMMANDS_MAPPER_MINI_AXIS_ACTION_ENUM _action, byte _axis, int _buttons) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMapAxisAction(this.jniFeature, _modes, _action.getValue(), _axis, _buttons));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendResetMapping(byte _modes) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendResetMapping(this.jniFeature, _modes));
            }
        }
        return error;
    }
}
