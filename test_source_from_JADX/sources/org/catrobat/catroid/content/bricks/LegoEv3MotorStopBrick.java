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
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.generated70026.R;

public class LegoEv3MotorStopBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private String motor;

    /* renamed from: org.catrobat.catroid.content.bricks.LegoEv3MotorStopBrick$1 */
    class C17851 implements OnItemSelectedListener {
        C17851() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            LegoEv3MotorStopBrick.this.motor = Motor.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum Motor {
        MOTOR_A,
        MOTOR_B,
        MOTOR_C,
        MOTOR_D,
        MOTOR_B_C,
        ALL_MOTORS
    }

    public LegoEv3MotorStopBrick(Motor motorEnum) {
        this.motor = motorEnum.name();
    }

    public int getViewResource() {
        return R.layout.brick_ev3_motor_stop;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.ev3_stop_motor_chooser, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.ev3_stop_motor_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17851());
        spinner.setSelection(Motor.valueOf(this.motor).ordinal());
        return this.view;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(20));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoEv3MotorStopAction(Motor.valueOf(this.motor)));
        return null;
    }
}
