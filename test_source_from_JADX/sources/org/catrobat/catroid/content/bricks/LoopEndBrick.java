package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class LoopEndBrick extends BrickBaseType implements NestingBrick, AllowedAfterDeadEndBrick {
    private static final long serialVersionUID = 1;
    private transient LoopBeginBrick loopBeginBrick;

    public LoopEndBrick(LoopBeginBrick loopBeginBrick) {
        this.loopBeginBrick = loopBeginBrick;
    }

    public LoopBeginBrick getLoopBeginBrick() {
        return this.loopBeginBrick;
    }

    public void setLoopBeginBrick(LoopBeginBrick loopBeginBrick) {
        this.loopBeginBrick = loopBeginBrick;
    }

    public int getViewResource() {
        return R.layout.brick_loop_end;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        LoopEndBrick clone = (LoopEndBrick) super.clone();
        clone.loopBeginBrick = null;
        return clone;
    }

    public boolean isDraggableOver(Brick brick) {
        return this.loopBeginBrick != null;
    }

    public boolean isInitialized() {
        return this.loopBeginBrick != null;
    }

    public void initialize() {
        this.loopBeginBrick = new ForeverBrick();
    }

    public List<NestingBrick> getAllNestingBrickParts() {
        List<NestingBrick> nestingBrickList = new ArrayList();
        nestingBrickList.add(this.loopBeginBrick);
        nestingBrickList.add(this);
        return nestingBrickList;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(sequence);
        return returnActionList;
    }
}
