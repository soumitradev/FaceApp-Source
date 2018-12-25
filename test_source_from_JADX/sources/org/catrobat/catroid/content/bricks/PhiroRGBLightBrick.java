package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.fragment.ColorSeekbar;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;

public class PhiroRGBLightBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String eye;

    /* renamed from: org.catrobat.catroid.content.bricks.PhiroRGBLightBrick$1 */
    class C17961 implements OnItemSelectedListener {
        C17961() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            PhiroRGBLightBrick.this.eye = Eye.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum Eye {
        LEFT,
        RIGHT,
        BOTH
    }

    public PhiroRGBLightBrick() {
        addAllowedBrickField(BrickField.PHIRO_LIGHT_RED, R.id.brick_phiro_rgb_led_action_red_edit_text);
        addAllowedBrickField(BrickField.PHIRO_LIGHT_GREEN, R.id.brick_phiro_rgb_led_action_green_edit_text);
        addAllowedBrickField(BrickField.PHIRO_LIGHT_BLUE, R.id.brick_phiro_rgb_led_action_blue_edit_text);
    }

    public PhiroRGBLightBrick(Eye eyeEnum, int red, int green, int blue) {
        this(eyeEnum, new Formula(Integer.valueOf(red)), new Formula(Integer.valueOf(green)), new Formula(Integer.valueOf(blue)));
    }

    public PhiroRGBLightBrick(Eye eyeEnum, Formula redFormula, Formula greenFormula, Formula blueFormula) {
        this();
        this.eye = eyeEnum.name();
        setFormulaWithBrickField(BrickField.PHIRO_LIGHT_RED, redFormula);
        setFormulaWithBrickField(BrickField.PHIRO_LIGHT_GREEN, greenFormula);
        setFormulaWithBrickField(BrickField.PHIRO_LIGHT_BLUE, blueFormula);
    }

    public int getViewResource() {
        return R.layout.brick_phiro_rgb_light;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getCustomView(Context context) {
        return new ColorSeekbar(this, BrickField.PHIRO_LIGHT_RED, BrickField.PHIRO_LIGHT_GREEN, BrickField.PHIRO_LIGHT_BLUE).getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.brick_phiro_select_light_spinner, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_rgb_light_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17961());
        spinner.setSelection(Eye.valueOf(this.eye).ordinal());
        return this.view;
    }

    public void showFormulaEditorToEditFormula(View view) {
        if (areAllBrickFieldsNumbers()) {
            FormulaEditorFragment.showCustomFragment(view.getContext(), this, getClickedBrickField(view));
        } else {
            super.showFormulaEditorToEditFormula(view);
        }
    }

    private boolean areAllBrickFieldsNumbers() {
        return getFormulaWithBrickField(BrickField.PHIRO_LIGHT_RED).getRoot().getElementType() == ElementType.NUMBER && getFormulaWithBrickField(BrickField.PHIRO_LIGHT_GREEN).getRoot().getElementType() == ElementType.NUMBER && getFormulaWithBrickField(BrickField.PHIRO_LIGHT_BLUE).getRoot().getElementType() == ElementType.NUMBER;
    }

    private BrickField getClickedBrickField(View view) {
        switch (view.getId()) {
            case R.id.brick_phiro_rgb_led_action_blue_edit_text:
                return BrickField.PHIRO_LIGHT_BLUE;
            case R.id.brick_phiro_rgb_led_action_green_edit_text:
                return BrickField.PHIRO_LIGHT_GREEN;
            default:
                return BrickField.PHIRO_LIGHT_RED;
        }
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(10));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPhiroRgbLedEyeActionAction(sprite, Eye.valueOf(this.eye), getFormulaWithBrickField(BrickField.PHIRO_LIGHT_RED), getFormulaWithBrickField(BrickField.PHIRO_LIGHT_GREEN), getFormulaWithBrickField(BrickField.PHIRO_LIGHT_BLUE)));
        return null;
    }
}
