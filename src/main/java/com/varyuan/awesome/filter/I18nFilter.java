package com.varyuan.awesome.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Locale;

// 国际化过滤器
// 适配情况:ie11中弃用了zh_CN请求头，而采用zh-Hans-CN
@Component
public class I18nFilter implements Filter {

    private final String zh = Locale.CHINA.getLanguage();
    private final String CN = Locale.CHINA.getCountry();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequestWrapper zhCnWrapper = new HttpServletRequestWrapper((HttpServletRequest) servletRequest) {
            @Override
            public Locale getLocale() {
                Locale loc = super.getLocale();
                String language = loc.getLanguage();
                String country = loc.getCountry();
                if ((zh.equals(language) && CN.equals(country))) {
                    return Locale.CHINA;
                } else {
                    return loc;
                }
            }
        };
        filterChain.doFilter(zhCnWrapper, servletResponse);
    }
}
