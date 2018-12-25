package org.catrobat.catroid.pocketmusic.note;

public enum MusicalBeat {
    BEAT_3_4(3, 4, NoteLength.QUARTER),
    BEAT_4_4(4, 4, NoteLength.QUARTER),
    BEAT_16_16(16, 16, NoteLength.SIXTEENTH);
    
    private final int bottomNumber;
    private final NoteLength noteLength;
    private final int topNumber;

    private MusicalBeat(int topNumnber, int bottomNumber, NoteLength noteLength) {
        this.topNumber = topNumnber;
        this.bottomNumber = bottomNumber;
        this.noteLength = noteLength;
    }

    public int getTopNumber() {
        return this.topNumber;
    }

    public int getBottomNumber() {
        return this.bottomNumber;
    }

    public NoteLength getNoteLength() {
        return this.noteLength;
    }

    public static MusicalBeat convertToMusicalBeat(int topNumber, int bottomNumber) {
        for (MusicalBeat beat : values()) {
            if (beat.getTopNumber() == topNumber && beat.getBottomNumber() == bottomNumber) {
                return beat;
            }
        }
        return BEAT_4_4;
    }
}
