package com.parrot.freeflight.receivers;

import android.net.NetworkInfo;

public interface NetworkChangeReceiverDelegate {
    void onNetworkChanged(NetworkInfo networkInfo);
}
