package android.support.v7.app;

import android.content.Context;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.ContentFrameLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;

class AppCompatDelegateImplV9$ListMenuDecorView extends ContentFrameLayout {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    public AppCompatDelegateImplV9$ListMenuDecorView(AppCompatDelegateImplV9 appCompatDelegateImplV9, Context context) {
        this.this$0 = appCompatDelegateImplV9;
        super(context);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (!this.this$0.dispatchKeyEvent(event)) {
            if (!super.dispatchKeyEvent(event)) {
                return false;
            }
        }
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() != 0 || !isOutOfBounds((int) event.getX(), (int) event.getY())) {
            return super.onInterceptTouchEvent(event);
        }
        this.this$0.closePanel(0);
        return true;
    }

    public void setBackgroundResource(int resid) {
        setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), resid));
    }

    private boolean isOutOfBounds(int x, int y) {
        if (x >= -5 && y >= -5 && x <= getWidth() + 5) {
            if (y <= getHeight() + 5) {
                return false;
            }
        }
        return true;
    }
}
