package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.SpeakAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SpeakAndWaitBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SpeakAndWaitBrick() {
        addAllowedBrickField(BrickField.SPEAK, R.id.brick_speak_and_wait_edit_text);
    }

    public SpeakAndWaitBrick(String text) {
        this(new Formula(text));
    }

    public SpeakAndWaitBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.SPEAK, formula);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(1));
        super.addRequiredResources(requiredResourcesSet);
    }

    public int getViewResource() {
        return R.layout.brick_speak_and_wait;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSpeakAction(sprite, getFormulaWithBrickField(BrickField.SPEAK)));
        sequence.addAction(sprite.getActionFactory().createWaitAction(sprite, new Formula(Float.valueOf(getDurationOfSpokenText(sprite, getFormulaWithBrickField(BrickField.SPEAK))))));
        return null;
    }

    private float getDurationOfSpokenText(Sprite sprite, Formula text) {
        SpeakAction action = (SpeakAction) sprite.getActionFactory().createSpeakAction(sprite, getFormulaWithBrickField(BrickField.SPEAK));
        action.setSprite(sprite);
        action.setText(text);
        action.setDetermineLength(true);
        action.act(1.0f);
        return action.getLengthOfText() / 1000.0f;
    }
}
