package javax.jmdns.impl.tasks.state;

import com.badlogic.gdx.Input.Keys;
import java.io.IOException;
import java.util.Timer;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.DNSRecord.Service;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;

public class Prober extends DNSStateTask {
    static Logger logger = Logger.getLogger(Prober.class.getName());

    public Prober(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl, DNSStateTask.defaultTTL());
        setTaskState(DNSState.PROBING_1);
        associate(DNSState.PROBING_1);
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Prober(");
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
        long now = System.currentTimeMillis();
        if (now - getDns().getLastThrottleIncrement() < DNSConstants.CLOSE_TIMEOUT) {
            getDns().setThrottle(getDns().getThrottle() + 1);
        } else {
            getDns().setThrottle(1);
        }
        getDns().setLastThrottleIncrement(now);
        if (getDns().isAnnounced() && getDns().getThrottle() < 10) {
            timer.schedule(this, (long) JmDNSImpl.getRandom().nextInt(Keys.F8), 250);
        } else if (!getDns().isCanceling() && !getDns().isCanceled()) {
            timer.schedule(this, 1000, 1000);
        }
    }

    public boolean cancel() {
        removeAssociation();
        return super.cancel();
    }

    public String getTaskDescription() {
        return "probing";
    }

    protected boolean checkRunCondition() {
        return (getDns().isCanceling() || getDns().isCanceled()) ? false : true;
    }

    protected DNSOutgoing createOugoing() {
        return new DNSOutgoing(0);
    }

    protected DNSOutgoing buildOutgoingForDNS(DNSOutgoing out) throws IOException {
        DNSOutgoing newOut = out;
        newOut.addQuestion(DNSQuestion.newQuestion(getDns().getLocalHost().getName(), DNSRecordType.TYPE_ANY, DNSRecordClass.CLASS_IN, false));
        for (DNSRecord answer : getDns().getLocalHost().answers(false, getTTL())) {
            newOut = addAuthoritativeAnswer(newOut, answer);
        }
        return newOut;
    }

    protected DNSOutgoing buildOutgoingForInfo(ServiceInfoImpl info, DNSOutgoing out) throws IOException {
        return addAuthoritativeAnswer(addQuestion(out, DNSQuestion.newQuestion(info.getQualifiedName(), DNSRecordType.TYPE_ANY, DNSRecordClass.CLASS_IN, false)), new Service(info.getQualifiedName(), DNSRecordClass.CLASS_IN, false, getTTL(), info.getPriority(), info.getWeight(), info.getPort(), getDns().getLocalHost().getName()));
    }

    protected void recoverTask(Throwable e) {
        getDns().recover();
    }

    protected void advanceTask() {
        setTaskState(getTaskState().advance());
        if (!getTaskState().isProbing()) {
            cancel();
            getDns().startAnnouncer();
        }
    }
}
