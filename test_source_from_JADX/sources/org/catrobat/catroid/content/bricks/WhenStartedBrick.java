package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class WhenStartedBrick extends BrickBaseType implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private Script script;

    public WhenStartedBrick() {
        this(new StartScript());
    }

    public WhenStartedBrick(StartScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    public int getViewResource() {
        return R.layout.brick_when_started;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenStartedBrick clone = (WhenStartedBrick) super.clone();
        clone.script = this.script.clone();
        clone.script.setScriptBrick(clone);
        return clone;
    }

    public Script getScript() {
        return this.script;
    }

    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }
}
