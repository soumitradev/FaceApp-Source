package org.catrobat.catroid.pocketmusic.note.trackgrid;

import android.util.SparseArray;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.Track;

public final class TrackGridToTrackConverter {
    private TrackGridToTrackConverter() {
    }

    public static Track convertTrackGridToTrack(TrackGrid trackGrid, int beatsPerMinute) {
        int i = beatsPerMinute;
        Track track = new Track(trackGrid.getKey(), trackGrid.getInstrument());
        Iterator it = trackGrid.getGridRows().iterator();
        while (it.hasNext()) {
            Iterator it2;
            GridRow gridRow = (GridRow) it.next();
            SparseArray<List<GridRowPosition>> positionsSparseArray = gridRow.getGridRowPositions();
            int i2 = 0;
            while (i2 < positionsSparseArray.size()) {
                GridRow gridRow2;
                int key = positionsSparseArray.keyAt(i2);
                for (GridRowPosition position : (List) positionsSparseArray.get(key)) {
                    int i3 = i2;
                    long startTicsInTrack = position.getStartTicksInTrack() + ((long) (((trackGrid.getBeat().getTopNumber() * key) * i) * 8));
                    long noteLength = position.getNoteLength().toTicks(i);
                    track.addNoteEvent(startTicsInTrack, new NoteEvent(gridRow.getNoteName(), true));
                    it2 = it;
                    gridRow2 = gridRow;
                    track.addNoteEvent(startTicsInTrack + noteLength, new NoteEvent(gridRow.getNoteName(), false));
                    i2 = i3;
                    it = it2;
                    gridRow = gridRow2;
                    i = beatsPerMinute;
                }
                it2 = it;
                gridRow2 = gridRow;
                i2++;
                i = beatsPerMinute;
            }
            it2 = it;
            i = beatsPerMinute;
        }
        return track;
    }
}
