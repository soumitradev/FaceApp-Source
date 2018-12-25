package com.google.android.gms.internal;

import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import org.xmlpull.v1.XmlPullParserException;

class zzasj<T extends zzasi> extends zzarh {
    private zzask<T> zza;

    public zzasj(zzark zzark, zzask<T> zzask) {
        super(zzark);
        this.zza = zzask;
    }

    private final T zza(XmlResourceParser xmlResourceParser) {
        String str;
        try {
            xmlResourceParser.next();
            int eventType = xmlResourceParser.getEventType();
            while (eventType != 1) {
                if (xmlResourceParser.getEventType() == 2) {
                    String toLowerCase = xmlResourceParser.getName().toLowerCase();
                    String trim;
                    if (toLowerCase.equals("screenname")) {
                        toLowerCase = xmlResourceParser.getAttributeValue(null, "name");
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(toLowerCase) || TextUtils.isEmpty(trim))) {
                            this.zza.zza(toLowerCase, trim);
                        }
                    } else if (toLowerCase.equals("string")) {
                        r0 = xmlResourceParser.getAttributeValue(null, "name");
                        trim = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(r0) || trim == null)) {
                            this.zza.zzb(r0, trim);
                        }
                    } else if (toLowerCase.equals("bool")) {
                        toLowerCase = xmlResourceParser.getAttributeValue(null, "name");
                        r1 = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(toLowerCase) || TextUtils.isEmpty(r1))) {
                            try {
                                this.zza.zza(toLowerCase, Boolean.parseBoolean(r1));
                            } catch (NumberFormatException e) {
                                r0 = e;
                                str = "Error parsing bool configuration value";
                                zzc(str, r1, r0);
                                eventType = xmlResourceParser.next();
                            }
                        }
                    } else if (toLowerCase.equals("integer")) {
                        toLowerCase = xmlResourceParser.getAttributeValue(null, "name");
                        r1 = xmlResourceParser.nextText().trim();
                        if (!(TextUtils.isEmpty(toLowerCase) || TextUtils.isEmpty(r1))) {
                            try {
                                this.zza.zza(toLowerCase, Integer.parseInt(r1));
                            } catch (NumberFormatException e2) {
                                r0 = e2;
                                str = "Error parsing int configuration value";
                                zzc(str, r1, r0);
                                eventType = xmlResourceParser.next();
                            }
                        }
                    }
                }
                eventType = xmlResourceParser.next();
            }
        } catch (XmlPullParserException e3) {
            zze("Error parsing tracker configuration file", e3);
        }
        return this.zza.zza();
    }

    public final T zza(int i) {
        try {
            return zza(zzi().zzb().getResources().getXml(i));
        } catch (NotFoundException e) {
            zzd("inflate() called with unknown resourceId", e);
            return null;
        }
    }
}
