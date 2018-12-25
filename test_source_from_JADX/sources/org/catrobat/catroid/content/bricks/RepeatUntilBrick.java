package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class RepeatUntilBrick extends FormulaBrick implements LoopBeginBrick {
    private static final long serialVersionUID = 1;
    private transient LoopEndBrick loopEndBrick;

    public RepeatUntilBrick() {
        addAllowedBrickField(BrickField.REPEAT_UNTIL_CONDITION, R.id.brick_repeat_until_edit_text);
    }

    public RepeatUntilBrick(int condition) {
        this(new Formula(Integer.valueOf(condition)));
    }

    public RepeatUntilBrick(Formula condition) {
        this();
        setFormulaWithBrickField(BrickField.REPEAT_UNTIL_CONDITION, condition);
    }

    public int getViewResource() {
        return R.layout.brick_repeat_until;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        ((TextView) prototypeView.findViewById(R.id.brick_repeat_until_edit_text)).setText(BrickValues.IF_CONDITION);
        return prototypeView;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction repeatSequence = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        sequence.addAction(sprite.getActionFactory().createRepeatUntilAction(sprite, getFormulaWithBrickField(BrickField.REPEAT_UNTIL_CONDITION), repeatSequence));
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(repeatSequence);
        return returnActionList;
    }

    public LoopEndBrick getLoopEndBrick() {
        return this.loopEndBrick;
    }

    public void setLoopEndBrick(LoopEndBrick loopEndBrick) {
        this.loopEndBrick = loopEndBrick;
    }

    public boolean isInitialized() {
        return this.loopEndBrick != null;
    }

    public void initialize() {
        this.loopEndBrick = new LoopEndBrick(this);
    }

    public boolean isDraggableOver(Brick brick) {
        return this.loopEndBrick != null;
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this);
        nestingBrickList.add(this.loopEndBrick);
        return nestingBrickList;
    }
}
