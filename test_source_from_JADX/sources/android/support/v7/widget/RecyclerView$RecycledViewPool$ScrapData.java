package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;

class RecyclerView$RecycledViewPool$ScrapData {
    long mBindRunningAverageNs = 0;
    long mCreateRunningAverageNs = 0;
    int mMaxScrap = 5;
    final ArrayList<ViewHolder> mScrapHeap = new ArrayList();

    RecyclerView$RecycledViewPool$ScrapData() {
    }
}
