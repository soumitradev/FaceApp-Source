package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class WaitUntilAction extends Action {
    private static final float LOOP_DELAY = 0.02f;
    private boolean completed = false;
    private Formula condition;
    private float currentTime = 0.0f;
    private Sprite sprite;

    public void setCondition(Formula condition) {
        this.condition = condition;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public boolean act(float delta) {
        if (this.completed) {
            return true;
        }
        this.currentTime += delta;
        if (this.currentTime < LOOP_DELAY) {
            return false;
        }
        this.currentTime = 0.0f;
        try {
            this.completed = this.condition.interpretBoolean(this.sprite).booleanValue();
        } catch (InterpretationException e) {
            this.completed = false;
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
        return this.completed;
    }

    public void restart() {
        this.completed = false;
    }
}
