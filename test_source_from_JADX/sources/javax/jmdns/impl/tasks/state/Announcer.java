package javax.jmdns.impl.tasks.state;

import java.io.IOException;
import java.util.Timer;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSState;

public class Announcer extends DNSStateTask {
    static Logger logger = Logger.getLogger(Announcer.class.getName());

    public Announcer(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl, DNSStateTask.defaultTTL());
        setTaskState(DNSState.ANNOUNCING_1);
        associate(DNSState.ANNOUNCING_1);
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Announcer(");
        stringBuilder.append(getDns() != null ? getDns().getName() : "");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" state: ");
        stringBuilder.append(getTaskState());
        return stringBuilder.toString();
    }

    public void start(Timer timer) {
        if (!getDns().isCanceling() && !getDns().isCanceled()) {
            timer.schedule(this, 1000, 1000);
        }
    }

    public boolean cancel() {
        removeAssociation();
        return super.cancel();
    }

    public String getTaskDescription() {
        return "announcing";
    }

    protected boolean checkRunCondition() {
        return (getDns().isCanceling() || getDns().isCanceled()) ? false : true;
    }

    protected DNSOutgoing createOugoing() {
        return new DNSOutgoing(33792);
    }

    protected DNSOutgoing buildOutgoingForDNS(DNSOutgoing out) throws IOException {
        DNSOutgoing newOut = out;
        for (DNSRecord answer : getDns().getLocalHost().answers(true, getTTL())) {
            newOut = addAnswer(newOut, null, answer);
        }
        return newOut;
    }

    protected DNSOutgoing buildOutgoingForInfo(ServiceInfoImpl info, DNSOutgoing out) throws IOException {
        DNSOutgoing newOut = out;
        for (DNSRecord answer : info.answers(true, getTTL(), getDns().getLocalHost())) {
            newOut = addAnswer(newOut, null, answer);
        }
        return newOut;
    }

    protected void recoverTask(Throwable e) {
        getDns().recover();
    }

    protected void advanceTask() {
        setTaskState(getTaskState().advance());
        if (!getTaskState().isAnnouncing()) {
            cancel();
            getDns().startRenewer();
        }
    }
}
