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

public class GoNStepsBackBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public GoNStepsBackBrick() {
        addAllowedBrickField(BrickField.STEPS, R.id.brick_go_back_edit_text);
    }

    public GoNStepsBackBrick(int steps) {
        this(new Formula(Integer.valueOf(steps)));
    }

    public GoNStepsBackBrick(Formula formula) {
        this();
        setFormulaWithBrickField(BrickField.STEPS, formula);
    }

    public int getViewResource() {
        return R.layout.brick_go_back;
    }

    public View getView(Context context) {
        super.getView(context);
        TextView times = (TextView) this.view.findViewById(R.id.brick_go_back_layers_text_view);
        if (getFormulaWithBrickField(BrickField.STEPS).isSingleNumberFormula()) {
            try {
                times.setText(this.view.getResources().getQuantityString(R.plurals.brick_go_back_layer_plural, Utils.convertDoubleToPluralInteger(getFormulaWithBrickField(BrickField.STEPS).interpretDouble(ProjectManager.getInstance().getCurrentSprite()).doubleValue())));
            } catch (InterpretationException interpretationException) {
                Log.d(getClass().getSimpleName(), "Couldn't interpret Formula.", interpretationException);
            }
        } else {
            times.setText(this.view.getResources().getQuantityString(R.plurals.brick_go_back_layer_plural, Utils.TRANSLATION_PLURAL_OTHER_INTEGER));
        }
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        ((TextView) prototypeView.findViewById(R.id.brick_go_back_layers_text_view)).setText(context.getResources().getQuantityString(R.plurals.brick_go_back_layer_plural, Utils.convertDoubleToPluralInteger(1.0d)));
        return prototypeView;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGoNStepsBackAction(sprite, getFormulaWithBrickField(BrickField.STEPS)));
        return null;
    }
}
