package android.support.v4.app;

import android.support.v4.app.FrameMetricsAggregator.FrameMetricsApi24Impl;
import android.view.FrameMetrics;
import android.view.Window;
import android.view.Window.OnFrameMetricsAvailableListener;

class FrameMetricsAggregator$FrameMetricsApi24Impl$1 implements OnFrameMetricsAvailableListener {
    final /* synthetic */ FrameMetricsApi24Impl this$0;

    FrameMetricsAggregator$FrameMetricsApi24Impl$1(FrameMetricsApi24Impl this$0) {
        this.this$0 = this$0;
    }

    public void onFrameMetricsAvailable(Window window, FrameMetrics frameMetrics, int dropCountSinceLastInvocation) {
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 1) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[0], frameMetrics.getMetric(8));
        }
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 2) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[1], frameMetrics.getMetric(1));
        }
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 4) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[2], frameMetrics.getMetric(3));
        }
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 8) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[3], frameMetrics.getMetric(4));
        }
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 16) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[4], frameMetrics.getMetric(5));
        }
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 64) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[6], frameMetrics.getMetric(7));
        }
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 32) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[5], frameMetrics.getMetric(6));
        }
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 128) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[7], frameMetrics.getMetric(0));
        }
        if ((FrameMetricsApi24Impl.access$100(this.this$0) & 256) != 0) {
            this.this$0.addDurationItem(FrameMetricsApi24Impl.access$200(this.this$0)[8], frameMetrics.getMetric(2));
        }
    }
}
