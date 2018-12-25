package javax.jmdns.impl.tasks;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.JmDNSImpl;

public class RecordReaper extends DNSTask {
    static Logger logger = Logger.getLogger(RecordReaper.class.getName());

    public RecordReaper(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl);
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RecordReaper(");
        stringBuilder.append(getDns() != null ? getDns().getName() : "");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void start(Timer timer) {
        if (!getDns().isCanceling() && !getDns().isCanceled()) {
            timer.schedule(this, 10000, 10000);
        }
    }

    public void run() {
        if (!getDns().isCanceling()) {
            if (!getDns().isCanceled()) {
                if (logger.isLoggable(Level.FINEST)) {
                    Logger logger = logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(getName());
                    stringBuilder.append(".run() JmDNS reaping cache");
                    logger.finest(stringBuilder.toString());
                }
                getDns().cleanCache();
            }
        }
    }
}
