package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

public class ChangeVariableAction extends Action {
    private Formula changeVariable;
    private Sprite sprite;
    private UserVariable userVariable;

    public boolean act(float delta) {
        if (this.userVariable == null) {
            return true;
        }
        Double originalValue = this.userVariable.getValue();
        Double value = this.changeVariable == null ? Double.valueOf(BrickValues.SET_COLOR_TO) : this.changeVariable.interpretObject(this.sprite);
        if (originalValue instanceof String) {
            return true;
        }
        try {
            if (value instanceof String) {
                value = Double.valueOf((String) value);
            }
        } catch (NumberFormatException numberFormatException) {
            Log.d(getClass().getSimpleName(), "Couldn't parse String", numberFormatException);
        }
        if (!(originalValue instanceof Double) || !(value instanceof Double)) {
            return true;
        }
        this.userVariable.setValue(Double.valueOf((((Double) originalValue).isNaN() ? Double.valueOf(BrickValues.SET_COLOR_TO) : originalValue).doubleValue() + (((Double) value).isNaN() ? Double.valueOf(BrickValues.SET_COLOR_TO) : value).doubleValue()));
        return true;
    }

    public void setUserVariable(UserVariable userVariable) {
        this.userVariable = userVariable;
    }

    public void setChangeVariable(Formula changeVariable) {
        this.changeVariable = changeVariable;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
