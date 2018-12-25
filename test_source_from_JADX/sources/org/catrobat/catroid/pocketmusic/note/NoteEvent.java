package org.catrobat.catroid.pocketmusic.note;

import java.io.Serializable;

public class NoteEvent implements Serializable {
    private static final long serialVersionUID = 7483022549872527955L;
    private NoteName noteName;
    private boolean noteOn;

    public NoteEvent(NoteName noteName, boolean noteOn) {
        this.noteName = noteName;
        this.noteOn = noteOn;
    }

    public NoteEvent(NoteEvent noteEvent) {
        this.noteName = noteEvent.getNoteName();
        this.noteOn = noteEvent.isNoteOn();
    }

    public NoteName getNoteName() {
        return this.noteName;
    }

    public boolean isNoteOn() {
        return this.noteOn;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            if (obj instanceof NoteEvent) {
                NoteEvent noteEvent = (NoteEvent) obj;
                if (this.noteName.equals(noteEvent.getNoteName()) && this.noteOn == noteEvent.isNoteOn()) {
                    z = true;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (31 * ((31 * 15) + this.noteName.hashCode())) + this.noteOn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[NoteEvent] noteName= ");
        stringBuilder.append(this.noteName);
        stringBuilder.append(" noteOn=");
        stringBuilder.append(this.noteOn);
        return stringBuilder.toString();
    }
}
