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

package com.varyuan.awesome.dynamic;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
// 启用DynamicDataSourceProperties配置
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
public class DynamicDataSourceConfig {
    @Resource
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Bean
    public AbstractRoutingDataSource dataSource() {
        // 实现AbstractRoutingDataSource的determineCurrentLookupKey方法, 该方法会返回当前要使用的数据源对应的dsKey
        AbstractRoutingDataSource abstractRoutingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DsKeyThreadLocal.getDsKey();
            }
        };
        Map<String, HikariDataSource> datasources = dynamicDataSourceProperties.getDatasources();
        log.info("all datasource key: {}", datasources.keySet());
        // 必须配置默认数据源
        String defaultDsKey = dynamicDataSourceProperties.getDefaultDsKey();
        if (!datasources.containsKey(defaultDsKey)) {
            throw new IllegalArgumentException("must config default datasource");
        }
        log.info("default datasource key: {}", defaultDsKey);
        // 设置所有数据源
        Map<Object, Object> dataSourceMap = new HashMap<>();
        datasources.forEach((dsKey, hikariDataSource) -> {
            dataSourceMap.put(dsKey, hikariDataSource);
        });
        abstractRoutingDataSource.setTargetDataSources(dataSourceMap);
        // 设置默认数据源 当dsKey找不到对应的数据源或没有设置数据源时, 使用默认数据源
        abstractRoutingDataSource.setDefaultTargetDataSource(dataSourceMap.get(defaultDsKey));
        // afterPropertiesSet()方法调用时用来将targetDataSources的属性写入resolvedDataSources中的
        abstractRoutingDataSource.afterPropertiesSet();
        return abstractRoutingDataSource;
    }
}