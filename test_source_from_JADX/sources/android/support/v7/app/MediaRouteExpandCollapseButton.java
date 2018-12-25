package android.support.v7.app;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.mediarouter.C0254R;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

class MediaRouteExpandCollapseButton extends ImageButton {
    final AnimationDrawable mCollapseAnimationDrawable;
    final String mCollapseGroupDescription;
    final AnimationDrawable mExpandAnimationDrawable;
    final String mExpandGroupDescription;
    boolean mIsGroupExpanded;
    OnClickListener mListener;

    /* renamed from: android.support.v7.app.MediaRouteExpandCollapseButton$1 */
    class C02421 implements OnClickListener {
        C02421() {
        }

        public void onClick(View view) {
            MediaRouteExpandCollapseButton.this.mIsGroupExpanded ^= 1;
            if (MediaRouteExpandCollapseButton.this.mIsGroupExpanded) {
                MediaRouteExpandCollapseButton.this.setImageDrawable(MediaRouteExpandCollapseButton.this.mExpandAnimationDrawable);
                MediaRouteExpandCollapseButton.this.mExpandAnimationDrawable.start();
                MediaRouteExpandCollapseButton.this.setContentDescription(MediaRouteExpandCollapseButton.this.mCollapseGroupDescription);
            } else {
                MediaRouteExpandCollapseButton.this.setImageDrawable(MediaRouteExpandCollapseButton.this.mCollapseAnimationDrawable);
                MediaRouteExpandCollapseButton.this.mCollapseAnimationDrawable.start();
                MediaRouteExpandCollapseButton.this.setContentDescription(MediaRouteExpandCollapseButton.this.mExpandGroupDescription);
            }
            if (MediaRouteExpandCollapseButton.this.mListener != null) {
                MediaRouteExpandCollapseButton.this.mListener.onClick(view);
            }
        }
    }

    public MediaRouteExpandCollapseButton(Context context) {
        this(context, null);
    }

    public MediaRouteExpandCollapseButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaRouteExpandCollapseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mExpandAnimationDrawable = (AnimationDrawable) ContextCompat.getDrawable(context, C0254R.drawable.mr_group_expand);
        this.mCollapseAnimationDrawable = (AnimationDrawable) ContextCompat.getDrawable(context, C0254R.drawable.mr_group_collapse);
        ColorFilter filter = new PorterDuffColorFilter(MediaRouterThemeHelper.getControllerColor(context, defStyleAttr), Mode.SRC_IN);
        this.mExpandAnimationDrawable.setColorFilter(filter);
        this.mCollapseAnimationDrawable.setColorFilter(filter);
        this.mExpandGroupDescription = context.getString(C0254R.string.mr_controller_expand_group);
        this.mCollapseGroupDescription = context.getString(C0254R.string.mr_controller_collapse_group);
        setImageDrawable(this.mExpandAnimationDrawable.getFrame(0));
        setContentDescription(this.mExpandGroupDescription);
        super.setOnClickListener(new C02421());
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mListener = listener;
    }
}
