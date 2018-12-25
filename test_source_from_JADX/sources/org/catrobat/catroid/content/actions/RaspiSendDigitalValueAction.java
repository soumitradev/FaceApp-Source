package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.raspberrypi.RPiSocketConnection;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class RaspiSendDigitalValueAction extends TemporalAction {
    private static final String TAG = RaspiSendDigitalValueAction.class.getSimpleName();
    private int pin;
    private Formula pinNumber;
    private Formula pinValue;
    private Sprite sprite;
    private boolean value;

    protected void begin() {
        Integer pinNumberInterpretation;
        boolean z = false;
        try {
            pinNumberInterpretation = this.pinNumber == null ? Integer.valueOf(0) : this.pinNumber.interpretInteger(this.sprite);
        } catch (InterpretationException interpretationException) {
            Integer pinNumberInterpretation2 = Integer.valueOf(0);
            Log.d(TAG, "Formula interpretation for this specific Brick failed.", interpretationException);
            pinNumberInterpretation = pinNumberInterpretation2;
        }
        try {
            if (this.pinValue != null && this.pinValue.interpretBoolean(this.sprite).booleanValue()) {
                z = true;
            }
        } catch (InterpretationException interpretationException2) {
            Log.d(TAG, "Formula interpretation for this specific Brick failed.", interpretationException2);
            z = false;
        }
        this.pin = pinNumberInterpretation.intValue();
        this.value = z;
    }

    protected void update(float percent) {
        RPiSocketConnection connection = RaspberryPiService.getInstance().connection;
        try {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RPi set ");
            stringBuilder.append(this.pin);
            stringBuilder.append(" to ");
            stringBuilder.append(this.value);
            Log.d(str, stringBuilder.toString());
            connection.setPin(this.pin, this.value);
        } catch (Exception e) {
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("RPi: exception during setPin: ");
            stringBuilder2.append(e);
            Log.e(str2, stringBuilder2.toString());
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
