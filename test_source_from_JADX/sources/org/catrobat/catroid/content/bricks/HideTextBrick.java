package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.generated70026.R;

public class HideTextBrick extends UserVariableBrick {
    private static final long serialVersionUID = 1;

    public int getViewResource() {
        return R.layout.brick_hide_variable;
    }

    protected int getSpinnerId() {
        return R.id.hide_variable_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.userVariable == null || this.userVariable.getName() == null) {
            this.userVariable = new UserVariable("NoVariableSet", Constants.NO_VARIABLE_SELECTED);
            this.userVariable.setDummy(true);
        }
        sequence.addAction(sprite.getActionFactory().createHideVariableAction(sprite, this.userVariable));
        return null;
    }
}
