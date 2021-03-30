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
import com.varyuan.awesome.dynamic.DsKeyThreadLocal;
import com.varyuan.awesome.po.Dict;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = App.class)
@Slf4j
public class DynamicDataSourceTest {

    @Resource
    DictDao dictDao;

    @Test
    public void dynamicDataSourceTest() {
        // 使用主数据源
        DsKeyThreadLocal.setDsKey("source");
        Dict dict = dictDao.selectById(1);
        log.info("dict : {}", dict);

        // 使用从数据源
        DsKeyThreadLocal.setDsKey("replica1");
        dict = dictDao.selectById(1);
        log.info("dict : {}", dict);
    }
}
