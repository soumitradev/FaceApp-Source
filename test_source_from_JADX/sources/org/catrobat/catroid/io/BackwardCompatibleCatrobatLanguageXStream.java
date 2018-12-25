package org.catrobat.catroid.io;

import android.support.v7.media.MediaRouteProviderProtocol;
import android.util.Log;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.CollisionScript;
import org.catrobat.catroid.content.RaspiInterruptScript;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.WhenBackgroundChangesScript;
import org.catrobat.catroid.content.WhenConditionScript;
import org.catrobat.catroid.content.WhenGamepadButtonScript;
import org.catrobat.catroid.content.WhenNfcScript;
import org.catrobat.catroid.content.WhenScript;
import org.catrobat.catroid.content.WhenTouchDownScript;
import org.catrobat.catroid.content.bricks.ArduinoSendDigitalValueBrick;
import org.catrobat.catroid.content.bricks.ArduinoSendPWMValueBrick;
import org.catrobat.catroid.content.bricks.AskBrick;
import org.catrobat.catroid.content.bricks.AskSpeechBrick;
import org.catrobat.catroid.content.bricks.Brick.BrickField;
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
import org.catrobat.catroid.content.bricks.IfLogicElseBrick;
import org.catrobat.catroid.content.bricks.IfLogicEndBrick;
import org.catrobat.catroid.content.bricks.IfOnEdgeBounceBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicEndBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorMoveBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorStopBrick;
import org.catrobat.catroid.content.bricks.LegoNxtMotorTurnAngleBrick;
import org.catrobat.catroid.content.bricks.LegoNxtPlayToneBrick;
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
import org.catrobat.catroid.content.bricks.SayBubbleBrick;
import org.catrobat.catroid.content.bricks.SayForBubbleBrick;
import org.catrobat.catroid.content.bricks.SceneStartBrick;
import org.catrobat.catroid.content.bricks.SceneTransitionBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundAndWaitBrick;
import org.catrobat.catroid.content.bricks.SetBackgroundBrick;
import org.catrobat.catroid.content.bricks.SetBrightnessBrick;
import org.catrobat.catroid.content.bricks.SetColorBrick;
import org.catrobat.catroid.content.bricks.SetLookBrick;
import org.catrobat.catroid.content.bricks.SetNfcTagBrick;
import org.catrobat.catroid.content.bricks.SetPenColorBrick;
import org.catrobat.catroid.content.bricks.SetPenSizeBrick;
import org.catrobat.catroid.content.bricks.SetRotationStyleBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.SetTextBrick;
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
import org.catrobat.catroid.content.bricks.VibrationBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.content.bricks.WhenBrick;
import org.catrobat.catroid.content.bricks.WhenClonedBrick;
import org.catrobat.catroid.content.bricks.WhenConditionBrick;
import org.catrobat.catroid.content.bricks.WhenGamepadButtonBrick;
import org.catrobat.catroid.content.bricks.WhenNfcBrick;
import org.catrobat.catroid.content.bricks.WhenStartedBrick;
import org.catrobat.catroid.physics.PhysicsCollision;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;
import org.catrobat.catroid.physics.content.bricks.SetBounceBrick;
import org.catrobat.catroid.physics.content.bricks.SetFrictionBrick;
import org.catrobat.catroid.physics.content.bricks.SetGravityBrick;
import org.catrobat.catroid.physics.content.bricks.SetMassBrick;
import org.catrobat.catroid.physics.content.bricks.SetPhysicsObjectTypeBrick;
import org.catrobat.catroid.physics.content.bricks.SetVelocityBrick;
import org.catrobat.catroid.physics.content.bricks.TurnLeftSpeedBrick;
import org.catrobat.catroid.physics.content.bricks.TurnRightSpeedBrick;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BackwardCompatibleCatrobatLanguageXStream extends XStream {
    private static final String TAG = BackwardCompatibleCatrobatLanguageXStream.class.getSimpleName();
    private HashMap<String, BrickInfo> brickInfoMap;
    private HashMap<String, String> scriptInfoMap;

    private class BrickInfo {
        private String brickClassName;
        private HashMap<String, BrickField> brickFieldMap;

        BrickInfo(String brickClassName) {
            this.brickClassName = brickClassName;
        }

        void addBrickFieldToMap(String oldFiledName, BrickField brickField) {
            if (this.brickFieldMap == null) {
                this.brickFieldMap = new HashMap();
            }
            this.brickFieldMap.put(oldFiledName, brickField);
        }

        BrickField getBrickFieldForOldFieldName(String oldFiledName) {
            if (this.brickFieldMap != null) {
                return (BrickField) this.brickFieldMap.get(oldFiledName);
            }
            return null;
        }

        String getBrickClassName() {
            return this.brickClassName;
        }
    }

    public BackwardCompatibleCatrobatLanguageXStream(PureJavaReflectionProvider reflectionProvider) {
        super((ReflectionProvider) reflectionProvider);
    }

    public Object getProjectFromXML(File file) {
        try {
            return super.fromXML(file);
        } catch (ConversionException exception) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Conversion error ");
            stringBuilder.append(exception.getLocalizedMessage());
            Log.e(str, stringBuilder.toString());
            modifyXMLToSupportUnknownFields(file);
            return super.fromXML(file);
        }
    }

    private void initializeBrickInfoMap() {
        if (this.brickInfoMap == null) {
            this.brickInfoMap = new HashMap();
            BrickInfo brickInfo = new BrickInfo(AskBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("askQuestion", BrickField.ASK_QUESTION);
            this.brickInfoMap.put("askBrick", brickInfo);
            brickInfo = new BrickInfo(AskSpeechBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("askQuestionSpoken", BrickField.ASK_SPEECH_QUESTION);
            this.brickInfoMap.put("askSpeechBrick", brickInfo);
            this.brickInfoMap.put("broadcastBrick", new BrickInfo(BroadcastBrick.class.getSimpleName()));
            this.brickInfoMap.put("broadcastReceiverBrick", new BrickInfo(BroadcastReceiverBrick.class.getSimpleName()));
            this.brickInfoMap.put("broadcastWaitBrick", new BrickInfo(BroadcastWaitBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(ChangeBrightnessByNBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("changeBrightness", BrickField.BRIGHTNESS_CHANGE);
            this.brickInfoMap.put("changeBrightnessByNBrick", brickInfo);
            brickInfo = new BrickInfo(ChangeColorByNBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("changeColor", BrickField.COLOR_CHANGE);
            this.brickInfoMap.put("changeColorByNBrick", brickInfo);
            brickInfo = new BrickInfo(ChangeTransparencyByNBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("changeTransparency", BrickField.TRANSPARENCY_CHANGE);
            this.brickInfoMap.put("changeTransparencyByNBrick", brickInfo);
            brickInfo = new BrickInfo(ChangeSizeByNBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("size", BrickField.SIZE_CHANGE);
            this.brickInfoMap.put("changeSizeByNBrick", brickInfo);
            brickInfo = new BrickInfo(ChangeVariableBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("variableFormula", BrickField.VARIABLE_CHANGE);
            this.brickInfoMap.put("changeVariableBrick", brickInfo);
            brickInfo = new BrickInfo(ChangeVolumeByNBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, BrickField.VOLUME_CHANGE);
            this.brickInfoMap.put("changeVolumeByNBrick", brickInfo);
            brickInfo = new BrickInfo(ChangeXByNBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("xMovement", BrickField.X_POSITION_CHANGE);
            this.brickInfoMap.put("changeXByNBrick", brickInfo);
            brickInfo = new BrickInfo(ChangeYByNBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("yMovement", BrickField.Y_POSITION_CHANGE);
            this.brickInfoMap.put("changeYByNBrick", brickInfo);
            this.brickInfoMap.put("clearGraphicEffectBrick", new BrickInfo(ClearGraphicEffectBrick.class.getSimpleName()));
            this.brickInfoMap.put("comeToFrontBrick", new BrickInfo(ComeToFrontBrick.class.getSimpleName()));
            this.brickInfoMap.put("foreverBrick", new BrickInfo(ForeverBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(GlideToBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("xDestination", BrickField.X_DESTINATION);
            brickInfo.addBrickFieldToMap("yDestination", BrickField.Y_DESTINATION);
            brickInfo.addBrickFieldToMap("durationInSeconds", BrickField.DURATION_IN_SECONDS);
            this.brickInfoMap.put("glideToBrick", brickInfo);
            brickInfo = new BrickInfo(GoNStepsBackBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("steps", BrickField.STEPS);
            this.brickInfoMap.put("goNStepsBackBrick", brickInfo);
            this.brickInfoMap.put("hideBrick", new BrickInfo(HideBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(IfLogicBeginBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("ifCondition", BrickField.IF_CONDITION);
            this.brickInfoMap.put("ifLogicBeginBrick", brickInfo);
            this.brickInfoMap.put("ifLogicElseBrick", new BrickInfo(IfLogicElseBrick.class.getSimpleName()));
            this.brickInfoMap.put("ifLogicEndBrick", new BrickInfo(IfLogicEndBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(IfThenLogicBeginBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("ifCondition", BrickField.IF_CONDITION);
            this.brickInfoMap.put("ifThenLogicBeginBrick", brickInfo);
            this.brickInfoMap.put("ifThenLogicEndBrick", new BrickInfo(IfThenLogicEndBrick.class.getSimpleName()));
            this.brickInfoMap.put("ifOnEdgeBounceBrick", new BrickInfo(IfOnEdgeBounceBrick.class.getSimpleName()));
            this.brickInfoMap.put("flashBrick", new BrickInfo(FlashBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(LegoNxtMotorMoveBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("speed", BrickField.LEGO_NXT_SPEED);
            this.brickInfoMap.put("legoNxtMotorMoveBrick", brickInfo);
            this.brickInfoMap.put("legoNxtMotorStopBrick", new BrickInfo(LegoNxtMotorStopBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(LegoNxtMotorTurnAngleBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("degrees", BrickField.LEGO_NXT_DEGREES);
            this.brickInfoMap.put("legoNxtMotorTurnAngleBrick", brickInfo);
            brickInfo = new BrickInfo(LegoNxtPlayToneBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("frequency", BrickField.LEGO_NXT_FREQUENCY);
            brickInfo.addBrickFieldToMap("durationInSeconds", BrickField.LEGO_NXT_DURATION_IN_SECONDS);
            this.brickInfoMap.put("legoNxtPlayToneBrick", brickInfo);
            brickInfo = new BrickInfo(PhiroMotorMoveForwardBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("speed", BrickField.PHIRO_SPEED);
            this.brickInfoMap.put("phiroMotorMoveForwardBrick", brickInfo);
            brickInfo = new BrickInfo(PhiroMotorMoveBackwardBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("speed", BrickField.PHIRO_SPEED);
            this.brickInfoMap.put("phiroMotorMoveBackwardBrick", brickInfo);
            this.brickInfoMap.put("phiroMotorStopBrick", new BrickInfo(PhiroMotorStopBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(PhiroPlayToneBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("durationInSeconds", BrickField.PHIRO_DURATION_IN_SECONDS);
            this.brickInfoMap.put("phiroPlayToneBrick", brickInfo);
            brickInfo = new BrickInfo(PhiroRGBLightBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("light", BrickField.PHIRO_LIGHT_RED);
            brickInfo.addBrickFieldToMap("light", BrickField.PHIRO_LIGHT_GREEN);
            brickInfo.addBrickFieldToMap("light", BrickField.PHIRO_LIGHT_BLUE);
            this.brickInfoMap.put("phiroRGBLightBrick", brickInfo);
            this.brickInfoMap.put("phiroSensorBrick", new BrickInfo(PhiroIfLogicBeginBrick.class.getSimpleName()));
            this.brickInfoMap.put("phiroSensorElseBrick", new BrickInfo(IfLogicElseBrick.class.getSimpleName()));
            this.brickInfoMap.put("phiroSensorEndBrick", new BrickInfo(IfLogicEndBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(ArduinoSendDigitalValueBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("digitalPinNumber", BrickField.ARDUINO_DIGITAL_PIN_NUMBER);
            brickInfo.addBrickFieldToMap("digitalPinValue", BrickField.ARDUINO_DIGITAL_PIN_VALUE);
            this.brickInfoMap.put("arduinoSendDigitalValueBrick", brickInfo);
            brickInfo = new BrickInfo(ArduinoSendPWMValueBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("pwmPinNumber", BrickField.ARDUINO_ANALOG_PIN_NUMBER);
            brickInfo.addBrickFieldToMap("pwmPinValue", BrickField.ARDUINO_ANALOG_PIN_VALUE);
            this.brickInfoMap.put("arduinoSendPWMValueBrick", brickInfo);
            brickInfo = new BrickInfo(RaspiSendDigitalValueBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("digitalPinNumber", BrickField.RASPI_DIGITAL_PIN_NUMBER);
            brickInfo.addBrickFieldToMap("digitalPinValue", BrickField.RASPI_DIGITAL_PIN_VALUE);
            this.brickInfoMap.put("raspiSendDigitalValueBrick", brickInfo);
            brickInfo = new BrickInfo(RaspiPwmBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("digitalPinNumber", BrickField.RASPI_DIGITAL_PIN_NUMBER);
            brickInfo.addBrickFieldToMap("pwmFrequency", BrickField.RASPI_PWM_FREQUENCY);
            brickInfo.addBrickFieldToMap("pwmPercentage", BrickField.RASPI_PWM_PERCENTAGE);
            this.brickInfoMap.put("raspiPwmBrick", brickInfo);
            brickInfo = new BrickInfo(RaspiIfLogicBeginBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("digitalPinNumber", BrickField.IF_CONDITION);
            this.brickInfoMap.put("raspiIfLogicBeginBrick", brickInfo);
            this.brickInfoMap.put("loopEndBrick", new BrickInfo(LoopEndBrick.class.getSimpleName()));
            this.brickInfoMap.put("loopEndlessBrick", new BrickInfo(LoopEndlessBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(MoveNStepsBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("steps", BrickField.STEPS);
            this.brickInfoMap.put("moveNStepsBrick", brickInfo);
            this.brickInfoMap.put("nextLookBrick", new BrickInfo(NextLookBrick.class.getSimpleName()));
            this.brickInfoMap.put("previousLookBrick", new BrickInfo(PreviousLookBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(NoteBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("note", BrickField.NOTE);
            this.brickInfoMap.put("noteBrick", brickInfo);
            brickInfo = new BrickInfo(PlaceAtBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("xPosition", BrickField.X_POSITION);
            brickInfo.addBrickFieldToMap("yPosition", BrickField.Y_POSITION);
            this.brickInfoMap.put("placeAtBrick", brickInfo);
            this.brickInfoMap.put("goToBrick", new BrickInfo(GoToBrick.class.getSimpleName()));
            this.brickInfoMap.put("playSoundBrick", new BrickInfo(PlaySoundBrick.class.getSimpleName()));
            this.brickInfoMap.put("playSoundAndWaitBrick", new BrickInfo(PlaySoundAndWaitBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(PointInDirectionBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("degrees", BrickField.DEGREES);
            this.brickInfoMap.put("pointInDirectionBrick", brickInfo);
            this.brickInfoMap.put("pointToBrick", new BrickInfo(PointToBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(RepeatBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timesToRepeat", BrickField.TIMES_TO_REPEAT);
            this.brickInfoMap.put("repeatBrick", brickInfo);
            this.brickInfoMap.put("sceneTransitionBrick", new BrickInfo(SceneTransitionBrick.class.getSimpleName()));
            this.brickInfoMap.put("sceneStartBrick", new BrickInfo(SceneStartBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(SetBrightnessBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("brightness", BrickField.BRIGHTNESS);
            this.brickInfoMap.put("setBrightnessBrick", brickInfo);
            brickInfo = new BrickInfo(SetColorBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("color", BrickField.COLOR);
            this.brickInfoMap.put("setColorBrick", brickInfo);
            brickInfo = new BrickInfo(SetTransparencyBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("transparency", BrickField.TRANSPARENCY);
            this.brickInfoMap.put("setTransparencyBrick", brickInfo);
            this.brickInfoMap.put("setBackgroundBrick", new BrickInfo(SetBackgroundBrick.class.getSimpleName()));
            this.brickInfoMap.put("setBackgroundAndWaitBrick", new BrickInfo(SetBackgroundAndWaitBrick.class.getSimpleName()));
            this.brickInfoMap.put("setLookBrick", new BrickInfo(SetLookBrick.class.getSimpleName()));
            this.brickInfoMap.put("setRotationStyleBrick", new BrickInfo(SetRotationStyleBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(SetSizeToBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("size", BrickField.SIZE);
            this.brickInfoMap.put("setSizeToBrick", brickInfo);
            brickInfo = new BrickInfo(SetVariableBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("variableFormula", BrickField.VARIABLE);
            this.brickInfoMap.put("setVariableBrick", brickInfo);
            brickInfo = new BrickInfo(SetVolumeToBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, BrickField.VOLUME);
            this.brickInfoMap.put("setVolumeToBrick", brickInfo);
            brickInfo = new BrickInfo(SetXBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("xPosition", BrickField.X_POSITION);
            this.brickInfoMap.put("setXBrick", brickInfo);
            brickInfo = new BrickInfo(SetYBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("yPosition", BrickField.Y_POSITION);
            this.brickInfoMap.put("setYBrick", brickInfo);
            this.brickInfoMap.put("showBrick", new BrickInfo(ShowBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(SpeakBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("text", BrickField.SPEAK);
            this.brickInfoMap.put("speakBrick", brickInfo);
            brickInfo = new BrickInfo(ThinkBubbleBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("text", BrickField.STRING);
            this.brickInfoMap.put("thinkBubbleBrick", brickInfo);
            brickInfo = new BrickInfo(SayBubbleBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("text", BrickField.STRING);
            this.brickInfoMap.put("sayBubbleBrick", brickInfo);
            brickInfo = new BrickInfo(ThinkForBubbleBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("text", BrickField.STRING);
            brickInfo.addBrickFieldToMap("durationInSeconds", BrickField.DURATION_IN_SECONDS);
            this.brickInfoMap.put("thinkForBubbleBrick", brickInfo);
            brickInfo = new BrickInfo(SayForBubbleBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("text", BrickField.STRING);
            brickInfo.addBrickFieldToMap("durationInSeconds", BrickField.DURATION_IN_SECONDS);
            this.brickInfoMap.put("sayForBubbleBrick", brickInfo);
            brickInfo = new BrickInfo(SpeakAndWaitBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("text", BrickField.SPEAK);
            this.brickInfoMap.put("speakAndWaitBrick", brickInfo);
            this.brickInfoMap.put("whenBrick", new BrickInfo(WhenBrick.class.getSimpleName()));
            this.brickInfoMap.put("whenConditionBrick", new BrickInfo(WhenConditionBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(TurnLeftBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("degrees", BrickField.TURN_LEFT_DEGREES);
            this.brickInfoMap.put("turnLeftBrick", brickInfo);
            brickInfo = new BrickInfo(TurnRightBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("degrees", BrickField.TURN_RIGHT_DEGREES);
            this.brickInfoMap.put("turnRightBrick", brickInfo);
            this.brickInfoMap.put("vibrationBrick", new BrickInfo(VibrationBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(WaitBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToWaitInSeconds", BrickField.TIME_TO_WAIT_IN_SECONDS);
            this.brickInfoMap.put("waitBrick", brickInfo);
            this.brickInfoMap.put("stopAllSoundsBrick", new BrickInfo(StopAllSoundsBrick.class.getSimpleName()));
            this.brickInfoMap.put("whenStartedBrick", new BrickInfo(WhenStartedBrick.class.getSimpleName()));
            this.brickInfoMap.put("stopScriptBrick", new BrickInfo(StopScriptBrick.class.getSimpleName()));
            this.brickInfoMap.put("whenNfcBrick", new BrickInfo(WhenNfcBrick.class.getSimpleName()));
            this.brickInfoMap.put("setNfcTagBrick", new BrickInfo(SetNfcTagBrick.class.getSimpleName()));
            this.brickInfoMap.put("whenClonedBrick", new BrickInfo(WhenClonedBrick.class.getSimpleName()));
            this.brickInfoMap.put("cloneBrick", new BrickInfo(CloneBrick.class.getSimpleName()));
            this.brickInfoMap.put("deleteThisCloneBrick", new BrickInfo(DeleteThisCloneBrick.class.getSimpleName()));
            this.brickInfoMap.put("dronePlayLedAnimationBrick", new BrickInfo(DronePlayLedAnimationBrick.class.getSimpleName()));
            this.brickInfoMap.put("droneGoEmergencyBrick", new BrickInfo(DroneEmergencyBrick.class.getSimpleName()));
            this.brickInfoMap.put("droneTakeOffBrick", new BrickInfo(DroneTakeOffLandBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(DroneMoveForwardBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToFlyInSeconds", BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
            brickInfo.addBrickFieldToMap("powerInPercent", BrickField.DRONE_POWER_IN_PERCENT);
            this.brickInfoMap.put("droneMoveForwardBrick", brickInfo);
            brickInfo = new BrickInfo(DroneMoveBackwardBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToFlyInSeconds", BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
            brickInfo.addBrickFieldToMap("powerInPercent", BrickField.DRONE_POWER_IN_PERCENT);
            this.brickInfoMap.put("droneMoveBackwardBrick", brickInfo);
            brickInfo = new BrickInfo(DroneMoveUpBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToFlyInSeconds", BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
            brickInfo.addBrickFieldToMap("powerInPercent", BrickField.DRONE_POWER_IN_PERCENT);
            this.brickInfoMap.put("droneMoveUpBrick", brickInfo);
            brickInfo = new BrickInfo(DroneMoveDownBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToFlyInSeconds", BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
            brickInfo.addBrickFieldToMap("powerInPercent", BrickField.DRONE_POWER_IN_PERCENT);
            this.brickInfoMap.put("droneMoveDownBrick", brickInfo);
            brickInfo = new BrickInfo(DroneMoveLeftBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToFlyInSeconds", BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
            brickInfo.addBrickFieldToMap("powerInPercent", BrickField.DRONE_POWER_IN_PERCENT);
            this.brickInfoMap.put("droneMoveLeftBrick", brickInfo);
            brickInfo = new BrickInfo(DroneMoveRightBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToFlyInSeconds", BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
            brickInfo.addBrickFieldToMap("powerInPercent", BrickField.DRONE_POWER_IN_PERCENT);
            this.brickInfoMap.put("droneMoveRightBrick", brickInfo);
            brickInfo = new BrickInfo(DroneTurnLeftBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToFlyInSeconds", BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
            brickInfo.addBrickFieldToMap("powerInPercent", BrickField.DRONE_POWER_IN_PERCENT);
            this.brickInfoMap.put("droneTurnLeftBrick", brickInfo);
            brickInfo = new BrickInfo(DroneTurnRightBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("timeToFlyInSeconds", BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
            brickInfo.addBrickFieldToMap("powerInPercent", BrickField.DRONE_POWER_IN_PERCENT);
            this.brickInfoMap.put("droneTurnRightBrick", brickInfo);
            this.brickInfoMap.put("droneSwitchCameraBrick", new BrickInfo(DroneSwitchCameraBrick.class.getSimpleName()));
            this.brickInfoMap.put("droneEmergencyBrick", new BrickInfo(DroneEmergencyBrick.class.getSimpleName()));
            this.brickInfoMap.put("droneFlipBrick", new BrickInfo(DroneFlipBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(SetTextBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("xDestination", BrickField.X_DESTINATION);
            brickInfo.addBrickFieldToMap("yDestination", BrickField.Y_DESTINATION);
            brickInfo.addBrickFieldToMap("string", BrickField.STRING);
            this.brickInfoMap.put("setTextBrick", brickInfo);
            this.brickInfoMap.put("showTextBrick", new BrickInfo(ShowTextBrick.class.getSimpleName()));
            this.brickInfoMap.put("hideTextBrick", new BrickInfo(HideTextBrick.class.getSimpleName()));
            this.brickInfoMap.put("videoBrick", new BrickInfo(CameraBrick.class.getSimpleName()));
            this.brickInfoMap.put("chooseCameraBrick", new BrickInfo(ChooseCameraBrick.class.getSimpleName()));
            this.brickInfoMap.put("collisionReceiverBrick", new BrickInfo(CollisionReceiverBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(SetBounceBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("bounceFactor", BrickField.PHYSICS_BOUNCE_FACTOR);
            this.brickInfoMap.put("setBounceBrick", brickInfo);
            brickInfo = new BrickInfo(SetFrictionBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("friction", BrickField.PHYSICS_FRICTION);
            this.brickInfoMap.put("setFrictionBrick", brickInfo);
            brickInfo = new BrickInfo(SetGravityBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("gravityX", BrickField.PHYSICS_GRAVITY_X);
            brickInfo.addBrickFieldToMap("gravityY", BrickField.PHYSICS_GRAVITY_Y);
            this.brickInfoMap.put("setGravityBrick", brickInfo);
            brickInfo = new BrickInfo(SetMassBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("mass", BrickField.PHYSICS_MASS);
            this.brickInfoMap.put("setMassBrick", brickInfo);
            this.brickInfoMap.put("setPhysicsObjectTypeBrick", new BrickInfo(SetPhysicsObjectTypeBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(SetVelocityBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("velocityX", BrickField.PHYSICS_VELOCITY_X);
            brickInfo.addBrickFieldToMap("velocityY", BrickField.PHYSICS_VELOCITY_Y);
            this.brickInfoMap.put("setVelocityBrick", brickInfo);
            brickInfo = new BrickInfo(TurnLeftSpeedBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("turnLeftSpeed", BrickField.PHYSICS_TURN_LEFT_SPEED);
            this.brickInfoMap.put("turnLeftSpeedBrick", brickInfo);
            brickInfo = new BrickInfo(TurnRightSpeedBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("turnRightSpeed", BrickField.PHYSICS_TURN_RIGHT_SPEED);
            this.brickInfoMap.put("turnRightSpeedBrick", brickInfo);
            this.brickInfoMap.put("penDownBrick", new BrickInfo(PenDownBrick.class.getSimpleName()));
            this.brickInfoMap.put("penUpBrick", new BrickInfo(PenUpBrick.class.getSimpleName()));
            this.brickInfoMap.put("stampBrick", new BrickInfo(StampBrick.class.getSimpleName()));
            this.brickInfoMap.put("clearBackgroundBrick", new BrickInfo(ClearBackgroundBrick.class.getSimpleName()));
            brickInfo = new BrickInfo(SetPenSizeBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("penSize", BrickField.PEN_SIZE);
            this.brickInfoMap.put("setPenSizeBrick", brickInfo);
            brickInfo = new BrickInfo(SetPenColorBrick.class.getSimpleName());
            brickInfo.addBrickFieldToMap("penColor", BrickField.PEN_COLOR_RED);
            brickInfo.addBrickFieldToMap("penColor", BrickField.PEN_COLOR_GREEN);
            brickInfo.addBrickFieldToMap("penColor", BrickField.PEN_COLOR_BLUE);
            this.brickInfoMap.put("setPenColorBrick", brickInfo);
            this.brickInfoMap.put("whenGamepadButtonBrick", new BrickInfo(WhenGamepadButtonBrick.class.getSimpleName()));
        }
    }

    private void initializeScriptInfoMap() {
        if (this.scriptInfoMap == null) {
            this.scriptInfoMap = new HashMap();
            this.scriptInfoMap.put("startScript", StartScript.class.getSimpleName());
            this.scriptInfoMap.put("whenScript", WhenScript.class.getSimpleName());
            this.scriptInfoMap.put("whenConditionScript", WhenConditionScript.class.getSimpleName());
            this.scriptInfoMap.put("whenBackgroundChangesScript", WhenBackgroundChangesScript.class.getSimpleName());
            this.scriptInfoMap.put("broadcastScript", BroadcastScript.class.getSimpleName());
            this.scriptInfoMap.put("raspiInterruptScript", RaspiInterruptScript.class.getSimpleName());
            this.scriptInfoMap.put("whenNfcScript", WhenNfcScript.class.getSimpleName());
            this.scriptInfoMap.put("collisionScript", CollisionScript.class.getSimpleName());
            this.scriptInfoMap.put("whenTouchDownScript", WhenTouchDownScript.class.getSimpleName());
            this.scriptInfoMap.put("whenGamepadButtonScript", WhenGamepadButtonScript.class.getSimpleName());
        }
    }

    private void modifyXMLToSupportUnknownFields(File file) {
        initializeScriptInfoMap();
        initializeBrickInfoMap();
        Document originalDocument = getDocument(file);
        if (originalDocument != null) {
            updateLegoNXTFields(originalDocument);
            convertChildNodeToAttribute(originalDocument, "lookList", "name");
            convertChildNodeToAttribute(originalDocument, "object", "name");
            deleteChildNodeByName(originalDocument, "scriptList", "object");
            deleteChildNodeByName(originalDocument, "brickList", "object");
            deleteChildNodeByName(originalDocument.getElementsByTagName("header").item(0), "isPhiroProProject");
            deleteChildNodeByName(originalDocument, "brickList", "inUserBrick");
            renameScriptChildNodeByName(originalDocument, "CollisionScript", "receivedMessage", "spriteToCollideWithName");
            modifyScriptLists(originalDocument);
            modifyBrickLists(originalDocument);
            modifyVariables(originalDocument);
            checkReferences(originalDocument.getDocumentElement());
            saveDocument(originalDocument, file);
        }
    }

    private void renameScriptChildNodeByName(Document originalDocument, String scriptName, String oldChildNodeName, String newChildNodeName) {
        for (Node collisionScript : getElementsFilteredByAttribute(originalDocument.getElementsByTagName("script"), "type", scriptName)) {
            originalDocument.renameNode(findNodeByName(collisionScript, oldChildNodeName), null, newChildNodeName);
        }
    }

    private List<Node> getElementsFilteredByAttribute(NodeList unfiltered, String attributeName, String filterAttributeValue) {
        List<Node> filtered = new ArrayList();
        for (int i = 0; i < unfiltered.getLength(); i++) {
            Node node = unfiltered.item(i);
            String attributeValue = getAttributeOfNode(attributeName, node);
            if (attributeValue != null && attributeValue.equals(filterAttributeValue)) {
                filtered.add(node);
            }
        }
        return filtered;
    }

    private String getAttributeOfNode(String attributeName, Node node) {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            return attributes.getNamedItem(attributeName).getNodeValue();
        }
        return null;
    }

    private void updateLegoNXTFields(Document originalDocument) {
        String oldDriveMotors = "MOTOR_A_C";
        String newDriveMotors = "MOTOR_B_C";
        String oldMotorMoveBrickName = "legoNxtMotorActionBrick";
        String newMotorMoveBrickName = "legoNxtMotorMoveBrick";
        NodeList motors = originalDocument.getElementsByTagName("motor");
        for (int i = 0; i < motors.getLength(); i++) {
            Node motor = motors.item(i);
            if (motor.getTextContent().equals("MOTOR_A_C")) {
                motor.setTextContent("MOTOR_B_C");
            }
        }
        NodeList motorMoveBricks = originalDocument.getElementsByTagName("legoNxtMotorActionBrick");
        for (int i2 = 0; i2 < motorMoveBricks.getLength(); i2++) {
            motor = motorMoveBricks.item(i2);
            originalDocument.renameNode(motor, motor.getNamespaceURI(), "legoNxtMotorMoveBrick");
        }
    }

    public void updateCollisionReceiverBrickMessage(File file) {
        String collisionTag = "CollisionScript";
        String receivedTag = "receivedMessage";
        Document originalDocument = getDocument(file);
        if (originalDocument != null) {
            NodeList scripts = originalDocument.getElementsByTagName("script");
            for (int i = 0; i < scripts.getLength(); i++) {
                Node script = scripts.item(i);
                NamedNodeMap attr = script.getAttributes();
                if (attr.getLength() > 0) {
                    for (int j = 0; j < attr.getLength(); j++) {
                        if (attr.item(j).getNodeValue().equals("CollisionScript")) {
                            NodeList messages = script.getChildNodes();
                            for (int k = 0; k < messages.getLength(); k++) {
                                Node message = messages.item(k);
                                if (message.getNodeName().equals("receivedMessage")) {
                                    String[] broadcastMessages = message.getTextContent().split("<(\\W)*-(\\W)*>");
                                    if (broadcastMessages[1].matches("(\\W)*ANYTHING(\\W)*")) {
                                        broadcastMessages[1] = PhysicsCollision.COLLISION_WITH_ANYTHING_IDENTIFIER;
                                    }
                                    String broadcastMessage = new StringBuilder();
                                    broadcastMessage.append(broadcastMessages[0]);
                                    broadcastMessage.append(PhysicsCollision.COLLISION_MESSAGE_CONNECTOR);
                                    broadcastMessage.append(broadcastMessages[1]);
                                    message.setTextContent(broadcastMessage.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
        saveDocument(originalDocument, file);
    }

    private void modifyVariables(Document originalDocument) {
        Node variableNode = originalDocument.getElementsByTagName("variables").item(0);
        if (variableNode != null) {
            originalDocument.renameNode(variableNode, variableNode.getNamespaceURI(), ShareConstants.WEB_DIALOG_PARAM_DATA);
        } else {
            Log.e(TAG, "XML-Update: No variables to modify.");
        }
    }

    private Document getDocument(File file) {
        try {
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty("indent", "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            normalizeWhiteSpaces(doc);
            return doc;
        } catch (Exception exception) {
            Log.e(TAG, "Failed to parse file to a Document", exception);
            return null;
        }
    }

    private void saveDocument(Document doc, File file) {
        try {
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty("indent", "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            serializer.transform(new DOMSource(doc), new StreamResult(file.getPath()));
        } catch (Exception exception) {
            Log.e(TAG, "Failed to save document to file", exception);
        }
    }

    private void normalizeWhiteSpaces(Document document) throws XPathExpressionException {
        NodeList emptyTextNodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//text()[normalize-space(.)='']", document, XPathConstants.NODESET);
        for (int index = 0; index < emptyTextNodeList.getLength(); index++) {
            Node emptyTextNode = emptyTextNodeList.item(index);
            emptyTextNode.getParentNode().removeChild(emptyTextNode);
        }
    }

    private Element findNodeByName(Node parentNode, String nodeName) {
        NodeList childNodes = parentNode.getChildNodes();
        if (childNodes != null) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (childNodes.item(i).getNodeName().equals(nodeName)) {
                    return (Element) childNodes.item(i);
                }
            }
        }
        return null;
    }

    private void deleteChildNodeByName(Node parentNode, String childNodeName) {
        Node node = findNodeByName(parentNode, childNodeName);
        if (node != null) {
            parentNode.removeChild(node);
        }
    }

    private void deleteChildNodeByName(Document doc, String listNodeName, String childNodeName) {
        NodeList nodeList = doc.getElementsByTagName(listNodeName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.hasChildNodes()) {
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    deleteChildNodeByName(childNodes.item(j), childNodeName);
                }
            }
        }
    }

    private void copyAttributesIfNeeded(Node sourceNode, Element destinationNode) {
        if (!(sourceNode.getNodeName().equals("loopEndlessBrick") || sourceNode.getNodeName().equals("loopEndBrick") || sourceNode.getNodeName().equals("ifLogicElseBrick"))) {
            if (!sourceNode.getNodeName().equals("ifLogicEndBrick")) {
                NamedNodeMap namedNodeMap = sourceNode.getAttributes();
                for (int i = 0; i < namedNodeMap.getLength(); i++) {
                    Attr node = (Attr) namedNodeMap.item(i);
                    destinationNode.setAttributeNS(node.getNamespaceURI(), node.getName(), node.getValue());
                }
            }
        }
    }

    private void convertChildNodeToAttribute(Document originalDocument, String parentNodeName, String childNodeName) {
        NodeList nodeList = originalDocument.getElementsByTagName(parentNodeName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Node childNode = findNodeByName(node, childNodeName);
            if (childNode != null && (node instanceof Element)) {
                ((Element) node).setAttribute(childNodeName, childNode.getTextContent());
                node.removeChild(childNode);
            }
        }
    }

    private void modifyScriptLists(Document originalDocument) {
        NodeList scriptListNodeList = originalDocument.getElementsByTagName("scriptList");
        for (int i = 0; i < scriptListNodeList.getLength(); i++) {
            Node scriptListNode = scriptListNodeList.item(i);
            if (scriptListNode.hasChildNodes()) {
                NodeList scriptListChildNodes = scriptListNode.getChildNodes();
                for (int j = 0; j < scriptListChildNodes.getLength(); j++) {
                    Node scriptNode = scriptListChildNodes.item(j);
                    Element newScriptNode = originalDocument.createElement("script");
                    String scriptName = (String) this.scriptInfoMap.get(scriptNode.getNodeName());
                    if (scriptName != null) {
                        newScriptNode.setAttribute("type", scriptName);
                        copyAttributesIfNeeded(scriptNode, newScriptNode);
                        if (scriptNode.hasChildNodes()) {
                            NodeList scriptNodeChildList = scriptNode.getChildNodes();
                            for (int k = 0; k < scriptNodeChildList.getLength(); k++) {
                                newScriptNode.appendChild(scriptNodeChildList.item(k));
                            }
                        }
                        scriptListNode.replaceChild(newScriptNode, scriptNode);
                    } else {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(scriptNode.getNodeName());
                        stringBuilder.append(": Found no scripts to convert to new structure.\"");
                        Log.e(str, stringBuilder.toString());
                    }
                }
            }
        }
    }

    private void modifyBrickLists(Document originalDocument) {
        NodeList brickListNodeList = originalDocument.getElementsByTagName("brickList");
        for (int i = 0; i < brickListNodeList.getLength(); i++) {
            Node brickListNode = brickListNodeList.item(i);
            if (brickListNode.hasChildNodes()) {
                NodeList brickListChildNodes = brickListNode.getChildNodes();
                for (int j = 0; j < brickListChildNodes.getLength(); j++) {
                    Node brickNode = brickListChildNodes.item(j);
                    Element newBrickNode = originalDocument.createElement("brick");
                    if (brickNode.getNodeName().equals("setGhostEffectBrick")) {
                        originalDocument.renameNode(brickNode, brickNode.getNamespaceURI(), "setTransparencyBrick");
                    }
                    if (brickNode.getNodeName().equals("changeGhostEffectByNBrick")) {
                        originalDocument.renameNode(brickNode, brickNode.getNamespaceURI(), "changeTransparencyByNBrick");
                    }
                    BrickInfo brickInfo = (BrickInfo) this.brickInfoMap.get(brickNode.getNodeName());
                    if (brickInfo != null) {
                        newBrickNode.setAttribute("type", brickInfo.brickClassName);
                        copyAttributesIfNeeded(brickNode, newBrickNode);
                        if (brickNode.hasChildNodes()) {
                            NodeList brickChildNodes = brickNode.getChildNodes();
                            for (int k = 0; k < brickChildNodes.getLength(); k++) {
                                Element brickChild = (Element) brickChildNodes.item(k);
                                if (brickChild.getNodeName().equals("changeGhostEffect")) {
                                    originalDocument.renameNode(brickChild, brickChild.getNamespaceURI(), "changeTransparency");
                                }
                                if (brickInfo.getBrickFieldForOldFieldName(brickChild.getNodeName()) != null) {
                                    handleFormulaNode(originalDocument, brickInfo, newBrickNode, brickChild);
                                } else if (brickChild.getNodeName().equals("userVariable")) {
                                    handleUserVariableNode(newBrickNode, brickChild);
                                } else if (!(brickChild.getNodeName().equals("loopEndBrick") || brickChild.getNodeName().equals("ifElseBrick"))) {
                                    if (!brickChild.getNodeName().equals("ifEndBrick")) {
                                        newBrickNode.appendChild(brickChild);
                                    }
                                }
                            }
                        }
                        brickListNode.replaceChild(newBrickNode, brickNode);
                    } else {
                        String str = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(brickNode.getNodeName());
                        stringBuilder.append(": Found no bricks to convert to new structure.");
                        Log.e(str, stringBuilder.toString());
                    }
                }
            }
        }
    }

    private void handleFormulaNode(Document doc, BrickInfo brickInfo, Element newParentNode, Element oldNode) {
        Node formulaListNode = findNodeByName(newParentNode, "formulaList");
        if (formulaListNode == null) {
            formulaListNode = doc.createElement("formulaList");
            newParentNode.appendChild(formulaListNode);
        }
        Element formulaNode = findNodeByName(oldNode, "formulaTree");
        if (formulaNode == null) {
            formulaNode = doc.createElement("formula");
        } else {
            doc.renameNode(formulaNode, formulaNode.getNamespaceURI(), "formula");
        }
        String category = brickInfo.getBrickFieldForOldFieldName(oldNode.getNodeName()).toString();
        formulaNode.setAttribute("category", category);
        if (category.equals("SPEAK") || category.equals("NOTE")) {
            Element type = doc.createElement("type");
            type.setTextContent("STRING");
            formulaNode.appendChild(type);
            Element value = doc.createElement(FirebaseAnalytics$Param.VALUE);
            value.setTextContent(oldNode.getFirstChild().getTextContent());
            formulaNode.appendChild(value);
        }
        formulaListNode.appendChild(formulaNode);
    }

    private void handleUserVariableNode(Element parentNode, Element userVariableNode) {
        if (!userVariableNode.hasAttribute("reference")) {
            Node nameNode = findNodeByName(userVariableNode, "name");
            if (nameNode != null) {
                String userVariable = nameNode.getTextContent();
                userVariableNode.removeChild(nameNode);
                userVariableNode.setTextContent(userVariable);
            }
        }
        parentNode.appendChild(userVariableNode);
    }

    private void checkReferences(Element node) {
        if (node.hasAttribute("reference")) {
            node.setAttribute("reference", getValidReference(node, node.getAttribute("reference")));
        }
        NodeList childNodes = node.getChildNodes();
        if (childNodes != null) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childNode = childNodes.item(i);
                if (childNode instanceof Element) {
                    checkReferences((Element) childNode);
                }
            }
        }
    }

    private String getValidReference(Node brickNode, String reference) {
        String[] parts = reference.split("/");
        Node node = brickNode;
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals("..")) {
                node = node.getParentNode();
            } else {
                int position = 0;
                String nodeName = parts[i];
                if (parts[i].endsWith("]")) {
                    nodeName = parts[i].substring(0, parts[i].indexOf(91));
                    position = Integer.parseInt(parts[i].substring(parts[i].indexOf(91) + 1, parts[i].indexOf(93))) - 1;
                }
                NodeList childNodes = node.getChildNodes();
                int occurrence = 0;
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Element childNode = (Element) childNodes.item(j);
                    if (childNode.getNodeName().equals(nodeName)) {
                        if (occurrence == position) {
                            node = childNode;
                            break;
                        }
                        occurrence++;
                    } else if (childNode.getNodeName().equals("script") && childNode.getAttribute("type").equals(this.scriptInfoMap.get(nodeName))) {
                        if (occurrence == position) {
                            r10 = new StringBuilder();
                            r10.append("script[");
                            r10.append(j + 1);
                            r10.append("]");
                            parts[i] = r10.toString();
                            node = childNode;
                            break;
                        }
                        occurrence++;
                    } else if (childNode.getNodeName().equals("brick") && childNode.getAttribute("type").equals(((BrickInfo) this.brickInfoMap.get(nodeName)).getBrickClassName())) {
                        if (occurrence == position) {
                            r10 = new StringBuilder();
                            r10.append("brick[");
                            r10.append(j + 1);
                            r10.append("]");
                            parts[i] = r10.toString();
                            node = childNode;
                            break;
                        }
                        occurrence++;
                    }
                }
            }
        }
        return generateReference(parts);
    }

    private String generateReference(String[] referenceParts) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < referenceParts.length; i++) {
            builder.append(referenceParts[i]);
            if (i != referenceParts.length - 1) {
                builder.append('/');
            }
        }
        return builder.toString();
    }
}
