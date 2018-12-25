package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.Sensors;

public class PhiroSensorAction extends Action {
    private static final int DISTANCE_THRESHOLD_VALUE = 850;
    private Action elseAction;
    private Action ifAction;
    private Formula ifCondition;
    private Boolean ifConditionValue;
    private boolean isInitialized = false;
    private boolean isInterpretedCorrectly;
    private int sensorNumber;
    private Sprite sprite;

    protected void begin() {
        try {
            if (this.ifCondition == null) {
                this.isInterpretedCorrectly = false;
                return;
            }
            this.ifConditionValue = Boolean.valueOf(this.ifCondition.interpretDouble(this.sprite).intValue() <= DISTANCE_THRESHOLD_VALUE);
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
        return this.elseAction.act(delta);
    }

    public void restart() {
        this.ifAction.restart();
        this.elseAction.restart();
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

    public void setSensor(int sensorNumber) {
        this.sensorNumber = sensorNumber;
        setIfCondition(new Formula(new FormulaElement(ElementType.SENSOR, getPhiroProSensorByNumber().name(), null)));
    }

    private Sensors getPhiroProSensorByNumber() {
        switch (this.sensorNumber) {
            case 0:
                return Sensors.PHIRO_FRONT_LEFT;
            case 1:
                return Sensors.PHIRO_FRONT_RIGHT;
            case 2:
                return Sensors.PHIRO_SIDE_LEFT;
            case 3:
                return Sensors.PHIRO_SIDE_RIGHT;
            case 4:
                return Sensors.PHIRO_BOTTOM_LEFT;
            case 5:
                return Sensors.PHIRO_BOTTOM_RIGHT;
            default:
                return Sensors.PHIRO_SIDE_RIGHT;
        }
    }

    public void setActor(Actor actor) {
        super.setActor(actor);
        this.ifAction.setActor(actor);
        this.elseAction.setActor(actor);
    }
}
