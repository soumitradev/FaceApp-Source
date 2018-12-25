package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

class Fragment$2 extends FragmentContainer {
    final /* synthetic */ Fragment this$0;

    Fragment$2(Fragment this$0) {
        this.this$0 = this$0;
    }

    @Nullable
    public View onFindViewById(int id) {
        if (this.this$0.mView != null) {
            return this.this$0.mView.findViewById(id);
        }
        throw new IllegalStateException("Fragment does not have a view");
    }

    public boolean onHasView() {
        return this.this$0.mView != null;
    }

    public Fragment instantiate(Context context, String className, Bundle arguments) {
        return this.this$0.mHost.instantiate(context, className, arguments);
    }
}
