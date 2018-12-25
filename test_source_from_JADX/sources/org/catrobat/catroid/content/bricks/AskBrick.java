package org.catrobat.catroid.content.bricks;

import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.generated70026.R;

public class AskBrick extends UserVariableBrick {
    private static final long serialVersionUID = 1;

    public AskBrick() {
        addAllowedBrickField(BrickField.ASK_QUESTION, R.id.brick_ask_question_edit_text);
    }

    public AskBrick(String questionText) {
        this(new Formula(questionText));
    }

    public AskBrick(Formula questionFormula, UserVariable answerVariable) {
        this(questionFormula);
        this.userVariable = answerVariable;
    }

    public AskBrick(Formula questionFormula) {
        this();
        setFormulaWithBrickField(BrickField.ASK_QUESTION, questionFormula);
    }

    public int getViewResource() {
        return R.layout.brick_ask;
    }

    protected int getSpinnerId() {
        return R.id.brick_ask_spinner;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAskAction(sprite, getFormulaWithBrickField(BrickField.ASK_QUESTION), this.userVariable));
        return Collections.emptyList();
    }
}
