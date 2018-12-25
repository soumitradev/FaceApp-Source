package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.squareup.picasso.RemoteViewsAction.RemoteViewsTarget;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

public class Picasso {
    static final Handler HANDLER = new Picasso$1(Looper.getMainLooper());
    static final String TAG = "Picasso";
    static volatile Picasso singleton = null;
    final Cache cache;
    private final Picasso$CleanupThread cleanupThread;
    final Context context;
    final Config defaultBitmapConfig;
    final Dispatcher dispatcher;
    boolean indicatorsEnabled;
    private final Picasso$Listener listener;
    volatile boolean loggingEnabled;
    final ReferenceQueue<Object> referenceQueue;
    private final List<RequestHandler> requestHandlers;
    private final Picasso$RequestTransformer requestTransformer;
    boolean shutdown;
    final Stats stats;
    final Map<Object, Action> targetToAction;
    final Map<ImageView, DeferredRequestCreator> targetToDeferredRequestCreator;

    public static class Builder {
        private Cache cache;
        private final Context context;
        private Config defaultBitmapConfig;
        private Downloader downloader;
        private boolean indicatorsEnabled;
        private Picasso$Listener listener;
        private boolean loggingEnabled;
        private List<RequestHandler> requestHandlers;
        private ExecutorService service;
        private Picasso$RequestTransformer transformer;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public Builder defaultBitmapConfig(Config bitmapConfig) {
            if (bitmapConfig == null) {
                throw new IllegalArgumentException("Bitmap config must not be null.");
            }
            this.defaultBitmapConfig = bitmapConfig;
            return this;
        }

        public Builder downloader(Downloader downloader) {
            if (downloader == null) {
                throw new IllegalArgumentException("Downloader must not be null.");
            } else if (this.downloader != null) {
                throw new IllegalStateException("Downloader already set.");
            } else {
                this.downloader = downloader;
                return this;
            }
        }

        public Builder executor(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("Executor service must not be null.");
            } else if (this.service != null) {
                throw new IllegalStateException("Executor service already set.");
            } else {
                this.service = executorService;
                return this;
            }
        }

        public Builder memoryCache(Cache memoryCache) {
            if (memoryCache == null) {
                throw new IllegalArgumentException("Memory cache must not be null.");
            } else if (this.cache != null) {
                throw new IllegalStateException("Memory cache already set.");
            } else {
                this.cache = memoryCache;
                return this;
            }
        }

        public Builder listener(Picasso$Listener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            } else if (this.listener != null) {
                throw new IllegalStateException("Listener already set.");
            } else {
                this.listener = listener;
                return this;
            }
        }

        public Builder requestTransformer(Picasso$RequestTransformer transformer) {
            if (transformer == null) {
                throw new IllegalArgumentException("Transformer must not be null.");
            } else if (this.transformer != null) {
                throw new IllegalStateException("Transformer already set.");
            } else {
                this.transformer = transformer;
                return this;
            }
        }

        public Builder addRequestHandler(RequestHandler requestHandler) {
            if (requestHandler == null) {
                throw new IllegalArgumentException("RequestHandler must not be null.");
            }
            if (this.requestHandlers == null) {
                this.requestHandlers = new ArrayList();
            }
            if (this.requestHandlers.contains(requestHandler)) {
                throw new IllegalStateException("RequestHandler already registered.");
            }
            this.requestHandlers.add(requestHandler);
            return this;
        }

        @Deprecated
        public Builder debugging(boolean debugging) {
            return indicatorsEnabled(debugging);
        }

        public Builder indicatorsEnabled(boolean enabled) {
            this.indicatorsEnabled = enabled;
            return this;
        }

        public Builder loggingEnabled(boolean enabled) {
            this.loggingEnabled = enabled;
            return this;
        }

        public Picasso build() {
            Context context = this.context;
            if (this.downloader == null) {
                r0.downloader = Utils.createDefaultDownloader(context);
            }
            if (r0.cache == null) {
                r0.cache = new LruCache(context);
            }
            if (r0.service == null) {
                r0.service = new PicassoExecutorService();
            }
            if (r0.transformer == null) {
                r0.transformer = Picasso$RequestTransformer.IDENTITY;
            }
            Stats stats = new Stats(r0.cache);
            return new Picasso(context, new Dispatcher(context, r0.service, Picasso.HANDLER, r0.downloader, r0.cache, stats), r0.cache, r0.listener, r0.transformer, r0.requestHandlers, stats, r0.defaultBitmapConfig, r0.indicatorsEnabled, r0.loggingEnabled);
        }
    }

    Picasso(Context context, Dispatcher dispatcher, Cache cache, Picasso$Listener listener, Picasso$RequestTransformer requestTransformer, List<RequestHandler> extraRequestHandlers, Stats stats, Config defaultBitmapConfig, boolean indicatorsEnabled, boolean loggingEnabled) {
        Context context2 = context;
        Dispatcher dispatcher2 = dispatcher;
        List<RequestHandler> list = extraRequestHandlers;
        Stats stats2 = stats;
        this.context = context2;
        this.dispatcher = dispatcher2;
        this.cache = cache;
        this.listener = listener;
        this.requestTransformer = requestTransformer;
        this.defaultBitmapConfig = defaultBitmapConfig;
        List<RequestHandler> allRequestHandlers = new ArrayList(7 + (list != null ? extraRequestHandlers.size() : 0));
        allRequestHandlers.add(new ResourceRequestHandler(context2));
        if (list != null) {
            allRequestHandlers.addAll(list);
        }
        allRequestHandlers.add(new ContactsPhotoRequestHandler(context2));
        allRequestHandlers.add(new MediaStoreRequestHandler(context2));
        allRequestHandlers.add(new ContentStreamRequestHandler(context2));
        allRequestHandlers.add(new AssetRequestHandler(context2));
        allRequestHandlers.add(new FileRequestHandler(context2));
        allRequestHandlers.add(new NetworkRequestHandler(dispatcher2.downloader, stats2));
        r0.requestHandlers = Collections.unmodifiableList(allRequestHandlers);
        r0.stats = stats2;
        r0.targetToAction = new WeakHashMap();
        r0.targetToDeferredRequestCreator = new WeakHashMap();
        r0.indicatorsEnabled = indicatorsEnabled;
        r0.loggingEnabled = loggingEnabled;
        r0.referenceQueue = new ReferenceQueue();
        r0.cleanupThread = new Picasso$CleanupThread(r0.referenceQueue, HANDLER);
        r0.cleanupThread.start();
    }

    public void cancelRequest(ImageView view) {
        cancelExistingRequest(view);
    }

    public void cancelRequest(Target target) {
        cancelExistingRequest(target);
    }

    public void cancelRequest(RemoteViews remoteViews, int viewId) {
        cancelExistingRequest(new RemoteViewsTarget(remoteViews, viewId));
    }

    public void cancelTag(Object tag) {
        Utils.checkMain();
        List<Action> actions = new ArrayList(this.targetToAction.values());
        int n = actions.size();
        for (int i = 0; i < n; i++) {
            Action action = (Action) actions.get(i);
            if (action.getTag().equals(tag)) {
                cancelExistingRequest(action.getTarget());
            }
        }
    }

    public void pauseTag(Object tag) {
        this.dispatcher.dispatchPauseTag(tag);
    }

    public void resumeTag(Object tag) {
        this.dispatcher.dispatchResumeTag(tag);
    }

    public RequestCreator load(Uri uri) {
        return new RequestCreator(this, uri, 0);
    }

    public RequestCreator load(String path) {
        if (path == null) {
            return new RequestCreator(this, null, 0);
        }
        if (path.trim().length() != 0) {
            return load(Uri.parse(path));
        }
        throw new IllegalArgumentException("Path must not be empty.");
    }

    public RequestCreator load(File file) {
        if (file == null) {
            return new RequestCreator(this, null, 0);
        }
        return load(Uri.fromFile(file));
    }

    public RequestCreator load(int resourceId) {
        if (resourceId != 0) {
            return new RequestCreator(this, null, resourceId);
        }
        throw new IllegalArgumentException("Resource ID must not be zero.");
    }

    public void invalidate(Uri uri) {
        if (uri == null) {
            throw new IllegalArgumentException("uri == null");
        }
        this.cache.clearKeyUri(uri.toString());
    }

    public void invalidate(String path) {
        if (path == null) {
            throw new IllegalArgumentException("path == null");
        }
        invalidate(Uri.parse(path));
    }

    public void invalidate(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        invalidate(Uri.fromFile(file));
    }

    @Deprecated
    public boolean isDebugging() {
        return areIndicatorsEnabled() && isLoggingEnabled();
    }

    @Deprecated
    public void setDebugging(boolean debugging) {
        setIndicatorsEnabled(debugging);
    }

    public void setIndicatorsEnabled(boolean enabled) {
        this.indicatorsEnabled = enabled;
    }

    public boolean areIndicatorsEnabled() {
        return this.indicatorsEnabled;
    }

    public void setLoggingEnabled(boolean enabled) {
        this.loggingEnabled = enabled;
    }

    public boolean isLoggingEnabled() {
        return this.loggingEnabled;
    }

    public StatsSnapshot getSnapshot() {
        return this.stats.createSnapshot();
    }

    public void shutdown() {
        if (this == singleton) {
            throw new UnsupportedOperationException("Default singleton instance cannot be shutdown.");
        } else if (!this.shutdown) {
            this.cache.clear();
            this.cleanupThread.shutdown();
            this.stats.shutdown();
            this.dispatcher.shutdown();
            for (DeferredRequestCreator deferredRequestCreator : this.targetToDeferredRequestCreator.values()) {
                deferredRequestCreator.cancel();
            }
            this.targetToDeferredRequestCreator.clear();
            this.shutdown = true;
        }
    }

    List<RequestHandler> getRequestHandlers() {
        return this.requestHandlers;
    }

    Request transformRequest(Request request) {
        Request transformed = this.requestTransformer.transformRequest(request);
        if (transformed != null) {
            return transformed;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Request transformer ");
        stringBuilder.append(this.requestTransformer.getClass().getCanonicalName());
        stringBuilder.append(" returned null for ");
        stringBuilder.append(request);
        throw new IllegalStateException(stringBuilder.toString());
    }

    void defer(ImageView view, DeferredRequestCreator request) {
        this.targetToDeferredRequestCreator.put(view, request);
    }

    void enqueueAndSubmit(Action action) {
        Object target = action.getTarget();
        if (!(target == null || this.targetToAction.get(target) == action)) {
            cancelExistingRequest(target);
            this.targetToAction.put(target, action);
        }
        submit(action);
    }

    void submit(Action action) {
        this.dispatcher.dispatchSubmit(action);
    }

    Bitmap quickMemoryCacheCheck(String key) {
        Bitmap cached = this.cache.get(key);
        if (cached != null) {
            this.stats.dispatchCacheHit();
        } else {
            this.stats.dispatchCacheMiss();
        }
        return cached;
    }

    void complete(BitmapHunter hunter) {
        Uri uri;
        Exception exception;
        Bitmap result;
        Picasso$LoadedFrom from;
        int n;
        int i;
        Action single = hunter.getAction();
        List<Action> joined = hunter.getActions();
        boolean shouldDeliver = false;
        boolean hasMultiple = (joined == null || joined.isEmpty()) ? false : true;
        if (single == null) {
            if (!hasMultiple) {
                if (!shouldDeliver) {
                    uri = hunter.getData().uri;
                    exception = hunter.getException();
                    result = hunter.getResult();
                    from = hunter.getLoadedFrom();
                    if (single != null) {
                        deliverAction(result, from, single);
                    }
                    if (hasMultiple) {
                        n = joined.size();
                        for (i = 0; i < n; i++) {
                            deliverAction(result, from, (Action) joined.get(i));
                        }
                    }
                    if (!(this.listener == null || exception == null)) {
                        this.listener.onImageLoadFailed(this, uri, exception);
                    }
                }
            }
        }
        shouldDeliver = true;
        if (!shouldDeliver) {
            uri = hunter.getData().uri;
            exception = hunter.getException();
            result = hunter.getResult();
            from = hunter.getLoadedFrom();
            if (single != null) {
                deliverAction(result, from, single);
            }
            if (hasMultiple) {
                n = joined.size();
                for (i = 0; i < n; i++) {
                    deliverAction(result, from, (Action) joined.get(i));
                }
            }
            this.listener.onImageLoadFailed(this, uri, exception);
        }
    }

    void resumeAction(Action action) {
        Bitmap bitmap = null;
        if (MemoryPolicy.shouldReadFromMemoryCache(action.memoryPolicy)) {
            bitmap = quickMemoryCacheCheck(action.getKey());
        }
        if (bitmap != null) {
            deliverAction(bitmap, Picasso$LoadedFrom.MEMORY, action);
            if (this.loggingEnabled) {
                String logId = action.request.logId();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("from ");
                stringBuilder.append(Picasso$LoadedFrom.MEMORY);
                Utils.log("Main", "completed", logId, stringBuilder.toString());
                return;
            }
            return;
        }
        enqueueAndSubmit(action);
        if (this.loggingEnabled) {
            Utils.log("Main", "resumed", action.request.logId());
        }
    }

    private void deliverAction(Bitmap result, Picasso$LoadedFrom from, Action action) {
        if (!action.isCancelled()) {
            if (!action.willReplay()) {
                this.targetToAction.remove(action.getTarget());
            }
            if (result == null) {
                action.error();
                if (this.loggingEnabled) {
                    Utils.log("Main", "errored", action.request.logId());
                }
            } else if (from == null) {
                throw new AssertionError("LoadedFrom cannot be null.");
            } else {
                action.complete(result, from);
                if (this.loggingEnabled) {
                    String logId = action.request.logId();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("from ");
                    stringBuilder.append(from);
                    Utils.log("Main", "completed", logId, stringBuilder.toString());
                }
            }
        }
    }

    private void cancelExistingRequest(Object target) {
        Utils.checkMain();
        Action action = (Action) this.targetToAction.remove(target);
        if (action != null) {
            action.cancel();
            this.dispatcher.dispatchCancel(action);
        }
        if (target instanceof ImageView) {
            DeferredRequestCreator deferredRequestCreator = (DeferredRequestCreator) this.targetToDeferredRequestCreator.remove((ImageView) target);
            if (deferredRequestCreator != null) {
                deferredRequestCreator.cancel();
            }
        }
    }

    public static Picasso with(Context context) {
        if (singleton == null) {
            synchronized (Picasso.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }

    public static void setSingletonInstance(Picasso picasso) {
        synchronized (Picasso.class) {
            if (singleton != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            singleton = picasso;
        }
    }
}
