package org.catrobat.catroid.ui.recyclerview.adapter;

import com.squareup.picasso.Picasso;
import java.util.List;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;
import org.catrobat.catroid.utils.Utils;

public class ScratchProgramAdapter extends ExtendedRVAdapter<ScratchProgramData> {
    public ScratchProgramAdapter(List<ScratchProgramData> objects) {
        super(objects);
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        ScratchProgramData item = (ScratchProgramData) this.items.get(position);
        holder.title.setText(item.getTitle());
        if (item.getImage().getUrl() != null) {
            Picasso.with(holder.image.getContext()).load(Utils.changeSizeOfScratchImageURL(item.getImage().getUrl().toString(), holder.image.getContext().getResources().getDimensionPixelSize(R.dimen.scratch_project_thumbnail_height))).into(holder.image);
        } else {
            holder.image.setImageBitmap(null);
        }
        if (this.showDetails) {
            holder.details.setVisibility(0);
            holder.details.setText(item.getOwner());
            return;
        }
        holder.details.setVisibility(8);
    }
}
