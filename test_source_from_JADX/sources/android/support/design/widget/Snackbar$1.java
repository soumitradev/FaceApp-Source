package android.support.design.widget;

import android.view.View;
import android.view.View.OnClickListener;

class Snackbar$1 implements OnClickListener {
    final /* synthetic */ Snackbar this$0;
    final /* synthetic */ OnClickListener val$listener;

    Snackbar$1(Snackbar this$0, OnClickListener onClickListener) {
        this.this$0 = this$0;
        this.val$listener = onClickListener;
    }

    public void onClick(View view) {
        this.val$listener.onClick(view);
        this.this$0.dispatchDismiss(1);
    }
}
