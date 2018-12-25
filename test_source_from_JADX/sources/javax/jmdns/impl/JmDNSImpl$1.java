package javax.jmdns.impl;

import javax.jmdns.ServiceEvent;
import javax.jmdns.impl.ListenerStatus.ServiceListenerStatus;

class JmDNSImpl$1 implements Runnable {
    final /* synthetic */ JmDNSImpl this$0;
    final /* synthetic */ ServiceListenerStatus val$listener;
    final /* synthetic */ ServiceEvent val$localEvent;

    JmDNSImpl$1(JmDNSImpl jmDNSImpl, ServiceListenerStatus serviceListenerStatus, ServiceEvent serviceEvent) {
        this.this$0 = jmDNSImpl;
        this.val$listener = serviceListenerStatus;
        this.val$localEvent = serviceEvent;
    }

    public void run() {
        this.val$listener.serviceResolved(this.val$localEvent);
    }
}
