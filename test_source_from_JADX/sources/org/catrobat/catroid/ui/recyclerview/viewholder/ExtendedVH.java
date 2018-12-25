package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.catrobat.catroid.generated70026.R;

public class ExtendedVH extends CheckableVH {
    public TextView details;
    public ImageView image;

    public ExtendedVH(View itemView) {
        super(itemView);
        this.image = (ImageView) itemView.findViewById(R.id.image_view);
        this.details = (TextView) itemView.findViewById(R.id.details_view);
    }
}
