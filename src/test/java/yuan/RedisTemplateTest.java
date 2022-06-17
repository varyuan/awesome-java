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

package yuan;

import com.varyuan.awesome.App;
import com.varyuan.awesome.dao.DictDao;
import com.varyuan.awesome.po.Dict;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest(classes = App.class)
@Slf4j
//@Transactional
public class RedisTemplateTest {
    @Resource
    private DictDao dictDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void insert() {
        // String
        redisTemplate.opsForValue().set("name", "xiaobai");
        // Hash
        redisTemplate.opsForHash().put("user1", "name", "clarence");
        redisTemplate.opsForHash().put("user1", "age", "25");
        // List 类型
        redisTemplate.opsForList().leftPushAll("list", "xiaobai", "xiaohei", "xiaolan");
        // Set 类型
        redisTemplate.opsForSet().add("set", "a", "b", "c");
        // SortedSet 类型, 自动使用score降序
        redisTemplate.opsForZSet().add("class", "xiaobai", 90);
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>("xiaohei", 88.0));
        set.add(new DefaultTypedTuple<>("xiaohui", 94.0));
        set.add(new DefaultTypedTuple<>("xiaolan", 84.0));
        set.add(new DefaultTypedTuple<>("xiaolv", 82.0));
        set.add(new DefaultTypedTuple<>("xiaohong", 99.0));
        redisTemplate.opsForZSet().add("zset", set);
    }
}
