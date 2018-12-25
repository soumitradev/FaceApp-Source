package io.fabric.sdk.android;

import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;

class InitializationTask<Result> extends PriorityAsyncTask<Void, Void, Result> {
    private static final String TIMING_METRIC_TAG = "KitInitialization";
    final Kit<Result> kit;

    public InitializationTask(Kit<Result> kit) {
        this.kit = kit;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        TimingMetric timingMetric = createAndStartTimingMetric("onPreExecute");
        try {
            boolean result = this.kit.onPreExecute();
            timingMetric.stopMeasuring();
            if (result) {
                return;
            }
        } catch (UnmetDependencyException ex) {
            throw ex;
        } catch (Exception ex2) {
            Fabric.getLogger().mo4812e(Fabric.TAG, "Failure onPreExecute()", ex2);
            timingMetric.stopMeasuring();
            if (null != null) {
                return;
            }
        } catch (Throwable th) {
            timingMetric.stopMeasuring();
            if (null == null) {
                cancel(true);
            }
        }
        cancel(true);
    }

    protected Result doInBackground(Void... voids) {
        TimingMetric timingMetric = createAndStartTimingMetric("doInBackground");
        Result result = null;
        if (!isCancelled()) {
            result = this.kit.doInBackground();
        }
        timingMetric.stopMeasuring();
        return result;
    }

    protected void onPostExecute(Result result) {
        this.kit.onPostExecute(result);
        this.kit.initializationCallback.success(result);
    }

    protected void onCancelled(Result result) {
        this.kit.onCancelled(result);
        String message = new StringBuilder();
        message.append(this.kit.getIdentifier());
        message.append(" Initialization was cancelled");
        this.kit.initializationCallback.failure(new InitializationException(message.toString()));
    }

    public Priority getPriority() {
        return Priority.HIGH;
    }

    private TimingMetric createAndStartTimingMetric(String event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.kit.getIdentifier());
        stringBuilder.append(".");
        stringBuilder.append(event);
        TimingMetric timingMetric = new TimingMetric(stringBuilder.toString(), TIMING_METRIC_TAG);
        timingMetric.startMeasuring();
        return timingMetric;
    }
}
