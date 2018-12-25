package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import org.catrobat.catroid.content.bricks.JumpingSumoAnimationsBrick.Animation;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;

public class JumpingSumoAnimationAction extends TemporalAction {
    private Animation animationType;
    private JumpingSumoDeviceController controller;
    private ARDeviceController deviceController;
    private float duration;

    public void setAnimationType(Animation animationType) {
        this.animationType = animationType;
    }

    protected void begin() {
        super.begin();
        this.controller = JumpingSumoDeviceController.getInstance();
        this.deviceController = this.controller.getDeviceController();
        animation();
    }

    protected void animation() {
        if (this.deviceController != null) {
            switch (this.animationType) {
                case SPIN:
                    this.deviceController.getFeatureJumpingSumo().sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_SPIN);
                    this.duration = 2.8f;
                    super.setDuration(this.duration);
                    return;
                case TAB:
                    this.deviceController.getFeatureJumpingSumo().sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_TAP);
                    this.duration = 1.2f;
                    super.setDuration(this.duration);
                    return;
                case SLOWSHAKE:
                    this.deviceController.getFeatureJumpingSumo().sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_SLOWSHAKE);
                    this.duration = 2.2f;
                    super.setDuration(this.duration);
                    return;
                case METRONOME:
                    this.deviceController.getFeatureJumpingSumo().sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_METRONOME);
                    this.duration = 3.2f;
                    super.setDuration(this.duration);
                    return;
                case ONDULATION:
                    this.deviceController.getFeatureJumpingSumo().sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ONDULATION);
                    this.duration = 1.6f;
                    super.setDuration(this.duration);
                    return;
                case SPINJUMP:
                    this.deviceController.getFeatureJumpingSumo().sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_SPINJUMP);
                    this.duration = 5.5f;
                    super.setDuration(this.duration);
                    return;
                case SPIRAL:
                    this.deviceController.getFeatureJumpingSumo().sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_SPIRAL);
                    this.duration = 9.6f;
                    super.setDuration(this.duration);
                    return;
                case SLALOM:
                    this.deviceController.getFeatureJumpingSumo().sendAnimationsSimpleAnimation(ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_ENUM.ARCOMMANDS_JUMPINGSUMO_ANIMATIONS_SIMPLEANIMATION_ID_SLALOM);
                    this.duration = 2.1f;
                    super.setDuration(this.duration);
                    return;
                default:
                    return;
            }
        }
    }

    protected void update(float percent) {
    }
}
