package org.catrobat.catroid.ui.recyclerview.adapter.draganddrop;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import org.catrobat.catroid.generated70026.R;

public class TouchHelperCallback extends Callback {
    public static final String TAG = TouchHelperCallback.class.getSimpleName();
    private TouchHelperAdapterInterface adapterInterface;

    public TouchHelperCallback(TouchHelperAdapterInterface adapterInterface) {
        this.adapterInterface = adapterInterface;
    }

    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        return makeMovementFlags(3, 0);
    }

    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        return this.adapterInterface.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
        if (actionState == 2) {
            viewHolder.itemView.setBackgroundResource(R.drawable.button_background_pressed);
        }
    }

    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundResource(R.drawable.button_background_selector);
    }

    public boolean isLongPressDragEnabled() {
        return false;
    }

    public void onSwiped(ViewHolder viewHolder, int direction) {
    }
}
