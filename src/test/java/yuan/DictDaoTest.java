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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest(classes = App.class)
@Slf4j
//@Transactional
public class DictDaoTest {
    @Resource
    private DictDao dictDao;

    @Test
    public void insert() {
        Dict dict = new Dict();
        dict.setKind("召唤师技能");
        dict.setCode("TP");
        dict.setVal("传送");
        int succ = dictDao.insert(dict);
        if (succ != 1) {
            log.error("fail to insert record");
        } else {
            log.info("insert success,new record id is: {}", dict.getId());
        }
    }

    @Test
    public void selectById() {
        Dict dict = dictDao.selectById(1);
        log.info("dict :{}", dict);
    }

    @Test
    public void delete() {
        int succ = dictDao.delete(1);
        if (succ != 1) {
            log.error("fail to delete record");
        } else {
            log.info("delete success");
        }
    }
}
