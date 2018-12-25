package javax.jmdns.impl;

import javax.jmdns.impl.DNSStatefulObject.DefaultImplementation;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;

final class ServiceInfoImpl$ServiceInfoState extends DefaultImplementation {
    private static final long serialVersionUID = 1104131034952196820L;
    private final ServiceInfoImpl _info;

    public ServiceInfoImpl$ServiceInfoState(ServiceInfoImpl info) {
        this._info = info;
    }

    protected void setTask(DNSTask task) {
        super.setTask(task);
        if (this._task == null && this._info.needTextAnnouncing()) {
            lock();
            try {
                if (this._task == null && this._info.needTextAnnouncing()) {
                    if (this._state.isAnnounced()) {
                        setState(DNSState.ANNOUNCING_1);
                        if (getDns() != null) {
                            getDns().startAnnouncer();
                        }
                    }
                    this._info.setNeedTextAnnouncing(false);
                }
                unlock();
            } catch (Throwable th) {
                unlock();
            }
        }
    }

    public void setDns(JmDNSImpl dns) {
        super.setDns(dns);
    }
}
