package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.generated70026.R;

public class ShowTextBrick extends UserVariableBrick {
    private static final long serialVersionUID = 1;

    public ShowTextBrick() {
        addAllowedBrickField(BrickField.X_POSITION, R.id.brick_show_variable_edit_text_x);
        addAllowedBrickField(BrickField.Y_POSITION, R.id.brick_show_variable_edit_text_y);
    }

    public ShowTextBrick(int xPosition, int yPosition) {
        this(new Formula(Integer.valueOf(xPosition)), new Formula(Integer.valueOf(yPosition)));
    }

    public ShowTextBrick(Formula xPosition, Formula yPosition) {
        this();
        setFormulaWithBrickField(BrickField.X_POSITION, xPosition);
        setFormulaWithBrickField(BrickField.Y_POSITION, yPosition);
    }

    public int getViewResource() {
        return R.layout.brick_show_variable;
    }

    protected int getSpinnerId() {
        return R.id.show_variable_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.userVariable == null || this.userVariable.getName() == null) {
            this.userVariable = new UserVariable("NoVariableSet", Constants.NO_VARIABLE_SELECTED);
            this.userVariable.setDummy(true);
        }
        sequence.addAction(sprite.getActionFactory().createShowVariableAction(sprite, getFormulaWithBrickField(BrickField.X_POSITION), getFormulaWithBrickField(BrickField.Y_POSITION), this.userVariable));
        return null;
    }
}
