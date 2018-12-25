package android.support.test.espresso;

import android.os.Looper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class IdlingRegistry {
    private static final IdlingRegistry instance = new IdlingRegistry();
    private final Set<Looper> loopers = Collections.synchronizedSet(new HashSet());
    private final Set<IdlingResource> resources = Collections.synchronizedSet(new HashSet());

    IdlingRegistry() {
    }

    public static IdlingRegistry getInstance() {
        return instance;
    }

    public boolean register(IdlingResource... idlingResources) {
        if (idlingResources != null) {
            return this.resources.addAll(Arrays.asList(idlingResources));
        }
        throw new NullPointerException("idlingResources cannot be null!");
    }

    public boolean unregister(IdlingResource... idlingResources) {
        if (idlingResources != null) {
            return this.resources.removeAll(Arrays.asList(idlingResources));
        }
        throw new NullPointerException("idlingResources cannot be null!");
    }

    public void registerLooperAsIdlingResource(Looper looper) {
        if (looper == null) {
            throw new NullPointerException("looper cannot be null!");
        } else if (Looper.getMainLooper() == looper) {
            throw new IllegalArgumentException("Not intended for use with main looper!");
        } else {
            this.loopers.add(looper);
        }
    }

    public Collection<IdlingResource> getResources() {
        return new HashSet(this.resources);
    }

    public Collection<Looper> getLoopers() {
        return new HashSet(this.loopers);
    }
}
