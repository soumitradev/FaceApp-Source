package uk.co.deanwild.flowtextview;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Iterator;
import uk.co.deanwild.flowtextview.helpers.ClickHandler;
import uk.co.deanwild.flowtextview.helpers.CollisionHelper;
import uk.co.deanwild.flowtextview.helpers.PaintHelper;
import uk.co.deanwild.flowtextview.helpers.SpanParser;
import uk.co.deanwild.flowtextview.listeners.OnLinkClickListener;
import uk.co.deanwild.flowtextview.models.HtmlLink;
import uk.co.deanwild.flowtextview.models.HtmlObject;
import uk.co.deanwild.flowtextview.models.Line;
import uk.co.deanwild.flowtextview.models.Obstacle;

public class FlowTextView extends RelativeLayout {
    private final ClickHandler mClickHandler = new ClickHandler(this.mSpanParser);
    private int mColor = ViewCompat.MEASURED_STATE_MASK;
    private int mDesiredHeight = 100;
    private boolean mIsHtml = false;
    private TextPaint mLinkPaint;
    private final PaintHelper mPaintHelper = new PaintHelper();
    private float mSpacingAdd;
    private float mSpacingMult;
    private final SpanParser mSpanParser = new SpanParser(this, this.mPaintHelper);
    private CharSequence mText = "";
    private TextPaint mTextPaint;
    private float mTextsize = 20.0f;
    private boolean needsMeasure = true;
    private final ArrayList<Obstacle> obstacles = new ArrayList();
    private int pageHeight = 0;
    private Typeface typeFace;

    public FlowTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public FlowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FlowTextView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mTextPaint = new TextPaint(1);
        this.mTextPaint.density = getResources().getDisplayMetrics().density;
        this.mTextPaint.setTextSize(this.mTextsize);
        this.mTextPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mLinkPaint = new TextPaint(1);
        this.mLinkPaint.density = getResources().getDisplayMetrics().density;
        this.mLinkPaint.setTextSize(this.mTextsize);
        this.mLinkPaint.setColor(-16776961);
        this.mLinkPaint.setUnderlineText(true);
        setBackgroundColor(0);
        setOnTouchListener(this.mClickHandler);
        if (attrs != null) {
            readAttrs(context, attrs);
        }
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{16843287, 16843288});
        this.mSpacingAdd = (float) ta.getDimensionPixelSize(0, 0);
        this.mSpacingMult = ta.getFloat(1, 1.0f);
        ta.recycle();
    }

    protected void onDraw(Canvas canvas) {
        float mViewWidth;
        String[] blocks;
        int paddingTop;
        super.onDraw(canvas);
        float mViewWidth2 = (float) getWidth();
        this.obstacles.clear();
        int lowestYCoord = findBoxesAndReturnLowestObstacleYCoord();
        String[] blocks2 = this.mText.toString().split("\n");
        int charOffsetEnd = 0;
        int lineIndex = 0;
        float yOffset = 0.0f;
        int lineHeight = getLineHeight();
        int paddingTop2 = getPaddingTop();
        ArrayList<HtmlObject> lineObjects = new ArrayList();
        this.mSpanParser.reset();
        int charOffsetStart = 0;
        int block_no = 0;
        while (block_no <= blocks2.length - 1) {
            int block_no2;
            ArrayList<HtmlObject> lineObjects2;
            String thisBlock = blocks2[block_no];
            if (thisBlock.length() <= 0) {
                lineIndex++;
                charOffsetEnd += 2;
                charOffsetStart = charOffsetEnd;
                block_no2 = block_no;
                lineObjects2 = lineObjects;
                mViewWidth = mViewWidth2;
                blocks = blocks2;
                paddingTop = paddingTop2;
            } else {
                int charOffsetStart2 = charOffsetStart;
                float yOffset2 = yOffset;
                String thisBlock2 = thisBlock;
                while (thisBlock2.length() > 0) {
                    int block_no3;
                    Line thisLine;
                    String thisLineStr;
                    int chunkSize;
                    float f;
                    float xOffset;
                    Line thisLine2;
                    float actualWidth;
                    String thisBlock3;
                    int lineIndex2 = lineIndex + 1;
                    float yOffset3 = ((float) (getPaddingTop() + (lineIndex2 * lineHeight))) - (((float) getLineHeight()) + r6.mTextPaint.getFontMetrics().ascent);
                    Line thisLine3 = CollisionHelper.calculateLineSpaceForGivenYOffset(yOffset3, lineHeight, mViewWidth2, r6.obstacles);
                    float xOffset2 = thisLine3.leftBound;
                    yOffset2 = thisLine3.rightBound - thisLine3.leftBound;
                    while (true) {
                        String substring;
                        int charOffsetStart3;
                        float maxWidth;
                        float actualWidth2 = yOffset2;
                        charOffsetStart = getChunk(thisBlock2, actualWidth2);
                        block_no3 = block_no;
                        block_no = charOffsetEnd + charOffsetStart;
                        thisLine = thisLine3;
                        if (charOffsetStart > 1) {
                            mViewWidth = mViewWidth2;
                            substring = thisBlock2.substring(0.0f, charOffsetStart);
                        } else {
                            mViewWidth = mViewWidth2;
                            substring = "";
                        }
                        thisLineStr = substring;
                        lineObjects.clear();
                        if (r6.mIsHtml) {
                            Object[] spans = ((Spanned) r6.mText).getSpans(charOffsetStart2, block_no, Object.class);
                            if (spans.length > 0) {
                                blocks = blocks2;
                                chunkSize = charOffsetStart;
                                mViewWidth2 = xOffset2;
                                charOffsetStart3 = charOffsetStart2;
                                paddingTop = paddingTop2;
                                maxWidth = actualWidth2;
                                actualWidth2 = r6.mSpanParser.parseSpans(lineObjects, spans, charOffsetStart3, block_no, mViewWidth2);
                            } else {
                                blocks = blocks2;
                                paddingTop = paddingTop2;
                                chunkSize = charOffsetStart;
                                mViewWidth2 = xOffset2;
                                charOffsetStart3 = charOffsetStart2;
                                maxWidth = actualWidth2;
                            }
                        } else {
                            blocks = blocks2;
                            paddingTop = paddingTop2;
                            chunkSize = charOffsetStart;
                            mViewWidth2 = xOffset2;
                            charOffsetStart3 = charOffsetStart2;
                            maxWidth = actualWidth2;
                        }
                        yOffset2 = actualWidth2;
                        if (yOffset2 > maxWidth) {
                            actualWidth2 = maxWidth - 5.0f;
                        } else {
                            actualWidth2 = maxWidth;
                        }
                        if (yOffset2 <= actualWidth2) {
                            break;
                        }
                        xOffset2 = mViewWidth2;
                        yOffset2 = actualWidth2;
                        charOffsetStart2 = charOffsetStart3;
                        block_no = block_no3;
                        thisLine3 = thisLine;
                        mViewWidth2 = mViewWidth;
                        blocks2 = blocks;
                        paddingTop2 = paddingTop;
                    }
                    int charOffsetEnd2 = charOffsetEnd + chunkSize;
                    if (lineObjects.size() <= 0) {
                        lineObjects.add(new HtmlObject(thisLineStr, 0, 0, mViewWidth2, r6.mTextPaint));
                    }
                    block_no = lineObjects.iterator();
                    while (true) {
                        Iterator i$ = block_no;
                        if (i$.hasNext() == 0) {
                            break;
                        }
                        String thisBlock4;
                        HtmlObject thisHtmlObject = (HtmlObject) i$.next();
                        if ((thisHtmlObject instanceof HtmlLink) != 0) {
                            HtmlLink block_no4 = (HtmlLink) thisHtmlObject;
                            thisBlock4 = thisBlock2;
                            r6.mSpanParser.addLink(block_no4, yOffset3, block_no4.paint.measureText(thisHtmlObject.content), (float) lineHeight);
                        } else {
                            thisBlock4 = thisBlock2;
                        }
                        thisBlock2 = thisHtmlObject.content;
                        f = thisHtmlObject.xOffset;
                        block_no2 = block_no3;
                        TextPaint textPaint = thisHtmlObject.paint;
                        xOffset = mViewWidth2;
                        thisLine2 = thisLine;
                        HtmlObject thisHtmlObject2 = thisHtmlObject;
                        String str = thisBlock2;
                        actualWidth = yOffset2;
                        thisBlock3 = thisBlock4;
                        yOffset = f;
                        f = yOffset3;
                        Iterator i$2 = i$;
                        lineObjects2 = lineObjects;
                        paintObject(canvas, str, yOffset, yOffset3, textPaint);
                        if (thisHtmlObject2.recycle != 0) {
                            r6.mPaintHelper.recyclePaint(thisHtmlObject2.paint);
                        }
                        yOffset3 = f;
                        thisBlock2 = thisBlock3;
                        block_no3 = block_no2;
                        thisLine = thisLine2;
                        lineObjects = lineObjects2;
                        mViewWidth2 = xOffset;
                        yOffset2 = actualWidth;
                        block_no = i$2;
                    }
                    f = yOffset3;
                    lineObjects2 = lineObjects;
                    xOffset = mViewWidth2;
                    actualWidth = yOffset2;
                    block_no2 = block_no3;
                    thisLine2 = thisLine;
                    thisBlock3 = thisBlock2;
                    if (chunkSize >= 1) {
                        thisBlock2 = thisBlock3.substring(chunkSize, thisBlock3.length());
                    } else {
                        thisBlock2 = thisBlock3;
                    }
                    charOffsetStart2 = charOffsetEnd2;
                    yOffset2 = f;
                    charOffsetEnd = charOffsetEnd2;
                    block_no = block_no2;
                    lineIndex = lineIndex2;
                    lineObjects = lineObjects2;
                    mViewWidth2 = mViewWidth;
                    blocks2 = blocks;
                    paddingTop2 = paddingTop;
                }
                block_no2 = block_no;
                lineObjects2 = lineObjects;
                mViewWidth = mViewWidth2;
                blocks = blocks2;
                paddingTop = paddingTop2;
                yOffset = yOffset2;
                charOffsetStart = charOffsetStart2;
            }
            block_no = block_no2 + 1;
            lineObjects = lineObjects2;
            mViewWidth2 = mViewWidth;
            blocks2 = blocks;
            paddingTop2 = paddingTop;
        }
        mViewWidth = mViewWidth2;
        blocks = blocks2;
        paddingTop = paddingTop2;
        yOffset += (float) (lineHeight / 2);
        View child = getChildAt(getChildCount() - 1);
        if (!(child == null || child.getTag() == null || !child.getTag().toString().equalsIgnoreCase("hideable"))) {
            if (yOffset <= ((float) r6.pageHeight)) {
                child.setVisibility(8);
            } else if (yOffset < ((float) (((Obstacle) r6.obstacles.get(r6.obstacles.size() - 1)).topLefty - getLineHeight()))) {
                child.setVisibility(8);
            } else {
                child.setVisibility(0);
            }
        }
        r6.mDesiredHeight = Math.max(lowestYCoord, (int) yOffset);
        if (r6.needsMeasure) {
            r6.needsMeasure = false;
            requestLayout();
        }
    }

    private int findBoxesAndReturnLowestObstacleYCoord() {
        int lowestYCoord = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                Obstacle obstacle = new Obstacle();
                obstacle.topLeftx = child.getLeft() - layoutParams.leftMargin;
                obstacle.topLefty = child.getTop() - layoutParams.topMargin;
                obstacle.bottomRightx = ((obstacle.topLeftx + layoutParams.leftMargin) + child.getWidth()) + layoutParams.rightMargin;
                obstacle.bottomRighty = ((obstacle.topLefty + layoutParams.topMargin) + child.getHeight()) + layoutParams.bottomMargin;
                this.obstacles.add(obstacle);
                if (obstacle.bottomRighty > lowestYCoord) {
                    lowestYCoord = obstacle.bottomRighty;
                }
            }
        }
        return lowestYCoord;
    }

    private int getChunk(String text, float maxWidth) {
        int length = this.mTextPaint.breakText(text, true, maxWidth, null);
        if (length > 0 && length < text.length()) {
            if (text.charAt(length - 1) != ' ') {
                if (text.length() > length && text.charAt(length) == ' ') {
                    return length + 1;
                }
                int tempLength = length - 1;
                while (text.charAt(tempLength) != ' ') {
                    tempLength--;
                    if (tempLength <= 0) {
                        return length;
                    }
                }
                return tempLength + 1;
            }
        }
        return length;
    }

    private void paintObject(Canvas canvas, String thisLineStr, float xOffset, float yOffset, Paint paint) {
        canvas.drawText(thisLineStr, xOffset, yOffset, paint);
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        invalidate();
    }

    public void invalidate() {
        this.needsMeasure = true;
        super.invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == Ints.MAX_POWER_OF_TWO) {
            width = widthSize;
        } else {
            width = getWidth();
        }
        if (heightMode == Ints.MAX_POWER_OF_TWO) {
            height = heightSize;
        } else {
            height = this.mDesiredHeight;
        }
        setMeasuredDimension(width, getLineHeight() + height);
    }

    public float getTextsize() {
        return this.mTextsize;
    }

    public void setTextSize(float textSize) {
        this.mTextsize = textSize;
        this.mTextPaint.setTextSize(this.mTextsize);
        this.mLinkPaint.setTextSize(this.mTextsize);
        invalidate();
    }

    public Typeface getTypeFace() {
        return this.typeFace;
    }

    public void setTypeface(Typeface type) {
        this.typeFace = type;
        this.mTextPaint.setTypeface(this.typeFace);
        this.mLinkPaint.setTypeface(this.typeFace);
        invalidate();
    }

    public TextPaint getTextPaint() {
        return this.mTextPaint;
    }

    public void setTextPaint(TextPaint mTextPaint) {
        this.mTextPaint = mTextPaint;
        invalidate();
    }

    public TextPaint getLinkPaint() {
        return this.mLinkPaint;
    }

    public void setLinkPaint(TextPaint mLinkPaint) {
        this.mLinkPaint = mLinkPaint;
        invalidate();
    }

    public CharSequence getText() {
        return this.mText;
    }

    public void setText(CharSequence text) {
        this.mText = text;
        if (text instanceof Spannable) {
            this.mIsHtml = true;
            this.mSpanParser.setSpannable((Spannable) text);
        } else {
            this.mIsHtml = false;
        }
        invalidate();
    }

    public int getColor() {
        return this.mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
        if (this.mTextPaint != null) {
            this.mTextPaint.setColor(this.mColor);
        }
        this.mPaintHelper.setColor(this.mColor);
        invalidate();
    }

    public OnLinkClickListener getOnLinkClickListener() {
        return this.mClickHandler.getOnLinkClickListener();
    }

    public void setOnLinkClickListener(OnLinkClickListener onLinkClickListener) {
        this.mClickHandler.setOnLinkClickListener(onLinkClickListener);
    }

    public int getLineHeight() {
        return Math.round((((float) this.mTextPaint.getFontMetricsInt(null)) * this.mSpacingMult) + this.mSpacingAdd);
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
        invalidate();
    }
}
