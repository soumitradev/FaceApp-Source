package com.google.common.hash;

import io.fabric.sdk.android.services.common.CommonUtils;

class Hashing$Md5Holder {
    static final HashFunction MD5 = new MessageDigestHashFunction(CommonUtils.MD5_INSTANCE, "Hashing.md5()");

    private Hashing$Md5Holder() {
    }
}
