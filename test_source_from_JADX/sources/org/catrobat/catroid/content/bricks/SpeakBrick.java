package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class SpeakBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SpeakBrick() {
        addAllowedBrickField(BrickField.SPEAK, R.id.brick_speak_edit_text);
    }

    public SpeakBrick(String text) {
        this(new Formula(text));
    }

    public SpeakBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.SPEAK, formula);
    }

    public int getViewResource() {
        return R.layout.brick_speak;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(1));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSpeakAction(sprite, getFormulaWithBrickField(BrickField.SPEAK)));
        return null;
    }
}
