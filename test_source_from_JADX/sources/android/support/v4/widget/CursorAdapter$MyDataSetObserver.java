package android.support.v4.widget;

import android.database.DataSetObserver;

class CursorAdapter$MyDataSetObserver extends DataSetObserver {
    final /* synthetic */ CursorAdapter this$0;

    CursorAdapter$MyDataSetObserver(CursorAdapter cursorAdapter) {
        this.this$0 = cursorAdapter;
    }

    public void onChanged() {
        this.this$0.mDataValid = true;
        this.this$0.notifyDataSetChanged();
    }

    public void onInvalidated() {
        this.this$0.mDataValid = false;
        this.this$0.notifyDataSetInvalidated();
    }
}
