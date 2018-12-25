package android.support.design.widget;

import android.os.Handler.Callback;
import android.os.Message;

class SnackbarManager$1 implements Callback {
    final /* synthetic */ SnackbarManager this$0;

    SnackbarManager$1(SnackbarManager this$0) {
        this.this$0 = this$0;
    }

    public boolean handleMessage(Message message) {
        if (message.what != 0) {
            return false;
        }
        this.this$0.handleTimeout((SnackbarManager$SnackbarRecord) message.obj);
        return true;
    }
}
