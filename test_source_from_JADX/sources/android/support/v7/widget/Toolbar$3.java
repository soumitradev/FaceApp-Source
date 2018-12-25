package android.support.v7.widget;

import android.view.View;
import android.view.View.OnClickListener;

class Toolbar$3 implements OnClickListener {
    final /* synthetic */ Toolbar this$0;

    Toolbar$3(Toolbar this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        this.this$0.collapseActionView();
    }
}
