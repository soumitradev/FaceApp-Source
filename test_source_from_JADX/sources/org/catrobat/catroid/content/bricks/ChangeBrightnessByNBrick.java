package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ChangeBrightnessByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ChangeBrightnessByNBrick() {
        addAllowedBrickField(BrickField.BRIGHTNESS_CHANGE, R.id.brick_change_brightness_edit_text);
    }

    public ChangeBrightnessByNBrick(double changeBrightnessValue) {
        this(new Formula(Double.valueOf(changeBrightnessValue)));
    }

    public ChangeBrightnessByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.BRIGHTNESS_CHANGE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_change_brightness;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeBrightnessByNAction(sprite, getFormulaWithBrickField(BrickField.BRIGHTNESS_CHANGE)));
        return null;
    }
}
