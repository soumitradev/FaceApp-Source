package org.catrobat.catroid.drone.ardrone;

import com.parrot.freeflight.drone.DroneProxy.ARDRONE_LED_ANIMATION;
import org.catrobat.catroid.content.bricks.BrickBaseType;
import org.catrobat.catroid.content.bricks.DroneEmergencyBrick;
import org.catrobat.catroid.content.bricks.DroneFlipBrick;
import org.catrobat.catroid.content.bricks.DroneMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.DroneMoveDownBrick;
import org.catrobat.catroid.content.bricks.DroneMoveForwardBrick;
import org.catrobat.catroid.content.bricks.DroneMoveLeftBrick;
import org.catrobat.catroid.content.bricks.DroneMoveRightBrick;
import org.catrobat.catroid.content.bricks.DroneMoveUpBrick;
import org.catrobat.catroid.content.bricks.DronePlayLedAnimationBrick;
import org.catrobat.catroid.content.bricks.DroneSwitchCameraBrick;
import org.catrobat.catroid.content.bricks.DroneTakeOffLandBrick;
import org.catrobat.catroid.content.bricks.DroneTurnLeftBrick;
import org.catrobat.catroid.content.bricks.DroneTurnRightBrick;

public final class DroneBrickFactory {

    public enum DroneBricks {
        DRONE_TAKE_OFF_LAND_BRICK,
        DRONE_FLIP_BRICK,
        DRONE_PLAY_LED_ANIMATION_BRICK,
        DRONE_GO_EMERGENCY,
        DRONE_MOVE_DOWN_BRICK,
        DRONE_MOVE_UP_BRICK,
        DRONE_MOVE_LEFT_BRICK,
        DRONE_MOVE_RIGHT_BRICK,
        DRONE_MOVE_BACKWARD_BRICK,
        DRONE_MOVE_FORWARD_BRICK,
        DRONE_TURN_RIGHT_BRICK,
        DRONE_TURN_LEFT_BRICK,
        DRONE_SWITCH_CAMERA_BRICK
    }

    private DroneBrickFactory() {
    }

    public static BrickBaseType getInstanceOfDroneBrick(DroneBricks brick, int timeInMilliseconds, int powerInPercent) {
        switch (brick) {
            case DRONE_TAKE_OFF_LAND_BRICK:
                return new DroneTakeOffLandBrick();
            case DRONE_FLIP_BRICK:
                return new DroneFlipBrick();
            case DRONE_PLAY_LED_ANIMATION_BRICK:
                return new DronePlayLedAnimationBrick(ARDRONE_LED_ANIMATION.ARDRONE_LED_ANIMATION_BLINK_GREEN_RED);
            case DRONE_MOVE_DOWN_BRICK:
                return new DroneMoveDownBrick(timeInMilliseconds, powerInPercent);
            case DRONE_MOVE_UP_BRICK:
                return new DroneMoveUpBrick(timeInMilliseconds, powerInPercent);
            case DRONE_MOVE_FORWARD_BRICK:
                return new DroneMoveForwardBrick(timeInMilliseconds, powerInPercent);
            case DRONE_MOVE_BACKWARD_BRICK:
                return new DroneMoveBackwardBrick(timeInMilliseconds, powerInPercent);
            case DRONE_MOVE_LEFT_BRICK:
                return new DroneMoveLeftBrick(timeInMilliseconds, powerInPercent);
            case DRONE_MOVE_RIGHT_BRICK:
                return new DroneMoveRightBrick(timeInMilliseconds, powerInPercent);
            case DRONE_TURN_LEFT_BRICK:
                return new DroneTurnLeftBrick(timeInMilliseconds, powerInPercent);
            case DRONE_TURN_RIGHT_BRICK:
                return new DroneTurnRightBrick(timeInMilliseconds, powerInPercent);
            case DRONE_SWITCH_CAMERA_BRICK:
                return new DroneSwitchCameraBrick();
            case DRONE_GO_EMERGENCY:
                return new DroneEmergencyBrick();
            default:
                return null;
        }
    }
}
