package android.support.v7.widget;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

class ListPopupWindow$PopupScrollListener implements OnScrollListener {
    final /* synthetic */ ListPopupWindow this$0;

    ListPopupWindow$PopupScrollListener(ListPopupWindow listPopupWindow) {
        this.this$0 = listPopupWindow;
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 1 && !this.this$0.isInputMethodNotNeeded() && this.this$0.mPopup.getContentView() != null) {
            this.this$0.mHandler.removeCallbacks(this.this$0.mResizePopupRunnable);
            this.this$0.mResizePopupRunnable.run();
        }
    }
}
