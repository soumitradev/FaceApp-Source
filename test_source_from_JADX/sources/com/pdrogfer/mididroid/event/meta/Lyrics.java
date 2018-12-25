package com.pdrogfer.mididroid.event.meta;

public class Lyrics extends TextualMetaEvent {
    public Lyrics(long tick, long delta, String lyric) {
        super(tick, delta, 5, lyric);
    }

    public void setLyric(String t) {
        setText(t);
    }

    public String getLyric() {
        return getText();
    }
}
