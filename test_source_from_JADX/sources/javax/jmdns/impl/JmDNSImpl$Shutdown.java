package javax.jmdns.impl;

import java.io.PrintStream;

protected class JmDNSImpl$Shutdown implements Runnable {
    final /* synthetic */ JmDNSImpl this$0;

    protected JmDNSImpl$Shutdown(JmDNSImpl jmDNSImpl) {
        this.this$0 = jmDNSImpl;
    }

    public void run() {
        try {
            this.this$0._shutdown = null;
            this.this$0.close();
        } catch (Throwable exception) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error while shuting down. ");
            stringBuilder.append(exception);
            printStream.println(stringBuilder.toString());
        }
    }
}
