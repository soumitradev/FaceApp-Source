package org.catrobat.catroid.content.bricks;

import org.catrobat.catroid.generated70026.R;

public class LoopEndlessBrick extends LoopEndBrick implements DeadEndBrick {
    private static final long serialVersionUID = 1;

    LoopEndlessBrick() {
    }

    public LoopEndlessBrick(LoopBeginBrick loopBeginBrick) {
        super(loopBeginBrick);
    }

    public int getViewResource() {
        return R.layout.brick_loop_endless;
    }
}
