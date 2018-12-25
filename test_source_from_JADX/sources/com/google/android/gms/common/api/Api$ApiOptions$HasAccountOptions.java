package com.google.android.gms.common.api;

import android.accounts.Account;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;

public interface Api$ApiOptions$HasAccountOptions extends HasOptions, Api$ApiOptions$NotRequiredOptions {
    Account getAccount();
}
