package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.Spinner;
import org.catrobat.catroid.generated70026.R;

public class ListVH extends CheckableVH {
    public Spinner spinner;

    public ListVH(View itemView) {
        super(itemView);
        this.spinner = (Spinner) itemView.findViewById(R.id.spinner);
    }
}
