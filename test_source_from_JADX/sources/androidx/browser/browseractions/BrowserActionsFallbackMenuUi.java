package androidx.browser.browseractions;

import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.VisibleForTesting;
import android.support.customtabs.C0100R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

class BrowserActionsFallbackMenuUi implements OnItemClickListener {
    private static final String TAG = "BrowserActionskMenuUi";
    private BrowserActionsFallbackMenuDialog mBrowserActionsDialog;
    private final Context mContext;
    private final List<BrowserActionItem> mMenuItems;
    private BrowserActionsFallbackMenuUi$BrowserActionsFallMenuUiListener mMenuUiListener;
    private final Uri mUri;

    BrowserActionsFallbackMenuUi(Context context, Uri uri, List<BrowserActionItem> menuItems) {
        this.mContext = context;
        this.mUri = uri;
        this.mMenuItems = menuItems;
    }

    @VisibleForTesting
    @RestrictTo({Scope.LIBRARY_GROUP})
    void setMenuUiListener(BrowserActionsFallbackMenuUi$BrowserActionsFallMenuUiListener menuUiListener) {
        this.mMenuUiListener = menuUiListener;
    }

    public void displayMenu() {
        View view = LayoutInflater.from(this.mContext).inflate(C0100R.layout.browser_actions_context_menu_page, null);
        this.mBrowserActionsDialog = new BrowserActionsFallbackMenuDialog(this.mContext, initMenuView(view));
        this.mBrowserActionsDialog.setContentView(view);
        if (this.mMenuUiListener != null) {
            this.mBrowserActionsDialog.setOnShowListener(new BrowserActionsFallbackMenuUi$1(this, view));
        }
        this.mBrowserActionsDialog.show();
    }

    private BrowserActionsFallbackMenuView initMenuView(View view) {
        BrowserActionsFallbackMenuView menuView = (BrowserActionsFallbackMenuView) view.findViewById(C0100R.id.browser_actions_menu_view);
        TextView urlTextView = (TextView) view.findViewById(C0100R.id.browser_actions_header_text);
        urlTextView.setText(this.mUri.toString());
        urlTextView.setOnClickListener(new BrowserActionsFallbackMenuUi$2(this, urlTextView));
        ListView menuListView = (ListView) view.findViewById(C0100R.id.browser_actions_menu_items);
        menuListView.setAdapter(new BrowserActionsFallbackMenuAdapter(this.mMenuItems, this.mContext));
        menuListView.setOnItemClickListener(this);
        return menuView;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        try {
            ((BrowserActionItem) this.mMenuItems.get(position)).getAction().send();
            this.mBrowserActionsDialog.dismiss();
        } catch (CanceledException e) {
            Log.e(TAG, "Failed to send custom item action", e);
        }
    }
}
