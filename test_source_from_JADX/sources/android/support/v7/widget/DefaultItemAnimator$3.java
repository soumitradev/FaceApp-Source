package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.Iterator;

class DefaultItemAnimator$3 implements Runnable {
    final /* synthetic */ DefaultItemAnimator this$0;
    final /* synthetic */ ArrayList val$additions;

    DefaultItemAnimator$3(DefaultItemAnimator this$0, ArrayList arrayList) {
        this.this$0 = this$0;
        this.val$additions = arrayList;
    }

    public void run() {
        Iterator it = this.val$additions.iterator();
        while (it.hasNext()) {
            this.this$0.animateAddImpl((ViewHolder) it.next());
        }
        this.val$additions.clear();
        this.this$0.mAdditionsList.remove(this.val$additions);
    }
}
