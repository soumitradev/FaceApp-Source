package android.support.v4.widget;

import android.database.ContentObserver;
import android.os.Handler;

class CursorAdapter$ChangeObserver extends ContentObserver {
    final /* synthetic */ CursorAdapter this$0;

    CursorAdapter$ChangeObserver(CursorAdapter cursorAdapter) {
        this.this$0 = cursorAdapter;
        super(new Handler());
    }

    public boolean deliverSelfNotifications() {
        return true;
    }

    public void onChange(boolean selfChange) {
        this.this$0.onContentChanged();
    }
}
