package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class WaitBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public WaitBrick() {
        addAllowedBrickField(BrickField.TIME_TO_WAIT_IN_SECONDS, R.id.brick_wait_edit_text);
    }

    public WaitBrick(int timeToWaitInMillisecondsValue) {
        this(new Formula(Double.valueOf(((double) timeToWaitInMillisecondsValue) / 1000.0d)));
    }

    public WaitBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.TIME_TO_WAIT_IN_SECONDS, formula);
    }

    public int getViewResource() {
        return R.layout.brick_wait;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        setSecondsLabel(prototypeView, BrickField.TIME_TO_WAIT_IN_SECONDS);
        return prototypeView;
    }

    public View getView(Context context) {
        super.getView(context);
        setSecondsLabel(this.view, BrickField.TIME_TO_WAIT_IN_SECONDS);
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createWaitAction(sprite, getFormulaWithBrickField(BrickField.TIME_TO_WAIT_IN_SECONDS)));
        return null;
    }
}
