package org.catrobat.catroid.drone.jumpingsumo;

import org.catrobat.catroid.content.bricks.BrickBaseType;
import org.catrobat.catroid.content.bricks.JumpingSumoAnimationsBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoAnimationsBrick.Animation;
import org.catrobat.catroid.content.bricks.JumpingSumoJumpHighBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoJumpLongBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoMoveForwardBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoNoSoundBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoRotateLeftBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoRotateRightBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoSoundBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoSoundBrick.Sounds;
import org.catrobat.catroid.content.bricks.JumpingSumoTakingPictureBrick;
import org.catrobat.catroid.content.bricks.JumpingSumoTurnBrick;

public final class JumpingSumoBrickFactory {

    public enum JumpingSumoBricks {
        JUMPING_SUMO_FORWARD,
        JUMPING_SUMO_BACKWARD,
        JUMPING_SUMO_ANIMATIONS,
        JUMPING_SUMO_SOUND,
        JUMPING_SUMO_NO_SOUND,
        JUMPING_SUMO_JUMP_LONG,
        JUMPING_SUMO_JUMP_HIGH,
        JUMPING_SUMO_ROTATE_LEFT,
        JUMPING_SUMO_ROTATE_RIGHT,
        JUMPING_SUMO_TURN,
        JUMPING_SUMO_PICTURE
    }

    private JumpingSumoBrickFactory() {
    }

    public static BrickBaseType getInstanceOfJumpingSumoBrick(JumpingSumoBricks brick, int timeInMilliseconds, byte powerInPercent, byte volumeInPercent, float degree) {
        switch (brick) {
            case JUMPING_SUMO_FORWARD:
                return new JumpingSumoMoveForwardBrick(timeInMilliseconds, (int) powerInPercent);
            case JUMPING_SUMO_BACKWARD:
                return new JumpingSumoMoveBackwardBrick(timeInMilliseconds, (int) powerInPercent);
            case JUMPING_SUMO_ANIMATIONS:
                return new JumpingSumoAnimationsBrick(Animation.SPIN);
            case JUMPING_SUMO_SOUND:
                return new JumpingSumoSoundBrick(Sounds.DEFAULT, (int) volumeInPercent);
            case JUMPING_SUMO_NO_SOUND:
                return new JumpingSumoNoSoundBrick();
            case JUMPING_SUMO_JUMP_HIGH:
                return new JumpingSumoJumpHighBrick();
            case JUMPING_SUMO_JUMP_LONG:
                return new JumpingSumoJumpLongBrick();
            case JUMPING_SUMO_ROTATE_LEFT:
                return new JumpingSumoRotateLeftBrick((double) degree);
            case JUMPING_SUMO_ROTATE_RIGHT:
                return new JumpingSumoRotateRightBrick((double) degree);
            case JUMPING_SUMO_TURN:
                return new JumpingSumoTurnBrick();
            case JUMPING_SUMO_PICTURE:
                return new JumpingSumoTakingPictureBrick();
            default:
                return null;
        }
    }
}
