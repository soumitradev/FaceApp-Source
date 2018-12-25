package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaControllerCompatApi23.TransportControls;

@RequiresApi(23)
class MediaControllerCompat$TransportControlsApi23 extends MediaControllerCompat$TransportControlsApi21 {
    public MediaControllerCompat$TransportControlsApi23(Object controlsObj) {
        super(controlsObj);
    }

    public void playFromUri(Uri uri, Bundle extras) {
        TransportControls.playFromUri(this.mControlsObj, uri, extras);
    }
}
