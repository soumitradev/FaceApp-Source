package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.freeflight.drone.DroneProxy.ARDRONE_LED_ANIMATION;
import com.parrot.freeflight.service.DroneControlService;
import java.util.HashMap;
import org.catrobat.catroid.drone.ardrone.DroneServiceWrapper;

public class DronePlayLedAnimationAction extends TemporalAction {
    private static final HashMap<String, Integer> ANIMATION_TYPE_MAPPING = new HashMap();
    private DroneControlService dcs;
    private float duration = 5.0f;
    private ARDRONE_LED_ANIMATION ledAnimationType;
    private int ledNumber;

    static {
        for (ARDRONE_LED_ANIMATION ledType : ARDRONE_LED_ANIMATION.values()) {
            ANIMATION_TYPE_MAPPING.put(ledType.name(), Integer.valueOf(ARDRONE_LED_ANIMATION.valueOf(ledType.name()).ordinal()));
        }
    }

    public void setAnimationType(ARDRONE_LED_ANIMATION ledAnimationType) {
        this.ledAnimationType = ledAnimationType;
    }

    protected void begin() {
        super.begin();
        this.dcs = DroneServiceWrapper.getInstance().getDroneService();
        if (this.dcs != null) {
            ledAnimation();
        }
    }

    protected void sendLedAnimation(int ledType) {
        this.dcs.playLedAnimation(5.0f, (int) this.duration, ledType);
        super.setDuration(this.duration);
    }

    protected void ledAnimation() {
        if (this.dcs != null) {
            this.ledNumber = ((Integer) ANIMATION_TYPE_MAPPING.get(this.ledAnimationType.name())).intValue();
            sendLedAnimation(this.ledNumber);
        }
    }

    protected void update(float percent) {
    }
}
