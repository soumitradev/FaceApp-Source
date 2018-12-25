package org.catrobat.catroid.io;

import android.content.Context;
import android.util.Log;
import com.facebook.internal.NativeProtocol;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.parrot.freeflight.utils.FileUtils;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.DroneVideoLookData;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.CollisionScript;
import org.catrobat.catroid.content.GroupItemSprite;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.LegoNXTSetting;
import org.catrobat.catroid.content.LegoNXTSetting.NXTPort;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.RaspiInterruptScript;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Setting;
import org.catrobat.catroid.content.SingleSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.SupportProject;
import org.catrobat.catroid.content.WhenBackgroundChangesScript;
import org.catrobat.catroid.content.WhenClonedScript;
import org.catrobat.catroid.content.WhenConditionScript;
import org.catrobat.catroid.content.WhenGamepadButtonScript;
import org.catrobat.catroid.content.WhenNfcScript;
import org.catrobat.catroid.content.WhenScript;
import org.catrobat.catroid.content.WhenTouchDownScript;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.content.bricks.AddItemToUserListBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendDigitalValueBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendPWMValueBrick;
import org.catrobat.catroid.content.bricks.AskBrick;
import org.catrobat.catroid.content.bricks.AskSpeechBrick;
import org.catrobat.catroid.content.bricks.Brick.ResourcesSet;
import org.catrobat.catroid.content.bricks.BrickBaseType;
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
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.GlideToBrick;
import org.catrobat.catroid.content.bricks.GoNStepsBackBrick;
import org.catrobat.catroid.content.bricks.GoToBrick;
import org.catrobat.catroid.content.bricks.HideBrick;
import org.catrobat.catroid.content.bricks.HideTextBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfLogicElseBrick;
import org.catrobat.catroid.content.bricks.IfLogicEndBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicEndBrick;
import org.catrobat.catroid.content.bricks.InsertItemIntoUserListBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoEv3MotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoEv3PlayToneBrick;
import org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoNxtPlayToneBrick;
import org.catrobat.catroid.content.bricks.LoopBeginBrick;
import org.catrobat.catroid.content.bricks.LoopEndBrick;
import org.catrobat.catroid.content.bricks.LoopEndlessBrick;
import org.catrobat.catroid.content.bricks.MoveNStepsBrick;
import org.catrobat.catroid.content.bricks.NextLookBrick;
import org.catrobat.catroid.content.bricks.NoteBrick;
import org.catrobat.catroid.content.bricks.PenDownBrick;
import org.catrobat.catroid.content.bricks.PenUpBrick;
import org.catrobat.catroid.content.bricks.PhiroIfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveBackwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorMoveForwardBrick;
import org.catrobat.catroid.content.bricks.PhiroMotorStopBrick;
import org.catrobat.catroid.content.bricks.PhiroPlayToneBrick;
import org.catrobat.catroid.content.bricks.PhiroRGBLightBrick;
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
import org.catrobat.catroid.content.bricks.UserBrickParameter;
import org.catrobat.catroid.content.bricks.UserListBrick;
import org.catrobat.catroid.content.bricks.UserScriptDefinitionBrick;
import org.catrobat.catroid.content.bricks.UserScriptDefinitionBrickElement;
import org.catrobat.catroid.content.bricks.UserVariableBrick;
import org.catrobat.catroid.content.bricks.VibrationBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.content.bricks.WaitUntilBrick;
import org.catrobat.catroid.content.bricks.WhenBackgroundChangesBrick;
import org.catrobat.catroid.content.bricks.WhenBrick;
import org.catrobat.catroid.content.bricks.WhenClonedBrick;
import org.catrobat.catroid.content.bricks.WhenConditionBrick;
import org.catrobat.catroid.content.bricks.WhenGamepadButtonBrick;
import org.catrobat.catroid.content.bricks.WhenNfcBrick;
import org.catrobat.catroid.content.bricks.WhenStartedBrick;
import org.catrobat.catroid.exceptions.LoadingProjectException;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.formulaeditor.datacontainer.DataContainer;
import org.catrobat.catroid.formulaeditor.datacontainer.SupportDataContainer;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;
import org.catrobat.catroid.physics.content.bricks.SetBounceBrick;
import org.catrobat.catroid.physics.content.bricks.SetFrictionBrick;
import org.catrobat.catroid.physics.content.bricks.SetGravityBrick;
import org.catrobat.catroid.physics.content.bricks.SetMassBrick;
import org.catrobat.catroid.physics.content.bricks.SetPhysicsObjectTypeBrick;
import org.catrobat.catroid.physics.content.bricks.SetVelocityBrick;
import org.catrobat.catroid.physics.content.bricks.TurnLeftSpeedBrick;
import org.catrobat.catroid.physics.content.bricks.TurnRightSpeedBrick;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.PathBuilder;
import org.catrobat.catroid.utils.StringFinder;

public final class XstreamSerializer {
    private static final String TAG = XstreamSerializer.class.getSimpleName();
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
    private static XstreamSerializer instance;
    private Lock loadSaveLock = new ReentrantLock();
    private BackwardCompatibleCatrobatLanguageXStream xstream;

    private XstreamSerializer() {
        prepareXstream(Project.class, DataContainer.class);
    }

    public static XstreamSerializer getInstance() {
        if (instance == null) {
            instance = new XstreamSerializer();
        }
        return instance;
    }

    private void prepareXstream(Class projectClass, Class dataContainerClass) {
        this.xstream = new BackwardCompatibleCatrobatLanguageXStream(new PureJavaReflectionProvider(new FieldDictionary(new CatroidFieldKeySorter())));
        this.xstream.processAnnotations(projectClass);
        this.xstream.processAnnotations(dataContainerClass);
        this.xstream.processAnnotations(Scene.class);
        this.xstream.processAnnotations(Sprite.class);
        this.xstream.processAnnotations(XmlHeader.class);
        this.xstream.processAnnotations(Setting.class);
        this.xstream.processAnnotations(UserVariableBrick.class);
        this.xstream.processAnnotations(UserListBrick.class);
        this.xstream.registerConverter(new XStreamConcurrentFormulaHashMapConverter());
        this.xstream.registerConverter(new XStreamUserVariableConverter());
        this.xstream.registerConverter(new XStreamBrickConverter(this.xstream.getMapper(), this.xstream.getReflectionProvider()));
        this.xstream.registerConverter(new XStreamScriptConverter(this.xstream.getMapper(), this.xstream.getReflectionProvider()));
        this.xstream.registerConverter(new XStreamSpriteConverter(this.xstream.getMapper(), this.xstream.getReflectionProvider()));
        this.xstream.registerConverter(new XStreamSettingConverter(this.xstream.getMapper(), this.xstream.getReflectionProvider()));
        this.xstream.omitField(Scene.class, "originalWidth");
        this.xstream.omitField(Scene.class, "originalHeight");
        this.xstream.omitField(CameraBrick.class, "spinnerValues");
        this.xstream.omitField(ChooseCameraBrick.class, "spinnerValues");
        this.xstream.omitField(FlashBrick.class, "spinnerValues");
        this.xstream.omitField(SetNfcTagBrick.class, "nfcTagNdefDefaultType");
        this.xstream.omitField(SpeakAndWaitBrick.class, "speechFile");
        this.xstream.omitField(SpeakAndWaitBrick.class, "duration");
        this.xstream.omitField(StopScriptBrick.class, "spinnerValue");
        this.xstream.omitField(ShowTextBrick.class, "userVariableName");
        this.xstream.omitField(HideTextBrick.class, "userVariableName");
        this.xstream.omitField(SayBubbleBrick.class, "type");
        this.xstream.omitField(SayBubbleBrick.class, "type");
        this.xstream.omitField(ThinkBubbleBrick.class, "type");
        this.xstream.omitField(ThinkForBubbleBrick.class, "type");
        this.xstream.omitField(StartScript.class, "isUserScript");
        this.xstream.omitField(WhenScript.class, NativeProtocol.WEB_DIALOG_ACTION);
        this.xstream.omitField(RaspiInterruptScript.class, "receivedMessage");
        this.xstream.alias(Constants.MEDIA_TYPE_LOOK, LookData.class);
        this.xstream.alias("droneLook", DroneVideoLookData.class);
        this.xstream.alias(Constants.MEDIA_TYPE_SOUND, SoundInfo.class);
        this.xstream.alias("nfcTag", NfcTagData.class);
        this.xstream.alias("userVariable", UserVariable.class);
        this.xstream.alias("userList", UserList.class);
        this.xstream.alias("script", Script.class);
        this.xstream.alias("object", Sprite.class);
        this.xstream.alias("object", SingleSprite.class);
        this.xstream.alias("object", GroupSprite.class);
        this.xstream.alias("object", GroupItemSprite.class);
        this.xstream.alias("script", StartScript.class);
        this.xstream.alias("script", WhenClonedScript.class);
        this.xstream.alias("script", WhenScript.class);
        this.xstream.alias("script", WhenConditionScript.class);
        this.xstream.alias("script", WhenNfcScript.class);
        this.xstream.alias("script", BroadcastScript.class);
        this.xstream.alias("script", RaspiInterruptScript.class);
        this.xstream.alias("script", WhenTouchDownScript.class);
        this.xstream.alias("script", WhenBackgroundChangesScript.class);
        this.xstream.alias("brick", AddItemToUserListBrick.class);
        this.xstream.alias("brick", AskBrick.class);
        this.xstream.alias("brick", AskSpeechBrick.class);
        this.xstream.alias("brick", BroadcastBrick.class);
        this.xstream.alias("brick", BroadcastReceiverBrick.class);
        this.xstream.alias("brick", BroadcastWaitBrick.class);
        this.xstream.alias("brick", ChangeBrightnessByNBrick.class);
        this.xstream.alias("brick", ChangeColorByNBrick.class);
        this.xstream.alias("brick", ChangeTransparencyByNBrick.class);
        this.xstream.alias("brick", ChangeSizeByNBrick.class);
        this.xstream.alias("brick", ChangeVariableBrick.class);
        this.xstream.alias("brick", ChangeVolumeByNBrick.class);
        this.xstream.alias("brick", ChangeXByNBrick.class);
        this.xstream.alias("brick", ChangeYByNBrick.class);
        this.xstream.alias("brick", ClearBackgroundBrick.class);
        this.xstream.alias("brick", ClearGraphicEffectBrick.class);
        this.xstream.alias("brick", CloneBrick.class);
        this.xstream.alias("brick", ComeToFrontBrick.class);
        this.xstream.alias("brick", DeleteItemOfUserListBrick.class);
        this.xstream.alias("brick", DeleteThisCloneBrick.class);
        this.xstream.alias("brick", ForeverBrick.class);
        this.xstream.alias("brick", GlideToBrick.class);
        this.xstream.alias("brick", GoNStepsBackBrick.class);
        this.xstream.alias("brick", HideBrick.class);
        this.xstream.alias("brick", HideTextBrick.class);
        this.xstream.alias("brick", IfLogicBeginBrick.class);
        this.xstream.alias("brick", IfLogicElseBrick.class);
        this.xstream.alias("brick", IfLogicEndBrick.class);
        this.xstream.alias("brick", IfThenLogicBeginBrick.class);
        this.xstream.alias("brick", IfThenLogicEndBrick.class);
        this.xstream.alias("brick", IfOnEdgeBounceBrick.class);
        this.xstream.alias("brick", InsertItemIntoUserListBrick.class);
        this.xstream.alias("brick", FlashBrick.class);
        this.xstream.alias("brick", ChooseCameraBrick.class);
        this.xstream.alias("brick", CameraBrick.class);
        this.xstream.alias("brick", LegoNxtMotorMoveBrick.class);
        this.xstream.alias("brick", LegoNxtMotorStopBrick.class);
        this.xstream.alias("brick", LegoNxtMotorTurnAngleBrick.class);
        this.xstream.alias("brick", LegoNxtPlayToneBrick.class);
        this.xstream.alias("brick", LoopBeginBrick.class);
        this.xstream.alias("brick", LoopEndBrick.class);
        this.xstream.alias("brick", LoopEndlessBrick.class);
        this.xstream.alias("brick", MoveNStepsBrick.class);
        this.xstream.alias("brick", NextLookBrick.class);
        this.xstream.alias("brick", NoteBrick.class);
        this.xstream.alias("brick", PenDownBrick.class);
        this.xstream.alias("brick", PenUpBrick.class);
        this.xstream.alias("brick", PlaceAtBrick.class);
        this.xstream.alias("brick", GoToBrick.class);
        this.xstream.alias("brick", PlaySoundBrick.class);
        this.xstream.alias("brick", PlaySoundAndWaitBrick.class);
        this.xstream.alias("brick", PointInDirectionBrick.class);
        this.xstream.alias("brick", PointToBrick.class);
        this.xstream.alias("brick", PreviousLookBrick.class);
        this.xstream.alias("brick", RepeatBrick.class);
        this.xstream.alias("brick", RepeatUntilBrick.class);
        this.xstream.alias("brick", ReplaceItemInUserListBrick.class);
        this.xstream.alias("brick", SceneTransitionBrick.class);
        this.xstream.alias("brick", SceneStartBrick.class);
        this.xstream.alias("brick", SetBrightnessBrick.class);
        this.xstream.alias("brick", SetColorBrick.class);
        this.xstream.alias("brick", SetTransparencyBrick.class);
        this.xstream.alias("brick", SetLookBrick.class);
        this.xstream.alias("brick", SetLookByIndexBrick.class);
        this.xstream.alias("brick", SetBackgroundBrick.class);
        this.xstream.alias("brick", SetBackgroundByIndexBrick.class);
        this.xstream.alias("brick", SetBackgroundAndWaitBrick.class);
        this.xstream.alias("brick", SetBackgroundByIndexAndWaitBrick.class);
        this.xstream.alias("brick", SetPenColorBrick.class);
        this.xstream.alias("brick", SetPenSizeBrick.class);
        this.xstream.alias("brick", SetRotationStyleBrick.class);
        this.xstream.alias("brick", SetSizeToBrick.class);
        this.xstream.alias("brick", SetVariableBrick.class);
        this.xstream.alias("brick", SetVolumeToBrick.class);
        this.xstream.alias("brick", SetXBrick.class);
        this.xstream.alias("brick", SetYBrick.class);
        this.xstream.alias("brick", ShowBrick.class);
        this.xstream.alias("brick", ShowTextBrick.class);
        this.xstream.alias("brick", SpeakBrick.class);
        this.xstream.alias("brick", SpeakAndWaitBrick.class);
        this.xstream.alias("brick", StampBrick.class);
        this.xstream.alias("brick", StopAllSoundsBrick.class);
        this.xstream.alias("brick", ThinkBubbleBrick.class);
        this.xstream.alias("brick", SayBubbleBrick.class);
        this.xstream.alias("brick", ThinkForBubbleBrick.class);
        this.xstream.alias("brick", SayForBubbleBrick.class);
        this.xstream.alias("brick", TurnLeftBrick.class);
        this.xstream.alias("brick", TurnRightBrick.class);
        this.xstream.alias("brick", UserBrick.class);
        this.xstream.alias("brick", UserScriptDefinitionBrick.class);
        this.xstream.alias("brick", VibrationBrick.class);
        this.xstream.alias("brick", WaitBrick.class);
        this.xstream.alias("brick", WaitUntilBrick.class);
        this.xstream.alias("brick", WhenBrick.class);
        this.xstream.alias("brick", WhenConditionBrick.class);
        this.xstream.alias("brick", WhenBackgroundChangesBrick.class);
        this.xstream.alias("brick", WhenStartedBrick.class);
        this.xstream.alias("brick", WhenClonedBrick.class);
        this.xstream.alias("brick", StopScriptBrick.class);
        this.xstream.alias("brick", WhenNfcBrick.class);
        this.xstream.alias("brick", SetNfcTagBrick.class);
        this.xstream.alias("brick", DronePlayLedAnimationBrick.class);
        this.xstream.alias("brick", DroneTakeOffLandBrick.class);
        this.xstream.alias("brick", DroneMoveForwardBrick.class);
        this.xstream.alias("brick", DroneMoveBackwardBrick.class);
        this.xstream.alias("brick", DroneMoveUpBrick.class);
        this.xstream.alias("brick", DroneMoveDownBrick.class);
        this.xstream.alias("brick", DroneMoveLeftBrick.class);
        this.xstream.alias("brick", DroneMoveRightBrick.class);
        this.xstream.alias("brick", DroneTurnLeftBrick.class);
        this.xstream.alias("brick", DroneTurnRightBrick.class);
        this.xstream.alias("brick", DroneSwitchCameraBrick.class);
        this.xstream.alias("brick", DroneEmergencyBrick.class);
        this.xstream.alias("brick", PhiroMotorMoveBackwardBrick.class);
        this.xstream.alias("brick", PhiroMotorMoveForwardBrick.class);
        this.xstream.alias("brick", PhiroMotorStopBrick.class);
        this.xstream.alias("brick", PhiroPlayToneBrick.class);
        this.xstream.alias("brick", PhiroRGBLightBrick.class);
        this.xstream.alias("brick", PhiroIfLogicBeginBrick.class);
        this.xstream.alias("brick", LegoEv3PlayToneBrick.class);
        this.xstream.alias("brick", LegoEv3MotorMoveBrick.class);
        this.xstream.alias("brick", LegoEv3MotorStopBrick.class);
        this.xstream.alias("brick", LegoEv3SetLedBrick.class);
        this.xstream.alias("brick", ArduinoSendPWMValueBrick.class);
        this.xstream.alias("brick", ArduinoSendDigitalValueBrick.class);
        this.xstream.alias("brick", RaspiSendDigitalValueBrick.class);
        this.xstream.alias("brick", RaspiIfLogicBeginBrick.class);
        this.xstream.alias("brick", RaspiPwmBrick.class);
        this.xstream.alias("userBrickElement", UserScriptDefinitionBrickElement.class);
        this.xstream.alias("userBrickParameter", UserBrickParameter.class);
        this.xstream.alias("script", WhenGamepadButtonScript.class);
        this.xstream.alias("brick", WhenGamepadButtonBrick.class);
        this.xstream.alias("script", CollisionScript.class);
        this.xstream.alias("brick", CollisionReceiverBrick.class);
        this.xstream.alias("brick", SetBounceBrick.class);
        this.xstream.alias("brick", SetFrictionBrick.class);
        this.xstream.alias("brick", SetGravityBrick.class);
        this.xstream.alias("brick", SetMassBrick.class);
        this.xstream.alias("brick", SetPhysicsObjectTypeBrick.class);
        this.xstream.alias("brick", SetVelocityBrick.class);
        this.xstream.alias("brick", TurnLeftSpeedBrick.class);
        this.xstream.alias("brick", TurnRightSpeedBrick.class);
        this.xstream.alias("setting", LegoNXTSetting.class);
        this.xstream.alias("nxtPort", NXTPort.class);
        this.xstream.aliasAttribute(NXTPort.class, "number", "number");
        this.xstream.aliasField("formulaList", FormulaBrick.class, "formulaMap");
        this.xstream.aliasField("object", BrickBaseType.class, "sprite");
    }

    public Project loadProject(String projectName, Context context) throws IOException, LoadingProjectException {
        cleanUpTmpCodeFile(projectName);
        File xmlFile = new File(PathBuilder.buildProjectPath(projectName), Constants.CODE_XML_FILE_NAME);
        if (!Files.toString(xmlFile, Charsets.UTF_8).contains(Constants.SCENES_ENABLED_TAG)) {
            return loadProjectMissingScenes(projectName, context);
        }
        this.loadSaveLock.lock();
        try {
            Project project = (Project) this.xstream.getProjectFromXML(xmlFile);
            for (Scene scene : project.getSceneList()) {
                scene.setProject(project);
                scene.getDataContainer().setProjectUserData(project);
            }
            setFileReferences(project);
            this.loadSaveLock.unlock();
            return project;
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("An Error occurred while parsing the xml: ");
            stringBuilder.append(e.getMessage());
            throw new LoadingProjectException(stringBuilder.toString());
        } catch (Throwable th) {
            this.loadSaveLock.unlock();
        }
    }

    private static void setFileReferences(Project project) {
        for (Scene scene : project.getSceneList()) {
            File imageDir = new File(scene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
            File soundDir = new File(scene.getDirectory(), Constants.SOUND_DIRECTORY_NAME);
            for (Sprite sprite : scene.getSpriteList()) {
                Iterator<LookData> iterator = sprite.getLookList().iterator();
                while (iterator.hasNext()) {
                    LookData lookData = (LookData) iterator.next();
                    File lookFile = new File(imageDir, lookData.getXstreamFileName());
                    if (lookFile.exists()) {
                        lookData.setFile(lookFile);
                    } else {
                        iterator.remove();
                    }
                }
                Iterator<SoundInfo> iterator2 = sprite.getSoundList().iterator();
                while (iterator2.hasNext()) {
                    SoundInfo soundInfo = (SoundInfo) iterator2.next();
                    lookFile = new File(soundDir, soundInfo.getXstreamFileName());
                    if (lookFile.exists()) {
                        soundInfo.setFile(lookFile);
                    } else {
                        iterator2.remove();
                    }
                }
            }
        }
    }

    private Project loadProjectMissingScenes(String projectName, Context context) throws IOException {
        this.loadSaveLock.lock();
        File projectDir = new File(PathBuilder.buildProjectPath(projectName));
        new File(projectDir, Constants.IMAGE_DIRECTORY_NAME).mkdir();
        new File(projectDir, Constants.SOUND_DIRECTORY_NAME).mkdir();
        File xmlFile = new File(projectDir, Constants.CODE_XML_FILE_NAME);
        prepareXstream(SupportProject.class, SupportDataContainer.class);
        SupportProject supportProject = (SupportProject) this.xstream.getProjectFromXML(xmlFile);
        prepareXstream(Project.class, DataContainer.class);
        Project project = new Project(supportProject, context);
        File sceneDir = new File(PathBuilder.buildScenePath(projectName, project.getDefaultScene().getName()));
        StorageOperations.createSceneDirectory(sceneDir);
        File automaticScreenshot = new File(projectDir, "automatic_screenshot.png");
        File manualScreenshot = new File(projectDir, "manual_screenshot.png");
        StorageOperations.copyDir(new File(projectDir, Constants.IMAGE_DIRECTORY_NAME), new File(sceneDir, Constants.IMAGE_DIRECTORY_NAME));
        StorageOperations.copyDir(new File(projectDir, Constants.SOUND_DIRECTORY_NAME), new File(sceneDir, Constants.SOUND_DIRECTORY_NAME));
        if (automaticScreenshot.exists()) {
            FileUtils.copyFileToDir(automaticScreenshot, sceneDir);
            automaticScreenshot.delete();
        }
        if (manualScreenshot.exists()) {
            FileUtils.copyFileToDir(manualScreenshot, sceneDir);
            manualScreenshot.delete();
        }
        StorageOperations.deleteDir(new File(projectDir, Constants.IMAGE_DIRECTORY_NAME));
        StorageOperations.deleteDir(new File(projectDir, Constants.SOUND_DIRECTORY_NAME));
        setFileReferences(project);
        this.loadSaveLock.unlock();
        return project;
    }

    public boolean saveProject(Project project) {
        String previousXml;
        if (project == null) {
            return false;
        }
        try {
            cleanUpTmpCodeFile(project.getName());
            this.loadSaveLock.lock();
            BufferedWriter writer = null;
            File tmpCodeFile = null;
            File currentCodeFile = null;
            String currentXml;
            try {
                currentXml = XML_HEADER.concat(this.xstream.toXML(project));
                tmpCodeFile = new File(PathBuilder.buildProjectPath(project.getName()), Constants.TMP_CODE_XML_FILE_NAME);
                currentCodeFile = new File(PathBuilder.buildProjectPath(project.getName()), Constants.CODE_XML_FILE_NAME);
                if (currentCodeFile.exists()) {
                    String str;
                    StringBuilder stringBuilder;
                    try {
                        previousXml = Files.toString(currentCodeFile, Charsets.UTF_8);
                        if (previousXml.equals(currentXml)) {
                            str = TAG;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Project version is the same. Do not update ");
                            stringBuilder.append(currentCodeFile.getName());
                            Log.d(str, stringBuilder.toString());
                            if (writer != null) {
                                try {
                                    writer.close();
                                    if (currentCodeFile.exists() && !currentCodeFile.delete()) {
                                        str = TAG;
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append("Cannot delete ");
                                        stringBuilder.append(currentCodeFile.getName());
                                        Log.e(str, stringBuilder.toString());
                                    }
                                    if (!tmpCodeFile.renameTo(currentCodeFile)) {
                                        str = TAG;
                                        stringBuilder = new StringBuilder();
                                        stringBuilder.append("Cannot rename: ");
                                        stringBuilder.append(tmpCodeFile.getAbsolutePath());
                                        stringBuilder.append(" to ");
                                        stringBuilder.append(currentCodeFile.getName());
                                        Log.e(str, stringBuilder.toString());
                                    }
                                } catch (IOException e) {
                                    Log.e(TAG, "Cannot close Buffered Writer", e);
                                }
                            }
                            this.loadSaveLock.unlock();
                            return false;
                        }
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Project version differ <");
                        stringBuilder.append(previousXml.length());
                        stringBuilder.append("> <");
                        stringBuilder.append(currentXml.length());
                        stringBuilder.append(">. update ");
                        stringBuilder.append(currentCodeFile.getName());
                        Log.d(str, stringBuilder.toString());
                    } catch (Exception exception) {
                        str = TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Opening old project ");
                        stringBuilder.append(currentCodeFile.getAbsolutePath());
                        stringBuilder.append(" failed.");
                        Log.e(str, stringBuilder.toString(), exception);
                        if (writer != null) {
                            try {
                                writer.close();
                                if (currentCodeFile.exists() && !currentCodeFile.delete()) {
                                    str = TAG;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Cannot delete ");
                                    stringBuilder.append(currentCodeFile.getName());
                                    Log.e(str, stringBuilder.toString());
                                }
                                if (!tmpCodeFile.renameTo(currentCodeFile)) {
                                    str = TAG;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Cannot rename: ");
                                    stringBuilder.append(tmpCodeFile.getAbsolutePath());
                                    stringBuilder.append(" to ");
                                    stringBuilder.append(currentCodeFile.getName());
                                    Log.e(str, stringBuilder.toString());
                                }
                            } catch (IOException e2) {
                                Log.e(TAG, "Cannot close Buffered Writer", e2);
                            }
                        }
                        this.loadSaveLock.unlock();
                        return false;
                    }
                }
                StorageOperations.createDir(FlavoredConstants.DEFAULT_ROOT_DIRECTORY);
                File projectDir = new File(PathBuilder.buildProjectPath(project.getName()));
                StorageOperations.createDir(projectDir);
                for (Scene scene : project.getSceneList()) {
                    StorageOperations.createSceneDirectory(new File(projectDir, scene.getName()));
                }
                writer = new BufferedWriter(new FileWriter(tmpCodeFile), 8192);
                writer.write(currentXml);
                writer.flush();
                writer = new BufferedWriter(new FileWriter(new File(PathBuilder.buildProjectPath(project.getName()), Constants.PERMISSIONS_FILE_NAME)), 8192);
                for (String resource : generatePermissionsSetFromResource(project.getRequiredResources())) {
                    writer.write(resource);
                    writer.newLine();
                }
                writer.flush();
                if (writer != null) {
                    try {
                        String str2;
                        StringBuilder stringBuilder2;
                        writer.close();
                        if (currentCodeFile.exists() && !currentCodeFile.delete()) {
                            str2 = TAG;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Cannot delete ");
                            stringBuilder2.append(currentCodeFile.getName());
                            Log.e(str2, stringBuilder2.toString());
                        }
                        if (!tmpCodeFile.renameTo(currentCodeFile)) {
                            str2 = TAG;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Cannot rename: ");
                            stringBuilder2.append(tmpCodeFile.getAbsolutePath());
                            stringBuilder2.append(" to ");
                            stringBuilder2.append(currentCodeFile.getName());
                            Log.e(str2, stringBuilder2.toString());
                        }
                    } catch (IOException e3) {
                        Log.e(TAG, "Cannot close Buffered Writer", e3);
                    }
                }
                this.loadSaveLock.unlock();
                return true;
            } catch (Exception exception2) {
                previousXml = TAG;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("Saving project ");
                stringBuilder3.append(project.getName());
                stringBuilder3.append(" failed.");
                Log.e(previousXml, stringBuilder3.toString(), exception2);
                if (writer != null) {
                    try {
                        writer.close();
                        if (currentCodeFile.exists() && !currentCodeFile.delete()) {
                            previousXml = TAG;
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("Cannot delete ");
                            stringBuilder3.append(currentCodeFile.getName());
                            Log.e(previousXml, stringBuilder3.toString());
                        }
                        if (!tmpCodeFile.renameTo(currentCodeFile)) {
                            previousXml = TAG;
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("Cannot rename: ");
                            stringBuilder3.append(tmpCodeFile.getAbsolutePath());
                            stringBuilder3.append(" to ");
                            stringBuilder3.append(currentCodeFile.getName());
                            Log.e(previousXml, stringBuilder3.toString());
                        }
                    } catch (IOException e4) {
                        Log.e(TAG, "Cannot close Buffered Writer", e4);
                    }
                }
                this.loadSaveLock.unlock();
                return false;
            } catch (Throwable th) {
                if (writer != null) {
                    try {
                        StringBuilder stringBuilder4;
                        writer.close();
                        if (currentCodeFile.exists() && !currentCodeFile.delete()) {
                            currentXml = TAG;
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("Cannot delete ");
                            stringBuilder4.append(currentCodeFile.getName());
                            Log.e(currentXml, stringBuilder4.toString());
                        }
                        if (!tmpCodeFile.renameTo(currentCodeFile)) {
                            currentXml = TAG;
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("Cannot rename: ");
                            stringBuilder4.append(tmpCodeFile.getAbsolutePath());
                            stringBuilder4.append(" to ");
                            stringBuilder4.append(currentCodeFile.getName());
                            Log.e(currentXml, stringBuilder4.toString());
                        }
                    } catch (IOException e5) {
                        Log.e(TAG, "Cannot close Buffered Writer", e5);
                    }
                }
                this.loadSaveLock.unlock();
            }
        } catch (LoadingProjectException e6) {
            return false;
        }
    }

    private void cleanUpTmpCodeFile(String projectName) throws LoadingProjectException {
        this.loadSaveLock.lock();
        File projectDir = new File(PathBuilder.buildProjectPath(projectName));
        File tmpXmlFile = new File(projectDir, Constants.TMP_CODE_XML_FILE_NAME);
        File actualXmlFile = new File(projectDir, Constants.CODE_XML_FILE_NAME);
        if (tmpXmlFile.exists()) {
            if (actualXmlFile.exists()) {
                tmpXmlFile.delete();
            } else if (!tmpXmlFile.renameTo(actualXmlFile)) {
                this.loadSaveLock.unlock();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("code.xml did not exist. But wait, renaming ");
                stringBuilder.append(tmpXmlFile.getAbsolutePath());
                stringBuilder.append(" failed too.");
                throw new LoadingProjectException(stringBuilder.toString());
            }
        }
        this.loadSaveLock.unlock();
    }

    public boolean projectExists(String projectName) {
        for (String projectNameIterator : FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY)) {
            if (projectNameIterator.equals(projectName)) {
                return true;
            }
        }
        return false;
    }

    public String getXmlAsStringFromProject(Project project) {
        this.loadSaveLock.lock();
        try {
            String xmlString = this.xstream.toXML(project);
            return xmlString;
        } finally {
            this.loadSaveLock.unlock();
        }
    }

    private Set<String> generatePermissionsSetFromResource(ResourcesSet resources) {
        Set<String> permissionsSet = new HashSet();
        if (resources.contains(Integer.valueOf(1))) {
            permissionsSet.add(Constants.TEXT_TO_SPEECH);
        }
        if (resources.contains(Integer.valueOf(2))) {
            permissionsSet.add(Constants.BLUETOOTH_LEGO_NXT);
        }
        if (resources.contains(Integer.valueOf(5))) {
            permissionsSet.add(Constants.ARDRONE_SUPPORT);
        }
        if (resources.contains(Integer.valueOf(23))) {
            permissionsSet.add(Constants.JUMPING_SUMO_SUPPORT);
        }
        if (resources.contains(Integer.valueOf(10))) {
            permissionsSet.add(Constants.BLUETOOTH_PHIRO_PRO);
        }
        if (resources.contains(Integer.valueOf(8))) {
            permissionsSet.add(Constants.CAMERA_FLASH);
        }
        if (resources.contains(Integer.valueOf(9))) {
            permissionsSet.add(Constants.VIBRATOR);
        }
        if (resources.contains(Integer.valueOf(4))) {
            permissionsSet.add(Constants.FACE_DETECTION);
        }
        if (resources.contains(Integer.valueOf(16))) {
            permissionsSet.add(Constants.NFC);
        }
        return permissionsSet;
    }

    public void updateCodeFileOnDownload(String projectName) {
        this.xstream.updateCollisionReceiverBrickMessage(new File(PathBuilder.buildProjectPath(projectName), Constants.CODE_XML_FILE_NAME));
    }

    public static String extractDefaultSceneNameFromXml(String projectName) {
        File xmlFile = new File(new File(PathBuilder.buildProjectPath(projectName)), Constants.CODE_XML_FILE_NAME);
        StringFinder stringFinder = new StringFinder();
        try {
            if (stringFinder.findBetween(Files.toString(xmlFile, Charsets.UTF_8), "<scenes>\\s*<scene>\\s*<name>", "</name>")) {
                return stringFinder.getResult();
            }
            return null;
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return null;
        }
    }
}
