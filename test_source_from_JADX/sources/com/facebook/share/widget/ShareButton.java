package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.C0410R;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.ShareContent;

public final class ShareButton extends ShareButtonBase {
    public ShareButton(Context context) {
        super(context, null, 0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    public ShareButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    public ShareButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, AnalyticsEvents.EVENT_SHARE_BUTTON_CREATE, AnalyticsEvents.EVENT_SHARE_BUTTON_DID_TAP);
    }

    protected int getDefaultStyleResource() {
        return C0410R.style.com_facebook_button_share;
    }

    protected int getDefaultRequestCode() {
        return RequestCodeOffset.Share.toRequestCode();
    }

    protected FacebookDialogBase<ShareContent, Result> getDialog() {
        ShareDialog dialog;
        if (getFragment() != null) {
            dialog = new ShareDialog(getFragment(), getRequestCode());
        } else if (getNativeFragment() == null) {
            return new ShareDialog(getActivity(), getRequestCode());
        } else {
            dialog = new ShareDialog(getNativeFragment(), getRequestCode());
        }
        return dialog;
    }
}
