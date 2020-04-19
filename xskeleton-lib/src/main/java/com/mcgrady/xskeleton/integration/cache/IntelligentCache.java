package com.mcgrady.xskeleton.integration.cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mcgrady.xskeleton.cache.Cache;
import com.mcgrady.xskeleton.cache.LruCache;
import com.mcgrady.xskeleton.utils.Preconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mcgrady on 2020/4/7.
 */
public class IntelligentCache<V> implements Cache<String, V> {
    public static final String KEY_KEEP = "Keep=";

    private final Map<String, V> map;//可将数据永久存储至内存中的存储容器
    private final Cache<String, V> cache;//当达到最大容量时可根据 LRU 算法抛弃不合规数据的存储容器

    public IntelligentCache(int size) {
        this.map = new HashMap<>();
        this.cache = new LruCache<>(size);
    }

    @NonNull
    public static String getKeyOfKeep(@NonNull String key) {
        Preconditions.checkNotNull(key, "key == null");
        return IntelligentCache.KEY_KEEP + key;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int getMaxSize() {
        return 0;
    }

    @Nullable
    @Override
    public V get(String key) {
        return null;
    }

    @Nullable
    @Override
    public V put(String key, V value) {
        return null;
    }

    @Nullable
    @Override
    public V remove(String key) {
        return null;
    }

    @Override
    public boolean containsKey(String key) {
        return false;
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public void clear() {

    }
}
