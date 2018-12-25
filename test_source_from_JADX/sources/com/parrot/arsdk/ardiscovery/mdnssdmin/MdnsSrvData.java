package com.parrot.arsdk.ardiscovery.mdnssdmin;

public class MdnsSrvData {
    private final int port;
    private final String target;
    private final long ttl;

    public MdnsSrvData(int port, String target, long ttl) {
        this.port = port;
        this.target = target;
        this.ttl = ttl;
    }

    public int getPort() {
        return this.port;
    }

    public String getTarget() {
        return this.target;
    }

    public long getTtl() {
        return this.ttl;
    }
}
