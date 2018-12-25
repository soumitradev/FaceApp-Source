package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.trackgrid.TrackGrid;

public class TrackView extends TableLayout {
    private static final int[] BLACK_KEY_INDICES = new int[]{1, 3, 6, 8, 10};
    public static final int ROW_COUNT = 13;
    private int tactPosition;
    private TrackGrid trackGrid;
    private List<TrackRowView> trackRowViews;

    public TrackView(Context context, AttributeSet attrs) {
        this(context, attrs, new TrackGrid(MusicalKey.VIOLIN, MusicalInstrument.ACCORDION, MusicalBeat.BEAT_4_4, new ArrayList()));
    }

    public TrackView(Context context, TrackGrid trackGrid) {
        this(context, null, trackGrid);
    }

    public TrackView(Context context, AttributeSet attrs, TrackGrid trackGrid) {
        super(context, attrs);
        this.trackRowViews = new ArrayList(13);
        this.tactPosition = 0;
        setStretchAllColumns(true);
        setClickable(true);
        this.trackGrid = trackGrid;
        initializeRows();
        setWeightSum(13.0f);
    }

    private void initializeRows() {
        if (!this.trackRowViews.isEmpty()) {
            removeAllViews();
            this.trackRowViews.clear();
        }
        LayoutParams params = new LayoutParams(-1, 0, 1.0f);
        for (int i = 0; i < 13; i++) {
            boolean isBlackRow = Arrays.binarySearch(BLACK_KEY_INDICES, i) > -1;
            NoteName noteName = NoteName.getNoteNameFromMidiValue(NoteName.DEFAULT_NOTE_NAME.getMidi() + i);
            TrackRowView trackRowView = new TrackRowView(getContext(), this.trackGrid.getBeat(), isBlackRow, noteName, this);
            trackRowView.setTactPosition(this.tactPosition, this.trackGrid.getGridRowForNoteName(noteName));
            this.trackRowViews.add(trackRowView);
            addView((View) this.trackRowViews.get(i), params);
        }
    }

    public List<TrackRowView> getTrackRowViews() {
        return this.trackRowViews;
    }

    public void updateDataForTactPosition(int tactPosition) {
        this.tactPosition = tactPosition;
        for (int i = 0; i < 13; i++) {
            ((TrackRowView) this.trackRowViews.get(i)).setTactPosition(tactPosition, this.trackGrid.getGridRowForNoteName(NoteName.getNoteNameFromMidiValue(NoteName.C1.getMidi() + i)));
        }
    }

    public void updateGridRowPosition(NoteName noteName, int columnIndex, NoteLength noteLength, boolean toggled) {
        this.trackGrid.updateGridRowPosition(noteName, columnIndex, noteLength, this.tactPosition, toggled);
    }
}
