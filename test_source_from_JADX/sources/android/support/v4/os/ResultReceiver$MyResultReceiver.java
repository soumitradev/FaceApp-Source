package android.support.v4.os;

import android.os.Bundle;
import android.support.v4.os.IResultReceiver.Stub;

class ResultReceiver$MyResultReceiver extends Stub {
    final /* synthetic */ ResultReceiver this$0;

    ResultReceiver$MyResultReceiver(ResultReceiver this$0) {
        this.this$0 = this$0;
    }

    public void send(int resultCode, Bundle resultData) {
        if (this.this$0.mHandler != null) {
            this.this$0.mHandler.post(new ResultReceiver$MyRunnable(this.this$0, resultCode, resultData));
        } else {
            this.this$0.onReceiveResult(resultCode, resultData);
        }
    }
}
