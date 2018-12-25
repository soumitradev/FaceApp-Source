package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.mindstorms.nxt.LegoNXT;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class LegoNxtPlayToneAction extends TemporalAction {
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Formula durationInSeconds;
    private Formula hertz;
    private Sprite sprite;

    protected void update(float percent) {
        int hertzInterpretation;
        float durationInterpretation;
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
        LegoNXT nxt = (LegoNXT) this.btService.getDevice(BluetoothDevice.LEGO_NXT);
        if (nxt != null) {
            nxt.playTone(hertzInterpretation * 100, (int) (1148846080 * durationInterpretation));
        }
    }

    public void setHertz(Formula hertz) {
        this.hertz = hertz;
    }

    public void setDurationInSeconds(Formula durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
