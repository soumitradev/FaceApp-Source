package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.note.NoteName;

public class PianoView extends ViewGroup {
    private static final int BLACK_KEY_COUNT = 5;
    private static final ButtonHeight[] HEIGHT_DISTRIBUTION = new ButtonHeight[]{ButtonHeight.oneAndAHalfButtonHeight, ButtonHeight.doubleButtonHeight, ButtonHeight.oneAndAHalfButtonHeight, ButtonHeight.oneAndAHalfButtonHeight, ButtonHeight.doubleButtonHeight, ButtonHeight.doubleButtonHeight, ButtonHeight.oneAndAHalfButtonHeight, ButtonHeight.singleButtonHeight};
    private static final int WHITE_KEY_COUNT = 8;
    private List<View> blackPianoKeys;
    private int currentHeight;
    private int margin;
    private List<View> whitePianoKeys;

    enum ButtonHeight {
        singleButtonHeight,
        oneAndAHalfButtonHeight,
        doubleButtonHeight
    }

    public PianoView(Context context) {
        this(context, null);
    }

    public PianoView(Context context, AttributeSet attributeSet) {
        int i;
        super(context, attributeSet);
        this.whitePianoKeys = new ArrayList();
        this.blackPianoKeys = new ArrayList();
        this.margin = getResources().getDimensionPixelSize(R.dimen.pocketmusic_trackrow_margin);
        for (i = 0; i < 8; i++) {
            View whiteButton = new View(context);
            whiteButton.setBackgroundColor(ContextCompat.getColor(context, R.color.solid_white));
            this.whitePianoKeys.add(whiteButton);
            addView(whiteButton);
        }
        for (i = 0; i < 5; i++) {
            whiteButton = new View(context);
            whiteButton.setBackgroundColor(ContextCompat.getColor(context, R.color.solid_black));
            this.blackPianoKeys.add(whiteButton);
            addView(whiteButton);
        }
        this.currentHeight = 0;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        PianoView pianoView = this;
        if (changed) {
            int collectiveButtonHeight = getMeasuredHeight() - (pianoView.margin * 26);
            pianoView.currentHeight = pianoView.margin;
            int rightside = getMeasuredWidth() - (pianoView.margin * 4);
            float currentButtonCount = 0.0f;
            int collectiveButtonHeight2 = collectiveButtonHeight;
            for (collectiveButtonHeight = 0; collectiveButtonHeight < 8; collectiveButtonHeight++) {
                int singleButtonHeight = round(((float) collectiveButtonHeight2) / (13.0f - currentButtonCount));
                float oneAndAHalfButtonHeight = (((float) singleButtonHeight) * 1.5f) + ((float) pianoView.margin);
                int doubleButtonHeight = (singleButtonHeight * 2) + (pianoView.margin * 2);
                switch (HEIGHT_DISTRIBUTION[collectiveButtonHeight]) {
                    case singleButtonHeight:
                        ((View) pianoView.whitePianoKeys.get(collectiveButtonHeight)).layout(pianoView.margin, pianoView.currentHeight, rightside, pianoView.currentHeight + singleButtonHeight);
                        pianoView.currentHeight += singleButtonHeight;
                        collectiveButtonHeight2 -= round((float) singleButtonHeight);
                        currentButtonCount += 1.0f;
                        break;
                    case oneAndAHalfButtonHeight:
                        ((View) pianoView.whitePianoKeys.get(collectiveButtonHeight)).layout(pianoView.margin, pianoView.currentHeight, rightside, pianoView.currentHeight + round(oneAndAHalfButtonHeight));
                        pianoView.currentHeight += round(oneAndAHalfButtonHeight);
                        collectiveButtonHeight2 -= round(((float) singleButtonHeight) * 1.5f);
                        currentButtonCount += 1.5f;
                        break;
                    case doubleButtonHeight:
                        ((View) pianoView.whitePianoKeys.get(collectiveButtonHeight)).layout(pianoView.margin, pianoView.currentHeight, rightside, pianoView.currentHeight + doubleButtonHeight);
                        pianoView.currentHeight += doubleButtonHeight;
                        collectiveButtonHeight2 -= singleButtonHeight * 2;
                        currentButtonCount += 2.0f;
                        break;
                    default:
                        break;
                }
                pianoView.currentHeight += pianoView.margin * 2;
            }
            collectiveButtonHeight = getMeasuredHeight() - (pianoView.margin * 26);
            collectiveButtonHeight2 = roundUp(((float) collectiveButtonHeight) / 1095761920);
            collectiveButtonHeight -= collectiveButtonHeight2;
            currentButtonCount = 1.0f;
            pianoView.currentHeight = pianoView.margin + collectiveButtonHeight2;
            for (int i = 0; i < 5; i++) {
                collectiveButtonHeight2 = roundUp(((float) collectiveButtonHeight) / (13.0f - currentButtonCount));
                ((View) pianoView.blackPianoKeys.get(i)).layout((int) (((float) getMeasuredWidth()) * 0.42f), pianoView.currentHeight, rightside, (pianoView.currentHeight + collectiveButtonHeight2) + (pianoView.margin * 4));
                pianoView.currentHeight += (collectiveButtonHeight2 * 2) + (pianoView.margin * 4);
                collectiveButtonHeight -= collectiveButtonHeight2 * 2;
                currentButtonCount += 2.0f;
                if (i == 1) {
                    pianoView.currentHeight += (pianoView.margin * 2) + collectiveButtonHeight2;
                    collectiveButtonHeight -= collectiveButtonHeight2;
                    currentButtonCount += 1.0f;
                }
            }
        }
    }

    public void setButtonColor(NoteName note, boolean active) {
        View noteView;
        int i = 0;
        for (int counter = NoteName.DEFAULT_NOTE_NAME.getMidi(); counter < NoteName.C2.getMidi(); counter++) {
            NoteName tempNote = NoteName.getNoteNameFromMidiValue(counter);
            if (note.equals(tempNote)) {
                break;
            }
            if (note.isSigned() == tempNote.isSigned()) {
                i++;
            }
        }
        if (note.isSigned()) {
            noteView = (View) this.blackPianoKeys.get(i);
        } else {
            noteView = (View) this.whitePianoKeys.get(i);
        }
        if (active) {
            noteView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
        } else {
            noteView.setBackgroundColor(ContextCompat.getColor(getContext(), note.isSigned() ? R.color.solid_black : R.color.solid_white));
        }
    }

    private int roundUp(float floatValue) {
        return (int) Math.ceil((double) floatValue);
    }

    private int round(float floatValue) {
        return (int) (0.5f + floatValue);
    }
}
