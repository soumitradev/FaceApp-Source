package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class RaspiPwmBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public RaspiPwmBrick() {
        addAllowedBrickField(BrickField.RASPI_DIGITAL_PIN_NUMBER, R.id.brick_raspi_pwm_pin_edit_text);
        addAllowedBrickField(BrickField.RASPI_PWM_FREQUENCY, R.id.brick_raspi_pwm_frequency_edit_text);
        addAllowedBrickField(BrickField.RASPI_PWM_PERCENTAGE, R.id.brick_raspi_pwm_percentage_edit_text);
    }

    public RaspiPwmBrick(int pinNumber, double pwmFrequency, double pwmPercentage) {
        this(new Formula(Integer.valueOf(pinNumber)), new Formula(Double.valueOf(pwmFrequency)), new Formula(Double.valueOf(pwmPercentage)));
    }

    public RaspiPwmBrick(Formula pinNumber, Formula pwmFrequency, Formula pwmPercentage) {
        this();
        setFormulaWithBrickField(BrickField.RASPI_DIGITAL_PIN_NUMBER, pinNumber);
        setFormulaWithBrickField(BrickField.RASPI_PWM_FREQUENCY, pwmFrequency);
        setFormulaWithBrickField(BrickField.RASPI_PWM_PERCENTAGE, pwmPercentage);
    }

    public int getViewResource() {
        return R.layout.brick_raspi_pwm;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(7));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSendRaspiPwmValueAction(sprite, getFormulaWithBrickField(BrickField.RASPI_DIGITAL_PIN_NUMBER), getFormulaWithBrickField(BrickField.RASPI_PWM_FREQUENCY), getFormulaWithBrickField(BrickField.RASPI_PWM_PERCENTAGE)));
        return null;
    }
}
