package org.catrobat.catroid.pocketmusic.note.trackgrid;

import android.util.SparseArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.Track;

public final class TrackToTrackGridConverter {
    private TrackToTrackGridConverter() {
    }

    public static TrackGrid convertTrackToTrackGrid(Track track, MusicalBeat beat, int beatsPerMinute) {
        Map<NoteName, Long> openNotes;
        NoteLength minNoteLength;
        int i = beatsPerMinute;
        Map<NoteName, Long> openNotes2 = new HashMap();
        Map<NoteName, GridRow> gridRows = new HashMap();
        NoteLength minNoteLength2 = beat.getNoteLength();
        for (Long tick : track.getSortedTicks()) {
            for (NoteEvent noteEvent : track.getNoteEventsForTick(tick.longValue())) {
                NoteName noteName = noteEvent.getNoteName();
                if (gridRows.get(noteName) == null) {
                    gridRows.put(noteName, new GridRow(noteName, new SparseArray()));
                }
                if (noteEvent.isNoteOn()) {
                    openNotes2.put(noteName, tick);
                    openNotes = openNotes2;
                    minNoteLength = minNoteLength2;
                } else {
                    long openTick = ((Long) openNotes2.get(noteName)).longValue();
                    NoteLength length = NoteLength.getNoteLengthFromTickDuration(tick.longValue() - openTick, i);
                    int columnStartIndex = (int) (openTick / minNoteLength2.toTicks(i));
                    int startBeatIndex = columnStartIndex / beat.getTopNumber();
                    openNotes = openNotes2;
                    i = ((((int) ((tick.longValue() - openTick) / minNoteLength2.toTicks(i))) + columnStartIndex) - 1) / beat.getTopNumber();
                    openNotes2 = new GridRowPosition(columnStartIndex % beat.getTopNumber(), length);
                    int i2 = startBeatIndex;
                    while (i2 <= i) {
                        int endBeatIndex = i;
                        if (((GridRow) gridRows.get(noteName)).getGridRowPositions().get(i2) == null) {
                            minNoteLength = minNoteLength2;
                            ((GridRow) gridRows.get(noteName)).getGridRowPositions().put(i2, new ArrayList());
                        } else {
                            minNoteLength = minNoteLength2;
                        }
                        ((List) ((GridRow) gridRows.get(noteName)).getGridRowPositions().get(i2)).add(openNotes2);
                        i2++;
                        i = endBeatIndex;
                        minNoteLength2 = minNoteLength;
                    }
                    minNoteLength = minNoteLength2;
                }
                openNotes2 = openNotes;
                minNoteLength2 = minNoteLength;
                i = beatsPerMinute;
            }
            minNoteLength = minNoteLength2;
            i = beatsPerMinute;
        }
        Track track2 = track;
        openNotes = openNotes2;
        minNoteLength = minNoteLength2;
        return new TrackGrid(track.getKey(), track.getInstrument(), beat, new ArrayList(gridRows.values()));
    }
}
