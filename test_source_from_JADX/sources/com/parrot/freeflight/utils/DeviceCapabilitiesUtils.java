package com.parrot.freeflight.utils;

import android.app.Activity;
import android.media.CamcorderProfile;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import com.parrot.freeflight.drone.DroneProxy.EVideoRecorderCapability;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScreenValues;

public class DeviceCapabilitiesUtils {
    public static EVideoRecorderCapability getMaxSupportedVideoRes() {
        EVideoRecorderCapability videoResolution = EVideoRecorderCapability.VIDEO_720P;
        CamcorderProfile profile = CamcorderProfile.get(1);
        if (profile == null) {
            return videoResolution;
        }
        if (profile.videoFrameHeight >= ScreenValues.CAST_SCREEN_HEIGHT) {
            return EVideoRecorderCapability.VIDEO_720P;
        }
        if (profile.videoFrameHeight >= Constants.SCRATCH_IMAGE_DEFAULT_HEIGHT) {
            return EVideoRecorderCapability.VIDEO_360P;
        }
        if (profile.videoFrameHeight < Constants.SCRATCH_IMAGE_DEFAULT_HEIGHT) {
            return EVideoRecorderCapability.NOT_SUPPORTED;
        }
        return videoResolution;
    }

    public static void dumpScreenSizeInDips(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = context.getResources().getDisplayMetrics().density;
        float dpHeight = ((float) outMetrics.heightPixels) / density;
        float dpWidth = ((float) outMetrics.widthPixels) / density;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dpHeight);
        stringBuilder.append("dp x ");
        stringBuilder.append(dpWidth);
        stringBuilder.append("dp");
        stringBuilder.append(" density: ");
        stringBuilder.append(density);
        Log.i("Display", stringBuilder.toString());
    }
}
