package javax.jmdns.impl.tasks.resolver;

import java.io.IOException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.tasks.DNSTask;

public abstract class DNSResolverTask extends DNSTask {
    private static Logger logger = Logger.getLogger(DNSResolverTask.class.getName());
    protected int _count = 0;

    protected abstract DNSOutgoing addAnswers(DNSOutgoing dNSOutgoing) throws IOException;

    protected abstract DNSOutgoing addQuestions(DNSOutgoing dNSOutgoing) throws IOException;

    protected abstract String description();

    public DNSResolverTask(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" count: ");
        stringBuilder.append(this._count);
        return stringBuilder.toString();
    }

    public void start(Timer timer) {
        if (!getDns().isCanceling() && !getDns().isCanceled()) {
            timer.schedule(this, 225, 225);
        }
    }

    public void run() {
        try {
            if (!getDns().isCanceling()) {
                if (!getDns().isCanceled()) {
                    int i = this._count;
                    this._count = i + 1;
                    if (i < 3) {
                        if (logger.isLoggable(Level.FINER)) {
                            Logger logger = logger;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(getName());
                            stringBuilder.append(".run() JmDNS ");
                            stringBuilder.append(description());
                            logger.finer(stringBuilder.toString());
                        }
                        DNSOutgoing out = addQuestions(new DNSOutgoing(0));
                        if (getDns().isAnnounced()) {
                            out = addAnswers(out);
                        }
                        if (!out.isEmpty()) {
                            getDns().send(out);
                        }
                    } else {
                        cancel();
                    }
                }
            }
            cancel();
        } catch (Throwable e) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(getName());
            stringBuilder2.append(".run() exception ");
            logger2.log(level, stringBuilder2.toString(), e);
            getDns().recover();
        }
    }
}
