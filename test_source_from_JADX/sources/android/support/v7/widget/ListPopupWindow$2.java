package android.support.v7.widget;

import android.view.View;

class ListPopupWindow$2 implements Runnable {
    final /* synthetic */ ListPopupWindow this$0;

    ListPopupWindow$2(ListPopupWindow this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        View view = this.this$0.getAnchorView();
        if (view != null && view.getWindowToken() != null) {
            this.this$0.show();
        }
    }
}
