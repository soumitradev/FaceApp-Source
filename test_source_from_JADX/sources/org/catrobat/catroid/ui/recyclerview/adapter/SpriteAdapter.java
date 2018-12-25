package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;

public class SpriteAdapter extends ExtendedRVAdapter<Sprite> {
    public SpriteAdapter(List<Sprite> items) {
        super(items);
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        Context context = holder.itemView.getContext();
        Sprite item = (Sprite) this.items.get(position);
        Bitmap lookData = null;
        if (!item.getLookList().isEmpty()) {
            lookData = ((LookData) item.getLookList().get(0)).getThumbnailBitmap();
        }
        holder.title.setText(item.getName());
        holder.image.setImageBitmap(lookData);
        if (this.showDetails) {
            holder.details.setText(String.format(Locale.getDefault(), context.getString(R.string.sprite_details), new Object[]{Integer.valueOf(item.getNumberOfScripts() + item.getNumberOfBricks()), Integer.valueOf(item.getLookList().size()), Integer.valueOf(item.getSoundList().size())}));
            holder.details.setVisibility(0);
            return;
        }
        holder.details.setVisibility(8);
    }
}
