package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.CheckBox;
import org.catrobat.catroid.generated70026.R;

public class CheckableVH extends ViewHolder {
    public CheckBox checkBox;

    public CheckableVH(View itemView) {
        super(itemView);
        this.checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
    }
}
