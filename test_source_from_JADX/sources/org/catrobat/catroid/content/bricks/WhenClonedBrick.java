package org.catrobat.catroid.content.bricks;

import java.util.Collections;
import java.util.List;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenClonedScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class WhenClonedBrick extends BrickBaseType implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private WhenClonedScript whenClonedScript;

    public WhenClonedBrick() {
        this(new WhenClonedScript());
    }

    public WhenClonedBrick(WhenClonedScript whenClonedScript) {
        whenClonedScript.setScriptBrick(this);
        this.commentedOut = whenClonedScript.isCommentedOut();
        this.whenClonedScript = whenClonedScript;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenClonedBrick clone = (WhenClonedBrick) super.clone();
        clone.whenClonedScript = (WhenClonedScript) this.whenClonedScript.clone();
        clone.whenClonedScript.setScriptBrick(clone);
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_when_cloned;
    }

    public Script getScript() {
        return this.whenClonedScript;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return Collections.emptyList();
    }
}
