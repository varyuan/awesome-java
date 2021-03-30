/*
 * Copyright 2021 varyuan <varyuan@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.varyuan.awesome.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MultiCacheManager extends AbstractCacheManager {

    private final CacheManager localCacheManger;
    private final CacheManager remoteCacheManager;

    public MultiCacheManager(CacheManager localCacheManager, CacheManager remoteManager) {
        this.localCacheManger = localCacheManager;
        this.remoteCacheManager = remoteManager;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        Set<String> localCacheNames = new HashSet<>(localCacheManger.getCacheNames());
        Set<String> remoteCacheNames = new HashSet<>(remoteCacheManager.getCacheNames());
        Collection<Cache> caches = new ArrayList<>();
        localCacheNames.forEach(name -> {
            if (remoteCacheNames.contains(name)) {
                caches.add(new MultiCache(name, localCacheManger.getCache(name), remoteCacheManager.getCache(name)));
            } else {
                caches.add(localCacheManger.getCache(name));
            }
        });

        remoteCacheNames.forEach(name -> {
            if (!localCacheNames.contains(name)) {
                caches.add(remoteCacheManager.getCache(name));
            }
        });
        return caches;
    }
}
