package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.arduino.Arduino;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class ArduinoSendPWMValueAction extends TemporalAction {
    private int pin;
    private Formula pinNumber;
    private Formula pinValue;
    private boolean restart = false;
    private Sprite sprite;
    private int value;

    protected void begin() {
        Integer pinNumberInterpretation;
        try {
            pinNumberInterpretation = this.pinNumber == null ? Integer.valueOf(0) : this.pinNumber.interpretInteger(this.sprite);
        } catch (InterpretationException interpretationException) {
            Integer pinNumberInterpretation2 = Integer.valueOf(0);
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            pinNumberInterpretation = pinNumberInterpretation2;
        }
        try {
            pinNumberInterpretation2 = this.pinValue == null ? Integer.valueOf(0) : this.pinValue.interpretInteger(this.sprite);
        } catch (InterpretationException interpretationException2) {
            Integer pinValueInterpretation = Integer.valueOf(0);
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException2);
            pinNumberInterpretation2 = pinValueInterpretation;
        }
        if (!this.restart && pinNumberInterpretation.intValue() >= 0 && pinNumberInterpretation.intValue() < 14) {
            this.pin = pinNumberInterpretation.intValue();
            this.value = pinNumberInterpretation2.intValue();
        }
        this.restart = false;
    }

    protected void update(float percent) {
        Arduino arduino = (Arduino) ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE)).getDevice(BluetoothDevice.ARDUINO);
        if (arduino != null) {
            arduino.setAnalogArduinoPin(this.pin, this.value);
        }
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setPinNumber(Formula newPinNumber) {
        this.pinNumber = newPinNumber;
    }

    public void setPinValue(Formula newpinValue) {
        this.pinValue = newpinValue;
    }
}
