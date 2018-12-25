package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.generated70026.R;

public class GlideToBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public GlideToBrick() {
        addAllowedBrickField(BrickField.X_DESTINATION, R.id.brick_glide_to_edit_text_x);
        addAllowedBrickField(BrickField.Y_DESTINATION, R.id.brick_glide_to_edit_text_y);
        addAllowedBrickField(BrickField.DURATION_IN_SECONDS, R.id.brick_glide_to_edit_text_duration);
    }

    public GlideToBrick(int xDestinationValue, int yDestinationValue, int durationInMilliSecondsValue) {
        this(new Formula(Integer.valueOf(xDestinationValue)), new Formula(Integer.valueOf(yDestinationValue)), new Formula(Double.valueOf(((double) durationInMilliSecondsValue) / 1000.0d)));
    }

    public GlideToBrick(Formula xDestination, Formula yDestination, Formula durationInSeconds) {
        this();
        setFormulaWithBrickField(BrickField.X_DESTINATION, xDestination);
        setFormulaWithBrickField(BrickField.Y_DESTINATION, yDestination);
        setFormulaWithBrickField(BrickField.DURATION_IN_SECONDS, durationInSeconds);
    }

    public int getViewResource() {
        return R.layout.brick_glide_to;
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
        sequence.addAction(sprite.getActionFactory().createGlideToAction(sprite, getFormulaWithBrickField(BrickField.X_DESTINATION), getFormulaWithBrickField(BrickField.Y_DESTINATION), getFormulaWithBrickField(BrickField.DURATION_IN_SECONDS)));
        return null;
    }
}
