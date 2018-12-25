package org.catrobat.catroid.content;

import android.util.Log;
import org.catrobat.catroid.content.eventids.WhenConditionEventId;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class ConditionScriptTrigger {
    static final int ALREADY_TRIGGERED = 1;
    private static final String TAG = ConditionScriptTrigger.class.getSimpleName();
    static final int TRIGGER_NOW = 0;
    private final Formula formula;
    private int status = 0;

    ConditionScriptTrigger(Formula formula) {
        this.formula = formula;
    }

    void evaluateAndTriggerActions(Sprite sprite) {
        try {
            if (this.formula.interpretBoolean(sprite).booleanValue()) {
                triggerScript(sprite);
            } else {
                this.status = 0;
            }
        } catch (InterpretationException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void triggerScript(Sprite sprite) {
        if (this.status == 0) {
            sprite.look.fire(new EventWrapper(new WhenConditionEventId(this.formula), 1));
            this.status = 1;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConditionScriptTrigger)) {
            return false;
        }
        return this.formula.equals(((ConditionScriptTrigger) o).formula);
    }

    public int hashCode() {
        return this.formula.hashCode();
    }
}
