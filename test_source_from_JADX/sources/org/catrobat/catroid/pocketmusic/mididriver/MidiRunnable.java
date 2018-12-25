package org.catrobat.catroid.pocketmusic.mididriver;

import android.os.Handler;
import com.pdrogfer.mididroid.event.meta.MetaEvent;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.ui.PianoView;

public class MidiRunnable implements Runnable {
    private final long duration;
    private final Handler handler;
    private final MidiNotePlayer midiNotePlayer;
    private final NoteName noteName;
    private final PianoView pianoView;
    private final MidiSignals signal;

    public MidiRunnable(MidiSignals signal, NoteName noteName, long duration, Handler handler, MidiNotePlayer midiNotePlayer, PianoView pianoView) {
        this.signal = signal;
        this.noteName = noteName;
        this.duration = duration;
        this.handler = handler;
        this.midiNotePlayer = midiNotePlayer;
        this.pianoView = pianoView;
    }

    public void run() {
        this.midiNotePlayer.sendMidi(this.signal.getSignalByte(), this.noteName.getMidi(), MetaEvent.SEQUENCER_SPECIFIC);
        if (this.pianoView != null) {
            this.pianoView.setButtonColor(this.noteName, MidiSignals.NOTE_ON.equals(this.signal));
        }
        if (this.signal.equals(MidiSignals.NOTE_ON)) {
            this.handler.postDelayed(new MidiRunnable(MidiSignals.NOTE_OFF, this.noteName, this.duration, this.handler, this.midiNotePlayer, this.pianoView), this.duration);
        }
    }

    public NoteName getNoteName() {
        return this.noteName;
    }
}
