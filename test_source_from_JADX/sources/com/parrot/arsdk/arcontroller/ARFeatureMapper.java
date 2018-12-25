package com.parrot.arsdk.arcontroller;

import com.parrot.arsdk.arcommands.ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM;

public class ARFeatureMapper {
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_ACTIVEPRODUCT_PRODUCT;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONAXISEVENT_ACTION;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONAXISEVENT_VALUE;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONBUTTONEVENT_ACTION;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_ACTION;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_AXIS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_BUTTONS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_PRODUCT;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_UID;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_ACTION;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_BUTTONS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_PRODUCT;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_UID;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_AXIS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_EXPO;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_PRODUCT;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_UID;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABAXISEVENT_AXIS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABAXISEVENT_VALUE;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABBUTTONEVENT_BUTTON;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABBUTTONEVENT_EVENT;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_AXES;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_BUTTONS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_BUTTONS_STATE;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_AXIS;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_INVERTED;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_PRODUCT;
    public static String ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_UID;
    private static String TAG = "ARFeatureMapper";
    private boolean initOk = false;
    private long jniFeature;

    private native int nativeSendGrab(long j, int i, int i2);

    private native int nativeSendMapAxisAction(long j, short s, int i, int i2, int i3);

    private native int nativeSendMapButtonAction(long j, short s, int i, int i2);

    private native int nativeSendResetMapping(long j, short s);

    private native int nativeSendSetExpo(long j, short s, int i, int i2);

    private native int nativeSendSetInverted(long j, short s, int i, byte b);

    private static native String nativeStaticGetKeyMapperActiveProductProduct();

    private static native String nativeStaticGetKeyMapperApplicationAxisEventAction();

    private static native String nativeStaticGetKeyMapperApplicationAxisEventValue();

    private static native String nativeStaticGetKeyMapperApplicationButtonEventAction();

    private static native String nativeStaticGetKeyMapperAxisMappingItemAction();

    private static native String nativeStaticGetKeyMapperAxisMappingItemAxis();

    private static native String nativeStaticGetKeyMapperAxisMappingItemButtons();

    private static native String nativeStaticGetKeyMapperAxisMappingItemListflags();

    private static native String nativeStaticGetKeyMapperAxisMappingItemProduct();

    private static native String nativeStaticGetKeyMapperAxisMappingItemUid();

    private static native String nativeStaticGetKeyMapperButtonMappingItemAction();

    private static native String nativeStaticGetKeyMapperButtonMappingItemButtons();

    private static native String nativeStaticGetKeyMapperButtonMappingItemListflags();

    private static native String nativeStaticGetKeyMapperButtonMappingItemProduct();

    private static native String nativeStaticGetKeyMapperButtonMappingItemUid();

    private static native String nativeStaticGetKeyMapperExpoMapItemAxis();

    private static native String nativeStaticGetKeyMapperExpoMapItemExpo();

    private static native String nativeStaticGetKeyMapperExpoMapItemListflags();

    private static native String nativeStaticGetKeyMapperExpoMapItemProduct();

    private static native String nativeStaticGetKeyMapperExpoMapItemUid();

    private static native String nativeStaticGetKeyMapperGrabAxisEventAxis();

    private static native String nativeStaticGetKeyMapperGrabAxisEventValue();

    private static native String nativeStaticGetKeyMapperGrabButtonEventButton();

    private static native String nativeStaticGetKeyMapperGrabButtonEventEvent();

    private static native String nativeStaticGetKeyMapperGrabStateAxes();

    private static native String nativeStaticGetKeyMapperGrabStateButtons();

    private static native String nativeStaticGetKeyMapperGrabStateButtonsstate();

    private static native String nativeStaticGetKeyMapperInvertedMapItemAxis();

    private static native String nativeStaticGetKeyMapperInvertedMapItemInverted();

    private static native String nativeStaticGetKeyMapperInvertedMapItemListflags();

    private static native String nativeStaticGetKeyMapperInvertedMapItemProduct();

    private static native String nativeStaticGetKeyMapperInvertedMapItemUid();

    static {
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_BUTTONS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_AXES = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_BUTTONS_STATE = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABBUTTONEVENT_BUTTON = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABBUTTONEVENT_EVENT = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABAXISEVENT_AXIS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABAXISEVENT_VALUE = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_UID = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_PRODUCT = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_ACTION = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_BUTTONS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_UID = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_PRODUCT = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_ACTION = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_AXIS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_BUTTONS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONAXISEVENT_ACTION = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONAXISEVENT_VALUE = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONBUTTONEVENT_ACTION = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_UID = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_PRODUCT = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_AXIS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_EXPO = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_UID = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_PRODUCT = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_AXIS = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_INVERTED = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_ACTIVEPRODUCT_PRODUCT = "";
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_BUTTONS = nativeStaticGetKeyMapperGrabStateButtons();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_AXES = nativeStaticGetKeyMapperGrabStateAxes();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABSTATE_BUTTONS_STATE = nativeStaticGetKeyMapperGrabStateButtonsstate();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABBUTTONEVENT_BUTTON = nativeStaticGetKeyMapperGrabButtonEventButton();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABBUTTONEVENT_EVENT = nativeStaticGetKeyMapperGrabButtonEventEvent();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABAXISEVENT_AXIS = nativeStaticGetKeyMapperGrabAxisEventAxis();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_GRABAXISEVENT_VALUE = nativeStaticGetKeyMapperGrabAxisEventValue();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_UID = nativeStaticGetKeyMapperButtonMappingItemUid();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_PRODUCT = nativeStaticGetKeyMapperButtonMappingItemProduct();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_ACTION = nativeStaticGetKeyMapperButtonMappingItemAction();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_BUTTONMAPPINGITEM_BUTTONS = nativeStaticGetKeyMapperButtonMappingItemButtons();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_UID = nativeStaticGetKeyMapperAxisMappingItemUid();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_PRODUCT = nativeStaticGetKeyMapperAxisMappingItemProduct();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_ACTION = nativeStaticGetKeyMapperAxisMappingItemAction();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_AXIS = nativeStaticGetKeyMapperAxisMappingItemAxis();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_AXISMAPPINGITEM_BUTTONS = nativeStaticGetKeyMapperAxisMappingItemButtons();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONAXISEVENT_ACTION = nativeStaticGetKeyMapperApplicationAxisEventAction();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONAXISEVENT_VALUE = nativeStaticGetKeyMapperApplicationAxisEventValue();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_APPLICATIONBUTTONEVENT_ACTION = nativeStaticGetKeyMapperApplicationButtonEventAction();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_UID = nativeStaticGetKeyMapperExpoMapItemUid();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_PRODUCT = nativeStaticGetKeyMapperExpoMapItemProduct();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_AXIS = nativeStaticGetKeyMapperExpoMapItemAxis();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_EXPOMAPITEM_EXPO = nativeStaticGetKeyMapperExpoMapItemExpo();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_UID = nativeStaticGetKeyMapperInvertedMapItemUid();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_PRODUCT = nativeStaticGetKeyMapperInvertedMapItemProduct();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_AXIS = nativeStaticGetKeyMapperInvertedMapItemAxis();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_INVERTEDMAPITEM_INVERTED = nativeStaticGetKeyMapperInvertedMapItemInverted();
        ARCONTROLLER_DICTIONARY_KEY_MAPPER_ACTIVEPRODUCT_PRODUCT = nativeStaticGetKeyMapperActiveProductProduct();
    }

    public ARFeatureMapper(long nativeFeature) {
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

    public ARCONTROLLER_ERROR_ENUM sendGrab(int _buttons, int _axes) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendGrab(this.jniFeature, _buttons, _axes));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMapButtonAction(short _product, ARCOMMANDS_MAPPER_BUTTON_ACTION_ENUM _action, int _buttons) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMapButtonAction(this.jniFeature, _product, _action.getValue(), _buttons));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendMapAxisAction(short _product, ARCOMMANDS_MAPPER_AXIS_ACTION_ENUM _action, int _axis, int _buttons) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendMapAxisAction(this.jniFeature, _product, _action.getValue(), _axis, _buttons));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendResetMapping(short _product) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendResetMapping(this.jniFeature, _product));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSetExpo(short _product, int _axis, ARCOMMANDS_MAPPER_EXPO_TYPE_ENUM _expo) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSetExpo(this.jniFeature, _product, _axis, _expo.getValue()));
            }
        }
        return error;
    }

    public ARCONTROLLER_ERROR_ENUM sendSetInverted(short _product, int _axis, byte _inverted) {
        ARCONTROLLER_ERROR_ENUM error = ARCONTROLLER_ERROR_ENUM.ARCONTROLLER_OK;
        synchronized (this) {
            if (this.initOk) {
                error = ARCONTROLLER_ERROR_ENUM.getFromValue(nativeSendSetInverted(this.jniFeature, _product, _axis, _inverted));
            }
        }
        return error;
    }
}
