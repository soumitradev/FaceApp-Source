package org.catrobat.catroid.ui.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.RVButton;
import org.catrobat.catroid.ui.recyclerview.viewholder.ButtonVH;

public class ButtonAdapter extends Adapter<ButtonVH> {
    public List<RVButton> items;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public ButtonAdapter(List<RVButton> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    public ButtonVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ButtonVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_button, parent, false));
    }

    public void onBindViewHolder(@NonNull ButtonVH holder, int position) {
        final RVButton item = (RVButton) this.items.get(position);
        holder.image.setImageDrawable(item.drawable);
        holder.title.setText(item.title);
        if (item.subtitle != null) {
            holder.subtitle.setText(item.subtitle);
            holder.subtitle.setVisibility(0);
        }
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ButtonAdapter.this.onItemClickListener.onItemClick(item.id);
            }
        });
    }

    public int getItemCount() {
        return this.items.size();
    }
}
