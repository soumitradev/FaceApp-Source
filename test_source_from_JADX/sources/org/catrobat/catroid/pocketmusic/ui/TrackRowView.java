package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.trackgrid.GridRow;
import org.catrobat.catroid.pocketmusic.note.trackgrid.GridRowPosition;

public class TrackRowView extends TableRow {
    public static final int QUARTER_COUNT = 4;
    private final MusicalBeat beat;
    private GridRow gridRow;
    private boolean isBlackRow;
    private NoteName noteName;
    private List<NoteView> noteViews;
    private int tactPosition;
    private TrackView trackView;

    public TrackRowView(Context context) {
        this(context, MusicalBeat.BEAT_4_4, false, NoteName.DEFAULT_NOTE_NAME, null);
    }

    public TrackRowView(Context context, MusicalBeat beat, boolean isBlackRow, NoteName noteName, TrackView trackView) {
        super(context);
        this.tactPosition = 0;
        this.noteViews = new ArrayList(4);
        this.beat = beat;
        this.noteName = noteName;
        this.trackView = trackView;
        setBlackRow(isBlackRow);
        initializeRow();
        setWeightSum(4.0f);
        updateGridRow();
    }

    public void setTactPosition(int tactPosition, GridRow gridRow) {
        this.gridRow = gridRow;
        this.tactPosition = tactPosition;
        refreshNoteViews();
    }

    private void refreshNoteViews() {
        int whiteKeyColor;
        int blackKeyColor;
        if (this.tactPosition % 2 == 0) {
            whiteKeyColor = ContextCompat.getColor(getContext(), R.color.pocketmusic_odd_bright);
            blackKeyColor = ContextCompat.getColor(getContext(), R.color.pocketmusic_odd_dusk);
        } else {
            whiteKeyColor = ContextCompat.getColor(getContext(), R.color.pocketmusic_even_bright);
            blackKeyColor = ContextCompat.getColor(getContext(), R.color.pocketmusic_even_dusk);
        }
        for (NoteView noteView : this.noteViews) {
            noteView.setNoteActive(false, false);
            if (this.isBlackRow) {
                noteView.setBackgroundColor(blackKeyColor);
            } else {
                noteView.setBackgroundColor(whiteKeyColor);
            }
        }
        updateGridRow();
    }

    public void updateGridRow() {
        if (this.gridRow != null) {
            if (this.gridRow.getGridRowPositions().size() != 0) {
                List<GridRowPosition> gridRowTact = getGridRowsForCurrentTact();
                if (gridRowTact != null) {
                    for (int i = 0; i < gridRowTact.size(); i++) {
                        setNoteForGridRowPosition((GridRowPosition) gridRowTact.get(i));
                    }
                }
            }
        }
    }

    private void setNoteForGridRowPosition(GridRowPosition position) {
        if (position != null) {
            long length = new BigDecimal(position.getNoteLength().toMilliseconds(1)).divide(new BigDecimal(this.beat.getNoteLength().toMilliseconds(1)), 4).longValue();
            for (int j = position.getColumnStartIndex(); ((long) j) < ((long) position.getColumnStartIndex()) + length; j++) {
                ((NoteView) this.noteViews.get(j)).setNoteActive(true, false);
            }
        }
    }

    private void initializeRow() {
        int i = 0;
        LayoutParams params = new LayoutParams(0, -1, 1.0f);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.pocketmusic_trackrow_margin);
        params.bottomMargin = dimensionPixelSize;
        params.rightMargin = dimensionPixelSize;
        params.topMargin = dimensionPixelSize;
        params.leftMargin = dimensionPixelSize;
        while (i < 4) {
            this.noteViews.add(new NoteView(getContext(), this, i));
            addView((View) this.noteViews.get(i), params);
            i++;
        }
    }

    public void updateGridRowPosition(int columnIndex, NoteLength noteLength, boolean toggled) {
        this.trackView.updateGridRowPosition(this.noteName, columnIndex, noteLength, toggled);
    }

    private List<GridRowPosition> getGridRowsForCurrentTact() {
        return this.gridRow.gridRowForTact(this.tactPosition);
    }

    public List<NoteView> getNoteViews() {
        return this.noteViews;
    }

    public int getTactCount() {
        return 4;
    }

    public void setBlackRow(boolean blackRow) {
        this.isBlackRow = blackRow;
    }
}
