package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SetVolumeToBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetVolumeToBrick() {
        addAllowedBrickField(BrickField.VOLUME, R.id.brick_set_volume_to_edit_text);
    }

    public SetVolumeToBrick(double volume) {
        this(new Formula(Double.valueOf(volume)));
    }

    public SetVolumeToBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.VOLUME, formula);
    }

    public int getViewResource() {
        return R.layout.brick_set_volume_to;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetVolumeToAction(sprite, getFormulaWithBrickField(BrickField.VOLUME)));
        return null;
    }
}
