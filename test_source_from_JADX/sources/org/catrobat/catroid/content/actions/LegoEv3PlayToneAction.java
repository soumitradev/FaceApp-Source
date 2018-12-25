package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class LegoEv3PlayToneAction extends TemporalAction {
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Formula durationInSeconds;
    private Formula hertz;
    private Sprite sprite;
    private Formula volumeInPercent;

    protected void update(float percent) {
        int hertzInterpretation;
        float durationInterpretation;
        int volumeInterpretation;
        try {
            hertzInterpretation = this.hertz.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            hertzInterpretation = 0;
        }
        try {
            durationInterpretation = this.durationInSeconds.interpretFloat(this.sprite).floatValue();
        } catch (InterpretationException interpretationException2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException2);
            durationInterpretation = 0.0f;
        }
        try {
            volumeInterpretation = this.volumeInPercent.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException interpretationException3) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException3);
            volumeInterpretation = 0;
        }
        LegoEV3 ev3 = (LegoEV3) this.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (ev3 != null) {
            ev3.playTone(hertzInterpretation * 100, (int) (1148846080 * durationInterpretation), volumeInterpretation);
        }
    }

    public void setHertz(Formula hertz) {
        this.hertz = hertz;
    }

    public void setDurationInSeconds(Formula durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setVolumeInPercent(Formula volumeInPercent) {
        this.volumeInPercent = volumeInPercent;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
