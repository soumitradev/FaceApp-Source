package org.catrobat.catroid.pocketmusic.note;

public enum MusicalKey {
    BASS(NoteName.D3),
    VIOLIN(NoteName.B4);
    
    private NoteName noteNameOnMiddleLine;

    private MusicalKey(NoteName noteNameOnMiddleLine) {
        this.noteNameOnMiddleLine = noteNameOnMiddleLine;
    }

    public NoteName getNoteNameOnMiddleLine() {
        return this.noteNameOnMiddleLine;
    }
}
