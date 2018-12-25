package org.catrobat.catroid.content.bricks;

import java.util.List;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenConditionScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class WhenConditionBrick extends FormulaBrick implements ScriptBrick {
    private WhenConditionScript script;

    public WhenConditionBrick() {
        addAllowedBrickField(BrickField.IF_CONDITION, R.id.brick_when_condition_edit_text);
    }

    public WhenConditionBrick(WhenConditionScript script) {
        this();
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
        this.formulaMap = script.getFormulaMap();
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenConditionBrick clone = (WhenConditionBrick) super.clone();
        clone.script = (WhenConditionScript) this.script.clone();
        clone.script.setScriptBrick(clone);
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_when_condition_true;
    }

    public Formula getConditionFormula() {
        return getFormulaWithBrickField(BrickField.IF_CONDITION);
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
