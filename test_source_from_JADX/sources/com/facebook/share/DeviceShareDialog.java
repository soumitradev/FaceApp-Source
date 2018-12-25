package com.facebook.share;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.internal.FragmentWrapper;
import com.facebook.share.internal.DeviceShareDialogFragment;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.util.List;

public class DeviceShareDialog extends FacebookDialogBase<ShareContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.DeviceShare.toRequestCode();

    public static class Result {
    }

    public DeviceShareDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
    }

    public DeviceShareDialog(Fragment fragment) {
        super(new FragmentWrapper(fragment), DEFAULT_REQUEST_CODE);
    }

    public DeviceShareDialog(android.support.v4.app.Fragment fragment) {
        super(new FragmentWrapper(fragment), DEFAULT_REQUEST_CODE);
    }

    protected boolean canShowImpl(ShareContent content, Object mode) {
        if (!(content instanceof ShareLinkContent)) {
            if (!(content instanceof ShareOpenGraphContent)) {
                return false;
            }
        }
        return true;
    }

    protected void showImpl(ShareContent content, Object mode) {
        if (content == null) {
            throw new FacebookException("Must provide non-null content to share");
        } else if ((content instanceof ShareLinkContent) || (content instanceof ShareOpenGraphContent)) {
            Intent intent = new Intent();
            intent.setClass(FacebookSdk.getApplicationContext(), FacebookActivity.class);
            intent.setAction(DeviceShareDialogFragment.TAG);
            intent.putExtra(FirebaseAnalytics$Param.CONTENT, content);
            startActivityForResult(intent, getRequestCode());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClass().getSimpleName());
            stringBuilder.append(" only supports ShareLinkContent or ShareOpenGraphContent");
            throw new FacebookException(stringBuilder.toString());
        }
    }

    protected List<ModeHandler> getOrderedModeHandlers() {
        return null;
    }

    protected AppCall createBaseAppCall() {
        return null;
    }

    protected void registerCallbackImpl(CallbackManagerImpl callbackManager, final FacebookCallback<Result> callback) {
        callbackManager.registerCallback(getRequestCode(), new Callback() {
            public boolean onActivityResult(int resultCode, Intent data) {
                Bundle extras = data.getExtras();
                if (data.hasExtra("error")) {
                    callback.onError(((FacebookRequestError) data.getParcelableExtra("error")).getException());
                    return true;
                }
                callback.onSuccess(new Result());
                return true;
            }
        });
    }
}
