package org.catrobat.catroid.formulaeditor;

import android.util.Log;

public enum Operators {
    LOGICAL_AND(2, true),
    LOGICAL_OR(1, true),
    EQUAL(3, true),
    NOT_EQUAL(4, true),
    SMALLER_OR_EQUAL(4, true),
    GREATER_OR_EQUAL(4, true),
    SMALLER_THAN(4, true),
    GREATER_THAN(4, true),
    PLUS(5),
    MINUS(5),
    MULT(6),
    DIVIDE(6),
    MOD(6),
    POW(7),
    LOGICAL_NOT(4, true);
    
    private static final String TAG = null;
    public final boolean isLogicalOperator;
    private final int priority;

    static {
        TAG = Operators.class.getSimpleName();
    }

    private Operators(int priority) {
        this.priority = priority;
        this.isLogicalOperator = null;
    }

    private Operators(int priority, boolean isLogical) {
        this.priority = priority;
        this.isLogicalOperator = isLogical;
    }

    public int compareOperatorTo(Operators operator) {
        if (this.priority > operator.priority) {
            return 1;
        }
        if (this.priority == operator.priority) {
            return 0;
        }
        if (this.priority < operator.priority) {
            return -1;
        }
        return 0;
    }

    public static Operators getOperatorByValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException illegalArgumentException) {
            Log.e(TAG, Log.getStackTraceString(illegalArgumentException));
            return null;
        }
    }

    public static boolean isOperator(String value) {
        return getOperatorByValue(value) != null;
    }
}
