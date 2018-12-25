package uk.co.deanwild.flowtextview.helpers;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.util.Iterator;
import org.catrobat.catroid.common.BrickValues;
import uk.co.deanwild.flowtextview.listeners.OnLinkClickListener;
import uk.co.deanwild.flowtextview.models.HtmlLink;

public class ClickHandler implements OnTouchListener {
    private double distance = BrickValues.SET_COLOR_TO;
    private OnLinkClickListener mOnLinkClickListener;
    private final SpanParser mSpanParser;
    private float x1;
    private float x2;
    private float y1;
    private float y2 = 0.0f;

    public ClickHandler(SpanParser spanParser) {
        this.mSpanParser = spanParser;
    }

    public boolean onTouch(View view, MotionEvent event) {
        int event_code = event.getAction();
        if (event_code == 0) {
            this.distance = BrickValues.SET_COLOR_TO;
            this.x1 = event.getX();
            this.y1 = event.getY();
        }
        if (event_code == 2) {
            this.x2 = event.getX();
            this.y2 = event.getY();
            this.distance = getPointDistance(this.x1, this.y1, this.x2, this.y2);
        }
        if (this.distance >= 10.0d) {
            return false;
        }
        if (event_code == 1) {
            onClick(event.getX(), event.getY());
        }
        return true;
    }

    private void onClick(float x, float y) {
        Iterator i$ = this.mSpanParser.getLinks().iterator();
        while (i$.hasNext()) {
            HtmlLink link = (HtmlLink) i$.next();
            float tlX = link.xOffset;
            float tlY = link.yOffset;
            float brX = link.xOffset + link.width;
            float brY = link.yOffset + link.height;
            if (x > tlX && x < brX && y > tlY && y < brY) {
                onLinkClick(link.url);
                return;
            }
        }
    }

    private void onLinkClick(String url) {
        if (this.mOnLinkClickListener != null) {
            this.mOnLinkClickListener.onLinkClick(url);
        }
    }

    private static double getPointDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow((double) (x1 - x2), 2.0d) + Math.pow((double) (y1 - y2), 2.0d));
    }

    public OnLinkClickListener getOnLinkClickListener() {
        return this.mOnLinkClickListener;
    }

    public void setOnLinkClickListener(OnLinkClickListener mOnLinkClickListener) {
        this.mOnLinkClickListener = mOnLinkClickListener;
    }
}
