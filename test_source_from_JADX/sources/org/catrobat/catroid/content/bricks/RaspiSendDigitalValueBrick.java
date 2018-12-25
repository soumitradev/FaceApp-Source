package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class RaspiSendDigitalValueBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public RaspiSendDigitalValueBrick() {
        addAllowedBrickField(BrickField.RASPI_DIGITAL_PIN_NUMBER, R.id.brick_raspi_set_digital_pin_edit_text);
        addAllowedBrickField(BrickField.RASPI_DIGITAL_PIN_VALUE, R.id.brick_raspi_set_digital_value_edit_text);
    }

    public RaspiSendDigitalValueBrick(int pinNumber, int pinValue) {
        this(new Formula(Integer.valueOf(pinNumber)), new Formula(Integer.valueOf(pinValue)));
    }

    public RaspiSendDigitalValueBrick(Formula pinNumber, Formula pinValue) {
        this();
        setFormulaWithBrickField(BrickField.RASPI_DIGITAL_PIN_NUMBER, pinNumber);
        setFormulaWithBrickField(BrickField.RASPI_DIGITAL_PIN_VALUE, pinValue);
    }

    public int getViewResource() {
        return R.layout.brick_raspi_send_digital;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(7));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSendDigitalRaspiValueAction(sprite, getFormulaWithBrickField(BrickField.RASPI_DIGITAL_PIN_NUMBER), getFormulaWithBrickField(BrickField.RASPI_DIGITAL_PIN_VALUE)));
        return null;
    }
}
