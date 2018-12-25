package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenTouchDownScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class WhenTouchDownBrick extends BrickBaseType implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private WhenTouchDownScript whenTouchDownScript;

    public WhenTouchDownBrick() {
        this(new WhenTouchDownScript());
    }

    public WhenTouchDownBrick(WhenTouchDownScript whenTouchDownScript) {
        whenTouchDownScript.setScriptBrick(this);
        this.commentedOut = whenTouchDownScript.isCommentedOut();
        this.whenTouchDownScript = whenTouchDownScript;
    }

    public Script getScript() {
        return this.whenTouchDownScript;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenTouchDownBrick clone = (WhenTouchDownBrick) super.clone();
        clone.whenTouchDownScript = (WhenTouchDownScript) this.whenTouchDownScript.clone();
        clone.whenTouchDownScript.setScriptBrick(clone);
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_screen_touched;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }

    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }
}
