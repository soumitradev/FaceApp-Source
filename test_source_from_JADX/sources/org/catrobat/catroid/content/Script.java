package org.catrobat.catroid.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.IfElseLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfLogicElseBrick;
import org.catrobat.catroid.content.bricks.IfLogicEndBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicEndBrick;
import org.catrobat.catroid.content.bricks.LoopBeginBrick;
import org.catrobat.catroid.content.bricks.LoopEndBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.content.eventids.EventId;

public abstract class Script implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    protected ArrayList<Brick> brickList = new ArrayList();
    protected boolean commentedOut = false;
    protected transient ScriptBrick scriptBrick;

    public abstract EventId createEventId(Sprite sprite);

    public abstract ScriptBrick getScriptBrick();

    public ArrayList<Brick> getBrickList() {
        return this.brickList;
    }

    public Script clone() throws CloneNotSupportedException {
        Script clone = (Script) super.clone();
        clone.commentedOut = this.commentedOut;
        clone.scriptBrick = null;
        clone.brickList = cloneBrickList();
        return clone;
    }

    private ArrayList<Brick> cloneBrickList() throws CloneNotSupportedException {
        ArrayList<Brick> clones = new ArrayList();
        Iterator it = this.brickList.iterator();
        while (it.hasNext()) {
            clones.add(((Brick) it.next()).clone());
        }
        it = this.brickList.iterator();
        while (it.hasNext()) {
            int begin;
            int end;
            Brick brick = (Brick) it.next();
            if (brick instanceof LoopBeginBrick) {
                begin = this.brickList.indexOf(brick);
                end = this.brickList.indexOf(((LoopBeginBrick) brick).getLoopEndBrick());
                if (end != -1) {
                    LoopBeginBrick beginBrick = (LoopBeginBrick) clones.get(begin);
                    LoopEndBrick endBrick = (LoopEndBrick) clones.get(end);
                    beginBrick.setLoopEndBrick(endBrick);
                    endBrick.setLoopBeginBrick(beginBrick);
                }
            }
            if (brick instanceof IfThenLogicBeginBrick) {
                begin = this.brickList.indexOf(brick);
                IfThenLogicBeginBrick beginBrick2 = (IfThenLogicBeginBrick) clones.get(begin);
                IfThenLogicEndBrick endBrick2 = (IfThenLogicEndBrick) clones.get(this.brickList.indexOf(((IfThenLogicBeginBrick) brick).getIfThenEndBrick()));
                beginBrick2.setIfThenEndBrick(endBrick2);
                endBrick2.setIfThenBeginBrick(beginBrick2);
            } else if (brick instanceof IfElseLogicBeginBrick) {
                begin = this.brickList.indexOf(brick);
                end = this.brickList.indexOf(((IfElseLogicBeginBrick) brick).getIfElseBrick());
                int end2 = this.brickList.indexOf(((IfElseLogicBeginBrick) brick).getIfEndBrick());
                if (end != -1) {
                    if (end2 != -1) {
                        IfElseLogicBeginBrick beginBrick3 = (IfElseLogicBeginBrick) clones.get(begin);
                        IfLogicElseBrick elseBrick = (IfLogicElseBrick) clones.get(end);
                        IfLogicEndBrick endBrick3 = (IfLogicEndBrick) clones.get(end2);
                        beginBrick3.setIfElseBrick(elseBrick);
                        beginBrick3.setIfEndBrick(endBrick3);
                        elseBrick.setIfBeginBrick(beginBrick3);
                        elseBrick.setIfEndBrick(endBrick3);
                        endBrick3.setIfBeginBrick(beginBrick3);
                        endBrick3.setIfElseBrick(elseBrick);
                    }
                }
            }
        }
        return clones;
    }

    public boolean isCommentedOut() {
        return this.commentedOut;
    }

    public void setCommentedOut(boolean commentedOut) {
        this.commentedOut = commentedOut;
        Iterator it = this.brickList.iterator();
        while (it.hasNext()) {
            ((Brick) it.next()).setCommentedOut(commentedOut);
        }
    }

    public void setScriptBrick(ScriptBrick scriptBrick) {
        this.scriptBrick = scriptBrick;
    }

    public void run(Sprite sprite, ScriptSequenceAction sequence) {
        if (!this.commentedOut) {
            ArrayList<ScriptSequenceAction> sequenceList = new ArrayList();
            sequenceList.add(sequence);
            Iterator it = this.brickList.iterator();
            while (it.hasNext()) {
                Brick brick = (Brick) it.next();
                if (!brick.isCommentedOut()) {
                    List<ScriptSequenceAction> actions = brick.addActionToSequence(sprite, (ScriptSequenceAction) sequenceList.get(sequenceList.size() - 1));
                    if (actions != null) {
                        for (ScriptSequenceAction action : actions) {
                            if (sequenceList.contains(action)) {
                                sequenceList.remove(action);
                            } else {
                                sequenceList.add(action);
                            }
                        }
                    }
                }
            }
        }
    }

    public void addBrick(Brick brick) {
        this.brickList.add(brick);
        updateUserBricksIfNecessary(brick);
    }

    public void addBrick(int position, Brick brick) {
        this.brickList.add(position, brick);
        updateUserBricksIfNecessary(brick);
    }

    public Brick getBrick(int index) {
        return (Brick) this.brickList.get(index);
    }

    public void removeBrick(Brick brick) {
        this.brickList.remove(brick);
    }

    private void updateUserBricksIfNecessary(Brick brick) {
        if (brick instanceof UserBrick) {
            ((UserBrick) brick).updateUserBrickParametersAndVariables();
        }
    }

    public void addRequiredResources(ResourcesSet resourcesSet) {
        Iterator it = this.brickList.iterator();
        while (it.hasNext()) {
            Brick brick = (Brick) it.next();
            if (!brick.isCommentedOut()) {
                brick.addRequiredResources(resourcesSet);
            }
        }
    }
}
