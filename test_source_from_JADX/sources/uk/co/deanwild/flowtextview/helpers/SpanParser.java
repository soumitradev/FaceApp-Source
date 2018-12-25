package uk.co.deanwild.flowtextview.helpers;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import uk.co.deanwild.flowtextview.FlowTextView;
import uk.co.deanwild.flowtextview.models.HtmlLink;
import uk.co.deanwild.flowtextview.models.HtmlObject;

public class SpanParser {
    private final FlowTextView mFlowTextView;
    private final ArrayList<HtmlLink> mLinks = new ArrayList();
    private final PaintHelper mPaintHelper;
    private Spannable mSpannable;
    private int mTextLength = 0;
    private final HashMap<Integer, HtmlObject> sorterMap = new HashMap();

    public SpanParser(FlowTextView flowTextView, PaintHelper paintHelper) {
        this.mFlowTextView = flowTextView;
        this.mPaintHelper = paintHelper;
    }

    public float parseSpans(ArrayList<HtmlObject> objects, Object[] spans, int lineStart, int lineEnd, float baseXOffset) {
        int i$;
        int len$;
        int i = lineStart;
        int i2 = lineEnd;
        this.sorterMap.clear();
        int charFlagSize = i2 - i;
        boolean[] charFlags = new boolean[charFlagSize];
        for (Object span : spans) {
            int spanStart = r0.mSpannable.getSpanStart(span);
            int spanEnd = r0.mSpannable.getSpanEnd(span);
            if (spanStart < i) {
                spanStart = i;
            }
            if (spanEnd > i2) {
                spanEnd = i2;
            }
            for (int charCounter = spanStart; charCounter < spanEnd; charCounter++) {
                charFlags[charCounter - i] = true;
            }
            r0.sorterMap.put(Integer.valueOf(spanStart), parseSpan(span, extractText(spanStart, spanEnd), spanStart, spanEnd));
        }
        int charCounter2 = 0;
        while (true) {
            int temp1 = charCounter2;
            if (isArrayFull(charFlags)) {
                break;
            }
            while (temp1 < charFlagSize) {
                if (charFlags[temp1]) {
                    temp1++;
                } else {
                    len$ = temp1;
                    while (len$ <= charFlagSize) {
                        if (len$ >= charFlagSize || charFlags[len$]) {
                            i$ = i + temp1;
                            int spanEnd2 = i + len$;
                            r0.sorterMap.put(Integer.valueOf(i$), parseSpan(null, extractText(i$, spanEnd2), i$, spanEnd2));
                            break;
                        }
                        charFlags[len$] = true;
                        len$++;
                    }
                    temp1 = len$;
                }
            }
            charCounter2 = temp1;
        }
        Object[] sorterKeys = r0.sorterMap.keySet().toArray();
        Arrays.sort(sorterKeys);
        float thisXoffset = baseXOffset;
        for (Object obj : sorterKeys) {
            HtmlObject thisObj = (HtmlObject) r0.sorterMap.get(obj);
            thisObj.xOffset = thisXoffset;
            thisXoffset += thisObj.paint.measureText(thisObj.content);
            objects.add(thisObj);
        }
        ArrayList<HtmlObject> arrayList = objects;
        return thisXoffset - baseXOffset;
    }

    private HtmlObject parseSpan(Object span, String content, int start, int end) {
        if (span instanceof URLSpan) {
            return getHtmlLink((URLSpan) span, content, start, end, 0.0f);
        }
        if (span instanceof StyleSpan) {
            return getStyledObject((StyleSpan) span, content, start, end, 0.0f);
        }
        return getHtmlObject(content, start, end, 0.0f);
    }

    private HtmlObject getStyledObject(StyleSpan span, String content, int start, int end, float thisXOffset) {
        TextPaint paint = this.mPaintHelper.getPaintFromHeap();
        paint.setTypeface(Typeface.defaultFromStyle(span.getStyle()));
        paint.setTextSize(this.mFlowTextView.getTextsize());
        paint.setColor(this.mFlowTextView.getColor());
        span.updateDrawState(paint);
        span.updateMeasureState(paint);
        HtmlObject obj = new HtmlObject(content, start, end, thisXOffset, paint);
        obj.recycle = true;
        return obj;
    }

    private HtmlObject getHtmlObject(String content, int start, int end, float thisXOffset) {
        return new HtmlObject(content, start, end, thisXOffset, this.mFlowTextView.getTextPaint());
    }

    public void reset() {
        this.mLinks.clear();
    }

    private HtmlLink getHtmlLink(URLSpan span, String content, int start, int end, float thisXOffset) {
        HtmlLink obj = new HtmlLink(content, start, end, thisXOffset, this.mFlowTextView.getLinkPaint(), span.getURL());
        this.mLinks.add(obj);
        return obj;
    }

    public void addLink(HtmlLink thisLink, float yOffset, float width, float height) {
        thisLink.yOffset = yOffset - 20.0f;
        thisLink.width = width;
        thisLink.height = 20.0f + height;
        this.mLinks.add(thisLink);
    }

    private String extractText(int start, int end) {
        if (start < 0) {
            start = 0;
        }
        if (end > this.mTextLength - 1) {
            end = this.mTextLength - 1;
        }
        return this.mSpannable.subSequence(start, end).toString();
    }

    private static boolean isArrayFull(boolean[] array) {
        for (boolean z : array) {
            if (!z) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<HtmlLink> getLinks() {
        return this.mLinks;
    }

    public Spannable getSpannable() {
        return this.mSpannable;
    }

    public void setSpannable(Spannable mSpannable) {
        this.mSpannable = mSpannable;
        this.mTextLength = mSpannable.length();
    }
}
