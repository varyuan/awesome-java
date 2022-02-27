/*
 * Copyright 2022 varyuan <varyuan@qq.com>
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

package com.varyuan.awesome.generator;

public final class Util {

    // 下划线转小驼峰
    public static String underscoreToCamelCase(String str) {
        if (str == null || str.length() == 0) return str;
        // 先把所有字母改为小写
        str = str.toLowerCase();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ('_' == c) {
                result.append(Character.toUpperCase(str.charAt(++i)));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // 下划线转大驼峰
    public static String underscoreToBigCamelCase(String str) {
        if (str == null || str.length() == 0) return str;
        // 先把所有字母改为小写
        str = str.toLowerCase();
        StringBuilder result = new StringBuilder();
        result.append(Character.toUpperCase(str.charAt(0)));
        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);
            if ('_' == c) {
                result.append(Character.toUpperCase(str.charAt(++i)));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
