package com.pdrogfer.mididroid.event.meta;

public class Text extends TextualMetaEvent {
    public Text(long tick, long delta, String text) {
        super(tick, delta, 1, text);
    }

    public void setText(String t) {
        super.setText(t);
    }

    public String getText() {
        return super.getText();
    }
}
