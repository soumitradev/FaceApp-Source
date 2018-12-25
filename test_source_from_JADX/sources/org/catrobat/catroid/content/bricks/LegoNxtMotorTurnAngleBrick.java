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
import org.catrobat.catroid.generated70026.R;

public class LegoNxtMotorTurnAngleBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String motor;

    /* renamed from: org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick$1 */
    class C17901 implements OnItemSelectedListener {
        C17901() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            LegoNxtMotorTurnAngleBrick.this.motor = Motor.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum Motor {
        MOTOR_A,
        MOTOR_B,
        MOTOR_C,
        MOTOR_B_C
    }

    public LegoNxtMotorTurnAngleBrick() {
        addAllowedBrickField(BrickField.LEGO_NXT_DEGREES, R.id.motor_turn_angle_edit_text);
    }

    public LegoNxtMotorTurnAngleBrick(Motor motorEnum, int degrees) {
        this(motorEnum, new Formula(Integer.valueOf(degrees)));
    }

    public LegoNxtMotorTurnAngleBrick(Motor motorEnum, Formula degreesFormula) {
        this();
        this.motor = motorEnum.name();
        setFormulaWithBrickField(BrickField.LEGO_NXT_DEGREES, degreesFormula);
    }

    public int getViewResource() {
        return R.layout.brick_nxt_motor_turn_angle;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.nxt_motor_chooser, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.lego_motor_turn_angle_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17901());
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(2));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoNxtMotorTurnAngleAction(sprite, Motor.valueOf(this.motor), getFormulaWithBrickField(BrickField.LEGO_NXT_DEGREES)));
        return null;
    }
}
