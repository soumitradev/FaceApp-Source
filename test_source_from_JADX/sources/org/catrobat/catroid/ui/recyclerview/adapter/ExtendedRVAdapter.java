package org.catrobat.catroid.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;

public abstract class ExtendedRVAdapter<T> extends RVAdapter<T> {
    public boolean showDetails = false;

    public abstract void onBindViewHolder(ExtendedVH extendedVH, int i);

    ExtendedRVAdapter(List<T> items) {
        super(items);
    }

    public CheckableVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExtendedVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_with_checkbox, parent, false));
    }

    public void onBindViewHolder(CheckableVH holder, int position) {
        super.onBindViewHolder(holder, position);
        onBindViewHolder((ExtendedVH) holder, position);
    }
}
