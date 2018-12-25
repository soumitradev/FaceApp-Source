package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ArduinoSendDigitalValueBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ArduinoSendDigitalValueBrick() {
        addAllowedBrickField(BrickField.ARDUINO_DIGITAL_PIN_NUMBER, R.id.brick_arduino_set_digital_pin_edit_text);
        addAllowedBrickField(BrickField.ARDUINO_DIGITAL_PIN_VALUE, R.id.brick_arduino_set_digital_value_edit_text);
    }

    public ArduinoSendDigitalValueBrick(int pinNumber, int pinValue) {
        this(new Formula(Integer.valueOf(pinNumber)), new Formula(Integer.valueOf(pinValue)));
    }

    public ArduinoSendDigitalValueBrick(Formula pinNumber, Formula pinValue) {
        this();
        setFormulaWithBrickField(BrickField.ARDUINO_DIGITAL_PIN_NUMBER, pinNumber);
        setFormulaWithBrickField(BrickField.ARDUINO_DIGITAL_PIN_VALUE, pinValue);
    }

    public int getViewResource() {
        return R.layout.brick_arduino_send_digital;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(6));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSendDigitalArduinoValueAction(sprite, getFormulaWithBrickField(BrickField.ARDUINO_DIGITAL_PIN_NUMBER), getFormulaWithBrickField(BrickField.ARDUINO_DIGITAL_PIN_VALUE)));
        return null;
    }
}
