package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class IfThenLogicEndBrick extends BrickBaseType implements NestingBrick, AllowedAfterDeadEndBrick {
    private static final long serialVersionUID = 1;
    private transient IfThenLogicBeginBrick ifBeginBrick;

    public IfThenLogicEndBrick(IfThenLogicBeginBrick ifBeginBrick) {
        this.ifBeginBrick = ifBeginBrick;
    }

    public IfThenLogicBeginBrick getIfBeginBrick() {
        return this.ifBeginBrick;
    }

    public void setIfThenBeginBrick(IfThenLogicBeginBrick ifBeginBrick) {
        this.ifBeginBrick = ifBeginBrick;
    }

    public int getViewResource() {
        return R.layout.brick_if_end_if;
    }

    public boolean isDraggableOver(Brick brick) {
        return brick != this.ifBeginBrick;
    }

    public boolean isInitialized() {
        return this.ifBeginBrick != null;
    }

    public void initialize() {
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this.ifBeginBrick);
        nestingBrickList.add(this);
        return nestingBrickList;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(sequence);
        return returnActionList;
    }
}
