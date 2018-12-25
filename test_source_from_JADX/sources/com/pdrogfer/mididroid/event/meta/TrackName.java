package com.pdrogfer.mididroid.event.meta;

public class TrackName extends TextualMetaEvent {
    public TrackName(long tick, long delta, String name) {
        super(tick, delta, 3, name);
    }

    public void setName(String name) {
        setText(name);
    }

    public String getTrackName() {
        return getText();
    }
}
