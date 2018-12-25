package org.catrobat.catroid.content.actions;

import android.support.v7.media.MediaRouter.GlobalMediaRouter.CallbackHandler;
import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.PhiroPlayToneBrick.Tone;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class PhiroPlayToneAction extends TemporalAction {
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Formula durationInSeconds;
    private Sprite sprite;
    private Tone toneEnum;

    protected void update(float percent) {
        int durationInterpretation;
        try {
            durationInterpretation = this.durationInSeconds.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            durationInterpretation = 0;
        }
        Phiro phiro = (Phiro) this.btService.getDevice(BluetoothDevice.PHIRO);
        if (phiro != null) {
            switch (this.toneEnum) {
                case DO:
                    phiro.playTone(CallbackHandler.MSG_ROUTE_SELECTED, durationInterpretation);
                    break;
                case RE:
                    phiro.playTone(294, durationInterpretation);
                    break;
                case MI:
                    phiro.playTone(330, durationInterpretation);
                    break;
                case FA:
                    phiro.playTone(349, durationInterpretation);
                    break;
                case SO:
                    phiro.playTone(392, durationInterpretation);
                    break;
                case LA:
                    phiro.playTone(440, durationInterpretation);
                    break;
                case TI:
                    phiro.playTone(494, durationInterpretation);
                    break;
                default:
                    break;
            }
        }
    }

    public void setSelectedTone(Tone toneEnum) {
        this.toneEnum = toneEnum;
    }

    public void setDurationInSeconds(Formula durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
