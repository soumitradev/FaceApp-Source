package org.catrobat.catroid.pocketmusic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.ui.AnimatorUpdateCallback;
import org.catrobat.catroid.pocketmusic.ui.PianoView;
import org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView;

public class ScrollController {
    private final int beatsPerMinute;
    private int oldScrollPosition = 0;
    private final PianoView pianoView;
    private final ImageButton playButton;
    private final View playLine;
    private ObjectAnimator scrollingAnimator;
    private final TactScrollRecyclerView scrollingView;

    /* renamed from: org.catrobat.catroid.pocketmusic.ScrollController$2 */
    class C18552 extends AnimatorListenerAdapter {
        C18552() {
        }

        public void onAnimationStart(Animator animation) {
            ScrollController.this.playButton.setImageResource(R.drawable.ic_stop_24dp);
            ScrollController.this.playLine.setVisibility(0);
            ScrollController.this.scrollingView.setPlaying(true);
            ScrollController.this.scrollingView.smoothScrollToPosition(0);
            ScrollController.this.scrollingView.getTrackGrid().startPlayback(ScrollController.this.pianoView);
        }

        public void onAnimationEnd(Animator animation) {
            ScrollController.this.playButton.setImageResource(R.drawable.ic_play);
            ScrollController.this.playLine.setVisibility(8);
            ScrollController.this.scrollingView.setPlaying(false);
            ScrollController.this.scrollingView.getTrackGrid().stopPlayback(ScrollController.this.pianoView);
            ScrollController.this.scrollingView.smoothScrollToPosition(0);
        }
    }

    /* renamed from: org.catrobat.catroid.pocketmusic.ScrollController$3 */
    class C18563 implements OnLayoutChangeListener {
        C18563() {
        }

        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            ScrollController.this.initializeAnimator();
            ScrollController.this.scrollingView.removeOnLayoutChangeListener(this);
        }
    }

    /* renamed from: org.catrobat.catroid.pocketmusic.ScrollController$4 */
    class C18574 implements OnClickListener {
        C18574() {
        }

        public void onClick(View v) {
            if (ScrollController.this.scrollingAnimator.isRunning()) {
                ScrollController.this.scrollingAnimator.end();
                ScrollController.this.scrollingAnimator.setupStartValues();
                return;
            }
            ScrollController.this.scrollingAnimator.start();
        }
    }

    /* renamed from: org.catrobat.catroid.pocketmusic.ScrollController$1 */
    class C21101 implements AnimatorUpdateCallback {
        C21101() {
        }

        public void updateCallback(int tactCount, int measuredWidth) {
            long singleButtonDuration = NoteLength.QUARTER.toMilliseconds(ScrollController.this.beatsPerMinute);
            int singleButtonWidth = measuredWidth / ScrollController.this.scrollingView.getNotesPerScreen();
            int buttonsInTrack = tactCount * 4;
            ScrollController.this.scrollingAnimator.setIntValues(new int[]{singleButtonWidth * buttonsInTrack});
            ScrollController.this.scrollingAnimator.setDuration(((long) buttonsInTrack) * singleButtonDuration);
        }
    }

    public ScrollController(ViewGroup pocketmusicMainLayout, TactScrollRecyclerView tactScrollRecyclerView, int beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
        this.scrollingView = tactScrollRecyclerView;
        this.playLine = pocketmusicMainLayout.findViewById(R.id.pocketmusic_play_line);
        this.playButton = (ImageButton) pocketmusicMainLayout.findViewById(R.id.pocketmusic_play_button);
        this.pianoView = (PianoView) pocketmusicMainLayout.findViewById(R.id.musicdroid_piano);
        init();
    }

    private void init() {
        initializePlayLine();
        initializeAnimator();
        this.scrollingView.setUpdateAnimatorCallback(new C21101());
    }

    private void setGlobalPlayPosition(int xPosition) {
        this.scrollingView.scrollBy(xPosition - this.oldScrollPosition, 0);
        this.oldScrollPosition = xPosition;
    }

    public void initializeAnimator() {
        this.scrollingAnimator = ObjectAnimator.ofInt(this, "globalPlayPosition", new int[]{0});
        this.scrollingAnimator.setInterpolator(new LinearInterpolator());
        this.scrollingAnimator.addListener(new C18552());
    }

    private void initializePlayLine() {
        this.scrollingView.addOnLayoutChangeListener(new C18563());
        this.playButton.setOnClickListener(new C18574());
    }
}
