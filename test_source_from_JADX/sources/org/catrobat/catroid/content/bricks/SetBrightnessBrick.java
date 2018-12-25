package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetBrightnessBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetBrightnessBrick() {
        addAllowedBrickField(BrickField.BRIGHTNESS, R.id.brick_set_brightness_edit_text);
    }

    public SetBrightnessBrick(double brightness) {
        this(new Formula(Double.valueOf(brightness)));
    }

    public SetBrightnessBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.BRIGHTNESS, formula);
    }

    public int getViewResource() {
        return R.layout.brick_set_brightness;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetBrightnessAction(sprite, getFormulaWithBrickField(BrickField.BRIGHTNESS)));
        return null;
    }
}
