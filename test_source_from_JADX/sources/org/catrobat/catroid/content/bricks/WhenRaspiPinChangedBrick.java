package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.RaspiInterruptScript;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class WhenRaspiPinChangedBrick extends BrickBaseType implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private RaspiInterruptScript script;

    /* renamed from: org.catrobat.catroid.content.bricks.WhenRaspiPinChangedBrick$2 */
    class C18092 implements OnItemSelectedListener {
        C18092() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            if (position < BrickValues.RASPI_EVENTS.length) {
                WhenRaspiPinChangedBrick.this.script.setEventValue(BrickValues.RASPI_EVENTS[position]);
            }
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public WhenRaspiPinChangedBrick(RaspiInterruptScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    public BrickBaseType clone() throws CloneNotSupportedException {
        WhenRaspiPinChangedBrick clone = (WhenRaspiPinChangedBrick) super.clone();
        clone.script = (RaspiInterruptScript) this.script.clone();
        clone.script.setScriptBrick(clone);
        return clone;
    }

    public int getViewResource() {
        return R.layout.brick_raspi_pin_changed;
    }

    public View getView(Context context) {
        super.getView(context);
        setupValueSpinner(context);
        setupPinSpinner(context);
        return this.view;
    }

    public View getPrototypeView(Context context) {
        View prototypeView = super.getPrototypeView(context);
        Spinner pinSpinner = (Spinner) prototypeView.findViewById(R.id.brick_raspi_when_pinspinner);
        ArrayAdapter<String> messageAdapter = new ArrayAdapter(context, 17367048);
        messageAdapter.setDropDownViewResource(17367049);
        messageAdapter.add(this.script.getPin());
        pinSpinner.setAdapter(messageAdapter);
        ((Spinner) prototypeView.findViewById(R.id.brick_raspi_when_valuespinner)).setAdapter(getValueSpinnerArrayAdapter(context));
        return prototypeView;
    }

    private void setupPinSpinner(Context context) {
        final Spinner pinSpinner = (Spinner) this.view.findViewById(R.id.brick_raspi_when_pinspinner);
        ArrayList<Integer> availableGPIOs = RaspberryPiService.getInstance().getGpioList(SettingsFragment.getRaspiRevision(context));
        ArrayAdapter<String> messageAdapter2 = new ArrayAdapter(context, 17367048);
        messageAdapter2.setDropDownViewResource(17367049);
        Iterator it = availableGPIOs.iterator();
        while (it.hasNext()) {
            messageAdapter2.add(((Integer) it.next()).toString());
        }
        pinSpinner.setAdapter(messageAdapter2);
        pinSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                WhenRaspiPinChangedBrick.this.script.setPin(pinSpinner.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        pinSpinner.setSelection(messageAdapter2.getPosition(this.script.getPin()), true);
    }

    private void setupValueSpinner(Context context) {
        Spinner valueSpinner = (Spinner) this.view.findViewById(R.id.brick_raspi_when_valuespinner);
        valueSpinner.setAdapter(getValueSpinnerArrayAdapter(context));
        valueSpinner.setOnItemSelectedListener(new C18092());
        for (int i = 0; i < BrickValues.RASPI_EVENTS.length; i++) {
            if (BrickValues.RASPI_EVENTS[i].equals(this.script.getEventValue())) {
                valueSpinner.setSelection(i, true);
                return;
            }
        }
    }

    private ArrayAdapter<String> getValueSpinnerArrayAdapter(Context context) {
        ArrayAdapter<String> messageAdapter = new ArrayAdapter(context, 17367048);
        messageAdapter.setDropDownViewResource(17367049);
        messageAdapter.add(context.getString(R.string.brick_raspi_pressed_text));
        messageAdapter.add(context.getString(R.string.brick_raspi_released_text));
        return messageAdapter;
    }

    public RaspiInterruptScript getScript() {
        return this.script;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(7));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        return null;
    }

    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        this.script.setCommentedOut(commentedOut);
    }
}
