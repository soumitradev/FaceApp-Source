package org.catrobat.catroid.formulaeditor;

import android.util.Log;

public enum Functions {
    SIN,
    COS,
    TAN,
    LN,
    LOG,
    SQRT,
    RAND,
    ROUND,
    ABS,
    PI,
    MOD,
    ARCSIN,
    ARCCOS,
    ARCTAN,
    EXP,
    POWER,
    FLOOR,
    CEIL,
    MAX,
    MIN,
    TRUE,
    FALSE,
    LENGTH,
    LETTER,
    JOIN,
    LIST_ITEM,
    CONTAINS,
    NUMBER_OF_ITEMS,
    ARDUINOANALOG,
    ARDUINODIGITAL,
    RASPIDIGITAL,
    MULTI_FINGER_X,
    MULTI_FINGER_Y,
    MULTI_FINGER_TOUCHED;
    
    private static final String TAG = null;

    static {
        TAG = Functions.class.getSimpleName();
    }

    public static boolean isFunction(String value) {
        return getFunctionByValue(value) != null;
    }

    public static Functions getFunctionByValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, Log.getStackTraceString(illegalArgumentException));
            return null;
        }
    }
}
