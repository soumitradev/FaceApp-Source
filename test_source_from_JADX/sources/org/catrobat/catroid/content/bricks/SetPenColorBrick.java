package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.fragment.ColorSeekbar;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;

public class SetPenColorBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    public SetPenColorBrick() {
        addAllowedBrickField(BrickField.PEN_COLOR_RED, R.id.brick_set_pen_color_action_red_edit_text);
        addAllowedBrickField(BrickField.PEN_COLOR_GREEN, R.id.brick_set_pen_color_action_green_edit_text);
        addAllowedBrickField(BrickField.PEN_COLOR_BLUE, R.id.brick_set_pen_color_action_blue_edit_text);
    }

    public SetPenColorBrick(int red, int green, int blue) {
        this(new Formula(Integer.valueOf(red)), new Formula(Integer.valueOf(green)), new Formula(Integer.valueOf(blue)));
    }

    public SetPenColorBrick(Formula red, Formula green, Formula blue) {
        this();
        setFormulaWithBrickField(BrickField.PEN_COLOR_RED, red);
        setFormulaWithBrickField(BrickField.PEN_COLOR_GREEN, green);
        setFormulaWithBrickField(BrickField.PEN_COLOR_BLUE, blue);
    }

    public View getCustomView(Context context) {
        return new ColorSeekbar(this, BrickField.PEN_COLOR_RED, BrickField.PEN_COLOR_GREEN, BrickField.PEN_COLOR_BLUE).getView(context);
    }

    public int getViewResource() {
        return R.layout.brick_set_pen_color;
    }

    public void showFormulaEditorToEditFormula(View view) {
        if (areAllBrickFieldsNumbers()) {
            FormulaEditorFragment.showCustomFragment(view.getContext(), this, getClickedBrickField(view));
        } else {
            super.showFormulaEditorToEditFormula(view);
        }
    }

    private boolean areAllBrickFieldsNumbers() {
        return getFormulaWithBrickField(BrickField.PEN_COLOR_RED).getRoot().getElementType() == ElementType.NUMBER && getFormulaWithBrickField(BrickField.PEN_COLOR_GREEN).getRoot().getElementType() == ElementType.NUMBER && getFormulaWithBrickField(BrickField.PEN_COLOR_BLUE).getRoot().getElementType() == ElementType.NUMBER;
    }

    private BrickField getClickedBrickField(View view) {
        switch (view.getId()) {
            case R.id.brick_set_pen_color_action_blue_edit_text:
                return BrickField.PEN_COLOR_BLUE;
            case R.id.brick_set_pen_color_action_green_edit_text:
                return BrickField.PEN_COLOR_GREEN;
            default:
                return BrickField.PEN_COLOR_RED;
        }
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetPenColorAction(sprite, getFormulaWithBrickField(BrickField.PEN_COLOR_RED), getFormulaWithBrickField(BrickField.PEN_COLOR_GREEN), getFormulaWithBrickField(BrickField.PEN_COLOR_BLUE)));
        return null;
    }

    public void correctBrickFieldsFromPhiro() {
        replaceFormulaBrickField(BrickField.PHIRO_LIGHT_RED, BrickField.PEN_COLOR_RED);
        replaceFormulaBrickField(BrickField.PHIRO_LIGHT_GREEN, BrickField.PEN_COLOR_GREEN);
        replaceFormulaBrickField(BrickField.PHIRO_LIGHT_BLUE, BrickField.PEN_COLOR_BLUE);
    }
}
