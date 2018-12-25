package org.catrobat.catroid.ui.recyclerview.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.catrobat.catroid.generated70026.R;

public class ButtonVH extends ViewHolder {
    public ImageView image;
    public TextView subtitle;

    public ButtonVH(View itemView) {
        super(itemView);
        this.image = (ImageView) itemView.findViewById(R.id.image_view);
        this.subtitle = (TextView) itemView.findViewById(R.id.subtitle_view);
    }
}
