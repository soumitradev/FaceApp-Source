package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ChangeVolumeByNBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ChangeVolumeByNBrick() {
        addAllowedBrickField(BrickField.VOLUME_CHANGE, R.id.brick_change_volume_by_edit_text);
    }

    public ChangeVolumeByNBrick(double changeVolumeValue) {
        this(new Formula(Double.valueOf(changeVolumeValue)));
    }

    public ChangeVolumeByNBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.VOLUME_CHANGE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_change_volume_by;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeVolumeByNAction(sprite, getFormulaWithBrickField(BrickField.VOLUME_CHANGE)));
        return null;
    }
}
