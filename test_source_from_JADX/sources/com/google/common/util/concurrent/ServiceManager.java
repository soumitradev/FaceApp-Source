package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.ImmutableSetMultimap$Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.util.concurrent.Monitor.Guard;
import com.google.common.util.concurrent.Service.State;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

@Beta
public final class ServiceManager {
    private static final Callback<Listener> HEALTHY_CALLBACK = new Callback<Listener>("healthy()") {
        void call(Listener listener) {
            listener.healthy();
        }
    };
    private static final Callback<Listener> STOPPED_CALLBACK = new Callback<Listener>("stopped()") {
        void call(Listener listener) {
            listener.stopped();
        }
    };
    private static final Logger logger = Logger.getLogger(ServiceManager.class.getName());
    private final ImmutableList<Service> services;
    private final ServiceManagerState state;

    private static final class EmptyServiceManagerWarning extends Throwable {
        private EmptyServiceManagerWarning() {
        }
    }

    @Beta
    public static abstract class Listener {
        public void healthy() {
        }

        public void stopped() {
        }

        public void failure(Service service) {
        }
    }

    private static final class ServiceManagerState {
        final Guard awaitHealthGuard = new AwaitHealthGuard();
        @GuardedBy("monitor")
        final List<ListenerCallQueue<Listener>> listeners = Collections.synchronizedList(new ArrayList());
        final Monitor monitor = new Monitor();
        final int numberOfServices;
        @GuardedBy("monitor")
        boolean ready;
        @GuardedBy("monitor")
        final SetMultimap<State, Service> servicesByState = MultimapBuilder.enumKeys(State.class).linkedHashSetValues().build();
        @GuardedBy("monitor")
        final Map<Service, Stopwatch> startupTimers = Maps.newIdentityHashMap();
        @GuardedBy("monitor")
        final Multiset<State> states = this.servicesByState.keys();
        final Guard stoppedGuard = new StoppedGuard();
        @GuardedBy("monitor")
        boolean transitioned;

        /* renamed from: com.google.common.util.concurrent.ServiceManager$ServiceManagerState$1 */
        class C10291 implements Function<Entry<Service, Long>, Long> {
            C10291() {
            }

            public Long apply(Entry<Service, Long> input) {
                return (Long) input.getValue();
            }
        }

        final class AwaitHealthGuard extends Guard {
            AwaitHealthGuard() {
                super(ServiceManagerState.this.monitor);
            }

            public boolean isSatisfied() {
                if (!(ServiceManagerState.this.states.count(State.RUNNING) == ServiceManagerState.this.numberOfServices || ServiceManagerState.this.states.contains(State.STOPPING) || ServiceManagerState.this.states.contains(State.TERMINATED))) {
                    if (!ServiceManagerState.this.states.contains(State.FAILED)) {
                        return false;
                    }
                }
                return true;
            }
        }

        final class StoppedGuard extends Guard {
            StoppedGuard() {
                super(ServiceManagerState.this.monitor);
            }

            public boolean isSatisfied() {
                return ServiceManagerState.this.states.count(State.TERMINATED) + ServiceManagerState.this.states.count(State.FAILED) == ServiceManagerState.this.numberOfServices;
            }
        }

        ServiceManagerState(ImmutableCollection<Service> services) {
            this.numberOfServices = services.size();
            this.servicesByState.putAll(State.NEW, services);
        }

        void tryStartTiming(Service service) {
            this.monitor.enter();
            try {
                if (((Stopwatch) this.startupTimers.get(service)) == null) {
                    this.startupTimers.put(service, Stopwatch.createStarted());
                }
                this.monitor.leave();
            } catch (Throwable th) {
                this.monitor.leave();
            }
        }

        void markReady() {
            this.monitor.enter();
            try {
                if (this.transitioned) {
                    List<Service> servicesInBadStates = Lists.newArrayList();
                    Iterator i$ = servicesByState().values().iterator();
                    while (i$.hasNext()) {
                        Service service = (Service) i$.next();
                        if (service.state() != State.NEW) {
                            servicesInBadStates.add(service);
                        }
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Services started transitioning asynchronously before the ServiceManager was constructed: ");
                    stringBuilder.append(servicesInBadStates);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                this.ready = true;
            } finally {
                this.monitor.leave();
            }
        }

        void addListener(Listener listener, Executor executor) {
            Preconditions.checkNotNull(listener, "listener");
            Preconditions.checkNotNull(executor, "executor");
            this.monitor.enter();
            try {
                if (!this.stoppedGuard.isSatisfied()) {
                    this.listeners.add(new ListenerCallQueue(listener, executor));
                }
                this.monitor.leave();
            } catch (Throwable th) {
                this.monitor.leave();
            }
        }

        void awaitHealthy() {
            this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);
            try {
                checkHealthy();
            } finally {
                this.monitor.leave();
            }
        }

        void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (this.monitor.waitForUninterruptibly(this.awaitHealthGuard, timeout, unit)) {
                    checkHealthy();
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Timeout waiting for the services to become healthy. The following services have not started: ");
                stringBuilder.append(Multimaps.filterKeys(this.servicesByState, Predicates.in(ImmutableSet.of(State.NEW, State.STARTING))));
                throw new TimeoutException(stringBuilder.toString());
            } finally {
                this.monitor.leave();
            }
        }

        void awaitStopped() {
            this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
            this.monitor.leave();
        }

        void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (!this.monitor.waitForUninterruptibly(this.stoppedGuard, timeout, unit)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Timeout waiting for the services to stop. The following services have not stopped: ");
                    stringBuilder.append(Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.in(EnumSet.of(State.TERMINATED, State.FAILED)))));
                    throw new TimeoutException(stringBuilder.toString());
                }
            } finally {
                this.monitor.leave();
            }
        }

        ImmutableMultimap<State, Service> servicesByState() {
            ImmutableSetMultimap$Builder<State, Service> builder = ImmutableSetMultimap.builder();
            this.monitor.enter();
            try {
                for (Entry entry : this.servicesByState.entries()) {
                    if (!(entry.getValue() instanceof NoOpService)) {
                        builder.put(entry);
                    }
                }
                return builder.build();
            } finally {
                this.monitor.leave();
            }
        }

        ImmutableMap<Service, Long> startupTimes() {
            this.monitor.enter();
            List<Entry<Service, Long>> list = null;
            try {
                list = Lists.newArrayListWithCapacity(this.startupTimers.size());
                for (Entry<Service, Stopwatch> entry : this.startupTimers.entrySet()) {
                    Service service = (Service) entry.getKey();
                    Stopwatch stopWatch = (Stopwatch) entry.getValue();
                    if (!(stopWatch.isRunning() || (service instanceof NoOpService))) {
                        list.add(Maps.immutableEntry(service, Long.valueOf(stopWatch.elapsed(TimeUnit.MILLISECONDS))));
                    }
                }
                Collections.sort(list, Ordering.natural().onResultOf(new C10291()));
                return ImmutableMap.copyOf(list);
            } finally {
                this.monitor.leave();
            }
        }

        void transitionService(Service service, State from, State to) {
            Preconditions.checkNotNull(service);
            Preconditions.checkArgument(from != to);
            this.monitor.enter();
            try {
                this.transitioned = true;
                if (this.ready) {
                    Preconditions.checkState(this.servicesByState.remove(from, service), "Service %s not at the expected location in the state map %s", service, from);
                    Preconditions.checkState(this.servicesByState.put(to, service), "Service %s in the state map unexpectedly at %s", service, to);
                    Stopwatch stopwatch = (Stopwatch) this.startupTimers.get(service);
                    if (stopwatch == null) {
                        stopwatch = Stopwatch.createStarted();
                        this.startupTimers.put(service, stopwatch);
                    }
                    if (to.compareTo(State.RUNNING) >= 0 && stopwatch.isRunning()) {
                        stopwatch.stop();
                        if (!(service instanceof NoOpService)) {
                            ServiceManager.logger.log(Level.FINE, "Started {0} in {1}.", new Object[]{service, stopwatch});
                        }
                    }
                    if (to == State.FAILED) {
                        fireFailedListeners(service);
                    }
                    if (this.states.count(State.RUNNING) == this.numberOfServices) {
                        fireHealthyListeners();
                    } else if (this.states.count(State.TERMINATED) + this.states.count(State.FAILED) == this.numberOfServices) {
                        fireStoppedListeners();
                    }
                    this.monitor.leave();
                    executeListeners();
                }
            } finally {
                this.monitor.leave();
                executeListeners();
            }
        }

        @GuardedBy("monitor")
        void fireStoppedListeners() {
            ServiceManager.STOPPED_CALLBACK.enqueueOn(this.listeners);
        }

        @GuardedBy("monitor")
        void fireHealthyListeners() {
            ServiceManager.HEALTHY_CALLBACK.enqueueOn(this.listeners);
        }

        @GuardedBy("monitor")
        void fireFailedListeners(final Service service) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("failed({service=");
            stringBuilder.append(service);
            stringBuilder.append("})");
            new Callback<Listener>(stringBuilder.toString()) {
                void call(Listener listener) {
                    listener.failure(service);
                }
            }.enqueueOn(this.listeners);
        }

        void executeListeners() {
            Preconditions.checkState(this.monitor.isOccupiedByCurrentThread() ^ 1, "It is incorrect to execute listeners with the monitor held.");
            for (int i = 0; i < this.listeners.size(); i++) {
                ((ListenerCallQueue) this.listeners.get(i)).execute();
            }
        }

        @GuardedBy("monitor")
        void checkHealthy() {
            if (this.states.count(State.RUNNING) != this.numberOfServices) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected to be healthy after starting. The following services are not running: ");
                stringBuilder.append(Multimaps.filterKeys(this.servicesByState, Predicates.not(Predicates.equalTo(State.RUNNING))));
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }

    private static final class ServiceListener extends com.google.common.util.concurrent.Service.Listener {
        final Service service;
        final WeakReference<ServiceManagerState> state;

        ServiceListener(Service service, WeakReference<ServiceManagerState> state) {
            this.service = service;
            this.state = state;
        }

        public void starting() {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                state.transitionService(this.service, State.NEW, State.STARTING);
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.FINE, "Starting {0}.", this.service);
                }
            }
        }

        public void running() {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                state.transitionService(this.service, State.STARTING, State.RUNNING);
            }
        }

        public void stopping(State from) {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                state.transitionService(this.service, from, State.STOPPING);
            }
        }

        public void terminated(State from) {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.log(Level.FINE, "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, from});
                }
                state.transitionService(this.service, from, State.TERMINATED);
            }
        }

        public void failed(State from, Throwable failure) {
            ServiceManagerState state = (ServiceManagerState) this.state.get();
            if (state != null) {
                if (!(this.service instanceof NoOpService)) {
                    Logger access$200 = ServiceManager.logger;
                    Level level = Level.SEVERE;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Service ");
                    stringBuilder.append(this.service);
                    stringBuilder.append(" has failed in the ");
                    stringBuilder.append(from);
                    stringBuilder.append(" state.");
                    access$200.log(level, stringBuilder.toString(), failure);
                }
                state.transitionService(this.service, from, State.FAILED);
            }
        }
    }

    private static final class NoOpService extends AbstractService {
        private NoOpService() {
        }

        protected void doStart() {
            notifyStarted();
        }

        protected void doStop() {
            notifyStopped();
        }
    }

    public ServiceManager(Iterable<? extends Service> services) {
        ImmutableList<Service> copy = ImmutableList.copyOf(services);
        if (copy.isEmpty()) {
            logger.log(Level.WARNING, "ServiceManager configured with no services.  Is your application configured properly?", new EmptyServiceManagerWarning());
            copy = ImmutableList.of(new NoOpService());
        }
        this.state = new ServiceManagerState(copy);
        this.services = copy;
        WeakReference<ServiceManagerState> stateReference = new WeakReference(this.state);
        Iterator i$ = copy.iterator();
        while (i$.hasNext()) {
            Service service = (Service) i$.next();
            service.addListener(new ServiceListener(service, stateReference), MoreExecutors.directExecutor());
            Preconditions.checkArgument(service.state() == State.NEW, "Can only manage NEW services, %s", service);
        }
        this.state.markReady();
    }

    public void addListener(Listener listener, Executor executor) {
        this.state.addListener(listener, executor);
    }

    public void addListener(Listener listener) {
        this.state.addListener(listener, MoreExecutors.directExecutor());
    }

    public ServiceManager startAsync() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            Preconditions.checkState(((Service) i$.next()).state() == State.NEW, "Service %s is %s, cannot start it.", (Service) i$.next(), ((Service) i$.next()).state());
        }
        i$ = this.services.iterator();
        while (i$.hasNext()) {
            Service service = (Service) i$.next();
            try {
                this.state.tryStartTiming(service);
                service.startAsync();
            } catch (IllegalStateException e) {
                Logger logger = logger;
                Level level = Level.WARNING;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to start Service ");
                stringBuilder.append(service);
                logger.log(level, stringBuilder.toString(), e);
            }
        }
        return this;
    }

    public void awaitHealthy() {
        this.state.awaitHealthy();
    }

    public void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
        this.state.awaitHealthy(timeout, unit);
    }

    public ServiceManager stopAsync() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            ((Service) i$.next()).stopAsync();
        }
        return this;
    }

    public void awaitStopped() {
        this.state.awaitStopped();
    }

    public void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
        this.state.awaitStopped(timeout, unit);
    }

    public boolean isHealthy() {
        Iterator i$ = this.services.iterator();
        while (i$.hasNext()) {
            if (!((Service) i$.next()).isRunning()) {
                return false;
            }
        }
        return true;
    }

    public ImmutableMultimap<State, Service> servicesByState() {
        return this.state.servicesByState();
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.state.startupTimes();
    }

    public String toString() {
        return MoreObjects.toStringHelper(ServiceManager.class).add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }
}
