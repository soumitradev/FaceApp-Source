package uk.co.deanwild.flowtextview.models;

import android.text.TextPaint;

public class HtmlObject {
    public String content;
    public int end;
    public TextPaint paint;
    public boolean recycle = false;
    public int start;
    public float xOffset;

    public HtmlObject(String content, int start, int end, float xOffset, TextPaint paint) {
        this.content = content;
        this.start = start;
        this.end = end;
        this.xOffset = xOffset;
        this.paint = paint;
    }
}
