package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class RepeatUntilAction extends RepeatAction {
    private static final float LOOP_DELAY = 0.02f;
    private float currentTime = 0.0f;
    private int executedCount = 0;
    private boolean isCurrentLoopInitialized = false;
    private Formula repeatCondition;
    private Sprite sprite;

    boolean isValidConditionFormula() {
        try {
            if (this.repeatCondition == null) {
                return false;
            }
            this.repeatCondition.interpretDouble(this.sprite);
            return true;
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            return false;
        }
    }

    boolean isConditionTrue() {
        boolean z = true;
        try {
            if (this.repeatCondition.interpretDouble(this.sprite).intValue() == 0) {
                z = false;
            }
            return z;
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            return true;
        }
    }

    public boolean delegate(float delta) {
        if (!isValidConditionFormula()) {
            return true;
        }
        if (!this.isCurrentLoopInitialized) {
            if (isConditionTrue()) {
                return true;
            }
            this.currentTime = 0.0f;
            this.isCurrentLoopInitialized = true;
        }
        this.currentTime += delta;
        if (this.action.act(delta) && this.currentTime >= LOOP_DELAY) {
            this.executedCount++;
            if (isConditionTrue()) {
                return true;
            }
            this.isCurrentLoopInitialized = false;
            if (this.action != null) {
                this.action.restart();
            }
        }
        return false;
    }

    public void restart() {
        this.isCurrentLoopInitialized = false;
        this.executedCount = 0;
        super.restart();
    }

    public void setRepeatCondition(Formula repeatCondition) {
        this.repeatCondition = repeatCondition;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getExecutedCount() {
        return this.executedCount;
    }
}
