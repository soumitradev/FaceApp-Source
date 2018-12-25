package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.NetworkInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import com.squareup.picasso.Downloader.ResponseException;
import com.squareup.picasso.RequestHandler.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

class BitmapHunter implements Runnable {
    private static final Object DECODE_LOCK = new Object();
    private static final RequestHandler ERRORING_HANDLER = new C20492();
    private static final ThreadLocal<StringBuilder> NAME_BUILDER = new C16561();
    private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger();
    Action action;
    List<Action> actions;
    final Cache cache;
    final Request data;
    final Dispatcher dispatcher;
    Exception exception;
    int exifRotation;
    Future<?> future;
    final String key;
    Picasso$LoadedFrom loadedFrom;
    final int memoryPolicy;
    int networkPolicy;
    final Picasso picasso;
    Picasso$Priority priority;
    final RequestHandler requestHandler;
    Bitmap result;
    int retryCount;
    final int sequence = SEQUENCE_GENERATOR.incrementAndGet();
    final Stats stats;

    /* renamed from: com.squareup.picasso.BitmapHunter$1 */
    static class C16561 extends ThreadLocal<StringBuilder> {
        C16561() {
        }

        protected StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    }

    /* renamed from: com.squareup.picasso.BitmapHunter$2 */
    static class C20492 extends RequestHandler {
        C20492() {
        }

        public boolean canHandleRequest(Request data) {
            return true;
        }

        public Result load(Request request, int networkPolicy) throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unrecognized type of request: ");
            stringBuilder.append(request);
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    BitmapHunter(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action, RequestHandler requestHandler) {
        this.picasso = picasso;
        this.dispatcher = dispatcher;
        this.cache = cache;
        this.stats = stats;
        this.action = action;
        this.key = action.getKey();
        this.data = action.getRequest();
        this.priority = action.getPriority();
        this.memoryPolicy = action.getMemoryPolicy();
        this.networkPolicy = action.getNetworkPolicy();
        this.requestHandler = requestHandler;
        this.retryCount = requestHandler.getRetryCount();
    }

    static Bitmap decodeStream(InputStream stream, Request request) throws IOException {
        InputStream markStream = new MarkableInputStream(stream);
        stream = markStream;
        long mark = markStream.savePosition(PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH);
        Options options = RequestHandler.createBitmapOptions(request);
        boolean calculateSize = RequestHandler.requiresInSampleSize(options);
        boolean isWebPFile = Utils.isWebPFile(stream);
        markStream.reset(mark);
        if (isWebPFile) {
            byte[] bytes = Utils.toByteArray(stream);
            if (calculateSize) {
                BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        }
        if (calculateSize) {
            BitmapFactory.decodeStream(stream, null, options);
            RequestHandler.calculateInSampleSize(request.targetWidth, request.targetHeight, options, request);
            markStream.reset(mark);
        }
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        if (bitmap != null) {
            return bitmap;
        }
        throw new IOException("Failed to decode stream.");
    }

    public void run() {
        try {
            updateThreadName(this.data);
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "executing", Utils.getLogIdsForHunter(this));
            }
            this.result = hunt();
            if (this.result == null) {
                this.dispatcher.dispatchFailed(this);
            } else {
                this.dispatcher.dispatchComplete(this);
            }
        } catch (ResponseException e) {
            if (!(e.localCacheOnly && e.responseCode == 504)) {
                this.exception = e;
            }
            this.dispatcher.dispatchFailed(this);
        } catch (ContentLengthException e2) {
            this.exception = e2;
            this.dispatcher.dispatchRetry(this);
        } catch (IOException e3) {
            this.exception = e3;
            this.dispatcher.dispatchRetry(this);
        } catch (OutOfMemoryError e4) {
            StringWriter writer = new StringWriter();
            this.stats.createSnapshot().dump(new PrintWriter(writer));
            this.exception = new RuntimeException(writer.toString(), e4);
            this.dispatcher.dispatchFailed(this);
        } catch (Exception e5) {
            this.exception = e5;
            this.dispatcher.dispatchFailed(this);
        } catch (Throwable th) {
            Thread.currentThread().setName("Picasso-Idle");
        }
        Thread.currentThread().setName("Picasso-Idle");
    }

    Bitmap hunt() throws IOException {
        Bitmap bitmap = null;
        if (MemoryPolicy.shouldReadFromMemoryCache(this.memoryPolicy)) {
            bitmap = this.cache.get(this.key);
            if (bitmap != null) {
                this.stats.dispatchCacheHit();
                this.loadedFrom = Picasso$LoadedFrom.MEMORY;
                if (this.picasso.loggingEnabled) {
                    Utils.log("Hunter", "decoded", this.data.logId(), "from cache");
                }
                return bitmap;
            }
        }
        this.data.networkPolicy = this.retryCount == 0 ? NetworkPolicy.OFFLINE.index : this.networkPolicy;
        Result result = this.requestHandler.load(this.data, this.networkPolicy);
        if (result != null) {
            this.loadedFrom = result.getLoadedFrom();
            this.exifRotation = result.getExifOrientation();
            bitmap = result.getBitmap();
            if (bitmap == null) {
                InputStream is = result.getStream();
                try {
                    bitmap = decodeStream(is, this.data);
                } finally {
                    Utils.closeQuietly(is);
                }
            }
        }
        if (bitmap != null) {
            if (this.picasso.loggingEnabled) {
                Utils.log("Hunter", "decoded", this.data.logId());
            }
            this.stats.dispatchBitmapDecoded(bitmap);
            if (this.data.needsTransformation() || this.exifRotation != 0) {
                synchronized (DECODE_LOCK) {
                    if (this.data.needsMatrixTransform() || this.exifRotation != 0) {
                        bitmap = transformResult(this.data, bitmap, this.exifRotation);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId());
                        }
                    }
                    if (this.data.hasCustomTransformations()) {
                        bitmap = applyCustomTransformations(this.data.transformations, bitmap);
                        if (this.picasso.loggingEnabled) {
                            Utils.log("Hunter", "transformed", this.data.logId(), "from custom transformations");
                        }
                    }
                }
                if (bitmap != null) {
                    this.stats.dispatchBitmapTransformed(bitmap);
                }
            }
        }
        return bitmap;
    }

    void attach(Action action) {
        boolean loggingEnabled = this.picasso.loggingEnabled;
        Request request = action.request;
        if (this.action == null) {
            this.action = action;
            if (loggingEnabled) {
                if (this.actions != null) {
                    if (!this.actions.isEmpty()) {
                        Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
                    }
                }
                Utils.log("Hunter", "joined", request.logId(), "to empty hunter");
            }
            return;
        }
        if (this.actions == null) {
            this.actions = new ArrayList(3);
        }
        this.actions.add(action);
        if (loggingEnabled) {
            Utils.log("Hunter", "joined", request.logId(), Utils.getLogIdsForHunter(this, "to "));
        }
        Picasso$Priority actionPriority = action.getPriority();
        if (actionPriority.ordinal() > this.priority.ordinal()) {
            this.priority = actionPriority;
        }
    }

    void detach(Action action) {
        boolean detached = false;
        if (this.action == action) {
            this.action = null;
            detached = true;
        } else if (this.actions != null) {
            detached = this.actions.remove(action);
        }
        if (detached && action.getPriority() == this.priority) {
            this.priority = computeNewPriority();
        }
        if (this.picasso.loggingEnabled) {
            Utils.log("Hunter", "removed", action.request.logId(), Utils.getLogIdsForHunter(this, "from "));
        }
    }

    private Picasso$Priority computeNewPriority() {
        int n;
        int i;
        Picasso$Priority actionPriority;
        Picasso$Priority newPriority = Picasso$Priority.LOW;
        boolean hasAny = false;
        boolean hasMultiple = (this.actions == null || this.actions.isEmpty()) ? false : true;
        if (this.action == null) {
            if (!hasMultiple) {
                if (!hasAny) {
                    return newPriority;
                }
                if (this.action != null) {
                    newPriority = this.action.getPriority();
                }
                if (hasMultiple) {
                    n = this.actions.size();
                    for (i = 0; i < n; i++) {
                        actionPriority = ((Action) this.actions.get(i)).getPriority();
                        if (actionPriority.ordinal() > newPriority.ordinal()) {
                            newPriority = actionPriority;
                        }
                    }
                }
                return newPriority;
            }
        }
        hasAny = true;
        if (!hasAny) {
            return newPriority;
        }
        if (this.action != null) {
            newPriority = this.action.getPriority();
        }
        if (hasMultiple) {
            n = this.actions.size();
            for (i = 0; i < n; i++) {
                actionPriority = ((Action) this.actions.get(i)).getPriority();
                if (actionPriority.ordinal() > newPriority.ordinal()) {
                    newPriority = actionPriority;
                }
            }
        }
        return newPriority;
    }

    boolean cancel() {
        if (this.action != null) {
            return false;
        }
        if ((this.actions == null || this.actions.isEmpty()) && this.future != null && this.future.cancel(false)) {
            return true;
        }
        return false;
    }

    boolean isCancelled() {
        return this.future != null && this.future.isCancelled();
    }

    boolean shouldRetry(boolean airplaneMode, NetworkInfo info) {
        if (!(this.retryCount > 0)) {
            return false;
        }
        this.retryCount--;
        return this.requestHandler.shouldRetry(airplaneMode, info);
    }

    boolean supportsReplay() {
        return this.requestHandler.supportsReplay();
    }

    Bitmap getResult() {
        return this.result;
    }

    String getKey() {
        return this.key;
    }

    int getMemoryPolicy() {
        return this.memoryPolicy;
    }

    Request getData() {
        return this.data;
    }

    Action getAction() {
        return this.action;
    }

    Picasso getPicasso() {
        return this.picasso;
    }

    List<Action> getActions() {
        return this.actions;
    }

    Exception getException() {
        return this.exception;
    }

    Picasso$LoadedFrom getLoadedFrom() {
        return this.loadedFrom;
    }

    Picasso$Priority getPriority() {
        return this.priority;
    }

    static void updateThreadName(Request data) {
        String name = data.getName();
        StringBuilder builder = (StringBuilder) NAME_BUILDER.get();
        builder.ensureCapacity("Picasso-".length() + name.length());
        builder.replace("Picasso-".length(), builder.length(), name);
        Thread.currentThread().setName(builder.toString());
    }

    static BitmapHunter forRequest(Picasso picasso, Dispatcher dispatcher, Cache cache, Stats stats, Action action) {
        Request request = action.getRequest();
        List<RequestHandler> requestHandlers = picasso.getRequestHandlers();
        int count = requestHandlers.size();
        for (int i = 0; i < count; i++) {
            RequestHandler requestHandler = (RequestHandler) requestHandlers.get(i);
            if (requestHandler.canHandleRequest(request)) {
                return new BitmapHunter(picasso, dispatcher, cache, stats, action, requestHandler);
            }
        }
        return new BitmapHunter(picasso, dispatcher, cache, stats, action, ERRORING_HANDLER);
    }

    static Bitmap applyCustomTransformations(List<Transformation> transformations, Bitmap result) {
        int i = 0;
        int count = transformations.size();
        while (i < count) {
            final Transformation transformation = (Transformation) transformations.get(i);
            try {
                Bitmap newResult = transformation.transform(result);
                if (newResult == null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("Transformation ");
                    builder.append(transformation.key());
                    builder.append(" returned null after ");
                    builder.append(i);
                    builder = builder.append(" previous transformation(s).\n\nTransformation list:\n");
                    for (Transformation t : transformations) {
                        builder.append(t.key());
                        builder.append('\n');
                    }
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            throw new NullPointerException(builder.toString());
                        }
                    });
                    return null;
                } else if (newResult == result && result.isRecycled()) {
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Transformation ");
                            stringBuilder.append(transformation.key());
                            stringBuilder.append(" returned input Bitmap but recycled it.");
                            throw new IllegalStateException(stringBuilder.toString());
                        }
                    });
                    return null;
                } else if (newResult == result || result.isRecycled()) {
                    result = newResult;
                    i++;
                } else {
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Transformation ");
                            stringBuilder.append(transformation.key());
                            stringBuilder.append(" mutated input Bitmap but failed to recycle the original.");
                            throw new IllegalStateException(stringBuilder.toString());
                        }
                    });
                    return null;
                }
            } catch (final RuntimeException e) {
                Picasso.HANDLER.post(new Runnable() {
                    public void run() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Transformation ");
                        stringBuilder.append(transformation.key());
                        stringBuilder.append(" crashed with exception.");
                        throw new RuntimeException(stringBuilder.toString(), e);
                    }
                });
                return null;
            }
        }
        return result;
    }

    static Bitmap transformResult(Request data, Bitmap result, int exifRotation) {
        int i;
        int drawY;
        Request request = data;
        int i2 = exifRotation;
        int inWidth = result.getWidth();
        int inHeight = result.getHeight();
        boolean onlyScaleDown = request.onlyScaleDown;
        int drawWidth = inWidth;
        int drawHeight = inHeight;
        Matrix matrix = new Matrix();
        if (data.needsMatrixTransform()) {
            int targetWidth = request.targetWidth;
            int targetHeight = request.targetHeight;
            float targetRotation = request.rotationDegrees;
            if (targetRotation != 0.0f) {
                if (request.hasRotationPivot) {
                    matrix.setRotate(targetRotation, request.rotationPivotX, request.rotationPivotY);
                } else {
                    matrix.setRotate(targetRotation);
                }
            }
            float widthRatio;
            int drawX;
            int drawY2;
            if (request.centerCrop) {
                float scaleX;
                widthRatio = ((float) targetWidth) / ((float) inWidth);
                int heightRatio = ((float) targetHeight) / ((float) inHeight);
                if (widthRatio > heightRatio) {
                    i = 0;
                    drawY = 0;
                    drawX = (int) Math.ceil((double) (((float) inHeight) * (heightRatio / widthRatio)));
                    drawHeight = drawX;
                    scaleX = widthRatio;
                    int newSize = drawX;
                    int drawY3 = (inHeight - drawX) / 2;
                    drawX = ((float) targetHeight) / ((float) drawHeight);
                    drawY2 = drawY3;
                } else {
                    i = 0;
                    drawX = (int) Math.ceil((double) (((float) inWidth) * (widthRatio / heightRatio)));
                    drawWidth = drawX;
                    int newSize2 = drawX;
                    scaleX = ((float) targetWidth) / ((float) drawWidth);
                    i = (inWidth - drawX) / 2;
                    drawX = heightRatio;
                    drawY2 = 0;
                }
                if (shouldResize(onlyScaleDown, inWidth, inHeight, targetWidth, targetHeight)) {
                    matrix.preScale(scaleX, drawX);
                }
                drawY = drawY2;
            } else {
                i = 0;
                drawY = 0;
                float heightRatio2;
                if (request.centerInside != 0) {
                    drawX = ((float) targetWidth) / ((float) inWidth);
                    heightRatio2 = ((float) targetHeight) / ((float) inHeight);
                    widthRatio = drawX < heightRatio2 ? drawX : heightRatio2;
                    if (shouldResize(onlyScaleDown, inWidth, inHeight, targetWidth, targetHeight)) {
                        matrix.preScale(widthRatio, widthRatio);
                    }
                } else if (!((targetWidth == 0 && targetHeight == 0) || (targetWidth == inWidth && targetHeight == inHeight))) {
                    if (targetWidth != 0) {
                        drawX = (float) targetWidth;
                        heightRatio2 = (float) inWidth;
                    } else {
                        drawX = (float) targetHeight;
                        heightRatio2 = (float) inHeight;
                    }
                    drawX /= heightRatio2;
                    if (targetHeight != 0) {
                        drawY2 = (float) targetHeight;
                        widthRatio = (float) inHeight;
                    } else {
                        drawY2 = (float) targetWidth;
                        widthRatio = (float) inWidth;
                    }
                    drawY2 /= widthRatio;
                    if (shouldResize(onlyScaleDown, inWidth, inHeight, targetWidth, targetHeight)) {
                        matrix.preScale(drawX, drawY2);
                    }
                }
            }
        } else {
            i = 0;
            drawY = 0;
        }
        if (i2 != 0) {
            matrix.preRotate((float) i2);
        }
        Bitmap newResult = Bitmap.createBitmap(result, i, drawY, drawWidth, drawHeight, matrix, true);
        Bitmap result2 = result;
        if (newResult == result2) {
            return result2;
        }
        result.recycle();
        return newResult;
    }

    private static boolean shouldResize(boolean onlyScaleDown, int inWidth, int inHeight, int targetWidth, int targetHeight) {
        if (onlyScaleDown && inWidth <= targetWidth) {
            if (inHeight <= targetHeight) {
                return false;
            }
        }
        return true;
    }
}
