package android.support.v4.app;

import android.app.SharedElementCallback.OnSharedElementsReadyListener;
import android.support.annotation.RequiresApi;
import android.view.View;
import java.util.List;

@RequiresApi(23)
class ActivityCompat$SharedElementCallback23Impl extends ActivityCompat$SharedElementCallback21Impl {
    ActivityCompat$SharedElementCallback23Impl(SharedElementCallback callback) {
        super(callback);
    }

    public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, final OnSharedElementsReadyListener listener) {
        this.mCallback.onSharedElementsArrived(sharedElementNames, sharedElements, new SharedElementCallback.OnSharedElementsReadyListener() {
            public void onSharedElementsReady() {
                listener.onSharedElementsReady();
            }
        });
    }
}
