package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class ThinkForBubbleBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public ThinkForBubbleBrick() {
        addAllowedBrickField(BrickField.STRING, R.id.brick_for_bubble_edit_text_text);
        addAllowedBrickField(BrickField.DURATION_IN_SECONDS, R.id.brick_for_bubble_edit_text_duration);
    }

    public ThinkForBubbleBrick(String text, float durationInSecondsValue) {
        this(new Formula(text), new Formula(Float.valueOf(durationInSecondsValue)));
    }

    public ThinkForBubbleBrick(Formula text, Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.STRING, text);
        setFormulaWithBrickField(BrickField.DURATION_IN_SECONDS, formula);
    }

    public int getViewResource() {
        return R.layout.brick_think_for_bubble;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        setSecondsLabel(prototypeView, BrickField.DURATION_IN_SECONDS);
        return prototypeView;
    }

    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, BrickField.DURATION_IN_SECONDS);
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThinkSayForBubbleAction(sprite, getFormulaWithBrickField(BrickField.STRING), 1));
        sequence.addAction(sprite.getActionFactory().createWaitForBubbleBrickAction(sprite, getFormulaWithBrickField(BrickField.DURATION_IN_SECONDS)));
        return null;
    }
}
