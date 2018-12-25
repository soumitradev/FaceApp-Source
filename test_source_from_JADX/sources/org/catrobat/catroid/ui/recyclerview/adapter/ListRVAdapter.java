package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.adapter.UserListValuesAdapter;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.ui.recyclerview.viewholder.ListVH;

public class ListRVAdapter extends RVAdapter<UserList> {
    ListRVAdapter(List<UserList> items) {
        super(items);
    }

    public CheckableVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListVH(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    public void onBindViewHolder(CheckableVH holder, int position) {
        super.onBindViewHolder(holder, position);
        UserList item = (UserList) this.items.get(position);
        ListVH listVH = (ListVH) holder;
        listVH.title.setText(item.getName());
        List<String> userList = new ArrayList();
        for (Object userListItem : item.getList()) {
            userList.add(userListItem.toString());
        }
        listVH.spinner.setAdapter(new UserListValuesAdapter(holder.itemView.getContext(), userList));
    }
}
