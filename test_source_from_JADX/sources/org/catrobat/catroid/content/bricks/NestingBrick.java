package org.catrobat.catroid.content.bricks;

import java.util.List;

public interface NestingBrick {
    List<NestingBrick> getAllNestingBrickParts();

    void initialize();

    boolean isDraggableOver(Brick brick);

    boolean isInitialized();
}
