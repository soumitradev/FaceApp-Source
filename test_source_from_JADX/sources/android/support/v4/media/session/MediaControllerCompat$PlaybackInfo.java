package android.support.v4.media.session;

public final class MediaControllerCompat$PlaybackInfo {
    public static final int PLAYBACK_TYPE_LOCAL = 1;
    public static final int PLAYBACK_TYPE_REMOTE = 2;
    private final int mAudioStream;
    private final int mCurrentVolume;
    private final int mMaxVolume;
    private final int mPlaybackType;
    private final int mVolumeControl;

    MediaControllerCompat$PlaybackInfo(int type, int stream, int control, int max, int current) {
        this.mPlaybackType = type;
        this.mAudioStream = stream;
        this.mVolumeControl = control;
        this.mMaxVolume = max;
        this.mCurrentVolume = current;
    }

    public int getPlaybackType() {
        return this.mPlaybackType;
    }

    public int getAudioStream() {
        return this.mAudioStream;
    }

    public int getVolumeControl() {
        return this.mVolumeControl;
    }

    public int getMaxVolume() {
        return this.mMaxVolume;
    }

    public int getCurrentVolume() {
        return this.mCurrentVolume;
    }
}
