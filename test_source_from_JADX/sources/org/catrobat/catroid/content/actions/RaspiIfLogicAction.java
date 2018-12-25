package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.devices.raspberrypi.RPiSocketConnection;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

public class RaspiIfLogicAction extends Action {
    private static final String TAG = RaspiIfLogicAction.class.getSimpleName();
    private Action elseAction;
    private Action ifAction;
    private boolean isInitialized = false;
    private int pin;
    private Formula pinNumber;
    private Sprite sprite;

    public void setPinNumber(Formula pinNumber) {
        this.pinNumber = pinNumber;
    }

    protected void begin() {
        Integer pinNumberInterpretation;
        try {
            pinNumberInterpretation = this.pinNumber == null ? Integer.valueOf(0) : this.pinNumber.interpretInteger(this.sprite);
        } catch (InterpretationException interpretationException) {
            pinNumberInterpretation = Integer.valueOf(0);
            Log.e(TAG, "Formula interpretation for this specific Brick failed.", interpretationException);
        }
        this.pin = pinNumberInterpretation.intValue();
    }

    public boolean act(float delta) {
        if (!this.isInitialized) {
            begin();
            this.isInitialized = true;
        }
        if (readIfConditionValue()) {
            return this.ifAction.act(delta);
        }
        return this.elseAction.act(delta);
    }

    protected boolean readIfConditionValue() {
        RPiSocketConnection connection = RaspberryPiService.getInstance().connection;
        try {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RPi get ");
            stringBuilder.append(this.pin);
            Log.d(str, stringBuilder.toString());
            return connection.getPin(this.pin);
        } catch (Exception e) {
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("RPi: exception during getPin: ");
            stringBuilder2.append(e);
            Log.e(str2, stringBuilder2.toString());
            return false;
        }
    }

    public void restart() {
        this.ifAction.restart();
        this.elseAction.restart();
        this.isInitialized = false;
        super.restart();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setIfAction(Action ifAction) {
        this.ifAction = ifAction;
    }

    public void setElseAction(Action elseAction) {
        this.elseAction = elseAction;
    }

    public void setActor(Actor actor) {
        super.setActor(actor);
        this.ifAction.setActor(actor);
        this.elseAction.setActor(actor);
    }
}
