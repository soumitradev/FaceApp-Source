package org.catrobat.catroid.content.bricks;

public class SetBackgroundByIndexAndWaitBrick extends SetBackgroundByIndexBrick {
    public SetBackgroundByIndexAndWaitBrick() {
        this.wait = 0;
    }

    public SetBackgroundByIndexAndWaitBrick(int index) {
        super(index);
        this.wait = 0;
    }
}
