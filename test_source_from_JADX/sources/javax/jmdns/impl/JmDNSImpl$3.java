package javax.jmdns.impl;

import javax.jmdns.ServiceEvent;
import javax.jmdns.impl.ListenerStatus.ServiceTypeListenerStatus;

class JmDNSImpl$3 implements Runnable {
    final /* synthetic */ JmDNSImpl this$0;
    final /* synthetic */ ServiceEvent val$event;
    final /* synthetic */ ServiceTypeListenerStatus val$status;

    JmDNSImpl$3(JmDNSImpl jmDNSImpl, ServiceTypeListenerStatus serviceTypeListenerStatus, ServiceEvent serviceEvent) {
        this.this$0 = jmDNSImpl;
        this.val$status = serviceTypeListenerStatus;
        this.val$event = serviceEvent;
    }

    public void run() {
        this.val$status.subTypeForServiceTypeAdded(this.val$event);
    }
}
