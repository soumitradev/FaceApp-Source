package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import com.google.android.gms.common.images.WebImage;
import com.squareup.picasso.Picasso;
import java.util.List;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;
import org.catrobat.catroid.utils.Utils;

public class ScratchJobAdapter extends ExtendedRVAdapter<Job> {
    public ScratchJobAdapter(List<Job> items) {
        super(items);
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        Job item = (Job) this.items.get(position);
        Context context = holder.itemView.getContext();
        holder.title.setText(item.getTitle());
        if (item.getImage() == null || item.getImage().getUrl() == null) {
            holder.image.setImageBitmap(null);
        } else {
            Picasso.with(holder.image.getContext()).load(Utils.changeSizeOfScratchImageURL(item.getImage().getUrl().toString(), context.getResources().getDimensionPixelSize(R.dimen.scratch_project_thumbnail_height))).into(holder.image);
        }
        switch (item.getState()) {
            case UNSCHEDULED:
                holder.details.setText("-");
                break;
            case SCHEDULED:
                holder.details.setText(context.getString(R.string.status_scheduled));
                break;
            case READY:
                holder.details.setText(context.getString(R.string.status_waiting_for_worker));
                break;
            case RUNNING:
                holder.details.setText(context.getString(R.string.status_started));
                break;
            case FINISHED:
                int messageID;
                switch (item.getDownloadState()) {
                    case DOWNLOADING:
                        messageID = R.string.status_downloading;
                        break;
                    case DOWNLOADED:
                        messageID = R.string.status_download_finished;
                        break;
                    case CANCELED:
                        messageID = R.string.status_download_canceled;
                        break;
                    default:
                        messageID = R.string.status_conversion_finished;
                        break;
                }
                holder.details.setText(context.getString(messageID));
                break;
            case FAILED:
                holder.details.setText(R.string.status_conversion_failed);
                holder.details.setTextColor(SupportMenu.CATEGORY_MASK);
                break;
            default:
                break;
        }
        WebImage httpImageMetadata = item.getImage();
        if (httpImageMetadata == null || httpImageMetadata.getUrl() == null) {
            holder.image.setImageBitmap(null);
            return;
        }
        Picasso.with(context).load(Utils.changeSizeOfScratchImageURL(httpImageMetadata.getUrl().toString(), context.getResources().getDimensionPixelSize(R.dimen.scratch_project_thumbnail_height))).into(holder.image);
    }
}
