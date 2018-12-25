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

public class LegoEv3SetLedBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private String ledStatus;

    /* renamed from: org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick$1 */
    class C17871 implements OnItemSelectedListener {
        C17871() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            LegoEv3SetLedBrick.this.ledStatus = LedStatus.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum LedStatus {
        LED_OFF,
        LED_GREEN,
        LED_RED,
        LED_ORANGE,
        LED_GREEN_FLASHING,
        LED_RED_FLASHING,
        LED_ORANGE_FLASHING,
        LED_GREEN_PULSE,
        LED_RED_PULSE,
        LED_ORANGE_PULSE
    }

    public LegoEv3SetLedBrick(LedStatus ledStatusEnum) {
        this.ledStatus = ledStatusEnum.name();
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public int getViewResource() {
        return R.layout.brick_ev3_set_led;
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.ev3_led_status_chooser, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_ev3_set_led_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17871());
        spinner.setSelection(LedStatus.valueOf(this.ledStatus).ordinal());
        return this.view;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(20));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLegoEv3SetLedAction(LedStatus.valueOf(this.ledStatus)));
        return null;
    }
}
