package javax.jmdns;

import java.util.Collection;

public interface JmDNS$Delegate {
    void cannotRecoverFromIOError(JmDNS jmDNS, Collection<ServiceInfo> collection);
}
