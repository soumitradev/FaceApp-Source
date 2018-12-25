package org.catrobat.catroid.pocketmusic.note.trackgrid;

import android.util.SparseArray;
import java.util.Collection;
import java.util.List;
import org.catrobat.catroid.pocketmusic.note.NoteName;

public class GridRow {
    private final SparseArray<List<GridRowPosition>> gridRowPositions;
    private final NoteName noteName;

    public GridRow(NoteName noteName, SparseArray<List<GridRowPosition>> gridRowPositions) {
        this.noteName = noteName;
        this.gridRowPositions = gridRowPositions;
    }

    public NoteName getNoteName() {
        return this.noteName;
    }

    public SparseArray<List<GridRowPosition>> getGridRowPositions() {
        return this.gridRowPositions;
    }

    public int hashCode() {
        int hashCode = (31 * 21) + this.noteName.hashCode();
        for (int i = 0; i < getGridRowPositions().size(); i++) {
            hashCode += ((List) getGridRowPositions().valueAt(i)).hashCode() * 31;
        }
        return hashCode;
    }

    public boolean equals(Object o) {
        if (!(o instanceof GridRow)) {
            return false;
        }
        GridRow reference = (GridRow) o;
        if (!reference.noteName.equals(this.noteName) || reference.getGridRowPositions().size() != getGridRowPositions().size()) {
            return false;
        }
        int i = 0;
        while (i < reference.getGridRowPositions().size()) {
            if (reference.getGridRowPositions().keyAt(i) != getGridRowPositions().keyAt(i) || ((List) reference.getGridRowPositions().valueAt(i)).size() != ((List) getGridRowPositions().valueAt(i)).size() || !((List) reference.getGridRowPositions().valueAt(i)).containsAll((Collection) getGridRowPositions().valueAt(i)) || !((List) getGridRowPositions().valueAt(i)).containsAll((Collection) reference.getGridRowPositions().valueAt(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    public List<GridRowPosition> gridRowForTact(int tactPosition) {
        return (List) this.gridRowPositions.get(tactPosition);
    }
}
