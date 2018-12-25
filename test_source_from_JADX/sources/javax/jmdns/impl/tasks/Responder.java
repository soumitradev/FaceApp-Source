package javax.jmdns.impl.tasks;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.DNSIncoming;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.constants.DNSConstants;

public class Responder extends DNSTask {
    static Logger logger = Logger.getLogger(Responder.class.getName());
    private final DNSIncoming _in;
    private final boolean _unicast;

    public Responder(JmDNSImpl jmDNSImpl, DNSIncoming in, int port) {
        super(jmDNSImpl);
        this._in = in;
        this._unicast = port != DNSConstants.MDNS_PORT;
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Responder(");
        stringBuilder.append(getDns() != null ? getDns().getName() : "");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(" incomming: ");
        stringBuilder.append(this._in);
        return stringBuilder.toString();
    }

    public void start(Timer timer) {
        boolean iAmTheOnlyOne = true;
        for (DNSQuestion question : this._in.getQuestions()) {
            if (logger.isLoggable(Level.FINEST)) {
                Logger logger = logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getName());
                stringBuilder.append("start() question=");
                stringBuilder.append(question);
                logger.finest(stringBuilder.toString());
            }
            iAmTheOnlyOne = question.iAmTheOnlyOne(getDns());
            if (!iAmTheOnlyOne) {
                break;
            }
        }
        int delay = (!iAmTheOnlyOne || this._in.isTruncated()) ? (JmDNSImpl.getRandom().nextInt(96) + 20) - this._in.elapseSinceArrival() : 0;
        if (delay < 0) {
            delay = 0;
        }
        if (logger.isLoggable(Level.FINEST)) {
            Logger logger2 = logger;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(getName());
            stringBuilder2.append("start() Responder chosen delay=");
            stringBuilder2.append(delay);
            logger2.finest(stringBuilder2.toString());
        }
        if (!getDns().isCanceling() && !getDns().isCanceled()) {
            timer.schedule(this, (long) delay);
        }
    }

    public void run() {
        getDns().respondToQuery(this._in);
        Set<DNSQuestion> questions = new HashSet();
        Set<DNSRecord> answers = new HashSet();
        if (getDns().isAnnounced()) {
            StringBuilder stringBuilder;
            try {
                Logger logger;
                for (DNSQuestion question : this._in.getQuestions()) {
                    if (logger.isLoggable(Level.FINER)) {
                        logger = logger;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(getName());
                        stringBuilder.append("run() JmDNS responding to: ");
                        stringBuilder.append(question);
                        logger.finer(stringBuilder.toString());
                    }
                    if (this._unicast) {
                        questions.add(question);
                    }
                    question.addAnswers(getDns(), answers);
                }
                long now = System.currentTimeMillis();
                for (DNSRecord knownAnswer : this._in.getAnswers()) {
                    if (knownAnswer.isStale(now)) {
                        answers.remove(knownAnswer);
                        if (logger.isLoggable(Level.FINER)) {
                            Logger logger2 = logger;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append(getName());
                            stringBuilder2.append("JmDNS Responder Known Answer Removed");
                            logger2.finer(stringBuilder2.toString());
                        }
                    }
                }
                if (!answers.isEmpty()) {
                    if (logger.isLoggable(Level.FINER)) {
                        logger = logger;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(getName());
                        stringBuilder.append("run() JmDNS responding");
                        logger.finer(stringBuilder.toString());
                    }
                    DNSOutgoing out = new DNSOutgoing(33792, this._unicast ^ 1, this._in.getSenderUDPPayload());
                    out.setId(this._in.getId());
                    for (DNSQuestion question2 : questions) {
                        if (question2 != null) {
                            out = addQuestion(out, question2);
                        }
                    }
                    for (DNSRecord answer : answers) {
                        if (answer != null) {
                            out = addAnswer(out, this._in, answer);
                        }
                    }
                    if (!out.isEmpty()) {
                        getDns().send(out);
                    }
                }
            } catch (Throwable e) {
                Logger logger3 = logger;
                Level level = Level.WARNING;
                stringBuilder = new StringBuilder();
                stringBuilder.append(getName());
                stringBuilder.append("run() exception ");
                logger3.log(level, stringBuilder.toString(), e);
                getDns().close();
            }
        }
    }
}
