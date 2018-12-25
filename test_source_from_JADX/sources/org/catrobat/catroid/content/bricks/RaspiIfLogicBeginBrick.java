package org.catrobat.catroid.content.bricks;

import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class RaspiIfLogicBeginBrick extends IfLogicBeginBrick {
    private static final long serialVersionUID = 1;

    public RaspiIfLogicBeginBrick(int condition) {
        super(new Formula(Integer.valueOf(condition)));
    }

    public RaspiIfLogicBeginBrick(Formula formula) {
        super(formula);
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(7));
        super.addRequiredResources(requiredResourcesSet);
    }

    public int getViewResource() {
        return R.layout.brick_raspi_if_begin_if;
    }

    void hidePrototypeElseAndPunctuation() {
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction ifAction = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        ScriptSequenceAction elseAction = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        sequence.addAction(sprite.getActionFactory().createRaspiIfLogicActionAction(sprite, getFormulaWithBrickField(BrickField.IF_CONDITION), ifAction, elseAction));
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(elseAction);
        returnActionList.add(ifAction);
        return returnActionList;
    }
}
