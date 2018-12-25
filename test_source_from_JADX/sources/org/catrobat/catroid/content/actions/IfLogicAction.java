package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class IfLogicAction extends Action {
    private Action elseAction;
    private Action ifAction;
    private Formula ifCondition;
    private Boolean ifConditionValue;
    private boolean isInitialized = false;
    private boolean isInterpretedCorrectly;
    private Sprite sprite;

    protected void begin() {
        try {
            if (this.ifCondition == null) {
                this.isInterpretedCorrectly = false;
                return;
            }
            this.ifConditionValue = Boolean.valueOf(this.ifCondition.interpretDouble(this.sprite).intValue() != 0);
            this.isInterpretedCorrectly = true;
        } catch (InterpretationException interpretationException) {
            this.isInterpretedCorrectly = false;
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public boolean act(float delta) {
        if (!this.isInitialized) {
            begin();
            this.isInitialized = true;
        }
        if (!this.isInterpretedCorrectly) {
            return true;
        }
        if (this.ifConditionValue.booleanValue()) {
            return this.ifAction.act(delta);
        }
        if (this.elseAction == null) {
            return true;
        }
        return this.elseAction.act(delta);
    }

    public void restart() {
        this.ifAction.restart();
        if (this.elseAction != null) {
            this.elseAction.restart();
        }
        this.isInitialized = false;
        super.restart();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setIfAction(Action ifAction) {
        this.ifAction = ifAction;
    }

    public void setElseAction(Action elseAction) {
        this.elseAction = elseAction;
    }

    public void setIfCondition(Formula ifCondition) {
        this.ifCondition = ifCondition;
    }

    public void setActor(Actor actor) {
        super.setActor(actor);
        this.ifAction.setActor(actor);
        if (this.elseAction != null) {
            this.elseAction.setActor(actor);
        }
    }
}
