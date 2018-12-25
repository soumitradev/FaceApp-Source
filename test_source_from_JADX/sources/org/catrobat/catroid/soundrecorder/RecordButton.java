package org.catrobat.catroid.soundrecorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

@SuppressLint({"AppCompatCustomView"})
public class RecordButton extends ImageButton {
    private RecordState state = RecordState.STOP;

    public enum RecordState {
        RECORD,
        STOP
    }

    public RecordButton(Context context) {
        super(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RecordState getState() {
        return this.state;
    }

    public void setState(RecordState state) {
        this.state = state;
    }
}
