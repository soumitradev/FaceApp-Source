package android.support.v4.app;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

class FragmentTabHost$DummyTabFactory implements TabContentFactory {
    private final Context mContext;

    public FragmentTabHost$DummyTabFactory(Context context) {
        this.mContext = context;
    }

    public View createTabContent(String tag) {
        View v = new View(this.mContext);
        v.setMinimumWidth(0);
        v.setMinimumHeight(0);
        return v;
    }
}
