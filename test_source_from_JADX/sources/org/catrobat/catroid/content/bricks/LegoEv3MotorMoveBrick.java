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

public class LegoEv3MotorMoveBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String motor;

    /* renamed from: org.catrobat.catroid.content.bricks.LegoEv3MotorMoveBrick$1 */
    class C17841 implements OnItemSelectedListener {
        C17841() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            LegoEv3MotorMoveBrick.this.motor = Motor.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum Motor {
        MOTOR_A,
        MOTOR_B,
        MOTOR_C,
        MOTOR_D,
        MOTOR_B_C
    }

    public LegoEv3MotorMoveBrick() {
        addAllowedBrickField(BrickField.LEGO_EV3_SPEED, R.id.ev3_motor_move_speed_edit_text);
    }

    public LegoEv3MotorMoveBrick(Motor motorEnum, int speedValue) {
        this(motorEnum, new Formula(Integer.valueOf(speedValue)));
    }

    public LegoEv3MotorMoveBrick(Motor motorEnum, Formula speed) {
        this();
        this.motor = motorEnum.name();
        setFormulaWithBrickField(BrickField.LEGO_EV3_SPEED, speed);
    }

    public int getViewResource() {
        return R.layout.brick_ev3_motor_move;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.ev3_motor_chooser, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_ev3_motor_move_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17841());
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(2));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoEv3SingleMotorMoveAction(sprite, Motor.valueOf(this.motor), getFormulaWithBrickField(BrickField.LEGO_EV3_SPEED)));
        return null;
    }
}
