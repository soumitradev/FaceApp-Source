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

public class JumpingSumoSoundBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;
    private String soundName;

    /* renamed from: org.catrobat.catroid.content.bricks.JumpingSumoSoundBrick$1 */
    class C17831 implements OnItemSelectedListener {
        C17831() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            JumpingSumoSoundBrick.this.soundName = Sounds.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum Sounds {
        DEFAULT,
        ROBOT,
        INSECT,
        MONSTER
    }

    public JumpingSumoSoundBrick() {
        addAllowedBrickField(BrickField.JUMPING_SUMO_VOLUME, R.id.brick_jumping_sumo_sound_edit_text);
    }

    public JumpingSumoSoundBrick(Sounds soundEnum, int volumeInPercent) {
        this(soundEnum, new Formula(Integer.valueOf(volumeInPercent)));
    }

    public JumpingSumoSoundBrick(Sounds soundEnum, Formula formula) {
        this();
        this.soundName = soundEnum.name();
        setFormulaWithBrickField(BrickField.JUMPING_SUMO_VOLUME, formula);
    }

    public int getViewResource() {
        return R.layout.brick_jumping_sumo_sound;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.brick_jumping_sumo_select_sound_spinner, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_jumping_sumo_sound_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17831());
        spinner.setSelection(Sounds.valueOf(this.soundName).ordinal());
        return this.view;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(23));
        super.addRequiredResources(requiredResourcesSet);
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createJumpingSumoSoundAction(sprite, Sounds.valueOf(this.soundName), getFormulaWithBrickField(BrickField.JUMPING_SUMO_VOLUME)));
        return null;
    }
}
