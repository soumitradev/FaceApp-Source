package org.catrobat.catroid.io;

import android.media.MediaPlayer;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import name.antonsmirnov.firmata.FormatHelper;

public class SoundManager {
    private static final SoundManager INSTANCE = new SoundManager();
    public static final int MAX_MEDIA_PLAYERS = 7;
    private static final String TAG = SoundManager.class.getSimpleName();
    private final List<MediaPlayer> mediaPlayers = new ArrayList(7);
    private float volume = 70.0f;

    public static SoundManager getInstance() {
        return INSTANCE;
    }

    public synchronized void playSoundFile(String pathToSoundfile) {
        MediaPlayer mediaPlayer = getAvailableMediaPlayer();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.setDataSource(pathToSoundfile);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (Exception exception) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't play sound file '");
                stringBuilder.append(pathToSoundfile);
                stringBuilder.append(FormatHelper.QUOTE);
                Log.e(str, stringBuilder.toString(), exception);
            }
        }
        return;
    }

    public synchronized float getDurationOfSoundFile(String pathToSoundfile) {
        float duration;
        MediaPlayer mediaPlayer = getAvailableMediaPlayer();
        duration = 0.0f;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.setDataSource(pathToSoundfile);
                mediaPlayer.prepare();
                duration = (float) mediaPlayer.getDuration();
                mediaPlayer.stop();
            } catch (Exception exception) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't play sound file '");
                stringBuilder.append(pathToSoundfile);
                stringBuilder.append(FormatHelper.QUOTE);
                Log.e(str, stringBuilder.toString(), exception);
            }
        }
        return duration;
    }

    private MediaPlayer getAvailableMediaPlayer() {
        for (MediaPlayer mediaPlayer : this.mediaPlayers) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.reset();
                return mediaPlayer;
            }
        }
        if (this.mediaPlayers.size() < 7) {
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.mediaPlayers.add(mediaPlayer2);
            setVolume(this.volume);
            return mediaPlayer2;
        }
        Log.d(TAG, "All MediaPlayer instances in use");
        return null;
    }

    public synchronized void setVolume(float volume) {
        if (volume > 100.0f) {
            volume = 100.0f;
        } else if (volume < 0.0f) {
            volume = 0.0f;
        }
        this.volume = volume;
        float volumeScalar = 0.01f * volume;
        for (MediaPlayer mediaPlayer : this.mediaPlayers) {
            mediaPlayer.setVolume(volumeScalar, volumeScalar);
        }
    }

    public synchronized float getVolume() {
        return this.volume;
    }

    public synchronized void clear() {
        for (MediaPlayer mediaPlayer : this.mediaPlayers) {
            mediaPlayer.release();
        }
        this.mediaPlayers.clear();
    }

    public synchronized void pause() {
        for (MediaPlayer mediaPlayer : this.mediaPlayers) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.reset();
            }
        }
    }

    public synchronized void resume() {
        for (MediaPlayer mediaPlayer : this.mediaPlayers) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
    }

    public synchronized void stopAllSounds() {
        for (MediaPlayer mediaPlayer : this.mediaPlayers) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
    }

    @VisibleForTesting
    public List<MediaPlayer> getMediaPlayers() {
        return this.mediaPlayers;
    }
}
