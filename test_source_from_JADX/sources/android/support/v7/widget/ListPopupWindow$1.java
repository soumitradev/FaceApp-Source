package android.support.v7.widget;

import android.view.View;

class ListPopupWindow$1 extends ForwardingListener {
    final /* synthetic */ ListPopupWindow this$0;

    ListPopupWindow$1(ListPopupWindow this$0, View src) {
        this.this$0 = this$0;
        super(src);
    }

    public ListPopupWindow getPopup() {
        return this.this$0;
    }
}
