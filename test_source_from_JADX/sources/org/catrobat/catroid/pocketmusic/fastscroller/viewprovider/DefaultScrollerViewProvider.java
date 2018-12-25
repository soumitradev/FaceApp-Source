package org.catrobat.catroid.pocketmusic.fastscroller.viewprovider;

import android.graphics.drawable.InsetDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.pocketmusic.fastscroller.viewprovider.VisibilityAnimationManager.Builder;

public class DefaultScrollerViewProvider extends ScrollerViewProvider {
    private View bubble;
    private View handle;

    public View provideHandleView(ViewGroup container) {
        this.handle = new View(getContext());
        int verticalInsetTop = getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_inset_top);
        int verticalInsetBottom = getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_inset_bottom);
        int horizontalInset = !getScroller().isVertical() ? 0 : getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_inset);
        this.handle.setBackground(new InsetDrawable(ContextCompat.getDrawable(getContext(), R.drawable.pocketmusic_fastscroller_handle), horizontalInset, verticalInsetTop, horizontalInset, verticalInsetBottom));
        this.handle.setLayoutParams(new LayoutParams(getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_clickable_width), getContext().getResources().getDimensionPixelSize(R.dimen.pocketmusic_fastscroller_handle_height)));
        return this.handle;
    }

    public View provideBubbleView(ViewGroup container) {
        this.bubble = LayoutInflater.from(getContext()).inflate(R.layout.pocketmusic_fastscroller_bubble, container, false);
        return this.bubble;
    }

    public TextView provideBubbleTextView() {
        return (TextView) this.bubble;
    }

    public int getBubbleOffset() {
        float height;
        if (getScroller().isVertical()) {
            height = (((float) this.handle.getHeight()) / 2.0f) - ((float) this.bubble.getHeight());
        } else {
            height = (((float) this.handle.getWidth()) / 2.0f) - ((float) this.bubble.getWidth());
        }
        return (int) height;
    }

    protected ViewBehavior provideHandleBehavior() {
        return new DefaultHandleBehavior(new Builder(this.handle).withHideDelay(2000).withHideAnimator(R.animator.pocketmusic_fastscroller_fade_out).withShowAnimator(R.animator.pocketmusic_fastscroller_fade_in).build());
    }

    protected ViewBehavior provideBubbleBehavior() {
        return new DefaultBubbleBehavior(new Builder(this.bubble).withHideDelay(0).withPivotX(0.5f).withPivotY(1.0f).build());
    }
}
