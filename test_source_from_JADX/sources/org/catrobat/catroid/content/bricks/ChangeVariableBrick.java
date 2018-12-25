package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.generated70026.R;

public class ChangeVariableBrick extends UserVariableBrick {
    private static final long serialVersionUID = 1;

    public ChangeVariableBrick() {
        addAllowedBrickField(BrickField.VARIABLE_CHANGE, R.id.brick_change_variable_edit_text);
    }

    public ChangeVariableBrick(double value) {
        this(new Formula(Double.valueOf(value)));
    }

    public ChangeVariableBrick(Formula formula, UserVariable userVariable) {
        this(formula);
        this.userVariable = userVariable;
    }

    public ChangeVariableBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.VARIABLE_CHANGE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_change_variable_by;
    }

    protected int getSpinnerId() {
        return R.id.change_variable_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createChangeVariableAction(sprite, getFormulaWithBrickField(BrickField.VARIABLE_CHANGE), this.userVariable));
        return null;
    }
}
