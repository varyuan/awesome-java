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

package com.varyuan.awesome.service;

import com.varyuan.awesome.dao.DictDao;
import com.varyuan.awesome.po.Dict;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@CacheConfig(cacheNames = "DictService")
public class DictService {

    @Resource
    private DictDao dictDao;

    @Cacheable// 默认key生成策略SimpleKeyGenerator, 只使用参数值
    public Dict selectById(int id) {
        return dictDao.selectById(id);
    }

    @CacheEvict // 先执行方法之后再清除缓存
    public boolean insert(Dict dict) {
        return dictDao.insert(dict) > 0;
    }

    @CacheEvict
    public boolean delete(int id) {
        return dictDao.delete(id) > 0;
    }
}
