package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.parrot.freeflight.drone.DroneProxy.ARDRONE_LED_ANIMATION;
import java.util.List;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.generated70026.R;

public class DronePlayLedAnimationBrick extends BrickBaseType {
    private static final long serialVersionUID = 1;
    private transient ARDRONE_LED_ANIMATION ledAnimation;
    private String ledAnimationName;

    /* renamed from: org.catrobat.catroid.content.bricks.DronePlayLedAnimationBrick$1 */
    class C17801 implements OnItemSelectedListener {
        C17801() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            DronePlayLedAnimationBrick.this.ledAnimation = ARDRONE_LED_ANIMATION.values()[position];
            DronePlayLedAnimationBrick.this.ledAnimationName = DronePlayLedAnimationBrick.this.ledAnimation.name();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public DronePlayLedAnimationBrick(ARDRONE_LED_ANIMATION ledAnimation) {
        this.ledAnimation = ledAnimation;
        this.ledAnimationName = ledAnimation.name();
    }

    protected Object readResolve() {
        if (this.ledAnimationName != null) {
            this.ledAnimation = ARDRONE_LED_ANIMATION.valueOf(this.ledAnimationName);
        }
        return this;
    }

    public int getViewResource() {
        return R.layout.brick_drone_play_led_animation;
    }

    public View getPrototypeView(Context context) {
        super.getPrototypeView(context);
        return getView(context);
    }

    public View getView(Context context) {
        super.getView(context);
        ArrayAdapter<CharSequence> animationAdapter = ArrayAdapter.createFromResource(context, R.array.brick_drone_play_led_animation_spinner, 17367048);
        animationAdapter.setDropDownViewResource(17367049);
        Spinner animationSpinner = (Spinner) this.view.findViewById(R.id.brick_drone_play_led_animation_spinner);
        animationSpinner.setAdapter(animationAdapter);
        animationSpinner.setOnItemSelectedListener(new C17801());
        if (this.ledAnimation == null) {
            readResolve();
        }
        animationSpinner.setSelection(this.ledAnimation.ordinal());
        return this.view;
    }

    public List<ScriptSequenceAction> addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDronePlayLedAnimationAction(this.ledAnimation));
        return null;
    }

    public void addRequiredResources(ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add(Integer.valueOf(5));
    }
}
