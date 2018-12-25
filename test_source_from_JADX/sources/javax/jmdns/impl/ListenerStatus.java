package javax.jmdns.impl;

import java.util.EventListener;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;

public class ListenerStatus<T extends EventListener> {
    public static final boolean ASYNCHONEOUS = false;
    public static final boolean SYNCHONEOUS = true;
    private final T _listener;
    private final boolean _synch;

    public static class ServiceListenerStatus extends ListenerStatus<ServiceListener> {
        private static Logger logger = Logger.getLogger(ServiceListenerStatus.class.getName());
        private final ConcurrentMap<String, ServiceInfo> _addedServices = new ConcurrentHashMap(32);

        public ServiceListenerStatus(ServiceListener listener, boolean synch) {
            super(listener, synch);
        }

        void serviceAdded(ServiceEvent event) {
            String qualifiedName = new StringBuilder();
            qualifiedName.append(event.getName());
            qualifiedName.append(".");
            qualifiedName.append(event.getType());
            if (this._addedServices.putIfAbsent(qualifiedName.toString(), event.getInfo().clone()) == null) {
                ((ServiceListener) getListener()).serviceAdded(event);
                ServiceInfo info = event.getInfo();
                if (info != null && info.hasData()) {
                    ((ServiceListener) getListener()).serviceResolved(event);
                }
                return;
            }
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Service Added called for a service already added: ");
            stringBuilder.append(event);
            logger.finer(stringBuilder.toString());
        }

        void serviceRemoved(ServiceEvent event) {
            String qualifiedName = new StringBuilder();
            qualifiedName.append(event.getName());
            qualifiedName.append(".");
            qualifiedName.append(event.getType());
            qualifiedName = qualifiedName.toString();
            if (this._addedServices.remove(qualifiedName, this._addedServices.get(qualifiedName))) {
                ((ServiceListener) getListener()).serviceRemoved(event);
                return;
            }
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Service Removed called for a service already removed: ");
            stringBuilder.append(event);
            logger.finer(stringBuilder.toString());
        }

        synchronized void serviceResolved(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            if (info == null || !info.hasData()) {
                Logger logger = logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Service Resolved called for an unresolved event: ");
                stringBuilder.append(event);
                logger.warning(stringBuilder.toString());
            } else {
                String qualifiedName = new StringBuilder();
                qualifiedName.append(event.getName());
                qualifiedName.append(".");
                qualifiedName.append(event.getType());
                qualifiedName = qualifiedName.toString();
                ServiceInfo previousServiceInfo = (ServiceInfo) this._addedServices.get(qualifiedName);
                if (_sameInfo(info, previousServiceInfo)) {
                    Logger logger2 = logger;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Service Resolved called for a service already resolved: ");
                    stringBuilder2.append(event);
                    logger2.finer(stringBuilder2.toString());
                } else if (previousServiceInfo == null) {
                    if (this._addedServices.putIfAbsent(qualifiedName, info.clone()) == null) {
                        ((ServiceListener) getListener()).serviceResolved(event);
                    }
                } else if (this._addedServices.replace(qualifiedName, previousServiceInfo, info.clone())) {
                    ((ServiceListener) getListener()).serviceResolved(event);
                }
            }
        }

        private static final boolean _sameInfo(ServiceInfo info, ServiceInfo lastInfo) {
            if (info == null || lastInfo == null || !info.equals(lastInfo)) {
                return false;
            }
            byte[] text = info.getTextBytes();
            byte[] lastText = lastInfo.getTextBytes();
            if (text.length != lastText.length) {
                return false;
            }
            for (int i = 0; i < text.length; i++) {
                if (text[i] != lastText[i]) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            StringBuilder aLog = new StringBuilder(2048);
            aLog.append("[Status for ");
            aLog.append(((ServiceListener) getListener()).toString());
            if (this._addedServices.isEmpty()) {
                aLog.append(" no type event ");
            } else {
                aLog.append(" (");
                for (String service : this._addedServices.keySet()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(service);
                    stringBuilder.append(", ");
                    aLog.append(stringBuilder.toString());
                }
                aLog.append(") ");
            }
            aLog.append("]");
            return aLog.toString();
        }
    }

    public static class ServiceTypeListenerStatus extends ListenerStatus<ServiceTypeListener> {
        private static Logger logger = Logger.getLogger(ServiceTypeListenerStatus.class.getName());
        private final ConcurrentMap<String, String> _addedTypes = new ConcurrentHashMap(32);

        public ServiceTypeListenerStatus(ServiceTypeListener listener, boolean synch) {
            super(listener, synch);
        }

        void serviceTypeAdded(ServiceEvent event) {
            if (this._addedTypes.putIfAbsent(event.getType(), event.getType()) == null) {
                ((ServiceTypeListener) getListener()).serviceTypeAdded(event);
                return;
            }
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Service Type Added called for a service type already added: ");
            stringBuilder.append(event);
            logger.finest(stringBuilder.toString());
        }

        void subTypeForServiceTypeAdded(ServiceEvent event) {
            if (this._addedTypes.putIfAbsent(event.getType(), event.getType()) == null) {
                ((ServiceTypeListener) getListener()).subTypeForServiceTypeAdded(event);
                return;
            }
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Service Sub Type Added called for a service sub type already added: ");
            stringBuilder.append(event);
            logger.finest(stringBuilder.toString());
        }

        public String toString() {
            StringBuilder aLog = new StringBuilder(2048);
            aLog.append("[Status for ");
            aLog.append(((ServiceTypeListener) getListener()).toString());
            if (this._addedTypes.isEmpty()) {
                aLog.append(" no type event ");
            } else {
                aLog.append(" (");
                for (String type : this._addedTypes.keySet()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(type);
                    stringBuilder.append(", ");
                    aLog.append(stringBuilder.toString());
                }
                aLog.append(") ");
            }
            aLog.append("]");
            return aLog.toString();
        }
    }

    public ListenerStatus(T listener, boolean synch) {
        this._listener = listener;
        this._synch = synch;
    }

    public T getListener() {
        return this._listener;
    }

    public boolean isSynchronous() {
        return this._synch;
    }

    public int hashCode() {
        return getListener().hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ListenerStatus) && getListener().equals(((ListenerStatus) obj).getListener());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Status for ");
        stringBuilder.append(getListener().toString());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
