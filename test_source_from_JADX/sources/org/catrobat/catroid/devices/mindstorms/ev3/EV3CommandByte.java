package org.catrobat.catroid.devices.mindstorms.ev3;

import android.util.SparseArray;
import com.badlogic.gdx.Input.Keys;

public class EV3CommandByte {

    public enum EV3CommandByteCode {
        SOUND_PLAY_TONE(1),
        UI_WRITE_LED(27),
        UI_READ_GET_VBATT(1),
        INPUT_DEVICE_GET_FORMAT(2),
        INPUT_DEVICE_GET_TYPEMODE(4),
        INPUT_DEVICE_SETUP(9),
        INPUT_DEVICE_GET_RAW(11),
        INPUT_DEVICE_GET_CONNECTION(12),
        INPUT_DEVICE_STOP_ALL(13),
        INPUT_DEVICE_READY_RAW(28),
        INPUT_DEVICE_READY_SI(29);
        
        private int commandByteCode;

        private EV3CommandByteCode(int commandByteCode) {
            this.commandByteCode = commandByteCode;
        }

        public byte getByte() {
            return (byte) this.commandByteCode;
        }
    }

    public enum EV3CommandOpCode {
        OP_UI_READ(129),
        OP_UI_WRITE(130),
        OP_KEEP_ALIVE(144),
        OP_SOUND(148),
        OP_SOUND_TEST(Keys.NUMPAD_5),
        OP_INPUT_DEVICE(Keys.NUMPAD_9),
        OP_INPUT_READ(154),
        OP_INPUT_READ_SI(157),
        OP_OUTPUT_POWER(164),
        OP_OUTPUT_SPEED(165),
        OP_OUTPUT_START(166),
        OP_OUTPUT_STEP_SPEED(174),
        OP_OUTPUT_STEP_POWER(172),
        OP_OUTPUT_TIME_SPEED(175),
        OP_OUTPUT_TIME_POWER(173),
        OP_OUTPUT_STOP(163);
        
        private static final SparseArray<EV3CommandOpCode> LOOKUP = null;
        private int commandByteValue;

        static {
            LOOKUP = new SparseArray();
            EV3CommandOpCode[] values = values();
            int length = values.length;
            int i;
            while (i < length) {
                EV3CommandOpCode c = values[i];
                LOOKUP.put(c.commandByteValue, c);
                i++;
            }
        }

        private EV3CommandOpCode(int commandByteValue) {
            this.commandByteValue = commandByteValue;
        }

        public byte getByte() {
            return (byte) this.commandByteValue;
        }

        public static boolean isMember(byte memberToTest) {
            return LOOKUP.get(memberToTest & 255) != null;
        }

        public static EV3CommandOpCode getOpCodeByValue(byte value) {
            return (EV3CommandOpCode) LOOKUP.get(value & 255);
        }
    }

    public enum EV3CommandParamByteCode {
        PARAM_TYPE_CONSTANT(0),
        PARAM_TYPE_VARIABLE(64),
        PARAM_CONST_TYPE_VALUE(0),
        PARAM_CONST_TYPE_LABEL(32),
        PARAM_FOLLOW_ONE_BYTE(1),
        PARAM_FOLLOW_TWO_BYTE(2),
        PARAM_FOLLOW_FOUR_BYTE(3),
        PARAM_FOLLOW_TERMINATED(0),
        PARAM_FOLLOW_TERMINATED2(4),
        PARAM_SHORT_MAX(31),
        PARAM_SHORT_SIGN_POSITIVE(0),
        PARAM_SHORT_SIGN_NEGATIVE(32);
        
        private int commandParamByteCode;

        private EV3CommandParamByteCode(int commandParamByteCode) {
            this.commandParamByteCode = commandParamByteCode;
        }

        public byte getByte() {
            return (byte) this.commandParamByteCode;
        }
    }

    public enum EV3CommandParamFormat {
        PARAM_FORMAT_SHORT(0),
        PARAM_FORMAT_LONG(128);
        
        private int commandParamFormat;

        private EV3CommandParamFormat(int commandParamFormat) {
            this.commandParamFormat = commandParamFormat;
        }

        public byte getByte() {
            return (byte) this.commandParamFormat;
        }
    }

    public enum EV3CommandVariableScope {
        PARAM_VARIABLE_SCOPE_LOCAL(0),
        PARAM_VARIABLE_SCOPE_GLOBAL(32);
        
        private int variableScope;

        private EV3CommandVariableScope(int variableScope) {
            this.variableScope = variableScope;
        }

        public byte getByte() {
            return (byte) this.variableScope;
        }
    }
}
