package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import java.util.List;
import java.util.Locale;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.GroupItemSprite;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableVH;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedVH;

public class MultiViewSpriteAdapter extends SpriteAdapter {
    private static final int BACKGROUND = 0;
    private static final int SPRITE_GROUP = 2;
    private static final int SPRITE_GROUP_ITEM = 3;
    private static final int SPRITE_SINGLE = 1;
    public static final String TAG = MultiViewSpriteAdapter.class.getSimpleName();

    public MultiViewSpriteAdapter(List<Sprite> items) {
        super(items);
    }

    public CheckableVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 0:
                return new ExtendedVH(inflater.inflate(R.layout.vh_background_sprite, parent, false));
            case 1:
                return new ExtendedVH(inflater.inflate(R.layout.vh_with_checkbox, parent, false));
            case 2:
                return new ExtendedVH(inflater.inflate(R.layout.vh_sprite_group, parent, false));
            case 3:
                return new ExtendedVH(inflater.inflate(R.layout.vh_sprite_group_item, parent, false));
            default:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(TAG);
                stringBuilder.append(": viewType was not defined correctly.");
                throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public void onBindViewHolder(ExtendedVH holder, int position) {
        Context context = holder.itemView.getContext();
        Sprite item = (Sprite) this.items.get(position);
        holder.title.setText(item.getName());
        if (holder.getItemViewType() == 2) {
            Drawable drawable;
            if (((GroupSprite) item).getCollapsed()) {
                drawable = context.getResources().getDrawable(R.drawable.ic_play);
            } else {
                drawable = context.getResources().getDrawable(R.drawable.ic_play_down);
            }
            holder.image.setImageDrawable(drawable);
            holder.checkBox.setVisibility(8);
            return;
        }
        if (holder.getItemViewType() == 0) {
            holder.itemView.setOnLongClickListener(null);
            holder.checkBox.setVisibility(8);
        }
        if (holder.getItemViewType() == 3) {
            LayoutParams params = new LayoutParams(-1, -2);
            holder.itemView.setLayoutParams(params);
            if (((GroupItemSprite) item).collapsed) {
                params.height = 0;
                holder.itemView.setLayoutParams(params);
            }
        }
        Bitmap lookData = null;
        if (!item.getLookList().isEmpty()) {
            lookData = ((LookData) item.getLookList().get(0)).getThumbnailBitmap();
        }
        holder.image.setImageBitmap(lookData);
        if (this.showDetails) {
            holder.details.setText(String.format(Locale.getDefault(), context.getString(R.string.sprite_details), new Object[]{Integer.valueOf(item.getNumberOfScripts() + item.getNumberOfBricks()), Integer.valueOf(item.getLookList().size()), Integer.valueOf(item.getSoundList().size())}));
            holder.details.setVisibility(0);
        } else {
            holder.details.setVisibility(8);
        }
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        if (this.items.get(position) instanceof GroupSprite) {
            return 2;
        }
        if (this.items.get(position) instanceof GroupItemSprite) {
            return 3;
        }
        return 1;
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition != 0) {
            if (toPosition != 0) {
                Sprite fromItem = (Sprite) this.items.get(fromPosition);
                Sprite toItem = (Sprite) this.items.get(toPosition);
                if (fromItem instanceof GroupSprite) {
                    return true;
                }
                if (toItem instanceof GroupSprite) {
                    if (fromPosition > toPosition) {
                        fromItem.setConvertToSingleSprite(true);
                    } else {
                        fromItem.setConvertToGroupItemSprite(true);
                    }
                    return super.onItemMove(fromPosition, toPosition);
                } else if (!(fromItem instanceof GroupItemSprite) && (toItem instanceof GroupItemSprite)) {
                    fromItem.setConvertToGroupItemSprite(true);
                    return super.onItemMove(fromPosition, toPosition);
                } else if (!(fromItem instanceof GroupItemSprite) || (toItem instanceof GroupItemSprite)) {
                    fromItem.setConvertToGroupItemSprite(false);
                    fromItem.setConvertToSingleSprite(false);
                    return super.onItemMove(fromPosition, toPosition);
                } else {
                    fromItem.setConvertToSingleSprite(true);
                    return super.onItemMove(fromPosition, toPosition);
                }
            }
        }
        return true;
    }

    public boolean setSelection(Sprite item, boolean selection) {
        if (this.items.indexOf(item) != 0) {
            return super.setSelection(item, selection);
        }
        throw new IllegalArgumentException("You should never select the Background Sprite for any ActionMode operation. Modifying it via ActionMode is not supported.");
    }

    public void selectAll() {
        for (Sprite item : this.items) {
            if (this.items.indexOf(item) != 0) {
                if (!(item instanceof GroupSprite)) {
                    this.selectionManager.setSelectionTo(true, this.items.indexOf(item));
                }
            }
        }
        notifyDataSetChanged();
    }

    public int getSelectableItemCount() {
        int groupSpriteCount = 0;
        for (Sprite item : this.items) {
            if (item instanceof GroupSprite) {
                groupSpriteCount++;
            }
        }
        return (this.items.size() - 1) - groupSpriteCount;
    }
}
