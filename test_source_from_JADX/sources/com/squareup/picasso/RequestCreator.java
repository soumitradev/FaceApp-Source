package com.squareup.picasso;

import android.app.Notification;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.squareup.picasso.RemoteViewsAction.AppWidgetAction;
import com.squareup.picasso.RemoteViewsAction.NotificationAction;
import com.squareup.picasso.Request.Builder;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestCreator {
    private static final AtomicInteger nextId = new AtomicInteger();
    private final Builder data;
    private boolean deferred;
    private Drawable errorDrawable;
    private int errorResId;
    private int memoryPolicy;
    private int networkPolicy;
    private boolean noFade;
    private final Picasso picasso;
    private Drawable placeholderDrawable;
    private int placeholderResId;
    private boolean setPlaceholder;
    private Object tag;

    RequestCreator(Picasso picasso, Uri uri, int resourceId) {
        this.setPlaceholder = true;
        if (picasso.shutdown) {
            throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
        }
        this.picasso = picasso;
        this.data = new Builder(uri, resourceId, picasso.defaultBitmapConfig);
    }

    RequestCreator() {
        this.setPlaceholder = true;
        this.picasso = null;
        this.data = new Builder(null, 0, null);
    }

    public RequestCreator noPlaceholder() {
        if (this.placeholderResId != 0) {
            throw new IllegalStateException("Placeholder resource already set.");
        } else if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.setPlaceholder = false;
            return this;
        }
    }

    public RequestCreator placeholder(int placeholderResId) {
        if (!this.setPlaceholder) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (placeholderResId == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        } else if (this.placeholderDrawable != null) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.placeholderResId = placeholderResId;
            return this;
        }
    }

    public RequestCreator placeholder(Drawable placeholderDrawable) {
        if (!this.setPlaceholder) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        } else if (this.placeholderResId != 0) {
            throw new IllegalStateException("Placeholder image already set.");
        } else {
            this.placeholderDrawable = placeholderDrawable;
            return this;
        }
    }

    public RequestCreator error(int errorResId) {
        if (errorResId == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        } else if (this.errorDrawable != null) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.errorResId = errorResId;
            return this;
        }
    }

    public RequestCreator error(Drawable errorDrawable) {
        if (errorDrawable == null) {
            throw new IllegalArgumentException("Error image may not be null.");
        } else if (this.errorResId != 0) {
            throw new IllegalStateException("Error image already set.");
        } else {
            this.errorDrawable = errorDrawable;
            return this;
        }
    }

    public RequestCreator tag(Object tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tag invalid.");
        } else if (this.tag != null) {
            throw new IllegalStateException("Tag already set.");
        } else {
            this.tag = tag;
            return this;
        }
    }

    public RequestCreator fit() {
        this.deferred = true;
        return this;
    }

    RequestCreator unfit() {
        this.deferred = false;
        return this;
    }

    public RequestCreator resizeDimen(int targetWidthResId, int targetHeightResId) {
        Resources resources = this.picasso.context.getResources();
        return resize(resources.getDimensionPixelSize(targetWidthResId), resources.getDimensionPixelSize(targetHeightResId));
    }

    public RequestCreator resize(int targetWidth, int targetHeight) {
        this.data.resize(targetWidth, targetHeight);
        return this;
    }

    public RequestCreator centerCrop() {
        this.data.centerCrop();
        return this;
    }

    public RequestCreator centerInside() {
        this.data.centerInside();
        return this;
    }

    public RequestCreator onlyScaleDown() {
        this.data.onlyScaleDown();
        return this;
    }

    public RequestCreator rotate(float degrees) {
        this.data.rotate(degrees);
        return this;
    }

    public RequestCreator rotate(float degrees, float pivotX, float pivotY) {
        this.data.rotate(degrees, pivotX, pivotY);
        return this;
    }

    public RequestCreator config(Config config) {
        this.data.config(config);
        return this;
    }

    public RequestCreator stableKey(String stableKey) {
        this.data.stableKey(stableKey);
        return this;
    }

    public RequestCreator priority(Picasso$Priority priority) {
        this.data.priority(priority);
        return this;
    }

    public RequestCreator transform(Transformation transformation) {
        this.data.transform(transformation);
        return this;
    }

    public RequestCreator transform(List<? extends Transformation> transformations) {
        this.data.transform(transformations);
        return this;
    }

    @Deprecated
    public RequestCreator skipMemoryCache() {
        return memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE);
    }

    public RequestCreator memoryPolicy(MemoryPolicy policy, MemoryPolicy... additional) {
        if (policy == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        this.memoryPolicy |= policy.index;
        if (additional == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        if (additional.length > 0) {
            for (MemoryPolicy memoryPolicy : additional) {
                if (memoryPolicy == null) {
                    throw new IllegalArgumentException("Memory policy cannot be null.");
                }
                this.memoryPolicy |= memoryPolicy.index;
            }
        }
        return this;
    }

    public RequestCreator networkPolicy(NetworkPolicy policy, NetworkPolicy... additional) {
        if (policy == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        this.networkPolicy |= policy.index;
        if (additional == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        if (additional.length > 0) {
            for (NetworkPolicy networkPolicy : additional) {
                if (networkPolicy == null) {
                    throw new IllegalArgumentException("Network policy cannot be null.");
                }
                this.networkPolicy |= networkPolicy.index;
            }
        }
        return this;
    }

    public RequestCreator noFade() {
        this.noFade = true;
        return this;
    }

    public Bitmap get() throws IOException {
        long started = System.nanoTime();
        Utils.checkNotMain();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with get.");
        } else if (!this.data.hasImage()) {
            return null;
        } else {
            Request finalData = createRequest(started);
            Request request = finalData;
            return BitmapHunter.forRequest(this.picasso, this.picasso.dispatcher, this.picasso.cache, this.picasso.stats, new GetAction(this.picasso, request, this.memoryPolicy, this.networkPolicy, this.tag, Utils.createKey(finalData, new StringBuilder()))).hunt();
        }
    }

    public void fetch() {
        fetch(null);
    }

    public void fetch(Callback callback) {
        long started = System.nanoTime();
        if (this.deferred) {
            throw new IllegalStateException("Fit cannot be used with fetch.");
        } else if (this.data.hasImage()) {
            if (!this.data.hasPriority()) {
                this.data.priority(Picasso$Priority.LOW);
            }
            Request request = createRequest(started);
            String key = Utils.createKey(request, new StringBuilder());
            if (this.picasso.quickMemoryCacheCheck(key) != null) {
                if (this.picasso.loggingEnabled) {
                    String plainId = request.plainId();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("from ");
                    stringBuilder.append(Picasso$LoadedFrom.MEMORY);
                    Utils.log("Main", "completed", plainId, stringBuilder.toString());
                }
                if (callback != null) {
                    callback.onSuccess();
                    return;
                }
                return;
            }
            this.picasso.submit(new FetchAction(this.picasso, request, this.memoryPolicy, this.networkPolicy, this.tag, key, callback));
        }
    }

    public void into(Target target) {
        RequestCreator requestCreator = this;
        Target target2 = target;
        long started = System.nanoTime();
        Utils.checkMain();
        if (target2 == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (requestCreator.deferred) {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        } else {
            Drawable drawable = null;
            if (requestCreator.data.hasImage()) {
                Request request = createRequest(started);
                String requestKey = Utils.createKey(request);
                if (MemoryPolicy.shouldReadFromMemoryCache(requestCreator.memoryPolicy)) {
                    Bitmap bitmap = requestCreator.picasso.quickMemoryCacheCheck(requestKey);
                    if (bitmap != null) {
                        requestCreator.picasso.cancelRequest(target2);
                        target2.onBitmapLoaded(bitmap, Picasso$LoadedFrom.MEMORY);
                        return;
                    }
                }
                if (requestCreator.setPlaceholder) {
                    drawable = getPlaceholderDrawable();
                }
                target2.onPrepareLoad(drawable);
                requestCreator.picasso.enqueueAndSubmit(new TargetAction(requestCreator.picasso, target2, request, requestCreator.memoryPolicy, requestCreator.networkPolicy, requestCreator.errorDrawable, requestKey, requestCreator.tag, requestCreator.errorResId));
                return;
            }
            requestCreator.picasso.cancelRequest(target2);
            if (requestCreator.setPlaceholder) {
                drawable = getPlaceholderDrawable();
            }
            target2.onPrepareLoad(drawable);
        }
    }

    public void into(RemoteViews remoteViews, int viewId, int notificationId, Notification notification) {
        RequestCreator requestCreator = this;
        long started = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("RemoteViews must not be null.");
        } else if (notification == null) {
            throw new IllegalArgumentException("Notification must not be null.");
        } else if (requestCreator.deferred) {
            throw new IllegalStateException("Fit cannot be used with RemoteViews.");
        } else {
            if (requestCreator.placeholderDrawable == null && requestCreator.placeholderResId == 0) {
                if (requestCreator.errorDrawable == null) {
                    Request request = createRequest(started);
                    String key = Utils.createKey(request, new StringBuilder());
                    performRemoteViewInto(new NotificationAction(requestCreator.picasso, request, remoteViews, viewId, notificationId, notification, requestCreator.memoryPolicy, requestCreator.networkPolicy, key, requestCreator.tag, requestCreator.errorResId));
                    return;
                }
            }
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
    }

    public void into(RemoteViews remoteViews, int viewId, int[] appWidgetIds) {
        RequestCreator requestCreator = this;
        long started = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("remoteViews must not be null.");
        } else if (appWidgetIds == null) {
            throw new IllegalArgumentException("appWidgetIds must not be null.");
        } else if (requestCreator.deferred) {
            throw new IllegalStateException("Fit cannot be used with remote views.");
        } else {
            if (requestCreator.placeholderDrawable == null && requestCreator.placeholderResId == 0) {
                if (requestCreator.errorDrawable == null) {
                    Request request = createRequest(started);
                    String key = Utils.createKey(request, new StringBuilder());
                    performRemoteViewInto(new AppWidgetAction(requestCreator.picasso, request, remoteViews, viewId, appWidgetIds, requestCreator.memoryPolicy, requestCreator.networkPolicy, key, requestCreator.tag, requestCreator.errorResId));
                    return;
                }
            }
            throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
        }
    }

    public void into(ImageView target) {
        into(target, null);
    }

    public void into(ImageView target, Callback callback) {
        RequestCreator requestCreator = this;
        ImageView imageView = target;
        Callback callback2 = callback;
        long started = System.nanoTime();
        Utils.checkMain();
        if (imageView == null) {
            throw new IllegalArgumentException("Target must not be null.");
        } else if (requestCreator.data.hasImage()) {
            if (requestCreator.deferred) {
                if (requestCreator.data.hasSize()) {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
                int width = target.getWidth();
                int height = target.getHeight();
                if (width != 0) {
                    if (height != 0) {
                        requestCreator.data.resize(width, height);
                    }
                }
                if (requestCreator.setPlaceholder) {
                    PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
                }
                requestCreator.picasso.defer(imageView, new DeferredRequestCreator(requestCreator, imageView, callback2));
                return;
            }
            Request request = createRequest(started);
            String requestKey = Utils.createKey(request);
            if (MemoryPolicy.shouldReadFromMemoryCache(requestCreator.memoryPolicy)) {
                Bitmap bitmap = requestCreator.picasso.quickMemoryCacheCheck(requestKey);
                if (bitmap != null) {
                    requestCreator.picasso.cancelRequest(imageView);
                    PicassoDrawable.setBitmap(imageView, requestCreator.picasso.context, bitmap, Picasso$LoadedFrom.MEMORY, requestCreator.noFade, requestCreator.picasso.indicatorsEnabled);
                    if (requestCreator.picasso.loggingEnabled) {
                        String plainId = request.plainId();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("from ");
                        stringBuilder.append(Picasso$LoadedFrom.MEMORY);
                        Utils.log("Main", "completed", plainId, stringBuilder.toString());
                    }
                    if (callback2 != null) {
                        callback.onSuccess();
                    }
                    return;
                }
            }
            if (requestCreator.setPlaceholder) {
                PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
            }
            Picasso picasso = requestCreator.picasso;
            int i = requestCreator.memoryPolicy;
            int i2 = requestCreator.networkPolicy;
            int i3 = requestCreator.errorResId;
            Drawable drawable = requestCreator.errorDrawable;
            Object obj = requestCreator.tag;
            Object obj2 = obj;
            requestCreator.picasso.enqueueAndSubmit(new ImageViewAction(picasso, imageView, request, i, i2, i3, drawable, requestKey, obj2, callback2, requestCreator.noFade));
        } else {
            requestCreator.picasso.cancelRequest(imageView);
            if (requestCreator.setPlaceholder) {
                PicassoDrawable.setPlaceholder(imageView, getPlaceholderDrawable());
            }
        }
    }

    private Drawable getPlaceholderDrawable() {
        if (this.placeholderResId != 0) {
            return this.picasso.context.getResources().getDrawable(this.placeholderResId);
        }
        return this.placeholderDrawable;
    }

    private Request createRequest(long started) {
        int id = nextId.getAndIncrement();
        Request request = this.data.build();
        request.id = id;
        request.started = started;
        boolean loggingEnabled = this.picasso.loggingEnabled;
        if (loggingEnabled) {
            Utils.log("Main", "created", request.plainId(), request.toString());
        }
        Request transformed = this.picasso.transformRequest(request);
        if (transformed != request) {
            transformed.id = id;
            transformed.started = started;
            if (loggingEnabled) {
                String logId = transformed.logId();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("into ");
                stringBuilder.append(transformed);
                Utils.log("Main", "changed", logId, stringBuilder.toString());
            }
        }
        return transformed;
    }

    private void performRemoteViewInto(RemoteViewsAction action) {
        if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
            Bitmap bitmap = this.picasso.quickMemoryCacheCheck(action.getKey());
            if (bitmap != null) {
                action.complete(bitmap, Picasso$LoadedFrom.MEMORY);
                return;
            }
        }
        if (this.placeholderResId != 0) {
            action.setImageResource(this.placeholderResId);
        }
        this.picasso.enqueueAndSubmit(action);
    }
}
