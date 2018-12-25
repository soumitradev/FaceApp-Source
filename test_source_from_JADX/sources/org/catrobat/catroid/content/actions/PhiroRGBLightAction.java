package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.PhiroRGBLightBrick.Eye;
import org.catrobat.catroid.devices.arduino.phiro.Phiro;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class PhiroRGBLightAction extends TemporalAction {
    private static final int MAX_VALUE = 255;
    private static final int MIN_VALUE = 0;
    private static final String TAG = PhiroRGBLightAction.class.getSimpleName();
    private Formula blue;
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private Eye eyeEnum;
    private Formula green;
    private Formula red;
    private Sprite sprite;

    protected void update(float percent) {
        int redValue = updateFormulaValue(this.red);
        int greenValue = updateFormulaValue(this.green);
        int blueValue = updateFormulaValue(this.blue);
        Phiro phiro = (Phiro) this.btService.getDevice(BluetoothDevice.PHIRO);
        if (this.eyeEnum.equals(Eye.LEFT)) {
            phiro.setLeftRGBLightColor(redValue, greenValue, blueValue);
        } else if (this.eyeEnum.equals(Eye.RIGHT)) {
            phiro.setRightRGBLightColor(redValue, greenValue, blueValue);
        } else if (this.eyeEnum.equals(Eye.BOTH)) {
            phiro.setLeftRGBLightColor(redValue, greenValue, blueValue);
            phiro.setRightRGBLightColor(redValue, greenValue, blueValue);
        } else {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error: EyeEnum:");
            stringBuilder.append(this.eyeEnum);
            Log.d(str, stringBuilder.toString());
        }
    }

    private int updateFormulaValue(Formula rgbFormula) {
        int rgbValue;
        try {
            rgbValue = rgbFormula.interpretInteger(this.sprite).intValue();
        } catch (InterpretationException interpretationException) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", interpretationException);
            rgbValue = 0;
        }
        if (rgbValue < 0) {
            return 0;
        }
        if (rgbValue > 255) {
            return 255;
        }
        return rgbValue;
    }

    public void setEyeEnum(Eye eyeEnum) {
        this.eyeEnum = eyeEnum;
    }

    public void setRed(Formula red) {
        this.red = red;
    }

    public void setGreen(Formula green) {
        this.green = green;
    }

    public void setBlue(Formula blue) {
        this.blue = blue;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
