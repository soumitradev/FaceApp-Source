package javax.jmdns.impl.tasks.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceInfo;
import javax.jmdns.impl.DNSOutgoing;
import javax.jmdns.impl.DNSStatefulObject;
import javax.jmdns.impl.JmDNSImpl;
import javax.jmdns.impl.ServiceInfoImpl;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;
import name.antonsmirnov.firmata.FormatHelper;

public abstract class DNSStateTask extends DNSTask {
    private static int _defaultTTL = 3600;
    static Logger logger1 = Logger.getLogger(DNSStateTask.class.getName());
    private DNSState _taskState = null;
    private final int _ttl;

    protected abstract void advanceTask();

    protected abstract DNSOutgoing buildOutgoingForDNS(DNSOutgoing dNSOutgoing) throws IOException;

    protected abstract DNSOutgoing buildOutgoingForInfo(ServiceInfoImpl serviceInfoImpl, DNSOutgoing dNSOutgoing) throws IOException;

    protected abstract boolean checkRunCondition();

    protected abstract DNSOutgoing createOugoing();

    public abstract String getTaskDescription();

    protected abstract void recoverTask(Throwable th);

    public static int defaultTTL() {
        return _defaultTTL;
    }

    public static void setDefaultTTL(int value) {
        _defaultTTL = value;
    }

    public DNSStateTask(JmDNSImpl jmDNSImpl, int ttl) {
        super(jmDNSImpl);
        this._ttl = ttl;
    }

    public int getTTL() {
        return this._ttl;
    }

    protected void associate(DNSState state) {
        synchronized (getDns()) {
            getDns().associateWithTask(this, state);
        }
        for (ServiceInfo serviceInfo : getDns().getServices().values()) {
            ((ServiceInfoImpl) serviceInfo).associateWithTask(this, state);
        }
    }

    protected void removeAssociation() {
        synchronized (getDns()) {
            getDns().removeAssociationWithTask(this);
        }
        for (ServiceInfo serviceInfo : getDns().getServices().values()) {
            ((ServiceInfoImpl) serviceInfo).removeAssociationWithTask(this);
        }
    }

    public void run() {
        DNSOutgoing out = createOugoing();
        StringBuilder stringBuilder;
        Logger logger;
        try {
            if (checkRunCondition()) {
                List<DNSStatefulObject> stateObjects = new ArrayList();
                synchronized (getDns()) {
                    if (getDns().isAssociatedWithTask(this, getTaskState())) {
                        Logger logger2 = logger1;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(getName());
                        stringBuilder.append(".run() JmDNS ");
                        stringBuilder.append(getTaskDescription());
                        stringBuilder.append(FormatHelper.SPACE);
                        stringBuilder.append(getDns().getName());
                        logger2.finer(stringBuilder.toString());
                        stateObjects.add(getDns());
                        out = buildOutgoingForDNS(out);
                    }
                }
                for (ServiceInfo serviceInfo : getDns().getServices().values()) {
                    ServiceInfoImpl info = (ServiceInfoImpl) serviceInfo;
                    synchronized (info) {
                        if (info.isAssociatedWithTask(this, getTaskState())) {
                            Logger logger3 = logger1;
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append(getName());
                            stringBuilder2.append(".run() JmDNS ");
                            stringBuilder2.append(getTaskDescription());
                            stringBuilder2.append(FormatHelper.SPACE);
                            stringBuilder2.append(info.getQualifiedName());
                            logger3.fine(stringBuilder2.toString());
                            stateObjects.add(info);
                            out = buildOutgoingForInfo(info, out);
                        }
                    }
                }
                if (out.isEmpty()) {
                    advanceObjectsState(stateObjects);
                    cancel();
                    return;
                }
                logger = logger1;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(getName());
                stringBuilder3.append(".run() JmDNS ");
                stringBuilder3.append(getTaskDescription());
                stringBuilder3.append(" #");
                stringBuilder3.append(getTaskState());
                logger.finer(stringBuilder3.toString());
                getDns().send(out);
                advanceObjectsState(stateObjects);
                advanceTask();
                return;
            }
            cancel();
        } catch (Throwable e) {
            logger = logger1;
            Level level = Level.WARNING;
            stringBuilder = new StringBuilder();
            stringBuilder.append(getName());
            stringBuilder.append(".run() exception ");
            logger.log(level, stringBuilder.toString(), e);
            recoverTask(e);
        }
    }

    protected void advanceObjectsState(List<DNSStatefulObject> list) {
        if (list != null) {
            for (DNSStatefulObject object : list) {
                synchronized (object) {
                    object.advanceState(this);
                }
            }
        }
    }

    protected DNSState getTaskState() {
        return this._taskState;
    }

    protected void setTaskState(DNSState taskState) {
        this._taskState = taskState;
    }
}
