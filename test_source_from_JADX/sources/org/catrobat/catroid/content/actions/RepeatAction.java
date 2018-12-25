package org.catrobat.catroid.content.actions;

import android.util.Log;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class RepeatAction extends com.badlogic.gdx.scenes.scene2d.actions.RepeatAction {
    private static final float LOOP_DELAY = 0.02f;
    private float currentTime = 0.0f;
    private int executedCount;
    private boolean isCurrentLoopInitialized = false;
    private boolean isForeverRepeat = false;
    private boolean isRepeatActionInitialized = false;
    private Formula repeatCount;
    private int repeatCountValue;
    private Sprite sprite;

    public boolean delegate(float delta) {
        if (!this.isRepeatActionInitialized) {
            this.isRepeatActionInitialized = true;
            try {
                this.repeatCountValue = (this.repeatCount == null ? Double.valueOf(BrickValues.SET_COLOR_TO) : this.repeatCount.interpretDouble(this.sprite)).intValue();
            } catch (InterpretationException interpretationException) {
                this.repeatCountValue = 0;
                Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            }
        }
        if (!this.isCurrentLoopInitialized) {
            this.currentTime = 0.0f;
            this.isCurrentLoopInitialized = true;
        }
        this.currentTime += delta;
        if (this.repeatCountValue < 0) {
            this.repeatCountValue = 0;
        }
        if (this.executedCount >= this.repeatCountValue && !this.isForeverRepeat) {
            return true;
        }
        if (this.action != null && this.action.act(delta) && this.currentTime >= LOOP_DELAY) {
            this.executedCount++;
            if (this.executedCount >= this.repeatCountValue && !this.isForeverRepeat) {
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
        this.isRepeatActionInitialized = false;
        this.executedCount = 0;
        super.restart();
    }

    public void setRepeatCount(Formula repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setIsForeverRepeat(boolean isForeverRepeat) {
        this.isForeverRepeat = isForeverRepeat;
    }
}
