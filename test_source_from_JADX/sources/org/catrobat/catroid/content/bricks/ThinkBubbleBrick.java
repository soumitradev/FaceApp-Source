package org.catrobat.catroid.content.bricks;

import android.view.View.OnClickListener;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ThinkBubbleBrick extends FormulaBrick implements OnClickListener {
    private static final long serialVersionUID = 1;

    public ThinkBubbleBrick() {
        addAllowedBrickField(BrickField.STRING, R.id.brick_bubble_edit_text);
    }

    public ThinkBubbleBrick(String text) {
        this(new Formula(text));
    }

    public ThinkBubbleBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.STRING, formula);
    }

    public int getViewResource() {
        return R.layout.brick_think_bubble;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThinkSayBubbleAction(sprite, getFormulaWithBrickField(BrickField.STRING), 1));
        return null;
    }
}
