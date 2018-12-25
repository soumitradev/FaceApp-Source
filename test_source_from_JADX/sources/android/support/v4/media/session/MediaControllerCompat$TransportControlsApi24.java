package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.media.session.MediaControllerCompatApi24.TransportControls;

@RequiresApi(24)
class MediaControllerCompat$TransportControlsApi24 extends MediaControllerCompat$TransportControlsApi23 {
    public MediaControllerCompat$TransportControlsApi24(Object controlsObj) {
        super(controlsObj);
    }

    public void prepare() {
        TransportControls.prepare(this.mControlsObj);
    }

    public void prepareFromMediaId(String mediaId, Bundle extras) {
        TransportControls.prepareFromMediaId(this.mControlsObj, mediaId, extras);
    }

    public void prepareFromSearch(String query, Bundle extras) {
        TransportControls.prepareFromSearch(this.mControlsObj, query, extras);
    }

    public void prepareFromUri(Uri uri, Bundle extras) {
        TransportControls.prepareFromUri(this.mControlsObj, uri, extras);
    }
}
