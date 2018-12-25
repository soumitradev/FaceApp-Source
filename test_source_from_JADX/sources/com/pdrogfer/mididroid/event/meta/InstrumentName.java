package com.pdrogfer.mididroid.event.meta;

public class InstrumentName extends TextualMetaEvent {
    public InstrumentName(long tick, long delta, String name) {
        super(tick, delta, 4, name);
    }

    public void setName(String name) {
        setText(name);
    }

    public String getName() {
        return getText();
    }
}
