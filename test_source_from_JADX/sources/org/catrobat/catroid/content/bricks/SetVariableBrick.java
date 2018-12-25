package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.generated70026.R;

public class SetVariableBrick extends UserVariableBrick {
    private static final long serialVersionUID = 1;

    public SetVariableBrick() {
        addAllowedBrickField(BrickField.VARIABLE, R.id.brick_set_variable_edit_text);
    }

    public SetVariableBrick(double value) {
        this(new Formula(Double.valueOf(value)));
    }

    public SetVariableBrick(Formula variableFormula, UserVariable userVariable) {
        this(variableFormula);
        this.userVariable = userVariable;
    }

    public SetVariableBrick(Sensors defaultValue) {
        this(new Formula(new FormulaElement(ElementType.SENSOR, defaultValue.name(), null)));
    }

    private SetVariableBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.VARIABLE, formula);
    }

    public int getViewResource() {
        return R.layout.brick_set_variable;
    }

    protected int getSpinnerId() {
        return R.id.set_variable_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetVariableAction(sprite, getFormulaWithBrickField(BrickField.VARIABLE), this.userVariable));
        return null;
    }
}
