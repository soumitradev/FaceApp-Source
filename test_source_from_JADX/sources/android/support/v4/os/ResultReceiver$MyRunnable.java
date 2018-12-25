package android.support.v4.os;

import android.os.Bundle;

class ResultReceiver$MyRunnable implements Runnable {
    final int mResultCode;
    final Bundle mResultData;
    final /* synthetic */ ResultReceiver this$0;

    ResultReceiver$MyRunnable(ResultReceiver this$0, int resultCode, Bundle resultData) {
        this.this$0 = this$0;
        this.mResultCode = resultCode;
        this.mResultData = resultData;
    }

    public void run() {
        this.this$0.onReceiveResult(this.mResultCode, this.mResultData);
    }
}
