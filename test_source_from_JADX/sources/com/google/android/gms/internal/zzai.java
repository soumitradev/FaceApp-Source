package com.google.android.gms.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public abstract class zzai implements zzar {
    public abstract zzaq zza(zzr<?> zzr, Map<String, String> map) throws IOException, zza;

    @Deprecated
    public final HttpResponse zzb(zzr<?> zzr, Map<String, String> map) throws IOException, zza {
        zzaq zza = zza(zzr, map);
        HttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), zza.zza(), ""));
        List arrayList = new ArrayList();
        for (zzl zzl : zza.zzb()) {
            arrayList.add(new BasicHeader(zzl.zza(), zzl.zzb()));
        }
        basicHttpResponse.setHeaders((Header[]) arrayList.toArray(new Header[arrayList.size()]));
        InputStream zzd = zza.zzd();
        if (zzd != null) {
            HttpEntity basicHttpEntity = new BasicHttpEntity();
            basicHttpEntity.setContent(zzd);
            basicHttpEntity.setContentLength((long) zza.zzc());
            basicHttpResponse.setEntity(basicHttpEntity);
        }
        return basicHttpResponse;
    }
}
