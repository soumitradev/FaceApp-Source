package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.ViewBoundsCheck.Callback;
import android.view.View;

class RecyclerView$LayoutManager$2 implements Callback {
    final /* synthetic */ LayoutManager this$0;

    RecyclerView$LayoutManager$2(LayoutManager this$0) {
        this.this$0 = this$0;
    }

    public int getChildCount() {
        return this.this$0.getChildCount();
    }

    public View getParent() {
        return this.this$0.mRecyclerView;
    }

    public View getChildAt(int index) {
        return this.this$0.getChildAt(index);
    }

    public int getParentStart() {
        return this.this$0.getPaddingTop();
    }

    public int getParentEnd() {
        return this.this$0.getHeight() - this.this$0.getPaddingBottom();
    }

    public int getChildStart(View view) {
        return this.this$0.getDecoratedTop(view) - ((LayoutParams) view.getLayoutParams()).topMargin;
    }

    public int getChildEnd(View view) {
        return this.this$0.getDecoratedBottom(view) + ((LayoutParams) view.getLayoutParams()).bottomMargin;
    }
}
