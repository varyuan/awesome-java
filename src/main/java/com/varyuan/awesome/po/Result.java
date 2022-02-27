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

package com.varyuan.awesome.po;

public final class Result {
    private int code;
    private String msg;
    private Object data;

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    static Result OK = new Result(200, null, null);
    static Result ERROR = new Result(500, null, null);

    public static Result OK() {
        return OK;
    }

    public static Result OK(Object data) {
        return new Result(200, null, data);
    }

    public static Result ERROR(String msg) {
        return new Result(500, msg, null);
    }

    public static Result ERROR() {
        return ERROR;
    }

}
