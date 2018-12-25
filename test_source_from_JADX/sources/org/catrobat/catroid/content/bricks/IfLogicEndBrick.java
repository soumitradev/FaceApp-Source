package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class IfLogicEndBrick extends BrickBaseType implements NestingBrick, AllowedAfterDeadEndBrick {
    private static final long serialVersionUID = 1;
    private transient IfElseLogicBeginBrick ifBeginBrick;
    private transient IfLogicElseBrick ifElseBrick;

    public IfLogicEndBrick(IfElseLogicBeginBrick ifLogicBeginBrick, IfLogicElseBrick ifElseBrick) {
        this.ifBeginBrick = ifLogicBeginBrick;
        this.ifElseBrick = ifElseBrick;
    }

    public IfElseLogicBeginBrick getIfBeginBrick() {
        return this.ifBeginBrick;
    }

    public void setIfBeginBrick(IfElseLogicBeginBrick ifBeginBrick) {
        this.ifBeginBrick = ifBeginBrick;
    }

    public IfLogicElseBrick getIfElseBrick() {
        return this.ifElseBrick;
    }

    public void setIfElseBrick(IfLogicElseBrick ifElseBrick) {
        this.ifElseBrick = ifElseBrick;
    }

    public int getViewResource() {
        return R.layout.brick_if_end_if;
    }

    public boolean isInitialized() {
        return this.ifElseBrick != null;
    }

    public void initialize() {
    }

    public boolean isDraggableOver(Brick brick) {
        return brick != this.ifElseBrick;
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this.ifBeginBrick);
        nestingBrickList.add(this.ifElseBrick);
        nestingBrickList.add(this);
        return nestingBrickList;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(sequence);
        return returnActionList;
    }
}
