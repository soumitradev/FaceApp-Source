package com.github.mrengineer13.snackbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.github.mrengineer13.snackbar.SnackBar.OnVisibilityChangeListener;
import java.util.LinkedList;
import java.util.Queue;

class SnackContainer extends FrameLayout {
    private static final int ANIMATION_DURATION = 300;
    private static final String SAVED_MSGS = "SAVED_MSGS";
    private final Runnable mHideRunnable = new C05173();
    private AnimationSet mInAnimationSet;
    private AnimationSet mOutAnimationSet;
    private float mPreviousY;
    private Queue<SnackHolder> mSnacks = new LinkedList();

    /* renamed from: com.github.mrengineer13.snackbar.SnackContainer$1 */
    class C05151 implements AnimationListener {
        C05151() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            SnackContainer.this.removeAllViews();
            if (!SnackContainer.this.mSnacks.isEmpty()) {
                SnackContainer.this.sendOnHide((SnackHolder) SnackContainer.this.mSnacks.poll());
            }
            if (SnackContainer.this.isEmpty()) {
                SnackContainer.this.setVisibility(8);
            } else {
                SnackContainer.this.showSnack((SnackHolder) SnackContainer.this.mSnacks.peek());
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /* renamed from: com.github.mrengineer13.snackbar.SnackContainer$3 */
    class C05173 implements Runnable {
        C05173() {
        }

        public void run() {
            if (SnackContainer.this.getVisibility() == 0) {
                SnackContainer.this.startAnimation(SnackContainer.this.mOutAnimationSet);
            }
        }
    }

    private static class SnackHolder {
        final TextView button;
        final TextView messageView;
        final Snack snack;
        final View snackView;
        final OnVisibilityChangeListener visListener;

        private SnackHolder(Snack snack, View snackView, OnVisibilityChangeListener listener) {
            this.snackView = snackView;
            this.button = (TextView) snackView.findViewById(C0511R.id.snackButton);
            this.messageView = (TextView) snackView.findViewById(C0511R.id.snackMessage);
            this.snack = snack;
            this.visListener = listener;
        }
    }

    public SnackContainer(Context context) {
        super(context);
        init();
    }

    public SnackContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    SnackContainer(ViewGroup container) {
        super(container.getContext());
        container.addView(this, new LayoutParams(-1, -1));
        setVisibility(8);
        setId(C0511R.id.snackContainer);
        init();
    }

    private void init() {
        this.mInAnimationSet = new AnimationSet(false);
        TranslateAnimation translateAnimation = new TranslateAnimation(2, 0.0f, 2, 0.0f, 1, 1.0f, 1, 0.0f);
        AlphaAnimation mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        this.mInAnimationSet.addAnimation(translateAnimation);
        this.mInAnimationSet.addAnimation(mFadeInAnimation);
        this.mOutAnimationSet = new AnimationSet(false);
        TranslateAnimation translateAnimation2 = new TranslateAnimation(2, 0.0f, 2, 0.0f, 1, 0.0f, 1, 1.0f);
        AlphaAnimation mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        this.mOutAnimationSet.addAnimation(translateAnimation2);
        this.mOutAnimationSet.addAnimation(mFadeOutAnimation);
        this.mOutAnimationSet.setDuration(300);
        this.mOutAnimationSet.setAnimationListener(new C05151());
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mInAnimationSet.cancel();
        this.mOutAnimationSet.cancel();
        removeCallbacks(this.mHideRunnable);
        this.mSnacks.clear();
    }

    public boolean isEmpty() {
        return this.mSnacks.isEmpty();
    }

    public Snack peek() {
        return ((SnackHolder) this.mSnacks.peek()).snack;
    }

    public Snack pollSnack() {
        return ((SnackHolder) this.mSnacks.poll()).snack;
    }

    public void clearSnacks(boolean animate) {
        this.mSnacks.clear();
        removeCallbacks(this.mHideRunnable);
        if (animate) {
            this.mHideRunnable.run();
        }
    }

    public boolean isShowing() {
        return this.mSnacks.isEmpty() ^ 1;
    }

    public void hide() {
        removeCallbacks(this.mHideRunnable);
        this.mHideRunnable.run();
    }

    public void showSnack(Snack snack, View snackView, OnVisibilityChangeListener listener) {
        showSnack(snack, snackView, listener, false);
    }

    public void showSnack(Snack snack, View snackView, OnVisibilityChangeListener listener, boolean immediately) {
        if (!(snackView.getParent() == null || snackView.getParent() == this)) {
            ((ViewGroup) snackView.getParent()).removeView(snackView);
        }
        SnackHolder holder = new SnackHolder(snack, snackView, listener);
        this.mSnacks.offer(holder);
        if (this.mSnacks.size() == 1) {
            showSnack(holder, immediately);
        }
    }

    private void showSnack(SnackHolder holder) {
        showSnack(holder, false);
    }

    private void showSnack(final SnackHolder holder, boolean showImmediately) {
        setVisibility(0);
        sendOnShow(holder);
        addView(holder.snackView);
        holder.messageView.setText(holder.snack.mMessage);
        if (holder.snack.mActionMessage != null) {
            holder.button.setVisibility(0);
            holder.button.setText(holder.snack.mActionMessage);
            holder.button.setCompoundDrawablesWithIntrinsicBounds(holder.snack.mActionIcon, 0, 0, 0);
        } else {
            holder.button.setVisibility(8);
        }
        holder.button.setTypeface(holder.snack.mTypeface);
        holder.messageView.setTypeface(holder.snack.mTypeface);
        holder.button.setTextColor(holder.snack.mBtnTextColor);
        holder.snackView.setBackgroundColor(holder.snack.mBackgroundColor.getDefaultColor());
        if (holder.snack.mHeight > 0) {
            holder.snackView.getLayoutParams().height = getPxFromDp(holder.snack.mHeight);
        }
        if (showImmediately) {
            this.mInAnimationSet.setDuration(0);
        } else {
            this.mInAnimationSet.setDuration(300);
        }
        startAnimation(this.mInAnimationSet);
        if (holder.snack.mDuration > (short) 0) {
            postDelayed(this.mHideRunnable, (long) holder.snack.mDuration);
        }
        holder.snackView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                float y = event.getY();
                if (event.getAction() == 2) {
                    int[] location = new int[2];
                    holder.snackView.getLocationInWindow(location);
                    if (y > SnackContainer.this.mPreviousY) {
                        holder.snackView.offsetTopAndBottom(Math.round(4.0f * (y - SnackContainer.this.mPreviousY)));
                        if ((SnackContainer.this.getResources().getDisplayMetrics().heightPixels - location[1]) - 100 <= 0) {
                            SnackContainer.this.removeCallbacks(SnackContainer.this.mHideRunnable);
                            SnackContainer.this.sendOnHide(holder);
                            SnackContainer.this.startAnimation(SnackContainer.this.mOutAnimationSet);
                            if (!SnackContainer.this.mSnacks.isEmpty()) {
                                SnackContainer.this.mSnacks.clear();
                            }
                        }
                    }
                }
                SnackContainer.this.mPreviousY = y;
                return true;
            }
        });
    }

    private void sendOnHide(SnackHolder snackHolder) {
        if (snackHolder.visListener != null) {
            snackHolder.visListener.onHide(this.mSnacks.size());
        }
    }

    private void sendOnShow(SnackHolder snackHolder) {
        if (snackHolder.visListener != null) {
            snackHolder.visListener.onShow(this.mSnacks.size());
        }
    }

    public void restoreState(Bundle state, View v) {
        boolean showImmediately = true;
        for (Parcelable message : state.getParcelableArray(SAVED_MSGS)) {
            showSnack((Snack) message, v, null, showImmediately);
            showImmediately = false;
        }
    }

    public Bundle saveState() {
        Bundle outState = new Bundle();
        Snack[] snacks = new Snack[this.mSnacks.size()];
        int i = 0;
        for (SnackHolder holder : this.mSnacks) {
            int i2 = i + 1;
            snacks[i] = holder.snack;
            i = i2;
        }
        outState.putParcelableArray(SAVED_MSGS, snacks);
        return outState;
    }

    private int getPxFromDp(int dp) {
        return ((int) TypedValue.applyDimension(1, 1.0f, getResources().getDisplayMetrics())) * dp;
    }
}
