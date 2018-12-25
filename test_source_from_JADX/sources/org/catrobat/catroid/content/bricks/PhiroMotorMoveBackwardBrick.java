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
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.fragment.SingleSeekBar;

public class PhiroMotorMoveBackwardBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String motor;

    /* renamed from: org.catrobat.catroid.content.bricks.PhiroMotorMoveBackwardBrick$1 */
    class C17921 implements OnItemSelectedListener {
        C17921() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            PhiroMotorMoveBackwardBrick.this.motor = Motor.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum Motor {
        MOTOR_LEFT,
        MOTOR_RIGHT,
        MOTOR_BOTH
    }

    public PhiroMotorMoveBackwardBrick() {
        addAllowedBrickField(BrickField.PHIRO_SPEED, R.id.brick_phiro_motor_backward_action_speed_edit_text);
    }

    public PhiroMotorMoveBackwardBrick(Motor motorEnum, int speed) {
        this(motorEnum, new Formula(Integer.valueOf(speed)));
    }

    public PhiroMotorMoveBackwardBrick(Motor motorEnum, Formula formula) {
        this();
        this.motor = motorEnum.name();
        setFormulaWithBrickField(BrickField.PHIRO_SPEED, formula);
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getCustomView(Context context) {
        return new SingleSeekBar(this, BrickField.PHIRO_SPEED, R.string.phiro_motor_speed).getView(context);
    }

    public int getViewResource() {
        return R.layout.brick_phiro_motor_backward;
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.brick_phiro_select_motor_spinner, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_motor_backward_action_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17921());
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    public void showFormulaEditorToEditFormula(View view) {
        if (isSpeedOnlyANumber()) {
            FormulaEditorFragment.showCustomFragment(view.getContext(), this, BrickField.PHIRO_SPEED);
        } else {
            super.showFormulaEditorToEditFormula(view);
        }
    }

    private boolean isSpeedOnlyANumber() {
        return getFormulaWithBrickField(BrickField.PHIRO_SPEED).getRoot().getElementType() == ElementType.NUMBER;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(10));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPhiroMotorMoveBackwardActionAction(sprite, Motor.valueOf(this.motor), getFormulaWithBrickField(BrickField.PHIRO_SPEED)));
        return null;
    }
}
