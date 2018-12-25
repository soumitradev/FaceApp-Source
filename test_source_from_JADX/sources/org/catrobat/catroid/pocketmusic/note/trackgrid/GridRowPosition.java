package org.catrobat.catroid.pocketmusic.note.trackgrid;

import java.util.List;
import org.catrobat.catroid.pocketmusic.note.NoteLength;

public class GridRowPosition {
    private int columnStartIndex;
    private final NoteLength noteLength;

    public GridRowPosition(int columnStartIndex, NoteLength noteLength) {
        this.columnStartIndex = columnStartIndex;
        this.noteLength = noteLength;
    }

    public int getColumnStartIndex() {
        return this.columnStartIndex;
    }

    public NoteLength getNoteLength() {
        return this.noteLength;
    }

    public void setColumnStartIndex(int columnStartIndex) {
        this.columnStartIndex = columnStartIndex;
    }

    public long getStartTicksInTrack() {
        return ((long) this.columnStartIndex) * this.noteLength.toTicks(60);
    }

    public int hashCode() {
        return (31 * ((31 * 23) + this.columnStartIndex)) + this.noteLength.hashCode();
    }

    public boolean equals(Object o) {
        GridRowPosition reference = (GridRowPosition) o;
        return reference.columnStartIndex == this.columnStartIndex && reference.noteLength.equals(this.noteLength);
    }

    public static int getGridRowPositionIndexInList(List<GridRowPosition> gridRowPositions, int columnStartIndex) {
        for (int i = 0; i < gridRowPositions.size(); i++) {
            if (((GridRowPosition) gridRowPositions.get(i)).getColumnStartIndex() == columnStartIndex) {
                return i;
            }
        }
        return -1;
    }
}
