package com.varyuan.awesome.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
public class SimpleController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);

    // curl localhost:8080/awesome/health
    @GetMapping("/health")
    public String healthCheck() {
        return "health check success";
    }

    // curl localhost:8080/awesome/clientIp
    @GetMapping("/clientIp")
    public String clientIp(HttpServletRequest httpServletRequest) {
        String headerVal = httpServletRequest.getHeader("X-Forwarded-For");
        logger.info("/clientIp , from {}", headerVal);
        return headerVal;
    }
}
