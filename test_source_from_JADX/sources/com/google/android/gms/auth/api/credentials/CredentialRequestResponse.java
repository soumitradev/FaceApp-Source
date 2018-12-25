package com.google.android.gms.auth.api.credentials;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.internal.Hide;

@Hide
public class CredentialRequestResponse extends Response<CredentialRequestResult> {
    public Credential getCredential() {
        return ((CredentialRequestResult) getResult()).getCredential();
    }
}
