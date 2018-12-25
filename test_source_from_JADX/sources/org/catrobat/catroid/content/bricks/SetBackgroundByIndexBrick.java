package org.catrobat.catroid.content.bricks;

import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;

public class SetBackgroundByIndexBrick extends SetLookByIndexBrick {
    public SetBackgroundByIndexBrick(int index) {
        super(new Formula(Integer.valueOf(index)));
    }

    public SetBackgroundByIndexBrick(Formula formula) {
        super(formula);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetLookByIndexAction(getSprite(), getFormulaWithBrickField(BrickField.LOOK_INDEX), this.wait));
        return Collections.emptyList();
    }

    protected Sprite getSprite() {
        return ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite();
    }
}
