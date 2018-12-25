package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.bricks.LegoEv3SetLedBrick.LedStatus;
import org.catrobat.catroid.devices.mindstorms.ev3.LegoEV3;

public class LegoEv3SetLedAction extends TemporalAction {
    private BluetoothDeviceService btService = ((BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE));
    private LedStatus ledStatusEnum;

    protected void update(float percent) {
        LegoEV3 ev3 = (LegoEV3) this.btService.getDevice(BluetoothDevice.LEGO_EV3);
        if (ev3 != null) {
            int ledStatus = 0;
            switch (this.ledStatusEnum) {
                case LED_OFF:
                    ledStatus = 0;
                    break;
                case LED_GREEN:
                    ledStatus = 1;
                    break;
                case LED_RED:
                    ledStatus = 2;
                    break;
                case LED_ORANGE:
                    ledStatus = 3;
                    break;
                case LED_GREEN_FLASHING:
                    ledStatus = 4;
                    break;
                case LED_RED_FLASHING:
                    ledStatus = 5;
                    break;
                case LED_ORANGE_FLASHING:
                    ledStatus = 6;
                    break;
                case LED_GREEN_PULSE:
                    ledStatus = 7;
                    break;
                case LED_RED_PULSE:
                    ledStatus = 8;
                    break;
                case LED_ORANGE_PULSE:
                    ledStatus = 9;
                    break;
                default:
                    break;
            }
            ev3.setLed(ledStatus);
        }
    }

    public void setLedStatusEnum(LedStatus ledStatusEnum) {
        this.ledStatusEnum = ledStatusEnum;
    }
}
