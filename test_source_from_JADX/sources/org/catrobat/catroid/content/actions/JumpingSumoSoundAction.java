package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.parrot.arsdk.arcommands.ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM;
import com.parrot.arsdk.arcontroller.ARDeviceController;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.JumpingSumoSoundBrick.Sounds;
import org.catrobat.catroid.drone.jumpingsumo.JumpingSumoDeviceController;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class JumpingSumoSoundAction extends TemporalAction {
    private JumpingSumoDeviceController controller;
    private ARDeviceController deviceController;
    private Sounds soundType;
    private Sprite sprite;
    private Formula volumeInPercent;

    protected void update(float percent) {
        int normVolume;
        this.controller = JumpingSumoDeviceController.getInstance();
        this.deviceController = this.controller.getDeviceController();
        try {
            normVolume = this.volumeInPercent.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            normVolume = 0;
        }
        if (this.deviceController != null) {
            switch (this.soundType) {
                case DEFAULT:
                    this.deviceController.getFeatureJumpingSumo().sendAudioSettingsMasterVolume((byte) normVolume);
                    this.deviceController.getFeatureJumpingSumo().sendAudioSettingsTheme(ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM.ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_DEFAULT);
                    return;
                case ROBOT:
                    this.deviceController.getFeatureJumpingSumo().sendAudioSettingsMasterVolume((byte) normVolume);
                    this.deviceController.getFeatureJumpingSumo().sendAudioSettingsTheme(ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM.ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ROBOT);
                    return;
                case INSECT:
                    this.deviceController.getFeatureJumpingSumo().sendAudioSettingsMasterVolume((byte) normVolume);
                    this.deviceController.getFeatureJumpingSumo().sendAudioSettingsTheme(ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM.ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_INSECT);
                    return;
                case MONSTER:
                    this.deviceController.getFeatureJumpingSumo().sendAudioSettingsMasterVolume((byte) normVolume);
                    this.deviceController.getFeatureJumpingSumo().sendAudioSettingsTheme(ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_ENUM.ARCOMMANDS_JUMPINGSUMO_AUDIOSETTINGS_THEME_THEME_MONSTER);
                    return;
                default:
                    return;
            }
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setVolume(Formula volume) {
        this.volumeInPercent = volume;
    }

    public void setSoundType(Sounds soundType) {
        this.soundType = soundType;
    }
}
