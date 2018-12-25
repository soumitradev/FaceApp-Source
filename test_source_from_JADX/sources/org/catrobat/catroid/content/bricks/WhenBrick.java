package org.catrobat.catroid.content.bricks;

import android.support.annotation.NonNull;
import java.util.List;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.generated70026.R;

public class WhenBrick extends BrickBaseType implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private WhenScript whenScript;

    public WhenBrick() {
        this(new WhenScript());
    }

    public WhenBrick(@NonNull WhenScript whenScript) {
        whenScript.setScriptBrick(this);
        this.commentedOut = whenScript.isCommentedOut();
        this.whenScript = whenScript;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenBrick clone = (WhenBrick) super.clone();
        clone.whenScript = (WhenScript) this.whenScript.clone();
        clone.whenScript.setScriptBrick(clone);
        return clone;
    }

    public Script getScript() {
        return this.whenScript;
    }

    public int getViewResource() {
        return R.layout.brick_when;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }

    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }
}
