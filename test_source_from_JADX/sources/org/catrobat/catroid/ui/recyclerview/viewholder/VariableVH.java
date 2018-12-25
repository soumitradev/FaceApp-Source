package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.TextView;
import org.catrobat.catroid.generated70026.R;

public class VariableVH extends CheckableVH {
    public TextView value;

    public VariableVH(View itemView) {
        super(itemView);
        this.value = (TextView) itemView.findViewById(R.id.value_view);
    }
}
