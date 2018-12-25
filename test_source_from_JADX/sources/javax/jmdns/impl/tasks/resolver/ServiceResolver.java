package javax.jmdns.impl.tasks.resolver;

import java.io.IOException;
import javax.jmdns.ServiceInfo;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord.Pointer;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

public class ServiceResolver extends DNSResolverTask {
    private final String _type;

    public ServiceResolver(JmDNSImpl jmDNSImpl, String type) {
        super(jmDNSImpl);
        this._type = type;
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServiceResolver(");
        stringBuilder.append(getDns() != null ? getDns().getName() : "");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    protected DNSOutgoing addAnswers(DNSOutgoing out) throws IOException {
        DNSOutgoing newOut = out;
        long now = System.currentTimeMillis();
        for (ServiceInfo info : getDns().getServices().values()) {
            newOut = addAnswer(newOut, new Pointer(info.getType(), DNSRecordClass.CLASS_IN, false, 3600, info.getQualifiedName()), now);
        }
        return newOut;
    }

    protected DNSOutgoing addQuestions(DNSOutgoing out) throws IOException {
        return addQuestion(out, DNSQuestion.newQuestion(this._type, DNSRecordType.TYPE_PTR, DNSRecordClass.CLASS_IN, false));
    }

    protected String description() {
        return "querying service";
    }
}
