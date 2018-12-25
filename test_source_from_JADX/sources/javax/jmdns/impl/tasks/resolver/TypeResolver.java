package javax.jmdns.impl.tasks.resolver;

import java.io.IOException;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSQuestion;
import javax.jmdns.impl.DNSRecord.Pointer;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.JmDNSImpl$ServiceTypeEntry;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

public class TypeResolver extends DNSResolverTask {
    public TypeResolver(JmDNSImpl jmDNSImpl) {
        super(jmDNSImpl);
    }

    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TypeResolver(");
        stringBuilder.append(getDns() != null ? getDns().getName() : "");
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    protected DNSOutgoing addAnswers(DNSOutgoing out) throws IOException {
        DNSOutgoing newOut = out;
        long now = System.currentTimeMillis();
        for (String type : getDns().getServiceTypes().keySet()) {
            newOut = addAnswer(newOut, new Pointer("_services._dns-sd._udp.local.", DNSRecordClass.CLASS_IN, false, 3600, ((JmDNSImpl$ServiceTypeEntry) getDns().getServiceTypes().get(type)).getType()), now);
        }
        return newOut;
    }

    protected DNSOutgoing addQuestions(DNSOutgoing out) throws IOException {
        return addQuestion(out, DNSQuestion.newQuestion("_services._dns-sd._udp.local.", DNSRecordType.TYPE_PTR, DNSRecordClass.CLASS_IN, false));
    }

    protected String description() {
        return "querying type";
    }
}
