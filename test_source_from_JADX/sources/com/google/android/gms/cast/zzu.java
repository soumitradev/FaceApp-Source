package com.google.android.gms.cast;

import android.support.v7.media.MediaRouter;
import android.support.v7.media.MediaRouter.Callback;
import android.support.v7.media.MediaRouter.RouteInfo;

final class zzu extends Callback {
    private /* synthetic */ CastRemoteDisplayLocalService zza;

    zzu(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zza = castRemoteDisplayLocalService;
    }

    public final void onRouteUnselected(MediaRouter mediaRouter, RouteInfo routeInfo) {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService;
        String str;
        this.zza.zza("onRouteUnselected");
        if (this.zza.zzl == null) {
            castRemoteDisplayLocalService = this.zza;
            str = "onRouteUnselected, no device was selected";
        } else if (CastDevice.getFromBundle(routeInfo.getExtras()).getDeviceId().equals(this.zza.zzl.getDeviceId())) {
            CastRemoteDisplayLocalService.stopService();
            return;
        } else {
            castRemoteDisplayLocalService = this.zza;
            str = "onRouteUnselected, device does not match";
        }
        castRemoteDisplayLocalService.zza(str);
    }
}
