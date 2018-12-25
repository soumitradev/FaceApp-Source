package android.support.v4.app;

import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.view.View;
import java.util.List;
import java.util.Map;

@RequiresApi(21)
class ActivityCompat$SharedElementCallback21Impl extends SharedElementCallback {
    protected SharedElementCallback mCallback;

    ActivityCompat$SharedElementCallback21Impl(SharedElementCallback callback) {
        this.mCallback = callback;
    }

    public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        this.mCallback.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
    }

    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        this.mCallback.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
    }

    public void onRejectSharedElements(List<View> rejectedSharedElements) {
        this.mCallback.onRejectSharedElements(rejectedSharedElements);
    }

    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        this.mCallback.onMapSharedElements(names, sharedElements);
    }

    public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
        return this.mCallback.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
    }

    public View onCreateSnapshotView(Context context, Parcelable snapshot) {
        return this.mCallback.onCreateSnapshotView(context, snapshot);
    }
}
