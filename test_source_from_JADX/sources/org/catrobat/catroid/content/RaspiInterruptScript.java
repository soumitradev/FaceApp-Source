package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenRaspiPinChangedBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.RaspiEventId;

public class RaspiInterruptScript extends Script {
    private static final long serialVersionUID = 1;
    private String eventValue;
    private String pin;

    public RaspiInterruptScript(String pin, String eventValue) {
        this.pin = pin;
        this.eventValue = eventValue;
    }

    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenRaspiPinChangedBrick(this);
        }
        return this.scriptBrick;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public String getPin() {
        return this.pin;
    }

    public String getEventValue() {
        return this.eventValue;
    }

    public EventId createEventId(Sprite sprite) {
        return new RaspiEventId(this.pin, this.eventValue);
    }
}
