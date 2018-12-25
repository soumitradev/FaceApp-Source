package org.catrobat.catroid.pocketmusic.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.note.NoteLength;

@SuppressLint({"AppCompatCustomView"})
public class NoteView extends ImageView implements OnClickListener {
    private static final int FULL_VISIBLE = 255;
    private static final int HIDDEN = 0;
    private final int horizontalIndexInGridRowPosition;
    private Drawable noteDrawable;
    private boolean toggled;
    private TrackRowView trackRowView;

    public NoteView(Context context) {
        this(context, null, 0);
    }

    public NoteView(Context context, TrackRowView trackRowView, int horizontalIndexInGridRowPosition) {
        super(context);
        setOnClickListener(this);
        setAdjustViewBounds(true);
        setScaleType(ScaleType.CENTER_INSIDE);
        initNoteDrawable();
        this.trackRowView = trackRowView;
        this.horizontalIndexInGridRowPosition = horizontalIndexInGridRowPosition;
    }

    private void initNoteDrawable() {
        this.noteDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_pocketmusic_note_toggle);
        this.noteDrawable.setColorFilter(ContextCompat.getColor(getContext(), R.color.orange), Mode.SRC_IN);
        this.noteDrawable.mutate();
        this.noteDrawable.setAlpha(0);
        setImageDrawable(this.noteDrawable);
    }

    public void setNoteActive(boolean active, boolean updateData) {
        if (this.toggled != active) {
            this.toggled = active;
            showNote();
            if (updateData) {
                updateGridRow();
            }
        }
    }

    public void onClick(View v) {
        this.toggled ^= 1;
        showNote();
        updateGridRow();
    }

    private void updateGridRow() {
        this.trackRowView.updateGridRowPosition(this.horizontalIndexInGridRowPosition, NoteLength.QUARTER, this.toggled);
    }

    private void showNote() {
        if (this.toggled) {
            this.noteDrawable.setAlpha(255);
        } else {
            this.noteDrawable.setAlpha(0);
        }
        invalidate();
    }

    public boolean isToggled() {
        return this.toggled;
    }
}
