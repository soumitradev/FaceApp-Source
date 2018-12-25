package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class MoveNStepsAction extends TemporalAction {
    private Sprite sprite;
    private Formula steps;

    protected void update(float percent) {
        try {
            Double stepsValue = this.steps == null ? Double.valueOf(BrickValues.SET_COLOR_TO) : this.steps.interpretDouble(this.sprite);
            double radians = Math.toRadians((double) this.sprite.look.getDirectionInUserInterfaceDimensionUnit());
            this.sprite.look.changeXInUserInterfaceDimensionUnit((float) (stepsValue.doubleValue() * Math.sin(radians)));
            this.sprite.look.changeYInUserInterfaceDimensionUnit((float) (stepsValue.doubleValue() * Math.cos(radians)));
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSteps(Formula steps) {
        this.steps = steps;
    }
}
