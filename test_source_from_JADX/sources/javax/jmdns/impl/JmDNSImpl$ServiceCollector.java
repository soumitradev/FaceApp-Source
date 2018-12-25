package javax.jmdns.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

class JmDNSImpl$ServiceCollector implements ServiceListener {
    private final ConcurrentMap<String, ServiceEvent> _events = new ConcurrentHashMap();
    private final ConcurrentMap<String, ServiceInfo> _infos = new ConcurrentHashMap();
    private volatile boolean _needToWaitForInfos;
    private final String _type;

    public JmDNSImpl$ServiceCollector(String type) {
        this._type = type;
        this._needToWaitForInfos = true;
    }

    public void serviceAdded(ServiceEvent event) {
        synchronized (this) {
            ServiceInfo info = event.getInfo();
            if (info == null || !info.hasData()) {
                info = ((JmDNSImpl) event.getDNS()).resolveServiceInfo(event.getType(), event.getName(), info != null ? info.getSubtype() : "", true);
                if (info != null) {
                    this._infos.put(event.getName(), info);
                } else {
                    this._events.put(event.getName(), event);
                }
            } else {
                this._infos.put(event.getName(), info);
            }
        }
    }

    public void serviceRemoved(ServiceEvent event) {
        synchronized (this) {
            this._infos.remove(event.getName());
            this._events.remove(event.getName());
        }
    }

    public void serviceResolved(ServiceEvent event) {
        synchronized (this) {
            this._infos.put(event.getName(), event.getInfo());
            this._events.remove(event.getName());
        }
    }

    public ServiceInfo[] list(long timeout) {
        if (this._infos.isEmpty() || !this._events.isEmpty() || this._needToWaitForInfos) {
            long loops = timeout / 200;
            if (loops < 1) {
                loops = 1;
            }
            for (int i = 0; ((long) i) < loops; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
                if (this._events.isEmpty() && !this._infos.isEmpty() && !this._needToWaitForInfos) {
                    break;
                }
            }
        }
        this._needToWaitForInfos = false;
        return (ServiceInfo[]) this._infos.values().toArray(new ServiceInfo[this._infos.size()]);
    }

    public String toString() {
        StringBuffer aLog = new StringBuffer();
        aLog.append("\n\tType: ");
        aLog.append(this._type);
        if (this._infos.isEmpty()) {
            aLog.append("\n\tNo services collected.");
        } else {
            aLog.append("\n\tServices");
            for (String key : this._infos.keySet()) {
                aLog.append("\n\t\tService: ");
                aLog.append(key);
                aLog.append(": ");
                aLog.append(this._infos.get(key));
            }
        }
        if (this._events.isEmpty()) {
            aLog.append("\n\tNo event queued.");
        } else {
            aLog.append("\n\tEvents");
            for (String key2 : this._events.keySet()) {
                aLog.append("\n\t\tEvent: ");
                aLog.append(key2);
                aLog.append(": ");
                aLog.append(this._events.get(key2));
            }
        }
        return aLog.toString();
    }
}
