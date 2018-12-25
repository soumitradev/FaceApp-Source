package com.koushikdutta.async.http;

import android.net.Uri;

public class AsyncHttpHead extends AsyncHttpRequest {
    public static final String METHOD = "HEAD";

    public AsyncHttpHead(Uri uri) {
        super(uri, "HEAD");
    }
}
