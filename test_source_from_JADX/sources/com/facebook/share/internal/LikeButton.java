package com.facebook.share.internal;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.C0410R;
import com.facebook.FacebookButtonBase;
import com.facebook.internal.AnalyticsEvents;

public class LikeButton extends FacebookButtonBase {
    public LikeButton(Context context, boolean isLiked) {
        super(context, null, 0, 0, AnalyticsEvents.EVENT_LIKE_BUTTON_CREATE, AnalyticsEvents.EVENT_LIKE_BUTTON_DID_TAP);
        setSelected(isLiked);
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        updateForLikeStatus();
    }

    protected void configureButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.configureButton(context, attrs, defStyleAttr, defStyleRes);
        updateForLikeStatus();
    }

    protected int getDefaultRequestCode() {
        return 0;
    }

    protected int getDefaultStyleResource() {
        return C0410R.style.com_facebook_button_like;
    }

    private void updateForLikeStatus() {
        if (isSelected()) {
            setCompoundDrawablesWithIntrinsicBounds(C0410R.drawable.com_facebook_button_like_icon_selected, 0, 0, 0);
            setText(getResources().getString(C0410R.string.com_facebook_like_button_liked));
            return;
        }
        setCompoundDrawablesWithIntrinsicBounds(C0410R.drawable.com_facebook_button_icon, 0, 0, 0);
        setText(getResources().getString(C0410R.string.com_facebook_like_button_not_liked));
    }
}
