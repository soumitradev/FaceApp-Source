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

public class IfLogicBeginBrick extends FormulaBrick implements IfElseLogicBeginBrick {
    private static final long serialVersionUID = 1;
    private transient IfLogicElseBrick ifElseBrick;
    private transient IfLogicEndBrick ifEndBrick;

    public IfLogicBeginBrick() {
        addAllowedBrickField(BrickField.IF_CONDITION, R.id.brick_if_begin_edit_text);
    }

    public IfLogicBeginBrick(int condition) {
        this(new Formula(Integer.valueOf(condition)));
    }

    public IfLogicBeginBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.IF_CONDITION, formula);
    }

    public IfLogicElseBrick getIfElseBrick() {
        return this.ifElseBrick;
    }

    public void setIfElseBrick(IfLogicElseBrick elseBrick) {
        this.ifElseBrick = elseBrick;
    }

    public IfLogicEndBrick getIfEndBrick() {
        return this.ifEndBrick;
    }

    public void setIfEndBrick(IfLogicEndBrick ifEndBrick) {
        this.ifEndBrick = ifEndBrick;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        IfLogicBeginBrick clone = (IfLogicBeginBrick) super.clone();
        clone.ifElseBrick = null;
        clone.ifEndBrick = null;
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_if_begin_if;
    }

    public View getView(Context context) {
        super.getView(context);
        onSuperGetViewCalled(context);
        return this.view;
    }

    protected void onSuperGetViewCalled(Context context) {
        hidePrototypeElseAndPunctuation();
    }

    void hidePrototypeElseAndPunctuation() {
        TextView prototypeTextElse = (TextView) this.view.findViewById(R.id.if_prototype_else);
        TextView prototypeTextPunctuation2 = (TextView) this.view.findViewById(R.id.if_else_prototype_punctuation2);
        ((TextView) this.view.findViewById(R.id.if_else_prototype_punctuation)).setVisibility(8);
        prototypeTextElse.setVisibility(8);
        prototypeTextPunctuation2.setVisibility(8);
    }

    public boolean isInitialized() {
        return this.ifElseBrick != null;
    }

    public void initialize() {
        this.ifElseBrick = new IfLogicElseBrick(this);
        this.ifEndBrick = new IfLogicEndBrick(this, this.ifElseBrick);
    }

    public boolean isDraggableOver(Brick brick) {
        return brick != this.ifElseBrick;
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this);
        nestingBrickList.add(this.ifElseBrick);
        nestingBrickList.add(this.ifEndBrick);
        return nestingBrickList;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction ifAction = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        ScriptSequenceAction elseAction = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        sequence.addAction(sprite.getActionFactory().createIfLogicAction(sprite, getFormulaWithBrickField(BrickField.IF_CONDITION), ifAction, elseAction));
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(elseAction);
        returnActionList.add(ifAction);
        return returnActionList;
    }
}
