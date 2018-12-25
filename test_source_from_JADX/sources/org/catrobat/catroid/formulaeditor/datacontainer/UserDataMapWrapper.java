package org.catrobat.catroid.formulaeditor.datacontainer;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.catrobat.catroid.formulaeditor.UserData;

public class UserDataMapWrapper<K, V extends UserData> {
    private static final String TAG = UserDataMapWrapper.class.getSimpleName();
    private Map<K, List<V>> map;

    UserDataMapWrapper(Map<K, List<V>> map) {
        this.map = map;
    }

    public boolean add(K key, V element) {
        return new UserDataListWrapper(get(key)).add(element);
    }

    public boolean contains(K key, String name) {
        return this.map.containsKey(key) && new UserDataListWrapper((List) this.map.get(key)).contains(name);
    }

    public boolean contains(String name) {
        for (List<V> value : this.map.values()) {
            if (new UserDataListWrapper(value).contains(name)) {
                return true;
            }
        }
        return false;
    }

    public V get(K key, String name) {
        return this.map.get(key) != null ? new UserDataListWrapper((List) this.map.get(key)).get(name) : null;
    }

    public List<V> get(K key) {
        if (key == null) {
            Log.e(TAG, "This map does not allow null keys.");
            return null;
        } else if (this.map.containsKey(key)) {
            return (List) this.map.get(key);
        } else {
            List<V> value = new ArrayList();
            this.map.put(key, value);
            return value;
        }
    }

    public Set<K> keySet() {
        return this.map.keySet();
    }

    public boolean updateKey(K previousKey, K newKey) {
        if (!this.map.containsKey(previousKey)) {
            return false;
        }
        List<V> value = (List) this.map.get(previousKey);
        this.map.remove(previousKey);
        this.map.put(newKey, value);
        return true;
    }

    public List<V> remove(K key) {
        return (List) this.map.remove(key);
    }

    public boolean remove(String name) {
        for (List<V> value : this.map.values()) {
            if (new UserDataListWrapper(value).remove(name)) {
                return true;
            }
        }
        return false;
    }
}
