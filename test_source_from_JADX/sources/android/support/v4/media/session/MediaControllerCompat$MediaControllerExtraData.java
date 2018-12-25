package android.support.v4.media.session;

import android.support.v4.app.SupportActivity.ExtraData;

class MediaControllerCompat$MediaControllerExtraData extends ExtraData {
    private final MediaControllerCompat mMediaController;

    MediaControllerCompat$MediaControllerExtraData(MediaControllerCompat mediaController) {
        this.mMediaController = mediaController;
    }

    MediaControllerCompat getMediaController() {
        return this.mMediaController;
    }
}
