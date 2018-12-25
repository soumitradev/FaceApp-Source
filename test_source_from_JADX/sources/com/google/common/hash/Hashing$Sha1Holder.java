package com.google.common.hash;

import io.fabric.sdk.android.services.common.CommonUtils;

class Hashing$Sha1Holder {
    static final HashFunction SHA_1 = new MessageDigestHashFunction(CommonUtils.SHA1_INSTANCE, "Hashing.sha1()");

    private Hashing$Sha1Holder() {
    }
}
