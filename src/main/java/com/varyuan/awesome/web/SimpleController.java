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

package com.varyuan.awesome.web;


import com.varyuan.awesome.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

@RestController
public class SimpleController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);

    // curl localhost:8080/awesome/health
    // 健康检查
    @GetMapping("/health")
    public String healthCheck() {
        return "health check success";
    }

    // curl localhost:8080/awesome/clientIp
    // 返回请求头 X-Forwarded-For 的值
    @GetMapping("/clientIp")
    public String clientIp(HttpServletRequest httpServletRequest) {
        String clientIp = IpUtil.getClientIp(httpServletRequest);
        logger.info("/clientIp , from {}", clientIp);
        return clientIp;
    }

    // curl localhost:8080/awesome/env
    // 返回服务器所有的环境变量
    @GetMapping("/env")
    public Map<String, String> env() {
        return System.getenv();
    }

    // curl -v localhost:8080/awesome/locale
    // ie11打开:http://localhost:8080/awesome/locale ,请求头:Accept-Language: zh-Hans-CN, zh-Hans; q=0.5 ,后端打印 zh_CN_#Hans
    // chrome打开: http://localhost:8080/awesome/locale ,请求头: Accept-Language: zh-CN,zh;q=0.9 ,后端打印 zh_CN
    @GetMapping("/locale")
    public Locale locale(HttpServletRequest httpServletRequest) {
        Locale locale = httpServletRequest.getLocale();
        logger.info("/locale , locale is:{}", locale);
        return locale;
    }

    // curl localhost:8080/awesome/sleep?seconds=5 -w '\n'
    // 自定义多少秒后响应
    @GetMapping("/sleep")
    public String sleep(int seconds) throws InterruptedException {
        logger.info("/sleep , seconds: {}", seconds);
        Thread.sleep(seconds * 1000L);
        return "sleep end";
    }
}
