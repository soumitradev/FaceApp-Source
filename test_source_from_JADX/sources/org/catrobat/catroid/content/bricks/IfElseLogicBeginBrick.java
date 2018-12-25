package org.catrobat.catroid.content.bricks;

public interface IfElseLogicBeginBrick extends Brick, NestingBrick {
    IfLogicElseBrick getIfElseBrick();

    IfLogicEndBrick getIfEndBrick();

    void setIfElseBrick(IfLogicElseBrick ifLogicElseBrick);

    void setIfEndBrick(IfLogicEndBrick ifLogicEndBrick);
}
