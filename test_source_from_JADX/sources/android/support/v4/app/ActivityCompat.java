package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v13.view.DragAndDropPermissionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.DragEvent;
import android.view.View;

public class ActivityCompat extends ContextCompat {
    private static ActivityCompat$PermissionCompatDelegate sDelegate;

    protected ActivityCompat() {
    }

    public static void setPermissionCompatDelegate(@Nullable ActivityCompat$PermissionCompatDelegate delegate) {
        sDelegate = delegate;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static ActivityCompat$PermissionCompatDelegate getPermissionCompatDelegate() {
        return sDelegate;
    }

    @Deprecated
    public static boolean invalidateOptionsMenu(Activity activity) {
        activity.invalidateOptionsMenu();
        return true;
    }

    public static void startActivityForResult(@NonNull Activity activity, @NonNull Intent intent, int requestCode, @Nullable Bundle options) {
        if (VERSION.SDK_INT >= 16) {
            activity.startActivityForResult(intent, requestCode, options);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static void startIntentSenderForResult(@NonNull Activity activity, @NonNull IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws SendIntentException {
        if (VERSION.SDK_INT >= 16) {
            activity.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        } else {
            activity.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
        }
    }

    public static void finishAffinity(@NonNull Activity activity) {
        if (VERSION.SDK_INT >= 16) {
            activity.finishAffinity();
        } else {
            activity.finish();
        }
    }

    public static void finishAfterTransition(@NonNull Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            activity.finishAfterTransition();
        } else {
            activity.finish();
        }
    }

    @Nullable
    public static Uri getReferrer(@NonNull Activity activity) {
        if (VERSION.SDK_INT >= 22) {
            return activity.getReferrer();
        }
        Intent intent = activity.getIntent();
        Uri referrer = (Uri) intent.getParcelableExtra("android.intent.extra.REFERRER");
        if (referrer != null) {
            return referrer;
        }
        String referrerName = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (referrerName != null) {
            return Uri.parse(referrerName);
        }
        return null;
    }

    @NonNull
    public static <T extends View> T requireViewById(@NonNull Activity activity, @IdRes int id) {
        T view = activity.findViewById(id);
        if (view != null) {
            return view;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Activity");
    }

    public static void setEnterSharedElementCallback(@NonNull Activity activity, @Nullable SharedElementCallback callback) {
        SharedElementCallback sharedElementCallback = null;
        if (VERSION.SDK_INT >= 23) {
            if (callback != null) {
                sharedElementCallback = new ActivityCompat$SharedElementCallback23Impl(callback);
            }
            activity.setEnterSharedElementCallback(sharedElementCallback);
        } else if (VERSION.SDK_INT >= 21) {
            if (callback != null) {
                sharedElementCallback = new ActivityCompat$SharedElementCallback21Impl(callback);
            }
            activity.setEnterSharedElementCallback(sharedElementCallback);
        }
    }

    public static void setExitSharedElementCallback(@NonNull Activity activity, @Nullable SharedElementCallback callback) {
        SharedElementCallback sharedElementCallback = null;
        if (VERSION.SDK_INT >= 23) {
            if (callback != null) {
                sharedElementCallback = new ActivityCompat$SharedElementCallback23Impl(callback);
            }
            activity.setExitSharedElementCallback(sharedElementCallback);
        } else if (VERSION.SDK_INT >= 21) {
            if (callback != null) {
                sharedElementCallback = new ActivityCompat$SharedElementCallback21Impl(callback);
            }
            activity.setExitSharedElementCallback(sharedElementCallback);
        }
    }

    public static void postponeEnterTransition(@NonNull Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            activity.postponeEnterTransition();
        }
    }

    public static void startPostponedEnterTransition(@NonNull Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            activity.startPostponedEnterTransition();
        }
    }

    public static void requestPermissions(@NonNull Activity activity, @NonNull String[] permissions, @IntRange(from = 0) int requestCode) {
        if (sDelegate == null || !sDelegate.requestPermissions(activity, permissions, requestCode)) {
            if (VERSION.SDK_INT >= 23) {
                if (activity instanceof ActivityCompat$RequestPermissionsRequestCodeValidator) {
                    ((ActivityCompat$RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(requestCode);
                }
                activity.requestPermissions(permissions, requestCode);
            } else if (activity instanceof ActivityCompat$OnRequestPermissionsResultCallback) {
                new Handler(Looper.getMainLooper()).post(new ActivityCompat$1(permissions, activity, requestCode));
            }
        }
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String permission) {
        if (VERSION.SDK_INT >= 23) {
            return activity.shouldShowRequestPermissionRationale(permission);
        }
        return false;
    }

    @Nullable
    public static DragAndDropPermissionsCompat requestDragAndDropPermissions(Activity activity, DragEvent dragEvent) {
        return DragAndDropPermissionsCompat.request(activity, dragEvent);
    }
}
