package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.ui.recyclerview.viewholder.VariableVH;

public class VariableRVAdapter extends RVAdapter<UserVariable> {
    VariableRVAdapter(List<UserVariable> items) {
        super(items);
    }

    public CheckableVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VariableVH(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    public void onBindViewHolder(CheckableVH holder, int position) {
        super.onBindViewHolder(holder, position);
        UserVariable item = (UserVariable) this.items.get(position);
        VariableVH variableVH = (VariableVH) holder;
        variableVH.title.setText(item.getName());
        variableVH.value.setText(item.getValue().toString());
    }
}
