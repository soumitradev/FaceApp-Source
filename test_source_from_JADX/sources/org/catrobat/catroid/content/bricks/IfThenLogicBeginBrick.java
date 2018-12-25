package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class IfThenLogicBeginBrick extends FormulaBrick implements NestingBrick {
    private static final long serialVersionUID = 1;
    private transient IfThenLogicEndBrick ifEndBrick;

    public IfThenLogicBeginBrick() {
        addAllowedBrickField(BrickField.IF_CONDITION, R.id.brick_if_begin_edit_text);
    }

    public IfThenLogicBeginBrick(int condition) {
        this(new Formula(Integer.valueOf(condition)));
    }

    public IfThenLogicBeginBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.IF_CONDITION, formula);
    }

    public IfThenLogicEndBrick getIfThenEndBrick() {
        return this.ifEndBrick;
    }

    public void setIfThenEndBrick(IfThenLogicEndBrick ifEndBrick) {
        this.ifEndBrick = ifEndBrick;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        IfThenLogicBeginBrick clone = (IfThenLogicBeginBrick) super.clone();
        clone.ifEndBrick = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_if_then_begin_if;
    }

    public View getView(Context context) {
        super.getView(context);
        hidePrototypePunctuation();
        return this.view;
    }

    private void hidePrototypePunctuation() {
        ((TextView) this.view.findViewById(R.id.if_prototype_punctuation)).setVisibility(8);
    }

    public boolean isInitialized() {
        return this.ifEndBrick != null;
    }

    public void initialize() {
        this.ifEndBrick = new IfThenLogicEndBrick(this);
    }

    public boolean isDraggableOver(Brick brick) {
        return brick != this.ifEndBrick;
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this);
        nestingBrickList.add(this.ifEndBrick);
        return nestingBrickList;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction ifAction = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        sequence.addAction(sprite.getActionFactory().createIfLogicAction(sprite, getFormulaWithBrickField(BrickField.IF_CONDITION), ifAction, null));
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(ifAction);
        return returnActionList;
    }
}
