package org.catrobat.catroid.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class BrickCategoryAdapter extends BaseAdapter {
    private List<View> categories;

    public BrickCategoryAdapter(List<View> categories) {
        this.categories = categories;
    }

    public int getCount() {
        return this.categories.size();
    }

    public String getItem(int position) {
        return ((TextView) ((LinearLayout) this.categories.get(position)).getChildAt(1)).getText().toString();
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return (View) this.categories.get(position);
    }
}
