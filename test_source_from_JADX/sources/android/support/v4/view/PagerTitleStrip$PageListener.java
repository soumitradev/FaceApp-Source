package android.support.v4.view;

import android.database.DataSetObserver;
import android.support.v4.view.ViewPager.OnAdapterChangeListener;
import android.support.v4.view.ViewPager.OnPageChangeListener;

class PagerTitleStrip$PageListener extends DataSetObserver implements OnPageChangeListener, OnAdapterChangeListener {
    private int mScrollState;
    final /* synthetic */ PagerTitleStrip this$0;

    PagerTitleStrip$PageListener(PagerTitleStrip pagerTitleStrip) {
        this.this$0 = pagerTitleStrip;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0.5f) {
            position++;
        }
        this.this$0.updateTextPositions(position, positionOffset, false);
    }

    public void onPageSelected(int position) {
        if (this.mScrollState == 0) {
            this.this$0.updateText(this.this$0.mPager.getCurrentItem(), this.this$0.mPager.getAdapter());
            float f = 0.0f;
            if (this.this$0.mLastKnownPositionOffset >= 0.0f) {
                f = this.this$0.mLastKnownPositionOffset;
            }
            this.this$0.updateTextPositions(this.this$0.mPager.getCurrentItem(), f, true);
        }
    }

    public void onPageScrollStateChanged(int state) {
        this.mScrollState = state;
    }

    public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter) {
        this.this$0.updateAdapter(oldAdapter, newAdapter);
    }

    public void onChanged() {
        this.this$0.updateText(this.this$0.mPager.getCurrentItem(), this.this$0.mPager.getAdapter());
        float f = 0.0f;
        if (this.this$0.mLastKnownPositionOffset >= 0.0f) {
            f = this.this$0.mLastKnownPositionOffset;
        }
        this.this$0.updateTextPositions(this.this$0.mPager.getCurrentItem(), f, true);
    }
}
