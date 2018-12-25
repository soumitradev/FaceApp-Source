package org.catrobat.catroid.io;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;

public class StageAudioFocus implements OnAudioFocusChangeListener {
    public static final String TAG = StageAudioFocus.class.getSimpleName();
    private AudioManager audioManager = null;
    private boolean isAudioFocusGranted = false;

    public StageAudioFocus(Context context) {
        this.audioManager = (AudioManager) context.getSystemService("audio");
    }

    public void requestAudioFocus() {
        if (!isAudioFocusGranted()) {
            if (this.audioManager.requestAudioFocus(this, 3, 1) == 1) {
                this.isAudioFocusGranted = true;
            } else {
                this.isAudioFocusGranted = false;
            }
        }
    }

    public void releaseAudioFocus() {
        this.audioManager.abandonAudioFocus(this);
        this.isAudioFocusGranted = false;
    }

    public boolean isAudioFocusGranted() {
        return this.isAudioFocusGranted;
    }

    public void onAudioFocusChange(int focusChange) {
        if (focusChange == -1) {
            releaseAudioFocus();
        }
    }
}
