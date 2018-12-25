package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.TextView;
import org.catrobat.catroid.generated70026.R;

public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
    public TextView title;

    public ViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.title_view);
    }
}
