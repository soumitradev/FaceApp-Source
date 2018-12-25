package uk.co.deanwild.flowtextview.helpers;

import android.text.TextPaint;
import java.util.ArrayList;
import java.util.Iterator;

public class PaintHelper {
    private ArrayList<TextPaint> mPaintHeap = new ArrayList();

    public TextPaint getPaintFromHeap() {
        if (this.mPaintHeap.size() > 0) {
            return (TextPaint) this.mPaintHeap.remove(0);
        }
        return new TextPaint(1);
    }

    public void setColor(int color) {
        Iterator i$ = this.mPaintHeap.iterator();
        while (i$.hasNext()) {
            ((TextPaint) i$.next()).setColor(color);
        }
    }

    public void recyclePaint(TextPaint paint) {
        this.mPaintHeap.add(paint);
    }
}
