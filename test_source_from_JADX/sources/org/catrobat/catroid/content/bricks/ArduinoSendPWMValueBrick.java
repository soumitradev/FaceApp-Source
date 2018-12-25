package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.Operators;
import org.catrobat.catroid.generated70026.R;

public class ArduinoSendPWMValueBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ArduinoSendPWMValueBrick() {
        addAllowedBrickField(BrickField.ARDUINO_ANALOG_PIN_NUMBER, R.id.brick_arduino_set_analog_pin_edit_text);
        addAllowedBrickField(BrickField.ARDUINO_ANALOG_PIN_VALUE, R.id.brick_arduino_set_analog_value_edit_text);
    }

    public ArduinoSendPWMValueBrick(int pinNumber, int pinValue) {
        this(new Formula(Integer.valueOf(pinNumber)), new Formula(Integer.valueOf(pinValue)));
    }

    public ArduinoSendPWMValueBrick(Formula pinNumber, Formula pinValue) {
        this();
        setFormulaWithBrickField(BrickField.ARDUINO_ANALOG_PIN_NUMBER, pinNumber);
        setFormulaWithBrickField(BrickField.ARDUINO_ANALOG_PIN_VALUE, pinValue);
    }

    public int getViewResource() {
        return R.layout.brick_arduino_send_analog;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(6));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSendPWMArduinoValueAction(sprite, getFormulaWithBrickField(BrickField.ARDUINO_ANALOG_PIN_NUMBER), getFormulaWithBrickField(BrickField.ARDUINO_ANALOG_PIN_VALUE)));
        return null;
    }

    public void updateArduinoValues994to995() {
        FormulaElement oldFormulaElement = getFormulaWithBrickField(BrickField.ARDUINO_ANALOG_PIN_VALUE).getFormulaTree();
        FormulaElement multiplication = new FormulaElement(ElementType.OPERATOR, Operators.MULT.toString(), null);
        multiplication.setLeftChild(new FormulaElement(ElementType.NUMBER, "2.55", null));
        multiplication.setRightChild(oldFormulaElement);
        setFormulaWithBrickField(BrickField.ARDUINO_ANALOG_PIN_VALUE, new Formula(multiplication));
    }
}
