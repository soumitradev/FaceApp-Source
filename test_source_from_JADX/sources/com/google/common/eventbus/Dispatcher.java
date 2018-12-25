package com.google.common.eventbus;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

abstract class Dispatcher {

    private static final class ImmediateDispatcher extends Dispatcher {
        private static final ImmediateDispatcher INSTANCE = new ImmediateDispatcher();

        private ImmediateDispatcher() {
        }

        void dispatch(Object event, Iterator<Subscriber> subscribers) {
            Preconditions.checkNotNull(event);
            while (subscribers.hasNext()) {
                ((Subscriber) subscribers.next()).dispatchEvent(event);
            }
        }
    }

    private static final class LegacyAsyncDispatcher extends Dispatcher {
        private final ConcurrentLinkedQueue<EventWithSubscriber> queue;

        private static final class EventWithSubscriber {
            private final Object event;
            private final Subscriber subscriber;

            private EventWithSubscriber(Object event, Subscriber subscriber) {
                this.event = event;
                this.subscriber = subscriber;
            }
        }

        private LegacyAsyncDispatcher() {
            this.queue = Queues.newConcurrentLinkedQueue();
        }

        void dispatch(Object event, Iterator<Subscriber> subscribers) {
            Preconditions.checkNotNull(event);
            while (subscribers.hasNext()) {
                this.queue.add(new EventWithSubscriber(event, (Subscriber) subscribers.next()));
            }
            while (true) {
                EventWithSubscriber eventWithSubscriber = (EventWithSubscriber) this.queue.poll();
                EventWithSubscriber e = eventWithSubscriber;
                if (eventWithSubscriber != null) {
                    e.subscriber.dispatchEvent(e.event);
                } else {
                    return;
                }
            }
        }
    }

    private static final class PerThreadQueuedDispatcher extends Dispatcher {
        private final ThreadLocal<Boolean> dispatching;
        private final ThreadLocal<Queue<Event>> queue;

        /* renamed from: com.google.common.eventbus.Dispatcher$PerThreadQueuedDispatcher$1 */
        class C05651 extends ThreadLocal<Queue<Event>> {
            C05651() {
            }

            protected Queue<Event> initialValue() {
                return Queues.newArrayDeque();
            }
        }

        /* renamed from: com.google.common.eventbus.Dispatcher$PerThreadQueuedDispatcher$2 */
        class C05662 extends ThreadLocal<Boolean> {
            C05662() {
            }

            protected Boolean initialValue() {
                return Boolean.valueOf(false);
            }
        }

        private static final class Event {
            private final Object event;
            private final Iterator<Subscriber> subscribers;

            private Event(Object event, Iterator<Subscriber> subscribers) {
                this.event = event;
                this.subscribers = subscribers;
            }
        }

        private PerThreadQueuedDispatcher() {
            this.queue = new C05651();
            this.dispatching = new C05662();
        }

        void dispatch(java.lang.Object r5, java.util.Iterator<com.google.common.eventbus.Subscriber> r6) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/1115170891.run(Unknown Source)
*/
            /*
            r4 = this;
            com.google.common.base.Preconditions.checkNotNull(r5);
            com.google.common.base.Preconditions.checkNotNull(r6);
            r0 = r4.queue;
            r0 = r0.get();
            r0 = (java.util.Queue) r0;
            r1 = new com.google.common.eventbus.Dispatcher$PerThreadQueuedDispatcher$Event;
            r2 = 0;
            r1.<init>(r5, r6);
            r0.offer(r1);
            r1 = r4.dispatching;
            r1 = r1.get();
            r1 = (java.lang.Boolean) r1;
            r1 = r1.booleanValue();
            if (r1 != 0) goto L_0x006b;
        L_0x0025:
            r1 = r4.dispatching;
            r2 = 1;
            r2 = java.lang.Boolean.valueOf(r2);
            r1.set(r2);
        L_0x002f:
            r1 = r0.poll();	 Catch:{ all -> 0x005f }
            r1 = (com.google.common.eventbus.Dispatcher.PerThreadQueuedDispatcher.Event) r1;	 Catch:{ all -> 0x005f }
            r2 = r1;	 Catch:{ all -> 0x005f }
            if (r1 == 0) goto L_0x0054;	 Catch:{ all -> 0x005f }
        L_0x0038:
            r1 = r2.subscribers;	 Catch:{ all -> 0x005f }
            r1 = r1.hasNext();	 Catch:{ all -> 0x005f }
            if (r1 == 0) goto L_0x002f;	 Catch:{ all -> 0x005f }
        L_0x0042:
            r1 = r2.subscribers;	 Catch:{ all -> 0x005f }
            r1 = r1.next();	 Catch:{ all -> 0x005f }
            r1 = (com.google.common.eventbus.Subscriber) r1;	 Catch:{ all -> 0x005f }
            r3 = r2.event;	 Catch:{ all -> 0x005f }
            r1.dispatchEvent(r3);	 Catch:{ all -> 0x005f }
            goto L_0x0038;
        L_0x0054:
            r1 = r4.dispatching;
            r1.remove();
            r1 = r4.queue;
            r1.remove();
            goto L_0x006b;
        L_0x005f:
            r1 = move-exception;
            r2 = r4.dispatching;
            r2.remove();
            r2 = r4.queue;
            r2.remove();
            throw r1;
        L_0x006b:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.eventbus.Dispatcher.PerThreadQueuedDispatcher.dispatch(java.lang.Object, java.util.Iterator):void");
        }
    }

    abstract void dispatch(Object obj, Iterator<Subscriber> it);

    Dispatcher() {
    }

    static Dispatcher perThreadDispatchQueue() {
        return new PerThreadQueuedDispatcher();
    }

    static Dispatcher legacyAsync() {
        return new LegacyAsyncDispatcher();
    }

    static Dispatcher immediate() {
        return ImmediateDispatcher.INSTANCE;
    }
}
