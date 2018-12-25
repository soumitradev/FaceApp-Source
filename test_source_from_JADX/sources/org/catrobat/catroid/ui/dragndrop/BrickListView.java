package org.catrobat.catroid.ui.dragndrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import com.badlogic.gdx.net.HttpStatus;
import com.google.common.primitives.Ints;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.generated70026.R;
import org.catrobat.catroid.ui.adapter.BrickAdapter;
import org.catrobat.catroid.ui.adapter.BrickAdapter$ActionModeEnum;
import org.catrobat.catroid.utils.Utils;

public class BrickListView extends ListView implements OnLongClickListener {
    private static final int DRAG_BACKGROUND_COLOR = 0;
    private static final int SCROLL_SPEED = 25;
    public static final int WIDTH_OF_BRICK_PREVIEW_IMAGE = 400;
    private long blinkAnimationTimestamp;
    private boolean dimBackground;
    private DragAndDropListener dragAndDropListener;
    private boolean dragNewBrick;
    private ImageView dragView;
    private boolean isScrolling;
    private int lowerDragBound;
    private int lowerScrollBound;
    private int maximumDragViewHeight;
    private boolean newView;
    private int position;
    private int previousItemPosition;
    private int touchPointY;
    private int touchedListPosition;
    private int upperDragBound;
    private int upperScrollBound;

    public BrickListView(Context context) {
        super(context);
    }

    public BrickListView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public BrickListView(Context context, AttributeSet attributes, int defStyle) {
        super(context, attributes, defStyle);
    }

    public void setOnDragAndDropListener(DragAndDropListener listener) {
        this.dragAndDropListener = listener;
    }

    public void setInsertedBrick(int pos) {
        this.position = pos;
        this.newView = true;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.dimBackground) {
            Rect rect = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
            Paint paint = new Paint();
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            paint.setStyle(Style.FILL);
            paint.setAlpha(128);
            canvas.drawRect(rect, paint);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (y < 0) {
            y = 0;
        } else if (y > getHeight()) {
            y = getHeight();
        }
        int itemPosition = pointToPosition(x, y);
        itemPosition = itemPosition < 0 ? ((BrickAdapter) this.dragAndDropListener).getCount() - 1 : itemPosition;
        if (this.touchedListPosition != itemPosition) {
            this.touchedListPosition = itemPosition;
            if (this.dragAndDropListener != null) {
                this.dragAndDropListener.setTouchedScript(this.touchedListPosition);
            }
        }
        if (this.dragAndDropListener == null || this.dragView == null) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case 1:
            case 3:
                setDragViewAnimation(0);
                this.dragAndDropListener.drop();
                stopDragging();
                this.dimBackground = false;
                this.dragNewBrick = false;
                break;
            case 2:
                scrollListWithDraggedItem(y);
                dragTouchedListItem((int) event.getRawY());
                dragItemInList(y, itemPosition);
                this.dimBackground = true;
                break;
            default:
                break;
        }
        return true;
    }

    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        this.upperScrollBound = height / 6;
        this.lowerScrollBound = (height * 5) / 6;
        this.maximumDragViewHeight = height / 2;
    }

    public static Bitmap getBitmapFromView(View view, int width, int height) {
        view.measure(MeasureSpec.makeMeasureSpec(width, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(height, Ints.MAX_POWER_OF_TWO));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate((float) (-view.getScrollX()), (float) (-view.getScrollY()));
        view.draw(canvas);
        return bitmap;
    }

    public boolean onLongClick(View view) {
        if (((BrickAdapter) getAdapter()).getActionMode() != BrickAdapter$ActionModeEnum.NO_ACTION) {
            return true;
        }
        Bitmap bitmap;
        ((BrickAdapter) getAdapter()).isDragging = true;
        ((BrickAdapter) getAdapter()).setSpinnersEnabled(false);
        int itemPosition = calculateItemPositionAndTouchPointY(view);
        boolean drawingCacheEnabled = view.isDrawingCacheEnabled();
        view.setDrawingCacheEnabled(true);
        view.measure(MeasureSpec.makeMeasureSpec(ScreenValues.SCREEN_WIDTH, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(Utils.getPhysicalPixels(400, getContext()), Integer.MIN_VALUE));
        view.layout(0, 0, ScreenValues.SCREEN_WIDTH, view.getMeasuredHeight());
        view.setDrawingCacheBackgroundColor(0);
        view.buildDrawingCache(true);
        if (view.getDrawingCache() == null) {
            view.setDrawingCacheEnabled(drawingCacheEnabled);
            bitmap = getBitmapFromView(view, getMeasuredWidth(), view.getHeight());
        } else {
            bitmap = Bitmap.createBitmap(view.getDrawingCache());
        }
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        startDragging(bitmap, this.touchPointY);
        if (!this.dragNewBrick) {
            setDragViewAnimation(0);
            this.dragNewBrick = false;
        }
        this.dragAndDropListener.drag(itemPosition, itemPosition);
        this.dimBackground = true;
        this.previousItemPosition = itemPosition;
        return true;
    }

    private void startDragging(Bitmap bitmap, int y) {
        stopDragging();
        if (bitmap.getHeight() > this.maximumDragViewHeight) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), this.maximumDragViewHeight);
        }
        ImageView imageView = getGlowingBorder(bitmap);
        LayoutParams dragViewParameters = createLayoutParameters();
        if (this.isScrolling) {
            this.isScrolling = false;
            dragViewParameters.y = (getHeight() / 2) - (bitmap.getHeight() / 2);
        } else {
            dragViewParameters.y = y - (bitmap.getHeight() / 2);
        }
        dragViewParameters.windowAnimations = R.style.brick_new;
        getWindowManager().addView(imageView, dragViewParameters);
        this.dragView = imageView;
    }

    public ImageView getGlowingBorder(Bitmap bitmap) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(0);
        imageView.setId(R.id.drag_and_drop_list_view_image_view);
        Bitmap glowingBitmap = Bitmap.createBitmap(bitmap.getWidth() + 30, bitmap.getHeight() + 30, Config.ARGB_8888);
        Canvas glowingCanvas = new Canvas(glowingBitmap);
        Bitmap alpha = bitmap.extractAlpha();
        Paint paintBlur = new Paint();
        paintBlur.setColor(-1);
        glowingCanvas.drawBitmap(alpha, 15.0f, 15.0f, paintBlur);
        paintBlur.setMaskFilter(new BlurMaskFilter(15.0f, Blur.OUTER));
        glowingCanvas.drawBitmap(alpha, 15.0f, 15.0f, paintBlur);
        paintBlur.setMaskFilter(null);
        glowingCanvas.drawBitmap(bitmap, 15.0f, 15.0f, paintBlur);
        imageView.setImageBitmap(glowingBitmap);
        return imageView;
    }

    private void dragTouchedListItem(int y) {
        LayoutParams dragViewParameters = (LayoutParams) this.dragView.getLayoutParams();
        dragViewParameters.y = y - (this.dragView.getHeight() / 2);
        WindowManager windowManager = getWindowManager();
        try {
            windowManager.updateViewLayout(this.dragView, dragViewParameters);
        } catch (IllegalArgumentException e) {
            windowManager.addView(this.dragView, dragViewParameters);
        }
    }

    private void stopDragging() {
        if (this.dragView != null) {
            this.dragView.setVisibility(8);
            getWindowManager().removeView(this.dragView);
            this.dragView.setImageDrawable(null);
            this.dragView = null;
        }
    }

    public void resetDraggingScreen() {
        stopDragging();
        this.dimBackground = false;
        this.dragNewBrick = false;
        invalidate();
    }

    private LayoutParams createLayoutParameters() {
        LayoutParams windowParameters = new LayoutParams();
        windowParameters.gravity = 51;
        windowParameters.height = -2;
        windowParameters.width = -2;
        windowParameters.flags = HttpStatus.SC_REQUEST_TIMEOUT;
        windowParameters.format = -3;
        return windowParameters;
    }

    private WindowManager getWindowManager() {
        return (WindowManager) getContext().getSystemService("window");
    }

    private int calculateItemPositionAndTouchPointY(View view) {
        int[] location = new int[2];
        if (this.newView) {
            int itemPosition = this.position;
            View tempView = getChildAt(getChildCount() - 1);
            if (tempView != null) {
                tempView.getLocationOnScreen(location);
                this.touchPointY = location[1] + getChildAt(getChildCount() - 1).getHeight();
            }
            this.newView = false;
            return itemPosition;
        }
        itemPosition = pointToPosition(view.getLeft(), view.getTop());
        int visiblePosition = itemPosition - getFirstVisiblePosition();
        getChildAt(visiblePosition).getLocationOnScreen(location);
        this.touchPointY = location[1] + (getChildAt(visiblePosition).getHeight() / 2);
        return itemPosition;
    }

    private void scrollListWithDraggedItem(int y) {
        if (y > this.lowerScrollBound) {
            smoothScrollBy(25, 0);
        } else if (y < this.upperScrollBound) {
            smoothScrollBy(-25, 0);
        }
    }

    private void dragItemInList(int y, int itemPosition) {
        int index = this.previousItemPosition - getFirstVisiblePosition();
        if (index > 0) {
            View upperChild = getChildAt(index - 1);
            this.upperDragBound = upperChild.getBottom() - (upperChild.getHeight() / 2);
        } else {
            this.upperDragBound = 0;
        }
        if (index < getChildCount() - 1) {
            upperChild = getChildAt(index + 1);
            this.lowerDragBound = upperChild.getTop() + (upperChild.getHeight() / 2);
        } else {
            this.lowerDragBound = getHeight();
        }
        if (y > this.lowerDragBound || y < this.upperDragBound) {
            if (this.previousItemPosition != itemPosition) {
                this.dragAndDropListener.drag(this.previousItemPosition, itemPosition);
            }
            this.previousItemPosition = itemPosition;
        }
    }

    public void animateHoveringBrick() {
        if (this.dragView != null) {
            LayoutParams dragViewParameters = (LayoutParams) this.dragView.getLayoutParams();
            long now = System.currentTimeMillis();
            if (this.blinkAnimationTimestamp < now) {
                dragViewParameters.windowAnimations = R.style.brick_blink;
                getWindowManager().removeView(this.dragView);
                getWindowManager().addView(this.dragView, dragViewParameters);
                this.blinkAnimationTimestamp = now + 800;
            }
        }
    }

    public boolean isCurrentlyDragging() {
        if (this.dragView == null) {
            return false;
        }
        return true;
    }

    private void setDragViewAnimation(int styleId) {
        LayoutParams dragViewParameters = (LayoutParams) this.dragView.getLayoutParams();
        dragViewParameters.windowAnimations = styleId;
        try {
            getWindowManager().updateViewLayout(this.dragView, dragViewParameters);
        } catch (IllegalArgumentException e) {
            getWindowManager().addView(this.dragView, dragViewParameters);
        }
    }

    public void setDraggingNewBrick() {
        this.dragNewBrick = true;
    }

    public void setIsScrolling() {
        this.isScrolling = true;
    }
}
