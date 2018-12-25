package org.catrobat.catroid.ui.recyclerview.adapter;

import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;

public class ScriptAdapter extends ExtendedRVAdapter<String> {
    public ScriptAdapter(List<String> items) {
        super(items);
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        holder.title.setText((CharSequence) this.items.get(position));
        holder.image.setImageResource(R.drawable.ic_program_menu_scripts);
    }
}
