package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import com.facebook.appevents.AppEventsConstants;
import com.parrot.freeflight.drone.DroneProxy.ARDRONE_LED_ANIMATION;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.BrickValues;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.CollisionScript;
import org.catrobat.catroid.content.RaspiInterruptScript;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenConditionScript;
import org.catrobat.catroid.content.WhenGamepadButtonScript;
import org.catrobat.catroid.content.bricks.AddItemToUserListBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendDigitalValueBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendPWMValueBrick;
import org.catrobat.catroid.content.bricks.AskBrick;
import org.catrobat.catroid.content.bricks.AskSpeechBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastBrick;
import org.catrobat.catroid.content.bricks.BroadcastReceiverBrick;
import org.catrobat.catroid.content.bricks.BroadcastWaitBrick;
import org.catrobat.catroid.content.bricks.CameraBrick;
import org.catrobat.catroid.content.bricks.ChangeBrightnessByNBrick;
import org.catrobat.catroid.content.bricks.ChangeColorByNBrick;
import org.catrobat.catroid.content.bricks.ChangeSizeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeTransparencyByNBrick;
import org.catrobat.catroid.content.bricks.ChangeVariableBrick;
import org.catrobat.catroid.content.bricks.ChangeVolumeByNBrick;
import org.catrobat.catroid.content.bricks.ChangeXByNBrick;
import org.catrobat.catroid.content.bricks.ChangeYByNBrick;
import org.catrobat.catroid.content.bricks.ChooseCameraBrick;
import org.catrobat.catroid.content.bricks.ClearBackgroundBrick;
import org.catrobat.catroid.content.bricks.ClearGraphicEffectBrick;
import org.catrobat.catroid.content.bricks.CloneBrick;
import org.catrobat.catroid.content.bricks.ComeToFrontBrick;
import org.catrobat.catroid.content.bricks.DeleteItemOfUserListBrick;
import org.catrobat.catroid.content.bricks.DeleteThisCloneBrick;
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
import org.catrobat.catroid.content.bricks.FlashBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.GoNStepsBackBrick;
import org.catrobat.catroid.content.bricks.GoToBrick;
import org.catrobat.catroid.content.bricks.HideBrick;
import org.catrobat.catroid.content.bricks.HideTextBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.InsertItemIntoUserListBrick;
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
import org.catrobat.catroid.content.bricks.LegoEv3MotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoEv3PlayToneBrick;
import org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick;
import org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick.LedStatus;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoNxtPlayToneBrick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.NoteBrick;
import org.catrobat.catroid.content.bricks.PenDownBrick;
import org.catrobat.catroid.content.bricks.PenUpBrick;
import org.catrobat.catroid.content.bricks.PhiroIfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveForwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveForwardBrick.Motor;
import org.catrobat.catroid.content.bricks.PhiroMotorStopBrick;
import org.catrobat.catroid.content.bricks.PhiroPlayToneBrick;
import org.catrobat.catroid.content.bricks.PhiroPlayToneBrick.Tone;
import org.catrobat.catroid.content.bricks.PhiroRGBLightBrick;
import org.catrobat.catroid.content.bricks.PhiroRGBLightBrick.Eye;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PlaySoundAndWaitBrick;
import org.catrobat.catroid.content.bricks.PlaySoundBrick;
import org.catrobat.catroid.content.bricks.PointInDirectionBrick;
import org.catrobat.catroid.content.bricks.PointToBrick;
import org.catrobat.catroid.content.bricks.PreviousLookBrick;
import org.catrobat.catroid.content.bricks.RaspiIfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.RaspiPwmBrick;
import org.catrobat.catroid.content.bricks.RaspiSendDigitalValueBrick;
import org.catrobat.catroid.content.bricks.RepeatBrick;
import org.catrobat.catroid.content.bricks.RepeatUntilBrick;
import org.catrobat.catroid.content.bricks.ReplaceItemInUserListBrick;
import org.catrobat.catroid.content.bricks.SayBubbleBrick;
import org.catrobat.catroid.content.bricks.SayForBubbleBrick;
import org.catrobat.catroid.content.bricks.SceneStartBrick;
import org.catrobat.catroid.content.bricks.SceneTransitionBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundAndWaitBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundByIndexAndWaitBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundByIndexBrick;
import org.catrobat.catroid.content.bricks.SetBrightnessBrick;
import org.catrobat.catroid.content.bricks.SetColorBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetLookByIndexBrick;
import org.catrobat.catroid.content.bricks.SetNfcTagBrick;
import org.catrobat.catroid.content.bricks.SetPenColorBrick;
import org.catrobat.catroid.content.bricks.SetPenSizeBrick;
import org.catrobat.catroid.content.bricks.SetRotationStyleBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.SetTransparencyBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.SetVolumeToBrick;
import org.catrobat.catroid.content.bricks.SetXBrick;
import org.catrobat.catroid.content.bricks.SetYBrick;
import org.catrobat.catroid.content.bricks.ShowBrick;
import org.catrobat.catroid.content.bricks.ShowTextBrick;
import org.catrobat.catroid.content.bricks.SpeakAndWaitBrick;
import org.catrobat.catroid.content.bricks.SpeakBrick;
import org.catrobat.catroid.content.bricks.StampBrick;
import org.catrobat.catroid.content.bricks.StopAllSoundsBrick;
import org.catrobat.catroid.content.bricks.StopScriptBrick;
import org.catrobat.catroid.content.bricks.ThinkBubbleBrick;
import org.catrobat.catroid.content.bricks.ThinkForBubbleBrick;
import org.catrobat.catroid.content.bricks.TurnLeftBrick;
import org.catrobat.catroid.content.bricks.TurnRightBrick;
import org.catrobat.catroid.content.bricks.UserBrick;
import org.catrobat.catroid.content.bricks.VibrationBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.content.bricks.WaitUntilBrick;
import org.catrobat.catroid.content.bricks.WhenBackgroundChangesBrick;
import org.catrobat.catroid.content.bricks.WhenBrick;
import org.catrobat.catroid.content.bricks.WhenClonedBrick;
import org.catrobat.catroid.content.bricks.WhenConditionBrick;
import org.catrobat.catroid.content.bricks.WhenGamepadButtonBrick;
import org.catrobat.catroid.content.bricks.WhenNfcBrick;
import org.catrobat.catroid.content.bricks.WhenRaspiPinChangedBrick;
import org.catrobat.catroid.content.bricks.WhenStartedBrick;
import org.catrobat.catroid.content.bricks.WhenTouchDownBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.FormulaElement.ElementType;
import org.catrobat.catroid.formulaeditor.Operators;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;
import org.catrobat.catroid.physics.content.bricks.SetBounceBrick;
import org.catrobat.catroid.physics.content.bricks.SetFrictionBrick;
import org.catrobat.catroid.physics.content.bricks.SetGravityBrick;
import org.catrobat.catroid.physics.content.bricks.SetMassBrick;
import org.catrobat.catroid.physics.content.bricks.SetPhysicsObjectTypeBrick;
import org.catrobat.catroid.physics.content.bricks.SetVelocityBrick;
import org.catrobat.catroid.physics.content.bricks.TurnLeftSpeedBrick;
import org.catrobat.catroid.physics.content.bricks.TurnRightSpeedBrick;
import org.catrobat.catroid.ui.UserBrickSpriteActivity;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;

public class CategoryBricksFactory {
    public List<Brick> getBricks(String category, Sprite sprite, Context context) {
        boolean isUserScriptMode = context instanceof UserBrickSpriteActivity;
        List<Brick> tempList = new LinkedList();
        List<Brick> toReturn = new ArrayList();
        if (category.equals(context.getString(R.string.category_event))) {
            tempList = setupEventCategoryList(context);
        } else if (category.equals(context.getString(R.string.category_control))) {
            tempList = setupControlCategoryList(context);
        } else if (category.equals(context.getString(R.string.category_motion))) {
            tempList = setupMotionCategoryList(sprite, context);
        } else if (category.equals(context.getString(R.string.category_sound))) {
            tempList = setupSoundCategoryList(context);
        } else if (category.equals(context.getString(R.string.category_looks))) {
            tempList = setupLooksCategoryList(context, sprite.getName().equals(context.getString(R.string.background)));
        } else if (category.equals(context.getString(R.string.category_pen))) {
            tempList = setupPenCategoryList(sprite);
        } else if (category.equals(context.getString(R.string.category_user_bricks))) {
            tempList = setupUserBricksCategoryList();
        } else if (category.equals(context.getString(R.string.category_data))) {
            tempList = setupDataCategoryList(context);
        } else if (category.equals(context.getString(R.string.category_lego_nxt))) {
            tempList = setupLegoNxtCategoryList();
        } else if (category.equals(context.getString(R.string.category_lego_ev3))) {
            tempList = setupLegoEv3CategoryList();
        } else if (category.equals(context.getString(R.string.category_arduino))) {
            tempList = setupArduinoCategoryList();
        } else if (category.equals(context.getString(R.string.category_drone))) {
            tempList = setupDroneCategoryList();
        } else if (category.equals(context.getString(R.string.category_jumping_sumo))) {
            tempList = setupJumpingSumoCategoryList();
        } else if (category.equals(context.getString(R.string.category_phiro))) {
            tempList = setupPhiroProCategoryList();
        } else if (category.equals(context.getString(R.string.category_cast))) {
            tempList = setupChromecastCategoryList(context);
        } else if (category.equals(context.getString(R.string.category_raspi))) {
            tempList = setupRaspiCategoryList();
        } else if (category.equals(context.getString(R.string.category_embroidery))) {
            tempList = setupEmbroideryCategoryList();
        }
        for (Brick brick : tempList) {
            if (!isUserScriptMode || !(brick instanceof ScriptBrick)) {
                toReturn.add(brick);
            }
        }
        return toReturn;
    }

    protected List<Brick> setupEventCategoryList(Context context) {
        FormulaElement defaultIf = new FormulaElement(ElementType.OPERATOR, Operators.SMALLER_THAN.toString(), null);
        defaultIf.setLeftChild(new FormulaElement(ElementType.NUMBER, AppEventsConstants.EVENT_PARAM_VALUE_YES, null));
        defaultIf.setRightChild(new FormulaElement(ElementType.NUMBER, "2", null));
        List<Brick> eventBrickList = new ArrayList();
        eventBrickList.add(new WhenStartedBrick());
        eventBrickList.add(new WhenBrick());
        eventBrickList.add(new WhenTouchDownBrick());
        List<String> broadcastMessages = ProjectManager.getInstance().getCurrentProject().getBroadcastMessageContainer().getBroadcastMessages();
        String broadcastMessage = context.getString(R.string.brick_broadcast_default_value);
        if (broadcastMessages.size() > 0) {
            broadcastMessage = (String) broadcastMessages.get(0);
        }
        eventBrickList.add(new BroadcastReceiverBrick(new BroadcastScript(broadcastMessage)));
        eventBrickList.add(new BroadcastBrick(broadcastMessage));
        eventBrickList.add(new BroadcastWaitBrick(broadcastMessage));
        eventBrickList.add(new WhenConditionBrick(new WhenConditionScript(new Formula(defaultIf))));
        eventBrickList.add(new CollisionReceiverBrick(new CollisionScript(null)));
        eventBrickList.add(new WhenBackgroundChangesBrick());
        eventBrickList.add(new WhenClonedBrick());
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            eventBrickList.add(new WhenNfcBrick());
        }
        return eventBrickList;
    }

    protected List<Brick> setupControlCategoryList(Context context) {
        FormulaElement defaultIf = new FormulaElement(ElementType.OPERATOR, Operators.SMALLER_THAN.toString(), null);
        defaultIf.setLeftChild(new FormulaElement(ElementType.NUMBER, AppEventsConstants.EVENT_PARAM_VALUE_YES, null));
        defaultIf.setRightChild(new FormulaElement(ElementType.NUMBER, "2", null));
        List<Brick> controlBrickList = new ArrayList();
        controlBrickList.add(new WaitBrick(1000));
        controlBrickList.add(new NoteBrick(context.getString(R.string.brick_note_default_value)));
        controlBrickList.add(new ForeverBrick());
        controlBrickList.add(new IfLogicBeginBrick(new Formula(defaultIf)));
        controlBrickList.add(new IfThenLogicBeginBrick(new Formula(defaultIf)));
        controlBrickList.add(new WaitUntilBrick(new Formula(defaultIf)));
        controlBrickList.add(new RepeatBrick(10));
        controlBrickList.add(new RepeatUntilBrick(new Formula(defaultIf)));
        controlBrickList.add(new SceneTransitionBrick(null));
        controlBrickList.add(new SceneStartBrick(null));
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            controlBrickList.add(new PhiroIfLogicBeginBrick());
        }
        controlBrickList.add(new StopScriptBrick(0));
        controlBrickList.add(new CloneBrick());
        controlBrickList.add(new DeleteThisCloneBrick());
        controlBrickList.add(new WhenClonedBrick());
        if (SettingsFragment.isNfcSharedPreferenceEnabled(context)) {
            controlBrickList.add(new SetNfcTagBrick(context.getString(R.string.brick_set_nfc_tag_default_value)));
        }
        return controlBrickList;
    }

    private List<Brick> setupUserBricksCategoryList() {
        List<UserBrick> userBrickList = ProjectManager.getInstance().getCurrentSprite().getUserBrickList();
        ArrayList<Brick> newList = new ArrayList();
        if (userBrickList != null) {
            for (UserBrick brick : userBrickList) {
                newList.add(brick);
            }
        }
        return newList;
    }

    private List<Brick> setupChromecastCategoryList(Context context) {
        List<Brick> chromecastBrickList = new ArrayList();
        chromecastBrickList.add(new WhenGamepadButtonBrick(new WhenGamepadButtonScript(context.getString(R.string.cast_gamepad_A))));
        return chromecastBrickList;
    }

    protected List<Brick> setupMotionCategoryList(Sprite sprite, Context context) {
        List<Brick> motionBrickList = new ArrayList();
        motionBrickList.add(new PlaceAtBrick(100, 200));
        motionBrickList.add(new SetXBrick(new Formula(Integer.valueOf(100))));
        motionBrickList.add(new SetYBrick(200));
        motionBrickList.add(new ChangeXByNBrick(10));
        motionBrickList.add(new ChangeYByNBrick(10));
        motionBrickList.add(new GoToBrick(null));
        if (!isBackground(sprite)) {
            motionBrickList.add(new IfOnEdgeBounceBrick());
        }
        motionBrickList.add(new MoveNStepsBrick(10.0d));
        motionBrickList.add(new TurnLeftBrick(15.0d));
        motionBrickList.add(new TurnRightBrick(15.0d));
        motionBrickList.add(new PointInDirectionBrick(90.0d));
        motionBrickList.add(new PointToBrick(null));
        motionBrickList.add(new SetRotationStyleBrick());
        motionBrickList.add(new GlideToBrick(100, 200, 1000));
        if (!isBackground(sprite)) {
            motionBrickList.add(new GoNStepsBackBrick(1));
            motionBrickList.add(new ComeToFrontBrick());
        }
        motionBrickList.add(new VibrationBrick(1.0d));
        motionBrickList.add(new SetPhysicsObjectTypeBrick(BrickValues.PHYSIC_TYPE));
        motionBrickList.add(new SetVelocityBrick(BrickValues.PHYSIC_VELOCITY));
        motionBrickList.add(new TurnLeftSpeedBrick(15.0d));
        motionBrickList.add(new TurnRightSpeedBrick(15.0d));
        motionBrickList.add(new SetGravityBrick(BrickValues.PHYSIC_GRAVITY));
        motionBrickList.add(new SetMassBrick(1.0d));
        motionBrickList.add(new SetBounceBrick(80.0d));
        motionBrickList.add(new SetFrictionBrick(20.0d));
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            motionBrickList.add(new PhiroMotorMoveForwardBrick(Motor.MOTOR_LEFT, 100));
            motionBrickList.add(new PhiroMotorMoveBackwardBrick(PhiroMotorMoveBackwardBrick.Motor.MOTOR_LEFT, 100));
            motionBrickList.add(new PhiroMotorStopBrick(PhiroMotorStopBrick.Motor.MOTOR_BOTH));
        }
        return motionBrickList;
    }

    protected List<Brick> setupSoundCategoryList(Context context) {
        List<Brick> soundBrickList = new ArrayList();
        soundBrickList.add(new PlaySoundBrick());
        soundBrickList.add(new PlaySoundAndWaitBrick());
        soundBrickList.add(new StopAllSoundsBrick());
        soundBrickList.add(new SetVolumeToBrick(60.0d));
        soundBrickList.add(new ChangeVolumeByNBrick(new Formula(new FormulaElement(ElementType.OPERATOR, Operators.MINUS.name(), null, null, new FormulaElement(ElementType.NUMBER, String.valueOf(Math.abs(-10.0d)), null)))));
        soundBrickList.add(new SpeakBrick(context.getString(R.string.brick_speak_default_value)));
        soundBrickList.add(new SpeakAndWaitBrick(context.getString(R.string.brick_speak_default_value)));
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            soundBrickList.add(new PhiroPlayToneBrick(Tone.DO, 1));
        }
        soundBrickList.add(new AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)));
        return soundBrickList;
    }

    protected List<Brick> setupLooksCategoryList(Context context, boolean isBackgroundSprite) {
        List<Brick> looksBrickList = new ArrayList();
        if (!isBackgroundSprite) {
            looksBrickList.add(new SetLookBrick());
            looksBrickList.add(new SetLookByIndexBrick(1));
        }
        looksBrickList.add(new NextLookBrick());
        looksBrickList.add(new PreviousLookBrick());
        looksBrickList.add(new SetSizeToBrick(60.0d));
        looksBrickList.add(new ChangeSizeByNBrick(10.0d));
        looksBrickList.add(new HideBrick());
        looksBrickList.add(new ShowBrick());
        looksBrickList.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
        if (!isBackgroundSprite) {
            looksBrickList.add(new SayBubbleBrick(context.getString(R.string.brick_say_bubble_default_value)));
            looksBrickList.add(new SayForBubbleBrick(context.getString(R.string.brick_say_bubble_default_value), 1.0f));
            looksBrickList.add(new ThinkBubbleBrick(context.getString(R.string.brick_think_bubble_default_value)));
            looksBrickList.add(new ThinkForBubbleBrick(context.getString(R.string.brick_think_bubble_default_value), 1.0f));
        }
        looksBrickList.add(new SetTransparencyBrick(50.0d));
        looksBrickList.add(new ChangeTransparencyByNBrick(25.0d));
        looksBrickList.add(new SetBrightnessBrick(50.0d));
        looksBrickList.add(new ChangeBrightnessByNBrick(25.0d));
        looksBrickList.add(new SetColorBrick((double) BrickValues.SET_COLOR_TO));
        looksBrickList.add(new ChangeColorByNBrick(25.0d));
        looksBrickList.add(new ClearGraphicEffectBrick());
        looksBrickList.add(new WhenBackgroundChangesBrick());
        looksBrickList.add(new SetBackgroundBrick());
        looksBrickList.add(new SetBackgroundByIndexBrick(1));
        looksBrickList.add(new SetBackgroundAndWaitBrick());
        looksBrickList.add(new SetBackgroundByIndexAndWaitBrick(1));
        if (!ProjectManager.getInstance().getCurrentProject().isCastProject()) {
            looksBrickList.add(new CameraBrick());
            looksBrickList.add(new ChooseCameraBrick());
            looksBrickList.add(new FlashBrick());
        }
        if (SettingsFragment.isPhiroSharedPreferenceEnabled(context)) {
            looksBrickList.add(new PhiroRGBLightBrick(Eye.BOTH, 0, 255, 255));
        }
        return looksBrickList;
    }

    private List<Brick> setupPenCategoryList(Sprite sprite) {
        List<Brick> penBrickList = new ArrayList();
        if (!isBackground(sprite)) {
            penBrickList.add(new PenDownBrick());
            penBrickList.add(new PenUpBrick());
            penBrickList.add(new SetPenSizeBrick(3.15d));
            penBrickList.add(new SetPenColorBrick(0, 0, 255));
            penBrickList.add(new StampBrick());
        }
        penBrickList.add(new ClearBackgroundBrick());
        return penBrickList;
    }

    protected List<Brick> setupDataCategoryList(Context context) {
        List<Brick> dataBrickList = new ArrayList();
        dataBrickList.add(new SetVariableBrick(1.0d));
        dataBrickList.add(new ChangeVariableBrick(1.0d));
        dataBrickList.add(new ShowTextBrick(100, 200));
        dataBrickList.add(new HideTextBrick());
        dataBrickList.add(new AddItemToUserListBrick(1.0d));
        dataBrickList.add(new DeleteItemOfUserListBrick(Integer.valueOf(1)));
        dataBrickList.add(new InsertItemIntoUserListBrick(1.0d, Integer.valueOf(1)));
        dataBrickList.add(new ReplaceItemInUserListBrick(1.0d, Integer.valueOf(1)));
        dataBrickList.add(new AskBrick(context.getString(R.string.brick_ask_default_question)));
        dataBrickList.add(new AskSpeechBrick(context.getString(R.string.brick_ask_speech_default_question)));
        return dataBrickList;
    }

    private List<Brick> setupLegoNxtCategoryList() {
        List<Brick> legoNXTBrickList = new ArrayList();
        legoNXTBrickList.add(new LegoNxtMotorTurnAngleBrick(LegoNxtMotorTurnAngleBrick.Motor.MOTOR_A, (int) BrickValues.LEGO_ANGLE));
        legoNXTBrickList.add(new LegoNxtMotorStopBrick(LegoNxtMotorStopBrick.Motor.MOTOR_A));
        legoNXTBrickList.add(new LegoNxtMotorMoveBrick(LegoNxtMotorMoveBrick.Motor.MOTOR_A, 100));
        legoNXTBrickList.add(new LegoNxtPlayToneBrick(2.0d, 1.0d));
        return legoNXTBrickList;
    }

    private List<Brick> setupLegoEv3CategoryList() {
        List<Brick> legoEV3BrickList = new ArrayList();
        legoEV3BrickList.add(new LegoEv3MotorTurnAngleBrick(LegoEv3MotorTurnAngleBrick.Motor.MOTOR_A, (int) BrickValues.LEGO_ANGLE));
        legoEV3BrickList.add(new LegoEv3MotorMoveBrick(LegoEv3MotorMoveBrick.Motor.MOTOR_A, 100));
        legoEV3BrickList.add(new LegoEv3MotorStopBrick(LegoEv3MotorStopBrick.Motor.MOTOR_A));
        legoEV3BrickList.add(new LegoEv3PlayToneBrick(2.0d, 1.0d, 100.0d));
        legoEV3BrickList.add(new LegoEv3SetLedBrick(LedStatus.LED_GREEN));
        return legoEV3BrickList;
    }

    private List<Brick> setupDroneCategoryList() {
        List<Brick> droneBrickList = new ArrayList();
        droneBrickList.add(new DroneTakeOffLandBrick());
        droneBrickList.add(new DroneEmergencyBrick());
        droneBrickList.add(new DroneMoveUpBrick(1000, 20));
        droneBrickList.add(new DroneMoveDownBrick(1000, 20));
        droneBrickList.add(new DroneMoveLeftBrick(1000, 20));
        droneBrickList.add(new DroneMoveRightBrick(1000, 20));
        droneBrickList.add(new DroneMoveForwardBrick(1000, 20));
        droneBrickList.add(new DroneMoveBackwardBrick(1000, 20));
        droneBrickList.add(new DroneTurnLeftBrick(1000, 20));
        droneBrickList.add(new DroneTurnRightBrick(1000, 20));
        droneBrickList.add(new DroneFlipBrick());
        droneBrickList.add(new DronePlayLedAnimationBrick(ARDRONE_LED_ANIMATION.ARDRONE_LED_ANIMATION_BLINK_GREEN_RED));
        droneBrickList.add(new DroneSwitchCameraBrick());
        return droneBrickList;
    }

    private List<Brick> setupJumpingSumoCategoryList() {
        List<Brick> jumpingSumoBrickList = new ArrayList();
        jumpingSumoBrickList.add(new JumpingSumoMoveForwardBrick(1000, 80));
        jumpingSumoBrickList.add(new JumpingSumoMoveBackwardBrick(1000, 80));
        jumpingSumoBrickList.add(new JumpingSumoAnimationsBrick(Animation.SPIN));
        jumpingSumoBrickList.add(new JumpingSumoSoundBrick(Sounds.DEFAULT, 50));
        jumpingSumoBrickList.add(new JumpingSumoNoSoundBrick());
        jumpingSumoBrickList.add(new JumpingSumoJumpLongBrick());
        jumpingSumoBrickList.add(new JumpingSumoJumpHighBrick());
        jumpingSumoBrickList.add(new JumpingSumoRotateLeftBrick(90.0d));
        jumpingSumoBrickList.add(new JumpingSumoRotateRightBrick(90.0d));
        jumpingSumoBrickList.add(new JumpingSumoTurnBrick());
        jumpingSumoBrickList.add(new JumpingSumoTakingPictureBrick());
        return jumpingSumoBrickList;
    }

    private List<Brick> setupPhiroProCategoryList() {
        List<Brick> phiroProBrickList = new ArrayList();
        phiroProBrickList.add(new PhiroMotorMoveForwardBrick(Motor.MOTOR_LEFT, 100));
        phiroProBrickList.add(new PhiroMotorMoveBackwardBrick(PhiroMotorMoveBackwardBrick.Motor.MOTOR_LEFT, 100));
        phiroProBrickList.add(new PhiroMotorStopBrick(PhiroMotorStopBrick.Motor.MOTOR_BOTH));
        phiroProBrickList.add(new PhiroPlayToneBrick(Tone.DO, 1));
        phiroProBrickList.add(new PhiroRGBLightBrick(Eye.BOTH, 0, 255, 255));
        phiroProBrickList.add(new PhiroIfLogicBeginBrick());
        phiroProBrickList.add(new SetVariableBrick(Sensors.PHIRO_FRONT_LEFT));
        phiroProBrickList.add(new SetVariableBrick(Sensors.PHIRO_FRONT_RIGHT));
        phiroProBrickList.add(new SetVariableBrick(Sensors.PHIRO_SIDE_LEFT));
        phiroProBrickList.add(new SetVariableBrick(Sensors.PHIRO_SIDE_RIGHT));
        phiroProBrickList.add(new SetVariableBrick(Sensors.PHIRO_BOTTOM_LEFT));
        phiroProBrickList.add(new SetVariableBrick(Sensors.PHIRO_BOTTOM_RIGHT));
        return phiroProBrickList;
    }

    private List<Brick> setupArduinoCategoryList() {
        List<Brick> arduinoBrickList = new ArrayList();
        arduinoBrickList.add(new ArduinoSendDigitalValueBrick(13, 1));
        arduinoBrickList.add(new ArduinoSendPWMValueBrick(3, 255));
        return arduinoBrickList;
    }

    private List<Brick> setupRaspiCategoryList() {
        RaspiInterruptScript defaultScript = new RaspiInterruptScript(Integer.toString(3), BrickValues.RASPI_EVENTS[0]);
        List<Brick> raspiBrickList = new ArrayList();
        raspiBrickList.add(new WhenRaspiPinChangedBrick(defaultScript));
        raspiBrickList.add(new RaspiIfLogicBeginBrick(3));
        raspiBrickList.add(new RaspiSendDigitalValueBrick(3, 1));
        raspiBrickList.add(new RaspiPwmBrick(3, 100.0d, 50.0d));
        return raspiBrickList;
    }

    private List<Brick> setupEmbroideryCategoryList() {
        return new ArrayList();
    }

    protected boolean isBackground(Sprite sprite) {
        return ProjectManager.getInstance().getCurrentlyEditedScene().getSpriteList().indexOf(sprite) == 0;
    }

    public String getBrickCategory(Brick brick, Sprite sprite, Context context) {
        Brick brick2 = brick;
        Sprite sprite2 = sprite;
        Context context2 = context;
        List<Brick> categoryBricks = setupControlCategoryList(context2);
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        Locale savedLocale = config.locale;
        config.locale = Locale.ENGLISH;
        res.updateConfiguration(config, null);
        String category = "No match";
        for (Brick categoryBrick : categoryBricks) {
            if (brick.getClass().equals(categoryBrick.getClass())) {
                category = res.getString(R.string.category_control);
            }
        }
        for (Brick categoryBrick2 : setupEventCategoryList(context2)) {
            if (brick.getClass().equals(categoryBrick2.getClass())) {
                category = res.getString(R.string.category_event);
            }
        }
        for (Brick categoryBrick22 : setupMotionCategoryList(sprite2, context2)) {
            if (brick.getClass().equals(categoryBrick22.getClass())) {
                category = res.getString(R.string.category_motion);
            }
        }
        for (Brick categoryBrick222 : setupSoundCategoryList(context2)) {
            if (brick.getClass().equals(categoryBrick222.getClass())) {
                category = res.getString(R.string.category_sound);
            }
        }
        for (Brick categoryBrick2222 : setupLooksCategoryList(context2, sprite.getName().equals(context2.getString(R.string.background)))) {
            if (brick.getClass().equals(categoryBrick2222.getClass())) {
                category = res.getString(R.string.category_looks);
            }
        }
        for (Brick categoryBrick22222 : setupPenCategoryList(sprite2)) {
            if (brick.getClass().equals(categoryBrick22222.getClass())) {
                category = res.getString(R.string.category_pen);
            }
        }
        for (Brick categoryBrick222222 : setupUserBricksCategoryList()) {
            if (brick.getClass().equals(categoryBrick222222.getClass())) {
                category = res.getString(R.string.category_user_bricks);
            }
        }
        for (Brick categoryBrick2222222 : setupDataCategoryList(context2)) {
            if (brick.getClass().equals(categoryBrick2222222.getClass())) {
                category = res.getString(R.string.category_data);
            }
        }
        for (Brick categoryBrick22222222 : setupLegoNxtCategoryList()) {
            if (brick.getClass().equals(categoryBrick22222222.getClass())) {
                category = res.getString(R.string.category_lego_nxt);
            }
        }
        for (Brick categoryBrick222222222 : setupLegoEv3CategoryList()) {
            if (brick.getClass().equals(categoryBrick222222222.getClass())) {
                category = res.getString(R.string.category_lego_ev3);
            }
        }
        for (Brick categoryBrick2222222222 : setupArduinoCategoryList()) {
            if (brick.getClass().equals(categoryBrick2222222222.getClass())) {
                category = res.getString(R.string.category_arduino);
            }
        }
        for (Brick categoryBrick22222222222 : setupDroneCategoryList()) {
            if (brick.getClass().equals(categoryBrick22222222222.getClass())) {
                category = res.getString(R.string.category_drone);
            }
        }
        for (Brick categoryBrick222222222222 : setupJumpingSumoCategoryList()) {
            if (brick.getClass().equals(categoryBrick222222222222.getClass())) {
                category = res.getString(R.string.category_jumping_sumo);
            }
        }
        for (Brick categoryBrick2222222222222 : setupPhiroProCategoryList()) {
            if (brick.getClass().equals(categoryBrick2222222222222.getClass())) {
                category = res.getString(R.string.category_phiro);
            }
        }
        for (Brick categoryBrick22222222222222 : setupRaspiCategoryList()) {
            if (brick.getClass().equals(categoryBrick22222222222222.getClass())) {
                category = res.getString(R.string.category_raspi);
            }
        }
        for (Brick categoryBrick222222222222222 : setupChromecastCategoryList(context2)) {
            if (brick.getClass().equals(categoryBrick222222222222222.getClass())) {
                category = res.getString(R.string.category_cast);
            }
        }
        for (Brick categoryBrick2222222222222222 : setupEmbroideryCategoryList()) {
            if (brick.getClass().equals(categoryBrick2222222222222222.getClass())) {
                category = res.getString(R.string.category_embroidery);
            }
        }
        if (brick2 instanceof AskBrick) {
            category = res.getString(R.string.category_looks);
        } else if (brick2 instanceof AskSpeechBrick) {
            category = res.getString(R.string.category_sound);
        } else if (brick2 instanceof WhenClonedBrick) {
            category = res.getString(R.string.category_control);
        } else if (brick2 instanceof WhenBackgroundChangesBrick) {
            category = res.getString(R.string.category_event);
        } else if (brick2 instanceof SetVariableBrick) {
            category = res.getString(R.string.category_data);
        }
        config.locale = savedLocale;
        res.updateConfiguration(config, null);
        return category;
    }
}
