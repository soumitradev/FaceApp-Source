package org.catrobat.catroid.pocketmusic.note.midi;

import com.pdrogfer.mididroid.event.ChannelEvent;
import com.pdrogfer.mididroid.event.NoteOff;
import com.pdrogfer.mididroid.event.NoteOn;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;

public class NoteEventToMidiEventConverter {
    private static final int DEFAULT_NOISE = 64;
    private static final int DEFAULT_SILENT = 0;

    public ChannelEvent convertNoteEvent(long tick, NoteEvent noteEvent, int channel) {
        if (noteEvent.isNoteOn()) {
            return new NoteOn(tick, channel, noteEvent.getNoteName().getMidi(), 64);
        }
        return new NoteOff(tick, channel, noteEvent.getNoteName().getMidi(), 0);
    }
}
