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

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
     主键使用bigint Long id  从6666开始递增


     */
    public static void main(String[] args) {
        String ddl = "CREATE TABLE `mall_user` (\n" +
                "  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户主键id',\n" +
                "  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',\n" +
                "  `login_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',\n" +
                "  `password_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',\n" +
                "  `introduce_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '个性签名',\n" +
                "  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货地址',\n" +
                "  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '注销标识字段(0-正常 1-已注销)',\n" +
                "  `locked_flag` tinyint NOT NULL DEFAULT '0' COMMENT '锁定标识字段(0-未锁定 1-已锁定)',\n" +
                "  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',\n" +
                "  PRIMARY KEY (`user_id`) USING BTREE\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='会员表';";
        String packageName = "com.varyuan.awesome";
        Bom bom = parse(ddl, packageName);
//        System.out.println(Generator.po(bom));
//        System.out.println(Generator.mapper(bom));
//        System.out.println(Generator.dao(bom));
//        System.out.println(Generator.service(bom));
        System.out.println(Generator.controller(bom));
    }

    static Bom parse(String ddl, String packageName) {
        Bom bom = new Bom();
        String[] split = ddl.split("\n");
        List<Column> columnList = new ArrayList<>();
        String tableName = get_(split[0]);
        bom.setPackageName(packageName);
        bom.setTableName(tableName);
        bom.setPoName(Util.underscoreToBigCamelCase(tableName));
        bom.setPoNameLow(Util.underscoreToCamelCase(tableName));

        for (int i = 1; i < split.length; i++) {
            String line = split[i];
            String type = null;
            if (line.contains("bigint")) {
                type = "Long";
            } else if (line.contains("int")) {
                type = "Integer";
            } else if (line.contains("varchar")) {
                type = "String";
            } else if (line.contains("datetime")) {
                type = "LocalDateTime";
            } else {
                continue;
            }
            String column = get_(line);
            String field = Util.underscoreToCamelCase(column);
            columnList.add(new Column(column, field, type));
        }
        bom.setColumnList(columnList);
        return bom;
    }

    static String get_(String line) {
        int start = line.indexOf("`");
        int end = line.indexOf("`", start + 1);
        return line.substring(start + 1, end);
    }
}

@Data
class Bom {
    private String tableName;
    private String packageName;
    private String poName;
    private String poNameLow;
    private List<Column> columnList;
}

@Data
class Column {
    private String column;
    private String field;
    private String type;

    public Column(String column, String field, String type) {
        this.column = column;
        this.field = field;
        this.type = type;
    }
}
