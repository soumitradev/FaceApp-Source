package com.pdrogfer.mididroid.util;

import com.pdrogfer.mididroid.event.MidiEvent;

public interface MidiEventListener {
    void onEvent(MidiEvent midiEvent, long j);

    void onStart(boolean z);

    void onStop(boolean z);
}
