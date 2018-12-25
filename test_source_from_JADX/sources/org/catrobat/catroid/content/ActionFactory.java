package org.catrobat.catroid.content;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.parrot.freeflight.drone.DroneProxy.ARDRONE_LED_ANIMATION;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.camera.CameraManager.CameraState;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.actions.AddItemToUserListAction;
import org.catrobat.catroid.content.actions.ArduinoSendDigitalValueAction;
import org.catrobat.catroid.content.actions.ArduinoSendPWMValueAction;
import org.catrobat.catroid.content.actions.AskAction;
import org.catrobat.catroid.content.actions.AskSpeechAction;
import org.catrobat.catroid.content.actions.CameraBrickAction;
import org.catrobat.catroid.content.actions.ChangeBrightnessByNAction;
import org.catrobat.catroid.content.actions.ChangeColorByNAction;
import org.catrobat.catroid.content.actions.ChangeSizeByNAction;
import org.catrobat.catroid.content.actions.ChangeTransparencyByNAction;
import org.catrobat.catroid.content.actions.ChangeVariableAction;
import org.catrobat.catroid.content.actions.ChangeVolumeByNAction;
import org.catrobat.catroid.content.actions.ChangeXByNAction;
import org.catrobat.catroid.content.actions.ChangeYByNAction;
import org.catrobat.catroid.content.actions.ChooseCameraAction;
import org.catrobat.catroid.content.actions.ClearBackgroundAction;
import org.catrobat.catroid.content.actions.ClearGraphicEffectAction;
import org.catrobat.catroid.content.actions.CloneAction;
import org.catrobat.catroid.content.actions.ComeToFrontAction;
import org.catrobat.catroid.content.actions.DeleteItemOfUserListAction;
import org.catrobat.catroid.content.actions.DeleteThisCloneAction;
import org.catrobat.catroid.content.actions.DroneEmergencyAction;
import org.catrobat.catroid.content.actions.DroneFlipAction;
import org.catrobat.catroid.content.actions.DroneMoveBackwardAction;
import org.catrobat.catroid.content.actions.DroneMoveDownAction;
import org.catrobat.catroid.content.actions.DroneMoveForwardAction;
import org.catrobat.catroid.content.actions.DroneMoveLeftAction;
import org.catrobat.catroid.content.actions.DroneMoveRightAction;
import org.catrobat.catroid.content.actions.DroneMoveUpAction;
import org.catrobat.catroid.content.actions.DronePlayLedAnimationAction;
import org.catrobat.catroid.content.actions.DroneSwitchCameraAction;
import org.catrobat.catroid.content.actions.DroneTakeoffAndLandAction;
import org.catrobat.catroid.content.actions.DroneTurnLeftAction;
import org.catrobat.catroid.content.actions.DroneTurnLeftWithMagnetometerAction;
import org.catrobat.catroid.content.actions.DroneTurnRightAction;
import org.catrobat.catroid.content.actions.DroneTurnRightWithMagnetometerAction;
import org.catrobat.catroid.content.actions.EventAction;
import org.catrobat.catroid.content.actions.EventThread;
import org.catrobat.catroid.content.actions.FlashAction;
import org.catrobat.catroid.content.actions.GoNStepsBackAction;
import org.catrobat.catroid.content.actions.GoToOtherSpritePositionAction;
import org.catrobat.catroid.content.actions.GoToRandomPositionAction;
import org.catrobat.catroid.content.actions.GoToTouchPositionAction;
import org.catrobat.catroid.content.actions.HideTextAction;
import org.catrobat.catroid.content.actions.IfLogicAction;
import org.catrobat.catroid.content.actions.InsertItemIntoUserListAction;
import org.catrobat.catroid.content.actions.JumpingSumoAnimationAction;
import org.catrobat.catroid.content.actions.JumpingSumoJumpHighAction;
import org.catrobat.catroid.content.actions.JumpingSumoJumpLongAction;
import org.catrobat.catroid.content.actions.JumpingSumoMoveBackwardAction;
import org.catrobat.catroid.content.actions.JumpingSumoMoveForwardAction;
import org.catrobat.catroid.content.actions.JumpingSumoNoSoundAction;
import org.catrobat.catroid.content.actions.JumpingSumoRotateLeftAction;
import org.catrobat.catroid.content.actions.JumpingSumoRotateRightAction;
import org.catrobat.catroid.content.actions.JumpingSumoSoundAction;
import org.catrobat.catroid.content.actions.JumpingSumoTakingPictureAction;
import org.catrobat.catroid.content.actions.JumpingSumoTurnAction;
import org.catrobat.catroid.content.actions.LegoEv3MotorMoveAction;
import org.catrobat.catroid.content.actions.LegoEv3MotorStopAction;
import org.catrobat.catroid.content.actions.LegoEv3MotorTurnAngleAction;
import org.catrobat.catroid.content.actions.LegoEv3PlayToneAction;
import org.catrobat.catroid.content.actions.LegoEv3SetLedAction;
import org.catrobat.catroid.content.actions.LegoNxtMotorMoveAction;
import org.catrobat.catroid.content.actions.LegoNxtMotorStopAction;
import org.catrobat.catroid.content.actions.LegoNxtMotorTurnAngleAction;
import org.catrobat.catroid.content.actions.LegoNxtPlayToneAction;
import org.catrobat.catroid.content.actions.MoveNStepsAction;
import org.catrobat.catroid.content.actions.NextLookAction;
import org.catrobat.catroid.content.actions.NotifyEventWaiterAction;
import org.catrobat.catroid.content.actions.PenDownAction;
import org.catrobat.catroid.content.actions.PenUpAction;
import org.catrobat.catroid.content.actions.PhiroMotorMoveBackwardAction;
import org.catrobat.catroid.content.actions.PhiroMotorMoveForwardAction;
import org.catrobat.catroid.content.actions.PhiroMotorStopAction;
import org.catrobat.catroid.content.actions.PhiroPlayToneAction;
import org.catrobat.catroid.content.actions.PhiroRGBLightAction;
import org.catrobat.catroid.content.actions.PhiroSensorAction;
import org.catrobat.catroid.content.actions.PlaySoundAction;
import org.catrobat.catroid.content.actions.PointInDirectionAction;
import org.catrobat.catroid.content.actions.PointToAction;
import org.catrobat.catroid.content.actions.PreviousLookAction;
import org.catrobat.catroid.content.actions.RaspiIfLogicAction;
import org.catrobat.catroid.content.actions.RaspiPwmAction;
import org.catrobat.catroid.content.actions.RaspiSendDigitalValueAction;
import org.catrobat.catroid.content.actions.RepeatAction;
import org.catrobat.catroid.content.actions.RepeatUntilAction;
import org.catrobat.catroid.content.actions.ReplaceItemInUserListAction;
import org.catrobat.catroid.content.actions.SceneStartAction;
import org.catrobat.catroid.content.actions.SceneTransitionAction;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.SetBrightnessAction;
import org.catrobat.catroid.content.actions.SetColorAction;
import org.catrobat.catroid.content.actions.SetLookAction;
import org.catrobat.catroid.content.actions.SetLookByIndexAction;
import org.catrobat.catroid.content.actions.SetNfcTagAction;
import org.catrobat.catroid.content.actions.SetPenColorAction;
import org.catrobat.catroid.content.actions.SetPenSizeAction;
import org.catrobat.catroid.content.actions.SetRotationStyleAction;
import org.catrobat.catroid.content.actions.SetSizeToAction;
import org.catrobat.catroid.content.actions.SetTextAction;
import org.catrobat.catroid.content.actions.SetTransparencyAction;
import org.catrobat.catroid.content.actions.SetVariableAction;
import org.catrobat.catroid.content.actions.SetVisibleAction;
import org.catrobat.catroid.content.actions.SetVolumeToAction;
import org.catrobat.catroid.content.actions.SetXAction;
import org.catrobat.catroid.content.actions.SetYAction;
import org.catrobat.catroid.content.actions.ShowTextAction;
import org.catrobat.catroid.content.actions.SpeakAction;
import org.catrobat.catroid.content.actions.StampAction;
import org.catrobat.catroid.content.actions.StopAllScriptsAction;
import org.catrobat.catroid.content.actions.StopAllSoundsAction;
import org.catrobat.catroid.content.actions.StopOtherScriptsAction;
import org.catrobat.catroid.content.actions.StopThisScriptAction;
import org.catrobat.catroid.content.actions.ThinkSayBubbleAction;
import org.catrobat.catroid.content.actions.TurnLeftAction;
import org.catrobat.catroid.content.actions.TurnRightAction;
import org.catrobat.catroid.content.actions.UserBrickAction;
import org.catrobat.catroid.content.actions.VibrateAction;
import org.catrobat.catroid.content.actions.WaitAction;
import org.catrobat.catroid.content.actions.WaitForBubbleBrickAction;
import org.catrobat.catroid.content.actions.WaitUntilAction;
import org.catrobat.catroid.content.actions.conditional.GlideToAction;
import org.catrobat.catroid.content.actions.conditional.IfOnEdgeBounceAction;
import org.catrobat.catroid.content.bricks.JumpingSumoAnimationsBrick.Animation;
import org.catrobat.catroid.content.bricks.JumpingSumoSoundBrick.Sounds;
import org.catrobat.catroid.content.bricks.LegoEv3MotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick.LedStatus;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick.Motor;
import org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveForwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorStopBrick;
import org.catrobat.catroid.content.bricks.PhiroPlayToneBrick.Tone;
import org.catrobat.catroid.content.bricks.PhiroRGBLightBrick.Eye;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.content.eventids.BroadcastEventId;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.physics.PhysicsObject.Type;

public class ActionFactory extends Actions {
    public EventAction createBroadcastAction(String broadcastMessage, int waitMode) {
        return createEventAction(new BroadcastEventId(broadcastMessage), waitMode);
    }

    private EventAction createEventAction(EventId eventId, int waitMode) {
        EventWrapper event = new EventWrapper(eventId, waitMode);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        EventAction action = (EventAction) Actions.action(EventAction.class);
        action.setEvent(event);
        action.setReceivingSprites(currentProject.getSpriteListWithClones());
        return action;
    }

    public Action createNotifyEventWaiterAction(Sprite sprite, EventWrapper event) {
        NotifyEventWaiterAction action = (NotifyEventWaiterAction) Actions.action(NotifyEventWaiterAction.class);
        action.setEvent(event);
        action.setSprite(sprite);
        return action;
    }

    public Action createWaitAction(Sprite sprite, Formula delay) {
        WaitAction action = (WaitAction) action(WaitAction.class);
        action.setSprite(sprite);
        action.setDelay(delay);
        return action;
    }

    public Action createWaitForBubbleBrickAction(Sprite sprite, Formula delay) {
        WaitForBubbleBrickAction action = (WaitForBubbleBrickAction) Actions.action(WaitForBubbleBrickAction.class);
        action.setSprite(sprite);
        action.setDelay(delay);
        return action;
    }

    public Action createChangeBrightnessByNAction(Sprite sprite, Formula changeBrightness) {
        ChangeBrightnessByNAction action = (ChangeBrightnessByNAction) Actions.action(ChangeBrightnessByNAction.class);
        action.setSprite(sprite);
        action.setBrightness(changeBrightness);
        return action;
    }

    public Action createChangeColorByNAction(Sprite sprite, Formula changeColor) {
        ChangeColorByNAction action = (ChangeColorByNAction) Actions.action(ChangeColorByNAction.class);
        action.setSprite(sprite);
        action.setColor(changeColor);
        return action;
    }

    public Action createChangeTransparencyByNAction(Sprite sprite, Formula transparency) {
        ChangeTransparencyByNAction action = (ChangeTransparencyByNAction) Actions.action(ChangeTransparencyByNAction.class);
        action.setSprite(sprite);
        action.setTransparency(transparency);
        return action;
    }

    public Action createChangeSizeByNAction(Sprite sprite, Formula size) {
        ChangeSizeByNAction action = (ChangeSizeByNAction) Actions.action(ChangeSizeByNAction.class);
        action.setSprite(sprite);
        action.setSize(size);
        return action;
    }

    public Action createChangeVolumeByNAction(Sprite sprite, Formula volume) {
        ChangeVolumeByNAction action = (ChangeVolumeByNAction) Actions.action(ChangeVolumeByNAction.class);
        action.setVolume(volume);
        action.setSprite(sprite);
        return action;
    }

    public Action createChangeXByNAction(Sprite sprite, Formula xMovement) {
        ChangeXByNAction action = (ChangeXByNAction) Actions.action(ChangeXByNAction.class);
        action.setSprite(sprite);
        action.setxMovement(xMovement);
        return action;
    }

    public Action createChangeYByNAction(Sprite sprite, Formula yMovement) {
        ChangeYByNAction action = (ChangeYByNAction) Actions.action(ChangeYByNAction.class);
        action.setSprite(sprite);
        action.setyMovement(yMovement);
        return action;
    }

    public Action createSetRotationStyleAction(Sprite sprite, int mode) {
        SetRotationStyleAction action = (SetRotationStyleAction) Actions.action(SetRotationStyleAction.class);
        action.setRotationStyle(mode);
        action.setSprite(sprite);
        return action;
    }

    public Action createClearGraphicEffectAction(Sprite sprite) {
        ClearGraphicEffectAction action = (ClearGraphicEffectAction) Actions.action(ClearGraphicEffectAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createComeToFrontAction(Sprite sprite) {
        ComeToFrontAction action = (ComeToFrontAction) Actions.action(ComeToFrontAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createGlideToAction(Sprite sprite, Formula x, Formula y, Formula duration) {
        GlideToAction action = (GlideToAction) Actions.action(GlideToAction.class);
        action.setPosition(x, y);
        action.setDuration(duration);
        action.setSprite(sprite);
        return action;
    }

    public Action createGlideToAction(Sprite sprite, Formula x, Formula y, Formula duration, Interpolation interpolation) {
        GlideToAction action = (GlideToAction) Actions.action(GlideToAction.class);
        action.setPosition(x, y);
        action.setDuration(duration);
        action.setInterpolation(interpolation);
        action.setSprite(sprite);
        return action;
    }

    public Action createPlaceAtAction(Sprite sprite, Formula x, Formula y) {
        GlideToAction action = (GlideToAction) Actions.action(GlideToAction.class);
        action.setPosition(x, y);
        action.setDuration(0.0f);
        action.setInterpolation(null);
        action.setSprite(sprite);
        return action;
    }

    public Action createGoToAction(Sprite sprite, Sprite destinationSprite, int spinnerSelection) {
        switch (spinnerSelection) {
            case 80:
                GoToTouchPositionAction touchPositionAction = (GoToTouchPositionAction) Actions.action(GoToTouchPositionAction.class);
                touchPositionAction.setSprite(sprite);
                return touchPositionAction;
            case 81:
                GoToRandomPositionAction randomPositionAction = (GoToRandomPositionAction) Actions.action(GoToRandomPositionAction.class);
                randomPositionAction.setSprite(sprite);
                return randomPositionAction;
            case 82:
                GoToOtherSpritePositionAction otherSpritePositionAction = (GoToOtherSpritePositionAction) Actions.action(GoToOtherSpritePositionAction.class);
                otherSpritePositionAction.setSprite(sprite);
                otherSpritePositionAction.setDestinationSprite(destinationSprite);
                return otherSpritePositionAction;
            default:
                return null;
        }
    }

    public Action createGoNStepsBackAction(Sprite sprite, Formula steps) {
        GoNStepsBackAction action = (GoNStepsBackAction) Actions.action(GoNStepsBackAction.class);
        action.setSprite(sprite);
        action.setSteps(steps);
        return action;
    }

    public Action createHideAction(Sprite sprite) {
        SetVisibleAction action = (SetVisibleAction) Actions.action(SetVisibleAction.class);
        action.setSprite(sprite);
        action.setVisible(false);
        return action;
    }

    public Action createIfOnEdgeBounceAction(Sprite sprite) {
        IfOnEdgeBounceAction action = (IfOnEdgeBounceAction) Actions.action(IfOnEdgeBounceAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createLegoNxtMotorMoveAction(Sprite sprite, Motor motorEnum, Formula speed) {
        LegoNxtMotorMoveAction action = (LegoNxtMotorMoveAction) Actions.action(LegoNxtMotorMoveAction.class);
        action.setMotorEnum(motorEnum);
        action.setSprite(sprite);
        action.setSpeed(speed);
        return action;
    }

    public Action createLegoNxtMotorStopAction(LegoNxtMotorStopBrick.Motor motorEnum) {
        LegoNxtMotorStopAction action = (LegoNxtMotorStopAction) Actions.action(LegoNxtMotorStopAction.class);
        action.setMotorEnum(motorEnum);
        return action;
    }

    public Action createLegoNxtMotorTurnAngleAction(Sprite sprite, LegoNxtMotorTurnAngleBrick.Motor motorEnum, Formula degrees) {
        LegoNxtMotorTurnAngleAction action = (LegoNxtMotorTurnAngleAction) Actions.action(LegoNxtMotorTurnAngleAction.class);
        action.setMotorEnum(motorEnum);
        action.setSprite(sprite);
        action.setDegrees(degrees);
        return action;
    }

    public Action createLegoNxtPlayToneAction(Sprite sprite, Formula hertz, Formula durationInSeconds) {
        LegoNxtPlayToneAction action = (LegoNxtPlayToneAction) Actions.action(LegoNxtPlayToneAction.class);
        action.setHertz(hertz);
        action.setSprite(sprite);
        action.setDurationInSeconds(durationInSeconds);
        return action;
    }

    public Action createLegoEv3SingleMotorMoveAction(Sprite sprite, LegoEv3MotorMoveBrick.Motor motorEnum, Formula speed) {
        LegoEv3MotorMoveAction action = (LegoEv3MotorMoveAction) action(LegoEv3MotorMoveAction.class);
        action.setSprite(sprite);
        action.setMotorEnum(motorEnum);
        action.setSpeed(speed);
        return action;
    }

    public Action createLegoEv3MotorStopAction(LegoEv3MotorStopBrick.Motor motorEnum) {
        LegoEv3MotorStopAction action = (LegoEv3MotorStopAction) action(LegoEv3MotorStopAction.class);
        action.setMotorEnum(motorEnum);
        return action;
    }

    public Action createLegoEv3SetLedAction(LedStatus ledStatusEnum) {
        LegoEv3SetLedAction action = (LegoEv3SetLedAction) action(LegoEv3SetLedAction.class);
        action.setLedStatusEnum(ledStatusEnum);
        return action;
    }

    public Action createLegoEv3PlayToneAction(Sprite sprite, Formula hertz, Formula durationInSeconds, Formula volumeInPercent) {
        LegoEv3PlayToneAction action = (LegoEv3PlayToneAction) action(LegoEv3PlayToneAction.class);
        action.setHertz(hertz);
        action.setSprite(sprite);
        action.setDurationInSeconds(durationInSeconds);
        action.setVolumeInPercent(volumeInPercent);
        return action;
    }

    public Action createLegoEv3MotorTurnAngleAction(Sprite sprite, LegoEv3MotorTurnAngleBrick.Motor motorEnum, Formula degrees) {
        LegoEv3MotorTurnAngleAction action = (LegoEv3MotorTurnAngleAction) action(LegoEv3MotorTurnAngleAction.class);
        action.setMotorEnum(motorEnum);
        action.setSprite(sprite);
        action.setDegrees(degrees);
        return action;
    }

    public Action createPhiroPlayToneActionAction(Sprite sprite, Tone toneEnum, Formula duration) {
        PhiroPlayToneAction action = (PhiroPlayToneAction) action(PhiroPlayToneAction.class);
        action.setSelectedTone(toneEnum);
        action.setSprite(sprite);
        action.setDurationInSeconds(duration);
        return action;
    }

    public Action createPhiroMotorMoveForwardActionAction(Sprite sprite, PhiroMotorMoveForwardBrick.Motor motorEnum, Formula speed) {
        PhiroMotorMoveForwardAction action = (PhiroMotorMoveForwardAction) action(PhiroMotorMoveForwardAction.class);
        action.setMotorEnum(motorEnum);
        action.setSprite(sprite);
        action.setSpeed(speed);
        return action;
    }

    public Action createPhiroMotorMoveBackwardActionAction(Sprite sprite, PhiroMotorMoveBackwardBrick.Motor motorEnum, Formula speed) {
        PhiroMotorMoveBackwardAction action = (PhiroMotorMoveBackwardAction) action(PhiroMotorMoveBackwardAction.class);
        action.setMotorEnum(motorEnum);
        action.setSprite(sprite);
        action.setSpeed(speed);
        return action;
    }

    public Action createPhiroRgbLedEyeActionAction(Sprite sprite, Eye eye, Formula red, Formula green, Formula blue) {
        PhiroRGBLightAction action = (PhiroRGBLightAction) action(PhiroRGBLightAction.class);
        action.setSprite(sprite);
        action.setEyeEnum(eye);
        action.setRed(red);
        action.setGreen(green);
        action.setBlue(blue);
        return action;
    }

    public Action createPhiroSendSelectedSensorAction(Sprite sprite, int sensorNumber, Action ifAction, Action elseAction) {
        PhiroSensorAction action = (PhiroSensorAction) action(PhiroSensorAction.class);
        action.setSprite(sprite);
        action.setSensor(sensorNumber);
        action.setIfAction(ifAction);
        action.setElseAction(elseAction);
        return action;
    }

    public Action createPhiroMotorStopActionAction(PhiroMotorStopBrick.Motor motorEnum) {
        PhiroMotorStopAction action = (PhiroMotorStopAction) action(PhiroMotorStopAction.class);
        action.setMotorEnum(motorEnum);
        return action;
    }

    public Action createMoveNStepsAction(Sprite sprite, Formula steps) {
        MoveNStepsAction action = (MoveNStepsAction) Actions.action(MoveNStepsAction.class);
        action.setSprite(sprite);
        action.setSteps(steps);
        return action;
    }

    public Action createPenDownAction(Sprite sprite) {
        PenDownAction action = (PenDownAction) Actions.action(PenDownAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createPenUpAction(Sprite sprite) {
        PenUpAction action = (PenUpAction) Actions.action(PenUpAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createSetPenSizeAction(Sprite sprite, Formula penSize) {
        SetPenSizeAction action = (SetPenSizeAction) Actions.action(SetPenSizeAction.class);
        action.setSprite(sprite);
        action.setPenSize(penSize);
        return action;
    }

    public Action createSetPenColorAction(Sprite sprite, Formula red, Formula green, Formula blue) {
        SetPenColorAction action = (SetPenColorAction) Actions.action(SetPenColorAction.class);
        action.setSprite(sprite);
        action.setRed(red);
        action.setGreen(green);
        action.setBlue(blue);
        return action;
    }

    public Action createClearBackgroundAction() {
        return Actions.action(ClearBackgroundAction.class);
    }

    public Action createStampAction(Sprite sprite) {
        StampAction action = (StampAction) Actions.action(StampAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createNextLookAction(Sprite sprite) {
        NextLookAction action = (NextLookAction) Actions.action(NextLookAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createPlaySoundAction(Sprite sprite, SoundInfo sound) {
        PlaySoundAction action = (PlaySoundAction) Actions.action(PlaySoundAction.class);
        action.setSprite(sprite);
        action.setSound(sound);
        return action;
    }

    public Action createPointInDirectionAction(Sprite sprite, Formula degrees) {
        PointInDirectionAction action = (PointInDirectionAction) Actions.action(PointInDirectionAction.class);
        action.setSprite(sprite);
        action.setDegreesInUserInterfaceDimensionUnit(degrees);
        return action;
    }

    public Action createPointToAction(Sprite sprite, Sprite pointedSprite) {
        PointToAction action = (PointToAction) Actions.action(PointToAction.class);
        action.setSprite(sprite);
        action.setPointedSprite(pointedSprite);
        return action;
    }

    public Action createCloneAction(Sprite sprite) {
        CloneAction action = (CloneAction) Actions.action(CloneAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createDeleteThisCloneAction(Sprite sprite) {
        DeleteThisCloneAction action = (DeleteThisCloneAction) Actions.action(DeleteThisCloneAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createSetBrightnessAction(Sprite sprite, Formula brightness) {
        SetBrightnessAction action = (SetBrightnessAction) Actions.action(SetBrightnessAction.class);
        action.setSprite(sprite);
        action.setBrightness(brightness);
        return action;
    }

    public Action createSetColorAction(Sprite sprite, Formula color) {
        SetColorAction action = (SetColorAction) Actions.action(SetColorAction.class);
        action.setSprite(sprite);
        action.setColor(color);
        return action;
    }

    public Action createSetTransparencyAction(Sprite sprite, Formula transparency) {
        SetTransparencyAction action = (SetTransparencyAction) Actions.action(SetTransparencyAction.class);
        action.setSprite(sprite);
        action.setTransparency(transparency);
        return action;
    }

    public Action createSetLookAction(Sprite sprite, LookData lookData, int waitMode) {
        return createSetLookEventAction((SetLookAction) createSetLookAction(sprite, lookData), waitMode);
    }

    public Action createSetLookAction(Sprite sprite, LookData lookData) {
        SetLookAction action = (SetLookAction) Actions.action(SetLookAction.class);
        action.setSprite(sprite);
        action.setLookData(lookData);
        return action;
    }

    private Action createSetLookEventAction(SetLookAction action, int waitMode) {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        action.setWaitMode(waitMode);
        action.setReceivingSprites(currentProject.getSpriteListWithClones());
        return action;
    }

    public Action createSetLookByIndexAction(Sprite sprite, Formula formula, int waitMode) {
        return createSetLookEventAction((SetLookAction) createSetLookByIndexAction(sprite, formula), waitMode);
    }

    public Action createSetLookByIndexAction(Sprite sprite, Formula formula) {
        SetLookByIndexAction action = (SetLookByIndexAction) Actions.action(SetLookByIndexAction.class);
        action.setSprite(sprite);
        action.setFormula(formula);
        return action;
    }

    public Action createSetSizeToAction(Sprite sprite, Formula size) {
        SetSizeToAction action = (SetSizeToAction) Actions.action(SetSizeToAction.class);
        action.setSprite(sprite);
        action.setSize(size);
        return action;
    }

    public Action createSetVolumeToAction(Sprite sprite, Formula volume) {
        SetVolumeToAction action = (SetVolumeToAction) Actions.action(SetVolumeToAction.class);
        action.setVolume(volume);
        action.setSprite(sprite);
        return action;
    }

    public Action createSetXAction(Sprite sprite, Formula x) {
        SetXAction action = (SetXAction) Actions.action(SetXAction.class);
        action.setSprite(sprite);
        action.setX(x);
        return action;
    }

    public Action createSetYAction(Sprite sprite, Formula y) {
        SetYAction action = (SetYAction) Actions.action(SetYAction.class);
        action.setSprite(sprite);
        action.setY(y);
        return action;
    }

    public Action createShowAction(Sprite sprite) {
        SetVisibleAction action = (SetVisibleAction) Actions.action(SetVisibleAction.class);
        action.setSprite(sprite);
        action.setVisible(true);
        return action;
    }

    public Action createSpeakAction(Sprite sprite, Formula text) {
        SpeakAction action = (SpeakAction) action(SpeakAction.class);
        action.setSprite(sprite);
        action.setText(text);
        return action;
    }

    public Action createStopAllSoundsAction() {
        return Actions.action(StopAllSoundsAction.class);
    }

    public Action createTurnLeftAction(Sprite sprite, Formula degrees) {
        TurnLeftAction action = (TurnLeftAction) Actions.action(TurnLeftAction.class);
        action.setSprite(sprite);
        action.setDegrees(degrees);
        return action;
    }

    public Action createTurnRightAction(Sprite sprite, Formula degrees) {
        TurnRightAction action = (TurnRightAction) Actions.action(TurnRightAction.class);
        action.setSprite(sprite);
        action.setDegrees(degrees);
        return action;
    }

    public Action createChangeVariableAction(Sprite sprite, Formula variableFormula, UserVariable userVariable) {
        ChangeVariableAction action = (ChangeVariableAction) Actions.action(ChangeVariableAction.class);
        action.setSprite(sprite);
        action.setChangeVariable(variableFormula);
        action.setUserVariable(userVariable);
        return action;
    }

    public Action createSetVariableAction(Sprite sprite, Formula variableFormula, UserVariable userVariable) {
        SetVariableAction action = (SetVariableAction) Actions.action(SetVariableAction.class);
        action.setSprite(sprite);
        action.setChangeVariable(variableFormula);
        action.setUserVariable(userVariable);
        return action;
    }

    public Action createAskAction(Sprite sprite, Formula questionFormula, UserVariable answerVariable) {
        AskAction action = (AskAction) Actions.action(AskAction.class);
        action.setSprite(sprite);
        action.setQuestionFormula(questionFormula);
        action.setAnswerVariable(answerVariable);
        return action;
    }

    public Action createAskSpeechAction(Sprite sprite, Formula questionFormula, UserVariable answerVariable) {
        AskSpeechAction action = (AskSpeechAction) Actions.action(AskSpeechAction.class);
        action.setSprite(sprite);
        action.setQuestionFormula(questionFormula);
        action.setAnswerVariable(answerVariable);
        return action;
    }

    public Action createDeleteItemOfUserListAction(Sprite sprite, Formula userListFormula, UserList userList) {
        DeleteItemOfUserListAction action = (DeleteItemOfUserListAction) action(DeleteItemOfUserListAction.class);
        action.setSprite(sprite);
        action.setFormulaIndexToDelete(userListFormula);
        action.setUserList(userList);
        return action;
    }

    public Action createAddItemToUserListAction(Sprite sprite, Formula userListFormula, UserList userList) {
        AddItemToUserListAction action = (AddItemToUserListAction) action(AddItemToUserListAction.class);
        action.setSprite(sprite);
        action.setFormulaItemToAdd(userListFormula);
        action.setUserList(userList);
        return action;
    }

    public Action createInsertItemIntoUserListAction(Sprite sprite, Formula userListFormulaIndexToInsert, Formula userListFormulaItemToInsert, UserList userList) {
        InsertItemIntoUserListAction action = (InsertItemIntoUserListAction) action(InsertItemIntoUserListAction.class);
        action.setSprite(sprite);
        action.setFormulaIndexToInsert(userListFormulaIndexToInsert);
        action.setFormulaItemToInsert(userListFormulaItemToInsert);
        action.setUserList(userList);
        return action;
    }

    public Action createReplaceItemInUserListAction(Sprite sprite, Formula userListFormulaIndexToReplace, Formula userListFormulaItemToInsert, UserList userList) {
        ReplaceItemInUserListAction action = (ReplaceItemInUserListAction) action(ReplaceItemInUserListAction.class);
        action.setSprite(sprite);
        action.setFormulaIndexToReplace(userListFormulaIndexToReplace);
        action.setFormulaItemToInsert(userListFormulaItemToInsert);
        action.setUserList(userList);
        return action;
    }

    public Action createThinkSayBubbleAction(Sprite sprite, Formula text, int type) {
        ThinkSayBubbleAction action = (ThinkSayBubbleAction) action(ThinkSayBubbleAction.class);
        action.setText(text);
        action.setSprite(sprite);
        action.setType(type);
        return action;
    }

    public Action createThinkSayForBubbleAction(Sprite sprite, Formula text, int type) {
        ThinkSayBubbleAction action = (ThinkSayBubbleAction) action(ThinkSayBubbleAction.class);
        action.setText(text);
        action.setSprite(sprite);
        action.setType(type);
        return action;
    }

    public Action createSceneTransitionAction(String sceneName) {
        SceneTransitionAction action = (SceneTransitionAction) action(SceneTransitionAction.class);
        action.setScene(sceneName);
        return action;
    }

    public Action createSceneStartAction(String sceneName) {
        SceneStartAction action = (SceneStartAction) action(SceneStartAction.class);
        action.setScene(sceneName);
        return action;
    }

    public Action createIfLogicAction(Sprite sprite, Formula condition, Action ifAction, Action elseAction) {
        IfLogicAction action = (IfLogicAction) Actions.action(IfLogicAction.class);
        action.setIfAction(ifAction);
        action.setIfCondition(condition);
        action.setElseAction(elseAction);
        action.setSprite(sprite);
        return action;
    }

    public Action createRepeatAction(Sprite sprite, Formula count, Action repeatedAction) {
        RepeatAction action = (RepeatAction) Actions.action(RepeatAction.class);
        action.setRepeatCount(count);
        action.setAction(repeatedAction);
        action.setSprite(sprite);
        return action;
    }

    public Action createWaitUntilAction(Sprite sprite, Formula condition) {
        WaitUntilAction action = (WaitUntilAction) Actions.action(WaitUntilAction.class);
        action.setSprite(sprite);
        action.setCondition(condition);
        return action;
    }

    public Action createRepeatUntilAction(Sprite sprite, Formula condition, Action repeatedAction) {
        RepeatUntilAction action = (RepeatUntilAction) action(RepeatUntilAction.class);
        action.setRepeatCondition(condition);
        action.setAction(repeatedAction);
        action.setSprite(sprite);
        return action;
    }

    public Action createDelayAction(Sprite sprite, Formula delay) {
        WaitAction action = (WaitAction) Actions.action(WaitAction.class);
        action.setSprite(sprite);
        action.setDelay(delay);
        return action;
    }

    public Action createForeverAction(Sprite sprite, ScriptSequenceAction foreverSequence) {
        RepeatAction action = (RepeatAction) Actions.action(RepeatAction.class);
        action.setIsForeverRepeat(true);
        action.setAction(foreverSequence);
        action.setSprite(sprite);
        return action;
    }

    public Action createUserBrickAction(Action userBrickAction, UserBrick userBrick) {
        UserBrickAction action = (UserBrickAction) action(UserBrickAction.class);
        action.setAction(userBrickAction);
        action.setUserBrick(userBrick);
        return action;
    }

    public static Action eventSequence(Script script) {
        return new ScriptSequenceAction(script);
    }

    public static Action createEventThread(Script script) {
        return new EventThread(script);
    }

    public Action createSetBounceFactorAction(Sprite sprite, Formula bounceFactor) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createTurnRightSpeedAction(Sprite sprite, Formula degreesPerSecond) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createTurnLeftSpeedAction(Sprite sprite, Formula degreesPerSecond) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetVelocityAction(Sprite sprite, Formula velocityX, Formula velocityY) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetPhysicsObjectTypeAction(Sprite sprite, Type type) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetMassAction(Sprite sprite, Formula mass) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetGravityAction(Sprite sprite, Formula gravityX, Formula gravityY) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createSetFrictionAction(Sprite sprite, Formula friction) {
        throw new RuntimeException("No physics action available in non-physics sprite!");
    }

    public Action createDroneTakeOffAndLandAction() {
        return action(DroneTakeoffAndLandAction.class);
    }

    public Action createDroneFlipAction() {
        return action(DroneFlipAction.class);
    }

    public Action createDroneMoveUpAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneMoveUpAction action = (DroneMoveUpAction) action(DroneMoveUpAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneMoveDownAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneMoveDownAction action = (DroneMoveDownAction) action(DroneMoveDownAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneMoveLeftAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneMoveLeftAction action = (DroneMoveLeftAction) action(DroneMoveLeftAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneMoveRightAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneMoveRightAction action = (DroneMoveRightAction) action(DroneMoveRightAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneMoveForwardAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneMoveForwardAction action = (DroneMoveForwardAction) action(DroneMoveForwardAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneMoveBackwardAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneMoveBackwardAction action = (DroneMoveBackwardAction) action(DroneMoveBackwardAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneTurnRightAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneTurnRightAction action = (DroneTurnRightAction) action(DroneTurnRightAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneTurnLeftAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneTurnLeftAction action = (DroneTurnLeftAction) action(DroneTurnLeftAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneTurnLeftMagnetoAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneTurnLeftWithMagnetometerAction action = (DroneTurnLeftWithMagnetometerAction) action(DroneTurnLeftWithMagnetometerAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDroneTurnRightMagnetoAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        DroneTurnRightWithMagnetometerAction action = (DroneTurnRightWithMagnetometerAction) action(DroneTurnRightWithMagnetometerAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createDronePlayLedAnimationAction(ARDRONE_LED_ANIMATION ledAnimationType) {
        DronePlayLedAnimationAction action = (DronePlayLedAnimationAction) action(DronePlayLedAnimationAction.class);
        action.setAnimationType(ledAnimationType);
        return action;
    }

    public Action createDroneSwitchCameraAction() {
        return action(DroneSwitchCameraAction.class);
    }

    public Action createDroneGoEmergencyAction() {
        return action(DroneEmergencyAction.class);
    }

    public Action createJumpingSumoMoveForwardAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        JumpingSumoMoveForwardAction action = (JumpingSumoMoveForwardAction) action(JumpingSumoMoveForwardAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createJumpingSumoMoveBackwardAction(Sprite sprite, Formula seconds, Formula powerInPercent) {
        JumpingSumoMoveBackwardAction action = (JumpingSumoMoveBackwardAction) action(JumpingSumoMoveBackwardAction.class);
        action.setSprite(sprite);
        action.setDelay(seconds);
        action.setPower(powerInPercent);
        return action;
    }

    public Action createJumpingSumoAnimationAction(Animation animationType) {
        JumpingSumoAnimationAction action = (JumpingSumoAnimationAction) action(JumpingSumoAnimationAction.class);
        action.setAnimationType(animationType);
        return action;
    }

    public Action createJumpingSumoNoSoundAction() {
        return action(JumpingSumoNoSoundAction.class);
    }

    public Action createJumpingSumoSoundAction(Sprite sprite, Sounds soundType, Formula volume) {
        JumpingSumoSoundAction action = (JumpingSumoSoundAction) action(JumpingSumoSoundAction.class);
        action.setSoundType(soundType);
        action.setSprite(sprite);
        action.setVolume(volume);
        return action;
    }

    public Action createJumpingSumoJumpLongAction() {
        return action(JumpingSumoJumpLongAction.class);
    }

    public Action createJumpingSumoJumpHighAction() {
        return action(JumpingSumoJumpHighAction.class);
    }

    public Action createJumpingSumoRotateLeftAction(Sprite sprite, Formula degree) {
        JumpingSumoRotateLeftAction action = (JumpingSumoRotateLeftAction) action(JumpingSumoRotateLeftAction.class);
        action.setSprite(sprite);
        action.setDegree(degree);
        return action;
    }

    public Action createJumpingSumoRotateRightAction(Sprite sprite, Formula degree) {
        JumpingSumoRotateRightAction action = (JumpingSumoRotateRightAction) action(JumpingSumoRotateRightAction.class);
        action.setSprite(sprite);
        action.setDegree(degree);
        return action;
    }

    public Action createJumpingSumoTurnAction() {
        return action(JumpingSumoTurnAction.class);
    }

    public Action createJumpingSumoTakingPictureAction() {
        return action(JumpingSumoTakingPictureAction.class);
    }

    public Action createSetTextAction(Sprite sprite, Formula x, Formula y, Formula text) {
        SetTextAction action = (SetTextAction) action(SetTextAction.class);
        action.setPosition(x, y);
        action.setText(text);
        action.setDuration(5.0f);
        action.setSprite(sprite);
        return action;
    }

    public Action createShowVariableAction(Sprite sprite, Formula xPosition, Formula yPosition, UserVariable userVariable) {
        ShowTextAction action = (ShowTextAction) action(ShowTextAction.class);
        action.setPosition(xPosition, yPosition);
        action.setVariableToShow(userVariable);
        action.setSprite(sprite);
        action.setUserBrick(ProjectManager.getInstance().getCurrentUserBrick());
        return action;
    }

    public Action createHideVariableAction(Sprite sprite, UserVariable userVariable) {
        HideTextAction action = (HideTextAction) action(HideTextAction.class);
        action.setVariableToHide(userVariable);
        action.setUserBrick(ProjectManager.getInstance().getCurrentUserBrick());
        action.setSprite(sprite);
        return action;
    }

    public Action createTurnFlashOnAction() {
        FlashAction action = (FlashAction) action(FlashAction.class);
        action.turnFlashOn();
        return action;
    }

    public Action createTurnFlashOffAction() {
        FlashAction action = (FlashAction) action(FlashAction.class);
        action.turnFlashOff();
        return action;
    }

    public Action createVibrateAction(Sprite sprite, Formula duration) {
        VibrateAction action = (VibrateAction) action(VibrateAction.class);
        action.setSprite(sprite);
        action.setDuration(duration);
        return action;
    }

    public Action createUpdateCameraPreviewAction(CameraState state) {
        CameraBrickAction action = (CameraBrickAction) action(CameraBrickAction.class);
        action.setCameraAction(state);
        return action;
    }

    public Action createSetFrontCameraAction() {
        ChooseCameraAction action = (ChooseCameraAction) action(ChooseCameraAction.class);
        action.setFrontCamera();
        return action;
    }

    public Action createSetBackCameraAction() {
        ChooseCameraAction action = (ChooseCameraAction) action(ChooseCameraAction.class);
        action.setBackCamera();
        return action;
    }

    public Action createSendDigitalArduinoValueAction(Sprite sprite, Formula pinNumber, Formula pinValue) {
        ArduinoSendDigitalValueAction action = (ArduinoSendDigitalValueAction) action(ArduinoSendDigitalValueAction.class);
        action.setSprite(sprite);
        action.setPinNumber(pinNumber);
        action.setPinValue(pinValue);
        return action;
    }

    public Action createSendPWMArduinoValueAction(Sprite sprite, Formula pinNumber, Formula pinValue) {
        ArduinoSendPWMValueAction action = (ArduinoSendPWMValueAction) action(ArduinoSendPWMValueAction.class);
        action.setSprite(sprite);
        action.setPinNumber(pinNumber);
        action.setPinValue(pinValue);
        return action;
    }

    public Action createSendDigitalRaspiValueAction(Sprite sprite, Formula pinNumber, Formula pinValue) {
        RaspiSendDigitalValueAction action = (RaspiSendDigitalValueAction) action(RaspiSendDigitalValueAction.class);
        action.setSprite(sprite);
        action.setPinNumber(pinNumber);
        action.setPinValue(pinValue);
        return action;
    }

    public Action createSendRaspiPwmValueAction(Sprite sprite, Formula pinNumber, Formula pwmFrequency, Formula pwmPercentage) {
        RaspiPwmAction action = (RaspiPwmAction) action(RaspiPwmAction.class);
        action.setSprite(sprite);
        action.setPinNumberFormula(pinNumber);
        action.setPwmFrequencyFormula(pwmFrequency);
        action.setPwmPercentageFormula(pwmPercentage);
        return action;
    }

    public Action createRaspiIfLogicActionAction(Sprite sprite, Formula pinNumber, Action ifAction, Action elseAction) {
        RaspiIfLogicAction action = (RaspiIfLogicAction) action(RaspiIfLogicAction.class);
        action.setSprite(sprite);
        action.setPinNumber(pinNumber);
        action.setIfAction(ifAction);
        action.setElseAction(elseAction);
        return action;
    }

    public Action createPreviousLookAction(Sprite sprite) {
        PreviousLookAction action = (PreviousLookAction) action(PreviousLookAction.class);
        action.setSprite(sprite);
        return action;
    }

    public Action createStopScriptAction(int spinnerSelection, Script currentScript) {
        if (spinnerSelection == 0) {
            StopThisScriptAction stopThisScriptAction = (StopThisScriptAction) Actions.action(StopThisScriptAction.class);
            stopThisScriptAction.setCurrentScript(currentScript);
            return stopThisScriptAction;
        } else if (spinnerSelection != 2) {
            return Actions.action(StopAllScriptsAction.class);
        } else {
            StopOtherScriptsAction stopOtherScriptsAction = (StopOtherScriptsAction) Actions.action(StopOtherScriptsAction.class);
            stopOtherScriptsAction.setCurrentScript(currentScript);
            return stopOtherScriptsAction;
        }
    }

    public Action createSetNfcTagAction(Sprite sprite, Formula nfcNdefMessage, int nfcNdefSpinnerSelection) {
        SetNfcTagAction setNfcTagAction = (SetNfcTagAction) Actions.action(SetNfcTagAction.class);
        setNfcTagAction.setSprite(sprite);
        setNfcTagAction.setNfcTagNdefSpinnerSelection(nfcNdefSpinnerSelection);
        setNfcTagAction.setNfcNdefMessage(nfcNdefMessage);
        return setNfcTagAction;
    }
}
