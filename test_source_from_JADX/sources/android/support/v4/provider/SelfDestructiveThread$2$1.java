package android.support.v4.provider;

import android.support.v4.provider.SelfDestructiveThread.C00242;

class SelfDestructiveThread$2$1 implements Runnable {
    final /* synthetic */ C00242 this$1;
    final /* synthetic */ Object val$result;

    SelfDestructiveThread$2$1(C00242 this$1, Object obj) {
        this.this$1 = this$1;
        this.val$result = obj;
    }

    public void run() {
        this.this$1.val$reply.onReply(this.val$result);
    }
}
