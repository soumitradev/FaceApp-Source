package org.catrobat.catroid.content.bricks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.catrobat.catroid.content.ActionFactory;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class ForeverBrick extends BrickBaseType implements LoopBeginBrick {
    private static final long serialVersionUID = 1;
    private transient LoopEndBrick loopEndBrick;

    public int getViewResource() {
        return R.layout.brick_forever;
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
        this.loopEndBrick = new LoopEndlessBrick(this);
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

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        ScriptSequenceAction foreverSequence = (ScriptSequenceAction) ActionFactory.eventSequence(sequence.getScript());
        sequence.addAction(sprite.getActionFactory().createForeverAction(sprite, foreverSequence));
        LinkedList<ScriptSequenceAction> returnActionList = new LinkedList();
        returnActionList.add(foreverSequence);
        return returnActionList;
    }
}
