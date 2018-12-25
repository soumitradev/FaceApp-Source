package androidx.browser.browseractions;

import android.content.Context;
import android.support.customtabs.C0100R;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

class BrowserActionsFallbackMenuAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<BrowserActionItem> mMenuItems;

    private static class ViewHolderItem {
        ImageView mIcon;
        TextView mText;

        private ViewHolderItem() {
        }
    }

    BrowserActionsFallbackMenuAdapter(List<BrowserActionItem> menuItems, Context context) {
        this.mMenuItems = menuItems;
        this.mContext = context;
    }

    public int getCount() {
        return this.mMenuItems.size();
    }

    public Object getItem(int position) {
        return this.mMenuItems.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        BrowserActionItem menuItem = (BrowserActionItem) this.mMenuItems.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext).inflate(C0100R.layout.browser_actions_context_menu_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.mIcon = (ImageView) convertView.findViewById(C0100R.id.browser_actions_menu_item_icon);
            viewHolder.mText = (TextView) convertView.findViewById(C0100R.id.browser_actions_menu_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        viewHolder.mText.setText(menuItem.getTitle());
        if (menuItem.getIconId() != 0) {
            viewHolder.mIcon.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), menuItem.getIconId(), null));
        } else {
            viewHolder.mIcon.setImageDrawable(null);
        }
        return convertView;
    }
}
