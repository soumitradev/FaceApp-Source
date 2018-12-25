package uk.co.deanwild.flowtextview.models;

import android.text.TextPaint;

public class HtmlLink extends HtmlObject {
    public float height;
    public String url;
    public float width;
    public float yOffset;

    public HtmlLink(String content, int start, int end, float xOffset, TextPaint paint, String url) {
        super(content, start, end, xOffset, paint);
        this.url = url;
    }
}
