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
