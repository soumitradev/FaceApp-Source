package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.utils.Utils;

public class MoveNStepsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public MoveNStepsBrick() {
        addAllowedBrickField(BrickField.STEPS, R.id.brick_move_n_steps_edit_text);
    }

    public MoveNStepsBrick(double steps) {
        this(new Formula(Double.valueOf(steps)));
    }

    public MoveNStepsBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.STEPS, formula);
    }

    public int getViewResource() {
        return R.layout.brick_move_n_steps;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        ((TextView) prototypeView.findViewById(R.id.brick_move_n_steps_step_text_view)).setText(context.getResources().getQuantityString(R.plurals.brick_move_n_step_plural, Utils.convertDoubleToPluralInteger(10.0d)));
        return prototypeView;
    }

    public View getView(Context context) {
        super.getView(context);
        TextView label = (TextView) this.view.findViewById(R.id.brick_move_n_steps_step_text_view);
        if (getFormulaWithBrickField(BrickField.STEPS).isSingleNumberFormula()) {
            try {
                label.setText(this.view.getResources().getQuantityString(R.plurals.brick_move_n_step_plural, Utils.convertDoubleToPluralInteger(getFormulaWithBrickField(BrickField.STEPS).interpretDouble(ProjectManager.getInstance().getCurrentSprite()).doubleValue())));
            } catch (InterpretationException interpretationException) {
                Log.d(getClass().getSimpleName(), "Couldn't interpret Formula.", interpretationException);
            }
        } else {
            label.setText(this.view.getResources().getQuantityString(R.plurals.brick_move_n_step_plural, Utils.TRANSLATION_PLURAL_OTHER_INTEGER));
        }
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createMoveNStepsAction(sprite, getFormulaWithBrickField(BrickField.STEPS)));
        return null;
    }
}
