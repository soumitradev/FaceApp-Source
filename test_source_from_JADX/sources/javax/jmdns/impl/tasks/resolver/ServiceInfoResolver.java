package javax.jmdns.impl.tasks.resolver;

import java.io.IOException;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

public class ServiceInfoResolver extends DNSResolverTask {
    private final ServiceInfoImpl _info;

    public ServiceInfoResolver(JmDNSImpl jmDNSImpl, ServiceInfoImpl info) {
        super(jmDNSImpl);
        this._info = info;
        info.setDns(getDns());
        getDns().addListener(info, DNSQuestion.newQuestion(info.getQualifiedName(), DNSRecordType.TYPE_ANY, DNSRecordClass.CLASS_IN, false));
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServiceInfoResolver(");
        stringBuilder.append(getDns() != null ? getDns().getName() : "");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean cancel() {
        boolean result = super.cancel();
        if (!this._info.isPersistent()) {
            getDns().removeListener(this._info);
        }
        return result;
    }

    protected DNSOutgoing addAnswers(DNSOutgoing out) throws IOException {
        DNSOutgoing newOut = out;
        if (this._info.hasData()) {
            return newOut;
        }
        long now = System.currentTimeMillis();
        newOut = addAnswer(addAnswer(newOut, (DNSRecord) getDns().getCache().getDNSEntry(this._info.getQualifiedName(), DNSRecordType.TYPE_SRV, DNSRecordClass.CLASS_IN), now), (DNSRecord) getDns().getCache().getDNSEntry(this._info.getQualifiedName(), DNSRecordType.TYPE_TXT, DNSRecordClass.CLASS_IN), now);
        if (this._info.getServer().length() > 0) {
            return addAnswer(addAnswer(newOut, (DNSRecord) getDns().getCache().getDNSEntry(this._info.getServer(), DNSRecordType.TYPE_A, DNSRecordClass.CLASS_IN), now), (DNSRecord) getDns().getCache().getDNSEntry(this._info.getServer(), DNSRecordType.TYPE_AAAA, DNSRecordClass.CLASS_IN), now);
        }
        return newOut;
    }

    protected DNSOutgoing addQuestions(DNSOutgoing out) throws IOException {
        DNSOutgoing newOut = out;
        if (this._info.hasData()) {
            return newOut;
        }
        newOut = addQuestion(addQuestion(newOut, DNSQuestion.newQuestion(this._info.getQualifiedName(), DNSRecordType.TYPE_SRV, DNSRecordClass.CLASS_IN, false)), DNSQuestion.newQuestion(this._info.getQualifiedName(), DNSRecordType.TYPE_TXT, DNSRecordClass.CLASS_IN, false));
        if (this._info.getServer().length() > 0) {
            return addQuestion(addQuestion(newOut, DNSQuestion.newQuestion(this._info.getServer(), DNSRecordType.TYPE_A, DNSRecordClass.CLASS_IN, false)), DNSQuestion.newQuestion(this._info.getServer(), DNSRecordType.TYPE_AAAA, DNSRecordClass.CLASS_IN, false));
        }
        return newOut;
    }

    protected String description() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("querying service info: ");
        stringBuilder.append(this._info != null ? this._info.getQualifiedName() : "null");
        return stringBuilder.toString();
    }
}
