/*
 * Copyright 2022 varyuan <varyuan@qq.com>
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

package com.varyuan.awesome.web;

import com.github.benmanes.caffeine.cache.Cache;
import com.varyuan.awesome.po.Dict;
import com.varyuan.awesome.service.DictService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RequestMapping("/dict")
@RestController
public class DictController {

    @Resource
    private DictService dictService;

    // curl localhost:8080/awesome/dict/1
    @GetMapping("/{id}")
    public Dict selectById(@PathVariable int id) {
        return dictService.selectById(id);
    }

    @PostMapping("/insert")
    public boolean insert(Dict dict) {
        return dictService.insert(dict);
    }

    // curl -X POST localhost:8080/awesome/dict/delete/1
    @PostMapping("/delete/{id}")
    public boolean delete(@PathVariable int id) {
        return dictService.delete(id);
    }

    @Resource
    private CacheManager cacheManager;

    // 缓存命中率统计
    // curl localhost:8080/awesome/dict/stats
    @GetMapping("/stats")
    public List<Map<String, Object>> recordStats() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        List<Map<String, Object>> list = new ArrayList<>(cacheNames.size());
        for (String cacheName : cacheNames) {
            CaffeineCache cache = (CaffeineCache) cacheManager.getCache(cacheName);
            Cache<Object, Object> nativeCache = cache.getNativeCache();
            //
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("cacheName", cacheName);
            map.put("size", nativeCache.estimatedSize());
            map.put("stats", nativeCache.stats());
            map.put("data", nativeCache.asMap());
            list.add(map);
        }
        return list;
    }
}
