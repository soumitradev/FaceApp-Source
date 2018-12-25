package org.catrobat.catroid.content.bricks;

import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class AskSpeechBrick extends UserVariableBrick {
    private static final long serialVersionUID = 1;

    public AskSpeechBrick() {
        addAllowedBrickField(BrickField.ASK_SPEECH_QUESTION, R.id.brick_ask_speech_question_edit_text);
    }

    public AskSpeechBrick(String questionText) {
        this(new Formula(questionText));
    }

    public AskSpeechBrick(Formula questionFormula) {
        this();
        setFormulaWithBrickField(BrickField.ASK_SPEECH_QUESTION, questionFormula);
    }

    public int getViewResource() {
        return R.layout.brick_ask_speech;
    }

    protected int getSpinnerId() {
        return R.id.brick_ask_speech_spinner;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(21));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAskSpeechAction(sprite, getFormulaWithBrickField(BrickField.ASK_SPEECH_QUESTION), this.userVariable));
        return Collections.emptyList();
    }
}
