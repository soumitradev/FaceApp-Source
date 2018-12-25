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

public class JumpingSumoAnimationsBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private String animationName;

    /* renamed from: org.catrobat.catroid.content.bricks.JumpingSumoAnimationsBrick$1 */
    class C17821 implements OnItemSelectedListener {
        C17821() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            JumpingSumoAnimationsBrick.this.animationName = Animation.values()[position].name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public enum Animation {
        SPIN,
        TAB,
        SLOWSHAKE,
        METRONOME,
        ONDULATION,
        SPINJUMP,
        SPIRAL,
        SLALOM
    }

    public JumpingSumoAnimationsBrick(Animation animation) {
        this.animationName = animation.name();
    }

    public int getViewResource() {
        return R.layout.brick_jumping_sumo_animations;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.brick_jumping_sumo_select_animation_spinner, 17367048);
        spinnerAdapter.setDropDownViewResource(17367049);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.brick_jumping_sumo_animation_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new C17821());
        spinner.setSelection(Animation.valueOf(this.animationName).ordinal());
        return this.view;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(23));
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createJumpingSumoAnimationAction(Animation.valueOf(this.animationName)));
        return null;
    }
}
