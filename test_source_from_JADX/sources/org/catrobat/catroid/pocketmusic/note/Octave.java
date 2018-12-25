package org.catrobat.catroid.pocketmusic.note;

public enum Octave {
    CONTRA_OCTAVE(new NoteName[]{NoteName.C1, NoteName.C1S, NoteName.D1, NoteName.D1S, NoteName.E1, NoteName.F1, NoteName.F1S, NoteName.G1, NoteName.G1S, NoteName.A1, NoteName.A1S, NoteName.B1}),
    GREAT_OCTAVE(new NoteName[]{NoteName.C2, NoteName.C2S, NoteName.D2, NoteName.D2S, NoteName.E2, NoteName.F2, NoteName.F2S, NoteName.G2, NoteName.G2S, NoteName.A2, NoteName.A2S, NoteName.B2}),
    SMALL_OCTAVE(new NoteName[]{NoteName.C3, NoteName.C3S, NoteName.D3, NoteName.D3S, NoteName.E3, NoteName.F3, NoteName.F3S, NoteName.G3, NoteName.G3S, NoteName.A3, NoteName.A3S, NoteName.B3}),
    ONE_LINE_OCTAVE(new NoteName[]{NoteName.C4, NoteName.C4S, NoteName.D4, NoteName.D4S, NoteName.E4, NoteName.F4, NoteName.F4S, NoteName.G4, NoteName.G4S, NoteName.A4, NoteName.A4S, NoteName.B4}),
    TWO_LINE_OCTAVE(new NoteName[]{NoteName.C5, NoteName.C5S, NoteName.D5, NoteName.D5S, NoteName.E5, NoteName.F5, NoteName.F5S, NoteName.G5, NoteName.G5S, NoteName.A5, NoteName.A5S, NoteName.B5}),
    THREE_LINE_OCTAVE(new NoteName[]{NoteName.C6, NoteName.C6S, NoteName.D6, NoteName.D6S, NoteName.E6, NoteName.F6, NoteName.F6S, NoteName.G6, NoteName.G6S, NoteName.A6, NoteName.A6S, NoteName.B6}),
    FOUR_LINE_OCTAVE(new NoteName[]{NoteName.C7, NoteName.C7S, NoteName.D7, NoteName.D7S, NoteName.E7, NoteName.F7, NoteName.F7S, NoteName.G7, NoteName.G7S, NoteName.A7, NoteName.A7S, NoteName.B7});
    
    public static final Octave DEFAULT_OCTAVE = null;
    public static final int NUMBER_OF_UNSIGNED_HALF_TONE_STEPS_PER_OCTAVE = 7;
    private NoteName[] noteNames;

    static {
        DEFAULT_OCTAVE = ONE_LINE_OCTAVE;
    }

    private Octave(NoteName[] noteNames) {
        this.noteNames = (NoteName[]) noteNames.clone();
    }

    public NoteName[] getNoteNames() {
        return (NoteName[]) this.noteNames.clone();
    }

    public Octave next() {
        int index = ordinal() + 1;
        if (index >= values().length) {
            index--;
        }
        return values()[index];
    }

    public Octave previous() {
        int index = ordinal() - 1;
        if (index < 0) {
            index++;
        }
        return values()[index];
    }
}
