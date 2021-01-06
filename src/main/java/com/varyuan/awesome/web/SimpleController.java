package com.varyuan.awesome.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
}
