package com.badlogic.gdx;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AnalyticsEvents;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.common.Constants;

public interface Input {

    public static class Buttons {
        public static final int BACK = 3;
        public static final int FORWARD = 4;
        public static final int LEFT = 0;
        public static final int MIDDLE = 2;
        public static final int RIGHT = 1;
    }

    public static class Keys {
        /* renamed from: A */
        public static final int f29A = 29;
        public static final int ALT_LEFT = 57;
        public static final int ALT_RIGHT = 58;
        public static final int ANY_KEY = -1;
        public static final int APOSTROPHE = 75;
        public static final int AT = 77;
        /* renamed from: B */
        public static final int f30B = 30;
        public static final int BACK = 4;
        public static final int BACKSLASH = 73;
        public static final int BACKSPACE = 67;
        public static final int BUTTON_A = 96;
        public static final int BUTTON_B = 97;
        public static final int BUTTON_C = 98;
        public static final int BUTTON_CIRCLE = 255;
        public static final int BUTTON_L1 = 102;
        public static final int BUTTON_L2 = 104;
        public static final int BUTTON_MODE = 110;
        public static final int BUTTON_R1 = 103;
        public static final int BUTTON_R2 = 105;
        public static final int BUTTON_SELECT = 109;
        public static final int BUTTON_START = 108;
        public static final int BUTTON_THUMBL = 106;
        public static final int BUTTON_THUMBR = 107;
        public static final int BUTTON_X = 99;
        public static final int BUTTON_Y = 100;
        public static final int BUTTON_Z = 101;
        /* renamed from: C */
        public static final int f31C = 31;
        public static final int CALL = 5;
        public static final int CAMERA = 27;
        public static final int CENTER = 23;
        public static final int CLEAR = 28;
        public static final int COLON = 243;
        public static final int COMMA = 55;
        public static final int CONTROL_LEFT = 129;
        public static final int CONTROL_RIGHT = 130;
        /* renamed from: D */
        public static final int f32D = 32;
        public static final int DEL = 67;
        public static final int DOWN = 20;
        public static final int DPAD_CENTER = 23;
        public static final int DPAD_DOWN = 20;
        public static final int DPAD_LEFT = 21;
        public static final int DPAD_RIGHT = 22;
        public static final int DPAD_UP = 19;
        /* renamed from: E */
        public static final int f33E = 33;
        public static final int END = 132;
        public static final int ENDCALL = 6;
        public static final int ENTER = 66;
        public static final int ENVELOPE = 65;
        public static final int EQUALS = 70;
        public static final int ESCAPE = 131;
        public static final int EXPLORER = 64;
        /* renamed from: F */
        public static final int f34F = 34;
        public static final int F1 = 244;
        public static final int F10 = 253;
        public static final int F11 = 254;
        public static final int F12 = 255;
        public static final int F2 = 245;
        public static final int F3 = 246;
        public static final int F4 = 247;
        public static final int F5 = 248;
        public static final int F6 = 249;
        public static final int F7 = 250;
        public static final int F8 = 251;
        public static final int F9 = 252;
        public static final int FOCUS = 80;
        public static final int FORWARD_DEL = 112;
        /* renamed from: G */
        public static final int f35G = 35;
        public static final int GRAVE = 68;
        /* renamed from: H */
        public static final int f36H = 36;
        public static final int HEADSETHOOK = 79;
        public static final int HOME = 3;
        /* renamed from: I */
        public static final int f37I = 37;
        public static final int INSERT = 133;
        /* renamed from: J */
        public static final int f38J = 38;
        /* renamed from: K */
        public static final int f39K = 39;
        /* renamed from: L */
        public static final int f40L = 40;
        public static final int LEFT = 21;
        public static final int LEFT_BRACKET = 71;
        /* renamed from: M */
        public static final int f41M = 41;
        public static final int MEDIA_FAST_FORWARD = 90;
        public static final int MEDIA_NEXT = 87;
        public static final int MEDIA_PLAY_PAUSE = 85;
        public static final int MEDIA_PREVIOUS = 88;
        public static final int MEDIA_REWIND = 89;
        public static final int MEDIA_STOP = 86;
        public static final int MENU = 82;
        public static final int META_ALT_LEFT_ON = 16;
        public static final int META_ALT_ON = 2;
        public static final int META_ALT_RIGHT_ON = 32;
        public static final int META_SHIFT_LEFT_ON = 64;
        public static final int META_SHIFT_ON = 1;
        public static final int META_SHIFT_RIGHT_ON = 128;
        public static final int META_SYM_ON = 4;
        public static final int MINUS = 69;
        public static final int MUTE = 91;
        /* renamed from: N */
        public static final int f42N = 42;
        public static final int NOTIFICATION = 83;
        public static final int NUM = 78;
        public static final int NUMPAD_0 = 144;
        public static final int NUMPAD_1 = 145;
        public static final int NUMPAD_2 = 146;
        public static final int NUMPAD_3 = 147;
        public static final int NUMPAD_4 = 148;
        public static final int NUMPAD_5 = 149;
        public static final int NUMPAD_6 = 150;
        public static final int NUMPAD_7 = 151;
        public static final int NUMPAD_8 = 152;
        public static final int NUMPAD_9 = 153;
        public static final int NUM_0 = 7;
        public static final int NUM_1 = 8;
        public static final int NUM_2 = 9;
        public static final int NUM_3 = 10;
        public static final int NUM_4 = 11;
        public static final int NUM_5 = 12;
        public static final int NUM_6 = 13;
        public static final int NUM_7 = 14;
        public static final int NUM_8 = 15;
        public static final int NUM_9 = 16;
        /* renamed from: O */
        public static final int f43O = 43;
        /* renamed from: P */
        public static final int f44P = 44;
        public static final int PAGE_DOWN = 93;
        public static final int PAGE_UP = 92;
        public static final int PERIOD = 56;
        public static final int PICTSYMBOLS = 94;
        public static final int PLUS = 81;
        public static final int POUND = 18;
        public static final int POWER = 26;
        /* renamed from: Q */
        public static final int f45Q = 45;
        /* renamed from: R */
        public static final int f46R = 46;
        public static final int RIGHT = 22;
        public static final int RIGHT_BRACKET = 72;
        /* renamed from: S */
        public static final int f47S = 47;
        public static final int SEARCH = 84;
        public static final int SEMICOLON = 74;
        public static final int SHIFT_LEFT = 59;
        public static final int SHIFT_RIGHT = 60;
        public static final int SLASH = 76;
        public static final int SOFT_LEFT = 1;
        public static final int SOFT_RIGHT = 2;
        public static final int SPACE = 62;
        public static final int STAR = 17;
        public static final int SWITCH_CHARSET = 95;
        public static final int SYM = 63;
        /* renamed from: T */
        public static final int f48T = 48;
        public static final int TAB = 61;
        /* renamed from: U */
        public static final int f49U = 49;
        public static final int UNKNOWN = 0;
        public static final int UP = 19;
        /* renamed from: V */
        public static final int f50V = 50;
        public static final int VOLUME_DOWN = 25;
        public static final int VOLUME_UP = 24;
        /* renamed from: W */
        public static final int f51W = 51;
        /* renamed from: X */
        public static final int f52X = 52;
        /* renamed from: Y */
        public static final int f53Y = 53;
        /* renamed from: Z */
        public static final int f54Z = 54;
        private static ObjectIntMap<String> keyNames;

        public static String toString(int keycode) {
            StringBuilder stringBuilder;
            if (keycode < 0) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("keycode cannot be negative, keycode: ");
                stringBuilder.append(keycode);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (keycode > 255) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("keycode cannot be greater than 255, keycode: ");
                stringBuilder.append(keycode);
                throw new IllegalArgumentException(stringBuilder.toString());
            } else if (keycode == 112) {
                return "Forward Delete";
            } else {
                switch (keycode) {
                    case 0:
                        return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                    case 1:
                        return "Soft Left";
                    case 2:
                        return "Soft Right";
                    case 3:
                        return "Home";
                    case 4:
                        return "Back";
                    case 5:
                        return "Call";
                    case 6:
                        return "End Call";
                    case 7:
                        return AppEventsConstants.EVENT_PARAM_VALUE_NO;
                    case 8:
                        return AppEventsConstants.EVENT_PARAM_VALUE_YES;
                    case 9:
                        return "2";
                    case 10:
                        return "3";
                    case 11:
                        return "4";
                    case 12:
                        return "5";
                    case 13:
                        return "6";
                    case 14:
                        return "7";
                    case 15:
                        return "8";
                    case 16:
                        return "9";
                    case 17:
                        return "*";
                    case 18:
                        return Constants.ACTION_SPRITE_SEPARATOR;
                    case 19:
                        return "Up";
                    case 20:
                        return "Down";
                    case 21:
                        return "Left";
                    case 22:
                        return "Right";
                    case 23:
                        return "Center";
                    case 24:
                        return "Volume Up";
                    case 25:
                        return "Volume Down";
                    case 26:
                        return "Power";
                    case 27:
                        return "Camera";
                    case 28:
                        return "Clear";
                    case 29:
                        return BrickValues.LEGO_MOTOR;
                    case 30:
                        return "B";
                    case 31:
                        return "C";
                    case 32:
                        return "D";
                    case 33:
                        return "E";
                    case 34:
                        return "F";
                    case 35:
                        return "G";
                    case 36:
                        return "H";
                    case 37:
                        return "I";
                    case 38:
                        return "J";
                    case 39:
                        return "K";
                    case 40:
                        return "L";
                    case 41:
                        return "M";
                    case 42:
                        return "N";
                    case 43:
                        return "O";
                    case 44:
                        return "P";
                    case 45:
                        return "Q";
                    case 46:
                        return "R";
                    case 47:
                        return "S";
                    case 48:
                        return "T";
                    case 49:
                        return "U";
                    case 50:
                        return "V";
                    case 51:
                        return "W";
                    case 52:
                        return "X";
                    case 53:
                        return "Y";
                    case 54:
                        return "Z";
                    case 55:
                        return ",";
                    case 56:
                        return ".";
                    case 57:
                        return "L-Alt";
                    case 58:
                        return "R-Alt";
                    case 59:
                        return "L-Shift";
                    case 60:
                        return "R-Shift";
                    case 61:
                        return "Tab";
                    case 62:
                        return "Space";
                    case 63:
                        return "SYM";
                    case 64:
                        return "Explorer";
                    case 65:
                        return "Envelope";
                    case 66:
                        return "Enter";
                    case 67:
                        return "Delete";
                    case 68:
                        return "`";
                    case 69:
                        return "-";
                    case 70:
                        return "=";
                    case 71:
                        return "[";
                    case 72:
                        return "]";
                    case 73:
                        return "\\";
                    case 74:
                        return ";";
                    case 75:
                        return FormatHelper.QUOTE;
                    case 76:
                        return "/";
                    case 77:
                        return "@";
                    case 78:
                        return "Num";
                    case 79:
                        return "Headset Hook";
                    case 80:
                        return "Focus";
                    case 81:
                        return "Plus";
                    case 82:
                        return "Menu";
                    case 83:
                        return "Notification";
                    case 84:
                        return "Search";
                    case 85:
                        return "Play/Pause";
                    case 86:
                        return "Stop Media";
                    case 87:
                        return "Next Media";
                    case 88:
                        return "Prev Media";
                    case 89:
                        return "Rewind";
                    case 90:
                        return "Fast Forward";
                    case 91:
                        return "Mute";
                    case 92:
                        return "Page Up";
                    case 93:
                        return "Page Down";
                    case 94:
                        return "PICTSYMBOLS";
                    case 95:
                        return "SWITCH_CHARSET";
                    case 96:
                        return "A Button";
                    case 97:
                        return "B Button";
                    case 98:
                        return "C Button";
                    case 99:
                        return "X Button";
                    case 100:
                        return "Y Button";
                    case 101:
                        return "Z Button";
                    case 102:
                        return "L1 Button";
                    case 103:
                        return "R1 Button";
                    case 104:
                        return "L2 Button";
                    case 105:
                        return "R2 Button";
                    case 106:
                        return "Left Thumb";
                    case 107:
                        return "Right Thumb";
                    case 108:
                        return "Start";
                    case 109:
                        return "Select";
                    case 110:
                        return "Button Mode";
                    default:
                        switch (keycode) {
                            case 129:
                                return "L-Ctrl";
                            case 130:
                                return "R-Ctrl";
                            case 131:
                                return "Escape";
                            case 132:
                                return "End";
                            case 133:
                                return "Insert";
                            default:
                                switch (keycode) {
                                    case 144:
                                        return "Numpad 0";
                                    case NUMPAD_1 /*145*/:
                                        return "Numpad 1";
                                    case NUMPAD_2 /*146*/:
                                        return "Numpad 2";
                                    case NUMPAD_3 /*147*/:
                                        return "Numpad 3";
                                    case 148:
                                        return "Numpad 4";
                                    case NUMPAD_5 /*149*/:
                                        return "Numpad 5";
                                    case NUMPAD_6 /*150*/:
                                        return "Numpad 6";
                                    case NUMPAD_7 /*151*/:
                                        return "Numpad 7";
                                    case NUMPAD_8 /*152*/:
                                        return "Numpad 8";
                                    case NUMPAD_9 /*153*/:
                                        return "Numpad 9";
                                    default:
                                        switch (keycode) {
                                            case COLON /*243*/:
                                                return ":";
                                            case 244:
                                                return "F1";
                                            case F2 /*245*/:
                                                return "F2";
                                            case F3 /*246*/:
                                                return "F3";
                                            case 247:
                                                return "F4";
                                            case F5 /*248*/:
                                                return "F5";
                                            case 249:
                                                return "F6";
                                            case 250:
                                                return "F7";
                                            case F8 /*251*/:
                                                return "F8";
                                            case F9 /*252*/:
                                                return "F9";
                                            case F10 /*253*/:
                                                return "F10";
                                            case F11 /*254*/:
                                                return "F11";
                                            case 255:
                                                return "F12";
                                            default:
                                                return null;
                                        }
                                }
                        }
                }
            }
        }

        public static int valueOf(String keyname) {
            if (keyNames == null) {
                initializeKeyNames();
            }
            return keyNames.get(keyname, -1);
        }

        private static void initializeKeyNames() {
            keyNames = new ObjectIntMap();
            for (int i = 0; i < 256; i++) {
                String name = toString(i);
                if (name != null) {
                    keyNames.put(name, i);
                }
            }
        }
    }

    public enum Orientation {
        Landscape,
        Portrait
    }

    public enum Peripheral {
        HardwareKeyboard,
        OnscreenKeyboard,
        MultitouchScreen,
        Accelerometer,
        Compass,
        Vibrator
    }

    public interface TextInputListener {
        void canceled();

        void input(String str);
    }

    void cancelVibrate();

    float getAccelerometerX();

    float getAccelerometerY();

    float getAccelerometerZ();

    float getAzimuth();

    long getCurrentEventTime();

    int getDeltaX();

    int getDeltaX(int i);

    int getDeltaY();

    int getDeltaY(int i);

    InputProcessor getInputProcessor();

    Orientation getNativeOrientation();

    float getPitch();

    float getRoll();

    int getRotation();

    void getRotationMatrix(float[] fArr);

    void getTextInput(TextInputListener textInputListener, String str, String str2, String str3);

    int getX();

    int getX(int i);

    int getY();

    int getY(int i);

    boolean isButtonPressed(int i);

    boolean isCatchBackKey();

    boolean isCursorCatched();

    boolean isKeyJustPressed(int i);

    boolean isKeyPressed(int i);

    boolean isPeripheralAvailable(Peripheral peripheral);

    boolean isTouched();

    boolean isTouched(int i);

    boolean justTouched();

    void setCatchBackKey(boolean z);

    void setCatchMenuKey(boolean z);

    void setCursorCatched(boolean z);

    void setCursorImage(Pixmap pixmap, int i, int i2);

    void setCursorPosition(int i, int i2);

    void setInputProcessor(InputProcessor inputProcessor);

    void setOnscreenKeyboardVisible(boolean z);

    void vibrate(int i);

    void vibrate(long[] jArr, int i);
}
