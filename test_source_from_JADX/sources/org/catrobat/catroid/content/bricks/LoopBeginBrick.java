package org.catrobat.catroid.content.bricks;

public interface LoopBeginBrick extends NestingBrick {
    LoopEndBrick getLoopEndBrick();

    void setLoopEndBrick(LoopEndBrick loopEndBrick);
}
