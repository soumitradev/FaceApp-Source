package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.UserVariable;

public class SetVariableAction extends TemporalAction {
    private Formula changeVariable;
    private Sprite sprite;
    private UserVariable userVariable;

    protected void update(float percent) {
        if (this.userVariable != null) {
            Object value = this.changeVariable == null ? Double.valueOf(BrickValues.SET_COLOR_TO) : this.changeVariable.interpretObject(this.sprite);
            boolean isFirstLevelStringTree = false;
            if (this.changeVariable != null && this.changeVariable.getRoot().getElementType() == ElementType.STRING) {
                isFirstLevelStringTree = true;
            }
            if (!isFirstLevelStringTree) {
                try {
                    if (value instanceof String) {
                        value = Double.valueOf((String) value);
                    }
                } catch (NumberFormatException numberFormatException) {
                    Log.d(getClass().getSimpleName(), "Couldn't parse String", numberFormatException);
                }
            }
            this.userVariable.setValue(value);
        }
    }

    public void setUserVariable(UserVariable userVariable) {
        if (userVariable != null) {
            this.userVariable = userVariable;
        }
    }

    public void setChangeVariable(Formula changeVariable) {
        this.changeVariable = changeVariable;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
