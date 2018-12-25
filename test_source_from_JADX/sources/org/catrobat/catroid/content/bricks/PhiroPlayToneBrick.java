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

public class PhiroPlayToneBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String tone;

    /* renamed from: org.catrobat.catroid.content.bricks.PhiroPlayToneBrick$1 */
    class C17951 implements OnItemSelectedListener {
        C17951() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            PhiroPlayToneBrick.this.tone = Tone.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum Tone {
        DO,
        RE,
        MI,
        FA,
        SO,
        LA,
        TI
    }

    public PhiroPlayToneBrick() {
        addAllowedBrickField(BrickField.PHIRO_DURATION_IN_SECONDS, R.id.brick_phiro_play_tone_duration_edit_text);
    }

    public PhiroPlayToneBrick(Tone toneEnum, int duration) {
        this(toneEnum, new Formula(Integer.valueOf(duration)));
    }

    public PhiroPlayToneBrick(Tone toneEnum, Formula formula) {
        this();
        this.tone = toneEnum.name();
        setFormulaWithBrickField(BrickField.PHIRO_DURATION_IN_SECONDS, formula);
    }

    public int getViewResource() {
        return R.layout.brick_phiro_play_tone;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(20));
        super.addRequiredResources(requiredResourcesSet);
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.brick_phiro_select_tone_spinner, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_phiro_select_tone_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17951());
        spinner.setSelection(Tone.valueOf(this.tone).ordinal());
        setSecondsLabel(this.view, BrickField.PHIRO_DURATION_IN_SECONDS);
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createPhiroPlayToneActionAction(sprite, Tone.valueOf(this.tone), getFormulaWithBrickField(BrickField.PHIRO_DURATION_IN_SECONDS)));
        sequence.addAction(sprite.getActionFactory().createDelayAction(sprite, getFormulaWithBrickField(BrickField.PHIRO_DURATION_IN_SECONDS)));
        return null;
    }
}
