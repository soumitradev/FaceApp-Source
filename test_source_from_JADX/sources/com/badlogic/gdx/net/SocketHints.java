package com.badlogic.gdx.net;

public class SocketHints {
    public int connectTimeout = 5000;
    public boolean keepAlive = true;
    public boolean linger = false;
    public int lingerDuration = 0;
    public int performancePrefBandwidth = 0;
    public int performancePrefConnectionTime = 0;
    public int performancePrefLatency = 1;
    public int receiveBufferSize = 4096;
    public int sendBufferSize = 4096;
    public int socketTimeout = 0;
    public boolean tcpNoDelay = true;
    public int trafficClass = 20;
}
