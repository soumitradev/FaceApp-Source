package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class GoNStepsBackAction extends TemporalAction {
    private Sprite sprite;
    private Formula steps;

    protected void update(float delta) {
        try {
            Float stepsValue = this.steps == null ? Float.valueOf(0.0f) : this.steps.interpretFloat(this.sprite);
            int zPosition = this.sprite.look.getZIndex();
            if (stepsValue.intValue() > 0 && zPosition - stepsValue.intValue() < 2) {
                this.sprite.look.setZIndex(2);
            } else if (stepsValue.intValue() >= 0 || zPosition - stepsValue.intValue() >= zPosition) {
                goNStepsBack(stepsValue.intValue());
            } else {
                toFront(delta);
            }
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    private void toFront(float delta) {
        this.sprite.getActionFactory().createComeToFrontAction(this.sprite).act(delta);
    }

    private void goNStepsBack(int steps) {
        this.sprite.look.setZIndex(Math.max(this.sprite.look.getZIndex() - steps, 2));
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSteps(Formula steps) {
        this.steps = steps;
    }
}
