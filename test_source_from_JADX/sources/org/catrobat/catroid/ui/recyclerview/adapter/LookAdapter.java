package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;
import org.catrobat.catroid.utils.FileMetaDataExtractor;

public class LookAdapter extends ExtendedRVAdapter<LookData> {
    public LookAdapter(List<LookData> items) {
        super(items);
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        LookData item = (LookData) this.items.get(position);
        holder.title.setText(item.getName());
        holder.image.setImageBitmap(item.getThumbnailBitmap());
        if (this.showDetails) {
            int[] measure = item.getMeasure();
            String measureString = new StringBuilder();
            measureString.append(measure[0]);
            measureString.append(" x ");
            measureString.append(measure[1]);
            measureString = measureString.toString();
            Context context = holder.itemView.getContext();
            holder.details.setText(String.format(Locale.getDefault(), context.getString(R.string.look_details), new Object[]{measureString, FileMetaDataExtractor.getSizeAsString(item.getFile(), context)}));
            holder.details.setVisibility(0);
            return;
        }
        holder.details.setVisibility(8);
    }
}
