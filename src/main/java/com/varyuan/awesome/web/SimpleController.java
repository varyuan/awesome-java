package com.varyuan.awesome.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

@CrossOrigin
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
        String headerVal = httpServletRequest.getHeader("X-Forwarded-For");
        logger.info("/clientIp , from {}", headerVal);
        return headerVal;
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
}
