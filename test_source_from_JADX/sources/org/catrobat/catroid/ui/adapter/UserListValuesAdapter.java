package org.catrobat.catroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import org.catrobat.catroid.generated70026.R;

public class UserListValuesAdapter extends BaseAdapter implements OnClickListener {
    private Context context;
    private List<String> userListValuesList;

    private static class ViewHolder {
        private TextView text1;

        private ViewHolder() {
        }
    }

    public UserListValuesAdapter(Context context, List<String> userListValuesList) {
        this.context = context;
        this.userListValuesList = userListValuesList;
    }

    public int getCount() {
        if (this.userListValuesList.size() == 0) {
            return 1;
        }
        return this.userListValuesList.size();
    }

    public Object getItem(int index) {
        if (index < this.userListValuesList.size()) {
            return this.userListValuesList.get(index);
        }
        return null;
    }

    public long getItemId(int index) {
        return (long) index;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(this.context, 17367049, null);
            holder = new ViewHolder();
            holder.text1 = (TextView) view.findViewById(16908308);
            view.setTag(holder);
        } else if (view.getTag() instanceof ViewHolder) {
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder();
            holder.text1 = (TextView) view.findViewById(16908308);
            view.setTag(holder);
        }
        holder.text1.setText(view.getContext().getString(R.string.formula_editor_fragment_data_current_items));
        return view;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        C19161 c19161 = null;
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(17367049, parent, false);
            holder = new ViewHolder();
            holder.text1 = (TextView) view.findViewById(16908308);
            view.setTag(holder);
        } else if (view.getTag() instanceof ViewHolder) {
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder();
            holder.text1 = (TextView) view.findViewById(16908308);
            view.setTag(holder);
        }
        view.setOnClickListener(this);
        if (getItem(position) != null) {
            c19161 = getItem(position).toString();
        }
        String currentItemStringValue = c19161;
        if (currentItemStringValue == null) {
            currentItemStringValue = "";
        }
        holder.text1.setText(currentItemStringValue);
        return view;
    }

    public void onClick(View view) {
    }
}
