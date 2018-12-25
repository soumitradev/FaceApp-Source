package org.catrobat.catroid.pocketmusic.note.trackgrid;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.pocketmusic.mididriver.MidiNotePlayer;
import org.catrobat.catroid.pocketmusic.mididriver.MidiRunnable;
import org.catrobat.catroid.pocketmusic.mididriver.MidiSignals;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.ui.PianoView;

public class TrackGrid {
    private static final int INDEX_TO_COUNT_OFFSET = 1;
    private static final int SOUND_OFFSET = 10;
    private final MusicalBeat beat;
    private final List<GridRow> gridRows;
    private Handler handler;
    private final MusicalInstrument instrument;
    private final MusicalKey key;
    private MidiNotePlayer midiDriver;
    private List<MidiRunnable> playRunnables = new ArrayList();

    public TrackGrid(MusicalKey key, MusicalInstrument instrument, MusicalBeat beat, List<GridRow> gridRows) {
        this.key = key;
        this.instrument = instrument;
        this.beat = beat;
        this.gridRows = gridRows;
        this.handler = new Handler(Looper.getMainLooper());
        this.midiDriver = new MidiNotePlayer();
    }

    public MusicalKey getKey() {
        return this.key;
    }

    public MusicalInstrument getInstrument() {
        return this.instrument;
    }

    public MusicalBeat getBeat() {
        return this.beat;
    }

    public List<GridRow> getGridRows() {
        return this.gridRows;
    }

    public int hashCode() {
        return (31 * ((31 * ((31 * ((31 * 172) + this.key.hashCode())) + this.instrument.hashCode())) + this.beat.hashCode())) + this.gridRows.hashCode();
    }

    public boolean equals(Object o) {
        TrackGrid reference = (TrackGrid) o;
        return reference.gridRows.containsAll(this.gridRows) && this.gridRows.containsAll(reference.gridRows) && reference.beat.equals(this.beat) && reference.instrument.equals(this.instrument) && reference.key.equals(this.key);
    }

    public GridRow getGridRowForNoteName(NoteName noteName) {
        for (GridRow gridRow : this.gridRows) {
            if (gridRow.getNoteName().equals(noteName)) {
                return gridRow;
            }
        }
        return null;
    }

    public void updateGridRowPosition(NoteName noteName, int columnIndex, NoteLength noteLength, int tactIndex, boolean toggled) {
        TrackGrid trackGrid = this;
        int i = columnIndex;
        int i2 = tactIndex;
        GridRow gridRow = getGridRowForNoteName(noteName);
        if (gridRow == null) {
            gridRow = createNewGridRow(noteName);
        }
        if (gridRow.getGridRowPositions().indexOfKey(i2) < 0) {
            appendNoteListAtPosition(gridRow.getGridRowPositions(), i2);
        }
        List<GridRowPosition> currentGridRowPositions = (List) gridRow.getGridRowPositions().get(i2);
        int indexInList = GridRowPosition.getGridRowPositionIndexInList(currentGridRowPositions, i);
        NoteLength noteLength2;
        if (!toggled) {
            noteLength2 = noteLength;
            if (indexInList >= 0) {
                currentGridRowPositions.remove(indexInList);
                Log.d("TrackGrid", String.format("Removed GridRowPosition with name %s on Tact %d with columnIndex %d and noteLength %s.", new Object[]{noteName.name(), Integer.valueOf(tactIndex), Integer.valueOf(columnIndex), noteLength.toString()}));
                if (currentGridRowPositions.isEmpty()) {
                    gridRow.getGridRowPositions().remove(i2);
                }
            }
        } else if (indexInList == -1) {
            Log.d("TrackGrid", String.format("Added GridRowPosition with name %s on Tact %d with columnIndex %d and noteLength %s. ", new Object[]{noteName.name(), Integer.valueOf(tactIndex), Integer.valueOf(columnIndex), noteLength.toString()}));
            currentGridRowPositions.add(new GridRowPosition(i, noteLength));
            long playLength = NoteLength.QUARTER.toMilliseconds(60);
            trackGrid.handler.post(new MidiRunnable(MidiSignals.NOTE_ON, noteName, playLength, trackGrid.handler, trackGrid.midiDriver, null));
        } else {
            noteLength2 = noteLength;
        }
    }

    private void appendNoteListAtPosition(SparseArray<List<GridRowPosition>> array, int tactIndex) {
        array.append(tactIndex, new ArrayList(this.beat.getTopNumber()));
    }

    private GridRow createNewGridRow(NoteName noteName) {
        GridRow gridRow = new GridRow(noteName, new SparseArray());
        this.gridRows.add(gridRow);
        return gridRow;
    }

    public int getTactCount() {
        int tactcount = 0;
        for (GridRow gridRow : this.gridRows) {
            SparseArray<List<GridRowPosition>> gridRowPositions = gridRow.getGridRowPositions();
            for (int i = 0; i < gridRowPositions.size(); i++) {
                int tactForGridRow = gridRowPositions.keyAt(i);
                if (tactForGridRow > tactcount) {
                    tactcount = tactForGridRow;
                }
            }
        }
        return tactcount + 1;
    }

    public void startPlayback(PianoView pianoView) {
        this.playRunnables.clear();
        long playLength = NoteLength.QUARTER.toMilliseconds(60);
        long currentTime = SystemClock.uptimeMillis();
        Iterator it = this.gridRows.iterator();
        while (it.hasNext()) {
            Iterator it2;
            GridRow row = (GridRow) it.next();
            for (int i = 0; i < row.getGridRowPositions().size(); i++) {
                int tactIndex = row.getGridRowPositions().keyAt(i);
                long tactOffset = (4 * playLength) * ((long) tactIndex);
                for (GridRowPosition position : (List) row.getGridRowPositions().get(tactIndex)) {
                    it2 = it;
                    MidiRunnable runnable = new MidiRunnable(MidiSignals.NOTE_ON, row.getNoteName(), playLength - 10, r0.handler, r0.midiDriver, pianoView);
                    long currentTime2 = currentTime;
                    r0.handler.postAtTime(runnable, ((currentTime + tactOffset) + (((long) position.getColumnStartIndex()) * playLength)) + 10);
                    r0.playRunnables.add(runnable);
                    it = it2;
                    currentTime = currentTime2;
                }
                it2 = it;
            }
            it2 = it;
        }
    }

    public void stopPlayback(PianoView pianoView) {
        for (MidiRunnable r : this.playRunnables) {
            this.handler.removeCallbacks(r);
            this.handler.post(new MidiRunnable(MidiSignals.NOTE_OFF, r.getNoteName(), 0, this.handler, this.midiDriver, pianoView));
        }
        this.playRunnables.clear();
    }
}
