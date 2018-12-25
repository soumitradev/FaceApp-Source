package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class IfLogicElseBrick extends BrickBaseType implements NestingBrick, AllowedAfterDeadEndBrick {
    private static final long serialVersionUID = 1;
    private transient IfElseLogicBeginBrick ifBeginBrick;
    private transient IfLogicEndBrick ifEndBrick;

    public IfLogicElseBrick(IfElseLogicBeginBrick ifBeginBrick) {
        this.ifBeginBrick = ifBeginBrick;
    }

    public IfElseLogicBeginBrick getIfBeginBrick() {
        return this.ifBeginBrick;
    }

    public void setIfBeginBrick(IfElseLogicBeginBrick ifBeginBrick) {
        this.ifBeginBrick = ifBeginBrick;
    }

    public IfLogicEndBrick getIfEndBrick() {
        return this.ifEndBrick;
    }

    public void setIfEndBrick(IfLogicEndBrick ifEndBrick) {
        this.ifEndBrick = ifEndBrick;
    }

    public int getViewResource() {
        return R.layout.brick_if_else;
    }

    public boolean isInitialized() {
        return (this.ifBeginBrick == null || this.ifEndBrick == null) ? false : true;
    }

    public void initialize() {
    }

    public boolean isDraggableOver(Brick brick) {
        return (brick == this.ifBeginBrick || brick == this.ifEndBrick) ? false : true;
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this.ifBeginBrick);
        nestingBrickList.add(this);
        nestingBrickList.add(this.ifEndBrick);
        return nestingBrickList;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(sequence);
        return returnActionList;
    }
}
